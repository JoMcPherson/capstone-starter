package org.yearup.data.mysql;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.yearup.models.Category;


import java.sql.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.yearup.models.Category;

import java.sql.SQLException;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)


public class MySqlCategoryDaoTest extends BaseDaoTestClass{

    private MySqlCategoryDao mySqlCategoryDao;

    @BeforeEach
    public void setup()
    {
        mySqlCategoryDao = new MySqlCategoryDao(dataSource);
    }
  
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
    public void testGetAllCategories() throws SQLException {
        List<Category> categories = mySqlCategoryDao.getAllCategories();

        assertNotNull(categories);
        assertEquals(3, categories.size());

        Category category = categories.get(0);
        assertEquals(1, category.getCategoryId());
        assertEquals("Electronics", category.getName());
        assertEquals("Explore the latest gadgets and electronic devices.", category.getDescription());

        Category category2 = categories.get(1);
        assertEquals(2, category2.getCategoryId());
        assertEquals("Fashion", category2.getName());
        assertEquals("Discover trendy clothing and accessories for men and women.", category2.getDescription());

        Category category3 = categories.get(2);
        assertEquals(3, category3.getCategoryId());
        assertEquals("Home & Kitchen", category3.getName());
        assertEquals("Find everything you need to decorate and equip your home.", category3.getDescription());
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
    // Add more tests for other methods

    @Test
    public void testUpdateCategory() throws SQLException {

        int categoryToUpdate = 1;

        List<Category> categories = mySqlCategoryDao.getAllCategories();

        categories.forEach((category -> System.out.println(category.getCategoryId())));

        Category newCategory = new Category(categoryToUpdate, "Test worked", "Description for working test");

        mySqlCategoryDao.update(1, newCategory);

        assertThat(mySqlCategoryDao.getById(categoryToUpdate), samePropertyValuesAs(newCategory));
    }

    @Test
    public void testDeleteCategory() throws SQLException {
        int categoryIdToDelete = 1;

        mySqlCategoryDao.delete(categoryIdToDelete);

        assertNull(mySqlCategoryDao.getById(categoryIdToDelete));
    }
}

