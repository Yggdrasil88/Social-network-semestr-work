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
 * Filter for posts
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope(scopeName = "prototype")
public class ComponentPostsFilter extends HorizontalLayout {

    private static final Logger logger = LoggerFactory.getLogger(ComponentPostsFilter.class);
    /**
     * Security Helper
     */
    @Autowired
    private SecurityHelper securityHelper;
    /**
     * Tag Service
     */
    @Autowired
    private TagService tagsService;
    /**
     * Users Service
     */
    @Autowired
    private UsersService usersService;
    /**
     * Post Service
     */
    @Autowired
    private PostService postService;
    /**
     * Post Tags Service
     */
    @Autowired
    private PostTagsService postTagsService;
    /**
     * Application Context
     */
    @Autowired
    private ApplicationContext applicationContext;
    /**
     * Visibility helper
     */
    @Autowired
    private Visibility visibilityHelper;
    /**
     * currentPage
     */
    private int currentPage = 0;
    /**
     * Component Post Paginator reference
     */
    private final ComponentPostPaginator postPaginator;
    /**
     * Messages helper
     */
    @Autowired
    protected MessagesLoader msgs;
    /**
     * Combobox for filter by
     */
    private final ComboBox filterBy;
    /**
     * Combobox filter
     */
    private final ComboBox filter;
    /**
     * Posts wrapper, this is where it stores posts
     */
    private final VerticalLayout postsWrapper;

    /**
     * Constructor
     *
     * @param postsWrapper postsWrapper reference
     * @param postPaginator postPaginator reference
     */
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

    /**
     * Post construct
     */
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

    /**
     * Filter by method for combobox
     *
     * @param filterValue filter Value
     */
    private void filterBytMethod(Object filterValue) {
        String value = (String) filterValue;
        if (value.equals(msgs.getMessage(FilterValues.FILTER_FROM))) {
            filter.removeAllItems();
            filter.addItem(visibilityHelper.getPublicValue());
            if (securityHelper.isAuthenticated()) {
                filter.addItem(visibilityHelper.getFriendsValue());
                filter.addItem(visibilityHelper.getFollowingValue());
                filter.addItem(visibilityHelper.getMeValue());
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

    /**
     * Filter method for filter combobox
     *
     * @param filterValue filter Value
     */
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
            } else if (value.equals(visibilityHelper.getMeValue())) {
                addUserPosts();
            }
        } else if (filterBy.getValue().equals(msgs.getMessage(FilterValues.FILTER_TAG))) {
            String value = (String) filterValue;

            addPostsByTag(value);
        } else if (filterBy.getValue().equals(msgs.getMessage(FilterValues.FILTER_NAME))) {
            UserInfo userInfo = (UserInfo) filterValue;

            addPostsByName(userInfo);
        }
    }

    /**
     * Adds public posts to the wrapper
     */
    private void addPublicPosts() {
        postsWrapper.removeAllComponents();
        List<Post> posts = postService.getPublicPosts();
        postPaginatorAddButtons((posts.size() - 1) / Constants.PAGE_LENGTH);
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

    /**
     * Adds buttons to paginator
     *
     * @param paginatorSize paginator Size
     */
    private void postPaginatorAddButtons(int paginatorSize) {

        if (postPaginator != null) {
            if (paginatorSize <= 0) {
                postPaginator.addButtons(0);
            } else {
                postPaginator.addButtons(paginatorSize);

            }
        }
    }

    /**
     * Adds friends posts
     */
    private void addFriendsPosts() {
        postsWrapper.removeAllComponents();
        Long userId = securityHelper.getLogedInUser().getId();
        List<Post> posts = postService.getFriendsPosts(userId);
        postPaginatorAddButtons((posts.size() - 1) / Constants.PAGE_LENGTH);

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

    /**
     * Adds following posts
     */
    private void addFollowingPosts() {
        postsWrapper.removeAllComponents();
        Long userId = securityHelper.getLogedInUser().getId();
        List<Post> posts = postService.getFollowingPosts(userId);
        postPaginatorAddButtons((posts.size() - 1) / Constants.PAGE_LENGTH);

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

    /**
     * Adds posts by tag
     *
     * @param tagName tag Name
     */
    private void addPostsByTag(String tagName) {

        postsWrapper.removeAllComponents();
        List<Post> posts = postTagsService.getPostsByTag(tagName);
        postPaginatorAddButtons((posts.size() - 1) / Constants.PAGE_LENGTH);

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

    /**
     * Adds posts by selected user name
     *
     * @param user
     */
    private void addPostsByName(UserInfo user) {
        postsWrapper.removeAllComponents();
        List<Post> posts = postService.getPostsByUsername(user.getUsername());
        postPaginatorAddButtons((posts.size() - 1) / Constants.PAGE_LENGTH);

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

    private void addUserPosts() {
        postsWrapper.removeAllComponents();
        Long userId = securityHelper.getLogedInUser().getId();
        List<Post> posts = postService.getUserPosts(userId);
        postPaginatorAddButtons((posts.size() - 1) / Constants.PAGE_LENGTH);

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

    /**
     * Reload filter
     */
    public void reload() {
        this.filterMethod(filter.getValue());
    }

    /**
     * Sets page and load new posts
     *
     * @param page
     */
    public void setPageAndReload(int page) {
        logger.debug("Page given: {}", page);

        this.currentPage = page;
        reload();
    }

    /**
     * Class for simple info about user
     */
    private class UserInfo {

        /**
         * Fullname
         */
        private String fullname;
        /**
         * Username
         */
        private String username;

        /**
         * Constructor
         *
         * @param fullname fullname
         * @param username username
         */
        public UserInfo(String fullname, String username) {
            this.fullname = fullname;
            this.username = username;
        }

        /**
         * Gets fullname
         *
         * @return fullname
         */
        public String getFullname() {
            return fullname;
        }

        /**
         * Sets fullname
         *
         * @param fullname fullname
         */
        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        /**
         * Gets username
         *
         * @return username
         */
        public String getUsername() {
            return username;
        }

        /**
         * Sets username
         *
         * @param username username
         */
        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return fullname + " (" + username + ")";
        }

    }

}
