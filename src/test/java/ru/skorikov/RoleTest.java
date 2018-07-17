package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * Test Role class.
 */
public class RoleTest {
    /**
     * test build new role class.
     */
    @Test
    public void thenCreateRolewhenReturnRole() {
        Role role = new Role();
        Role role1 = null;
        Assert.assertNotEquals(role, role1);
        role1 = new Role();

        role.setId(1);
        Assert.assertThat(role.getId(), is(1));

        role1.setId(1);
        Assert.assertEquals(role, role1);

        role.setRole("role");
        Assert.assertThat(role.getRole(), is("role"));
        Assert.assertNotEquals(role, role1);
        role1.setRole("role");
        Assert.assertEquals(role, role1);

        Assert.assertEquals(role.hashCode(), role1.hashCode());
        User user = new User();
        Assert.assertNotEquals(role, user);
    }
}