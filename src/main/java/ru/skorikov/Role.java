package ru.skorikov;

import java.util.Objects;

/**
 * Class Role.
 */
public class Role extends Entity {
    /**
     * Role id.
     */
    private Integer id;
    /**
     * Role name.
     */
    private String role;

    /**
     * Constuctor.
     */
    public Role() {
    }

    /**
     * Get roleID.
     * @return id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set role id.
     * @param id new id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get role.
     * @return role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Set role.
     * @param role role.
     */
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role1 = (Role) o;
        return Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role);
    }
}
