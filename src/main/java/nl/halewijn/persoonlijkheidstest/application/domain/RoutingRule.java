package nl.halewijn.persoonlijkheidstest.application.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "RoutingRule")
public class RoutingRule {

	@Id
	@GeneratedValue
	private int routingRuleId;

    @NotNull
    private String description;

	public RoutingRule(String description) {
        this.description = description;
	}

	public RoutingRule() {
        /*
         * ThymeLeaf requires us to have default constructors, further explanation can be found here:
         * http://javarevisited.blogspot.in/2014/01/why-default-or-no-argument-constructor-java-class.html
         */
	}

	public int getRoutingRuleId() {
		return routingRuleId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}