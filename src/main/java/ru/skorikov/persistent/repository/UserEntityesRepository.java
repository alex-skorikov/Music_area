package ru.skorikov.persistent.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ru.skorikov.User;
import ru.skorikov.DataBaseUtility;
import ru.skorikov.MusicType;
import ru.skorikov.Role;
import ru.skorikov.Address;
import ru.skorikov.Entity;
import ru.skorikov.UserMusic;
import ru.skorikov.persistent.dao.AddressStore;
import ru.skorikov.persistent.dao.RolesStore;
import ru.skorikov.persistent.dao.UserMusicStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Users repository.
 */
public class UserEntityesRepository implements EntityRepository<User> {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(UserEntityesRepository.class);
    /**
     * Utility.
     */
    private static BasicDataSource utility;
    /**
     * Instance this Store.
     */
    private static UserEntityesRepository instance = new UserEntityesRepository();

    /**
     * Get instance this Store.
     *
     * @return instance.
     */
    public static UserEntityesRepository getInstanse() {
        return instance;
    }

    /**
     * AddressStore for simple select.
     */
    private static AddressStore addressStore;
    /**
     * RoleStore for simple select.
     */
    private static RolesStore rolesStore;
    /**
     * User music store for simple select.
     */
    private static UserMusicStore userMusicStore;

    /**
     * Constructor.
     */
    private UserEntityesRepository() {
        utility = DataBaseUtility.getBasicDataSource();
        addressStore = AddressStore.getInstanse();
        rolesStore = RolesStore.getInstanse();
        userMusicStore = UserMusicStore.getInstanse();
    }

    /**
     * Set basicdatasourse.
     * @param utility utility.
     */
    public void setUtility(BasicDataSource utility) {
        UserEntityesRepository.utility = utility;
    }

    /**
     * Set address store.
     * @param addressStore store.
     */
    public void setAddressStore(AddressStore addressStore) {
        UserEntityesRepository.addressStore = addressStore;
    }

    /**
     * Set role store.
     * @param rolesStore store.
     */
    public void setRolesStore(RolesStore rolesStore) {
        UserEntityesRepository.rolesStore = rolesStore;
    }

    /**
     * Set music store.
     * @param userMusicStore store.
     */
    public void setUserMusicStore(UserMusicStore userMusicStore) {
        UserEntityesRepository.userMusicStore = userMusicStore;
    }

    /**
     * Get users musictype SQL.
     */
    private static final String GET_USERS_MUSICTYPES = "SELECT musictype.id, music_type, musictype.description \n"
            + "FROM (users LEFT JOIN (musictype join user_musictype on musictype.id = user_musictype.music_type_id) "
            + "on users.id = user_musictype.user_id) WHERE users.id = ?;";
    /**
     * Get all users by address.
     */
    private static final String GET_ALL_USERS_BY_ADDRESS = "SELECT users.id, users.user_name, users.user_login, \n"
            + "  users.user_email, users.create_date, users.user_password, users.user_address, users.user_role\n"
            + "FROM users WHERE user_address = ?;";
    /**
     * Get all users by role SQL.
     */
    private static final String GET_ALL_USERS_BY_ROLE = "SELECT users.id, users.user_name, users.user_login, users.user_email, \n"
            + "  users.create_date, users.user_password, users.user_address, users.user_role\n"
            + "FROM users WHERE users.user_role = ?;";
    /**
     * Get all users by musictype SQL.
     */
    private static final String GET_ALL_USERS_BY_MUSICTYPE = "SELECT users.id, users.user_name, users.user_login, users.user_email, "
            + "users.create_date, users.user_password, users.user_address, users.user_role FROM users, musictype, user_musictype\n"
            + "WHERE users.id = user_musictype.user_id AND user_musictype.music_type_id = musictype.id and musictype.id = ?;";

    @Override
    public ConcurrentHashMap<String, ArrayList<Entity>> getAllEntity(User user) {

        ArrayList<Entity> musiclist = new ArrayList<>();
        ConcurrentHashMap<String, ArrayList<Entity>> map = new ConcurrentHashMap<>();

        ArrayList<Entity> addressList = new ArrayList<>();
        addressList.add(addressStore.findById(user.getAddress().getId()));

        ArrayList<Entity> roleList = new ArrayList<>();
        roleList.add(rolesStore.findById(user.getRole().getId()));

        map.put("Address", addressList);
        map.put("Role", roleList);

        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_MUSICTYPES)) {
            preparedStatement.setInt(1, user.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    MusicType musicType = new MusicType();
                    musicType.setId(resultSet.getInt("id"));
                    musicType.setType(resultSet.getString("music_type"));
                    musicType.setDescription(resultSet.getString("description"));
                    musiclist.add(musicType);
                }
                map.put("Music_types", musiclist);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return map;
    }

    /**
     * Add User to DB and all it's entityes.
     * @param user user.
     * @return isAdded.
     */
    public boolean addNewUserAndHisEntityes(User user) {
        boolean isAdded = false;
        boolean addAddress, addRole;
        addAddress = addressStore.add(user.getAddress());
        addRole = rolesStore.add(user.getRole());
        Iterator<MusicType> iterator = user.getMusicType().listIterator();
        while (iterator.hasNext()) {
            MusicType musicType = iterator.next();
            UserMusic userMusic = new UserMusic();
            userMusic.setUserId(user.getId());
            userMusic.setMusicId(musicType.getId());
            userMusicStore.add(userMusic);
        }
        if (addAddress && addRole) {
            isAdded = true;
        }
        return isAdded;
    }

    /**
     * Find users from DB by address.
     * @param address user address.
     * @return users list.
     */
    public ArrayList<User> findUserByAddress(Address address) {
        ArrayList<User> list = new ArrayList<>();
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS_BY_ADDRESS)) {
            preparedStatement.setInt(1, address.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(getUserFromResultset(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * Find users from DB by Role.
     * @param role user role.
     * @return users list.
     */
    public ArrayList<User> findUsersByRole(Role role) {
        ArrayList<User> list = new ArrayList<>();
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS_BY_ROLE)) {
            preparedStatement.setInt(1, role.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(getUserFromResultset(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * Find User list by music type.
     * @param musicType music type.
     * @return users list.
     */
    public ArrayList<User> findUsersByMusicType(MusicType musicType) {
        ArrayList<User> list = new ArrayList<>();
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS_BY_MUSICTYPE)) {
            preparedStatement.setInt(1, musicType.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(getUserFromResultset(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * Get User from database.
     * @param resultSet resultset.
     * @return user.
     */
    private User getUserFromResultset(ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("user_name"));
            user.setLogin(resultSet.getString("user_login"));
            user.setEmail(resultSet.getString("user_email"));
            user.setCreateDate(resultSet.getDate("create_date"));
            user.setPassword(resultSet.getString("user_password"));
            user.setAddress(addressStore.findById(resultSet.getInt("user_address")));
            user.setRole(rolesStore.findById(resultSet.getInt("user_role")));
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return user;
    }
}
