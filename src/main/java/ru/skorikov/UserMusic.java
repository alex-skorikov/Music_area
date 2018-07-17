package ru.skorikov;

/**
 * Intermediate class between user and musictype.
 */
public class UserMusic extends  Entity {
    /**
     * User id.
     */
    private Integer userId;
    /**
     * Music type id.
     */
    private Integer musicId;

    /**
     * Constructor.
     */
    public UserMusic() {
    }

    /**
     * Get user id.
     * @return user id.
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Set user id.
     * @param userId user id.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * et music type id.
     * @return music type id.
     */
    public Integer getMusicId() {
        return musicId;
    }

    /**
     * Set music type id.
     * @param musicId music type id.
     */
    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }


}
