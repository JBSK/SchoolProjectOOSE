package nl.halewijn.persoonlijkheidstest.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "RoutingTable")
public class RoutingTable implements Serializable {

	@Id
	@GeneratedValue
	private int routeId;
	
	@OneToOne
    @JoinColumn(name = "questionId")
	private Question question;

	@NotNull
	private char answer;

	@OneToOne
    @JoinColumn(name = "routingRule")
	private RoutingRule routingRule;
	
	private int routingRuleParam;

	public RoutingTable(Question question, char answer, RoutingRule routingRule) {
        this.question = question;
        this.answer = answer;
        this.routingRule = routingRule;
	}

	public RoutingTable() {
        /*
         * ThymeLeaf requires us to have default constructors, further explanation can be found here:
         * http://javarevisited.blogspot.in/2014/01/why-default-or-no-argument-constructor-java-class.html
         */
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public char getAnswer() {
		return answer;
	}

	public void setAnswer(char answer) {
		this.answer = answer;
	}

	public RoutingRule getRoutingRule() {
		return routingRule;
	}

	public void setRoutingRule(RoutingRule routingRule) {
		this.routingRule = routingRule;
	}

	public int getRoutingRuleParam() {
		return routingRuleParam;
	}

	public void setRoutingRuleParam(int routingRuleParam) {
		this.routingRuleParam = routingRuleParam;
	}
	
}