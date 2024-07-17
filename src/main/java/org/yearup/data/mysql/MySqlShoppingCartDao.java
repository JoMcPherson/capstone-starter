package org.yearup.data.mysql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import static java.sql.DriverManager.getConnection;


@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao
{
    private final ProductDao productDao;
    private UserDao userDao = null;


    @Autowired
    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao) {
        super(dataSource);
        this.productDao = productDao;
        this.userDao = userDao;
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String sql = "SELECT * FROM shopping_cart WHERE user_id = ?";
        ShoppingCart cart = new ShoppingCart();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cart.add(mapRow(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cart;
    }

//    public void updateShoppingCartItem(int productId, ShoppingCartItem shoppingCartItem, Principal principal){
//        String userName = principal.getName();
//        int userId;
//        try {
//            User user = userDao.getByUserName(userName);
//            userId = user.getId();
//        } catch (Exception e) {
//            throw new RuntimeException("Error fetching user information for username: " + userName, e);
//        }
//
//        //Sql query to update the quantity of a shopping cart item
//        String sql = "UPDATE shopping_cart SET quantity = ? WHERE product_id = ?";
//
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//
//            //Set the parameters for the Prepared Statement
//            statement.setInt(1, shoppingCartItem.getQuantity());
//            statement.setInt(2, userId);
//            statement.setInt(3, productId);
//
//            //Execute the update query
//            int rowsUpdated = statement.executeUpdate();
//
//            // Check if any rows were updated
//            if (rowsUpdated == 0) {
//                throw new IllegalArgumentException("Product with ID " + productId + " is not in the shopping cart.");
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error updating shopping cart item with product ID: " + productId, e);
//        }
//    }


    public void updateShoppingCartItem(int productId, ShoppingCartItem shoppingCartItem, Principal principal){
        String userName = principal.getName();
//            // find database user by userId
        //User user = userDao.getByUserName(userName);
        //int userId = user.getId();

        //Sql query to update the quantity of a shopping cart item
        String sql = "UPDATE shopping_cart SET quantity = ? WHERE product_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            //Set the parameters for the Prepared Statement
            statement.setInt(1, shoppingCartItem.getQuantity());
            statement.setInt(2, productId);

            //Execute the update query
            int rowsUpdated = statement.executeUpdate();

            // Check if any rows were updated
            if (rowsUpdated == 0) {
                throw new IllegalArgumentException("Product with ID " + productId + " is not in the shopping cart.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating shopping cart item with product ID: " + productId, e);
        }
    }

    private ShoppingCartItem mapRow(ResultSet row) throws SQLException {
        int productId = row.getInt("product_id");
        int quantity = row.getInt("quantity");
        Product product = productDao.getById(productId);
        ShoppingCartItem cartItem = new ShoppingCartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        return cartItem;
    }
}

