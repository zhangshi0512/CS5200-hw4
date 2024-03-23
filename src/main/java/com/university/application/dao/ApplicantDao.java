package com.university.application.dao;

import com.university.application.models.Applicant;
import com.university.application.models.Applicant.Program;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicantDao {
    protected ConnectionManager connectionManager;

    private static ApplicantDao instance = null;

    protected ApplicantDao() {
        connectionManager = new ConnectionManager();
    }

    public static ApplicantDao getInstance() {
        if(instance == null) {
            instance = new ApplicantDao();
        }
        return instance;
    }

    // Insert an Applicant into the database
    public Applicant create(Applicant applicant) throws SQLException {
        String insertApplicant = "INSERT INTO Applicant(userID, program, essay) VALUES(?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertApplicant);
            insertStmt.setInt(1, applicant.getUserID());
            insertStmt.setString(2, applicant.getProgram().name()); // Enum to String
            insertStmt.setString(3, applicant.getEssay());
            insertStmt.executeUpdate();
            return applicant;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(insertStmt != null) insertStmt.close();
            if(connection != null) connection.close();
        }
    }

    // Get an Applicant by their userID
    public Applicant getApplicantByUserID(int userID) throws SQLException {
        String selectApplicant = "SELECT userID, program, essay FROM Applicant WHERE userID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectApplicant);
            selectStmt.setInt(1, userID);
            results = selectStmt.executeQuery();
            if(results.next()) {
                int resultUserID = results.getInt("userID");
                Program program = Program.valueOf(results.getString("program").toUpperCase()); // String to Enum
                String essay = results.getString("essay");
                Applicant applicant = new Applicant(resultUserID, program, essay);
                return applicant;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(results != null) results.close();
            if(selectStmt != null) selectStmt.close();
            if(connection != null) connection.close();
        }
        return null;
    }

    // Get Applicants by their program
    public List<Applicant> getApplicantsByProgram(Program program) throws SQLException {
        List<Applicant> applicants = new ArrayList<>();
        String selectApplicants = "SELECT userID, program, essay FROM Applicant WHERE program=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectApplicants);
            selectStmt.setString(1, program.name()); // Enum to String
            results = selectStmt.executeQuery();
            while(results.next()) {
                int userID = results.getInt("userID");
                String essay = results.getString("essay");
                Applicant applicant = new Applicant(userID, program, essay);
                applicants.add(applicant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(results != null) results.close();
            if(selectStmt != null) selectStmt.close();
            if(connection != null) connection.close();
        }
        return applicants;
    }

    // Delete an Applicant from the database
    public Applicant delete(Applicant applicant) throws SQLException {
        String deleteApplicant = "DELETE FROM Applicant WHERE userID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteApplicant);
            deleteStmt.setInt(1, applicant.getUserID());
            deleteStmt.executeUpdate();
            return null; // Return null to indicate successful deletion
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(deleteStmt != null) deleteStmt.close();
            if(connection != null) connection.close();
        }
    }

    // Update an existing Applicant in the database.
    public Applicant updateApplicant(Applicant applicant) throws SQLException {
        String updateApplicant = "UPDATE Applicant SET program=?, essay=? WHERE userID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateApplicant);
            updateStmt.setString(1, applicant.getProgram().name());
            updateStmt.setString(2, applicant.getEssay());
            updateStmt.setInt(3, applicant.getUserID());

            updateStmt.executeUpdate();

            // Return the updated Applicant
            return applicant;
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

}
