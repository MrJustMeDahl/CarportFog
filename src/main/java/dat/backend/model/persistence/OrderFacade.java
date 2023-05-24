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
     * @author pelle112112
     */
    public static Order createOrder (Carport carport, int userId, double price, double indicativePrice, ItemList itemList, ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.createOrder(carport, userId, price, indicativePrice, itemList, connectionPool);
    }

    /**
     * This method will update the orderstatus for an order in the DB. Its used for changing the order which is visible in the shoppingbasket.
     * for the user. After pressing "get offer", it will change the orderstatus from "pending" to "ordered".
     * @param orderId Is the ID for the order itself.
     * @param connectionPool Is required for establishing connection to the DB.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     * @author pelle112112
     */
    public static void updateOrderOrdered(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        OrderMapper.updateOrderOrdered(orderId, connectionPool);
    }

    /**
     * This method returns a list of Order, where all orders have order status "ordered".
     * @param connectionPool required to establish connection to the database.
     * @return List of Order.
     * @throws DatabaseException Is thrown if there isn't a valid connection to the database or if the data in the database is invalid.
     * @author MrJustMeDahl
     */
    public static List<Order> getNewOrders(ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.getNewOrders(connectionPool);
    }

    /**
     * This method will update the orderstatus for an order in the DB to "payed"
     * @param orderId Is the ID for the order itself.
     * @param connectionPool Is required for establishing connection to the DB.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     * @author pelle112112
     */

    public static int updateOrderPayed(int orderId, ConnectionPool connectionPool) throws DatabaseException{
        OrderMapper.updateOrderPayed(orderId, connectionPool);
        return orderId;
    }

    /**
     * This method deletes an order from the database, it deletes everything to do with the order in orders table and itemList table.
     * @param orderID ID number for the order you want to delete.
     * @param connectionPool required to establish connection to the database.
     * @return True if both statements succeeded in removing lines.
     * @throws DatabaseException Is thrown if there isn't a valid connection to the database.
     * @author MrJustMeDahl
     */
    public static boolean deleteOrder(int orderID, ConnectionPool connectionPool) throws DatabaseException{
        return OrderMapper.deleteOrder(orderID, connectionPool);
    }
    /**
     * This method will iterate through the hashmap which contains the materials needed for the carport. It will then be
     * put into the DB.
     * @param itemList The Hashmap of the materials. The key is the object of the material, while the value is the amount
     * @param orderId Is the ID for the order itself.
     * @param connectionPool Is required for establishing connection to the DB.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     * @author pelle112112
     */
    public static void addItemlistToDB(ItemList itemList, int orderId, ConnectionPool connectionPool) throws DatabaseException {
        OrderMapper.addItemlistToDB(itemList, orderId, connectionPool);
    }

    /**
     * This method handles when an offer is sent to the customer.
     * It changes the price to match the price entered by the salesperson and changes order status to confirmed.
     * @param orderID required to identify correct order.
     * @param salesPrice Price entered by the salesperson.
     * @param connectionPool required to establish connection to the database.
     * @return true if there is changes at 1 line in the database.
     * @throws DatabaseException Is thrown there is no connection to the database or if input data is invalid.
     * @author MrJustMeDahl
     */
    public static boolean sendOfferToCustomer(int orderID, double salesPrice, ConnectionPool connectionPool) throws DatabaseException {
        return OrderMapper.sendOfferToCustomer(orderID, salesPrice, connectionPool);
    }

    /**
     * This method inserts a new item list for an order.
     * It deletes every itemlist row for that order and inserts a line for each ItemListMaterial in the ItemList
     * @param orderID Needed to recognise which order to change.
     * @param itemList An object that contains all the materials, messages, etc. for an order.
     * @param connectionPool required to establish connection to the database.
     * @return true if delete SQL query is successful.
     * @throws DatabaseException is thrown if there isn't any rows inserted in itemList table.
     * @author MrJustMeDahl
     */
    public static boolean updateItemListForOrder(int orderID, ItemList itemList, ConnectionPool connectionPool) throws DatabaseException{
        return OrderMapper.updateItemListForOrder(orderID, itemList, connectionPool);
    }

    /**
     * This method updates every column in the order table for a specific order, except varying ids and the order status.
     * @param orderID Needed to recognise which order to change.
     * @param carport An object of the carport that belongs to the order - it contains all the data needed to update everything needed.
     * @param connectionPool required to establish connection to the database.
     * @return true if exactly 1 line is affected by the SQL query.
     * @throws DatabaseException Is thrown there is no connection to the database or if input data is invalid.
     * @author MrJustMeDahl
     */
    public static boolean updateMeasurementsForOrder(int orderID, Carport carport, ConnectionPool connectionPool) throws DatabaseException{
        return OrderMapper.updateMeasurementsForOrder(orderID, carport, connectionPool);
    }

    /**
     * This method handles the request to retrive all orders from the mapper, and forward it to the allOrders servlet
     * @param connectionPool
     * @return
     * @throws DatabaseException
     * @author CarstenJuhl
     */
    public static List<Order> getAllOrders(ConnectionPool connectionPool)throws DatabaseException {
        return OrderMapper.getAllOrders(connectionPool);
    }

}
