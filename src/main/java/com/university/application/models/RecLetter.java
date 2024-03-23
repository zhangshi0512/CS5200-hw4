package com.university.application.models;

import java.util.Date;

public class RecLetter {
    private int authorID;
    private int applicantID;
    private Date dateReceived;
    private String body;

    public RecLetter(int authorID, int applicantID, Date dateReceived, String body) {
        this.authorID = authorID;
        this.applicantID = applicantID;
        this.dateReceived = dateReceived;
        this.body = body;
    }

	public int getAuthorID() {
		return authorID;
	}

	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}

	public int getApplicantID() {
		return applicantID;
	}

	public void setApplicantID(int applicantID) {
		this.applicantID = applicantID;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
    
    
}
