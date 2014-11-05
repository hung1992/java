package controllers;

import models.Account;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Constant;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This is class Application.java
 */
public class Application extends Controller {

	/**
	 * Description: Check session
	 * @return
	 * Result
	 */
    public static Result ping() {
		String loggedUser = ctx().session().get("user");

		if (loggedUser == null) {
			return unauthorized(Constant.NEED_LOGIN);
		}

		Account account = Account.getById(loggedUser);

		if (account == null) {
			return unauthorized(Constant.NEED_LOGIN);
		}

		return ok(account.toJson());
	}

    /**
     * Description: authenticate and set session
     * @return
     * Result
     */
	public static Result authenticate() {
		JsonNode auth = request().body().asJson();

		// check candidate is existed in DB or not
		Account account = Account.authenticate(auth.findValue("email")
				.asText(), auth.findValue("password").asText());
		if (account == null) {
			return badRequest(Constant.INVALID_USER);
		} else {

			// store session
			session("user", account.email);
			return ok(account.toJson());

		}
	}


    /**
	 * Logout and clean the session.
	 *
	 * @return Ok
	 */
	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return ok(Constant.LOG_OUT_SUCCESS);
	}

}
