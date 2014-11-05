package models;



import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * Created by hung on 08/08/2014.
 */

@Entity
public class Score extends Model{
    @Id
    public String userEmail;
    public int userScore;

    //constructor
    public Score(String email, int score){
        this.userEmail = email;
        this.userScore = score;
    }

    public static Finder<String,Score> find = new Model.Finder<String,Score>(
            String.class, Score.class
    );

    public static int getScoreByEmail(String anEmail){
        List<Score>lists = find.all();
        for(int i=0; i<lists.size(); i++){
            Score score = lists.get(i);
            String email = score.userEmail;
            if(anEmail.equals(email))
                return score.userScore;
        }
        return 1000;
    }

    public static Score findScore(String anEmail){
        List<Score>lists = find.all();
        for(int i=0; i<lists.size(); i++){
            Score score = lists.get(i);
            if(anEmail.equals(score.userEmail)){
                return score;
            }
        }
        return null;
    }
}
