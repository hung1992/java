/**
 * Copyright by Technologic Arts
 * Project: e-Test
 * Package: models
 * Author: ta-08
 */
package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is class Anwser.java
 */
@Entity
public class Answer extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	public long ID;

	public String answerValue;

	@OneToOne
	public Question question;

	@OneToOne
	public Candidate candidate;

    public static List<Answer> candidateAnswers = new ArrayList<>();



    public static List<Answer> getAnswerList(Long candidateID) {
        candidateAnswers = anFinder.where().eq("candidate_id", candidateID).findList();
        return candidateAnswers;
    }

    public static Finder<Long, Answer> anFinder =
            new Finder<Long, Answer>(Long.class, Answer.class);

    @Override
    public String toString() {
        return ("ANSWER : "+answerValue);
    }
}
