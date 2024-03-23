package com.university.application.models;

public class Rating {
    private int reviewerID;
    private int applicantID;
    private int rating;

    public Rating(int reviewerID, int applicantID, int rating) {
        this.reviewerID = reviewerID;
        this.applicantID = applicantID;
        this.rating = rating;
    }

	public int getReviewerID() {
		return reviewerID;
	}

	public void setReviewerID(int reviewerID) {
		this.reviewerID = reviewerID;
	}

	public int getApplicantID() {
		return applicantID;
	}

	public void setApplicantID(int applicantID) {
		this.applicantID = applicantID;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
    
    
}
