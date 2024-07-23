//package org.yearup;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.yearup.controllers.ProductsController;
//import org.yearup.data.mysql.MySqlProductDao;
//import org.yearup.models.Product;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.*;
//
//class TestClass {
//
//    @Mock
//    private MySqlProductDao mySqlProductDao;
//
//    @InjectMocks
//    private ProductsController productsController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testFetchAllProducts() {
//        // Arrange
//        when(mySqlProductDao.search()).thenReturn(List.of(new Product()));
//
//        // Act
//        List<Product> products = mySqlProductDao.search(null, null, null, null);
//        products.forEach(product -> System.out.println(product.getName()));
//
//        // Assert
//        assertNotNull(products);
//        assertFalse(products.isEmpty());
//        verify(mySqlProductDao, times(1)).search(null, null, null, null);
//    }
//}
