
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import models.*;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Constant;
import utils.Tools;

import com.fasterxml.jackson.databind.JsonNode;


public class UserManager extends Controller {

	/**
	 * Description: Register a candidate and an account for him
	 * @return
	 * Result
	 */
	public static Result register() {
    	JsonNode json = request().body().asJson();

		Candidate candidate = null;
		Account account = null;

		try {

			// parse json data to objects
			candidate = Tools.fromJson(json.findValue("candidate"), Candidate.class);
			account = Tools.fromJson(json.findValue("account"), Account.class);

			// this is an account of a candidate
			account.role = 1;
			account.active = true;

			//set Date java.sql.Date
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = df.parse(json.findValue("candidate").findValue("birthday").asText());
		    candidate.birthday = new java.sql.Date(date.getTime());

			// save into db
			account.save();
			candidate.account = account;
			candidate.save();

		} catch (Exception e) {
	    	System.out.println(e);
			// TODO: handle exception
	    	return badRequest();
		}
    	return noContent();
	}

	/**
	 * Description: find Candidate with email parameter
	 * @param email
	 * @return Candidate
	 */
	public static Result getProfile(String email){
		Candidate candidate = Candidate.getByEmail(email);
        if(candidate == null)
            return TODO;
		return ok(Tools.toJson(candidate));
	}

	/**
	 * Description: received Candidate object from a candidate.
	 * 				Then update the changes of candidate into Candidate table
	 * @return Result
	 */
	public static Result modifyProfile(){
    	JsonNode json = request().body().asJson();
		Candidate candidate;
		try {
			candidate = Tools.fromJson(json, Candidate.class);
			candidate.update();
		} catch (IOException e) {
			System.out.println("Modify Profile caughts some errors!!!");
			e.printStackTrace();
		}
		return noContent();
	}

	/**
	 * Description: received Account object from a candidate.
	 * 				Then update the password of candidate into Account table
	 * @return Result
	 */
	public static Result changePass(){
    	JsonNode json = request().body().asJson();

		Account account;
		account = Account.authenticate(json.findValue("email").asText(), json.findValue("oldPass").asText());

		if (!json.findValue("email").asText().equals(session().values().toArray()[0].toString()))
			return badRequest(Constant.CAN_NOT_CHANGE_PASS);
		if (account == null)
			return badRequest("Old " + Constant.PASSWORD_INCORRECT);
		if (!json.findValue("newPass").asText().equals(json.findValue("confirmNewPass").asText()))
			return badRequest(Constant.PASSWORD_NOT_MATCH);

		account.password = json.findValue("newPass").asText();
		account.update();

		return ok("It works!");
	}

    //admin action
    public static Result loadAllUser(){
       try {
           List<Candidate> candidateList = Candidate.getAllCandidate();
           int score = 0;
           MyCandidate myCandidate;
           List<MyCandidate>dataResult = new ArrayList<>();
           for(int i=0; i<candidateList.size(); i++){
               Candidate c = candidateList.get(i);
               score = Score.getScoreByEmail(c.account.email);
               myCandidate = new MyCandidate(c,score);
               dataResult.add(myCandidate);
           }
           return ok(Tools.listToJson(dataResult));

       }catch(Exception e){
           System.out.println(e.getMessage().toString());
       }
        return noContent();
    }

    //inner class to simplify data to be sent to client
    static class MyCandidate{
        public Candidate candidate;
        public int score;

        public MyCandidate(Candidate c, int aScore){
            this.candidate = c;
            this.score = aScore;
        }
    }

    //delete user
    public static Result deleteUser(String email){

        try {
            Candidate candidate = Candidate.getByEmail(email);
            //find all data related to this candidate
            Account candidate_account = candidate.account;
            List<Answer> candidate_answerList = Candidate.getAnswerList(candidate.ID);

            if(candidate_answerList.size() != 0){
                for(int i=0; i<candidate_answerList.size(); i++){
                    Answer answer = candidate_answerList.get(i);
                    answer.delete();
                }
            }

            Score candidate_score = Score.findScore(email);
            if(candidate_score != null){
                candidate_score.delete();
            }

            candidate.delete();

            candidate_account.delete();

        }catch (Exception ex){
            System.out.println("ERROR DELETE USER :     "+ex.getMessage().toString());
        }
        return ok();
    }

    public static Result sendDataToClient(String userEmail){

        Candidate c = Candidate.getByEmail(userEmail);

        if(c == null)
            return noContent();

        Exam exam = c.exam;
        //Get list of question order by sequence.
        List<OrderQuestion> questionList = exam.questions;

        List<Answer> candidateAnswer = Candidate.getAnswerList(c.ID);
        Data d;
        List<Data>dataReturn = new ArrayList<>();
        for(int i = 0; i < questionList.size(); i++){
            Question q = questionList.get(i).question;
            List<Quiz>quizList = q.quizzes;
            modifyQuizzes(quizList);
            Answer answer = candidateAnswer.get(i);
            String a = answer.answerValue;
            if(a.equals(""))
                a = "0000";
            int score = 0;
            //override for test
            if(q.type != 3){
                q.answerValue = Data.convertAnswer(q.answerValue);
                a = Data.convertAnswer(a);


                if(q.answerValue.equals(a)){
                    score = 1;
                }
                else {
                    score = 0;
                }
            }
            d = new Data(q,a,score);
            dataReturn.add(d);
        }


        return ok(Tools.listToJson(dataReturn));
    }

    private static void modifyQuizzes(List<Quiz>quizList) {
        char[] pattern = {'A', 'B', 'C', 'D'};
        int size = quizList.size();
        if (size == 1)
            pattern = Constant.pattern1;
        else if (size == 2)
            pattern = Constant.pattern2;
        else if (size == 3)
            pattern = Constant.pattern3;
        else if (size == 4)
            pattern = Constant.pattern4;
        else if (size == 5)
            pattern = Constant.pattern5;
        else if (size == 6)
            pattern = Constant.pattern6;
        else if (size == 7)
            pattern = Constant.pattern7;
        else if (size == 8)
            pattern = Constant.pattern8;
        else if (size == 9)
            pattern = Constant.pattern9;
        else if (size == 10)
            pattern = Constant.pattern10;

        for (int i = 0; i < size; i++) {
            Quiz quiz = quizList.get(i);
            String letter = String.valueOf(pattern[i]);
            quiz.content = letter + ". " + quiz.content;
        }
    }
            //use inner class to simplify data
    static class Data {
      public Question question ;
      public String answer;
      public int score ;

      public Data(Question q, String a, int theScore){
          this.question = q;
          this.answer = a;
          this.score = theScore;
      }

                //convert answer to origin ABCD
      public static String convertAnswer(String a){
          String result = "";

          Integer integerValue = Integer.parseInt(a);
          int answerInt = integerValue.intValue();
          String answerBit = Integer.toBinaryString(answerInt);
          String actualAnswer = new StringBuffer(answerBit).reverse().toString();

          char[] answerArray = actualAnswer.toCharArray();
          char[] patternArray =  matchingChar(answerArray.length);

          for(int i=0; i<answerArray.length; i++){
              if(answerArray[i] == '1'){
                  result += patternArray[i];
              }
          }
          return  result;
      }

            //matching binary string to character array in quizzes
       private static char[] matchingChar(int len){
           char[]result = {'A','B','C','D'};
            switch (len){
                case 0:
                    break;
                case 1:
                    result = Constant.pattern1;
                    break;
                case 2:
                    result = Constant.pattern2;
                    break;
                case 3:
                    result = Constant.pattern3;
                    break;
                case 4:
                    result = Constant.pattern4;
                    break;
                case 5:
                    result = Constant.pattern5;
                    break;
                case 6:
                    result = Constant.pattern6;
                    break;
                case 7:
                    result = Constant.pattern7;
                    break;
                case 8:
                    result = Constant.pattern8;
                    break;
                case 9:
                    result = Constant.pattern9;
                    break;
                case 10:
                    result = Constant.pattern10;
                    break;

            }
            return result;
       }
    };


    //save email and score to database
    public static Result saveScore(String userEmail, String userScore){

        Score score = new Score(userEmail,Integer.valueOf(userScore).intValue());
        int check = 0;
        check = Score.find.where().eq("userEmail",userEmail).findRowCount();
        if(check != 0)
            score.update();
        else
            score.save();
        return ok();
    }


    public static Result saveUserList(){

        return TODO;
    }
    /**
     * Description: Deactive an account.
     * @param email
     * @return
     * Result
     */
    public static Result deactive(String email){
    	Account account = Account.getById(email);

    	if(account == null){
    		return badRequest();
    	}

    	account.active = false;
    	account.update();
    	return ok(account.toJson());
    }

    /**
     * Description: set exam id for candidate.
     * @param email
     * @param id
     * @return
     * Result
     */
    public static Result setExam(String email, long id){
    	Candidate candidate = Candidate.getByEmail(email);
    	Exam exam = Exam.getExamById(id);

    	candidate.exam = exam;
    	candidate.update();
    	return ok("success");
    }
}
