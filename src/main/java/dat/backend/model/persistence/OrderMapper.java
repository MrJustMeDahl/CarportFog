package dat.backend.model.persistence;

import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.*;
import java.util.*;

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
        String SQL = "SELECT * FROM itemListView WHERE orderId = ?";
        try(Connection conn = connectionPool.getConnection()){
            try(PreparedStatement ps = conn.prepareStatement(SQL)){
                ps.setInt(1, orderID);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    if(rs.getString("partFor").equals("carport")) {
                        int materialID = rs.getInt("materialId");
                        int materialVariantID = rs.getInt("materialVariantId");
                        String description = rs.getString("materialDescription");
                        String type = rs.getString("materialType");
                        String function = rs.getString("buildFunction");
                        double price = rs.getDouble("price");
                        int length = rs.getInt("length");
                        int amount = rs.getInt("amount");
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
        int orderId = 0;
        String SQL = "INSERT INTO orders (price, indicativePrice, orderStatus, userId, carportLength, carportWidth, carportMinHeight, carportPrice, carportIndicativePrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = connectionPool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)){

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
                System.out.println("Auto-incremented values of the column ID generated by the current PreparedStatement object: ");
                while (rs.next()) {
                    System.out.println(rs.getString(1));
                    orderId = Integer.parseInt(rs.getString(1));
                }
            }
        }
        catch (SQLException ex){
            throw new DatabaseException(ex, "Error creating order in the Database");
        }

        return new Order(orderId, userId, carport, "pending", price, indicativePrice);
    }


    /**
     * This method will update the orderstatus for an order in the DB. Its used for changing the order which is visible in the shoppingbasket.
     * for the user. After pressing "get offer", it will change the orderstatus from "pending" to "ordered".
     * @param orderId Is the ID for the order itself.
     * @param connectionPool Is required for establishing connection to the DB.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     */
    public static void updateOrderOrdered(int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE orders SET orders.orderStatus = ? WHERE orderId = ?";

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

    /**
     * This method will update the orderstatus for an order in the DB to "payed"
     * @param orderId Is the ID for the order itself.
     * @param connectionPool Is required for establishing connection to the DB.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     */
    public static void updateOrderPayed (int orderId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE orders SET orders.orderStatus = ? WHERE orderId = ?";

        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){

                ps.setString(1, "payed");
                ps.setInt(2, orderId);
                ps.execute();
            }
        } catch(SQLException e){
            throw new DatabaseException("Failed to update paid in database");
        }
    }

    /**
     * This method will iterate through the hashmap which contains the materials needed for the carport. It will then be
     * put into the DB.
     * @param itemList The Hashmap of the materials. The key is the object of the material, while the value is the amount
     * @param orderId Is the ID for the order itself.
     * @param connectionPool Is required for establishing connection to the DB.
     * @throws DatabaseException is thrown if there isn't a connection to the database or if the data in the database is invalid.
     */
    public static void addItemlistToDB(Map<Material, Integer> itemList, int orderId, ConnectionPool connectionPool) throws DatabaseException {

        String SQL = "INSERT INTO itemList (amount, orderId, materialVariantId, partFor) VALUES (?, ?, ?, ?)";
        try(Connection connection = connectionPool.getConnection()){
            Iterator it = itemList.entrySet().iterator();
                while (it.hasNext()){
                    try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                        Map.Entry pair = (Map.Entry)it.next();

                        ps.setInt(1, (Integer) pair.getValue());
                        ps.setInt(2, orderId);
                        Material material = (Material) pair.getKey();
                        ps.setInt(3, material.getMaterialVariantID());
                        ps.setString(4, "carport");
                        ps.execute();

                }
            }
        }
        catch (SQLException ex){
            throw new DatabaseException(ex, "Error creating order in the Database");
        }
    }

    public static void deleteOrder(int orderId, ConnectionPool connectionPool) throws DatabaseException{
        String SQL = "DELETE FROM orders WHERE orderId = ?";

        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(SQL)){
                ps.setInt(1, orderId);
             ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
