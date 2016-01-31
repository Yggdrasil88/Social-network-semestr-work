/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.helpers;

import java.io.File;

/**
 * Constants 
 * @author Frantisek Kolenak
 */
public class Constants {

    public static final int PAGE_LENGTH = 10;
    public static final int MAX_RESULTS = 50;
    public static final String BASE_PATH = System.getProperty("user.home") + File.separatorChar + "pia" + File.separatorChar;
    public static final String BASE_PATH_RESIZED = System.getProperty("user.home") + File.separatorChar + "pia" + File.separatorChar + "resized" + File.separatorChar ;
    public static final int PROFILE_IMAGE_SIZE = 150;
}
