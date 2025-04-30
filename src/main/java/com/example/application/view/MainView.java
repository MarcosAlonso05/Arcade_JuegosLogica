package com.example.application.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
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
        setPadding(false);
        setSpacing(false);

        Div starsBackground = createEnhancedStarsBackground();
        add(starsBackground);

        HorizontalLayout mainContent = new HorizontalLayout();
        mainContent.setWidth("90%");
        mainContent.setHeight("80%");
        mainContent.setAlignItems(Alignment.CENTER);
        mainContent.setJustifyContentMode(JustifyContentMode.BETWEEN);

        VerticalLayout buttonPanel = createButtonPanel();

        VerticalLayout logoPanel = createArcadeLogoPanel();

        Button dataButton = new Button("Ver Datos", e -> UI.getCurrent().navigate("data"));
        dataButton.getStyle()
                .set("position", "absolute")
                .set("top", "10px")
                .set("right", "10px")
                .set("background-color", "#FF00FF")
                .set("color", "white")
                .set("border", "2px solid #FF00FF")
                .set("border-radius", "5px")
                .set("cursor", "pointer");

        mainContent.add(buttonPanel, logoPanel);
        mainContent.expand(logoPanel);

        add(mainContent);
        add(dataButton);
    }

    private Div createEnhancedStarsBackground() {
        Div space = new Div();
        space.getStyle()
                .set("position", "fixed")
                .set("top", "0")
                .set("left", "0")
                .set("width", "100%")
                .set("height", "100%")
                .set("z-index", "-1")
                .set("overflow", "hidden");

        for (int i = 0; i < 150; i++) {
            Div star = new Div();
            star.getStyle()
                    .set("position", "absolute")
                    .set("width", "2px")
                    .set("height", "2px")
                    .set("background-color", "white")
                    .set("border-radius", "50%")
                    .set("opacity", String.valueOf(Math.random() * 0.8 + 0.2))
                    .set("top", Math.random() * 100 + "%")
                    .set("left", Math.random() * 100 + "%");

            star.getStyle().set("animation", "twinkle " + (Math.random() * 5 + 3) + "s infinite");

            if (i % 15 == 0) {
                star.getStyle()
                        .set("width", "3px")
                        .set("height", "3px")
                        .set("box-shadow", "0 0 5px 1px white");
            }

            space.add(star);
        }

        for (int i = 0; i < 5; i++) {
            Div shootingStar = new Div();
            shootingStar.getStyle()
                    .set("position", "absolute")
                    .set("width", "100px")
                    .set("height", "2px")
                    .set("background", "linear-gradient(90deg, rgba(255,255,255,0), rgba(255,255,255,1))")
                    .set("top", Math.random() * 100 + "%")
                    .set("left", Math.random() * 100 + "%")
                    .set("transform", "rotate(-45deg)")
                    .set("animation",
                            "shootingStar " + (Math.random() * 10 + 5) + "s linear infinite");

            space.add(shootingStar);
        }

        space.getStyle().set("--animations",
                "@keyframes twinkle { " +
                        "  0% { opacity: 0.2; } " +
                        "  50% { opacity: 1; } " +
                        "  100% { opacity: 0.2; } " +
                        "} " +
                        "@keyframes shootingStar { " +
                        "  0% { transform: rotate(-45deg) translateX(-100px); opacity: 1; } " +
                        "  70% { opacity: 1; } " +
                        "  100% { transform: rotate(-45deg) translateX(1000px); opacity: 0; } " +
                        "}");

        return space;
    }

    private VerticalLayout createButtonPanel() {
        VerticalLayout buttonPanel = new VerticalLayout();
        buttonPanel.setWidth("400px");
        buttonPanel.setAlignItems(Alignment.CENTER);
        buttonPanel.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonPanel.getStyle()
                .set("background", "rgba(0, 0, 30, 0.7)")
                .set("border", "4px solid #FF00FF")
                .set("border-radius", "10px")
                .set("padding", "2rem")
                .set("box-shadow", "0 0 20px #FF00FF, inset 0 0 20px #FF00FF");

        H1 title = new H1("SELECCIÓN DE JUEGO");
        title.getStyle()
                .set("color", "#FF00FF")
                .set("font-family", "'Press Start 2P', cursive")
                .set("font-size", "1.5rem")
                .set("text-shadow", "0 0 10px #FF00FF")
                .set("text-align", "center")
                .set("margin-bottom", "2rem");

        Button queensButton = createRetroButton("N REINAS", "nqueens", "#FF00FF");
        Button knightButton = createRetroButton("RECORRIDO CABALLO", "horse", "#00FFFF");
        Button hanoiButton = createRetroButton("TORRES HANÓI", "hanoi", "#FFFF00");

        buttonPanel.add(title, queensButton, knightButton, hanoiButton);
        return buttonPanel;
    }

    private Button createRetroButton(String text, String route, String color) {
        Button button = new Button(text, e -> UI.getCurrent().navigate(route));
        button.getStyle()
                .set("width", "100%")
                .set("height", "60px")
                .set("margin", "10px 0")
                .set("font-family", "'Press Start 2P', cursive")
                .set("font-size", "0.9rem")
                .set("color", color)
                .set("background-color", "rgba(0, 0, 0, 0.7)")
                .set("border", "3px solid " + color)
                .set("border-radius", "5px")
                .set("cursor", "pointer")
                .set("transition", "all 0.3s")
                .set("text-shadow", "0 0 5px " + color)
                .set("box-shadow", "0 0 10px " + color);

        button.addClassName("retro-button");
        button.getElement().setAttribute("theme", "primary");

        return button;
    }

    private VerticalLayout createArcadeLogoPanel() {
        VerticalLayout logoPanel = new VerticalLayout();
        logoPanel.setAlignItems(Alignment.CENTER);
        logoPanel.setJustifyContentMode(JustifyContentMode.CENTER);
        logoPanel.getStyle()
                .set("margin-left", "2rem")
                .set("animation", "float 6s ease-in-out infinite");

        Div logoContainer = new Div();
        logoContainer.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("padding", "2rem");

        Div neonEffect = new Div();
        neonEffect.getStyle()
                .set("position", "absolute")
                .set("width", "100%")
                .set("height", "100%")
                .set("border-radius", "50%")
                .set("box-shadow", "0 0 20px #FF00FF, 0 0 40px #FF00FF, 0 0 60px #FF00FF")
                .set("opacity", "0.7")
                .set("z-index", "-1");

        H1 logoLine1 = new H1("ArCade");
        styleLogoText(logoLine1, "#FF00FF");

        H1 logoLine2 = new H1("LoGico");
        styleLogoText(logoLine2, "#00FFFF");

        Div cornerTL = createCorner("#FF00FF");
        cornerTL.getStyle().set("top", "0").set("left", "0");

        Div cornerTR = createCorner("#00FFFF");
        cornerTR.getStyle().set("top", "0").set("right", "0");

        Div cornerBL = createCorner("#FFFF00");
        cornerBL.getStyle().set("bottom", "0").set("left", "0");

        Div cornerBR = createCorner("#FF00FF");
        cornerBR.getStyle().set("bottom", "0").set("right", "0");

        logoContainer.add(neonEffect, cornerTL, cornerTR, cornerBL, cornerBR, logoLine1, logoLine2);
        logoPanel.add(logoContainer);

        logoPanel.getStyle().set("--animation",
                "@keyframes float { " +
                        "  0% { transform: translateY(0px); } " +
                        "  50% { transform: translateY(-20px); } " +
                        "  100% { transform: translateY(0px); } " +
                        "}");

        return logoPanel;
    }

    private void styleLogoText(H1 textElement, String color) {
        textElement.getStyle()
                .set("color", color)
                .set("font-family", "'Press Start 2P', cursive")
                .set("font-size", "3.5rem")
                .set("margin", "0")
                .set("text-shadow",
                        "0 0 5px white, " +
                                "0 0 10px white, " +
                                "0 0 20px " + color + ", " +
                                "0 0 40px " + color + ", " +
                                "0 0 80px " + color)
                .set("line-height", "1.2");
    }

    private Div createCorner(String color) {
        Div corner = new Div();
        corner.getStyle()
                .set("position", "absolute")
                .set("width", "50px")
                .set("height", "50px")
                .set("border", "3px solid " + color)
                .set("opacity", "0.7");

        return corner;
    }


}