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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.entities.PostTags;
import cz.zcu.pia.social.network.backend.entities.Tag;
import cz.zcu.pia.social.network.backend.services.services.impl.PostService;
import cz.zcu.pia.social.network.backend.services.services.impl.PostTagsService;
import cz.zcu.pia.social.network.backend.services.services.impl.TagService;
import cz.zcu.pia.social.network.backend.services.services.impl.UsersService;
import cz.zcu.pia.social.network.frontend.handlers.OnEnterKeyHandler;
import cz.zcu.pia.social.network.frontend.views.ViewHome;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import cz.zcu.pia.social.network.helpers.Visibility;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(ComponentPostAdd.class);
    @Autowired
    protected MessagesLoader msgs;
    @Autowired
    private Visibility visibility;

    protected CustomLayout layout = new CustomLayout("addPost");

    protected ComboBox visibilityComboBox;
    protected TextArea message = new TextArea();
    protected HorizontalLayout tags;
    protected TextField newTag;
    private Button confirmButton;
    private List<Button> taglist = new ArrayList();
    private Panel panel;
    
    private ViewHome parentReference;
    @Autowired
    private UsersService usersService;
    @Autowired
    protected PostService postService;
    @Autowired
    private PostTagsService postTagsService;
    @Autowired
    private TagService tagService;
    @Autowired
    protected SecurityHelper securityHelper;
    private Window subWindow;

    public ComponentPostAdd() {

        this.setSpacing(true);

        message.setWidth(100, Unit.PERCENTAGE);
        message.setHeight(100, Unit.PIXELS);

        tags = new HorizontalLayout();
        tags.setSpacing(true);
        tags.setSizeUndefined();

        newTag = new TextField();
        newTag.setImmediate(true);
        OnEnterKeyHandler onEnterHandler = new OnEnterKeyHandler() {
            @Override
            public void onEnterKeyPressed() {
                CustomLayout tag = new CustomLayout("tag");
                Button tagsButton = new Button(newTag.getValue());
                taglist.add(tagsButton);
                newTag.setValue("");
                newTag.focus();
                tagsButton.addClickListener(new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        tags.removeComponent(event.getButton().getParent());
                        taglist.remove(event.getButton());
                    }
                });

                tag.addComponent(tagsButton, "button");

                tags.addComponent(tag);
            }
        };
        onEnterHandler.installOn(newTag);

    }

    @PostConstruct
    public void postConstruct() {
        visibilityComboBox = visibility.getVisibilityComboBox();

        confirmButton = new Button(msgs.getMessage("post.add.new"));
        confirmButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                postButtonFunction(event);
            }

        });
        this.addComponent(layout);

        layout.addComponent(visibilityComboBox, "selector");
        layout.addComponent(message, "message");
        panel = new Panel("scroll");
        panel.setSizeFull();
        panel.setContent(tags);
        layout.addComponent(panel, "tags");

        layout.addComponent(newTag, "newTag");

        layout.addComponent(confirmButton, "okButton");

    }

    protected void postButtonFunction(Button.ClickEvent event) {
        if (message.getValue().equals("") || message.getValue() == null) {
            Notification.show(msgs.getMessage("post.add.empty-msg"),Notification.Type.ERROR_MESSAGE);
            return;
        }
        //Create all tags
        List<String> createdTags = createTags();
        // Load tags I do have in database
        List<Tag> tags = tagService.getTagsByName(createdTags);
        if (createdTags.size() != tags.size()) {
            List<Tag> tagsToSave = createNotFoundTags(createdTags, tags);
            tagService.saveTags(tagsToSave);
            tags = mergeTags(tags, tagsToSave);
        }

        Post post;
        if (visibilityComboBox.getValue().equals(visibility.getPublicValue())) {
            post = new Post(securityHelper.getLogedInUser(), message.getValue());
        } else {
            post = new Post(securityHelper.getLogedInUser(), message.getValue(), Visibility.FRIENDS);
        }
        
        //Save post
        postService.persist(post);
        securityHelper.getLogedInUser().setTotalPosts(securityHelper.getLogedInUser().getTotalPosts() + 1);
        usersService.update(securityHelper.getLogedInUser());
        // Save posts tags
        if (tags != null && !createdTags.isEmpty()) {
            PostTags postTags = new PostTags(tags, post);
            postTagsService.persist(postTags);
        }
        Notification.show(msgs.getMessage("post.posted"), Notification.Type.HUMANIZED_MESSAGE);
        parentReference.reload();
        subWindow.close();
    }

    private List<Tag> mergeTags(List<Tag> tags, List<Tag> saveTags) {
        for (Tag t : saveTags) {
            tags.add(t);
        }
        return tags;
    }

    private List<Tag> createNotFoundTags(List<String> allTags, List<Tag> foundTags) {

        List<Tag> tagsToSave = new ArrayList();
        for (String name : allTags) {
            boolean found = false;
            for (Tag t : foundTags) {
                if (name.equals(t.getTagName())) {
                    found = true;
                    break;
                }

            }
            if (!found) {
                tagsToSave.add(new Tag(name));
            }
        }

        return tagsToSave;
    }

    private List<String> createTags() {
        List<String> tags = new ArrayList();
        for (Button tag : taglist) {
            tags.add(tag.getCaption());
        }
        return tags;
    }

    public void setComponentParent(ViewHome parentReference) {
        this.parentReference = parentReference;
    }

    public void setWindow(Window subWindow) {
        this.subWindow = subWindow;
    }
}
