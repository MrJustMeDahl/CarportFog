package dat.backend.persistence;

import dat.backend.model.entities.Carport;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.UserFacade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    private final static String USER = System.getenv("USERNAME");
    private final static String PASSWORD = System.getenv("PASSWORD");
    private final static String URL = "jdbc:mysql://134.122.87.83:3306/fog_test?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";

    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);

        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Create test database - if not exist
                stmt.execute("CREATE DATABASE  IF NOT EXISTS fog_test;");

                // create test tables - if not exist
                stmt.execute("CREATE TABLE IF NOT EXISTS fog_test.user LIKE fog.user;");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @BeforeEach
    void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Remove all data in test tables
                stmt.execute("TRUNCATE fog_test.user");



                stmt.execute("insert into fog_test.user (email, password, phoneNumber, address, FullName, role) " +
                        "values ('user@usersen.dk','1234','12345678','Danmarksgade 1','User Usersen','user'),('admin@adminsen.dk','1234','87654321','Danmarksgade 2','Admin Adminsen','admin'), ('test@testesen.dk','1234','14725836','Danmarksgade 3','Test Testesen','user')");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testConnection() throws SQLException {
        Connection connection = connectionPool.getConnection();
        assertNotNull(connection);
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void login() throws DatabaseException {
        User expectedUser = new User(1, "user@usersen.dk", "1234", "User Usersen", 12345678, "Danmarksgade 1", "user", connectionPool);
        User actualUser = UserFacade.login("user@usersen.dk", "1234", connectionPool);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void invalidPasswordLogin() throws DatabaseException {
        assertThrows(DatabaseException.class, () -> UserFacade.login("user@usersen.dk", "123", connectionPool));
    }

    @Test
    void invalidUserNameLogin() throws DatabaseException {
        assertThrows(DatabaseException.class, () -> UserFacade.login("admin@adminsen", "1234", connectionPool));
    }

    @Test
    void createUser() throws DatabaseException {
        /*User newUser = UserFacade.createUser("jill", "1234", "user", connectionPool);
        User logInUser = UserFacade.login("jill", "1234", connectionPool);
        User expectedUser = new User("jill", "1234", "user");
        assertEquals(expectedUser, newUser);
        assertEquals(expectedUser, logInUser);


         */
    }

    @Test
    void getUsersForOrders() throws DatabaseException {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, 1, new Carport(new HashMap<>(), 300, 580, 210, null), "ordered", 1000, 1500, null));
        orders.add(new Order(2, 2, new Carport(new HashMap<>(), 300, 580, 210, null), "ordered", 1000, 1500, null));
        orders.add(new Order(3, 2, new Carport(new HashMap<>(), 300, 580, 210, null), "ordered", 1000, 1500, null));
        Set<User> users = UserFacade.getUsersForOrders(orders, connectionPool);
        assertEquals(2, users.size());
    }

}