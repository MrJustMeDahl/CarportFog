package dat.backend.model.persistence;

import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains all the methods used to retrieve and alter materials from the database.
 * All methods in this class is static.
 *
 * @author MrJustMeDahl
 */
public class MaterialMapper {

    /**
     * This method retrieves all the posts from the database.
     * The list is sorted by description and length in the end.
     *
     * @param connectionPool required to establish connection to the database.
     * @return List of Post objects
     * @throws DatabaseException is thrown if connection to database fails.
     * @author MrJustMeDahl
     */
    static List<Post> getAllPosts(ConnectionPool connectionPool) throws DatabaseException {
        List<Post> allPosts = new ArrayList<>();
        String SQL = "SELECT * FROM allMaterialsView WHERE buildFunction = 'stolpe'";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int materialID = rs.getInt("materialId");
                    int materialVariantID = rs.getInt("materialVariantId");
                    int length = rs.getInt("length");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String type = rs.getString("type");
                    String buildFunction = rs.getString("buildFunction");
                    allPosts.add(new Post(materialID, materialVariantID, description, type, buildFunction, price, length));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to retrieve post data.");
        }
        Comparator<Material> materialComparator = Comparator.comparing(Material::getDescription).thenComparing(Material::getLength);
        Collections.sort(allPosts, materialComparator);
        return allPosts;
    }

    /**
     * This method retrieves all the rafters from the database.
     * The list is sorted by description and length in the end.
     *
     * @param connectionPool required to establish connection to the database.
     * @return List of Rafter objects
     * @throws DatabaseException is thrown if connection to database fails.
     * @author MrJustMeDahl
     */
    static List<Rafter> getAllRafters(ConnectionPool connectionPool) throws DatabaseException {
        List<Rafter> allRafters = new ArrayList<>();
        String SQL = "SELECT * FROM allMaterialsView WHERE buildFunction = 'spær'";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int materialID = rs.getInt("materialId");
                    int materialVariantID = rs.getInt("materialVariantId");
                    int length = rs.getInt("length");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String type = rs.getString("type");
                    String buildFunction = rs.getString("buildFunction");
                    allRafters.add(new Rafter(materialID, materialVariantID, description, type, buildFunction, price, length));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to retrieve post data.");
        }
        Comparator<Material> materialComparator = Comparator.comparing(Material::getDescription).thenComparing(Material::getLength);
        Collections.sort(allRafters, materialComparator);
        return allRafters;
    }

    /**
     * This method retrieves all the purlins from the database.
     * The list is sorted by description and length in the end.
     *
     * @param connectionPool required to establish connection to the database.
     * @return List of Purlin objects
     * @throws DatabaseException is thrown if connection to database fails.
     * @author MrJustMeDahl
     */
    static List<Purlin> getAllPurlins(ConnectionPool connectionPool) throws DatabaseException {
        List<Purlin> allPurlins = new ArrayList<>();
        String SQL = "SELECT * FROM allMaterialsView WHERE buildFunction = 'rem'";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int materialID = rs.getInt("materialId");
                    int materialVariantID = rs.getInt("materialVariantId");
                    int length = rs.getInt("length");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String type = rs.getString("type");
                    String buildFunction = rs.getString("buildFunction");
                    allPurlins.add(new Purlin(materialID, materialVariantID, description, type, buildFunction, price, length));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to retrieve post data.");
        }
        Comparator<Material> materialComparator = Comparator.comparing(Material::getDescription).thenComparing(Material::getLength);
        Collections.sort(allPurlins, materialComparator);
        return allPurlins;
    }

    /**
     * This method retrieves a list of all material types from the database.
     *
     * @param connectionPool required to establish connection to the database.
     * @return List of strings with material types.
     * @throws DatabaseException Is thrown if any SQL exception is thrown.
     * @author MrJustMeDahl
     */
    static List<String> getAllMaterialTypes(ConnectionPool connectionPool) throws DatabaseException {
        List<String> allTypes = new ArrayList<>();
        String SQL = "SELECT * FROM materialType";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    allTypes.add(rs.getString("description"));
                }
                return allTypes;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to retrieve material types from the database");
        }
    }

    /**
     * This method retrieves a list of all material build functions from the databse.
     *
     * @param connectionPool required to establish connection to the database.
     * @return List of strings with material build functions.
     * @throws DatabaseException Is thrown if any SQL exception is thrown.
     * @author MrJustMeDahl
     */
    static List<String> getAllMaterialFunctions(ConnectionPool connectionPool) throws DatabaseException {
        List<String> allFunctions = new ArrayList<>();
        String SQL = "SELECT * FROM materialBuildFunction";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    allFunctions.add(rs.getString("description"));
                }
                return allFunctions;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to retrieve material build functions from the database");
        }
    }

    /**
     * This method updates the material in DB, from updatematerial.jsp, with inputs from an admin
     *
     * @param materialId     the selected material in updatematerial.jsp
     * @param newPrice       changeable for admin via inputform
     * @param newDescription changeable for admin via inputform
     * @param connectionPool required to establish connection to the database.
     * @throws DatabaseException is thrown if connection to database fails.
     * @author CarstenJuhl
     */

    static boolean updateMaterial(int materialId, double newPrice, String newDescription, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE material SET price = ?, description = ?  WHERE materialId = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setDouble(1, newPrice);
                ps.setString(2, newDescription);
                ps.setInt(3, materialId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1) {
                    throw new DatabaseException("did not update");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to update material in database");
        }
        return true;
    }

    /**
     * This method creates a new materialtype in the DB, by filling out 4 forms, the admin can add a new item to the material DB
     *
     * @param description      The description of the product, this is added as a String, from a userinput
     * @param materialType     The Type of material, added as an int, via a dropdown from the user
     * @param materialFunction The Function this pruduct has, also saved as an int, also from a dropdown
     * @param price            The indicative price. Added as a double, from a userinput
     * @param connectionPool   required to establish connection to the database.
     * @return the newly created materialId
     * @throws DatabaseException is thrown if connection to database fails.
     * @author CarstenJuhl
     */

    static int createNewMaterial(String description, int materialType, int materialFunction, double price, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT into material (price, description, materialTypeId, materialBuildFunctionId) VALUES (?,?,?,?)";
        int newmaterialId = -1;
        try (Connection connection = connectionPool.getConnection()) {

            try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setDouble(1, price);
                ps.setString(2, description);
                ps.setInt(3, materialType);
                ps.setInt(4, materialFunction);
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                while (generatedKeys.next()) {
                    newmaterialId = generatedKeys.getInt(1);

                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create a new material");
        }
        return newmaterialId;
    }

    /**
     * The addLength method is part of creating a new materialvariant, after the type is created in the createNewMaterial method, the materialId is rturned, and used here to add a length and therefor
     * creates a new material
     *
     * @param materialId     is used to combine the new materialtype, with the new material. if no new material is created, then it takes the materialId from the existing material insted
     * @param length         the length of the new material, an input from the user, stored as an int.
     * @param connectionPool required to establish connection to the database.
     * @return the newly created materialvariantId
     * @throws DatabaseException
     */
    static int addLength(int materialId, int length, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT into materialVariant (length, materialId) VALUES (?,?)";
        int newMaterialVariantId = -1;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, length);
                ps.setInt(2, materialId);
                ps.executeUpdate();
                ResultSet materialVariantKey = ps.getGeneratedKeys();
                while (materialVariantKey.next()) {
                    newMaterialVariantId = materialVariantKey.getInt(1);
                }

            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create a new material");
        }
        return newMaterialVariantId;
    }

    /**
     * This method retrieves all the sheathings from the database.
     * The list is sorted by description and length in the end.
     *
     * @param connectionPool required to establish connection to the database.
     * @return List of Sheathing objects
     * @throws DatabaseException is thrown if connection to database fails.
     * @author MrJustMeDahl
     */
    static List<Sheathing> getAllSheathing(ConnectionPool connectionPool) throws DatabaseException{
        String SQL = "SELECT * FROM allMaterialsView WHERE buildFunction = 'bræddebeklædning'";
        List<Sheathing> allSheathings = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(SQL)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int materialID = rs.getInt("materialId");
                    int materialVariantID = rs.getInt("materialVariantId");
                    int length = rs.getInt("length");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String type = rs.getString("type");
                    String buildFunction = rs.getString("buildFunction");
                    allSheathings.add(new Sheathing(materialID, materialVariantID, description, type, buildFunction, price, length));
                }
            }
        } catch (SQLException e){
            throw new DatabaseException("Failed to retrieve sheathings from database");
        }
        Comparator<Material> materialComparator = Comparator.comparing(Material::getDescription).thenComparing(Material::getLength);
        Collections.sort(allSheathings, materialComparator);
        return allSheathings;
    }

    /**
     * This method retrieves all the roofs from the database.
     * The list is sorted by description and length in the end.
     *
     * @param connectionPool required to establish connection to the database.
     * @return List of Roof objects
     * @throws DatabaseException is thrown if connection to database fails.
     * @author MrJustMeDahl
     */
    static List<Roof> getAllRoofs(ConnectionPool connectionPool) throws DatabaseException{
        String SQL = "SELECT * FROM allMaterialsView WHERE buildFunction = 'tag'";
        List<Roof> allRoofs = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(SQL)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int materialID = rs.getInt("materialId");
                    int materialVariantID = rs.getInt("materialVariantId");
                    int length = rs.getInt("length");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String type = rs.getString("type");
                    String buildFunction = rs.getString("buildFunction");
                    allRoofs.add(new Roof(materialID, materialVariantID, description, type, buildFunction, price, length));
                }
            }
        } catch (SQLException e){
            throw new DatabaseException("Failed to retrieve roofs from database");
        }
        Comparator<Material> materialComparator = Comparator.comparing(Material::getDescription).thenComparing(Material::getLength);
        Collections.sort(allRoofs, materialComparator);
        return allRoofs;
    }
}
