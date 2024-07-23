package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yearup.models.Product;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Enable Mockito annotations
class MySqlShoppingProfileTest extends BaseDaoTestClass {
    @InjectMocks
    private MySqlProfileDao dao;

    @BeforeEach
    public void setup() {
        dao = new MySqlProfileDao(dataSource);
    }

    @Test
    public void testGetByUserId() throws SQLException {
        // arrange
        int userId = 1;
        Profile expectedProfile = new Profile();
        expectedProfile.setUserId(1);
        expectedProfile.setFirstName("Joe");
        expectedProfile.setLastName("Joesephus");
        expectedProfile.setPhone("800-555-1234");
        expectedProfile.setEmail("joejoesephus@email.com");
        expectedProfile.setAddress("789 Oak Avenue");
        expectedProfile.setCity( "Dallas");
        expectedProfile.setState("TX");
        expectedProfile.setZip("75051");

        // act

        var actualProfile = dao.getByUserId(userId);

        // assert
        assertThat(actualProfile, samePropertyValuesAs(expectedProfile));
    }

    @Test
    public void testCreateProfile() throws SQLException {
        // arrange
        int userId = 3;
        Profile expectedProfile = new Profile();
        expectedProfile.setUserId(userId);
        expectedProfile.setFirstName("Big Joe");
        expectedProfile.setLastName("Big Joesephus");
        expectedProfile.setPhone("800-555-1234");
        expectedProfile.setEmail("bigjoejoesephus@email.com");
        expectedProfile.setAddress("789 Oak Avenue");
        expectedProfile.setCity( "Big Dallas");
        expectedProfile.setState("TX");
        expectedProfile.setZip("75051");

        // act

        dao.create(expectedProfile);
        var actualProfile = dao.getByUserId(userId);

        // assert
        assertThat(actualProfile, samePropertyValuesAs(expectedProfile));
    }

}