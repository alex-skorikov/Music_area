package ru.skorikov.persistent.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import ru.skorikov.Address;
import ru.skorikov.DataBaseUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Address Store.
 */
public class AddressStore implements EntityDAO<Address> {
    /**
     * SQL add address.
     */
    private static final String ADD_ADDRESS_SQL = "INSERT INTO address (country, sity, street) VALUES (?, ?, ?);";
    /**
     * SQL Add address and return address id.
     */
    private static final String ADD_ADDRESS_AND_GET_ID_SQL = "INSERT INTO address (country, sity, street) VALUES (?, ?, ?) returning id;";
    /**
     * SQL update address.
     */
    private static final String UPDATE_ADDRESS_SQL = "UPDATE address SET country =?, sity =?, street =? WHERE (id = ?);";
    /**
     * SQL delete address.
     */
    private static final String DELETE_ADDRESS_SQL = "DELETE FROM address WHERE (id = ?);";
    /**
     * SQL get address by ID.
     */
    private static final String GET_ADDRESS_BY_ID = "SELECT id, country, sity, street FROM address WHERE (id = ?);";
    /**
     * SQL get all addresses.
     */
    private static final String GET_ALL_ADDRESS_SQL = "SELECT id, country, sity, street FROM address;";
    /**
     * SQL Get all countryes.
     */
    private static final String GET_ALL_COUNTRIES = "SELECT DISTINCT country FROM address;";
    /**
     * SQL Get all sities.
     */
    private static final String GET_ALL_SITIES = "SELECT DISTINCT sity FROM address where country=?;";
    /**
     * SQL Get all streets.
     */
    private static final String GET_ALL_STREETS = "SELECT DISTINCT street FROM address where sity=?;";
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(AddressStore.class);
    /**
     * Utility.
     */
    private static BasicDataSource utility;
    /**
     * Instance this Store.
     */
    private static AddressStore instance = new AddressStore();

    /**
     * Get instance this Store.
     *
     * @return instance.
     */
    public static AddressStore getInstanse() {
        return instance;
    }

    /**
     * Constructor.
     */
    private AddressStore() {
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
    public boolean add(Address address) {
        boolean isAdded = false;
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_ADDRESS_SQL)) {
            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getSity());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.executeUpdate();
            isAdded = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return isAdded;
    }

    @Override
    public boolean update(Integer id, Address address) {
        boolean isUpdate = false;
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADDRESS_SQL)) {
            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getSity());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setInt(4, id);
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
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADDRESS_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            isDelete = true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return isDelete;
    }

    @Override
    public Address findById(Integer id) {
        Address address = new Address();
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ADDRESS_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    address.setId(resultSet.getInt("id"));
                    address.setCountry(resultSet.getString("country"));
                    address.setSity(resultSet.getString("sity"));
                    address.setStreet(resultSet.getString("street"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return address;
    }

    @Override
    public CopyOnWriteArrayList<Address> findAll() {
        CopyOnWriteArrayList<Address> list = new CopyOnWriteArrayList<>();
        try (Connection connection = utility.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_ADDRESS_SQL)) {
            while (resultSet.next()) {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setCountry(resultSet.getString("country"));
                address.setSity(resultSet.getString("sity"));
                address.setStreet(resultSet.getString("street"));
                list.add(address);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * Get all countries from DB.
     *
     * @return countries list.
     */
    public CopyOnWriteArrayList<String> getCountries() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try (Connection connection = utility.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_COUNTRIES)) {
            while (resultSet.next()) {
                list.add(resultSet.getString("country"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * Get all sities from DB.
     * @param country country.
     * @return sities list.
     */
    public CopyOnWriteArrayList<String> getSities(String country) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SITIES)) {
            preparedStatement.setString(1, country);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(resultSet.getString("sity"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * Get all streets from DB.
     * @param sity sity.
     * @return streets list.
     */
    public CopyOnWriteArrayList<String> getStreets(String sity) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_STREETS)) {
            preparedStatement.setString(1, sity);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(resultSet.getString("street"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return list;
    }

    /**
     * Add address and return ID.
     * @param address address.
     * @return ID.
     */
    public Integer addAddressAndGetID(Address address) {
        Integer id = null;
        try (Connection connection = utility.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_ADDRESS_AND_GET_ID_SQL)) {
            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getSity());
            preparedStatement.setString(3, address.getStreet());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return id;
    }
}