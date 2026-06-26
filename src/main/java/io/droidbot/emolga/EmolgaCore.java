package io.droidbot.emolga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;

@SpringBootApplication
@StyleSheet("styles/fonts.css")
@StyleSheet("styles/home-view.css")
@StyleSheet("styles/wordle.css")
@StyleSheet("styles/layout.css")
public class EmolgaCore implements AppShellConfigurator {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        SpringApplication.run(EmolgaCore.class, args);
    }

}
