/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.profile.profile;

import com.vaadin.server.FileResource;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import cz.zcu.pia.social.network.backend.services.services.impl.UsersService;
import cz.zcu.pia.social.network.helpers.Constants;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Image uploader, handles events when uploading
 * @author Frantisek Kolenak
 */
@Component
public class ImageUploader implements Upload.Receiver, Upload.SucceededListener {

    private static Logger logger = LoggerFactory.getLogger(ComponentProfile.class);
    /**
     * Security Helper
     */
    @Autowired
    private SecurityHelper securityHelper;
    /**
     * Messages Loader
     */
    @Autowired
    private MessagesLoader msgs;
    /**
     * Users Service
     */
    @Autowired
    private UsersService usersService;
    /**
     * file that is saved
     */
    public File file;

    /**
     * ComponentProfile parent
     */
    private ComponentProfile parent;

    @Override
    public OutputStream receiveUpload(String filename,
        String mimeType) {
        if (filename == null) {
            return null;
        }
        // Create upload stream
        FileOutputStream fos; // Stream to write to
        try {

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

            file = new File(Constants.BASE_PATH + securityHelper.getLogedInUser().getId() + "-" + Math.abs((int) (Math.random() * 10000000)) + "." + FilenameUtils.getExtension(filename));
            logger.debug("FileName {}", file.getName());
            if (file.exists() && !file.isDirectory()) {
                Notification.show(msgs.getMessage("error.file.exists"), Notification.Type.ERROR_MESSAGE);
                return null;
            }
            fos = new FileOutputStream(file);

        } catch (final java.io.FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            return null;
        } catch (SecurityException e) {
            logger.error(e.getMessage(), e);
            return null;

        }
        return fos; // Return the output stream to write to
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        try {

            //Scale the image
            String ext = FilenameUtils.getExtension(file.getName());

            BufferedImage resizedImage = Scalr.resize(ImageIO.read(file), 150);
            //FileUtils.deleteQuietly(file);
            ImageIO.write(resizedImage, ext, new File(Constants.BASE_PATH_RESIZED + file.getName()));
            File resizedFile = new File(Constants.BASE_PATH_RESIZED + file.getName());
            if (securityHelper.getLogedInUser().getUserImageName() != null) {
                new File(Constants.BASE_PATH_RESIZED + securityHelper.getLogedInUser().getUserImageName()).delete();
            }
            securityHelper.getLogedInUser().setUserImageName(resizedFile.getName());
            usersService.update(securityHelper.getLogedInUser());
            file.delete();
            // Show the uploaded file in the image viewer
            parent.reloadImage();
            Notification.show(msgs.getMessage("upload.succes"));
        } catch (IOException | IllegalArgumentException e) {
            Notification.show(msgs.getMessage("error.upload.ioexception"), Notification.Type.ERROR_MESSAGE);
            logger.error(e.getMessage(), e);
        }

    }
    /**
     * Sets parent reference
     * @param parent 
     */
    void setParentReference(ComponentProfile parent) {
        this.parent = parent;
    }
}
