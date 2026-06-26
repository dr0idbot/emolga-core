package io.droidbot.emolga.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Home")
@Menu(title = "Home", order = 0)
public class MainView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    private final Span ipValue = new Span();
    private final Span hostnameValue = new Span();
    private final Span protocolValue = new Span();
    private final Span localeValue = new Span();
    private final Span userAgentValue = new Span();
    private final Span timezoneValue = new Span();
    private final Span browserLanguageValue = new Span();
    private final Span screenValue = new Span();
    private final Span platformValue = new Span();
    private final Span connectionValue = new Span();
    private final Span memoryValue = new Span();
    private final Span cpuCoresValue = new Span();
    private final Span colorDepthValue = new Span();
    private final Span pixelRatioValue = new Span();
    private final Span cookiesValue = new Span();
    private final Span referrerValue = new Span();
    private final Span currentUrlValue = new Span();
    private final Span touchValue = new Span();

    public MainView(@Autowired HttpServletRequest request) {
        addClassName("home-view");

        var card = new VerticalLayout();
        card.addClassName("client-card");

        var title = new H2("Client Information");
        title.addClassName("client-card__title");
        card.add(title);

        var networkGroup = createGroup("Network");
        addItem(networkGroup, "IP Address", ipValue);
        addItem(networkGroup, "Hostname", hostnameValue);
        addItem(networkGroup, "Protocol", protocolValue);
        addItem(networkGroup, "Referrer", referrerValue);
        addItem(networkGroup, "Current URL", currentUrlValue);
        card.add(networkGroup);

        var systemGroup = createGroup("System");
        addItem(systemGroup, "Platform", platformValue);
        addItem(systemGroup, "CPU Cores", cpuCoresValue);
        addItem(systemGroup, "Device Memory", memoryValue);
        addItem(systemGroup, "Screen", screenValue);
        addItem(systemGroup, "Color Depth", colorDepthValue);
        addItem(systemGroup, "Pixel Ratio", pixelRatioValue);
        addItem(systemGroup, "Touch Screen", touchValue);
        card.add(systemGroup);

        var browserGroup = createGroup("Browser");
        addItem(browserGroup, "Locale", localeValue);
        addItem(browserGroup, "Timezone", timezoneValue);
        addItem(browserGroup, "Language", browserLanguageValue);
        addItem(browserGroup, "Connection", connectionValue);
        addItem(browserGroup, "Cookies", cookiesValue);
        addItem(browserGroup, "User Agent", userAgentValue, true);
        card.add(browserGroup);

        add(card);

        populateServerInfo(request);
        populateClientInfo();
    }

    private void populateServerInfo(HttpServletRequest request) {
        ipValue.setText(request.getRemoteAddr());
        hostnameValue.setText(request.getRemoteHost());
        protocolValue.setText(request.getProtocol());
        localeValue.setText(UI.getCurrent().getLocale().toLanguageTag());
        userAgentValue.setText(request.getHeader("User-Agent"));
    }

    private void populateClientInfo() {
        var page = UI.getCurrent().getPage();

        page.executeJs("return Intl.DateTimeFormat().resolvedOptions().timeZone")
            .then(String.class, this.timezoneValue::setText);
        page.executeJs("return navigator.language")
            .then(String.class, this.browserLanguageValue::setText);
        page.executeJs("return window.screen.width + 'x' + window.screen.height")
            .then(String.class, this.screenValue::setText);
        page.executeJs("return navigator.platform")
            .then(String.class, this.platformValue::setText);
        page.executeJs(
            "return navigator.connection ? navigator.connection.effectiveType : 'unknown'")
            .then(String.class, this.connectionValue::setText);
        page.executeJs(
            "return navigator.deviceMemory ? navigator.deviceMemory + ' GB' : 'unknown'")
            .then(String.class, this.memoryValue::setText);
        page.executeJs("return navigator.hardwareConcurrency + ' cores'")
            .then(String.class, this.cpuCoresValue::setText);
        page.executeJs("return window.screen.colorDepth + '-bit'")
            .then(String.class, this.colorDepthValue::setText);
        page.executeJs("return window.devicePixelRatio + ''")
            .then(String.class, this.pixelRatioValue::setText);
        page.executeJs("return navigator.cookieEnabled ? 'Yes' : 'No'")
            .then(String.class, this.cookiesValue::setText);
        page.executeJs("return document.referrer || 'none'")
            .then(String.class, this.referrerValue::setText);
        page.executeJs("return document.location.href")
            .then(String.class, this.currentUrlValue::setText);
        page.executeJs(
            "return 'ontouchstart' in window || navigator.maxTouchPoints > 0 ? 'Yes' : 'No'")
            .then(String.class, this.touchValue::setText);
    }

    private Div createGroup(String title) {
        var group = new Div();
        group.addClassName("info-group");

        var titleSpan = new Span(title);
        titleSpan.addClassName("info-group__title");
        group.add(titleSpan);

        var grid = new Div();
        grid.addClassName("info-grid");
        group.add(grid);

        return group;
    }

    private void addItem(Div group, String label, Span value) {
        addItem(group, label, value, false);
    }

    private void addItem(Div group, String label, Span value, boolean fullWidth) {
        var grid = (Div) group.getComponentAt(1);

        var item = new Div();
        item.addClassName("info-item");

        if (fullWidth) {
            item.addClassName("user-agent-item");
        }

        var labelSpan = new Span(label);
        labelSpan.addClassName("info-item__label");
        value.addClassName("info-item__value");

        item.add(labelSpan, value);
        grid.add(item);
    }

}
