/**
 * Copyright by Technologic Arts
 * Project: e-Test
 * Package: models
 * Author: ta-08
 */
package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.db.ebean.Model;

/**
 * This is class Question.java
 */
@Entity
public class Question extends Model {
	/**
	 *
	 */
	private static final long serialVersionUID = 0L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	public long ID;

	@NotNull
	public String language;

	@NotNull
	public int type;

	@NotNull
	public String level;

	@NotNull
	@Column(columnDefinition = "TEXT")
	public String content;

	@OneToMany(mappedBy = "question")
	@JsonIgnore
	public List<OrderQuestion> exam;
	/**
	 * If type is text question, answerValue is ""
	 * otherwise answerValue is a array of bits and it convert to number.
	 *
	 * Example: A question have 4 quizzes, there are A, B, C and D.
	 * 			if B and D is correct, answerValue is "0101"
	 * 			there are quiz A is incorrect and value of A is "0"
	 * 			B is correct and value of B is "1", C and D is "0" and "1".
	 * 			answerValue of question is "0101", convert it to decimal is "5".
	 */
	@NotNull
	public String answerValue;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Quiz> quizzes;

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

	public static Question fromJson(final JsonNode json)
			throws JsonProcessingException {

		return mapper.treeToValue(json, Question.class);

	}

	/**
	 * Constructor
	 *
	 * If type is text question, answerValue is "" and List<Quiz> is null
	 */
	public Question(int type, String language, String level, String content, String answerValue, List<Quiz> quizzes){
		this.type = type;
		this.language = language;
		this.level = level;
		this.content = content;
		this.answerValue = answerValue;
		this.quizzes = quizzes;
	};
	public Question(){};

	@Override
	public void save(){
		super.save();
	}
	@Override
	public void delete(){
		super.delete();
	}

	/**
	 * Create the question
	 * @param question
	 */
	public static void create(Question question){
		question.save();
	}

	/**
	 * Delete the question
	 * @param question
	 */
	public static void deleted(Question question){
		question.delete();
	}

	/**
	 * Delete the question
	 * @param question
	 */
	public static void delete(Question question){
		question.delete();
	}
	public static Finder<Long,Question> find = new Finder<Long,Question>(
	        Long.class, Question.class
	);

	/**
	 * Description: Get all question from DB
	 * @return list of all question in database
	 * List<Question>

	 */
	public static List<Question> getAllQuestion() {
		return find.all();
	}

	/**
	 * Description: Get all question from DB by ID
	 * @param id
	 * @return list of all question in database
	 * List<Question>
	 */
	public static Question findById(Long id){
		return find.byId(id);
	}

	/**
	 * Description: Get all question from DB by languages
	 * @param languages
	 * @return list of all question in database
	 * List<Question>
	 */
	public static List<Question> getByLanguage(String languages) {
		List<Question> questionList = getAllQuestion();
		List<Question> lists = new ArrayList<Question>();
		for (int i = 0; i < questionList.size(); i++) {
			Question question = questionList.get(i);
			if (question.language.equals(languages))
				lists.add(question);
		}
		return lists;
	}

	/**
	 * Description: Get all question from DB by type
	 * @param type
	 * @return list of all question in database
	 * List<Question>
	 */
	public static List<Question> getByType(int type) {
		List<Question> questionList = getAllQuestion();
		List<Question> lists = new ArrayList<Question>();
		for (int i = 0; i < questionList.size(); i++) {
			Question question = questionList.get(i);
			if (question.type == type)
				lists.add(question);
		}
		return lists;
	}

	/**
	 * Description: Get Question by ID
	 * @param ID
	 * @return
	 * Question

	 */
	public static Question getById(long ID) {
		return find.byId(ID);
	}

}
