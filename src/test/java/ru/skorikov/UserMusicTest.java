package ru.skorikov;


import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * Test user-music class.
 */
public class UserMusicTest {
    /**
     * test build new User-Music.
     */
    @Test
    public void thenAddNewUserMusicWhenCreatenewUser() {
        UserMusic userMusic = new UserMusic();
        userMusic.setMusicId(1);
        Assert.assertThat(userMusic.getMusicId(), is(1));

        userMusic.setUserId(1);
        Assert.assertThat(userMusic.getUserId(), is(1));
    }

}