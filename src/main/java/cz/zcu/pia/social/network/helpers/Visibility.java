/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.helpers;

import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
public class Visibility {

    public static final int PUBLIC = 0, FRIENDS = 1;

    @Autowired
    private MessagesLoader msgs;

    public ComboBox getVisibilityComboBox() {
        ComboBox cb = new ComboBox();
        cb.addItem(this.getPublicValue());
        cb.addItem(this.getFriendsValue());
        cb.setValue(this.getPublicValue());
        cb.setNullSelectionAllowed(false);
        cb.setFilteringMode(FilteringMode.OFF);
        cb.setDescription(this.getPublicValue());
        return cb;
    }

    public String getPublicValue() {
        if(msgs == null){
            return "";
        }
        return msgs.getMessage("visibility.public");
    }

    public String getFriendsValue() {
        if(msgs == null){
            return "";
        }
        return msgs.getMessage("visibility.friends");
    }
    
    public String getFollowingValue() {
        if(msgs == null){
            return "";
        }
        return msgs.getMessage("visibility.following");
    }
}
