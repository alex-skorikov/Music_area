package ru.skorikov.persistent.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.User;
import ru.skorikov.UserMusic;
import ru.skorikov.Entity;
import ru.skorikov.MusicType;
import ru.skorikov.Role;
import ru.skorikov.Address;
import ru.skorikov.persistent.dao.AddressStore;
import ru.skorikov.persistent.dao.RolesStore;
import ru.skorikov.persistent.dao.UserMusicStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Test user-entity repository class.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserEntityesRepositoryTest {
    /**
     *
     */
    private UserEntityesRepository repository = UserEntityesRepository.getInstanse();
    /**
     * Address store for test.
     */
    private AddressStore addressStore = Mockito.mock(AddressStore.class);
    /**
     * Role store for test.
     */
    private RolesStore rolesStore = Mockito.mock(RolesStore.class);
    /**
     * Music store for test.
     */
    private UserMusicStore userMusicStore = Mockito.mock(UserMusicStore.class);
    /**
     * Datasourse for test.
     */
    private BasicDataSource dataSource = Mockito.mock(BasicDataSource.class);
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
     * Init repositiry.
     * @throws SQLException exception.
     */
    @Before
    public void init() throws SQLException {

        repository.setUtility(dataSource);
        repository.setAddressStore(addressStore);
        repository.setRolesStore(rolesStore);
        repository.setUserMusicStore(userMusicStore);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
    }

    /**
     * Test get all entityes.
     * @throws SQLException exception.
     */
    @Test
    public void getAllEntity() throws SQLException {

        User user = new User();
        Address address = new Address();
        Role role = new Role();
        MusicType musicType = new MusicType();
        musicType.setType("music");
        ArrayList<MusicType> list = new ArrayList<>();
        list.add(musicType);
        user.setId(1);
        role.setId(1);
        role.setRole("role");
        address.setId(1);
        address.setSity("sity");
        musicType.setId(1);
        user.setRole(role);
        user.setAddress(address);
        user.setMusicType(list);

        when(addressStore.findById(any(Integer.class))).thenReturn(address);
        when(rolesStore.findById(any(Integer.class))).thenReturn(role);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("music_type")).thenReturn("music");

        ConcurrentHashMap<String, ArrayList<Entity>> map
                = repository.getAllEntity(user);

        ArrayList<Entity> addresslist = map.get("Address");
        ArrayList<Entity> rolelist = map.get("Role");
        ArrayList<Entity> musiclist = map.get("Music_types");

        Role testRole = (Role) rolelist.get(0);
        Address testaddress = (Address) addresslist.get(0);
        MusicType testmusic = (MusicType) musiclist.get(0);

        Assert.assertEquals(testaddress.getSity(), "sity");
        Assert.assertEquals(testRole.getRole(), "role");
        Assert.assertEquals(testmusic.getType(), "music");

    }

    /**
     * The test for adding a new user and its essence.
     */
    @Test
    public void addNewUserAndHisEntityes() {
        User user = new User();
        MusicType musicType = new MusicType();
        ArrayList<MusicType> list = new ArrayList<>();
        musicType.setType("music");
        list.add(musicType);
        user.setMusicType(list);
        when(userMusicStore.add(any(UserMusic.class))).thenReturn(true);

        when(addressStore.add(any(Address.class))).thenReturn(false);
        when(rolesStore.add(any(Role.class))).thenReturn(false);
        Assert.assertFalse(repository.addNewUserAndHisEntityes(user));

        when(addressStore.add(any(Address.class))).thenReturn(true);
        Assert.assertFalse(repository.addNewUserAndHisEntityes(user));

        when(rolesStore.add(any(Role.class))).thenReturn(true);
        Assert.assertTrue(repository.addNewUserAndHisEntityes(user));
    }

    /**
     * The test for find user by address.
     * @throws SQLException exception.
     */
    @Test
    public void findUserByAddress() throws SQLException {
        User user = new User();
        user.setName("name");
        Address address = new Address();
        address.setId(1);
        address.setSity("sity");
        Role role = new Role();
        role.setRole("role");
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("user_name")).thenReturn("name");
        when(resultSet.getString("user_password")).thenReturn("password");
        when(addressStore.findById(any(Integer.class))).thenReturn(address);
        when(rolesStore.findById(any(Integer.class))).thenReturn(role);

        Assert.assertThat(repository.findUserByAddress(address).get(0).getAddress().getSity(), is("sity"));

    }

    /**
     * The test for find by role.
     * @throws SQLException exception.
     */
    @Test
    public void findUsersByRole() throws SQLException {
        User user = new User();
        user.setName("name");
        Address address = new Address();
        address.setId(1);
        address.setSity("sity");
        Role role = new Role();
        role.setId(1);
        role.setRole("role");
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("user_name")).thenReturn("name");
        when(resultSet.getString("user_password")).thenReturn("password");
        when(addressStore.findById(any(Integer.class))).thenReturn(address);
        when(rolesStore.findById(any(Integer.class))).thenReturn(role);

        Assert.assertThat(repository.findUsersByRole(role).get(0).getRole().getRole(), is("role"));
    }

    /**
     * The test for find by musictype.
     * @throws SQLException exception.
     */
    @Test
    public void findUsersByMusicType() throws SQLException {
        User user = new User();
        user.setName("name");
        Address address = new Address();
        address.setId(1);
        address.setSity("sity");
        Role role = new Role();
        role.setId(1);
        role.setRole("role");
        MusicType musicType = new MusicType();
        musicType.setId(1);
        musicType.setType("music");
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString("user_name")).thenReturn("name");
        when(resultSet.getString("user_password")).thenReturn("password");
        when(addressStore.findById(any(Integer.class))).thenReturn(address);
        when(rolesStore.findById(any(Integer.class))).thenReturn(role);

        Assert.assertThat(repository.findUsersByMusicType(musicType).get(0).getName(), is("name"));
    }
}