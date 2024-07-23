package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.Product;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MySqlProductDaoTest extends BaseDaoTestClass
{
    private MySqlProductDao dao;

    @BeforeEach
    public void setup()
    {
        dao = new MySqlProductDao(dataSource);
    }

    @Test
    public void getById_shouldReturn_theCorrectProduct()
    {
        // arrange
        int productId = 1;
        Product expected = new Product()
        {{
            setProductId(1);
            setName("Smartphone");
            setPrice(new BigDecimal("499.99"));
            setCategoryId(1);
            setDescription("A powerful and feature-rich smartphone for all your communication needs.");
            setColor("Black");
            setStock(50);
            setFeatured(false);
            setImageUrl("smartphone.jpg");
        }};

        // act
        var actual = dao.getById(productId);

        // assert
        assertEquals(expected.getPrice(), actual.getPrice(), "Because I tried to get product 1 from the database.");
    }
    @Test
    public void update_should_correctly_update_field()
    {
        Product product = new Product(){{
            setProductId(1);
            setName("Smartphone");
            setPrice(new BigDecimal("4.99"));
            setCategoryId(1);
            setDescription("A powerful and feature-rich smartphone for all your communication needs.");
            setColor("Black");
            setStock(50);
            setFeatured(false);
            setImageUrl("smartphone.jpg");
        }};

        dao.update(1, product);
        Product actual = dao.getById(1);


        assertEquals(actual.getName(), product.getName());
        assertEquals(actual.getPrice(), product.getPrice());
        assertEquals(actual.getCategoryId(), product.getCategoryId());
        assertEquals(actual.getDescription(), product.getDescription());
        assertEquals(actual.getColor(), product.getColor());
        assertEquals(actual.getStock(), product.getStock());
        assertEquals(actual.isFeatured(), product.isFeatured());
        assertEquals(actual.getImageUrl(), product.getImageUrl());
    }

    @Test
    public void delete_should_remove_product(){
        dao.delete(10);
        assertNull(dao.getById(10));
    }
}