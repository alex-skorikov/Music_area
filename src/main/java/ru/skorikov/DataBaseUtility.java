package ru.skorikov;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
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
     * SQL create table Roles.
     */
    private static final String CREATE_TABLE_ROLES_SQL = "CREATE TABLE IF NOT EXISTS Roles"
            + "(id SERIAL PRIMARY KEY, role VARCHAR(32));";
    /**
     * Create Adress table.
     */
    private static final String CREATE_TABLE_ADDRESS_SQL = "CREATE TABLE IF NOT EXISTS Address "
            + "(id SERIAL PRIMARY KEY, country text, sity text, street text);";
    /**
     * Create MusicType table.
     */
    private static final String CREATE_TABLE_MUSIC_TYPE_SQL = "CREATE TABLE IF NOT EXISTS MusicType "
            + "(id SERIAL PRIMARY KEY, music_type text, description text);";
    /**
     * SQL create table Users.
     */
    private static final String CREATE_TABLE_USERS_SQL = "CREATE TABLE IF NOT EXISTS Users"
            + "(id SERIAL PRIMARY KEY, user_name text, user_login text, user_email text, create_date date,\n"
            + "            user_password text, user_address integer REFERENCES Address(id), user_role integer REFERENCES Roles(id));";
    /**
     * Create reference table User - Music type.
     */
    private static final String CREATE_TABLE_REFERENCE_USER_MUSICTYPE_SQL = "CREATE TABLE IF NOT EXISTS User_musicType (\n"
            + "    user_id serial, music_type_id serial, PRIMARY KEY(user_id, music_type_id), FOREIGN KEY (user_id)"
            + " REFERENCES Users(id), FOREIGN KEY (music_type_id) REFERENCES MusicType(id));";
    /**
     * Inser roles sql.
     */
    private static final String INSERT_ROLES_SQL = "INSERT INTO Roles(role) values ('USER'), ('ADMIN'),('MODERATOR') ON CONFLICT DO NOTHING;";
    /**
     * Insert music type sql.
     */
    private static final String INSERT_MUSIC_TYPE_SQL = "INSERT INTO MusicType(music_type, description) values ('Acid Jazz',''), ('Alternative',''), ('Ambient',''), ('Bass',''), \n"
            + "('Blues', ''), ('Breaks', ''),('Breakbeat', ''),('Chillout', ''),('Сlassic', ''), ('Classic Rock', ''),('Club', ''),\n"
            + "            ('Hip-Hop',''), ('Disco', ''), ('Downtempo', ''), ('Electronic', ''),('Pop', ''), \n"
            + "            ('Indie',''), ('Industrial',''), ('Jazz', ''), ('Latin',''), ('Psychadelic', ''), \n"
            + "            ('Rap', ''), ('R&B', ''), ('Reggae', ''), ('Tango', ''), ('Techno', ''), ('Trance', '') ON CONFLICT DO NOTHING;";
    /**
     * Insert first address.
     */
    private static final String INSERT_FIRST_ADDRESS_SQL = "INSERT INTO Address (country, sity, street) values "
            + "('Russia', 'Moscow', 'Kremlin-1') ON CONFLICT DO NOTHING ";
    /**
     * Inser first root user.
     */
    private static final String INSERT_FIRST_USER_SQL = "INSERT INTO Users"
            + "(user_name, user_login, user_email, create_date, user_password, user_address, user_role) \n"
            + "VALUES ('admin', 'admin', 'admin@admin', CURRENT_DATE, 'admin', 1, 2);";

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

            try {
                dataBaseInit(ds);
            } catch (LiquibaseException e) {
                LOGGER.error(e.getMessage(), e);
            }

        }
        return basicDataSource;
    }

    /**
     * Initialise DataBase.
     *
     * @param dataSource connection pool.
     * @exception LiquibaseException exception
     */
    private static void dataBaseInit(BasicDataSource dataSource) throws LiquibaseException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
/*            statement.execute(CREATE_TABLE_ROLES_SQL);
            statement.execute(CREATE_TABLE_ADDRESS_SQL);
            statement.execute(CREATE_TABLE_MUSIC_TYPE_SQL);
            statement.execute(CREATE_TABLE_USERS_SQL);
            statement.execute(CREATE_TABLE_REFERENCE_USER_MUSICTYPE_SQL);
            statement.execute(INSERT_ROLES_SQL);
            statement.execute(INSERT_MUSIC_TYPE_SQL);
            statement.execute(INSERT_FIRST_ADDRESS_SQL);
            statement.execute(INSERT_FIRST_USER_SQL);*/
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation((DatabaseConnection) dataSource.getConnection());

            Liquibase liquibase = new liquibase.Liquibase("src/main/resources/liquibase/db.changelog-master.xml", new ClassLoaderResourceAccessor(), database);

            liquibase.update(new Contexts(), new LabelExpression());

        } catch (SQLException e) {
            LOGGER.info("Don't initialize DataBase");
            LOGGER.error(e.getMessage(), e);
        }
    }
}
