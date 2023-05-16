package dat.backend.persistence;

import dat.backend.model.entities.Post;
import dat.backend.model.entities.Purlin;
import dat.backend.model.entities.Rafter;
import dat.backend.model.exceptions.DatabaseException;
import dat.backend.model.persistence.ConnectionPool;
import dat.backend.model.persistence.MaterialFacade;
import dat.backend.model.persistence.MaterialMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialMapperTest {

    private final static String USER = System.getenv("USERNAME");
    private final static String PASSWORD = System.getenv("PASSWORD");
    private final static String URL = "jdbc:mysql://134.122.87.83:3306/fog_test?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";

    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);

        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement()) {
                // Create test database - if not exist
                stmt.execute("CREATE DATABASE  IF NOT EXISTS fog_test;");

                // create test tables - if not exist
                stmt.execute("CREATE TABLE IF NOT EXISTS fog_test.material LIKE fog.material;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fog_test.materialBuildFunction LIKE fog.materialBuildFunction;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fog_test.materialType LIKE fog.materialType;");
                stmt.execute("CREATE TABLE IF NOT EXISTS fog_test.materialVariant LIKE fog.materialVariant;");
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
                stmt.execute("TRUNCATE fog_test.material;");
                stmt.execute("TRUNCATE fog_test.materialType;");
                stmt.execute("TRUNCATE fog_test.materialVariant;");
                stmt.execute("TRUNCATE fog_test.materialBuildFunction;");


                //Insert new test data
                stmt.execute("INSERT into fog_test.materialType (description) VALUES ('træ'), ('metal'), ('plastik');");
                stmt.execute("INSERT INTO fog_test.materialBuildFunction (description) VALUES ('stolpe'), ('rem'), ('spær');");
                stmt.execute("INSERT INTO fog_test.material (price, description, materialTypeId, materialBuildFunctionId) VALUES " +
                        "(55, '97x97mm. trykimp.', 1, 1), (35, '45x195mm. spærtræ', 1, 2), (35, '45x195mm. spærtræ', 1, 3);");
                stmt.execute("INSERT into fog_test.materialVariant (length, materialId) VALUES (330, 1), (420, 2), (360, 3);");
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
    void getAllPosts() throws DatabaseException {
        List<Post> allPosts = MaterialMapper.getAllPosts(connectionPool);
        assertEquals("97x97mm. trykimp.", allPosts.get(0).getDescription());
        assertEquals("træ", allPosts.get(0).getType());
        assertEquals("stolpe", allPosts.get(0).getFunction());
    }

    @Test
    void getAllRafters() throws DatabaseException {
        List<Rafter> allRafters = MaterialMapper.getAllRafters(connectionPool);
        assertEquals("45x195mm. spærtræ", allRafters.get(0).getDescription());
        assertEquals("træ", allRafters.get(0).getType());
        assertEquals("spær", allRafters.get(0).getFunction());
    }

    @Test
    void getAllPurlins() throws DatabaseException {
        List<Purlin> allPurlins = MaterialMapper.getAllPurlins(connectionPool);
        assertEquals("45x195mm. spærtræ", allPurlins.get(0).getDescription());
        assertEquals("træ", allPurlins.get(0).getType());
        assertEquals("rem", allPurlins.get(0).getFunction());
    }

    @Test
    void getAllMaterialTypes() throws DatabaseException {
        List<String> allTypes = MaterialMapper.getAllMaterialTypes(connectionPool);
        assertEquals("træ", allTypes.get(0));
        assertEquals("metal", allTypes.get(1));
        assertEquals("plastik", allTypes.get(2));
    }

    @Test
    void getAllMaterialFunctions() throws DatabaseException {
        List<String> allFunctions = MaterialMapper.getAllMaterialFunctions(connectionPool);
        assertEquals("stolpe", allFunctions.get(0));
        assertEquals("rem", allFunctions.get(1));
        assertEquals("spær", allFunctions.get(2));
    }

    @Test
    void updateMaterial() throws DatabaseException {
        assertTrue(MaterialFacade.updateMaterial(1, 60, "100x100. tryk.Imp", connectionPool));
        assertThrows(DatabaseException.class, () -> MaterialFacade.updateMaterial(10, 50, "100x100", connectionPool));
    }

    @Test
    void createNewMaterial() throws DatabaseException {
        assertEquals(4, MaterialFacade.newMaterial("105x105", 1, 1, 50, connectionPool));
    }

    @Test
    void addLength() throws DatabaseException {
        assertEquals(4, MaterialFacade.addLength(1, 360, connectionPool));
        assertThrows(DatabaseException.class, () -> MaterialFacade.addLength(7, 360, connectionPool));
    }


}
