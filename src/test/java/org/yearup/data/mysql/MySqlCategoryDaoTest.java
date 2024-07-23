package org.yearup.data.mysql;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MySqlCategoryDaoTest extends BaseDaoTestClass{

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private MySqlCategoryDao mySqlCategoryDao;

//    @BeforeEach
//    public void setUp() throws SQLException {
//        when(dataSource.getConnection()).thenReturn(connection);
//        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
//        when(preparedStatement.executeQuery()).thenReturn(resultSet);
//    }

    @BeforeEach
    public void setup()
    {
        mySqlCategoryDao = new MySqlCategoryDao(dataSource);
    }

    @Test
    public void testGetAllCategories() throws SQLException {
//        when(resultSet.next()).thenReturn(true).thenReturn(false);
//        when(resultSet.getInt("category_id")).thenReturn(1);
//        when(resultSet.getString("name")).thenReturn("Electronics");
//        when(resultSet.getString("description")).thenReturn("Devices and gadgets");

        List<Category> categories = mySqlCategoryDao.getAllCategories();

        assertNotNull(categories);
        assertEquals(3, categories.size());

        Category category = categories.get(0);
        assertEquals(1, category.getCategoryId());
        assertEquals("Electronics", category.getName());
        assertEquals("Explore the latest gadgets and electronic devices.", category.getDescription());
    }

    // Add more tests for other methods

    @Test
    public void testUpdateCategory() throws SQLException {
//        when(resultSet.next()).thenReturn(true).thenReturn(false);
//        when(resultSet.getInt("category_id")).thenReturn(1);
//        when(resultSet.getString("name")).thenReturn("Electronics");
//        when(resultSet.getString("description")).thenReturn("Devices and gadgets");

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

        // Call the delete method
        mySqlCategoryDao.delete(categoryIdToDelete);

        // Make sure it doesn't exist
        assertNull(mySqlCategoryDao.getById(categoryIdToDelete));
    }
}
