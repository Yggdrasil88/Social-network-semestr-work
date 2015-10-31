/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Frantisek Kolenak
 */
public class ViewBase extends Panel implements View {

    /**
     * Content wrapper
     */
    private VerticalLayout contentWrapper;

    /**
     * Messages helper
     */
    @Autowired
    protected MessagesLoader msgs;

    /**
     * PostConstruct
     */
    @PostConstruct
    public void postConstruct() {
        setSizeFull();

        contentWrapper = new VerticalLayout();
        //contentWrapper.setStyleName(CONTENT_WRAPPER_STYLE_NAME);

        contentWrapper.setSpacing(true);
        contentWrapper.setMargin(true);
        contentWrapper.setWidth(100, Unit.PERCENTAGE);
        // contentWrapper.setStyleName(VIEW_STYLE_NAME);

        setContent(contentWrapper);
        //setSelectedItemHeader();

    }

    /**
     * @return the content
     */
    protected VerticalLayout getContentWrapper() {
        return contentWrapper;
    }

    /**
     * @param contentWrapper the content to set
     */
    protected void setContentWrapper(VerticalLayout contentWrapper) {
        this.contentWrapper = contentWrapper;
    }

    /**
     * Gets message
     *
     * @param id id
     * @return message
     */
    public String getMessageValue(String id) {
        return msgs.getMessage(id);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //Nothing to do currently
    }
}
