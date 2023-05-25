package dat.backend.model.persistence;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains all methods used to retrieve or alter userdata in the database.
 * Every method in this class is static, and an instance of this class is never needed.
 * @MrJustMeDahl
 */
class UserMapper {

    /**
     * Method is used to log in users.
     * It checks if a user exist with the email and password given, if it does it create a user object with the data from that row in the database.
     * @param email user's email / functions as a username.
     * @param password Password matching the given email.
     * @param connectionPool Required to make connection to the database.
     * @return User
     * @throws DatabaseException is thrown if there is no connection to the database or if the given email or password doesn't match any data in the user table.
     * @author MrJustMeDahl
     */
    static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");

        User user = null;

        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    int userID = rs.getInt("userId");
                    String name = rs.getString("FullName");
                    int phoneNumber = rs.getInt("phoneNumber");
                    String address = rs.getString("address");
                    String role = rs.getString("role");
                    if(ApplicationStart.getConnectionPool() == null) {
                        user = new User(userID, email, password, name, phoneNumber, address, role, connectionPool);
                    } else {
                        user = new User(userID, email, password, name, phoneNumber, address, role);
                    }
                } else
                {
                    throw new DatabaseException("Wrong email or password");
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Error logging in. Something went wrong with the database");
        }
        return user;
    }

    static User createUser(String email, String password, int phoneNumber, String address, String fullName, String role, ConnectionPool connectionPool) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        User user;
        String sql = "INSERT INTO user(email, password, phoneNumber, address, fullName, role) values (?,?,?,?,?,?)";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setString(1, email);
                ps.setString(2, password);
                ps.setInt(3, phoneNumber);
                ps.setString(4, address);
                ps.setString(5, fullName);
                ps.setString(6, role);
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                int userID = 0;
                while(rs.next()){
                    userID = rs.getInt(1);
                }
                if (userID != 0) {
                    user = new User(userID, email, password, fullName, phoneNumber, address, role);
                } else {
                    throw new DatabaseException("The user with email = " + email + " could not be inserted into the database");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Could not insert username into database");
        }
        return user;
    }


    /**
     * This methods retrieves a Set of user objects from the database, who are owners of one or more orders from the given list.
     * @param orders List of Order objects
     * @param connectionPool required to establish connection to the database.
     * @return Set of User objects
     * @throws DatabaseException Is thrown if there is no connection to the database or if data is invalid.
     * @author MrJustMeDahl
     */
    static Set<User> getUsersForOrders(List<Order> orders, ConnectionPool connectionPool) throws DatabaseException {
        Set<User> userSet = new HashSet<>();
        String SQL = "SELECT * FROM user WHERE userId = ?";
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(SQL)){
                for(Order o: orders){
                    ps.setInt(1, o.getUserID());
                    User user = null;
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        int userID = rs.getInt("userId");
                        String email = rs.getString("email");
                        String password = rs.getString("password");
                        String name = rs.getString("FullName");
                        int phoneNumber = rs.getInt("phoneNumber");
                        String address = rs.getString("address");
                        String role = rs.getString("role");
                        if(ApplicationStart.getConnectionPool() == null) {
                            user = new User(userID, email, password, name, phoneNumber, address, role, connectionPool);
                        } else {
                            user = new User(userID, email, password, name, phoneNumber, address, role);
                        }
                        userSet.add(user);
                    }
                }
            }
        } catch (SQLException e){
            throw new DatabaseException("Failed to retrieve users with new orders.");
        }
        return userSet;
    }

    /**
     * A method to retrieve all users from the DB to use on the all orders admin page.
     * @param connectionPool
     * @return a List of users
     * @throws DatabaseException
     * @author CarstenJuhl
     */
    public static List<User> getAllUsers(ConnectionPool connectionPool)throws DatabaseException {
        List<User> allUsers = new ArrayList<>();
        String SQL = "SELECT * FROM user";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int userId = rs.getInt("userId");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String name = rs.getString("FullName");
                    int phoneNumber = rs.getInt("phoneNumber");
                    String address = rs.getString("address");
                    String role = rs.getString("role");

                    allUsers.add(new User(userId, email, password, name, phoneNumber, address, role));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to retrieve all Order data.");
        }
        return allUsers;
    }
}
