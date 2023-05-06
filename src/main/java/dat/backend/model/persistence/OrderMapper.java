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
        String sql = "SELECT * FROM fog.order WHERE userId = ?";

        try(Connection conn = connectionPool.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userID);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int orderID = rs.getInt("orderId");
                    Carport carport = getCarportForOrder(orderID, connectionPool);
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
    public static Carport getCarportForOrder(int orderID, ConnectionPool connectionPool) throws DatabaseException{
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
        return null;
    }
}
