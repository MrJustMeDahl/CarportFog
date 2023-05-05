package dat.backend.model.persistence;

import dat.backend.model.entities.Carport;
import dat.backend.model.entities.Material;
import dat.backend.model.entities.Order;
import dat.backend.model.entities.Post;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapper {

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
