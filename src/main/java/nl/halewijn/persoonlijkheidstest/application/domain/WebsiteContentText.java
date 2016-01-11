package nl.halewijn.persoonlijkheidstest.application.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "WebsiteContentText")
public class WebsiteContentText {
	
	@Id
	@GeneratedValue
	private int contentId;
	
	private String contentTitle;

	@NotNull
	@Column(name="contentText", columnDefinition="varchar(25000)")
	private String contentText;
	
	private String contentDescription;

    public WebsiteContentText() {
        /*
         * ThymeLeaf requires us to have default constructors, further explanation can be found here:
         * http://javarevisited.blogspot.in/2014/01/why-default-or-no-argument-constructor-java-class.html
         */
    }

    public WebsiteContentText(int contentId) {
        this.contentId = contentId;
    }
	
	public int getContentId() {
		return contentId;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getContentDescription() {
		return contentDescription;
	}

	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}
}