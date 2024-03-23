package com.university.application.dao;

import com.university.application.models.LetterWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LetterWriterDao {
    protected ConnectionManager connectionManager;

    private static LetterWriterDao instance = null;

    protected LetterWriterDao() {
        connectionManager = new ConnectionManager();
    }

    public static LetterWriterDao getInstance() {
        if(instance == null) {
            instance = new LetterWriterDao();
        }
        return instance;
    }

    // Create a new letter writer entry in the database
    public LetterWriter create(LetterWriter letterWriter) throws SQLException {
        String insertLetterWriter = "INSERT INTO LetterWriter(userID, university) VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertLetterWriter);
            // Set the parameters for the INSERT statement
            insertStmt.setInt(1, letterWriter.getUserID());
            insertStmt.setString(2, letterWriter.getUniversity());
            // Execute the INSERT operation
            insertStmt.executeUpdate();
            return letterWriter;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Clean up database resources
            if(insertStmt != null) insertStmt.close();
            if(connection != null) connection.close();
        }
    }

    // Get a letter writer by their userID
    public LetterWriter getLetterWriterByUserID(int userID) throws SQLException {
        String selectLetterWriter = "SELECT userID, university FROM LetterWriter WHERE userID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectLetterWriter);
            selectStmt.setInt(1, userID);
            results = selectStmt.executeQuery();
            if(results.next()) {
                int resultUserID = results.getInt("userID");
                String university = results.getString("university");
                LetterWriter letterWriter = new LetterWriter(resultUserID, university);
                return letterWriter;
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
        return null; // If no LetterWriter is found, return null
    }

    // Update an existing LetterWriter in the database.
    public LetterWriter updateLetterWriter(LetterWriter letterWriter) throws SQLException {
        String updateLetterWriter = "UPDATE LetterWriter SET university=? WHERE userID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateLetterWriter);
            updateStmt.setString(1, letterWriter.getUniversity());
            updateStmt.setInt(2, letterWriter.getUserID());

            updateStmt.executeUpdate();
            
            // Return the updated LetterWriter
            return letterWriter;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(updateStmt != null) updateStmt.close();
            if(connection != null) connection.close();
        }
    }

    // Delete a LetterWriter from the database.
    public LetterWriter deleteLetterWriter(int userID) throws SQLException {
        String deleteLetterWriter = "DELETE FROM LetterWriter WHERE userID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteLetterWriter);
            deleteStmt.setInt(1, userID);

            deleteStmt.executeUpdate();
            
            // Return null to indicate the LetterWriter was deleted
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(deleteStmt != null) deleteStmt.close();
            if(connection != null) connection.close();
        }
    }

}
