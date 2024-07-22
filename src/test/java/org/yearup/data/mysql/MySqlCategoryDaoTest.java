package org.yearup.data.mysql;

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
public class MySqlCategoryDaoTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private MySqlCategoryDao mySqlCategoryDao;

    @BeforeEach
    public void setUp() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void testGetAllCategories() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("category_id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Electronics");
        when(resultSet.getString("description")).thenReturn("Devices and gadgets");

        List<Category> categories = mySqlCategoryDao.getAllCategories();

        assertNotNull(categories);
        assertEquals(1, categories.size());

        Category category = categories.get(0);
        assertEquals(1, category.getCategoryId());
        assertEquals("Electronics", category.getName());
        assertEquals("Devices and gadgets", category.getDescription());
    }

    // Add more tests for other methods
}
