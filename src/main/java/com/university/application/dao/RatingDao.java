package com.university.application.dao;

import com.university.application.models.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RatingDao {
    protected ConnectionManager connectionManager;

    private static RatingDao instance = null;

    protected RatingDao() {
        connectionManager = new ConnectionManager();
    }

    public static RatingDao getInstance() {
        if (instance == null) {
            instance = new RatingDao();
        }
        return instance;
    }

    // Save a Rating instance into the database
    public Rating create(Rating rating) throws SQLException {
        String insertRating = "INSERT INTO Rating(reviewerID, applicantID, rating) VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertRating);
            insertStmt.setInt(1, rating.getReviewerID());
            insertStmt.setInt(2, rating.getApplicantID());
            insertStmt.setInt(3, rating.getRating());
            
            int result = insertStmt.executeUpdate();
            System.out.println("Inserted Rating with result: " + result);
            
            return rating;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (insertStmt != null) {
                insertStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    // Fetch all Ratings for a given applicantID
    public List<Rating> getRatingsByApplicant(int applicantID) throws SQLException {
        List<Rating> ratings = new ArrayList<>();
        String selectRatings = "SELECT reviewerID, applicantID, rating FROM Rating WHERE applicantID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRatings);
            selectStmt.setInt(1, applicantID);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int reviewerID = results.getInt("reviewerID");
                int ratingVal = results.getInt("rating");
                Rating rating = new Rating(reviewerID, applicantID, ratingVal);
                ratings.add(rating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (results != null) {
                results.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return ratings;
    }

    // Update an existing Rating in the database.
    public Rating updateRating(Rating rating) throws SQLException {
        String updateRating = "UPDATE Rating SET rating=? WHERE reviewerID=? AND applicantID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateRating);
            updateStmt.setInt(1, rating.getRating());
            updateStmt.setInt(2, rating.getReviewerID());
            updateStmt.setInt(3, rating.getApplicantID());

            updateStmt.executeUpdate();
            
            // Return the updated Rating
            return rating;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(updateStmt != null) {
                updateStmt.close();
            }
            if(connection != null) {
                connection.close();
            }
        }
    }

    // Delete a Rating from the database.
    public boolean deleteRating(int reviewerID, int applicantID) throws SQLException {
        String deleteRating = "DELETE FROM Rating WHERE reviewerID=? AND applicantID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteRating);
            deleteStmt.setInt(1, reviewerID);
            deleteStmt.setInt(2, applicantID);

            int affectedRows = deleteStmt.executeUpdate();
            
            // Return true if the deletion was successful
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(deleteStmt != null) {
                deleteStmt.close();
            }
            if(connection != null) {
                connection.close();
            }
        }
    }

}
