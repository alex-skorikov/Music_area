package ru.skorikov;

import java.util.Objects;

/**
 * Class Address.
 */
public class Address extends Entity {
    /**
     * Address ID.
     */
    private Integer id;
    /**
     * Country.
     */
    private String country;
    /**
     * Sity.
     */
    private String sity;
    /**
     * Street.
     */
    private String street;

    /**
     * Constructor.
     */
    public Address() {
    }

    /**
     * Get address id.
     * @return id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set address id.
     * @param id id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get counrty.
     * @return country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set country.
     * @param country country.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get sity.
     * @return sity.
     */
    public String getSity() {
        return sity;
    }

    /**
     * Set sity.
     * @param sity new sity.
     */
    public void setSity(String sity) {
        this.sity = sity;
    }

    /**
     * Get street.
     * @return street.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Set new street.
     * @param street street.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return Objects.equals(country, address.country)
                && Objects.equals(sity, address.sity)
                && Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {

        return Objects.hash(country, sity, street);
    }
}
