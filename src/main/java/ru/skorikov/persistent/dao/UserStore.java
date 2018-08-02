package ru.skorikov.persistent.dao;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ru.skorikov.Address;
import ru.skorikov.DataBaseUtility;
import ru.skorikov.Role;
import ru.skorikov.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * User Store.
 */
public class UserStore implements EntityDAO<User> {
    /**
     * SQL Add user to database.
     */
    private static final String ADD_USER_TO_DATABASE_SQL = "INSERT INTO Users"
            + "(user_name, user_login, user_email, create_date, user_password, user_address, user_role"
            + ") VALUES (?,?,?,?,?,?,?);";
    /**
     * SQL update user.
     */
    private static final String UPDATE_USER_FROM_DATABASE_SQL = "UPDATE Users SET "
            + "user_name=?, user_login=?, user_email=?, user_password=?, user_address=?, user_role=? "
            + "WHERE (id = ?);";
    /**
     * SQL delete user.
     */
    private static final String DELETE_USER_FROM_DATABASE_SQL = "DELETE FROM Users WHERE (id = ?);";
    /**
     * SQL find user by id.
     */
    private static final String FIND_USER_BY_ID_SQL = "SELECT id, user_name, user_login, user_email, "
            + "create_date, user_password, user_address, user_role FROM Users WHERE (id = ?);";
    /**
     * SQL find all users.
     */
    private static final String FIND_ALL_USER_SQL = "SELECT id, user_name, user_login, user_email, "
            + "create_date, user_password, user_address, user_role FROM Users ORDER BY id;";
    /**
     * SQL finde dublicate user.
     */
    private static final String FIND_DUBLICATE_LOGIN_USER_SQL = "SELECT user_login FROM Users WHERE(user_login = ?);";
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UserStore.class);
    /**
     * DB util.
     */
    private static BasicDataSource utility;
    /**
     * Instance User store.
     */
    private static UserStore instance = new UserStore();

    /**
     * Constructor.
     */
    public UserStore() {
        utility = DataBaseUtility.getBasicDataSource();
    }

    /**
     * Get user sstore instance.
     * @return insance user store.
     */
    public static UserStore getInstance() {
        return instance;
    }

    /**
     * Set utility for class.
     * @param utility utility.
     */
    public void setUtility(BasicDataSource utility) {
        this.utility = utility;
    }

    @Override
    public boolean add(User user) {
        boolean isAdded = false;
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_TO_DATABASE_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setDate(4, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getAddress().getId());
            preparedStatement.setInt(7, user.getRole().getId());
            preparedStatement.executeUpdate();
            isAdded = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return isAdded;
    }

    @Override
    public boolean update(Integer id, User user) {
        boolean isUpdate = false;
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_FROM_DATABASE_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getAddress().getId());
            preparedStatement.setInt(6, user.getRole().getId());
            preparedStatement.setInt(7, user.getId());
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
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_FROM_DATABASE_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            isDelete = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return isDelete;
    }

    @Override
    public User findById(Integer id) {
        User user = new User();
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("user_name"));
                    user.setLogin(resultSet.getString("user_login"));
                    user.setEmail(resultSet.getString("user_email"));
                    user.setCreateDate(resultSet.getDate("create_date"));
                    user.setPassword(resultSet.getString("user_password"));
                    Address address = new Address();
                    address.setId(resultSet.getInt("user_address"));
                    user.setAddress(address);
                    Role role = new Role();
                    role.setId(resultSet.getInt("user_role"));
                    user.setRole(role);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return user;
    }

    @Override
    public ArrayList<User> findAll() {
        ArrayList<User> list = new ArrayList<>();
        try (Connection connection = utility.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_USER_SQL)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("user_name"));
                user.setLogin(resultSet.getString("user_login"));
                user.setEmail(resultSet.getString("user_email"));
                user.setCreateDate(resultSet.getDate("create_date"));
                user.setPassword(resultSet.getString("user_password"));
                Address address = new Address();
                address.setId(resultSet.getInt("user_address"));
                user.setAddress(address);
                Role role = new Role();
                role.setId(resultSet.getInt("user_role"));
                user.setRole(role);
                list.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * Find dublicate User by login.
     * @param user user.
     * @return is dublicat.
     */
    private User findDublicateUser(User user) {
        User dublicate = new User();

        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_DUBLICATE_LOGIN_USER_SQL)) {
            preparedStatement.setString(1, user.getLogin());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    dublicate = user;
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return dublicate;
    }
}
