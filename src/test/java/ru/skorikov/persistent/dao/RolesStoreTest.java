package ru.skorikov.persistent.dao;

import org.junit.Test;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.Role;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Role store class test.
 */
@RunWith(MockitoJUnitRunner.class)
public class RolesStoreTest {
    /**
     * Instance role store.
     */
    private RolesStore rolesStore = RolesStore.getInstanse();
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
        rolesStore.setUtility(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any(String.class))).thenReturn(resultSet);
    }


    /**
     * Test add role.
     */
    @Test
    public void add() {
        Role role = new Role();
        role.setId(1);
        role.setRole("role");
        Assert.assertTrue(rolesStore.add(role));
    }

    /**
     * Test update role.
     */
    @Test
    public void update() {
        Role role = new Role();
        role.setId(1);
        role.setRole("role");
        Assert.assertTrue(rolesStore.update(1, role));
    }

    /**
     * Test delete role.
     */
    @Test
    public void delete() {
        Assert.assertTrue(rolesStore.delete(1));
    }

    /**
     * Test find user by id.
     *
     * @throws SQLException exception.
     */
    @Test
    public void findById() throws SQLException {
        Role role = new Role();
        role.setId(1);
        role.setRole("role");

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("role")).thenReturn("role");

        Assert.assertEquals(role, rolesStore.findById(1));
    }

    /**
     * Test find all roles.
     *
     * @throws SQLException exception
     */
    @Test
    public void findAll() throws SQLException {

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("role")).thenReturn("role");

        Assert.assertThat(rolesStore.findAll().size(), is(2));

    }

    /**
     * Test find role by name.
     * @throws SQLException exception
     */
    @Test
    public void findRoleIDByRoleName() throws SQLException {
        Role role = new Role();
        role.setId(1);
        role.setRole("role");
        when(resultSet.getInt("id")).thenReturn(1);
        Assert.assertThat(rolesStore.findRoleIDByRoleName("role"), is(1));
    }
}