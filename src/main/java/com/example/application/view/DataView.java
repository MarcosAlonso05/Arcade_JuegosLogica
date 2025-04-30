
package com.example.application.view;

import com.example.application.model.GameData;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("data")
public class DataView extends VerticalLayout {

    public DataView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(true);
        setSpacing(true);

        Div title = new Div();
        title.setText("Selecciona un Juego para ver los Datos");
        title.getStyle().set("font-size", "2rem").set("color", "white");

        Button queensButton = new Button("N REINAS", e -> showGameData("N REINAS"));
        Button knightButton = new Button("RECORRIDO CABALLO", e -> showGameData("RECORRIDO CABALLO"));
        Button hanoiButton = new Button("TORRES HANÓI", e -> showGameData("TORRES HANÓI"));

        Button backButton = new Button("Regresar", e -> UI.getCurrent().navigate(""));

        queensButton.getStyle().set("background-color", "#FF00FF");
        knightButton.getStyle().set("background-color", "#00FFFF");
        hanoiButton.getStyle().set("background-color", "#FFFF00");
        backButton.getStyle().set("background-color", "#FF0000");

        add(title, queensButton, knightButton, hanoiButton, backButton);
    }

    private Button createBackButton() {
        Button backButton = new Button("VOLVER AL MENÚ", e -> UI.getCurrent().navigate(""));
        backButton.getStyle()
                .set("font-family", "'Press Start 2P', cursive")
                .set("font-size", "0.8rem")
                .set("color", "#00FFFF")
                .set("background-color", "rgba(0, 0, 0, 0.7)")
                .set("border", "3px solid #00FFFF")
                .set("border-radius", "5px")
                .set("cursor", "pointer")
                .set("text-shadow", "0 0 5px #00FFFF")
                .set("box-shadow", "0 0 10px #00FFFF")
                .set("margin", "1rem");
        return backButton;
    }

    private void showGameData(String gameName) {
        GameData gameData = getGameData(gameName);

        Div gameDetails = new Div();
        gameDetails.add(new Div("Nombre del Juego: " + gameData.getName()));
        gameDetails.add(new Div("Puntuación Más Alta: " + gameData.getHighScore()));
        gameDetails.add(new Div("Número de Jugadores: " + gameData.getNumberOfPlayers()));
        gameDetails.add(new Div("Información Adicional: " + gameData.getAdditionalInfo()));

        gameDetails.getStyle().set("color", "white").set("font-size", "1.2rem");

        removeAll();
        add(gameDetails);


        add(createBackButton());
    }

    private GameData getGameData(String gameName) {
        switch (gameName) {
            case "N REINAS":
                return new GameData("N REINAS", "150", "1", "Juego de lógica y estrategia.");
            case "RECORRIDO CABALLO":
                return new GameData("RECORRIDO CABALLO", "120", "2", "Juego basado en el movimiento del caballo en el ajedrez.");
            case "TORRES HANÓI":
                return new GameData("TORRES HANÓI", "200", "1", "Resolver las torres de Hanói en el menor tiempo posible.");
            default:
                return new GameData("Desconocido", "N/A", "N/A", "Datos no disponibles.");
        }
    }
}
