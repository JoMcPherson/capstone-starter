package org.yearup.data.mysql;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.yearup.models.Category;


import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MySqlCategoryDaoTest extends BaseDaoTestClass {

    private MySqlCategoryDao mySqlCategoryDao;

    @BeforeEach
    public void setup() {
        mySqlCategoryDao = new MySqlCategoryDao(dataSource);
    }


//    @Test
//    public void testGetAllCategories() throws SQLException {
//        when(resultSet.next()).thenReturn(true).thenReturn(false);
//        when(resultSet.getInt("category_id")).thenReturn(1);
//        when(resultSet.getString("name")).thenReturn("Electronics");
//        when(resultSet.getString("description")).thenReturn("Devices and gadgets");
//
//        List<Category> categories = mySqlCategoryDao.getAllCategories();
//
//        assertNotNull(categories);
//        assertEquals(1, categories.size());
//
//        Category category = categories.get(0);
//        assertEquals(1, category.getCategoryId());
//        assertEquals("Electronics", category.getName());
//        assertEquals("Devices and gadgets", category.getDescription());
//    }

    // Add more tests for other methods
    @Test
    public void testCreateCategory() {
        Category expected = new Category();
        expected.setName("Shoes");
        expected.setDescription("A variety of shoes");

        Category actual = mySqlCategoryDao.create(expected);

        assertThat(actual, samePropertyValuesAs(expected, "categoryId"));
    }

    @Test
    public void testGetCategoryById() throws SQLException {
        int categoryId = 1;
        Category expected = new Category();
        expected.setCategoryId(1);
        expected.setName("Electronics");
        expected.setDescription("Explore the latest gadgets and electronic devices.");

        var actual = mySqlCategoryDao.getById(categoryId);

        assertNotNull(actual, "Returned category should not be null");
        assertEquals(expected.getCategoryId(), actual.getCategoryId(), "Category ID should match");
        assertEquals(expected.getName(), actual.getName(), "Name should match");
        assertEquals(expected.getDescription(), actual.getDescription(), "Description should match");
    }
}