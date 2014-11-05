package controllers;

import java.util.List;

import models.OrderQuestion;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Tools;

/**
 *
 * @author Yokovu
 *
 */
public class OrderQuestionManager extends Controller{


	public static Result orderQuestions(){
		List<OrderQuestion> allOrderQuestions = OrderQuestion.findAll();
		return ok(Tools.listToJson(allOrderQuestions));
	}

	public static Result getByIdExam(Long id){
		return ok(Tools.listToJson(OrderQuestion.findByIdExam(id)));
	}

}
