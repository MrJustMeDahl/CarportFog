package dat.backend.persistence;

import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.OrderFacade;
import dat.backend.model.persistence.OrderMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderMapperTest {

    private final static String USER = System.getenv("USERNAME");
    private final static String PASSWORD = System.getenv("PASSWORD");
    private final static String URL = "jdbc:mysql://134.122.87.83:3306/fog_test?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";

    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {
        connectionPool =  new ConnectionPool(USER, PASSWORD, URL);

        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Create test database - if not exist
                stmt.execute("CREATE DATABASE  IF NOT EXISTS fog_test;");

                // create test tables - if not exist
                stmt.execute("CREATE TABLE IF NOT EXISTS fog_test.user LIKE fog.user;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fog_test.orders LIKE fog.orders;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fog_test.material LIKE fog.material;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fog_test.materialBuildFunction LIKE fog.materialBuildFunction;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fog_test.materialType LIKE fog.materialType;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fog_test.materialVariant LIKE fog.materialVariant;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fog_test.itemList LIKE fog.itemList;");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }


    @BeforeEach
    void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Remove all data in test tables
                stmt.execute("TRUNCATE fog_test.user;");
                stmt.execute("TRUNCATE fog_test.material;");
                stmt.execute("TRUNCATE fog_test.materialType;");
                stmt.execute("TRUNCATE fog_test.materialVariant;");
                stmt.execute("TRUNCATE fog_test.materialBuildFunction;");
                stmt.execute("TRUNCATE fog_test.itemList;");
                stmt.execute("TRUNCATE fog_test.orders;");


                //Insert new test data
                stmt.execute("insert into fog_test.user (email, password, phoneNumber, address, FullName, role) " +
                        "values ('user@usersen.dk','1234','12345678','Danmarksgade 1','User Usersen','user'),('admin@adminsen.dk','1234','87654321','Danmarksgade 2','Admin Adminsen','admin'), ('test@testesen.dk','1234','14725836','Danmarksgade 3','Test Testesen','user');");
                stmt.execute("INSERT into fog_test.materialType (description) VALUES ('træ'), ('metal'), ('plastik');");
                stmt.execute("INSERT INTO fog_test.materialBuildFunction (description) VALUES ('stolpe'), ('rem'), ('spær');");
                stmt.execute("INSERT INTO fog_test.material (price, description, materialTypeId, materialBuildFunctionId) VALUES " +
                        "(55, '97x97mm. trykimp.', 1, 1), (35, '45x195mm. spærtræ', 1, 2), (35, '45x195mm. spærtræ', 1, 3);");
                stmt.execute("INSERT into fog_test.materialVariant (length, materialId) VALUES (330, 1), (420, 2), (360, 3);");
                stmt.execute("INSERT INTO fog_test.orders (price, indicativePrice, orderStatus, userId, carportLength, carportWidth, carportMinHeight, carportPrice, carportIndicativePrice) VALUES " +
                        "(1000, 1500, 'pending', 1, 500, 300, 210, 1000, 1500), (800, 1350, 'paid', 2, 400, 250, 180, 800, 1500), (0, 200, 'ordered', 1, 800, 250, 210, 0, 200);");
                stmt.execute("INSERT INTO fog_test.itemList (amount, orderId, materialVariantId, partFor) VALUES (4, 1, 1, 'carport'), (10, 1, 2, 'carport'), (8, 1, 3, 'carport'), " +
                        "(4, 2, 1, 'carport'), (2, 2, 2, 'carport'), (30, 2, 3, 'carport'), (8, 3, 1, 'carport')");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testConnection() throws SQLException {
        Connection connection = connectionPool.getConnection();
        assertNotNull(connection);
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void getOrdersByUserID() throws DatabaseException {
        User user = new User(1, "user@usersen.dk", "1234", "User Usersen", 12345678, "Danmarksgade 1", "user", connectionPool);
        User admin = new User(2, "admin@adminsen.dk", "1234", "Admin Adminsen", 87654321, "Danmarksgade 2", "admin", connectionPool);
        assertEquals(1, user.getOrders().get(0).getUserID());
        assertEquals(2, admin.getOrders().get(0).getUserID());
        assertEquals(1, user.getOrders().get(1).getUserID());

        assertEquals(1500, user.getOrders().get(0).getIndicativePrice());
        assertEquals(800, admin.getOrders().get(0).getPrice());
        Map<Material, Integer> materials = new HashMap<>();
        materials.put(new Post(-1, -1, "97x97mm. trykimp.", "træ", "stolpe", 55, 330), 4);
        Carport carport = new Carport(materials, 1000, 1500, 300, 500, 210);
        assertEquals(carport.getLength(), user.getOrders().get(0).getCarport().getLength());
        assertEquals(330, user.getOrders().get(1).getItemList().getMaterials().get(0).getMaterial().getLength());
    }

    @Test
    void getItemListContentForOrder() throws DatabaseException{
        ItemList itemList = new ItemList(400, 350, 210, false);
        OrderMapper.getItemListContentForOrder(1, itemList, connectionPool);
        assertEquals(3, itemList.getMaterials().size());
        assertEquals(10, itemList.getMaterials().get(1).getAmount());
        assertEquals(3, itemList.getMaterials().get(2).getMaterial().getMaterialVariantID());
    }

    @Test
    void createOrder() {
        Map<Material, Integer> materials = new HashMap<>();
        materials.put(new Post(1, 1,"97x97mm. trykimp.", "træ", "stolpe", 55, 330), 4);
        Carport carport = new Carport(materials, 2000, 3000, 300, 500, 210);
        User user = new User(4, "user@usersen.dk", "1234", "User Usersen", 12345678, "Danmarksgade 1", "user", connectionPool);

        try (Connection testConnection = connectionPool.getConnection()) {
            try  {

                OrderFacade.createOrder(carport,user.getUserID(), 2000, 3000,null, connectionPool);
                List testlist = OrderFacade.getOrdersByUserID(user.getUserID(), connectionPool);
                Order testorder = (Order) testlist.get(0);

                assertEquals(carport.getPrice(), testorder.getCarport().getPrice());
                assertEquals(testorder.getIndicativePrice(), carport.getIndicativePrice());
                assertEquals(testorder.getOrderID(), testorder.getOrderID());
                assertEquals(testorder.getUserID(), user.getUserID());


            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void updateOrderOrdered() throws DatabaseException {

        User user = new User(1, "user@usersen.dk", "1234", "User Usersen", 12345678, "Danmarksgade 1", "user", connectionPool);
        
        List testlist = OrderFacade.getOrdersByUserID(user.getUserID(), connectionPool);
        Order testorder = (Order) testlist.get(0);

        assertEquals(testorder.getOrderStatus(), "pending");
        OrderFacade.updateOrderOrdered(testorder.getOrderID(), connectionPool);
        testlist = OrderFacade.getOrdersByUserID(user.getUserID(), connectionPool);
        testorder = (Order) testlist.get(0);
        assertEquals(testorder.getOrderStatus(), "ordered");

    }

    @Test
    void getNewOrders() throws DatabaseException{
        List<Order> newOrders = OrderMapper.getNewOrders(connectionPool);
        assertEquals(1, newOrders.size());
        assertEquals(1, newOrders.get(0).getCarport().getMaterials().size());
        assertEquals(1, newOrders.get(0).getUserID());
    }

    @Test
    void deleteOrder() throws DatabaseException {
        assertTrue(OrderFacade.deleteOrder(1, connectionPool));
        assertFalse(OrderFacade.deleteOrder(1, connectionPool));
    }
    @Test
    void addItemlistToDB() throws DatabaseException{

        int orderId = 1;
        Post pole1 = new Post(1, 1, "'97x97mm. trykimp.'", "træ", "stolpe", 50, 360);
        Purlin purlin1 = new Purlin(3, 3, "'45x195mm. spærtræ'", "træ", "spær", 38, 300);
        Rafter rafter1 = new Rafter(2, 2, "'45x195mm. spærtræ'", "træ", "rem", 40, 300);

        ItemList itemList = new ItemList(500, 300, 210, false);
        itemList.addMaterialToItemList(new ItemListMaterial(pole1, 1, "stolpe", "carport"));
        itemList.addMaterialToItemList(new ItemListMaterial(purlin1, 1, "rem", "carport"));
        itemList.addMaterialToItemList(new ItemListMaterial(rafter1, 1, "spær", "carport"));

        Carport carport = new Carport(itemList.getMaterialsForCarport(), 2000, 3000, 300, 500, 210);

        OrderFacade.addItemlistToDB(itemList, orderId, connectionPool);
        List testlist = OrderFacade.getOrdersByUserID(1, connectionPool);
        Order testorder = (Order) testlist.get(0);

        assertEquals(carport.getMaterials().size() + 3, testorder.getCarport().getMaterials().size());

    }
}
