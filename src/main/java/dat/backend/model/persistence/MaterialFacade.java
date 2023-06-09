package dat.backend.model.persistence;

import dat.backend.model.entities.*;
import dat.backend.model.exceptions.DatabaseException;

import java.util.List;

/**
 * This class functions as a facade for MaterialMapper.java for the sake of simplicity.
 * The MaterialMapper class contains all the methods used to retrieve and alter materials from the database.
 * All methods in this class is static.
 *
 * @author MrJustMeDahl
 */
public class MaterialFacade {

    public static List<Post> getAllPosts(ConnectionPool connectionPool) throws DatabaseException {
        return MaterialMapper.getAllPosts(connectionPool);
    }

    public static List<Rafter> getAllRafters(ConnectionPool connectionPool) throws DatabaseException {
        return MaterialMapper.getAllRafters(connectionPool);
    }

    public static List<Purlin> getAllPurlins(ConnectionPool connectionPool) throws DatabaseException {
        return MaterialMapper.getAllPurlins(connectionPool);
    }

    public static List<String> getAllMaterialTypes(ConnectionPool connectionPool) throws DatabaseException {
        return MaterialMapper.getAllMaterialTypes(connectionPool);
    }

    public static List<String> getAllMaterialFunctions(ConnectionPool connectionPool) throws DatabaseException {
        return MaterialMapper.getAllMaterialFunctions(connectionPool);
    }

    public static boolean updateMaterial(int materialId, double newPrice, String newDescription, ConnectionPool connectionPool) throws DatabaseException {
        return MaterialMapper.updateMaterial(materialId, newPrice, newDescription, connectionPool);
    }

    public static int newMaterial(String description, int materialType, int materialFunction, double price, ConnectionPool connectionPool) throws DatabaseException {
        return MaterialMapper.createNewMaterial(description, materialType, materialFunction, price, connectionPool);
    }

    public static int addLength(int materialId, int length, ConnectionPool connectionPool) throws DatabaseException {
        return MaterialMapper.addLength(materialId, length, connectionPool);
    }

    public static List<Roof> getAllRoofs(ConnectionPool connectionPool) throws DatabaseException{
        return MaterialMapper.getAllRoofs(connectionPool);
    }

    public static List<Sheathing> getAllSheathing(ConnectionPool connectionPool) throws DatabaseException{
        return MaterialMapper.getAllSheathing(connectionPool);
    }
}
