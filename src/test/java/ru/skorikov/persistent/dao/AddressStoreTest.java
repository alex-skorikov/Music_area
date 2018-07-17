package ru.skorikov.persistent.dao;

import org.junit.Test;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.Address;


import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


/**
 * Sddress store class test.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddressStoreTest {
    /**
     * Instance address store.
     */
    private AddressStore addressStore = AddressStore.getInstanse();
    /**
     * Datasourse for test.
     */
    private BasicDataSource dataSource = Mockito.mock(BasicDataSource.class);
    /**
     * Connection mock for test.
     */
    private Connection connection = Mockito.mock(Connection.class);
    /**
     * Preparestatment for test.
     */
    private PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
    /**
     * Statment for test.
     */
    private Statement statement = Mockito.mock(Statement.class);
    /**
     * Resyltset for test.
     */
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    /**
     * Init address store.
     *
     * @throws SQLException exception.
     */
    @Before
    public void init() throws SQLException {
        addressStore.setUtility(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any(String.class))).thenReturn(resultSet);
    }

    /**
     * Test add address.
     */
    @Test
    public void add() {
        Address address = new Address();
        address.setCountry("");
        address.setSity("");
        address.setStreet("");
        Assert.assertTrue(addressStore.add(address));
    }

    /**
     * Test update address.
     */
    @Test
    public void update() {
        Address address = new Address();
        address.setCountry("");
        address.setSity("");
        address.setStreet("");
        Assert.assertTrue(addressStore.update(1, address));
    }

    /**
     * Test delete address.
     */
    @Test
    public void delete() {
        Assert.assertTrue(addressStore.delete(1));
    }

    /**
     * Test find address by id.
     *
     * @throws SQLException exception.
     */
    @Test
    public void findById() throws SQLException {
        Address address = new Address();
        address.setId(1);
        address.setCountry("country");
        address.setSity("sity");
        address.setStreet("street");

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("country")).thenReturn("country");
        when(resultSet.getString("sity")).thenReturn("sity");
        when(resultSet.getString("street")).thenReturn("street");

        Assert.assertEquals(address, addressStore.findById(1));
    }

    /**
     * Test find all adresses.
     *
     * @throws SQLException exception
     */
    @Test
    public void findAll() throws SQLException {

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("country")).thenReturn("country");
        when(resultSet.getString("sity")).thenReturn("sity");
        when(resultSet.getString("street")).thenReturn("street");

        Assert.assertThat(addressStore.findAll().size(), is(1));

    }

    /**
     * Test get countries from adressstore.
     *
     * @throws SQLException exception
     */
    @Test
    public void getCountries() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, true, false);
        Assert.assertThat(addressStore.getCountries().size(), is(3));
    }

    /**
     * Test get all sities from country.
     * @throws SQLException exception.
     */
    @Test
    public void getSities() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, true, false);
        Assert.assertThat(addressStore.getSities("country").size(), is(3));
    }

    /**
     * Test get all streets from sity.
     * @throws SQLException exception.
     */
    @Test
    public void getStreets() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, true, false);
        Assert.assertThat(addressStore.getStreets("sity").size(), is(3));
    }

    /**
     * Test add address ang return id.
     * @throws SQLException exception
     */
    @Test
    public void addAddressAndGetID() throws SQLException {
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt(any(Integer.class))).thenReturn(1);

        Assert.assertThat(addressStore.addAddressAndGetID(new Address()), is(1));
    }
    /**
     * Test SQL exception.
     * @throws SQLException exception
     */
    @Test
    public void tryTestSQLException() throws SQLException {

        when(dataSource.getConnection()).thenThrow(SQLException.class);

        Assert.assertFalse(addressStore.add(new Address()));
        Assert.assertFalse(addressStore.delete(1));
        Assert.assertFalse(addressStore.update(1, new Address()));
        Assert.assertNull(addressStore.findById(1).getId());
        Assert.assertThat(addressStore.findAll().size(), is(0));
        Assert.assertThat(addressStore.getCountries().size(), is(0));
        Assert.assertThat(addressStore.getSities("").size(), is(0));
        Assert.assertThat(addressStore.getStreets("").size(), is(0));
        Assert.assertNull(addressStore.addAddressAndGetID(new Address()));

    }
    /**
     * Test SQL exception.
     * @throws SQLException exception
     */
    @Test
    public void tryTestSQLException2() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenThrow(SQLException.class);

        Assert.assertFalse(addressStore.add(new Address()));
        Assert.assertFalse(addressStore.delete(1));
        Assert.assertFalse(addressStore.update(1, new Address()));
        Assert.assertNull(addressStore.findById(1).getId());
        Assert.assertThat(addressStore.findAll().size(), is(0));
        Assert.assertThat(addressStore.getCountries().size(), is(0));
        Assert.assertThat(addressStore.getSities("").size(), is(0));
        Assert.assertThat(addressStore.getStreets("").size(), is(0));
        Assert.assertNull(addressStore.addAddressAndGetID(new Address()));

    }

}