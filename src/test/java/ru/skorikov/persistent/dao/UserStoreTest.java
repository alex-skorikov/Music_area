package ru.skorikov.persistent.dao;

import org.junit.Test;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.Address;
import ru.skorikov.Role;
import ru.skorikov.User;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * UserStore class test.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserStoreTest {
    /**
     * Instance user store.
     */
    private UserStore userStore = UserStore.getInstance();
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
     * Init user store store.
     *
     * @throws SQLException exception.
     */
    @Before
    public void init() throws SQLException {
        userStore.setUtility(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any(String.class))).thenReturn(resultSet);
    }

    /**
     * Test add user.
     */
    @Test
    public void add() {
        User user = new User();
        user.setName("");
        user.setLogin("");
        user.setId(1);
        user.setPassword("");
        Address address = new Address();
        address.setId(1);
        Role role = new Role();
        role.setId(1);
        user.setRole(role);
        user.setAddress(address);
        Assert.assertTrue(userStore.add(user));
    }

    /**
     * Test update user.
     */
    @Test
    public void update() {
        User user = new User();
        user.setName("");
        user.setLogin("");
        user.setId(1);
        user.setPassword("");
        Address address = new Address();
        address.setId(1);
        Role role = new Role();
        role.setId(1);
        user.setRole(role);
        user.setAddress(address);
        Assert.assertTrue(userStore.update(1, user));
    }

    /**
     * Test delete user.
     */
    @Test
    public void delete() {
        Assert.assertTrue(userStore.delete(1));
    }

    /**
     * Test find user by id.
     *
     * @throws SQLException exception.
     */
    @Test
    public void findById() throws SQLException {
        User user = new User();
        user.setName("");
        user.setLogin("");
        user.setId(1);
        user.setPassword("");

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("user_name")).thenReturn("");
        when(resultSet.getString("user_login")).thenReturn("");
        when(resultSet.getString("user_password")).thenReturn("");

        Assert.assertEquals(user, userStore.findById(1));
    }

    /**
     * Test find all users.
     *
     * @throws SQLException exception
     */
    @Test
    public void findAll() throws SQLException {

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("user_name")).thenReturn("");
        when(resultSet.getString("user_login")).thenReturn("");
        when(resultSet.getString("user_password")).thenReturn("");

        Assert.assertThat(userStore.findAll().size(), is(1));

    }
}