/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.views;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import cz.zcu.pia.social.network.frontend.components.posts.Post;
import cz.zcu.pia.social.network.frontend.components.posts.PostAdd;
import java.util.Date;
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

    @Autowired
    private ApplicationContext applicationContext;
    private ComboBox filterBy;
    private ComboBox filter;
    private Button addPost;

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
        addPost.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                addButtonFunction(event);

            }
        });
        addPost.setDescription(msgs.getMessage(BUTTON_DESCRIPTION));
        addPost.setWidth(25, Unit.PIXELS);
        HorizontalLayout addButtonWrapper = new HorizontalLayout();
        addButtonWrapper.setSizeFull();

        addButtonWrapper.addComponent(filterBy);
        addButtonWrapper.addComponent(filter);
        addButtonWrapper.addComponent(addPost);
        addButtonWrapper.setComponentAlignment(addPost, Alignment.TOP_RIGHT);
        this.getContentWrapper().addComponent(addButtonWrapper);

        //long postId, String name, int numberOflikes, int numberOfdisagrees, String postMessage, int numberOfComments) {
        Post post = applicationContext.getBean(Post.class, 1, "Frantisek Kolenak", new Date(), 3, 1, "This is post message", 3);
        Post post2 = applicationContext.getBean(Post.class, 1, "Frantisek Kolenak", new Date(), 3, 1, "This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.This is very long post message.", 3);

        this.getContentWrapper().addComponent(post);
        this.getContentWrapper().addComponent(post2);

    }

    private void addButtonFunction(Button.ClickEvent event) {
        Window subWindow = new Window(msgs.getMessage("post.add.new"));
        subWindow.setModal(true);
        subWindow.center();
        subWindow.setWidth(400, Unit.PIXELS);
        subWindow.setHeight(300, Unit.PIXELS);
        PostAdd postAdd = applicationContext.getBean(PostAdd.class);
        postAdd.setMargin(true);
        subWindow.setContent(postAdd);
        UI.getCurrent().addWindow(subWindow);
    }

}
