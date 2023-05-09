package dat.backend.model.persistence;

import dat.backend.model.entities.Material;
import dat.backend.model.entities.Post;
import dat.backend.model.entities.Purlin;
import dat.backend.model.entities.Rafter;
import dat.backend.model.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MaterialMapper {

    public static List<Post> getAllPosts(ConnectionPool connectionPool) throws DatabaseException {
        List<Post> allPosts = new ArrayList<>();
        String SQL = "SELECT * FROM fog.allMaterialsView WHERE buildFunction = stolpe";
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(SQL)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int materialID = rs.getInt("materialId");
                    int length = rs.getInt("length");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String type = rs.getString("type");
                    String buildFunction = rs.getString("buildFunction");
                    allPosts.add(new Post(materialID, description, type, buildFunction, price, length));
                }
            }
        } catch(SQLException e){
            throw new DatabaseException("Failed to retrieve post data.");
        }
        Comparator<Material> materialComparator = Comparator.comparing(Material::getDescription).thenComparing(Material::getLength);
        Collections.sort(allPosts, materialComparator);
        return allPosts;
    }

    public static List<Rafter> getAllRafters(ConnectionPool connectionPool) throws DatabaseException{
        List<Rafter> allRafters = new ArrayList<>();
        String SQL = "SELECT * FROM fog.allMaterialsView WHERE buildFunction = spær";
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(SQL)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int materialID = rs.getInt("materialId");
                    int length = rs.getInt("length");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String type = rs.getString("type");
                    String buildFunction = rs.getString("buildFunction");
                    allRafters.add(new Rafter(materialID, description, type, buildFunction, price, length));
                }
            }
        } catch(SQLException e){
            throw new DatabaseException("Failed to retrieve post data.");
        }
        Comparator<Material> materialComparator = Comparator.comparing(Material::getDescription).thenComparing(Material::getLength);
        Collections.sort(allRafters, materialComparator);
        return allRafters;
    }

    public static List<Purlin> getAllPurlins(ConnectionPool connectionPool) throws DatabaseException{
        List<Purlin> allPurlins = new ArrayList<>();
        String SQL = "SELECT * FROM fog.allMaterialsView WHERE buildFunction = rem";
        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(SQL)){
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int materialID = rs.getInt("materialId");
                    int length = rs.getInt("length");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String type = rs.getString("type");
                    String buildFunction = rs.getString("buildFunction");
                    allPurlins.add(new Purlin(materialID, description, type, buildFunction, price, length));
                }
            }
        } catch(SQLException e){
            throw new DatabaseException("Failed to retrieve post data.");
        }
        Comparator<Material> materialComparator = Comparator.comparing(Material::getDescription).thenComparing(Material::getLength);
        Collections.sort(allPurlins, materialComparator);
        return allPurlins;
    }
}
