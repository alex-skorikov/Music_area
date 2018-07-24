package ru.skorikov;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.skorikov.persistent.dao.AddressStore;
import ru.skorikov.persistent.dao.RolesStore;
import ru.skorikov.persistent.dao.UserMusicStore;
import ru.skorikov.persistent.dao.UserStore;
import ru.skorikov.persistent.repository.UserEntityesRepository;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


/**
 * Test User-service class.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    /**
     * user store for test.
     */
    @Mock
    private UserStore userStore = Mockito.mock(UserStore.class);
    /**
     * rolestore for test.
     */
    @Mock
    private RolesStore rolesStore = Mockito.mock(RolesStore.class);
    /**
     * address store for test.
     */
    @Mock
    private AddressStore addressStore = Mockito.mock(AddressStore.class);
    /**
     * music store for test.
     */
    @Mock
    private UserMusicStore userMusicStore = Mockito.mock(UserMusicStore.class);
    /**
     * repository for test.
     */
    @Mock
    private UserEntityesRepository repository = Mockito.mock(UserEntityesRepository.class);
    /**
     * Test instance.
     */
    @InjectMocks
    private UserService userService = UserService.getInstance();
    /**
     * Init storege and services.
     */
    @Before
    public void init() {
        userService.setUserStore(userStore);
        userService.setAddressStore(addressStore);
        userService.setRolesStore(rolesStore);
        userService.setUserMusicStore(userMusicStore);
        userService.setUserEntityesRepository(repository);
    }
    /**
     * Test sercice add User.
     */
    @Test
    public void thenAddUserWhenReturnTrue() {
        User user = new User();
        when(userStore.add(any(User.class))).thenReturn(true);
        Assert.assertTrue(UserService.getInstance().add(user));
    }

    /**
     * Test update User.
     */
    @Test
    public void update() {
        User user = new User();
        when(userStore.update(any(Integer.class), any(User.class))).thenReturn(true);
        Assert.assertTrue(UserService.getInstance().update(user));
    }

    /**
     * Test delete User.
     */
    @Test
    public void delete() {
        User user = new User();
        user.setId(1);
        Address address = new Address();
        user.setAddress(address);
        when(userStore.findById(any(Integer.class))).thenReturn(user);

        when(userStore.delete(any(Integer.class))).thenReturn(true);
        Assert.assertFalse(UserService.getInstance().delete(1));

        when(userMusicStore.delete(any(Integer.class))).thenReturn(true);
        Assert.assertFalse(UserService.getInstance().delete(1));

        when(addressStore.delete(any(Integer.class))).thenReturn(true);
        Assert.assertTrue(UserService.getInstance().delete(1));
    }

    /**
     * Test find all users.
     */
    @Test
    public void findAll() {
        User user = new User();
        User user2 = new User();
        user.setId(1);
        user2.setId(2);
        ArrayList<User> list = new ArrayList<>();
        list.add(user);
        list.add(user2);
        when(userStore.findAll()).thenReturn(list);

        ConcurrentHashMap<String, ArrayList<Entity>> map = new ConcurrentHashMap<>();
        Address address = new Address();
        Role role = new Role();
        MusicType musicType = new MusicType();
        ArrayList addressList = new ArrayList();
        addressList.add(address);
        ArrayList roleList = new ArrayList();
        roleList.add(role);
        ArrayList musicList = new ArrayList();
        musicList.add(musicType);
        map.put("Address", addressList);
        map.put("Role", roleList);
        map.put("Music_types", musicList);
        when(repository.getAllEntity(any(User.class))).thenReturn(map);

        Assert.assertNotNull(UserService.getInstance().findAll());
    }

    /**
     * Test find user by id.
     */
    @Test
    public void findById() {
        User user = new User();
        user.setId(1);
        ConcurrentHashMap<String, ArrayList<Entity>> map = new ConcurrentHashMap<>();
        Address address = new Address();
        Role role = new Role();
        MusicType musicType = new MusicType();
        ArrayList addressList = new ArrayList();
        addressList.add(address);
        ArrayList roleList = new ArrayList();
        roleList.add(role);
        ArrayList musicList = new ArrayList();
        musicList.add(musicType);
        map.put("Address", addressList);
        map.put("Role", roleList);
        map.put("Music_types", musicList);
        when(userStore.findById(any(Integer.class))).thenReturn(user);
        when(repository.getAllEntity(any(User.class))).thenReturn(map);

        Assert.assertNotNull(UserService.getInstance().findById(1));
    }

    /**
     * Test find user by name.
     */
    @Test
    public void findByName() {
        User user = new User();
        user.setName("name");
        ArrayList<User> list = new ArrayList<>();
        list.add(user);

        when(userStore.findAll()).thenReturn(list);

        Assert.assertThat(userService.findByName("name"), is(user));

    }
    /**
     * Test isCreditional user.
     */
    @Test
    public void isCreditionalUserTest() {
        User user = new User();
        user.setLogin("");
        user.setPassword("");
        User user1 = new User();
        user1.setLogin("");
        user1.setPassword("");
        User user2 = new User();
        user2.setLogin("login");
        user2.setPassword("pass");
        ArrayList<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);
        list.add(user2);

        when(userStore.findAll()).thenReturn(list);

        ConcurrentHashMap<String, ArrayList<Entity>> map = new ConcurrentHashMap<>();
        Address address = new Address();
        Role role = new Role();
        MusicType musicType = new MusicType();
        ArrayList addressList = new ArrayList();
        assertTrue(addressList.add(address));
        ArrayList roleList = new ArrayList();
        assertTrue(roleList.add(role));
        ArrayList musicList = new ArrayList();
        assertTrue(musicList.add(musicType));
        map.put("Address", addressList);
        map.put("Role", roleList);
        map.put("Music_types", musicList);
        when(repository.getAllEntity(any(User.class))).thenReturn(map);

        Assert.assertThat(UserService.getInstance().isCreditional("login", "pass"), is(user2));

        when(userStore.findAll()).thenReturn(new ArrayList<>());
        Assert.assertNull(UserService.getInstance().isCreditional("", ""));
    }

}