/**
* Copyright by Technologic Arts
 * Project: e-Test
 * Package: models
 * Author: ta-08
 */
package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.Model;

/**
 * This is class Quiz.java
 */
@Entity
public class Quiz extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 0L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	public long ID;

	@Column(columnDefinition = "TEXT")
	public String content;

	public int sequence; // specify the A B C or D order


	@OneToOne(cascade = CascadeType.REMOVE)
	@JsonIgnore
	public Question question;

	public Quiz(String content, int sequence){
		this.content = content;
		this.sequence = sequence;
	}

	@Override
	public void save(){
		super.save();
	}
	/**
	 * Create the quiz
	 * @param quiz
	 */
	public static void create(Quiz quiz){
		quiz.save();
	}
	/**
	 * Delete the quiz
	 * @param quiz
	 */
	public static void deleted(Quiz quiz){
		quiz.delete();
	}

	public static Finder<Long,Quiz> find = new Finder<Long,Quiz>(
	        Long.class, Quiz.class
	);

	public static List<Quiz> all(){
		return find.all();
	}

	public static Quiz findById(long id){
			return find.byId(id);
	}

	public static List<Quiz> findByIdQuestion(long id){
		return find.where().eq("question_id", id).findList();
	}

    @Override
    public String toString() {
        return ("Quizz : "+content);
    }
}
