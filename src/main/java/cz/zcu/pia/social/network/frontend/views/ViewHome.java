/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.views;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import cz.zcu.pia.social.network.MyUI;
import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.services.services.impl.PostService;
import cz.zcu.pia.social.network.frontend.components.posts.ComponentPost;
import cz.zcu.pia.social.network.frontend.components.posts.ComponentPostAdd;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.VaadinView;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
@VaadinView(ViewHome.NAME)
public class ViewHome extends ViewBase {

    public static final String NAME = "";
    public static final String BUTTON_DESCRIPTION = "view.home.add-description";

    private static final Logger logger = LoggerFactory.getLogger(ViewHome.class);
    private int currentPage = 0;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private SecurityHelper securityHelper;
    @Autowired
    private PostService postService;
    private ComboBox filterBy;
    private ComboBox filter;
    private Button addPost;
    private VerticalLayout postWrapper;

    public ViewHome() {
        super();
        postWrapper = new VerticalLayout();
        postWrapper.setSizeUndefined();
        postWrapper.setSpacing(true);
    }

    /**
     * PostConstruct
     */
    @PostConstruct
    @Override
    public void postConstruct() {
        super.postConstruct();

        filterBy = new ComboBox();
        filter = new ComboBox();
        filterBy.addItem("Filtrovat podle (tag,jmeno...)");
        filter.addItem("hodnota filtru");

        filterBy.setValue("Filtrovat podle (tag,jmeno...)");
        filter.setValue("hodnota filtru");

        addPost = new Button("+");
        if (!securityHelper.isAuthenticated()) {
            addPost.setVisible(false);
        }
        addPost.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                addButtonFunction(event);

            }
        });
        addPost.setDescription(msgs.getMessage(BUTTON_DESCRIPTION));
        addPost.setWidth(25, Unit.PIXELS);
        HorizontalLayout addButtonWrapper = new HorizontalLayout();

        addButtonWrapper.setWidth(ComponentPost.POST_WIDTH, Unit.PIXELS);

        addButtonWrapper.addComponent(filterBy);
        addButtonWrapper.addComponent(filter);
        addButtonWrapper.addComponent(addPost);
        addButtonWrapper.setComponentAlignment(addPost, Alignment.TOP_RIGHT);

        addButtonWrapper.setExpandRatio(filterBy, 5);
        addButtonWrapper.setExpandRatio(filter, 5);
        addButtonWrapper.setExpandRatio(addPost, 1);
        this.getContentWrapper().addComponent(addButtonWrapper);
        this.getContentWrapper().addComponent(postWrapper);
        
        addPublicPosts();

    }

    private void addButtonFunction(Button.ClickEvent event) {
        Window subWindow = new Window(msgs.getMessage("post.add.new"));
        subWindow.setModal(true);
        subWindow.center();
        subWindow.setWidth(400, Unit.PIXELS);
        subWindow.setHeight(300, Unit.PIXELS);
        ComponentPostAdd postAdd = applicationContext.getBean(ComponentPostAdd.class);
        postAdd.setComponentParent(this);
        postAdd.setWindow(subWindow);
        postAdd.setMargin(true);
        subWindow.setContent(postAdd);
        UI.getCurrent().addWindow(subWindow);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        ((MyUI) UI.getCurrent()).getHeader().setSelectedMenuItem(1);
    }

    public void reload() {
        Notification.show("VIEW HOME RELOAD", Notification.Type.ERROR_MESSAGE);
    }

    private void addPublicPosts() {
        postWrapper.removeAllComponents();
        List<Post> publicPosts = postService.getPublicPosts(currentPage);
        for (Post p : publicPosts) {
            
            ComponentPost post = applicationContext.getBean(ComponentPost.class, p.getId(), p.getUser().getFullname(), p.getDateSent(), p.getLikeCount(), p.getHateCount(), p.getMessage(), p.getNumberOfComments());
            postWrapper.addComponent(post);
        }

    }
}
