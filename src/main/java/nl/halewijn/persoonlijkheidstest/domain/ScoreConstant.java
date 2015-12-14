package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ScoreConstant")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ScoreConstant {

    @Id
    private char answer;

    @NotNull
    private double score;

    public ScoreConstant(char answer, double score) {
        this.answer = answer;
        this.score = score;
    }

    public ScoreConstant() {

    }

    public char getAnswer() {
        return answer;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
