package com.university.application.models;

public class Applicant {
    private int userID;
    private Program program; 
    private String essay;

    // Enum to represent the program types
    public enum Program {
        MASTERS, PHD
    }

    public Applicant(int userID, Program program, String essay) {
        this.userID = userID;
        this.program = program;
        this.essay = essay;
    }

    // Getters and setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public String getEssay() {
        return essay;
    }

    public void setEssay(String essay) {
        this.essay = essay;
    }
}
