package com.university.application.dao;

import com.university.application.models.RecLetter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecLetterDao {
    protected ConnectionManager connectionManager;

    private static RecLetterDao instance = null;

    protected RecLetterDao() {
        connectionManager = new ConnectionManager();
    }

    public static RecLetterDao getInstance() {
        if(instance == null) {
            instance = new RecLetterDao();
        }
        return instance;
    }

    // Create a new recommendation letter entry in the database
    public RecLetter create(RecLetter recLetter) throws SQLException {
        String insertRecLetter = "INSERT INTO RecLetter(authorID, applicantID, dateReceived, body) VALUES(?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertRecLetter);
            // Set the parameters for the INSERT statement
            insertStmt.setInt(1, recLetter.getAuthorID());
            insertStmt.setInt(2, recLetter.getApplicantID());
            insertStmt.setDate(3, new java.sql.Date(recLetter.getDateReceived().getTime()));
            insertStmt.setString(4, recLetter.getBody());
            // Execute the INSERT operation
            insertStmt.executeUpdate();
            return recLetter;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Clean up database resources
            if(insertStmt != null) insertStmt.close();
            if(connection != null) connection.close();
        }
    }

    // Get all recommendation letters for a given applicant
    public List<RecLetter> getRecLettersByApplicant(int applicantID) throws SQLException {
        List<RecLetter> recLetters = new ArrayList<>();
        String selectRecLetters = "SELECT authorID, applicantID, dateReceived, body FROM RecLetter WHERE applicantID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectRecLetters);
            selectStmt.setInt(1, applicantID);
            results = selectStmt.executeQuery();
            while(results.next()) {
                int authorID = results.getInt("authorID");
                int resultApplicantID = results.getInt("applicantID");
                java.sql.Date dateReceived = results.getDate("dateReceived");
                String body = results.getString("body");
                RecLetter recLetter = new RecLetter(authorID, resultApplicantID, new java.util.Date(dateReceived.getTime()), body);
                recLetters.add(recLetter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Clean up database resources
            if(results != null) results.close();
            if(selectStmt != null) selectStmt.close();
            if(connection != null) connection.close();
        }
        return recLetters;
    }

    // Update an existing RecLetter in the database.
    public RecLetter updateRecLetter(RecLetter recLetter) throws SQLException {
        String updateRecLetter = "UPDATE RecLetter SET dateReceived=?, body=? WHERE authorID=? AND applicantID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateRecLetter);
            updateStmt.setDate(1, new java.sql.Date(recLetter.getDateReceived().getTime()));
            updateStmt.setString(2, recLetter.getBody());
            updateStmt.setInt(3, recLetter.getAuthorID());
            updateStmt.setInt(4, recLetter.getApplicantID());

            updateStmt.executeUpdate();
            
            // Return the updated RecLetter
            return recLetter;
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

    // Delete a RecLetter from the database.
    public boolean deleteRecLetter(int authorID, int applicantID) throws SQLException {
        String deleteRecLetter = "DELETE FROM RecLetter WHERE authorID=? AND applicantID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteRecLetter);
            deleteStmt.setInt(1, authorID);
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
