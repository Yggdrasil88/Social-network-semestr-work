/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.interfaces;

import cz.zcu.pia.social.network.backend.entities.Friends;
import cz.zcu.pia.social.network.backend.entities.Users;
import java.util.List;

/**
 *
 * @author Frantisek Kolenak
 */
public interface FriendsInterface {

    /**
     * Gets friend
     *
     * @param logedInUser logedInUser
     * @param user user
     * @return friends
     */
    public Friends getFriend(Users logedInUser, Users user);

    /**
     * Gets friends of user
     *
     * @param logedInUser user
     * @return friends of user
     */
    public List<Friends> getFriend(Users logedInUser);
}
