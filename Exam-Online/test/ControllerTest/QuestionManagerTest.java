package ControllerTest;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

import java.io.IOException;
import java.util.List;

import models.Question;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

import play.mvc.Result;
import utils.Tools;
import controllers.routes;

public class QuestionManagerTest {

	@Test
	public void shouldReturnAllQuestionWhenGetAllQuestion(){
		running(fakeApplication(), new Runnable() {
			
			@Override
			public void run() {
				Result result = callAction(routes.ref.QuestionManager.questions(), fakeRequest());
				assertEquals(HttpStatus.OK_200, status(result));
				try {
					List<Question> qs = Tools.listFromJson(contentAsString(result), Question.class);
					assertEquals(qs.size(), 20);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	@Test
	public void shouldReuturnOkWhenCreateQuestion(){
		
	}


}