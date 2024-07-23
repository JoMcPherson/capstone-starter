package org.yearup.data;


import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;

public interface ProfileDao
{
    Profile create(Profile profile);
    Profile getByUserId(int userId);
    Profile update(Profile profile, int userId);
}
