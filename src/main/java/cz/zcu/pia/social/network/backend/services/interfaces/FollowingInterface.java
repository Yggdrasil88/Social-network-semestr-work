/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services.interfaces;

import cz.zcu.pia.social.network.backend.entities.Following;
import cz.zcu.pia.social.network.backend.entities.Users;
import java.util.List;

/**
 * Following Interface
 *
 * @author Frantisek Kolenak
 */
public interface FollowingInterface {

    /**
     * Removes follow
     *
     * @param follower follower
     * @param feeder feeder
     */
    public void removeFollow(Users follower, Users feeder);

    /**
     * Gets follower row
     *
     * @param follower follower
     * @param feeder feeder
     * @return follower row
     */
    public Following getFollower(Users follower, Users feeder);

    /**
     * Gets users all feeders
     *
     * @param logedInUser logged in User
     * @return users all feeders
     */
    public List<Following> getUserFeeders(Users logedInUser);


}
