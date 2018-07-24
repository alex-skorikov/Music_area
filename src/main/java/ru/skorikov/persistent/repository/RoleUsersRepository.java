package ru.skorikov.persistent.repository;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ru.skorikov.DataBaseUtility;
import ru.skorikov.Entity;
import ru.skorikov.Role;
import ru.skorikov.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Role ropository.
 */
public class RoleUsersRepository implements EntityRepository<Role> {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(RoleUsersRepository.class);
    /**
     * Utility.
     */
    private static BasicDataSource utility;
    /**
     * Instance this Store.
     */
    private static RoleUsersRepository instance = new RoleUsersRepository();

    /**
     * Get instance this Store.
     *
     * @return instance.
     */
    public static RoleUsersRepository getInstanse() {
        return instance;
    }

    /**
     * Constructor.
     */
    private RoleUsersRepository() {
        utility = DataBaseUtility.getBasicDataSource();
    }

    /**
     * Set basic datasourse utility.
     * @param utility utility.
     */
    public void setUtility(BasicDataSource utility) {
        RoleUsersRepository.utility = utility;
    }

    /**
     * SQL get all users by role.
     */
    private static final String GET_ALL_USERS_BY_ROLE = "select id, user_name, user_login, user_email, create_date, user_password "
            + "from users where user_role = ?";

    @Override
    public ConcurrentHashMap<String, ArrayList<Entity>> getAllEntity(Role role) {

        ArrayList<Entity> linkedList = new ArrayList<>();
        ConcurrentHashMap<String, ArrayList<Entity>>  map = new ConcurrentHashMap<>();
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS_BY_ROLE)) {
            preparedStatement.setInt(1, role.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("user_name"));
                    user.setLogin(resultSet.getString("user_login"));
                    user.setEmail(resultSet.getString("user_email"));
                    user.setCreateDate(resultSet.getDate("create_date"));
                    user.setPassword(resultSet.getString("user_password"));
                    linkedList.add(user);
                }
                map.put("Users", linkedList);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return map;
    }
}
