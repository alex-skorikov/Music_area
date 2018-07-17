package ru.skorikov.persistent.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ru.skorikov.DataBaseUtility;
import ru.skorikov.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Roles store.
 */
public class RolesStore implements EntityDAO<Role> {
    /**
     * SQL add role.
     */
    private static final String ADD_ROLE_SQL = "INSERT INTO roles (role) VALUES (?);";
    /**
     * SQL update role.
     */
    private static final String UPDATE_ROLE_SQL = "UPDATE roles SET role =? WHERE (id = ?);";
    /**
     * SQL delete role.
     */
    private static final String DELETE_ROLE_SQL = "DELETE FROM roles WHERE (id = ?);";
    /**
     * SQL get role by ID.
     */
    private static final String GET_ROLE_BY_ID = "SELECT id, role FROM roles WHERE (id = ?);";
    /**
     * SQL get all roles.
     */
    private static final String GET_ALL_ROLES_SQL = "SELECT id, role FROM roles;";
    /**
     * SQL get role id.
     */
    private static final String GET_ROLE_ID_BY_ROLE_NAME_SQL = "SELECT id FROM roles WHERE (role = ?);";
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(RolesStore.class);
    /**
     * Utility.
     */
    private static BasicDataSource utility;
    /**
     * Instance this Store.
     */
    private static RolesStore instance = new RolesStore();

    /**
     * Get instance this Store.
     *
     * @return instance.
     */
    public static RolesStore getInstanse() {
        return instance;
    }

    /**
     * Constructor.
     */
    private RolesStore() {
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
    public boolean add(Role role) {
        boolean isAdded = false;
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_ROLE_SQL)) {
            preparedStatement.setString(1, role.getRole());
            preparedStatement.executeUpdate();
            isAdded = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return isAdded;
    }

    @Override
    public boolean update(Integer id, Role role) {
        boolean isUpdate = false;
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROLE_SQL)) {
            preparedStatement.setString(1, role.getRole());
            preparedStatement.setInt(2, id);
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
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            isDelete = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return isDelete;
    }

    @Override
    public Role findById(Integer id) {
        Role role = new Role();
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ROLE_BY_ID)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    role.setId(resultSet.getInt("id"));
                    role.setRole(resultSet.getString("role"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return role;
    }

    /**
     * Get all roles from DataBase.
     *
     * @return roles list.
     */
    @Override
    public CopyOnWriteArrayList<Role> findAll() {
        CopyOnWriteArrayList<Role> list = new CopyOnWriteArrayList<>();
        try (Connection connection = utility.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_ROLES_SQL)) {
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setRole(resultSet.getString("role"));
                list.add(role);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * Find role ID.
     *
     * @param roleName role.
     * @return role id.
     */
    public Integer findRoleIDByRoleName(String roleName) {
        Integer roleID = null;
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ROLE_ID_BY_ROLE_NAME_SQL)) {
            preparedStatement.setString(1, roleName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                roleID = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return roleID;
    }
}
