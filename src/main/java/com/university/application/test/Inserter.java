package com.university.application.test;

import com.university.application.dao.*;
import com.university.application.models.*;
import com.university.application.models.Applicant.Program;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Inserter {

    public static void main(String[] args) throws SQLException {
        // DAO instances
        UserDao userDao = UserDao.getInstance();
        ApplicantDao applicantDao = ApplicantDao.getInstance();
        DegreeDao degreeDao = DegreeDao.getInstance();
        LetterWriterDao letterWriterDao = LetterWriterDao.getInstance();
        RatingDao ratingDao = RatingDao.getInstance();
        RecLetterDao recLetterDao = RecLetterDao.getInstance();
        ReviewerDao reviewerDao = ReviewerDao.getInstance();
        

        // Ensuring unique usernames by appending a timestamp
        long timestamp = System.currentTimeMillis();

        User user1 = new User("johnDoe" + timestamp, "John", "Doe", "john.doe" + timestamp + "@example.com");
        user1 = userDao.create(user1);
        User user2 = new User("janeDoe" + timestamp, "Jane", "Doe", "jane.doe" + timestamp + "@example.com");
        user2 = userDao.create(user2);

        // Additional User creation with unique usernames
        for (int i = 3; i <= 5; i++) {
            User user = new User("user" + i + timestamp, "User" + i, "Surname" + i, "user" + i + timestamp + "@example.com");
            userDao.create(user);
        }

        // Applicant creation
        Applicant applicant1 = new Applicant(user1.getUserID(), Program.MASTERS, "This is my application essay.");
        applicant1 = applicantDao.create(applicant1);
        
        // Additional Applicant creation
        for (int i = 2; i <= 4; i++) {
            Applicant applicant = new Applicant(user1.getUserID() + i, Program.values()[i % Program.values().length], "Application essay " + i);
            applicantDao.create(applicant);
        }

        // Degree creation
        Degree degree1 = new Degree(0, applicant1.getUserID(), "bachelors", "University of Nowhere", "Computer Science", new Date());
        degree1 = degreeDao.create(degree1);
        
        // Additional Degree creation
        String[] validDegreeTypes = {"bachelors", "masters", "phd"};
        for (int i = 2; i <= 4; i++) {
            // Use the validDegreeTypes array to cycle through valid enum values
            String degreeType = validDegreeTypes[i % validDegreeTypes.length];
            Degree degree = new Degree(0, applicant1.getUserID() + i, degreeType, "University" + i, "Field" + i, new Date());
            degreeDao.create(degree);
        }

        // LetterWriter creation with user2, assuming user2 was successfully created.
        LetterWriter letterWriter1 = new LetterWriter(user2.getUserID(), "University of Somewhere");
        letterWriter1 = letterWriterDao.create(letterWriter1);

        // Revised User and LetterWriter creation
        for (int i = 3; i <= 5; i++) {
            // Always create a new User instance to ensure uniqueness
            String uniqueUsername = "uniqueUser" + timestamp + i; // Using timestamp to ensure global uniqueness
            User newUser = new User(uniqueUsername, "FirstName" + i, "LastName" + i, "email" + i + "@example.com");
            newUser = userDao.create(newUser);

            // Create a LetterWriter instance for the new User
            LetterWriter newLetterWriter = new LetterWriter(newUser.getUserID(), "University" + i);
            newLetterWriter = letterWriterDao.create(newLetterWriter);

            System.out.println("Created LetterWriter for User " + newUser.getUsername() + " associated with " + newLetterWriter.getUniversity());
        }
        
        // List to hold created Reviewers' userIDs for later use in Rating creation
        List<Integer> reviewerUserIDs = new ArrayList<>();

        // Reviewer creation for user2 with a valid enum value
        Reviewer reviewer1 = new Reviewer(user2.getUserID(), "masters"); // Assuming 'masters' is a valid value for the 'program' enum
        reviewer1 = reviewerDao.create(reviewer1);
        reviewerUserIDs.add(reviewer1.getUserID());

        // Additional Reviewer creation with valid enum values
        for (int i = 2; i <= 4; i++) {
            // Append the timestamp to the username to ensure uniqueness
            String uniqueUsername = "user" + i + "_" + timestamp;
            User newUser = new User(uniqueUsername, "User" + i, "Surname" + i, "user" + i + "@example.com");
            newUser = userDao.create(newUser);  // Create new User for each new Reviewer

            // Alternate between 'masters' and 'phd' for the program value
            String programValue = (i % 2 == 0) ? "masters" : "phd";
            Reviewer newReviewer = new Reviewer(newUser.getUserID(), programValue);
            newReviewer = reviewerDao.create(newReviewer);
            reviewerUserIDs.add(newReviewer.getUserID());  // Store new Reviewer's userID
        }


        // Rating creation for the first reviewer and applicant
        Rating rating1 = new Rating(reviewerUserIDs.get(0), applicant1.getUserID(), 5);  
        rating1 = ratingDao.create(rating1);

        // Additional Rating creation, using stored reviewerUserIDs
        for (int i = 1; i < reviewerUserIDs.size(); i++) {  
            int reviewerUserID = reviewerUserIDs.get(i);

            Rating newRating = new Rating(reviewerUserID, applicant1.getUserID(), 5 + i);  // Use subsequent Reviewer's userID
            ratingDao.create(newRating);
        }


        // RecLetter creation
        RecLetter recLetter1 = new RecLetter(letterWriter1.getUserID(), applicant1.getUserID(), new Date(), "Excellent candidate.");
        recLetter1 = recLetterDao.create(recLetter1);
        
        // Additional RecLetter creation
        for (int i = 2; i <= 4; i++) {
            RecLetter recLetter = new RecLetter(letterWriter1.getUserID(), applicant1.getUserID() + i, new Date(), "Excellent candidate " + i);
            recLetterDao.create(recLetter);
        }


	
	    // Fetch Users by ID
	    User fetchedUser1 = userDao.getUserByUserID(user1.getUserID());
	    System.out.println("Fetched User 1: " + fetchedUser1.getUsername());
	
	    User fetchedUser2 = userDao.getUserByUserID(user2.getUserID());
	    System.out.println("Fetched User 2: " + fetchedUser2.getUsername());
	
	    // Fetch Applicant by ID
	    Applicant fetchedApplicant = applicantDao.getApplicantByUserID(applicant1.getUserID());
	    System.out.println("Fetched Applicant: " + fetchedApplicant.getEssay());
	
	    // Fetch Degree by Applicant
	    List<Degree> degreesForApplicant = degreeDao.getDegreesByApplicant(applicant1.getUserID());
	    for (Degree degree : degreesForApplicant) {
	        System.out.println("Degree for Applicant: " + degree.getDegreeType() + " in " + degree.getSubject());
	    }
	
	    // Fetch LetterWriter by ID
	    LetterWriter fetchedLetterWriter = letterWriterDao.getLetterWriterByUserID(letterWriter1.getUserID());
	    System.out.println("Fetched LetterWriter: " + fetchedLetterWriter.getUniversity());
	
	    // Fetch Ratings for Applicant
	    List<Rating> ratingsForApplicant = ratingDao.getRatingsByApplicant(applicant1.getUserID());
	    for (Rating rating : ratingsForApplicant) {
	        System.out.println("Rating for Applicant: " + rating.getRating());
	    }
	
	    // Fetch Recommendation Letters for Applicant
	    List<RecLetter> recLettersForApplicant = recLetterDao.getRecLettersByApplicant(applicant1.getUserID());
	    for (RecLetter recLetter : recLettersForApplicant) {
	        System.out.println("Recommendation Letter: " + recLetter.getBody());
	    }
	
	    // Fetch Reviewer by ID
	    Reviewer fetchedReviewer = reviewerDao.getReviewerByUserID(reviewer1.getUserID());
	    System.out.println("Fetched Reviewer: " + fetchedReviewer.getProgram());
	
	    // Updating a User
	    user1.setEmail("new.email@example.com");
	    userDao.update(user1); 
	
	    // Deleting a User
	    System.out.println("Deleting User1...");
	    userDao.delete(user1); 
        
        // Delete another User
        System.out.println("Deleting User2...");
        userDao.delete(user2);
        
        // Update Applicant
        System.out.println("Updating Applicant's essay...");
        applicant1.setEssay("This is my updated application essay.");
        applicantDao.updateApplicant(applicant1);
        
        // Delete Applicant
        System.out.println("Deleting Applicant...");
        applicantDao.delete(applicant1);

        // Update Degree
        System.out.println("Updating Degree...");
        degree1.setSubject("Updated Computer Science");
        degreeDao.updateDegree(degree1);
        
        // Delete Degree
        System.out.println("Deleting Degree...");
        degreeDao.deleteDegree(degree1.getDegreeID());

        // Update LetterWriter
        System.out.println("Updating LetterWriter's university...");
        letterWriter1.setUniversity("Updated University of Somewhere");
        letterWriterDao.updateLetterWriter(letterWriter1);
        
        // Delete LetterWriter
        System.out.println("Deleting LetterWriter...");
        letterWriterDao.deleteLetterWriter(letterWriter1.getUserID());

        // Update Rating
        System.out.println("Updating Rating...");
        rating1.setRating(10);  // Assuming the scale is 1-10
        ratingDao.updateRating(rating1);
        
        // Delete Rating
        System.out.println("Deleting Rating...");
        ratingDao.deleteRating(rating1.getReviewerID(), rating1.getApplicantID());

        // Update RecLetter
        System.out.println("Updating Recommendation Letter...");
        recLetter1.setBody("This is an updated excellent recommendation.");
        recLetterDao.updateRecLetter(recLetter1);
        
        // Delete RecLetter
        System.out.println("Deleting Recommendation Letter...");
        recLetterDao.deleteRecLetter(recLetter1.getAuthorID(), recLetter1.getApplicantID());

        // Update Reviewer
        System.out.println("Updating Reviewer's program...");
        reviewer1.setProgram("Updated Computer Science");
        reviewerDao.updateReviewer(reviewer1);
        
        // Delete Reviewer
        System.out.println("Deleting Reviewer...");
        reviewerDao.deleteReviewer(reviewer1.getUserID());
	
    }
}

