package ru.skorikov.persistent.dao;

import org.junit.Test;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.MusicType;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Test music type store class.
 */
@RunWith(MockitoJUnitRunner.class)
public class MusicTypeStoreTest {
    /**
     * Instance Music type store.
     */
    private MusicTypeStore store = MusicTypeStore.getInstanse();
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
        store.setUtility(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any(String.class))).thenReturn(resultSet);
    }

    /**
     * Test add music type.
     */
    @Test
    public void add() {
        MusicType musicType = new MusicType();
        musicType.setId(1);
        musicType.setType("type");
        musicType.setDescription("desc");
        Assert.assertTrue(store.add(musicType));
    }

    /**
     * Test update music type.
     */
    @Test
    public void update() {
        MusicType musicType = new MusicType();
        musicType.setId(1);
        musicType.setType("type");
        musicType.setDescription("desc");
        Assert.assertTrue(store.update(1, musicType));
    }

    /**
     * Test delete music type.
     */
    @Test
    public void delete() {
        Assert.assertTrue(store.delete(1));
    }

    /**
     * Test find user by id.
     *
     * @throws SQLException exception.
     */
    @Test
    public void findById() throws SQLException {
        MusicType musicType = new MusicType();
        musicType.setId(1);
        musicType.setType("type");
        musicType.setDescription("desc");

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("music_type")).thenReturn("type");
        when(resultSet.getString("description")).thenReturn("desc");

        Assert.assertEquals(musicType, store.findById(1));
    }

    /**
     * Test find all music types.
     *
     * @throws SQLException exception
     */
    @Test
    public void findAll() throws SQLException {

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("music_type")).thenReturn("type");
        when(resultSet.getString("description")).thenReturn("desc");

        Assert.assertThat(store.findAll().size(), is(1));

    }

    /**
     * Test find music type by type.
     * @throws SQLException exception
     */
    @Test
    public void findByType() throws SQLException {
        MusicType musicType = new MusicType();
        musicType.setId(1);
        musicType.setType("type");
        musicType.setDescription("desc");

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("music_type")).thenReturn("type");
        when(resultSet.getString("description")).thenReturn("desc");

        Assert.assertThat(musicType, is(store.findByType("type")));
    }
}