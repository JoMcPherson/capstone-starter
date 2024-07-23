package org.yearup.data.mysql;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yearup.models.Category;

import java.sql.SQLException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MySqlCategoryDaoTest extends BaseDaoTestClass{

    private MySqlCategoryDao mySqlCategoryDao;

    @BeforeEach
    public void setup()
    {
        mySqlCategoryDao = new MySqlCategoryDao(dataSource);
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
