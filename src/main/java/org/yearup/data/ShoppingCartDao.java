package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    // add additional method signatures here

    void deleteByUserId(int userId);

    void addProductToCart(ShoppingCart cart, int userId, int addedProductId);

}
