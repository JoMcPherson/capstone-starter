package org.yearup.data;

import org.yearup.models.Category;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.security.Principal;

//Interface Declaration
public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    void updateShoppingCartItem(int productId, ShoppingCartItem shoppingCartItem, Principal principal);
}
