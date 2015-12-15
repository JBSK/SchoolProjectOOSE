package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ScoreConstant")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ScoreConstant {

    @Id
    private String answer;

    @NotNull
    private double score;

    public ScoreConstant(char answer, double score) {
        this.answer = String.valueOf(answer);
        this.score = score;
    }

    public ScoreConstant() {
        /*
         * ThymeLeaf requires us to have default constructors, further explanation can be found here:
         * http://javarevisited.blogspot.in/2014/01/why-default-or-no-argument-constructor-java-class.html
         */
    }

    public char getAnswer() {
        return answer.charAt(0);
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
