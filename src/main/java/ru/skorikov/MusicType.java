package ru.skorikov;

import java.util.Objects;

/**
 * Class music type.
 */
public class MusicType extends Entity {
    /**
     * Music id.
     */
    private Integer id;
    /**
     * Music type.
     */
    private String type;
    /**
     * Music description.
     */
    private String description;

    /**
     * Constructor.
     */
    public MusicType() {
    }

    /**
     * Get music id.
     * @return id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set music id.
     * @param id id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get music type.
     * @return type.
     */
    public String getType() {
        return type;
    }

    /**
     * Set music type.
     * @param type type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get musicType description.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set musictype discription.
     * @param description description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MusicType musicType = (MusicType) o;
        return Objects.equals(type, musicType.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type);
    }
}
