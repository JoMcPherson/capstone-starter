package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.security.Principal;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);

    void updateShoppingCartItem(int productId, ShoppingCartItem shoppingCartItem, Principal principal);

    void deleteByUserId(int userId);

    void addProductToCart(ShoppingCart cart, int userId, int addedProductId);

}
