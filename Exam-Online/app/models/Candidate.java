/**
 * Copyright by Technologic Arts
 * Project: e-Test
 * Package: models
 * Author: ta-08
 */
package models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is class Candidate.java
 */
@Entity
public class Candidate extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	public long ID;

	@Required
	public String fullName;

	@Required
	public Date birthday;

	@Required
	public String gender;

	@Required
	public String address;

	@Required
	public String phoneNumber;

	@Required
	public int experience;

	@Required
	public String language;

	@NotNull
	public String position;


	@Required
	public String level;


	@OneToOne
	public Exam exam;

	@OneToMany
	public static List<Answer> answers = new ArrayList<Answer>();

	@OneToOne
	public Account account;


	/**
	 * Description : return list of user answer 
	 */
	public static  List<Answer> getAnswerList(Long candidateID) {
        answers = answerFinder.where().eq("candidate_id",candidateID).findList();
		return answers;
	}


	/**
	 * 
	 * @param answer
	 * @return true if add an answer to answer list successfully
	 */
	public static boolean addToListAnswer(Answer answer) {
		return answers.add(answer);
	}


	public static Finder<Long, Candidate> find =
		new Finder<Long, Candidate>(
				Long.class, Candidate.class
		);

    public static Finder<Long, Answer> answerFinder =
            new Finder<Long, Answer>(Long.class, Answer.class);



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

	public static Candidate fromJson(final JsonNode json)
			throws JsonProcessingException {

		return mapper.treeToValue(json, Candidate.class);

	}

	/**
	 * {@inheritDoc}
	 */

	/**
	 * Description: Get all candidate from DB 
	 * @return
	 * List<Candidate>

	 */
	public static List<Candidate> getAllCandidate() {
		return find.all();
	}

	/**
	 * Description : override toString() method
	 * @return A String that represents a candidate instance

	 */
	@Override
	public String toString() {
		return "Candidate [ID=" + ID + ", fullName=" + fullName + ", birthday="
			+ birthday + ", gender=" + gender + ", address=" + address
			+ ", phoneNumber=" + phoneNumber + ", experience=" + experience
			+ ", language=" + language + ", position=" + position + ", level="
			+ level + ", exam=" + exam + ", account=" + account + "]";
	}

	/**
	 * Description: Get Candidate by ID 
	 * @param username
	 * @return
	 * Candidate
	
	 */

	public static Candidate getById(long ID) {
		return find.byId(ID);
	}
	
	/**
	 * Description: Get Candidate by email
	 * @param email
	 * @return
	 * Candidate
	 */
	
	public static Candidate getByEmail(String email){
		List<Candidate> candidateList = getAllCandidate();
		
		for (int i = 0; i < candidateList.size(); i++){
			if (candidateList.get(i).account.email.equals(email)){
				return candidateList.get(i);
			}
		}
		return null;
	}

	/**
	 * Description: Get list of candidate by position 
	 * @return
	 * List<Candidate>

	 */
	public static List<Candidate> getByPosition(String position) {
		List<Candidate> candidateList = getAllCandidate();
		List<Candidate> lists = new ArrayList<Candidate>();
		for (int i = 0; i < candidateList.size(); i++) {
			Candidate candidate = candidateList.get(i);
			if (candidate.position.equals(position))
				lists.add(candidate);
		}
		return lists;
	}



}
