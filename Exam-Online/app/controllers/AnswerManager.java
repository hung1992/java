/**
 * Copyright by Technologic Arts
 * Project: EXAMONLINE
 * Package: controllers
 * Author: ta-08
 */
package controllers;

import models.*;

import com.fasterxml.jackson.databind.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * This is class AnswerManager.java
 */
public class AnswerManager extends Controller {

    /**
     * Description: received the results from the candidate
     * 				save the results to database
     * @return
     * Result
     */
	public static Result answer(String email){
		Candidate candidate = Candidate.getByEmail(email);
    	JsonNode json = request().body().asJson();
		for (int i = 0; i < json.size(); i++){
			Question question = Question.getById(json.get(i).findValue("questionID").asLong());
			Answer answer = new Answer();
			answer.candidate = candidate;
			answer.question = question;
			answer.answerValue = json.get(i).findValue("answerValue").asText();
			answer.save();
		}
		
		return ok("It works!");
	}
}
