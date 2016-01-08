/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.views;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.UI;
import cz.zcu.pia.social.network.MyUI;
import cz.zcu.pia.social.network.frontend.components.login.ComponentRegister;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.VaadinView;

/**
 * View Register
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
@VaadinView(ViewRegister.NAME)
public class ViewRegister extends ViewBase {

    public static final String NAME = "register";
    /**
     * Sec. helper
     */
    @Autowired
    private SecurityHelper securityHelper;
    /**
     * Component for registering users
     */
    @Autowired
    private ComponentRegister componentRegister;
    /**
     * PostConstruct
     */
    @PostConstruct
    @Override
    public void postConstruct() {
        super.postConstruct();
        
        this.getContentWrapper().addComponent(componentRegister);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (securityHelper.isAuthenticated()) {
            ((MyUI) UI.getCurrent().getUI()).getNavigator()
                .navigateTo(ViewHome.NAME);
        }
    }
}
