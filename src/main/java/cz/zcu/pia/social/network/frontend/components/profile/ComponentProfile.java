/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.profile;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
public class ComponentProfile extends VerticalLayout {

    @Autowired
    private MessagesLoader msgs;
    private CustomLayout layout = new CustomLayout("profile");
    
    public ComponentProfile(){
        this.setSizeUndefined();
        layout.setSizeUndefined();
    }
    
    @PostConstruct
    public void postConstruct() {
        this.addComponent(layout);
        Label picture = new Label("IF  picture is required, picture here");
        picture.setWidth(100, Unit.PIXELS);
        picture.setHeight(100, Unit.PIXELS);
        layout.addComponent(picture, "picture");

        layout.addComponent(new Label("Frantisek Kolenak"), "name");
        
        layout.addComponent(new Label(msgs.getMessage("username")), "usernameLabel");
        layout.addComponent(new Label(msgs.getMessage("number-of-posts")), "numberPostsLabel");
        layout.addComponent(new Label(msgs.getMessage("number-of-followers")), "numberFollowersLabel");
        layout.addComponent(new Button(msgs.getMessage("edit")), "button");
    }
}
