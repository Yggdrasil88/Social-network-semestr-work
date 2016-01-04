/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.views;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.MyUI;
import cz.zcu.pia.social.network.frontend.components.profile.friends.ComponentFriends;
import cz.zcu.pia.social.network.frontend.components.profile.profile.ComponentProfile;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.VaadinView;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
@VaadinView(ViewProfile.NAME)
public class ViewProfile extends ViewBase {

    public static final String NAME = "profile";
    public final static int COMPONENT_WIDTH = 450;
    private static final Logger logger
        = LoggerFactory.getLogger(ViewProfile.class);
    @Autowired
    private ComponentProfile profile;
    @Autowired
    private ComponentFriends friends;
    
    @Autowired
    private SecurityHelper securityHelper;
    /**
     * PostConstruct
     */
    @PostConstruct
    @Override
    public void postConstruct() {
        super.postConstruct();
        if(!securityHelper.isAuthenticated()){
            ((MyUI) UI.getCurrent().getUI()).getNavigator()
                        .navigateTo(ViewHome.NAME);
        }
        TabSheet tabsheet = new TabSheet();
        tabsheet.setWidth(COMPONENT_WIDTH, Unit.PIXELS);
        tabsheet.addTab(friends, msgs.getMessage("view.profile.tab.friends"));
        tabsheet.addTab(profile, msgs.getMessage("view.profile.tab.profile"));
        this.getContentWrapper().addComponent(tabsheet);
        //this.getContentWrapper().addComponent();

    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
       ( (MyUI) UI.getCurrent()).getHeader().setSelectedMenuItem(2);
    }
}
