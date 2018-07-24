package ru.skorikov.persistent.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ru.skorikov.DataBaseUtility;
import ru.skorikov.UserMusic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * User - Music store.
 */
public class UserMusicStore implements EntityDAO<UserMusic> {
    /**
     * SQL Add user-musictype.
     */
    private static final String ADD_USER_MUSICTYPE_SQL = "INSERT INTO user_musictype (user_id, music_type_id) VALUES (?,?);";
    /**
     * SQL update user-music type.
     */
    private static final String UPDATE_USER_MUSICTYPE_SQL = "UPDATE user_musictype SET user_id =?, music_type_id =? WHERE (id = ?);";
    /**
     * SQL delete user-music type.
     */
    private static final String DELETE_USER_MUSICTYPE_SQL = "DELETE FROM user_musictype WHERE (user_id = ?);";
    /**
     * SQL get user-music type by ID.
     */
    private static final String GET_USER_MUSICTYPE_BY_ID = "SELECT id, users_id, musictype_id FROM user_musictype WHERE (id = ?);";
    /**
     * SQL get all roles.
     */
    private static final String GET_ALL_USERS_MUSICTYPE_SQL = "SELECT user_id, music_type_id FROM user_musictype;";
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UserMusicStore.class);
    /**
     * Utility.
     */
    private static BasicDataSource utility;
    /**
     * Instance this Store.
     */
    private static UserMusicStore instance = new UserMusicStore();

    /**
     * Get instance this Store.
     *
     * @return instance.
     */
    public static UserMusicStore getInstanse() {
        return instance;
    }

    /**
     * Constructor.
     */
    private UserMusicStore() {
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
    public boolean add(UserMusic userMusic) {
        boolean isAdded = false;
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_MUSICTYPE_SQL)) {
            preparedStatement.setInt(1, userMusic.getUserId());
            preparedStatement.setInt(2, userMusic.getMusicId());
            preparedStatement.executeUpdate();
            isAdded = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return isAdded;
    }

    @Override
    public boolean update(Integer id, UserMusic userMusic) {
        boolean isUpdate = false;
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_MUSICTYPE_SQL)) {
            preparedStatement.setInt(1, userMusic.getUserId());
            preparedStatement.setInt(2, userMusic.getMusicId());
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
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_MUSICTYPE_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            isDelete = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return isDelete;
    }

    @Override
    public UserMusic findById(Integer id) {
        UserMusic userMusic = new UserMusic();
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_MUSICTYPE_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userMusic.setUserId(resultSet.getInt("user_id"));
                    userMusic.setMusicId(resultSet.getInt("music_type_id"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return userMusic;
    }

    @Override
    public ArrayList<UserMusic> findAll() {
        ArrayList<UserMusic> list = new ArrayList<>();
        try (Connection connection = utility.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_USERS_MUSICTYPE_SQL)) {
            while (resultSet.next()) {
                UserMusic userMusic = new UserMusic();
                userMusic.setUserId(resultSet.getInt("user_id"));
                userMusic.setMusicId(resultSet.getInt("music_type_id"));
                list.add(userMusic);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }
}
