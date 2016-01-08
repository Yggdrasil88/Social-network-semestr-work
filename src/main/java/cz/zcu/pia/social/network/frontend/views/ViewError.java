/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;


/**
 * Error view
 * @author Frantisek Kolenak
 */

public class ViewError extends Panel implements View {
    private static final String CONTENT_WRAPPER_STYLE_NAME = "content-wrapper";
    /**
     * Content wrapper
     */
    protected VerticalLayout contentWrapper;
    /**
     * Constructor
     */
    public ViewError(){
        setSizeFull();

        contentWrapper = new VerticalLayout();
        contentWrapper.setStyleName(CONTENT_WRAPPER_STYLE_NAME);

        contentWrapper.setSpacing(true);
        contentWrapper.setMargin(true);
        contentWrapper.setWidth(100, Unit.PERCENTAGE);
        // contentWrapper.setStyleName(VIEW_STYLE_NAME);

        setContent(contentWrapper);
        this.contentWrapper.addComponent(new Label("Error 404 - Nenalezeno"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
    
}
