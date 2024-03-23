package com.university.application.models;

import java.util.Date;

public class Degree {
    private int degreeID;
    private int applicantID;
    private String degreeType; 
    private String grantingInstitution;
    private String subject;
    private Date dateGranted;

    public Degree(int degreeID, int applicantID, String degreeType, String grantingInstitution, String subject, Date dateGranted) {
        this.degreeID = degreeID;
        this.applicantID = applicantID;
        this.degreeType = degreeType;
        this.grantingInstitution = grantingInstitution;
        this.subject = subject;
        this.dateGranted = dateGranted;
    }

	public int getDegreeID() {
		return degreeID;
	}

	public void setDegreeID(int degreeID) {
		this.degreeID = degreeID;
	}

	public int getApplicantID() {
		return applicantID;
	}

	public void setApplicantID(int applicantID) {
		this.applicantID = applicantID;
	}

	public String getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(String degreeType) {
		this.degreeType = degreeType;
	}

	public String getGrantingInstitution() {
		return grantingInstitution;
	}

	public void setGrantingInstitution(String grantingInstitution) {
		this.grantingInstitution = grantingInstitution;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getDateGranted() {
		return dateGranted;
	}

	public void setDateGranted(Date dateGranted) {
		this.dateGranted = dateGranted;
	}
    
    
}
