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

    /**
     * @param carport the Object, which will be generated with height, width and length from the user, later to be put into the order in the DB
     * @param userId ID number for the user, which the order is made for.
     * @param price Price is the final price for the order. (This will later be changeable for the admin)
     * @param indicativePrice The price which is predetermined, before the admin has made a deal.
     * @param connectionPool Is required for establishing connection to the DB.
     * @return Will return the order, which is being created by the method.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     */
    public static Order createOrder (Carport carport, int userId, double price, double indicativePrice, ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.createOrder(carport, userId, price, indicativePrice, connectionPool);
    }

    /**
     * This method will update the orderstatus for an order in the DB. Its used for changing the order which is visible in the shoppingbasket.
     * for the user. After pressing "get offer", it will change the orderstatus from "pending" to "ordered".
     * @param orderId Is the ID for the order itself.
     * @param connectionPool Is required for establishing connection to the DB.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     */
    public static void updateOrderOrdered(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        OrderMapper.updateOrderOrdered(orderId, connectionPool);
    }

    public static List<Order> getNewOrders(ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.getNewOrders(connectionPool);
    }

    public static void updateOrderPayed(int orderId, ConnectionPool connectionPool) throws DatabaseException{
        OrderMapper.updateOrderPayed(orderId, connectionPool);
    }

    public static boolean deleteOrder(int orderID, ConnectionPool connectionPool) throws DatabaseException{
        return OrderMapper.deleteOrder(orderID, connectionPool);
    }
}
