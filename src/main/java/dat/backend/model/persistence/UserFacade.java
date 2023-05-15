package dat.backend.model.persistence;

import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;

import java.util.List;
import java.util.Set;

/**
 * This class is a facade for UserMapper.java. For the sake of simplicity and making the class easier to read.
 * Every method in this class is static, and an instance of this class is never needed.
 * @author MrJustMeDahl
 */
public class UserFacade
{
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
    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        return UserMapper.login(email, password, connectionPool);
    }

    public static User createUser(String email, String password, int phoneNumber, String address, String fullName, String role, ConnectionPool connectionPool) throws DatabaseException
    {
        System.out.println("L30 UF");
        return UserMapper.createUser(email, password, phoneNumber, address, fullName, role, connectionPool);
    }

    /**
     * This methods retrieves a Set of user objects from the database, who are owners of one or more orders from the given list.
     * @param newOrders List of Order objects
     * @param connectionPool required to establish connection to the database.
     * @return Set of User objects
     * @throws DatabaseException Is thrown if there is no connection to the database or if data is invalid.
     * @author MrJustMeDahl
     */
    public static Set<User> getUsersForOrders(List<Order> newOrders, ConnectionPool connectionPool) throws DatabaseException {
        return UserMapper.getUsersForOrders(newOrders, connectionPool);
    }
}
