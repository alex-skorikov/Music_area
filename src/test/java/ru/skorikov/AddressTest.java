package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * Test Address class.
 */
public class AddressTest {
    /**
     * Test create, get, set and equals.
     */
    @Test
    public void createGetAndSet() {
        Address address = new Address();
        Address address2 = new Address();

        address.setId(1);
        Assert.assertThat(address.getId(), is(1));
        Assert.assertEquals(address, address);

        address.setCountry("country");
        Assert.assertThat(address.getCountry(), is("country"));
        Assert.assertNotEquals(address, address2);
        address2.setCountry("country");
        Assert.assertEquals(address, address2);

        address.setSity("sity");
        Assert.assertThat(address.getSity(), is("sity"));
        Assert.assertNotEquals(address, address2);
        address2.setSity("sity");
        Assert.assertEquals(address, address2);

        address.setStreet("street");
        Assert.assertThat(address.getStreet(), is("street"));
        Assert.assertNotEquals(address, address2);
        address2.setStreet("street");
        Assert.assertEquals(address, address2);

        Assert.assertEquals(address.hashCode(), address2.hashCode());
        address2 = null;
        Assert.assertNotEquals(address, address2);
        User user = new User();
        Assert.assertNotEquals(address, user);
    }
}