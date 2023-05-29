package dat.backend.model.persistence;

import dat.backend.model.entities.Carport;
import dat.backend.model.entities.ItemList;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.User;
import dat.backend.model.exceptions.DatabaseException;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a facade for OrderMapper.java. For the sake of simplicity and making the class easier to read.
 * Every method in this class is static, and an instance of this class is never needed.
 * @author MrJustMeDahl
 */
public class OrderFacade {

    public static List<Order> getOrdersByUserID(int userID, ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.getOrdersByUserID(userID, connectionPool);
    }

    public static Order createOrder (Carport carport, int userId, double price, double indicativePrice, ItemList itemList, ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.createOrder(carport, userId, price, indicativePrice, itemList, connectionPool);
    }

    public static void updateOrderOrdered(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        OrderMapper.updateOrderOrdered(orderId, connectionPool);
    }


    public static List<Order> getNewOrders(ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.getNewOrders(connectionPool);
    }


    public static int updateOrderPayed(int orderId, ConnectionPool connectionPool) throws DatabaseException{
        OrderMapper.updateOrderPayed(orderId, connectionPool);
        return orderId;
    }


    public static boolean deleteOrder(int orderID, ConnectionPool connectionPool) throws DatabaseException{
        return OrderMapper.deleteOrder(orderID, connectionPool);
    }

    public static void addItemlistToDB(ItemList itemList, int orderId, ConnectionPool connectionPool) throws DatabaseException {
        OrderMapper.addItemlistToDB(itemList, orderId, connectionPool);
    }


    public static boolean sendOfferToCustomer(int orderID, double salesPrice, ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.sendOfferToCustomer(orderID, salesPrice, connectionPool);
    }


    public static boolean updateItemListForOrder(int orderID, ItemList itemList, ConnectionPool connectionPool) throws DatabaseException{
        return OrderMapper.updateItemListForOrder(orderID, itemList, connectionPool);
    }


    public static boolean updateMeasurementsForOrder(int orderID, Carport carport, ConnectionPool connectionPool) throws DatabaseException{
        return OrderMapper.updateMeasurementsForOrder(orderID, carport, connectionPool);
    }


    public static List<Order> getAllOrders(ConnectionPool connectionPool)throws DatabaseException {
        return OrderMapper.getAllOrders(connectionPool);
    }

}
