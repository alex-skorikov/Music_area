package ru.skorikov.persistent.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ru.skorikov.DataBaseUtility;
import ru.skorikov.MusicType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Music Store.
 */
public class MusicTypeStore implements EntityDAO<MusicType> {

    /**
     * SQL add music type.
     */
    private static final String ADD_MUSIC_TYPE_SQL = "INSERT INTO musictype (music_type, description)\n"
            + "SELECT ?, ?\n"
            + "WHERE NOT EXISTS (SELECT id FROM musictype WHERE music_type = ?)";
    /**
     * SQL update musictype.
     */
    private static final String UPDATE_MUSIC_TYPE_SQL = "UPDATE musictype SET music_type =?, description =? WHERE (id = ?);";
    /**
     * SQl delete music type.
     */
    private static final String DELETE_MUSIC_TYPE_SQL = "DELETE FROM musictype WHERE (id = ?);";
    /**
     * SQL get music type by ID.
     */
    private static final String GET_MYSIC_TYPE_BY_ID = "SELECT id, music_type, description FROM musictype WHERE (id = ?);";
    /**
     * SQL get music type by type.
     */
    private static final String GET_MYSIC_BY_TYPE = "SELECT id, music_type, description FROM musictype WHERE (music_type = ?);";
    /**
     * SQL get all roles.
     */
    private static final String GET_ALL_MUSIC_TYPES_SQL = "SELECT id, music_type, description FROM musictype;";
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(MusicTypeStore.class);
    /**
     * Utility.
     */
    private static BasicDataSource utility;
    /**
     * Instance this Store.
     */
    private static MusicTypeStore instance = new MusicTypeStore();

    /**
     * Get instance this Store.
     *
     * @return instance.
     */
    public static MusicTypeStore getInstanse() {
        return instance;
    }

    /**
     * Constructor.
     */
    private MusicTypeStore() {
        utility = DataBaseUtility.getBasicDataSource();
    }

    /**
     * Set utility.
     *
     * @param utility utility.
     */
    public void setUtility(BasicDataSource utility) {
        this.utility = utility;
    }
    @Override
    public boolean add(MusicType musicType) {
        boolean isAdded = false;
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_MUSIC_TYPE_SQL)) {
            preparedStatement.setString(1, musicType.getType());
            preparedStatement.setString(2, musicType.getDescription());
            preparedStatement.setString(3, musicType.getType());
            preparedStatement.executeUpdate();
            isAdded = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return isAdded;
    }

    @Override
    public boolean update(Integer id, MusicType musicType) {
        boolean isUpdate = true;
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MUSIC_TYPE_SQL)) {
            preparedStatement.setString(1, musicType.getType());
            preparedStatement.setString(2, musicType.getDescription());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            isUpdate = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return isUpdate;
    }

    @Override
    public boolean delete(Integer id) {
        boolean isDelete = false;
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MUSIC_TYPE_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            isDelete = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return isDelete;
    }

    @Override
    public MusicType findById(Integer id) {
        MusicType musicType = new MusicType();
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_MYSIC_TYPE_BY_ID)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    musicType.setId(resultSet.getInt("id"));
                    musicType.setType(resultSet.getString("music_type"));
                    musicType.setDescription(resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return musicType;
    }

    @Override
    public CopyOnWriteArrayList<MusicType> findAll() {
        CopyOnWriteArrayList<MusicType> list = new CopyOnWriteArrayList<>();
        try (Connection connection = utility.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_MUSIC_TYPES_SQL)) {
            while (resultSet.next()) {
                MusicType musicType = new MusicType();
                musicType.setId(resultSet.getInt("id"));
                musicType.setType(resultSet.getString("music_type"));
                musicType.setDescription(resultSet.getString("description"));
                list.add(musicType);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * Get music by type.
     * @param type type.
     * @return MusicType.
     */
    public MusicType findByType(String type) {
        MusicType musicType = new MusicType();
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_MYSIC_BY_TYPE)) {
            preparedStatement.setString(1, type);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    musicType.setId(resultSet.getInt("id"));
                    musicType.setType(resultSet.getString("music_type"));
                    musicType.setDescription(resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return musicType;
    }
}
