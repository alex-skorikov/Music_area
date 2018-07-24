package ru.skorikov;

import ru.skorikov.persistent.dao.UserStore;
import ru.skorikov.persistent.dao.RolesStore;
import ru.skorikov.persistent.dao.AddressStore;
import ru.skorikov.persistent.dao.UserMusicStore;
import ru.skorikov.persistent.repository.UserEntityesRepository;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User service impl Validate.
 */
public class UserService implements ValidateService<User> {
    /**
     * Users storege.
     */
    private static UserStore userStore;
    /**
     * Users repository.
     */
    private static UserEntityesRepository userEntityesRepository;
    /**
     * Roles store.
     */
    private static RolesStore rolesStore;
    /**
     * Address store.
     */
    private static AddressStore addressStore;
    /**
     * User - music store.
     */
    private static UserMusicStore userMusicStore;
    /**
     * Instance user service.
     */
    private static UserService instance = new UserService();

    /**
     * Get user service instance.
     *
     * @return insatance.
     */
    public static UserService getInstance() {
        return instance;
    }

    /**
     * Constructor.
     */
    private UserService() {
        userStore = UserStore.getInstance();
        userEntityesRepository = UserEntityesRepository.getInstanse();
        rolesStore = RolesStore.getInstanse();
        addressStore = AddressStore.getInstanse();
        userMusicStore = UserMusicStore.getInstanse();
    }

    /**
     * Authentication user.
     *
     * @param login    login.
     * @param password pasword.
     * @return is creditional.
     */
    public User isCreditional(String login, String password) {
        User user = null;
        ArrayList<User> users = userStore.findAll();
        for (User us : users) {
            if (us.getLogin().equals(login) && us.getPassword().equals(password)) {
                user = us;
                break;
            }
        }
        if (user != null) {
            ConcurrentHashMap<String, ArrayList<Entity>> map = userEntityesRepository.getAllEntity(user);
            user.setAddress((Address) map.get("Address").get(0));
            user.setRole((Role) map.get("Role").get(0));
            ArrayList<MusicType> userMusic = new ArrayList<>();
            for (Entity musicType : map.get("Music_types")) {
                userMusic.add((MusicType) musicType);
            }
            user.setMusicType(userMusic);
        }
        return user;
    }

    @Override
    public boolean add(User user) {
        return userStore.add(user);
    }

    @Override
    public boolean update(User user) {
        return userStore.update(user.getId(), user);
    }

    @Override
    public boolean delete(Integer id) {
        boolean isDelete = false;
        User user = userStore.findById(id);
        if (userStore.delete(user.getId()) && userMusicStore.delete(user.getId())
                && addressStore.delete(user.getAddress().getId())) {
            isDelete = true;
        }
        return isDelete;
    }

    @Override
    public ArrayList<User> findAll() {
        ArrayList<User> usersList;
        usersList = userStore.findAll();
        for (User user : usersList) {
            ConcurrentHashMap<String, ArrayList<Entity>> map = userEntityesRepository.getAllEntity(user);
            user.setAddress((Address) map.get("Address").get(0));
            user.setRole((Role) map.get("Role").get(0));
            ArrayList<MusicType> userMusic = new ArrayList<>();
            for (Entity musicType : map.get("Music_types")) {
                userMusic.add((MusicType) musicType);
            }
            user.setMusicType(userMusic);
        }
        return usersList;
    }

    @Override
    public User findById(Integer id) {
        User user = userStore.findById(id);
        ConcurrentHashMap<String, ArrayList<Entity>> map = userEntityesRepository.getAllEntity(user);
        user.setAddress((Address) map.get("Address").get(0));
        user.setRole((Role) map.get("Role").get(0));
        ArrayList<MusicType> userMusic = new ArrayList<>();
        for (Entity musicType : map.get("Music_types")) {
            userMusic.add((MusicType) musicType);
        }
        user.setMusicType(userMusic);

        return user;
    }

    @Override
    public User findByName(String name) {
        User user = null;
        ArrayList<User> users = userStore.findAll();
        for (User us : users) {
            if (us.getName().equals(name)) {
                user = us;
                break;
            }
        }
        return user;
    }

    /**
     * Set UserStore.
     * @param userStore user store.
     */
    public void setUserStore(UserStore userStore) {
        UserService.userStore = userStore;
    }

    /**
     * set user repository.
     * @param userEntityesRepository repository.
     */
    public void setUserEntityesRepository(UserEntityesRepository userEntityesRepository) {
        UserService.userEntityesRepository = userEntityesRepository;
    }

    /**
     * set Role store.
     * @param rolesStore rolestore.
     */
    public void setRolesStore(RolesStore rolesStore) {
        UserService.rolesStore = rolesStore;
    }

    /**
     * set Address store.
     * @param addressStore address store.
     */
    public void setAddressStore(AddressStore addressStore) {
        UserService.addressStore = addressStore;
    }

    /**
     * set Music store.
     * @param userMusicStore music store.
     */
    public void setUserMusicStore(UserMusicStore userMusicStore) {
        UserService.userMusicStore = userMusicStore;
    }
}
