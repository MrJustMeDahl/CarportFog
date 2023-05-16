package dat.backend.model.persistence;

import dat.backend.model.entities.Material;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;

import java.util.Map;

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
        return UserMapper.createUser(email, password, phoneNumber, address, fullName, role, connectionPool);
    }

}
