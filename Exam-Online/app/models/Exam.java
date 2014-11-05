/**
 * Copyright by Technologic Arts
 * Project: e-Test
 * Package: models
 * Author: ta-08
 */
package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is class Exam.java
 */
@Entity
public class Exam extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	public long ID;

	@Required
	@Column(unique = true)
	public String name;


	@NotNull
	public int duration; //minutes

	@NotNull
	public int numberOfQuestion;

	@OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
	@OrderBy("sequence")
	public List<OrderQuestion> questions;

	@NotNull
	public String language;

	@OneToMany
	public Candidate candidate;

	@Override
	public void save(){
		super.save();
	}

	//constructor
	public Exam(String name, int duration, String language, int numberOfQuestion){
		this.name = name;
		this.duration = duration;
		this.language = language;
		this.numberOfQuestion = numberOfQuestion;
		this.questions = new ArrayList<OrderQuestion>();
	}

	public static void create(Exam exam){
		exam.save();
	}

	public static Finder<Long, Exam> find = new Finder<Long, Exam>(
			Long.class, Exam.class
		);


    //find all questions of an exam
    public  List<Question>getQuestionList(Long examID){
       return null;
    }

    public static List<OrderQuestion> getQuestionByExamID(long examID){
        Exam exam = getExamById(examID);
        return exam.questions;
    }

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

	public static Exam fromJson(final JsonNode json) throws JsonProcessingException{
        return mapper.treeToValue(json, Exam.class);
	}

	/**
	 * Description: Get all exam from DB
	 * @return
	 * List<Exam>
	 */
	public static List<Exam> getAllExam() {
		return find.all();

	}

	/**
	 * Description: select exam by Id
	 * @param id
	 * @return
	 * Exam
	 */
	public static Exam getExamById(long id) {
		return find.byId(id);
	}

	/**
	 * Description: Query exam by language
	 * @param language
	 * @return
	 * List<Exam>

	 */
	public static List<Exam> getExams(String language) {
		List<Exam> examList = Exam.getAllExam();
		List<Exam> lists = new ArrayList<Exam>();
		for (int i = 0; i < examList.size(); i++) {
			Exam exam = examList.get(i);

			if (exam.language.equals(language))

				lists.add(exam);
		}
		return lists;
	}

	/**
	 * Description: Get exam of a candidate.
	 * @param candidate
	 * @return
	 * Exam

	 */
	public static Exam getExamOfCandidate(Candidate candidate) {
		return candidate.exam;
	}

}
