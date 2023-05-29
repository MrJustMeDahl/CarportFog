package dat.backend.model.persistence;

import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;

import java.util.List;
import java.util.Set;
import java.util.List;
import java.util.Map;

/**
 * This class is a facade for UserMapper.java. For the sake of simplicity and making the class easier to read.
 * Every method in this class is static, and an instance of this class is never needed.
 * @author MrJustMeDahl
 */
public class UserFacade
{

    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        return UserMapper.login(email, password, connectionPool);
    }

    public static User createUser(String email, String password, int phoneNumber, String address, String fullName, String role, ConnectionPool connectionPool) throws DatabaseException
    {
        return UserMapper.createUser(email, password, phoneNumber, address, fullName, role, connectionPool);
    }

    public static Set<User> getUsersForOrders(List<Order> newOrders, ConnectionPool connectionPool) throws DatabaseException {
        return UserMapper.getUsersForOrders(newOrders, connectionPool);
    }

    public static List<User> getAllUsers(ConnectionPool connectionPool) throws DatabaseException {
        return UserMapper.getAllUsers(connectionPool);
    }
}
