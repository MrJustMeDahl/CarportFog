package dat.backend.model.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionPool
{
    // TODO: Change access credentials for MySql server as needed below:

    private HikariDataSource ds;
    private static String USER = System.getenv("USERNAME");
    private static String PASSWORD = System.getenv("PASSWORD");
    private static String URL = "jdbc:mysql://134.122.87.83:3306/fog";

    public ConnectionPool()
    {
        this(USER, PASSWORD, URL);
    }

    public ConnectionPool(String USER, String PASSWORD, String URL)
    {
        String deployed = System.getenv("DEPLOYED");
        if (deployed != null)
        {
            // Prod: hent variabler fra setenv.sh i Tomcats bin folder
            USER = System.getenv("USERNAME");
            PASSWORD = System.getenv("PASSWORD");
            URL = System.getenv("JDBC_CONNECTION_STRING");
        }

        Logger.getLogger("web").log(Level.INFO,
                String.format("Connection Pool created for: (%s, %s, %s)", USER, PASSWORD, URL));
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(5);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        this.ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException
    {
        Logger.getLogger("web").log(Level.INFO, ": get data connection");
        return ds.getConnection();
    }

    public void close()
    {
        Logger.getLogger("web").log(Level.INFO, "Shutting down connection pool");
        ds.close();
    }

}
