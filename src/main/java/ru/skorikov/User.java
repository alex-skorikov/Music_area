package ru.skorikov;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Class User.
 */
public class User extends Entity {
    /**
     * User ID.
     */
    private Integer id;
    /**
     * User Name.
     */
    private String name;
    /**
     * User login.
     */
    private String login;
    /**
     * User email.
     */
    private String email;
    /**
     * User createDate.
     */
    private Date createDate;
    /**
     * User password.
     */
    private String password;
    /**
     * User address.
     */
    private Address address;
    /**
     * User role.
     */
    private Role role;
    /**
     * User music types.
     */
    private ArrayList<MusicType> musicType = new ArrayList<>();

    /**
     * Create new User.
     */
    public User() {
    }

    /**
     * Get user password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set user password.
     *
     * @param password password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get user name.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set user name.
     *
     * @param name name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get user login.
     *
     * @return login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Set user login.
     *
     * @param login login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Get user email.
     *
     * @return email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get user Id.
     *
     * @return id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set User id.
     *
     * @param id id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get date create user.
     *
     * @return date.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Set user email.
     *
     * @param email email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get user address.
     * @return address.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Set address.
     * @param address new address.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Get user role.
     * @return role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Set role.
     * @param role new role.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * get user music type.
     * @return types list.
     */
    public ArrayList<MusicType> getMusicType() {
        return musicType;
    }

    /**
     * Set music type list to User.
     * @param musicType music type list.
     */
    public void setMusicType(ArrayList<MusicType> musicType) {
        this.musicType = musicType;
    }

    /**
     * Set user createDate.
     *
     * @param createDate create Date.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "User{"
                + "name='" + name + '\''
                + ", login='" + login + '\''
                + ", email='" + email + '\''
                + ", createDate=" + createDate
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(name, user.name)
                && Objects.equals(login, user.login)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login, email, createDate);
    }
}
