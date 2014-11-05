/**
 * Copyright by Technologic Arts
 * Project: EXAMONLINE
 * Package: controllers
 * Author: khacvu
 */
package controllers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
//import com.sun.xml.internal.ws.wsdl.writer.document.soap.Body;

import models.Question;
import models.Quiz;
import play.api.libs.json.Json;
import play.mvc.Controller;
import play.mvc.Result;
//import utils.TestLevel;
import utils.Tools;

public class QuestionManager extends Controller {

	/**
	 * Question management page
	 *
	 * @return the question view
	 */
	public static Result questions(){
		List<Question> allQuestion = Question.getAllQuestion();
		return ok(Tools.listToJson(allQuestion));
	}

	/**
	 * Delete the question of given Id
	 *
	 * @param id
	 * 			Id of the question to deleteById
	 * @return The question view
	 */
	public static Result deleteQuestion(Long id) {
		Question question = Question.findById(id);
		if(question == null){
			System.out.println("No found question");
			return noContent();
		}
		else{
			try{
				Question.deleted(question);
			}catch(Exception e){
				System.out.println(e);
			}
			return ok("Delete Successful!");
		}
	}

	/**
	 * Create a new question from a json request
	 *
	 * @return The question view
	 */
	public static Result newQuestion() {
		JsonNode json = request().body().asJson();
		Question question = new Question();
		List<Quiz> quizzes = new ArrayList<Quiz>();

		try{
			question.content = json.findValue("content").asText();
			question.type = json.findValue("type").findValue("type").asInt();
			question.answerValue = json.findValue("answerValue").asText();
			question.language = json.findValue("language").asText();
			question.level = json.findValue("level").asText();
			Question.create(question);

			if(!json.findValue("type").findValue("text").asBoolean()){
				//Read each quiz and save them.
				for(int i = 0; i < json.findValue("quizzes").size(); i++){
					Quiz temp = new Quiz(json.findValue("quizzes").get(i).findValue("content").asText(),
						json.findValue("quizzes").get(i).findValue("sequence").asInt());
					temp.question = question;
					Quiz.create(temp);
					quizzes.add(temp);
				}
			}
		}catch (Exception e){
	    	System.out.println(e);
		}
		return noContent();
	}

	/**
	 * Update the question with the given Id
	 *
	 * @param id
	 * 			Id of the question to updateById
	 * @return The question view
	 */
	public static Result updateQuestion(Long id) {
		Question question = Question.findById(id);
		JsonNode json = request().body().asJson();
		Quiz quiz;
		List<Quiz> quizzes;

		if(question == null){
			System.out.println("No found question.");
			return noContent();
		}else{

			try{
				question.content = json.findValue("content").asText();
				question.type = json.findValue("type").findValue("type").asInt();
				question.answerValue = json.findValue("answerValue").asText();
				question.language = json.findValue("language").asText();
				question.level = json.findValue("level").asText();
				Question.create(question);
				//if question is choice question
				if(!json.findValue("type").findValue("text").asBoolean())
				{
					//Read each quiz in question
					for(int i = 0; i < json.findValue("quizzes").size(); i++){
						Integer temp = json.findValue("quizzes").get(i).findValue("id").asInt();
						quiz = Quiz.findById(temp.longValue());
						//if question don't include quiz, create it.
						if(quiz == null){
							Quiz newQuiz = new Quiz(json.findValue("quizzes").get(i).findValue("content").asText(),
									json.findValue("quizzes").get(i).findValue("sequence").asInt());
							newQuiz.question = question;
							Quiz.create(newQuiz);
						}else{
						//if question include quiz, edit it.
							quiz.content = json.findValue("quizzes").get(i).findValue("content").asText();
							quiz.sequence = json.findValue("quizzes").get(i).findValue("sequence").asInt();
							quiz.question = question;
							quiz.save();
						}
					}
				}else{
					//if question is text question, delete all quiz.
					quizzes = Quiz.findByIdQuestion(id);
					while(!quizzes.isEmpty()){
						quiz = quizzes.get(0);
						quizzes.remove(0);
						quiz.question = null;
						quiz.update();
						quiz.delete();
					}
				}
			}catch (Exception e){
				System.out.println(e);
			}
			return ok("Update successful!");
		}
	}
}
