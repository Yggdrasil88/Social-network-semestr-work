/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.interfaces;

import cz.zcu.pia.social.network.backend.entities.FriendRequest;
import cz.zcu.pia.social.network.backend.entities.Users;
import java.util.List;

/**
 * Friend Request Interface
 *
 * @author Frantisek Kolenak
 */
public interface FriendRequestInterface {

    /**
     * Gets friend request
     *
     * @param logedInUser user that requests
     * @param user user
     * @return friend request
     */
    public FriendRequest getFriendRequest(Users logedInUser, Users user);

    /**
     * Gets all friend requests of user
     *
     * @param userId userId
     * @return all friend requests of user
     */
    public List<FriendRequest> getFriendRequests(Long userId);

}
