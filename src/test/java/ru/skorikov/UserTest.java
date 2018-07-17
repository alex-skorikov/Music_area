package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.core.Is.is;

/**
 * Test User class.
 */
public class UserTest {
    /**
     * test build new User.
     */
    @Test
    public void thenAddNewUserWhenCreatenewUser() {
        User user1 = new User();
        Assert.assertEquals(user1, user1);
        User user2 = new User();
        user1.setEmail("email");
        Assert.assertThat(user1.getEmail(), is("email"));
        Assert.assertNotEquals(user1, user2);

        user2.setEmail("email");
        Assert.assertEquals(user1, user2);

        user1.setLogin("login");
        Assert.assertThat(user1.getLogin(), is("login"));
        Assert.assertNotEquals(user1, user2);

        user2.setLogin("login");
        Assert.assertEquals(user1, user2);

        user1.setName("name");
        Assert.assertThat(user1.getName(), is("name"));
        Assert.assertNotEquals(user1, user2);

        user2.setName("name");
        Assert.assertEquals(user1, user2);

        user1.setPassword("pass");
        Assert.assertEquals(user1.getPassword(), "pass");
        Assert.assertNotEquals(user1, user2);

        user1.setId(1);
        Assert.assertThat(user1.getId(), is(1));

        Role role = new Role();
        user1.setRole(role);
        Assert.assertThat(user1.getRole(), is(role));

        Address address = new Address();
        user1.setAddress(address);
        Assert.assertThat(user1.getAddress(), is(address));

        CopyOnWriteArrayList<MusicType> list = new CopyOnWriteArrayList<>();
        user1.setMusicType(list);
        Assert.assertThat(user1.getMusicType(), is(list));

        int u1 = user1.hashCode();
        int u2 = user2.hashCode();
        Assert.assertEquals(u1, u2);

        String str1 = user1.toString();
        String str2 = user2.toString();
        Assert.assertEquals(str1, str2);

        Date date = new Date();
        user1.setCreateDate(date);
        Assert.assertThat(user1.getCreateDate(), is(date));

        Assert.assertNotEquals(user1, null);
        Assert.assertNotEquals(user1, String.class);
    }
}