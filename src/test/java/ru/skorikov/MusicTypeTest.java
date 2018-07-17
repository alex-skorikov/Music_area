package ru.skorikov;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * Music type class test.
 */
public class MusicTypeTest {
    /**
     * musicType1.
     */
    private MusicType musicType;
    /**
     * musicType2.
     */
    private MusicType musicType1;

    /**
     * Before init test musictype.
     */
    @Before
    public void init() {
        musicType = new MusicType();
        musicType1 = new MusicType();
    }

    /**
     * Test get and set musicType id.
     */
    @Test
    public void thenSetIdwhenReturnID() {
        musicType.setId(1);
        musicType1 = null;
        Assert.assertThat(musicType.getId(), is(1));
        Assert.assertEquals(musicType, musicType);
        Assert.assertNotEquals(musicType, musicType1);
        User user = new User();
        Assert.assertNotEquals(musicType, user);
    }

    /**
     * Test get and set type.
     */
    @Test
    public void getType() {
        musicType.setType("type");
        Assert.assertEquals(musicType.getType(), "type");
        Assert.assertNotEquals(musicType, musicType1);
        musicType1.setType("type");
        Assert.assertEquals(musicType, musicType1);
        Assert.assertThat(musicType.hashCode(), is(musicType1.hashCode()));
    }

    /**
     * Test get and set description.
     */
    @Test
    public void getDescription() {
        musicType.setDescription("desc");
        Assert.assertEquals(musicType.getDescription(), "desc");
    }

}