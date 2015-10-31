package cz.zcu.pia.social.network.frontend.components.header;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.MyUI;
import cz.zcu.pia.social.network.frontend.views.ViewHome;
import cz.zcu.pia.social.network.helpers.MessagesLoader;

/**
 * Represents header of the app / menubar
 *
 * @author Frantisek Kolenak
 *
 */
@Component
@Scope("prototype")
@SuppressWarnings("serial")
public class ComponentHeader extends HorizontalLayout {

    private final String CLASS_NAME = "header";
    private final int HEADER_WIDTH = 100;
    private final int HEADER_HEIGHT = 150;
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
    // @Autowired
    // private SecurityHelper securityHelper;
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

        // A layout structure used for composition
        addComponent(imageSide);
        addComponent(rightSide);

        rightSide.addComponent(menuSide);

        rightSide.setComponentAlignment(menuSide, Alignment.TOP_RIGHT);

        setWidth(HEADER_WIDTH, Unit.PERCENTAGE);
        setHeight(HEADER_HEIGHT, Unit.PIXELS);

        /*
         if(securityHelper.getLogedInUser() != null){
         if(securityHelper.getLogedInUserRole().equals(Constants.ROLE_MANAGER)){
         addManagerMenu();
         }
         this.setUsersFullName(securityHelper.getLogedInUser().getFullName());
         }
         */
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
        l.setIcon(new ThemeResource("./images/logo/logo.png"));
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

        addBasicMenu();

        for (MenuItem i : menuBar.getItems()) {
            i.setStyleName(CLASS_NAME + "-menuitem");

        }
        menuBar.setHeight(35, Unit.PIXELS);
        return menuBar;

    }

    /**
     * Adds basic menu all users have
     */
    private void addBasicMenu() {
        addMenuItem(msgs.getMessage("header.home"));
        addMenuItem(msgs.getMessage("header.training"));
    }

    /**
     * Adds menu for managers
     */
    public void addManagerMenu() {
        addMenuItem(msgs.getMessage("header.manage"));
        addMenuItem(msgs.getMessage("header.reports"));
        addMenuItem(msgs.getMessage("header.people"));
    }

    /**
     * Remove managers menu
     */
    private void removeManagerMenu() {

        menuBar.removeItems();
        addBasicMenu();
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
                /*
                 if (securityHelper.getLogedInUser() == null) {
                 ((MyVaadinUI) UI.getCurrent().getUI()).getNavigator()
                 .navigateTo(ViewLogin.NAME);
                 return;
                 }
                 */
                setActiveItem(selectedItem);

                String value = selectedItem.getText();
                if (msgs.getMessage("header.home").equals(value)) {
                    ((MyUI) UI.getCurrent().getUI()).getNavigator()
                        .navigateTo(ViewHome.NAME);
                } /*else if (msgs.getMessage("header.people").equals(value)) {
                 ((MyUI) UI.getCurrent().getUI()).getNavigator()
                 .navigateTo(ViewPeople.NAME);
                 } else if (msgs.getMessage("header.training").equals(value)) {
                 ((MyUI) UI.getCurrent().getUI()).getNavigator()
                 .navigateTo(ViewTraining.NAME);
                 } else if (msgs.getMessage("header.reports").equals(value)) {
                 ((MyUI) UI.getCurrent().getUI()).getNavigator()
                 .navigateTo(ViewReports.NAME);
                 } else if (msgs.getMessage("header.manage").equals(value)) {
                 ((MyUI) UI.getCurrent().getUI()).getNavigator()
                 .navigateTo(ViewManage.NAME);
                 }*/

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
        selectedItem.setStyleName(CLASS_NAME + "-menuitem" + " active");
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
        Button logout = createLogout();

        userLayout.addComponent(userInfo);
        userLayout.addComponent(logout);

        userLayout.setExpandRatio(userInfo, 3);
        userLayout.setExpandRatio(logout, 1);

        userLayout.setComponentAlignment(userInfo, Alignment.TOP_RIGHT);
        userLayout.setComponentAlignment(logout, Alignment.TOP_RIGHT);

        userLayout.setWidth(HEADER_WIDTH, Unit.PERCENTAGE);
        userLayout.setHeight(HEADER_HEIGHT / 2, Unit.PIXELS);

        return userLayout;
    }

    /**
     * Link to logout
     *
     * @return logout button
     */
    private Button createLogout() {
        Button logoutButton = new Button(msgs.getMessage("header.logout"));
        logoutButton.setStyleName("my-button");
        /*logoutButton.addClickListener(new Button.ClickListener() {

         @Override
         public void buttonClick(ClickEvent event) {
         securityHelper.setLogedInUser(null);

         userInfo.setValue("");
         removeManagerMenu();

         ((MyVaadinUI) UI.getCurrent().getUI()).getNavigator()
         .navigateTo(ViewLogin.NAME);
         }
         });*/
        logoutButton.setSizeUndefined();
        return logoutButton;
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

    public void setUsersFullName(String fullName) {
        userInfo.setValue(fullName);
    }
}
