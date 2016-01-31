/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.profile.profile;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import cz.zcu.pia.social.network.backend.services.FileService;
import cz.zcu.pia.social.network.helpers.Constants;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.io.FilenameUtils;
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
     * file that is saved
     */
    public File file;
    
    @Autowired
    private FileService fileService;

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

            fileService.createBasicFolders();
            file = fileService.createFile(Constants.BASE_PATH + securityHelper.getLogedInUser().getId() + "-" + Math.abs((int) (Math.random() * 10000000)) + "." + FilenameUtils.getExtension(filename));
            if (file == null) {
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

            fileService.resizeImage(file);
            // Show the uploaded file in the image viewer
            if(parent != null){
                parent.reloadImage();
            }
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
