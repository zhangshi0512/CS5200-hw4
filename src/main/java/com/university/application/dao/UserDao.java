package com.university.application.dao;

import com.university.application.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {
    protected ConnectionManager connectionManager;

    private static UserDao instance = null;
    protected UserDao() {
        connectionManager = new ConnectionManager();
    }
    public static UserDao getInstance() {
        if(instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    public User create(User user) throws SQLException {
        String insertUser = "INSERT INTO User(username, firstName, lastName, email) VALUES(?,?,?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKeys = null;
        try {
            connection = connectionManager.getConnection();
            // Modify the prepareStatement call to request the return of generated keys
            insertStmt = connection.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, user.getUsername());
            insertStmt.setString(2, user.getFirstName());
            insertStmt.setString(3, user.getLastName());
            insertStmt.setString(4, user.getEmail());
            insertStmt.executeUpdate();
            
            // Retrieve the generated keys
            resultKeys = insertStmt.getGeneratedKeys();
            int userId = 1; // Start from 1 if no ID is found
            if(resultKeys.next()) {
                userId = resultKeys.getInt(1); // Assume the first column contains the userID
            }
            // Update the user object with the new userID
            user.setUserID(userId);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(insertStmt != null) {
                insertStmt.close();
            }
            if(resultKeys != null) {
                resultKeys.close();
            }
        }
    }
    
    public boolean doesUsernameExist(String username) throws SQLException {
        String selectUser = "SELECT 1 FROM User WHERE username = ?;";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(selectUser)) {

            selectStmt.setString(1, username);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                return resultSet.next(); // True if there's at least one result
            }
        }
    }

    public User getUserByUserID(int userID) throws SQLException {
        String selectUser = "SELECT userID, username, firstName, lastName, email FROM User WHERE userID=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectUser);
            selectStmt.setInt(1, userID);
            results = selectStmt.executeQuery();
            if(results.next()) {
                int resultUserID = results.getInt("userID");
                String username = results.getString("username");
                String firstName = results.getString("firstName");
                String lastName = results.getString("lastName");
                String email = results.getString("email");
                User user = new User(resultUserID, username, firstName, lastName, email);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
        return null;
    }
    
    public User getUserByUsername(String username) throws SQLException {
        String selectUser = "SELECT userID, username, firstName, lastName, email FROM User WHERE username=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectUser);
            selectStmt.setString(1, username);
            results = selectStmt.executeQuery();
            if (results.next()) {
                int resultUserID = results.getInt("userID");
                String resultUsername = results.getString("username");
                String firstName = results.getString("firstName");
                String lastName = results.getString("lastName");
                String email = results.getString("email");
                User user = new User(resultUserID, resultUsername, firstName, lastName, email);
                return user;
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
        return null; // Return null if no user is found with the given username
    }
    
    // Update a user's details
    public User update(User user) throws SQLException {
        String updateUser = "UPDATE User SET username=?, firstName=?, lastName=?, email=? WHERE userID=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateUser);
            updateStmt.setString(1, user.getUsername());
            updateStmt.setString(2, user.getFirstName());
            updateStmt.setString(3, user.getLastName());
            updateStmt.setString(4, user.getEmail());
            updateStmt.setInt(5, user.getUserID());
            updateStmt.executeUpdate();
            
            // Return the updated user
            return user;
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

    // Delete a user by userID
    public boolean delete(User user) throws SQLException {
        String deleteUser = "DELETE FROM User WHERE userID=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteUser);
            deleteStmt.setInt(1, user.getUserID());
            int affectedRows = deleteStmt.executeUpdate();
            
            // Return true if a row was deleted
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
