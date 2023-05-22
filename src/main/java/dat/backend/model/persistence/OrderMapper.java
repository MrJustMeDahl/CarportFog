package dat.backend.model.persistence;

import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all methods used to retrieve or alter order data in the database.
 * Every method in this class is static, and an instance of this class is never needed.
 *
 * @MrJustMeDahl
 */
public class OrderMapper {

    /**
     * This method retrieves a list of orders belonging to a specific user, from the database.
     *
     * @param userID         user ID number - generated in the database.
     * @param connectionPool Required to establish connection to the database.
     * @return List of instances from the Order.java class - data for each instance is retrieved from the database.
     * @throws DatabaseException is thrown if there is no connection to the database, or if data retrieved from the database is invalid.
     * @author MrJustMeDahl
     */
    public static List<Order> getOrdersByUserID(int userID, ConnectionPool connectionPool) throws DatabaseException {
        List<Order> allOrders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE userId = ?";

        try (Connection conn = connectionPool.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int orderID = rs.getInt("orderId");
                    int carportLength = rs.getInt("carportLength");
                    int carportWidth = rs.getInt("carportWidth");
                    int carportMinHeight = rs.getInt("carportMinHeight");
                    int shedLength = rs.getInt("shedLength");
                    int shedWidth = rs.getInt("shedWidth");
                    double shedPrice = rs.getDouble("shedPrice");
                    double shedIndicativePrice = rs.getDouble("shedIndicativePrice");
                    ItemList itemList;
                    if (shedLength == 0) {
                        itemList = new ItemList(carportLength, carportWidth, carportMinHeight, false, 0, 0);
                    } else {
                        itemList = new ItemList(carportLength, carportWidth, carportMinHeight, true, shedLength, shedWidth);
                    }
                    itemList = getItemListContentForOrder(orderID, itemList, connectionPool);
                    Shed shed = new Shed(itemList.getMaterialsForShed(), shedWidth, shedLength, carportMinHeight, shedPrice, shedIndicativePrice);
                    Carport carport = new Carport(itemList.getMaterialsForCarport(), rs.getInt("carportPrice"), rs.getInt("carportIndicativePrice"), carportWidth, carportLength, carportMinHeight, shed);
                    String orderStatus = rs.getString("orderStatus");
                    double price = rs.getDouble("price");
                    double indicativePrice = rs.getDouble("indicativePrice");
                    allOrders.add(new Order(orderID, userID, carport, orderStatus, price, indicativePrice, itemList));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving orders from database.");
        }
        return allOrders;
    }

    /**
     * This method generates an instance of ItemList.java. Data for each ItemListMaterial element is retrieved from the itemListView view in the database, that matches the given order ID.
     *
     * @param orderID        ID number for the order you need the carport for.
     * @param itemList       Empty ItemList that needs to filled with content for the given order.
     * @param connectionPool Required to establish connection to the database.
     * @return Carport including all the data it persists of.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     * @author MrJustMeDahl
     */
    public static ItemList getItemListContentForOrder(int orderID, ItemList itemList, ConnectionPool connectionPool) throws DatabaseException {
        String SQL = "SELECT * FROM itemListView WHERE orderId = ?";
        try (Connection conn = connectionPool.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(SQL)) {
                ps.setInt(1, orderID);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int materialID = rs.getInt("materialId");
                    int materialVariantID = rs.getInt("materialVariantId");
                    String description = rs.getString("materialDescription");
                    String type = rs.getString("materialType");
                    String function = rs.getString("buildFunction");
                    double price = rs.getDouble("price");
                    int length = rs.getInt("length");
                    int amount = rs.getInt("amount");
                    String message = rs.getString("message");
                    Material newMaterial = null;
                    switch (rs.getString("buildFunction")) {
                        case "stolpe":
                            newMaterial = new Post(materialID, materialVariantID, description, type, function, price, length);
                            break;
                        case "rem":
                            newMaterial = new Purlin(materialID, materialVariantID, description, type, function, price, length);
                            break;
                        case "spær":
                            newMaterial = new Rafter(materialID, materialVariantID, description, type, function, price, length);
                            break;
                        case "tag":
                            newMaterial = new Roof(materialID, materialVariantID, description, type, function, price, length);
                            break;
                        case "bræddebeklædning":
                            newMaterial = new Sheathing(materialID, materialVariantID, description, type, function, price, length);
                            break;
                        default:
                            newMaterial = new UnspecifiedMaterial(materialID, materialVariantID, description, type, function, price, length, price);
                    }
                    String partFor = rs.getString("partFor");
                    int actualLength = rs.getInt("actualLength");
                    itemList.addMaterialToItemList(new ItemListMaterial(newMaterial, amount, message, partFor, actualLength));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving itemlist for order: " + orderID);
        }
        return itemList;
    }

    /**
     * @param carport         the Object, which will be generated with height, width and length from the user, later to be put into the order in the DB
     * @param userId          ID number for the user, which the order is made for.
     * @param price           Price is the final price for the order. (This will later be changeable for the admin)
     * @param indicativePrice The price which is predetermined, before the admin has made a deal.
     * @param connectionPool  Is required for establishing connection to the DB.
     * @return Will return the order, which is being created by the method.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     */
    public static Order createOrder(Carport carport, int userId, double price, double indicativePrice, ItemList itemList, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = 0;
        String SQL = "INSERT INTO orders (price, indicativePrice, orderStatus, userId, carportLength, carportWidth, carportMinHeight, carportPrice, carportIndicativePrice, shedLength, shedWidth, shedPrice, shedIndicativePrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

                ps.setDouble(1, price);
                ps.setDouble(2, indicativePrice);
                ps.setString(3, "pending");
                ps.setInt(4, userId);
                ps.setInt(5, carport.getLength());
                ps.setInt(6, carport.getWidth());
                ps.setInt(7, carport.getMinHeight());
                ps.setFloat(8, (float) carport.getPrice());
                ps.setFloat(9, (float) carport.getIndicativePrice());
                if(carport.getShed() != null){
                    ps.setInt(10, carport.getShed().getLength());
                    ps.setInt(11, carport.getShed().getWidth());
                    ps.setFloat(12, (float) carport.getShed().getPrice());
                    ps.setFloat(13, (float) carport.getShed().getIndicativePrice());
                }
                else {
                    ps.setInt(10, 0);
                    ps.setInt(11, 0);
                    ps.setFloat(12, 0);
                    ps.setFloat(13, 0);
                }

                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                while (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error creating order in the Database");
        }


        return new Order(orderId, userId, carport, "pending", price, indicativePrice, itemList);
    }


    /**
     * This method will update the orderstatus for an order in the DB. Its used for changing the order which is visible in the shoppingbasket.
     * for the user. After pressing "get offer", it will change the orderstatus from "pending" to "ordered".
     *
     * @param orderId        Is the ID for the order itself.
     * @param connectionPool Is required for establishing connection to the DB.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     */
    public static void updateOrderOrdered(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE orders SET orders.orderStatus = ? WHERE orderId = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setString(1, "ordered");
                ps.setInt(2, orderId);
                ps.execute();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update paid in database");
        }
    }

    /**
     * This method will update the orderstatus for an order in the DB to "payed"
     *
     * @param orderId        Is the ID for the order itself.
     * @param connectionPool Is required for establishing connection to the DB.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     */
    public static int updateOrderPayed(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE orders SET orders.orderStatus = ? WHERE orderId = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setString(1, "payed");
                ps.setInt(2, orderId);
                ps.execute();
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update paid in database");
        }
        return orderId;
    }

    /**
     * This method will iterate through the hashmap which contains the materials needed for the carport. It will then be
     * put into the DB.
     *
     * @param itemList       The Hashmap of the materials. The key is the object of the material, while the value is the amount
     * @param orderId        Is the ID for the order itself.
     * @param connectionPool Is required for establishing connection to the DB.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     */
    public static void addItemlistToDB(ItemList itemList, int orderId, ConnectionPool connectionPool) throws DatabaseException {

        String SQL = "INSERT INTO itemList (amount, orderId, materialVariantId, partFor, message, actualLength) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            for (ItemListMaterial i : itemList.getMaterials()) {
                try (PreparedStatement ps = connection.prepareStatement(SQL)) {

                    ps.setInt(1, i.getAmount());
                    ps.setInt(2, orderId);
                    ps.setInt(3, i.getMaterial().getMaterialVariantID());
                    ps.setString(4, i.getPartFor());
                    ps.setString(5, i.getMessage());
                    ps.setInt(6, i.getActualLength());
                    ps.execute();
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error creating order in the Database");
        }
    }


    /**
     * This method returns a list of Order, where all orders have order status "ordered".
     *
     * @param connectionPool required to establish connection to the database.
     * @return List of Order.
     * @throws DatabaseException Is thrown if there isn't a valid connection to the database or if the data in the database is invalid.
     * @author MrJustMeDahl
     */
    public static List<Order> getNewOrders(ConnectionPool connectionPool) throws DatabaseException {
        List<Order> newOrders = new ArrayList<>();
        String SQL = "SELECT * FROM orders WHERE orderStatus = 'ordered'";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int orderID = rs.getInt("orderId");
                    int userID = rs.getInt("userId");
                    int carportLength = rs.getInt("carportLength");
                    int carportWidth = rs.getInt("carportWidth");
                    int carportMinHeight = rs.getInt("carportMinHeight");
                    int shedLength = rs.getInt("shedLength");
                    int shedWidth = rs.getInt("shedWidth");
                    double shedPrice = rs.getDouble("shedPrice");
                    double shedIndicativePrice = rs.getDouble("shedIndicativePrice");
                    ItemList itemList;
                    if (shedLength == 0) {
                        itemList = new ItemList(carportLength, carportWidth, carportMinHeight, false, 0, 0);
                    } else {
                        itemList = new ItemList(carportLength, carportWidth, carportMinHeight, true, shedLength, shedWidth);
                    }
                    itemList = getItemListContentForOrder(orderID, itemList, connectionPool);
                    Carport carport = new Carport(itemList.getMaterialsForCarport(), rs.getDouble("carportPrice"), rs.getDouble("carportIndicativePrice"), carportWidth, carportLength, carportMinHeight, new Shed(itemList.getMaterialsForShed(), shedWidth, shedLength, carportMinHeight, shedPrice, shedIndicativePrice));
                    String orderStatus = rs.getString("orderStatus");
                    double price = rs.getDouble("price");
                    double indicativePrice = rs.getDouble("indicativePrice");
                    newOrders.add(new Order(orderID, userID, carport, orderStatus, price, indicativePrice, itemList));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to retrieve new orders from the database.");
        }
        return newOrders;
    }

    /**
     * This method deletes an order from the database, it deletes everything to do with the order in orders table and itemList table.
     *
     * @param orderID        ID number for the order you want to delete.
     * @param connectionPool required to establish connection to the database.
     * @return True if both statements succeeded in removing lines.
     * @throws DatabaseException Is thrown if there isn't a valid connection to the database.
     * @author MrJustMeDahl
     */
    public static boolean deleteOrder(int orderID, ConnectionPool connectionPool) throws DatabaseException {
        String ordersSQL = "DELETE FROM orders WHERE orderId = ?";
        String itemListSQL = "DELETE FROM itemList WHERE orderId = ?";
        boolean itemList = false;
        boolean orders = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(itemListSQL)) {
                ps.setInt(1, orderID);
                if (ps.executeUpdate() > 0) {
                    itemList = true;
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(ordersSQL)) {
                ps.setInt(1, orderID);
                if (ps.executeUpdate() == 1) {
                    orders = true;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete order with ordernumber: " + orderID + " from the database.");
        }
        return itemList && orders;
    }

    /**
     * This method handles when an offer is sent to the customer.
     * It changes the price to match the price entered by the salesperson and changes order status to confirmed.
     *
     * @param orderID        required to identify correct order.
     * @param salesPrice     Price entered by the salesperson.
     * @param connectionPool required to establish connection to the database.
     * @return true if there is changes at 1 line in the database.
     * @throws DatabaseException Is thrown there is no connection to the database or if input data is invalid.
     * @author MrJustMeDahl
     */
    public static boolean sendOfferToCustomer(int orderID, double salesPrice, ConnectionPool connectionPool) throws DatabaseException {
        String SQL = "UPDATE orders SET indicativePrice = ?, orderStatus = ? WHERE orderId = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setDouble(1, salesPrice);
                ps.setString(2, "confirmed");
                ps.setInt(3, orderID);
                int linesAffected = ps.executeUpdate();
                if (linesAffected == 1) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to send offer to customer - order id number: " + orderID);
        }
        return false;
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
    public static boolean updateItemListForOrder(int orderID, ItemList itemList, ConnectionPool connectionPool) throws DatabaseException {
        String removeOldItemListSQL = "DELETE FROM itemList WHERE orderId = ?";
        String insertNewItemListSQL = "INSERT INTO itemList (amount, orderId, materialVariantId, partFor, message) VALUES (?, ?, ?, ?, ?)";
        boolean deleteSucces = false;
        if(itemList.getMaterials().size() == 0){
            throw new DatabaseException("There is no items in the itemlist, therefore it can not be updated.");
        }
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps1 = connection.prepareStatement(removeOldItemListSQL)) {
                ps1.setInt(1, orderID);
                if (ps1.executeUpdate() > 0) {
                    deleteSucces = true;
                }
            }
            for (ItemListMaterial i : itemList.getMaterials()) {
                try (PreparedStatement ps2 = connection.prepareStatement(insertNewItemListSQL)) {
                    ps2.setInt(1, i.getAmount());
                    ps2.setInt(2, orderID);
                    ps2.setInt(3, i.getMaterial().getMaterialVariantID());
                    ps2.setString(4, i.getPartFor());
                    ps2.setString(5, i.getMessage());
                    if (ps2.executeUpdate() != 1){
                        throw new DatabaseException("Something went wrong inserting itemlist for order: " + orderID);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to update itemlist for order: " + orderID);
        }
        return deleteSucces;
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
        String SQL = "UPDATE orders SET carportLength = ?, carportWidth = ?, carportMinHeight = ?, price = ?, indicativePrice = ?, carportPrice = ?, carportIndicativePrice = ?, shedLength = ?, shedWidth = ?, shedPrice = ?, shedIndicativePrice = ? WHERE orderId = ?";
        boolean updateSucces = false;
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(SQL)){
                ps.setInt(1, carport.getLength());
                ps.setInt(2, carport.getWidth());
                ps.setInt(3, carport.getMinHeight());
                ps.setDouble(4, carport.getPrice());
                ps.setDouble(5, carport.getIndicativePrice());
                ps.setDouble(6, carport.getPrice());
                ps.setDouble(7, carport.getIndicativePrice());
                ps.setInt(8, carport.getShed().getLength());
                ps.setInt(9, carport.getShed().getWidth());
                ps.setDouble(10, carport.getShed().getPrice());
                ps.setDouble(11, carport.getShed().getIndicativePrice());
                ps.setInt(12, orderID);
                if(ps.executeUpdate() == 1){
                    updateSucces = true;
                }
            }
        } catch (SQLException e){
            throw new DatabaseException("Failed to update order with new measurements");
        }
        return updateSucces;
    }

    /**
     * A method to retrieve all orders from DB, used on the admin allorder page.
     * @param connectionPool
     * @return a List of orders
     * @throws DatabaseException
     * @author CarstenJuhl
     */
    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws DatabaseException {
        List<Order> allOrders = new ArrayList<>();
        String SQL = "SELECT * FROM orders";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int orderID = rs.getInt("orderId");
                    int userID = rs.getInt("userId");
                    int carportLength = rs.getInt("carportLength");
                    int carportWidth = rs.getInt("carportWidth");
                    int carportMinHeight = rs.getInt("carportMinHeight");
                    int shedLength = rs.getInt("shedLength");
                    int shedWidth = rs.getInt("shedWidth");
                    double shedPrice = rs.getDouble("shedPrice");
                    double shedIndicativePrice = rs.getDouble("shedIndicativePrice");
                    ItemList itemList;
                    if (shedLength == 0) {
                        itemList = new ItemList(carportLength, carportWidth, carportMinHeight, false, 0, 0);
                    } else {
                        itemList = new ItemList(carportLength, carportWidth, carportMinHeight, true, shedLength, shedWidth);
                    }
                    itemList = getItemListContentForOrder(orderID, itemList, connectionPool);
                    Carport carport = new Carport(itemList.getMaterialsForCarport(), rs.getDouble("carportPrice"), rs.getDouble("carportIndicativePrice"), carportWidth, carportLength, carportMinHeight, new Shed(itemList.getMaterialsForShed(), shedWidth, shedLength, carportMinHeight, shedPrice, shedIndicativePrice));
                    String orderStatus = rs.getString("orderStatus");
                    double price = rs.getDouble("price");
                    double indicativePrice = rs.getDouble("indicativePrice");
                    allOrders.add(new Order(orderID, userID, carport, orderStatus, price, indicativePrice, itemList));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to retrieve new orders from the database.");
        }
        return allOrders;
    }
}
