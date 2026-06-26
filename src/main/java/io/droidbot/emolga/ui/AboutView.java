package io.droidbot.emolga.ui;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("about")
@PageTitle("About")
@Menu(title = "About", order = 1)
public class AboutView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public AboutView() {
        addClassName("about-view");

        var card = new VerticalLayout();
        card.addClassName("about-card");

        var title = new H2("About Emolga Core");
        title.addClassName("about-card__title");
        card.add(title);

        card.add(new Paragraph("A Vaadin 25 starter application."));

        add(card);
    }

}
