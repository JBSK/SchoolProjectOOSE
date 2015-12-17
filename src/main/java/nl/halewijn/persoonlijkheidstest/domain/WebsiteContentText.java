package nl.halewijn.persoonlijkheidstest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WebsiteContentText")
public class WebsiteContentText {
	
	@Id
	@GeneratedValue
	private int contentId;
	
	private String contentTitle;
	
	@Column(name="contentText", columnDefinition="varchar(25000)") 
	private String contentText;
	
	private String contentDescription;
	
	public int getTextId() {
		return this.contentId;
	}
	
	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
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