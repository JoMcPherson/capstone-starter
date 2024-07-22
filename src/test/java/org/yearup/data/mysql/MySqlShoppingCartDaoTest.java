package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Enable Mockito annotations
class MySqlShoppingCartDaoTest extends BaseDaoTestClass {
    @Mock
    private MySqlProductDao productDao;
    @InjectMocks
    private MySqlShoppingCartDao dao;

    @BeforeEach
    public void setup() {
        dao = new MySqlShoppingCartDao(dataSource, productDao);
    }

    @Test
    public void testGetByUserID() throws SQLException {
        // arrange
        int userId = 1;
        Product expectedProduct = new Product();
        expectedProduct.setProductId(1);
        expectedProduct.setName("Smartphone");
        expectedProduct.setPrice(new BigDecimal("499.99"));
        expectedProduct.setCategoryId(1);
        expectedProduct.setDescription("A powerful and feature-rich smartphone for all your communication needs.");
        expectedProduct.setColor("Black");
        expectedProduct.setStock(50);
        expectedProduct.setFeatured(false);
        expectedProduct.setImageUrl("smartphone.jpg");


        ShoppingCartItem expectedItem = new ShoppingCartItem();
        expectedItem.setProduct(expectedProduct);
        expectedItem.setQuantity(3);

        ShoppingCart expected = new ShoppingCart();
      expected.add(expectedItem);


        //mock
        when(productDao.getById(1)).thenReturn(expectedProduct);
        // act

        var actual = dao.getByUserId(userId);

        // assert
        assertEquals(expected.getTotal(), actual.getTotal(), "Check shopping cart from database matches");
    }
}