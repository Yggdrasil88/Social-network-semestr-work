/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.profile;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import cz.zcu.pia.social.network.helpers.MessagesLoader;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope("prototype")
public class ComponentFriends extends VerticalLayout {

    @Autowired
    private MessagesLoader msgs;

    private CustomLayout layout = new CustomLayout("friends");
    private ComboBox filter;
    private Table table;

    public ComponentFriends() {
        filter = new ComboBox();
        table = new Table();
        
    }

    @PostConstruct
    public void postConstruct() {
        this.addComponent(layout);

        setFilterComboBox();
        setTable();
        layout.addComponent(new Button(msgs.getMessage("component.friends.request")), "button");
        layout.addComponent(filter, "filter");
        layout.addComponent(table, "table");
    }

    private void setFilterComboBox() {
        filter.addItem(msgs.getMessage("view.profile.tab.friends"));
        filter.addItem(msgs.getMessage("component.friends.following"));
        filter.setValue(msgs.getMessage("view.profile.tab.friends"));

    }

    private void setTable() {
        table.addContainerProperty(msgs.getMessage("name"),String.class, "");
        table.addContainerProperty(msgs.getMessage("surname"),String.class, "");
        table.addContainerProperty(msgs.getMessage("component.friends.friends-since"),Date.class, "");
        table.addContainerProperty("Tlacitko s odebrat pritele",String.class, "");
        
    }
}
