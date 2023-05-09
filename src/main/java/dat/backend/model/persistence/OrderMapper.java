package dat.backend.model.persistence;

import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains all methods used to retrieve or alter order data in the database.
 * Every method in this class is static, and an instance of this class is never needed.
 * @MrJustMeDahl
 */
public class OrderMapper {

    /**
     * This method retrieves a list of orders belonging to a specific user, from the database.
     * @param userID user ID number - generated in the database.
     * @param connectionPool Required to establish connection to the database.
     * @return List of instances from the Order.java class - data for each instance is retrieved from the database.
     * @throws DatabaseException is thrown if there is no connection to the database, or if data retrieved from the database is invalid.
     * @author MrJustMeDahl
     */
    public static List<Order> getOrdersByUserID(int userID, ConnectionPool connectionPool) throws DatabaseException {
        List<Order> allOrders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE userId = ?";

        try(Connection conn = connectionPool.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userID);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int orderID = rs.getInt("orderId");
                    Carport carport = new Carport(getMaterialsForCarport(orderID, connectionPool), rs.getInt("carportPrice"), rs.getInt("carportIndicativePrice"), rs.getInt("carportWidth"), rs.getInt("carportLength"), rs.getInt("carportMinHeight"));
                    String orderStatus = rs.getString("orderStatus");
                    double price = rs.getDouble("price");
                    double indicativePrice = rs.getDouble("indicativePrice");
                    allOrders.add(new Order(orderID, userID, carport, orderStatus, price, indicativePrice));
                }
            }
        } catch (SQLException e){
            throw new DatabaseException("Error retrieving orders from database.");
        }
        return allOrders;
    }

    /**
     * This method generates an instance of Carport.java. The instance is made with data from the row, from the itemListView view in the database, that matches the given order ID.
     * @param orderID ID number for the order you need the carport for.
     * @param connectionPool Required to establish connection to the database.
     * @return Carport including all the data it persists of.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     * @author MrJustMeDahl
     */
    public static Map<Material, Integer> getMaterialsForCarport(int orderID, ConnectionPool connectionPool) throws DatabaseException{
        Map<Material, Integer> materials = new HashMap<>();
        String SQL = "SELECT * FROM fog.itemListView WHERE orderId = ?";
        try(Connection conn = connectionPool.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(SQL)){
                ps.setInt(1, orderID);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    if(rs.getString("partFor").equals("carport")) {
                        int materialID = rs.getInt("materialId");
                        String description = rs.getString("materialDescription");
                        String type = rs.getString("materialType");
                        String function = rs.getString("buildFunction");
                        double price = rs.getDouble("price");
                        int length = rs.getInt("length");
                        int amount = rs.getInt("amount");
                        Material newMaterial = null;
                        switch (rs.getString("buildFunction")) {
                            case "stolpe":
                                newMaterial = new Post(materialID, description, type, function, price, length);
                                break;
                            case "rem":
                                newMaterial = new Purlin(materialID, description, type, function, price, length);
                                break;
                            case "spær":
                                newMaterial = new Rafter(materialID, description, type, function, price, length);
                                break;
                            default:
                                throw new DatabaseException("Function of: "+ description + " " + materialID + " is not recognised in database.");
                        }
                        materials.put(newMaterial, amount);
                    }
                }
            }
        }catch(SQLException e){
            throw new DatabaseException("Error retrieving carport for order: " + orderID);
        }
        return materials;
    }
    public static Order createOrder (Carport carport, int userId, double price, double indicativePrice, ConnectionPool connectionPool) throws DatabaseException {
        int orderId = 0;
        String SQL = "INSERT INTO fog.orders (price, indicativePrice, orderStatus, userId, carportLength, carportWidth, carportMinHeight, carportPrice, carportIndicativePrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try(Connection connection = connectionPool.getConnection()){
        try (PreparedStatement ps = connection.prepareStatement(SQL)){

            ps.setDouble(1, price);
            ps.setDouble(2, indicativePrice);
            ps.setString(3, "pending");
            ps.setInt(4, userId);
            ps.setInt(5, carport.getLength());
            ps.setInt(6, carport.getWidth());
            ps.setInt(7, carport.getMinHeight());
            ps.setFloat(8, (float) carport.getPrice());
            ps.setFloat(9, (float) carport.getIndicativePrice());
            ps.executeUpdate();
        }
    }
    catch (SQLException ex){
        throw new DatabaseException(ex, "Error creating order in the Database");
    }
    return new Order(orderId, userId, carport, "pending", price, indicativePrice);
    }
    public static void updateOrderOrdered(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE fog.orders SET orders.orderStatus = ? WHERE orderId = ?";

        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){

                ps.setString(1, "ordered");
                ps.setInt(2, orderId);
                ps.execute();
            }
        } catch(SQLException e){
            throw new DatabaseException("Failed to update paid in database");
        }
    }
}
