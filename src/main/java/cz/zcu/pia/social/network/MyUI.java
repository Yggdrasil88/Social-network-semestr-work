package cz.zcu.pia.social.network;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.frontend.components.header.ComponentHeader;
import cz.zcu.pia.social.network.frontend.views.ViewError;
import cz.zcu.pia.social.network.frontend.views.ViewLogin;
import cz.zcu.pia.social.network.frontend.views.ViewProfile;
import cz.zcu.pia.social.network.frontend.views.ViewRegister;
import cz.zcu.pia.social.network.helpers.SecurityHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.DiscoveryNavigator;

/**
 * Application UI
 *
 * @author Frantisek Kolenak
 *
 */
@Component
@Scope("prototype")
@Theme("mytheme")
public class MyUI extends UI {
    
    private final Logger logger = LoggerFactory.getLogger(MyUI.class);
    private static final String MAIN_STYLE = "main-wrapper";
    /**
     *
     */
    private static final long serialVersionUID = 2177360609828565097L;
    /**
     * Header of the application
     */
    @Autowired
    private ComponentHeader header;

    /**
     * Sec. helper
     */
    @Autowired
    private SecurityHelper securityHelper;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
       // BasicConfigurator.configure();
        VerticalLayout main = new VerticalLayout();
        main.setStyleName(MAIN_STYLE);
        main.setSpacing(true);
        // main.setMargin(true);
        main.setWidth(60, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        main.addComponent(header);

        main.addComponent(content);
        main.setComponentAlignment(header, Alignment.TOP_CENTER);

        main.setComponentAlignment(content, Alignment.TOP_CENTER);
        setContent(main);
        new DiscoveryNavigator(this, content);

        getNavigator().setErrorView(ViewError.class);

        //Disable checking
        getNavigator().addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {

                if (securityHelper.isAuthenticated()) {
                    if (event.getNewView() instanceof ViewLogin
                        || event.getNewView() instanceof ViewRegister) {
                        return false;
                    }

                } else {
                    if (event.getNewView() instanceof ViewProfile) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
            }

        });

    }

 
    /**
     * Method returns header
     *
     * @return header
     */
    public ComponentHeader getHeader() {
        return this.header;
    }

}
