package com.example.application.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        setSizeFull();
        getStyle().set("background-color", "black");
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Botón de registro en la esquina superior derecha
        Button registerButton = new Button("Registrarse", e -> UI.getCurrent().navigate("register"));
        registerButton.getStyle().set("position", "absolute")
                .set("top", "10px")
                .set("right", "10px");

        // Título opcional
        H1 title = new H1("Máquina de Puzzles Lógicos");
        title.getStyle().set("color", "white");

        // Botones de juegos
        Button queensButton = new Button("N Reinas", e -> UI.getCurrent().navigate("nqueens"));
        Button knightButton = new Button("Recorrido del Caballo", e -> UI.getCurrent().navigate("knight"));
        Button hanoiButton = new Button("Torres de Hanói", e -> UI.getCurrent().navigate("hanoi"));

        for (Button button : new Button[]{queensButton, knightButton, hanoiButton}) {
            button.getStyle()
                    .set("width", "200px")
                    .set("height", "50px")
                    .set("margin", "10px")
                    .set("font-size", "18px");
        }

        VerticalLayout buttonLayout = new VerticalLayout(queensButton, knightButton, hanoiButton);
        buttonLayout.setAlignItems(Alignment.CENTER);

        add(registerButton, title, buttonLayout);
    }
}
