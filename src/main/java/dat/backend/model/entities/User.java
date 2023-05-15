package dat.backend.model.entities;

import dat.backend.model.config.ApplicationStart;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;
import dat.backend.model.persistence.UserFacade;

import java.net.CookieHandler;
import java.util.List;
import java.util.Objects;

/**
 * This class contains all the data that represents a user of the application, including methods that alters the user's information.
 * @author MrJustMeDahl
 */
public class User {
    private int userID;
    private String email;
    private String password;
    private String name;
    private int phoneNumber;
    private String address;
    private String role;
    private List<Order> orders;

    /**
     * This constructor is used after login confirmation.
     * @param userID user's id number - generated by the database.
     * @param email user's email - required to log on the application.
     * @param password user's password for the application.
     * @param name Customer's full name - contact information.
     * @param phoneNumber Customer's phone number - contact information.
     * @param address Customer's real life address - contact information.
     * @param role admin/user
     * @author MrJustMeDahl
     */
    public User(int userID, String email, String password, String name, int phoneNumber, String address, String role) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        try{
        this.orders = OrderFacade.getOrdersByUserID(userID, ApplicationStart.getConnectionPool());
        } catch (DatabaseException e){
        }
    }

    /**
     * This constructor is only used for test-purposes.
     * @param userID user's id number - generated by the database.
     * @param email user's email - required to log on the application.
     * @param password user's password for the application.
     * @param name Customer's full name - contact information.
     * @param phoneNumber Customer's phone number - contact information.
     * @param address Customer's real life address - contact information.
     * @param role admin/user
     * @param connectionPool required to make connection to the database
     * @author MrJustMeDahl
     */
    public User(int userID, String email, String password, String name, int phoneNumber, String address, String role, ConnectionPool connectionPool) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        try{
            this.orders = OrderFacade.getOrdersByUserID(userID, connectionPool);
        } catch (DatabaseException e){
        }
    }

    public User(String email, String password, String name, int phoneNumber, String address) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = "user";
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    /**
     * When using the equals method a user is the same if every variable is the same except List of Order.
     * @param o Any object
     * @return True if every variable is the same, except the List of Order.
     * @author MrJustMeDahl
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getEmail().equals(user.getEmail()) &&
                getPassword().equals(user.getPassword()) &&
                getRole().equals(user.getRole()) &&
                getUserID() == user.getUserID() &&
                getPhoneNumber() == user.getPhoneNumber() &&
                getAddress().equals(user.getAddress()) &&
                getName().equals(user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword(), getRole());
    }

    @Override
    public String toString() {
        return "User{" +
                "Email='" + email + '\'' +
                ", kodeord='" + password + '\'' +
                ", rolle='" + role + '\'' +
                '}';
    }

    @Override
    public boolean createUserCheck() throws DatabaseException {

        User user = UserFacade.createUser(null, null, 0, null, null, role, new ConnectionPool());

        return false;
    }

}
