
package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import models.Exam;
import models.OrderQuestion;
import models.Question;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Constant;
import utils.Tools;

/**
 * This is class ExamManager.java
 * Update the exam by Id
 * @param id
 * @returns The exam view
 */
public class ExamManager extends Controller{

	public static Result updateExam(Long id){
		Exam exam = Exam.getExamById(id);
		JsonNode json = request().body().asJson();
		if(exam == null){
			return badRequest(Constant.CAN_NOT_FIND_EXAM);
		}
		else{
			try{
				exam.name = json.findValue("name").asText();
				exam.duration = json.findValue("duration").asInt();
				exam.language = json.findValue("language").asText();
				exam.numberOfQuestion = json.findValue("numberQuestion").asInt();
				deleteOrderQuestion(exam);
				addQuestion(json, exam.numberOfQuestion, exam);
			}catch(Exception e){
				System.out.println(e);
			}
		}
		return noContent();
	}

	/**
	 * Delete all orderQuestion by exam.
	 * @param exam
	 */
	public static void deleteOrderQuestion(Exam exam){
		OrderQuestion temp;
		List<OrderQuestion> orderQuestion = exam.questions;
		while(!orderQuestion.isEmpty()){
			temp = orderQuestion.get(0);
			temp.exam = null;
			temp.question.exam = null;
			temp.question = null;
			temp.delete();
			orderQuestion.remove(0);
		}
	}

	/**
	 * Delete the exam of given Id
	 *
	 * @param id
	 * 			Id of the exam to deleteById
	 * @return The exam view
	 */
	public static Result deleteExam(Long id){
		Exam exam = Exam.getExamById(id);
		if(exam == null){
			return badRequest(Constant.CAN_NOT_FIND_EXAM);
		}else{
			try{
				deleteOrderQuestion(exam);
				exam.delete();
			}catch(Exception e){
				System.out.println(e);
			}
		}
		return noContent();
	}

	/**
	 * Create a new exam from a json request
	 *
	 * @return The exam view
	 */
	public static Result exam(){
		JsonNode json = request().body().asJson();
		Exam exam;
		try{
			exam = new Exam(json.findValue("name").asText(), json.findValue("duration").asInt(),
					json.findValue("language").asText(), json.findValue("numberQuestion").asInt());
			Exam.create(exam);
			addQuestion(json, exam.numberOfQuestion, exam);

		}catch(Exception e){
			System.out.println(e);
		}
		return noContent();
	}

	/**
	 * Exam management page
	 *
	 * @return the exam view
	 */
	public static Result exams(){
		List<Exam> result = Exam.getAllExam();
		return ok(Tools.listToJson(result));
	}

	/**
	 * Add each question into exam.
	 * @param json
	 * @param length
	 * @param exam
	 */
	public static void addQuestion(JsonNode json, int length, Exam exam){
		Question question;
		OrderQuestion orderQuestion;
		System.out.println(length);
		//Read each question and save them.
		for(int i = 0; i < length; i++){
			question = Question.findById(json.findValue("questions").get(i).findValue("ID").asLong());
			if(question == null){
				badRequest(Constant.CAN_NOT_FIND_QUESTION);
			}else{
				orderQuestion = new OrderQuestion(i, question, exam);
				question.exam.add(orderQuestion);
				exam.questions.add(orderQuestion);
			}
		}
		exam.update();
		exam.save();
	}
}
