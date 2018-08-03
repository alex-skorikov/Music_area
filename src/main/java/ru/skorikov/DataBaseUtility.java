package ru.skorikov;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * Class DataBase Util.
 */

public class DataBaseUtility {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(DataBaseUtility.class);
    /**
     * Connection pool.
     */
    private static BasicDataSource basicDataSource;

    /**
     * Get connection pool.
     *
     * @return pool.
     */
    public static synchronized BasicDataSource getBasicDataSource() {
        if (basicDataSource == null) {
            BasicDataSource ds = new BasicDataSource();

            Properties properties = new Properties();
            properties.getProperty("src/main/resources/release/database.properties");

            ds.setDriverClassName(properties.getProperty("jdbc.drivers"));
            ds.setUrl(properties.getProperty("jdbc.url"));
            ds.setUsername(properties.getProperty("jdbc.username"));
            ds.setPassword(properties.getProperty("jdbc.password"));

            ds.setMinIdle(4);
            ds.setMaxIdle(16);
            ds.setMaxOpenPreparedStatements(64);

            ds.setValidationQuery("select 1");

            basicDataSource = ds;
        }
        return basicDataSource;
    }
}
