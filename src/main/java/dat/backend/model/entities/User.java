package dat.backend.model.entities;

import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;

import java.util.List;
import java.util.Objects;

public class User {
    private int userID;
    private String email;
    private String password;
    private String name;
    private int phoneNumber;
    private String address;
    private String role;
    private List<Order> orders;

    public User(int userID, String email, String password, String name, int phoneNumber, String address, String role) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
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

    public void loadOrders(ConnectionPool connectionPool) throws DatabaseException {
        orders = OrderFacade.getOrdersByUserID(userID, connectionPool);
    }

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
}
