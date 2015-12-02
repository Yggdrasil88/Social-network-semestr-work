/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.posts;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.Visibility;
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
public class ComponentPostAdd extends VerticalLayout {

    @Autowired
    private MessagesLoader msgs;
    @Autowired
    private Visibility visibility;
    
    private CustomLayout layout = new CustomLayout("addPost");
    
    
    
    private ComboBox visibilityComboBox;
    private TextArea message = new TextArea();
    private HorizontalLayout tags;
    private TextField newTag;
    private Button confirmButton;

    public ComponentPostAdd() {
        this.setSpacing(true);

        message.setWidth(100, Unit.PERCENTAGE);
        message.setHeight(100, Unit.PIXELS);

        tags = new HorizontalLayout();
        tags.setSpacing(true);

        newTag = new TextField();
        /**
         * DELETE LATER
         */
        CustomLayout tag1 = new CustomLayout("tag");

        CustomLayout tag2 = new CustomLayout("tag");
        CustomLayout tag3 = new CustomLayout("tag");


        tag1.addComponent(new Button("dwq"), "button");
        tag2.addComponent(new Button("oiuyt"), "button");
        tag3.addComponent(new Button("tag1"), "button");


        tags.addComponent(tag1);
        tags.addComponent(tag2);
        tags.addComponent(tag3);


    }

    @PostConstruct
    public void postConstruct() {
        visibilityComboBox = visibility.getVisibilityComboBox();
        
        
        confirmButton = new Button(msgs.getMessage("post.add.new"));

        this.addComponent(layout);

        layout.addComponent(visibilityComboBox, "selector");
        layout.addComponent(message, "message");
        layout.addComponent(tags, "tags");

        layout.addComponent(newTag, "newTag");

        layout.addComponent(confirmButton, "okButton");

    }
}
