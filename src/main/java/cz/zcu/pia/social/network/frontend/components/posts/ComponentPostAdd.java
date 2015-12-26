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
import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.entities.PostTags;
import cz.zcu.pia.social.network.backend.entities.Tag;
import cz.zcu.pia.social.network.backend.services.services.impl.PostService;
import cz.zcu.pia.social.network.backend.services.services.impl.PostTagsService;
import cz.zcu.pia.social.network.backend.services.services.impl.TagService;
import cz.zcu.pia.social.network.frontend.handlers.OnEnterKeyHandler;
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
    private MessagesLoader msgs;
    @Autowired
    private Visibility visibility;

    private CustomLayout layout = new CustomLayout("addPost");

    private ComboBox visibilityComboBox;
    private TextArea message = new TextArea();
    private HorizontalLayout tags;
    private TextField newTag;
    private Button confirmButton;
    private List<Button> taglist = new ArrayList();
    private Panel panel;

    @Autowired
    private PostService postService;
    @Autowired
    private PostTagsService postTagsService;
    @Autowired
    private TagService tagService;
    @Autowired
    private SecurityHelper securityHelper;

    public ComponentPostAdd() {

        this.setSpacing(true);

        message.setWidth(100, Unit.PERCENTAGE);
        message.setHeight(100, Unit.PIXELS);

        tags = new HorizontalLayout();
        tags.setStyleName("");
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
                logger.debug("Clicked the button");
                if (message.getValue().equals("") || message.getValue() == null) {
                    Notification.show(msgs.getMessage("post.add.empty-msg"));
                    return;
                }
                //Create all tags
                List<Tag> createdTags = createTags();
                //Save all new tags, ignore tags that are already in the database
                int error = tagService.saveTags(createdTags);
                // load tags with correct ids
                createdTags = tagService.getTagsByName(createdTags);
                
                for (Tag t : createdTags) {
                    logger.debug("ID: " + t.getId() + "TAGNAME: " + t.getTagName());
                }
                Post post;
                if (visibilityComboBox.getValue().equals(visibility.getPublicValue())) {
                    post = new Post(securityHelper.getLogedInUser(), message.getValue());
                } else {
                    post = new Post(securityHelper.getLogedInUser(), message.getValue(), Visibility.FRIENDS);
                }
                
                //Save post
                postService.persist(post);
                // Save posts tags
                if (createdTags == null || createdTags.size() > 0) {
                    PostTags postTags = new PostTags(createdTags, post);
                    postTagsService.persist(postTags);
                }

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

    private List<Tag> createTags() {
        List<Tag> tags = new ArrayList();
        for (Button tag : taglist) {
            Tag t = new Tag(tag.getCaption());
            tags.add(t);
        }
        return tags;
    }
}
