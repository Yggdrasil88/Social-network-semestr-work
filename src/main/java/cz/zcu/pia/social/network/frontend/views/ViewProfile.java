/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.views;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.frontend.components.profile.ComponentFriends;
import cz.zcu.pia.social.network.frontend.components.profile.ComponentProfile;
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

    private static final Logger logger
        = LoggerFactory.getLogger(ViewProfile.class);
    @Autowired
    private ComponentProfile profile;
    @Autowired
    private ComponentFriends friends;
    /**
     * PostConstruct
     */
    @PostConstruct
    @Override
    public void postConstruct() {
        super.postConstruct();
        TabSheet tabsheet = new TabSheet();
        tabsheet.addTab(friends, msgs.getMessage("view.profile.tab.friends"));
        tabsheet.addTab(profile, msgs.getMessage("view.profile.tab.profile"));
        this.getContentWrapper().addComponent(tabsheet);
        //this.getContentWrapper().addComponent();

    }
}
