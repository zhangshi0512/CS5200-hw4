package com.university.application.dao;

import com.university.application.models.Reviewer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewerDao {
    // Connection manager to handle database connections
    protected ConnectionManager connectionManager;

    // Singleton pattern: ensures only one instance of ReviewerDao is instantiated.
    private static ReviewerDao instance = null;

    protected ReviewerDao() {
        connectionManager = new ConnectionManager();
    }

    public static ReviewerDao getInstance() {
        if (instance == null) {
            instance = new ReviewerDao();
        }
        return instance;
    }

    // Save a Reviewer instance into the database
    public Reviewer create(Reviewer reviewer) throws SQLException {
        String insertReviewer = "INSERT INTO Reviewer(userID, program) VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertReviewer);
            // Set the parameters of the prepared statement
            insertStmt.setInt(1, reviewer.getUserID());
            insertStmt.setString(2, reviewer.getProgram());
            // Execute the statement
            insertStmt.executeUpdate();
            // Return the Reviewer object
            return reviewer;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Clean up the database resources
            if (insertStmt != null) {
                insertStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    // Fetch a single Reviewer from the database by userID
    public Reviewer getReviewerByUserID(int userID) throws SQLException {
        String selectReviewer = "SELECT userID, program FROM Reviewer WHERE userID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectReviewer);
            // Set the parameter of the prepared statement
            selectStmt.setInt(1, userID);
            // Execute the query
            results = selectStmt.executeQuery();
            // Extract the results
            if (results.next()) {
                int resultUserID = results.getInt("userID");
                String program = results.getString("program");
                Reviewer reviewer = new Reviewer(resultUserID, program);
                return reviewer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Clean up the database resources
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
        return null; // If no Reviewer is found, return null
    }

    // Update an existing Reviewer in the database.
    public Reviewer updateReviewer(Reviewer reviewer) throws SQLException {
        String updateReviewer = "UPDATE Reviewer SET program=? WHERE userID=?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(updateReviewer)) {
            
            updateStmt.setString(1, reviewer.getProgram());
            updateStmt.setInt(2, reviewer.getUserID());
            
            updateStmt.executeUpdate();
            
            return reviewer;
        }
    }

    // Delete a Reviewer from the database.
    public boolean deleteReviewer(int userID) throws SQLException {
        String deleteReviewer = "DELETE FROM Reviewer WHERE userID=?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement deleteStmt = connection.prepareStatement(deleteReviewer)) {
            
            deleteStmt.setInt(1, userID);
            
            int affectedRows = deleteStmt.executeUpdate();
            
            return affectedRows > 0;
        }
    }

}
