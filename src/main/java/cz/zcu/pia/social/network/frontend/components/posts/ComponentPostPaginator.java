/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zcu.pia.social.network.frontend.components.posts;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Frantisek Kolenak
 */
@Component
@Scope(scopeName = "prototype")
public class ComponentPostPaginator extends HorizontalLayout {

    private static final Logger logger = LoggerFactory.getLogger(ComponentPostPaginator.class);

    private int curentPage = 0;
    
    private final List<Button> buttonList = new ArrayList();

    private ComponentPostsFilter filterReference;
    
    public ComponentPostPaginator(){
        this.setSpacing(true);
    }

    public void addButtons(int numberOfPages) {
        this.removeAllComponents();
        buttonList.clear();
        for (int i = 0; i <= numberOfPages; i++) {
            Button b = new Button("" + i);
            b.setData(i);
            b.setStyleName("button-paginator");
            b.addClickListener(new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    int page = (int) event.getButton().getData();
                    if (curentPage != page) {
                        filterReference.setPageAndReload(page);
                        removeActiveStyle();
                        Button b = event.getButton();
                        b.addStyleName("paginator-active");
                        curentPage = page;
                    }
                }

            });
            if (i == 0) {
                b.addStyleName("paginator-active");
            }
            buttonList.add(b);
            this.addComponent(b);
        }

    }
    
    public void setFilter(ComponentPostsFilter filterReference){
        this.filterReference = filterReference;
    }

    private void removeActiveStyle() {
        Button b = buttonList.get(curentPage);
        b.removeStyleName("paginator-active");
    }
}
