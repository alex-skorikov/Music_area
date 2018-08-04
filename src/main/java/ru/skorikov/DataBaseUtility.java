package ru.skorikov;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

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

            ds.setDriverClassName("org.postgresql.Driver");
            ds.setUrl("jdbc:postgresql://ec2-54-217-205-90.eu-west-1.compute.amazonaws.com:5432/ddi2ocre4imcf5?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
            ds.setUsername("guaumlbhoxgeac");
            ds.setPassword("b442a1e669831fb2e499fc936bb97973149e54edbde3780ece73e1ad193e5a8a");

            ds.setMinIdle(4);
            ds.setMaxIdle(16);
            ds.setMaxOpenPreparedStatements(64);
            ds.setValidationQuery("select 1");
            basicDataSource = ds;
        }
        return basicDataSource;
    }
}
