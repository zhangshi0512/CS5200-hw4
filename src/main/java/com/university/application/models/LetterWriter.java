package com.university.application.models;

public class LetterWriter {
    private int userID;
    private String university;

    public LetterWriter(int userID, String university) {
        this.userID = userID;
        this.university = university;
    }

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}
    
    
}
