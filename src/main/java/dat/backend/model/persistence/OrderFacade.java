package dat.backend.model.persistence;

import dat.backend.model.entities.Carport;
import dat.backend.model.entities.Order;
import dat.backend.model.exceptions.DatabaseException;

import java.util.List;

/**
 * This class is a facade for OrderMapper.java. For the sake of simplicity and making the class easier to read.
 * Every method in this class is static, and an instance of this class is never needed.
 * @author MrJustMeDahl
 */
public class OrderFacade {

    /**
     * This method retrieves a list of orders belonging to a specific user, from the database.
     * @param userID user ID number - generated in the database.
     * @param connectionPool Required to establish connection to the database.
     * @return List of instances from the Order.java class - data for each instance is retrieved from the database.
     * @throws DatabaseException is thrown if there is no connection to the database, or if data retrieved from the database is invalid.
     * @author MrJustMeDahl
     */
    public static List<Order> getOrdersByUserID(int userID, ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.getOrdersByUserID(userID, connectionPool);
    }

    public static Order createOrder (Carport carport, int userId, double price, double indicativePrice, ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.createOrder(carport, userId, price, indicativePrice, connectionPool);
    }
    public static void updateOrderOrdered(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        OrderMapper.updateOrderOrdered(orderId, connectionPool);
    }

}
