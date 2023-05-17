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
                        itemList = new ItemList(carportLength, carportWidth, carportMinHeight, false);
                    } else {
                        itemList = new ItemList(carportLength, carportWidth, carportMinHeight, true);
                    }
                    itemList = getItemListContentForOrder(orderID, itemList, connectionPool);
                    Carport carport = new Carport(itemList.getMaterialsForCarport(), rs.getInt("carportPrice"), rs.getInt("carportIndicativePrice"), carportWidth, carportLength, carportMinHeight);
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
                        default:
                            throw new DatabaseException("Function of: " + description + " " + materialID + " is not recognised in database.");
                    }
                    String partFor = rs.getString("partFor");
                    itemList.addMaterialToItemList(new ItemListMaterial(newMaterial, amount, message, partFor));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving carport for order: " + orderID);
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
        String SQL = "INSERT INTO orders (price, indicativePrice, orderStatus, userId, carportLength, carportWidth, carportMinHeight, carportPrice, carportIndicativePrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
    public static void updateOrderPayed(int orderId, ConnectionPool connectionPool) throws DatabaseException {
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

        String SQL = "INSERT INTO itemList (amount, orderId, materialVariantId, partFor, message) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection()) {
            for (ItemListMaterial i : itemList.getMaterials()) {
                try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                    ps.setInt(1, i.getAmount());
                    ps.setInt(2, orderId);
                    ps.setInt(3, i.getMaterial().getMaterialVariantID());
                    ps.setString(4, i.getPartFor());
                    ps.setString(5, i.getMessage());
                    ps.execute();

                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex, "Error creating order in the Database");
        }
    }


    /**
     * This method returns a list of Order, where all orders have order status "ordered".
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
                        itemList = new ItemList(carportLength, carportWidth, carportMinHeight, false);
                    } else {
                        itemList = new ItemList(carportLength, carportWidth, carportMinHeight, true);
                    }
                    itemList = getItemListContentForOrder(orderID, itemList, connectionPool);
                    Carport carport = new Carport(itemList.getMaterialsForCarport(), rs.getDouble("carportPrice"), rs.getDouble("carportIndicativePrice"), carportWidth, carportLength, carportMinHeight);
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
     * @param orderID ID number for the order you want to delete.
     * @param connectionPool required to establish connection to the database.
     * @return True if both statements succeeded in removing lines.
     * @throws DatabaseException Is thrown if there isn't a valid connection to the database.
     * @author MrJustMeDahl
     */
    public static boolean deleteOrder(int orderID, ConnectionPool connectionPool) throws DatabaseException{
        String ordersSQL = "DELETE FROM orders WHERE orderId = ?";
        String itemListSQL = "DELETE FROM itemList WHERE orderId = ?";
        boolean itemList = false;
        boolean orders = false;
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(itemListSQL)){
                ps.setInt(1, orderID);
                if(ps.executeUpdate() > 0){
                    itemList = true;
                }
            }
            try(PreparedStatement ps = connection.prepareStatement(ordersSQL)){
                ps.setInt(1, orderID);
                if(ps.executeUpdate() == 1){
                    orders = true;
                }
            }
        } catch (SQLException e){
            throw new DatabaseException("Failed to delete order with ordernumber: " + orderID + " from the database.");
        }
        return itemList && orders;
    }
}
