/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.handlers;


import com.vaadin.event.FieldEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.TextField;

/**
 * Handles enter key pressed, its added to text field 
 * @author Ramón Talavera
 * @author http://ramontalaverasuarez.blogspot.cz/2014/06/vaadin-7-detect-enter-key-in-textfield.html
 */
public abstract class OnEnterKeyHandler {

     final ShortcutListener enterShortCut = new ShortcutListener(
                "EnterOnTextAreaShorcut", ShortcutAction.KeyCode.ENTER, null) {
                    @Override
                    public void handleAction(Object sender, Object target) {
                        onEnterKeyPressed();
                    }
                };

     public void installOn(final TextField component)
     {
        component.addFocusListener(
                new FieldEvents.FocusListener() {

                    @Override
                    public void focus(FieldEvents.FocusEvent event
                    ) {
                        component.addShortcutListener(enterShortCut);
                    }

                }
        );

        component.addBlurListener(
                new FieldEvents.BlurListener() {

                    @Override
                    public void blur(FieldEvents.BlurEvent event
                    ) {
                        component.removeShortcutListener(enterShortCut);
                    }

                }
        );
     }

     public abstract void onEnterKeyPressed();

}