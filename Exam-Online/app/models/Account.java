/**
 * Copyright by Technologic Arts
 * Project: EXAMONLINE
 * Package: models
 * Author: ta-08
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity
public class Account extends Model {
	
	@Id
	public String email;
	
	@Required
	public String password;
	
	@Required
	public int role ;
	
	// active is true after register. It is false after he did the test.
	// It means he can do test one time.
	public boolean active;
	
	public static Finder<String,Account> find = new Finder<String,Account>(
			String.class, Account.class
	); 
	
	/**
	 * This class object mapper, used to serialize into Json.
	 */
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Convert this User into a Json Object.
	 * 
	 * @return JsonNode representation of this User
	 */
	public final JsonNode toJson() {
		return mapper.convertValue(this, JsonNode.class);
	}
	
	public static Candidate fromJson(final JsonNode json) throws JsonProcessingException{
		
        return mapper.treeToValue(json, Candidate.class);
       
	}
	
	/**
	 * Authenticate a User.
	 * 
	 * @param email
	 *            Email.
	 * @param password
	 *            Password.
	 * @return user.
	 */
	public static Account authenticate(String email, String password) {
		return find.where().eq("email", email).eq("password", password)
				.findUnique();
	}
	
	public static Account getById(String email){
		return find.byId(email);
	}

    @Override
    public String toString() {
        return ("ACCOUNT : "+email);
    }
}


