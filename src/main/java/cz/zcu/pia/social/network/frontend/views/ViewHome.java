/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.views;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ViewHome.class);

    /**
     * PostConstruct
     */
    @PostConstruct
    public void postConstruct() {
        super.postConstruct();

        HorizontalLayout content = new HorizontalLayout();

        content.addComponent(new Button("new Button"));
        this.getContentWrapper().addComponent(content);

    }

}
