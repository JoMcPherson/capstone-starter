package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {
    private final ProductDao productDao;
    private UserDao userDao = null;

    @Autowired
    public MySqlShoppingCartDao(DataSource dataSource, ProductDao productDao, UserDao userDao) {
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


    public void deleteByUserId(int userId) {
        String sql = "DELETE FROM shopping_cart WHERE user_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateShoppingCartItem(int productId, ShoppingCartItem shoppingCartItem, Principal principal){
        String sql = "UPDATE shopping_cart SET quantity = ? WHERE product_id = ? AND user_id = ?";

        String userName = principal.getName();
        User user = userDao.getByUserName(userName);
        int userId = user.getId();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, shoppingCartItem.getQuantity());
            statement.setInt(2, productId);
            statement.setInt(3, userId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated == 0) {
                throw new IllegalArgumentException("Product with ID " + productId + " is not in the shopping cart.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating shopping cart item with product ID: " + productId, e);
        }
    }

    @Override
    public void addProductToCart(ShoppingCart cart, int userId, int addedProductId) {
        String updateSql = "UPDATE shopping_cart SET quantity = quantity + 1 WHERE user_id = ? AND product_id = ?";
        String insertSql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection connection = getConnection()) {
            try {
                // Start transaction
                connection.setAutoCommit(false);

                // Insert new items into shopping cart
                for (Map.Entry<Integer, ShoppingCartItem> entry : cart.getItems().entrySet()) {
                    ShoppingCartItem item = entry.getValue();
                    int productId = item.getProduct().getProductId();

                    if (productId == addedProductId) { // Check if the product being added is the same as the product in the cart
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                            updateStatement.setInt(1, userId);
                            updateStatement.setInt(2, productId);
                            int updateCount = updateStatement.executeUpdate();

                            if (updateCount == 0) {
                                try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                                    insertStatement.setInt(1, userId);
                                    insertStatement.setInt(2, productId);
                                    insertStatement.setInt(3, 1); // Set initial quantity to 1
                                    int insertCount = insertStatement.executeUpdate();
                                    if (insertCount != 1) {
                                        throw new RuntimeException("Failed to insert item into shopping cart");
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            connection.rollback();
                            throw new RuntimeException("Failed to update or insert item into shopping cart", e);
                        }
                    }
                }

                // Commit transaction
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Error saving or updating shopping cart", e);
            } finally {
                // Reset auto-commit to true
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database", e);
        }
    }

    private ShoppingCartItem mapRow(ResultSet row) throws SQLException {
        int productId = row.getInt("product_id");
        int quantity = row.getInt("quantity");
        Product product = productDao.getById(productId);
        ShoppingCartItem cartItem = new ShoppingCartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setDiscountPercent(BigDecimal.valueOf(10));
        return cartItem;
    }
}