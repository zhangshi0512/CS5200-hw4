package com.university.application.dao;

import com.university.application.models.Degree;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DegreeDao {
    protected ConnectionManager connectionManager;

    private static DegreeDao instance = null;

    protected DegreeDao() {
        connectionManager = new ConnectionManager();
    }

    public static DegreeDao getInstance() {
        if (instance == null) {
            instance = new DegreeDao();
        }
        return instance;
    }

    // Save a Degree instance into the database
    public Degree create(Degree degree) throws SQLException {
        String insertDegree = "INSERT INTO Degree(applicantID, degreeType, grantingInstitution, subject, dateGranted) VALUES(?,?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertDegree);
            insertStmt.setInt(1, degree.getApplicantID());
            insertStmt.setString(2, degree.getDegreeType());
            insertStmt.setString(3, degree.getGrantingInstitution());
            insertStmt.setString(4, degree.getSubject());
            insertStmt.setDate(5, new java.sql.Date(degree.getDateGranted().getTime()));
            insertStmt.executeUpdate();
            return degree;
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

    // Fetch all Degrees for a given applicantID
    public List<Degree> getDegreesByApplicant(int applicantID) throws SQLException {
        List<Degree> degrees = new ArrayList<>();
        String selectDegrees = "SELECT degreeID, applicantID, degreeType, grantingInstitution, subject, dateGranted FROM Degree WHERE applicantID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectDegrees);
            selectStmt.setInt(1, applicantID);
            results = selectStmt.executeQuery();
            while (results.next()) {
                int degreeID = results.getInt("degreeID");
                String degreeType = results.getString("degreeType");
                String grantingInstitution = results.getString("grantingInstitution");
                String subject = results.getString("subject");
                java.sql.Date dateGranted = results.getDate("dateGranted");
                Degree degree = new Degree(degreeID, applicantID, degreeType, grantingInstitution, subject, new java.util.Date(dateGranted.getTime()));
                degrees.add(degree);
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
        return degrees;
    }

    // Update an existing Degree in the database.
    public Degree updateDegree(Degree degree) throws SQLException {
        String updateDegree = "UPDATE Degree SET degreeType=?, grantingInstitution=?, subject=?, dateGranted=? WHERE degreeID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateDegree);
            updateStmt.setString(1, degree.getDegreeType());
            updateStmt.setString(2, degree.getGrantingInstitution());
            updateStmt.setString(3, degree.getSubject());
            updateStmt.setDate(4, new java.sql.Date(degree.getDateGranted().getTime()));
            updateStmt.setInt(5, degree.getDegreeID());
            updateStmt.executeUpdate();
            
            // Return the updated Degree
            return degree;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(updateStmt != null) updateStmt.close();
            if(connection != null) connection.close();
        }
    }

    // Delete a Degree from the database.
    public Degree deleteDegree(int degreeID) throws SQLException {
        String deleteDegree = "DELETE FROM Degree WHERE degreeID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteDegree);
            deleteStmt.setInt(1, degreeID);
            deleteStmt.executeUpdate();
            
            // Return null to indicate the Degree was deleted
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
