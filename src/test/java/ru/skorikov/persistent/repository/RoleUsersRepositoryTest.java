package ru.skorikov.persistent.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.Entity;
import ru.skorikov.Role;
import ru.skorikov.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Role-User ropository test class.
 */
@RunWith(MockitoJUnitRunner.class)
public class RoleUsersRepositoryTest {
    /**
     * datasourse for tesst.
     */
    private BasicDataSource dataSource = Mockito.mock(BasicDataSource.class);
    /**
     * Repository for test.
     */
    private RoleUsersRepository repository = Mockito.mock(RoleUsersRepository.class);
    /**
     * Connection for test.
     */
    private Connection connection = Mockito.mock(Connection.class);
    /**
     * Statment for test.
     */
    private PreparedStatement statement = Mockito.mock(PreparedStatement.class);
    /**
     * Resultset for test.
     */
    private ResultSet resultSet = Mockito.mock(ResultSet.class);

    /**
     * Init sourse for test.
     * @throws SQLException exception.
     */
    @Before
    public void init() throws SQLException {
        repository.setUtility(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
    }

    /**
     * Test get all users by role.
     * @throws SQLException exception.
     */
    @Test
    public void getAllEntity() throws SQLException {
        User user = new User();
        user.setId(1);
        user.setName("User");
        user.setLogin("Login");
        Role role = new Role();
        role.setId(1);
        user.setRole(role);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(user.getId());
        when(resultSet.getString("user_name")).thenReturn(user.getName());
        when(resultSet.getString("user_login")).thenReturn(user.getLogin());

        ConcurrentHashMap<String, CopyOnWriteArrayList<Entity>> map
                = RoleUsersRepository.getInstanse().getAllEntity(role);
        CopyOnWriteArrayList<Entity> list = map.get("Users");
        User test = (User) list.get(0);

        Assert.assertThat(test.getName(), is(user.getName()));
        Assert.assertThat(test.getLogin(), is(user.getLogin()));
    }
}