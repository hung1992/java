package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import play.db.ebean.Model;

@Entity
@Table(name = "exam_question")
public class OrderQuestion extends Model{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	public long ID;


	@ManyToOne
	@PrimaryKeyJoinColumn(name = "question_id", referencedColumnName = "id")
	public Question question;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "exam_id", referencedColumnName = "id")
	@JsonIgnore
	public Exam exam;

	@NotNull
	public int sequence;

	//Constructor
	public OrderQuestion(int sequence, Question q, Exam e){
		this.sequence = sequence;
		this.question = q;
		this.exam = e;
	}

	@Override
	public void save(){
		super.save();
	}

	public static void create(OrderQuestion orderQuestion){
		orderQuestion.save();
	}

	public static Finder<Long, OrderQuestion> find = new Finder<Long, OrderQuestion>(Long.class, OrderQuestion.class);

	public static List<OrderQuestion> findAll(){
		return find.all();
	}

	/**
	 * Find OrderQuestion by Id Exam
	 * @param id
	 * @return list OrderQuestion with order by sequence.
	 */
	public static List<OrderQuestion> findByIdExam(Long id){
		return find.where().eq("exam_id", id).orderBy("sequence").findList();
	}
}
