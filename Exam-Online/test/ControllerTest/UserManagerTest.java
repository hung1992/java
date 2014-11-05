package ControllerTest;

import java.util.HashMap;
import java.util.Map;

import models.Candidate;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

import play.mvc.Result;
import utils.Tools;
import static org.fest.assertions.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.routes;
import static play.test.Helpers.*;

public class UserManagerTest {
	private static final String TYPE_JSON = "application/json";
	
	@Test
	public void testRegister() {		       
	     running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
            	Map<String, String> candidate = new HashMap<>();
            	candidate.put("fullName", "Pham Trung Kha");
            	candidate.put("birthday", "1992-11-11");
            	candidate.put("gender", "Male");
            	candidate.put("address", "Phu Nhuan");
            	candidate.put("phoneNumber", "0979315329");
            	candidate.put("experience", "3");
            	candidate.put("language", "java");
            	candidate.put("position", "Developer");
            	candidate.put("level", "InternShip");

            	Map<String, String> account = new HashMap<>();
            	account.put("email", "phamtrungkha@gmail.com");
            	account.put("password", "111111");
            	
            	Map<String, Map<String, String>> json = new HashMap<>();
            	json.put("candidate", candidate);
            	json.put("account", account);
            	
            	JsonNode jNode = Tools.toJson(json);
				Result result = callAction(routes.ref.UserManager.register(),
						fakeRequest().withJsonBody(jNode));
				assertThat(status(result)).isEqualTo(NO_CONTENT);
            }
        });	
	}
	
	@Test
	public void testGetProfile() {		       
	     running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
            	String email = "phamtrungkha@gmail.com";
            	Result result = callAction(routes.ref.UserManager.getProfile(email));
            	assertThat(contentType(result)).isEqualTo("text/html");
            }
        });	
	}
	
	@Test
	public void testModifyProfile() {		       
	     running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
            	Map<String, String> candidateUpdate = new HashMap<>();
            	candidateUpdate.put("fullName", "Pham Trung Kha");
            	candidateUpdate.put("birthday", "1992-11-11");
            	candidateUpdate.put("gender", "Male");
            	candidateUpdate.put("address", "Go Vap");
            	candidateUpdate.put("phoneNumber", "0979315329");
            	candidateUpdate.put("experience", "3");
            	candidateUpdate.put("language", "java");
            	candidateUpdate.put("position", "Developer");
            	candidateUpdate.put("level", "InternShip");
            	candidateUpdate.put("id", "4");
            	
            	JsonNode jNode = Tools.toJson(candidateUpdate);
            	System.out.println(jNode);
				Result result = callAction(routes.ref.UserManager.modifyProfile(),
						fakeRequest().withJsonBody(jNode).withSession("email", "phamtrungkha@gmail.com"));
            	System.out.println(jNode);
				assertThat(status(result)).isEqualTo(NO_CONTENT);
            }
        });	
	}
}
