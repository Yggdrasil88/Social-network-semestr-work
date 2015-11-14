package cz.zcu.pia.social.network.frontend.views;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import cz.zcu.pia.social.network.MyUI;
import cz.zcu.pia.social.network.frontend.components.login.ComponentLogin;
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
@VaadinView(ViewLogin.NAME)
public class ViewLogin extends ViewBase {
    public static final String NAME = "login";

    private static final Logger logger
        = LoggerFactory.getLogger(ViewLogin.class);
    /**
     * Sec. helper
     */
    @Autowired
    private SecurityHelper securityHelper;
    
    @Autowired
    private ComponentLogin login;
    
    
    /**
     * PostConstruct
     */
    @PostConstruct
    @Override
    public void postConstruct() {
        super.postConstruct();

       
        this.getContentWrapper().addComponent(login);

    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if(securityHelper.isAuthenticated()){
            ((MyUI) UI.getCurrent().getUI()).getNavigator()
                        .navigateTo(ViewHome.NAME);
        }
        
    }
}
