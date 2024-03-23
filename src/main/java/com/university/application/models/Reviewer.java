package com.university.application.models;

public class Reviewer {
    private int userID;
    private String program; 

    public Reviewer(int userID, String program) {
        this.userID = userID;
        this.program = program;
    }

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}
    
    
}
