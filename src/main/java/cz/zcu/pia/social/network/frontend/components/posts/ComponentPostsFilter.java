/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.posts;

import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.backend.entities.Post;
import cz.zcu.pia.social.network.backend.entities.Tag;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.backend.services.services.impl.PostService;
import cz.zcu.pia.social.network.backend.services.services.impl.PostTagsService;
import cz.zcu.pia.social.network.backend.services.services.impl.TagService;
import cz.zcu.pia.social.network.backend.services.services.impl.UsersService;
import cz.zcu.pia.social.network.helpers.Constants;
import cz.zcu.pia.social.network.helpers.FilterValues;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import cz.zcu.pia.social.network.helpers.Visibility;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope(scopeName = "prototype")
public class ComponentPostsFilter extends HorizontalLayout {

    private static final Logger logger = LoggerFactory.getLogger(ComponentPostsFilter.class);

    @Autowired
    private SecurityHelper securityHelper;
    @Autowired
    private TagService tagsService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private PostService postService;
    @Autowired
    private PostTagsService postTagsService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private Visibility visibilityHelper;
    private int currentPage = 0;
    private ComponentPostPaginator postPaginator;
    /**
     * Messages helper
     */
    @Autowired
    protected MessagesLoader msgs;

    private final ComboBox filterBy;
    private final ComboBox filter;
    private final VerticalLayout postsWrapper;

    public ComponentPostsFilter(VerticalLayout postsWrapper, ComponentPostPaginator postPaginator) {
        this.postsWrapper = postsWrapper;

        filterBy = new ComboBox();
        filterBy.setNullSelectionAllowed(false);

        filter = new ComboBox();
        filter.setNullSelectionAllowed(false);
        this.addComponent(filterBy);
        this.addComponent(filter);

        this.setSpacing(true);
        this.postPaginator = postPaginator;
    }

    @PostConstruct
    public void postConstruct() {
        filterBy.setDescription(msgs.getMessage("filter.by.description"));

        filterBy.addItem(msgs.getMessage(FilterValues.FILTER_NAME));
        filterBy.addItem(msgs.getMessage(FilterValues.FILTER_FROM));
        filterBy.addItem(msgs.getMessage(FilterValues.FILTER_TAG));

        filterBy.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                filterBytMethod(event.getProperty().getValue());
            }
        });
        filter.addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                currentPage = 0;

                filterMethod(event.getProperty().getValue());
            }
        });
        filterBy.setValue(msgs.getMessage(FilterValues.FILTER_FROM));

    }

    private void filterBytMethod(Object filterValue) {
        String value = (String) filterValue;
        if (value.equals(msgs.getMessage(FilterValues.FILTER_FROM))) {
            filter.removeAllItems();
            filter.addItem(visibilityHelper.getPublicValue());
            if (securityHelper.isAuthenticated()) {
                filter.addItem(visibilityHelper.getFriendsValue());
                filter.addItem(visibilityHelper.getFollowingValue());
            }
            filter.setValue(visibilityHelper.getPublicValue());
        } else if (value.equals(msgs.getMessage(FilterValues.FILTER_TAG))) {
            filter.removeAllItems();
            List<Tag> tags = tagsService.findAll();
            for (Tag tag : tags) {
                filter.addItem(tag.getTagName());
            }
        } else if (value.equals(msgs.getMessage(FilterValues.FILTER_NAME))) {
            filter.removeAllItems();
            List<Users> users = usersService.findAll();
            for (Users user : users) {
                UserInfo userInfo = new UserInfo(user.getFullname(), user.getUsername());
                filter.addItem(userInfo);
            }
        }
    }

    private void filterMethod(Object filterValue) {
        if (filterValue == null) {
            return;
        }
        if (filterBy.getValue().equals(msgs.getMessage(FilterValues.FILTER_FROM))) {

            String value = (String) filterValue;

            if (value.equals(visibilityHelper.getPublicValue())) {
                addPublicPosts();
            } else if (value.equals(visibilityHelper.getFriendsValue())) {
                addFriendsPosts();
            } else if (value.equals(visibilityHelper.getFollowingValue())) {
                addFollowingPosts();
            }
        } else if (filterBy.getValue().equals(msgs.getMessage(FilterValues.FILTER_TAG))) {
            String value = (String) filterValue;

            addPostsByTag(value);
        } else if (filterBy.getValue().equals(msgs.getMessage(FilterValues.FILTER_NAME))) {
            UserInfo userInfo = (UserInfo) filterValue;

            addPostsByName(userInfo);
        }
    }

    private void addPublicPosts() {
        postsWrapper.removeAllComponents();
        List<Post> posts = postService.getPublicPosts();
        postPaginatorAddButtons(posts.size() / Constants.PAGE_LENGTH);
        int i = 0;
        for (Post p : posts) {
            if (i >= currentPage * Constants.PAGE_LENGTH) {
                ComponentPost post = applicationContext.getBean(ComponentPost.class, p.getId(), p.getUser().getFullname(), p.getDateSent(), p.getLikeCount(), p.getHateCount(), p.getMessage(), p.getNumberOfComments());
                postsWrapper.addComponent(post);
            }
            if (i == currentPage * Constants.PAGE_LENGTH + Constants.PAGE_LENGTH - 1) {
                break;
            }
            i++;
        }
    }

    private void postPaginatorAddButtons(int paginatorSize) {
        if (postPaginator != null) {
            postPaginator.addButtons(paginatorSize);
        }
    }

    private void addFriendsPosts() {
        postsWrapper.removeAllComponents();
        Long userId = securityHelper.getLogedInUser().getId();
        List<Post> posts = postService.getFriendsPosts(userId);
        postPaginatorAddButtons(posts.size() / Constants.PAGE_LENGTH);

        int i = 0;
        for (Post p : posts) {
            if (i >= currentPage * Constants.PAGE_LENGTH) {
                ComponentPost post = applicationContext.getBean(ComponentPost.class, p.getId(), p.getUser().getFullname(), p.getDateSent(), p.getLikeCount(), p.getHateCount(), p.getMessage(), p.getNumberOfComments());
                postsWrapper.addComponent(post);
            }
            if (i == currentPage * Constants.PAGE_LENGTH + Constants.PAGE_LENGTH - 1) {
                break;
            }
            i++;
        }
    }

    private void addFollowingPosts() {
        postsWrapper.removeAllComponents();
        Long userId = securityHelper.getLogedInUser().getId();
        List<Post> posts = postService.getFollowingPosts(userId);
        postPaginatorAddButtons(posts.size() / Constants.PAGE_LENGTH);

        int i = 0;
        for (Post p : posts) {
            if (i >= currentPage * Constants.PAGE_LENGTH) {
                ComponentPost post = applicationContext.getBean(ComponentPost.class, p.getId(), p.getUser().getFullname(), p.getDateSent(), p.getLikeCount(), p.getHateCount(), p.getMessage(), p.getNumberOfComments());
                postsWrapper.addComponent(post);
            }
            if (i == currentPage * Constants.PAGE_LENGTH + Constants.PAGE_LENGTH - 1) {
                break;
            }
            i++;
        }
    }

    private void addPostsByTag(String tagName) {

        postsWrapper.removeAllComponents();
        List<Post> posts = postTagsService.getPostsByTag(tagName);
        postPaginatorAddButtons(posts.size() / Constants.PAGE_LENGTH);

        int i = 0;
        for (Post p : posts) {
            if (i >= currentPage * Constants.PAGE_LENGTH) {
                ComponentPost post = applicationContext.getBean(ComponentPost.class, p.getId(), p.getUser().getFullname(), p.getDateSent(), p.getLikeCount(), p.getHateCount(), p.getMessage(), p.getNumberOfComments());
                postsWrapper.addComponent(post);
            }
            if (i == currentPage * Constants.PAGE_LENGTH + Constants.PAGE_LENGTH - 1) {
                break;
            }
            i++;
        }
    }

    private void addPostsByName(UserInfo user) {
        postsWrapper.removeAllComponents();
        List<Post> posts = postService.getPostsByUsername(user.getUsername());
        postPaginatorAddButtons(posts.size() / Constants.PAGE_LENGTH);

        int i = 0;
        for (Post p : posts) {
            if (i >= currentPage * Constants.PAGE_LENGTH) {
                ComponentPost post = applicationContext.getBean(ComponentPost.class, p.getId(), p.getUser().getFullname(), p.getDateSent(), p.getLikeCount(), p.getHateCount(), p.getMessage(), p.getNumberOfComments());
                postsWrapper.addComponent(post);
            }
            if (i == currentPage * Constants.PAGE_LENGTH + Constants.PAGE_LENGTH - 1) {
                break;
            }
            i++;
        }
    }

    public void setPostPaginator(ComponentPostPaginator postPaginator) {
        this.postPaginator = postPaginator;
    }

    public void reload() {
        this.filterMethod(filter.getValue());
    }

    public void setPageAndReload(int page) {
        logger.debug("Page given: {}", page);

        this.currentPage = page;
        reload();
    }

    private class UserInfo {

        private String fullname;

        private String username;

        public UserInfo(String fullname, String username) {
            this.fullname = fullname;
            this.username = username;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return fullname + " (" + username + ")";
        }

    }

}
