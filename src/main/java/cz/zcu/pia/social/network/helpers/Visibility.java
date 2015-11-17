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
    
    public static final String PUBLIC = "Veřejné";
    public static final String FRIENDS = "Přátelé";
        
    @Autowired
    private MessagesLoader msgs;
    
    public static ComboBox getVisibilityComboBox(){
        ComboBox cb = new ComboBox();
        cb.addItem(PUBLIC);
        cb.addItem(FRIENDS);
        cb.setValue(PUBLIC);
        cb.setNullSelectionAllowed(false);
        cb.setFilteringMode(FilteringMode.OFF);
        cb.setDescription(PUBLIC);
        return cb;
    }
}
