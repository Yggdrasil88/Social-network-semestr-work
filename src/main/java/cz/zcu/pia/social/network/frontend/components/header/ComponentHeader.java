package cz.zcu.pia.social.network.frontend.components.header;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.MyUI;
import cz.zcu.pia.social.network.backend.entities.Users;
import cz.zcu.pia.social.network.frontend.views.ViewHome;
import cz.zcu.pia.social.network.frontend.views.ViewLogin;
import cz.zcu.pia.social.network.frontend.views.ViewProfile;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import cz.zcu.pia.social.network.helpers.SecurityHelper;

/**
 * Represents header of the app / menubar
 *
 * @author Frantisek Kolenak
 *
 */
@Component
@Scope("prototype")
public class ComponentHeader extends HorizontalLayout {
    
    private static final String CLASS_NAME = "header";
    private static final int HEADER_WIDTH = 100;
    private static final int HEADER_HEIGHT = 150;
    /**
     * Messages helper
     */
    @Autowired
    private MessagesLoader msgs;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory
        .getLogger(ComponentHeader.class);
    /**
     * Sec. helper
     */
    @Autowired
    private SecurityHelper securityHelper;
    /**
     * Selected menu item
     */
    private MenuItem selectedMenu;
    /**
     * Menubar
     */
    private MenuBar menuBar;
    /**
     * Menu
     */
    private MenuBar menu;
    /**
     * Image layout
     */
    private VerticalLayout imageSide;
    /**
     * Menu layout
     */
    private VerticalLayout menuSide;

    /**
     * Users name info
     */
    private Label userInfo;
    /**
     * Login button
     */
    private Button loginButton;

    /**
     * PostConstruct
     */
    @PostConstruct
    public void postConstruct() {
        setStyleName(CLASS_NAME);

        // Init
        imageSide = createImageLayout();

        HorizontalLayout rightSide = new HorizontalLayout();
        rightSide.setSizeFull();
        menuSide = createMenuLayout();

        addComponent(imageSide);
        addComponent(rightSide);

        rightSide.addComponent(menuSide);

        rightSide.setComponentAlignment(menuSide, Alignment.TOP_RIGHT);

        setWidth(HEADER_WIDTH, Unit.PERCENTAGE);
        setHeight(HEADER_HEIGHT, Unit.PIXELS);

    }

    /**
     * Creates image layout
     *
     * @return layout
     */
    private VerticalLayout createImageLayout() {
        VerticalLayout imageLayout = new VerticalLayout();
        imageLayout.setStyleName(CLASS_NAME + "-image");

        Label l = new Label();
        // l.setIcon(new ThemeResource("./images/logo/logo.png"));
        l.setSizeUndefined();
        imageLayout.addComponent(l);
        imageLayout.setComponentAlignment(l, Alignment.BOTTOM_LEFT);
        imageLayout.setHeight(HEADER_HEIGHT, Unit.PIXELS);
        imageLayout.setWidth(HEADER_WIDTH / 2, Unit.PIXELS);
        return imageLayout;
    }

    /**
     * Creates menubar
     *
     * @return menubar
     */
    private MenuBar createMenuBar() {
        menuBar = new MenuBar();
        menuBar.setStyleName(CLASS_NAME + "-menu");
        
        if(securityHelper.isAuthenticated()){
            addLogedInMenu();
        } else {
            addNotLogedInMenu();
        }
        
        for (MenuItem i : menuBar.getItems()) {
            i.setStyleName(CLASS_NAME + "-menuitem");

        }
        menuBar.setHeight(35, Unit.PIXELS);
        return menuBar;

    }
    /**
     * Adds menu for logged in users
     */
    public void addLogedInMenu(){
        menuBar.removeItems();
        addMenuItem(msgs.getMessage("header.home"));
        addMenuItem(msgs.getMessage("header.profile"));
    }
    /**
     * Adds basic menu all users have
     */
    public void addNotLogedInMenu() {
        menuBar.removeItems();
        addMenuItem(msgs.getMessage("header.home"));
    }

    /**
     * Adds menuitem
     *
     * @param message
     */
    private void addMenuItem(String title) {
        MenuItem item = menuBar.addItem(title, null, menuAction());
        item.setStyleName(CLASS_NAME + "-menuitem");
    }

    /**
     * Sets selected menu item
     *
     * @param menuName
     */
    public void setSelectedMenuItem(String menuName) {
        for (MenuItem item : menuBar.getItems()) {
            
            if (item.getText().equals(menuName)) {
                setActiveItem(item);
            }
        }
    }
    /**
     * Sets selected menu item, by number. 
     * Number is the position in menu, starts with 1.
     *  
     * @param menuNumber menu number
     */
    public void setSelectedMenuItem(int menuNumber) {
        int tmp = 1;
        for (MenuItem item : menuBar.getItems()) {
            if (tmp == menuNumber) {
                setActiveItem(item);
            }
            tmp++;
        }
    }

    /**
     * Command when menuitem is clicked
     *
     * @return commnad
     */
    private MenuBar.Command menuAction() {
        MenuBar.Command mycommand = new MenuBar.Command() {
            @Override
            public void menuSelected(MenuItem selectedItem) {
                if (selectedMenu == selectedItem) {
                    return;
                }
                setActiveItem(selectedItem);

                String value = selectedItem.getText();
                if (msgs.getMessage("header.home").equals(value)) {
                    ((MyUI) UI.getCurrent().getUI()).getNavigator()
                        .navigateTo(ViewHome.NAME);
                } else if (msgs.getMessage("header.profile").equals(value)) {
                    ((MyUI) UI.getCurrent().getUI()).getNavigator()
                        .navigateTo(ViewProfile.NAME);
                }

            }

        };
        return mycommand;
    }

    /**
     * Sets active menuitem
     *
     * @param selectedItem menuitem selected
     */
    private void setActiveItem(MenuItem selectedItem) {

        if (selectedMenu != null) {
            selectedMenu.setStyleName(CLASS_NAME + "-menuitem");
        }

        this.selectedMenu = selectedItem;
        if (selectedItem != null) {
            selectedItem.setStyleName(CLASS_NAME + "-menuitem" + " active");
        }
    }

    /**
     * Creates menu layout
     *
     * @return layout
     */
    private VerticalLayout createMenuLayout() {
        VerticalLayout menuLayout = new VerticalLayout();
        menuLayout.setSizeFull();
        menu = createMenuBar();

        HorizontalLayout userLayout = createUserLayout();

        userLayout.setSizeFull();

        HorizontalLayout menuHolder = new HorizontalLayout();
        menuHolder.addComponent(menu);
        menuHolder.setComponentAlignment(menu, Alignment.BOTTOM_RIGHT);
        menuHolder.setSizeFull();

        menuLayout.addComponent(userLayout);
        menuLayout.addComponent(menuHolder);

        menuLayout.setMargin(false);

        menuLayout.setWidth(HEADER_WIDTH, Unit.PERCENTAGE);
        menuLayout.setHeight(HEADER_HEIGHT, Unit.PIXELS);

        return menuLayout;
    }

    /**
     * Creates user layout
     *
     * @return layout
     */
    private HorizontalLayout createUserLayout() {
        HorizontalLayout userLayout = new HorizontalLayout();
        userLayout.setSizeFull();
        userInfo = createNameLabel("");
        loginButton = createLogButton();

        userLayout.addComponent(userInfo);
        userLayout.addComponent(loginButton);

        userLayout.setExpandRatio(userInfo, 2);
        userLayout.setExpandRatio(loginButton, 1);

        userLayout.setComponentAlignment(userInfo, Alignment.TOP_RIGHT);
        userLayout.setComponentAlignment(loginButton, Alignment.TOP_RIGHT);

        userLayout.setWidth(HEADER_WIDTH, Unit.PERCENTAGE);
        userLayout.setHeight(HEADER_HEIGHT / 2, Unit.PIXELS);

        return userLayout;
    }

    /**
     * Link to logout
     *
     * @return logout button
     */
    private Button createLogButton() {
        Button logButton;
        if (securityHelper.isAuthenticated()) {
            logButton = new Button(msgs.getMessage("header.logout"));
            setUsersFullName(securityHelper.getLogedInUser().getName() + " " + securityHelper.getLogedInUser().getSurname());
        } else {
            logButton = new Button(msgs.getMessage("header.login"));
        }
        
        logButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {

                if (securityHelper.isAuthenticated()) {
                    securityHelper.setLogedInUser(null);
                    ((MyUI) UI.getCurrent().getUI()).getNavigator()
                        .navigateTo(ViewHome.NAME);
                    
                    setUsersFullName("");
                    addNotLogedInMenu();
                } else {
                    
                    ((MyUI) UI.getCurrent().getUI()).getNavigator()
                        .navigateTo(ViewLogin.NAME);
                    setActiveItem(null);
                }
                setLoginButtonCaption();
                event.getButton().setCaption(msgs.getMessage("header.login"));

            }
        });
        logButton.setSizeUndefined();
        return logButton;
    }

    /**
     * Creates label with users name
     *
     * @param name name to set
     * @return label
     */
    private Label createNameLabel(String name) {
        Label l = new Label(name);
        l.setSizeUndefined();
        l.setStyleName(CLASS_NAME + "-name");
        return l;
    }

    /**
     * Gets menubar
     *
     * @return
     */
    public MenuBar getMenuBar() {
        return this.menu;
    }
    /**
     * Sets users name to the header
     * @param fullName users name
     */
    public void setUsersFullName(String fullName) {
        userInfo.setValue(fullName);
    }
    /**
     * Sets users name to the header
     * @param user user
     */
    public void setUsersFullName(Users user) {
        userInfo.setValue(user.getName() + " " + user.getSurname());
    }
    /**
     * Sets correct caption to the login/out button
     */
    public void setLoginButtonCaption() {
        if (securityHelper.isAuthenticated()) {
            loginButton.setCaption(msgs.getMessage("header.logout"));
        } else {
            loginButton.setCaption(msgs.getMessage("header.login"));
        }
    }


}
