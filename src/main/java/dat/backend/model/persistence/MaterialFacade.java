package dat.backend.model.persistence;

import dat.backend.model.entities.Post;
import dat.backend.model.entities.Purlin;
import dat.backend.model.entities.Rafter;
import dat.backend.model.exceptions.DatabaseException;

import java.util.List;

public class MaterialFacade {

    public static List<Post> getAllPosts(ConnectionPool connectionPool) throws DatabaseException {
        return MaterialMapper.getAllPosts(connectionPool);
    }

    public static List<Rafter> getAllRafters(ConnectionPool connectionPool) throws DatabaseException{
        return MaterialMapper.getAllRafters(connectionPool);
    }

    public static List<Purlin> getAllPurlins(ConnectionPool connectionPool) throws DatabaseException{
        return MaterialMapper.getAllPurlins(connectionPool);
    }


}
