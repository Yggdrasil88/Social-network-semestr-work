/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.views;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import cz.zcu.pia.social.network.MyUI;
import cz.zcu.pia.social.network.frontend.components.posts.ComponentPost;
import cz.zcu.pia.social.network.frontend.components.posts.ComponentPostAdd;
import cz.zcu.pia.social.network.frontend.components.posts.ComponentPostPaginator;
import cz.zcu.pia.social.network.frontend.components.posts.ComponentPostsFilter;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.VaadinView;

/**
 * View Home
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
@VaadinView(ViewHome.NAME)
public class ViewHome extends ViewBase {

    public static final String NAME = "";
    public static final String BUTTON_DESCRIPTION = "view.home.add-description";

    private static final Logger logger = LoggerFactory.getLogger(ViewHome.class);
    /**
     * ApplicationContext
     */
    @Autowired
    private ApplicationContext applicationContext;
    /**
     * Security Helper
     */
    @Autowired
    private SecurityHelper securityHelper;
    /**
     * Component Posts Filter
     */
    private ComponentPostsFilter postsFilter;
    /**
     * Component Post Paginator
     */
    private ComponentPostPaginator postPaginator;

    /**
     * Add post button
     */
    private Button addPost;
    /**
     * post Wrapper
     */
    private final VerticalLayout postWrapper;
    /**
     * Constructor
     */
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
        postPaginator = applicationContext.getBean(ComponentPostPaginator.class);

        postsFilter = applicationContext.getBean(ComponentPostsFilter.class, postWrapper, postPaginator);
        //postsFilter.setPostPaginator(postPaginator);
        postPaginator.setFilter(postsFilter);

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

        addButtonWrapper.addComponent(postsFilter);
        addButtonWrapper.addComponent(addPost);
        addButtonWrapper.setComponentAlignment(addPost, Alignment.TOP_RIGHT);

        addButtonWrapper.setExpandRatio(postsFilter, 10);
        addButtonWrapper.setExpandRatio(addPost, 1);
        this.getContentWrapper().addComponent(addButtonWrapper);
        this.getContentWrapper().addComponent(postWrapper);
        this.getContentWrapper().addComponent(postPaginator);
    }
    /**
     * Add button function
     * @param event  click event
     */
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
    /**
     * Reload what is needed
     */
    public void reload() {
        postsFilter.reload();
    }

}
