package ru.skorikov.persistent.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.UserMusic;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;


/**
 * Test User music store class.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserMusicStoreTest {
    /**
     * Musicstore for test.
     */
    private UserMusicStore musicStore = UserMusicStore.getInstanse();
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
     * Init music store.
     * @throws SQLException exception.
     */
    @Before
    public void init() throws SQLException {
        musicStore.setUtility(dataSource);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(any(String.class))).thenReturn(resultSet);
    }

    /**
     * Test add usermusic to user-music store.
     */
    @Test
    public void add() {
        UserMusic userMusic = new UserMusic();
        userMusic.setUserId(1);
        userMusic.setMusicId(1);
        Assert.assertTrue(musicStore.add(userMusic));
    }

    /**
     * Test update user-music.
     */
    @Test
    public void update() {
        UserMusic userMusic = new UserMusic();
        userMusic.setUserId(1);
        userMusic.setMusicId(1);
        Assert.assertTrue(musicStore.update(1, userMusic));
    }

    /**
     * Test delete user-musictype.
     */
    @Test
    public void delete() {
        UserMusic userMusic = new UserMusic();
        userMusic.setUserId(1);
        userMusic.setMusicId(1);

        Assert.assertTrue(musicStore.delete(1));
    }

    /**
     * Test find music by id.
     * @throws SQLException exception.
     */
    @Test
    public void findById() throws SQLException {

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt(any(String.class))).thenReturn(1);
        UserMusic userMusic = musicStore.findById(1);

        Assert.assertThat(userMusic.getUserId(), is(1));
        Assert.assertThat(userMusic.getMusicId(), is(1));

    }

    /**
     * Test find all musictypes.
     * @throws SQLException exception.
     */
    @Test
    public void findAll() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt(any(String.class))).thenReturn(1);
        ArrayList<UserMusic> list = musicStore.findAll();

        Assert.assertTrue(list.size() == 2);

    }

    /**
     * Test SQL exceptionin when method is not connection.
     * @throws SQLException exception.
     */
    @Test
    public void testSQLExceptionWhenNotConnection() throws SQLException {
        when(dataSource.getConnection()).thenThrow(SQLException.class);

        musicStore.add(new UserMusic());
        verify(connection, never()).prepareStatement(any(String.class));

        musicStore.update(1, new UserMusic());
        verify(connection, never()).prepareStatement(any(String.class));

        musicStore.delete(1);
        verify(connection, never()).prepareStatement(any(String.class));

        musicStore.findById(1);
        verify(connection, never()).prepareStatement(any(String.class));

        musicStore.findAll();
        verify(connection, never()).prepareStatement(any(String.class));

    }

    /**
     * Test SQL exceptionin when method is not preparestatment.
     * @throws SQLException exception.
     */
    @Test
    public void testSQLExceptionWhenNotPreparestatement() throws SQLException {

        when(connection.prepareStatement(any(String.class))).thenThrow(SQLException.class);

        musicStore.add(new UserMusic());
        verify(preparedStatement, never()).executeUpdate();

        musicStore.delete(1);
        verify(preparedStatement, never()).executeUpdate();

        musicStore.update(1, new UserMusic());
        verify(preparedStatement, never()).executeUpdate();

        when(connection.createStatement()).thenThrow(SQLException.class);
        musicStore.findAll();
        verify(resultSet, never()).next();
    }

    /**
     * Test SQL exceptionin when method is not resultset.
     * @throws SQLException exception.
     */
    @Test
    public void testSQLExceptionWhenNotResultSet() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);

        musicStore.findById(1);
        verify(resultSet, never()).next();

        when(statement.executeQuery(any(String.class))).thenThrow(SQLException.class);
        musicStore.findAll();
        verify(resultSet, never()).next();
    }
}