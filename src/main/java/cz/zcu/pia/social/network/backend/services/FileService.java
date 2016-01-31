/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.backend.services;

import cz.zcu.pia.social.network.backend.services.services.impl.UsersService;
import cz.zcu.pia.social.network.helpers.Constants;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Frantisek Kolenak
 */
@Service
public class FileService {

    private static Logger logger = LoggerFactory.getLogger(FileService.class);
    /**
     * Users Service
     */
    @Autowired
    private UsersService usersService;
    /**
     * Security Helper
     */
    @Autowired
    private SecurityHelper securityHelper;

    public void createBasicFolders() {
        // Open the file for writing.
        File theDir = new File(Constants.BASE_PATH);

        // if the directory does not exist, create it
        if (!theDir.exists()) {
            logger.debug("creating directory: {}", Constants.BASE_PATH);
            theDir.mkdir();
        }
        theDir = new File(Constants.BASE_PATH_RESIZED);

        // if the directory does not exist, create it
        if (!theDir.exists()) {
            logger.debug("creating directory: {}", Constants.BASE_PATH);
            theDir.mkdir();
        }
    }

    public File createFile(String string) {
        File file = new File(string);
        logger.debug("FileName {}", file.getName());
        if (file.exists() && !file.isDirectory()) {
            return null;
        }
        return file;
    }

    public void resizeImage(File file) throws IOException, IllegalArgumentException {
        //Scale the image
        String ext = FilenameUtils.getExtension(file.getName());

        BufferedImage resizedImage = Scalr.resize(ImageIO.read(file), Constants.PROFILE_IMAGE_SIZE);
        ImageIO.write(resizedImage, ext, new File(Constants.BASE_PATH_RESIZED + file.getName()));
        File resizedFile = new File(Constants.BASE_PATH_RESIZED + file.getName());
        if (securityHelper.getLogedInUser().getUserImageName() != null) {
            new File(Constants.BASE_PATH_RESIZED + securityHelper.getLogedInUser().getUserImageName()).delete();
        }
        
        securityHelper.getLogedInUser().setUserImageName(resizedFile.getName());
        usersService.update(securityHelper.getLogedInUser());
        file.delete();
    }
}
