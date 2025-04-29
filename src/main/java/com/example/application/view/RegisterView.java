package com.example.application.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("register")
public class RegisterView extends VerticalLayout {

    public RegisterView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        getStyle().set("background-color", "black");

        H1 title = new H1("Registro");
        title.getStyle().set("color", "white");

        TextField username = new TextField("Nombre de usuario");
        PasswordField password = new PasswordField("Contraseña");

        Button registerButton = new Button("Registrarse", e -> {
            // Aquí más adelante guardarás en base de datos
            Notification.show("Registro exitoso");
            UI.getCurrent().navigate(""); // volver a la pantalla principal
        });

        for (var component : new com.vaadin.flow.component.Component[]{username, password, registerButton}) {
            component.getStyle().set("width", "300px");
        }

        add(title, username, password, registerButton);
    }
}
