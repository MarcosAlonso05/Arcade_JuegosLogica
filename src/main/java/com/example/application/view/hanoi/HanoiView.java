package com.example.application.view.hanoi;

import com.example.application.controller.hanoi.HanoiController;
import com.example.application.model.pieces.HanoiPiece;
import com.example.application.model.pieces.Piece;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;

@Route("hanoi")
public class HanoiView extends VerticalLayout {
    private HanoiController controller;
    private Div boardContainer;
    private Div[] towers;
    private final VerticalLayout contentLayout;
    private final int MIN_DISKS = 3;
    private final int MAX_DISKS = 8;
    private Button solveButton;
    private Button resetButton;
    private H1 movesLabel;

    public HanoiView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();
        getStyle().set("background-color", "black");

        contentLayout = new VerticalLayout();
        contentLayout.setAlignItems(Alignment.CENTER);
        contentLayout.setSpacing(true);
        add(contentLayout);

        showDiskSelector();
    }

    private void showDiskSelector() {
        H1 title = new H1("Torres de Hanoi");
        title.getStyle().set("color", "white");

        IntegerField disksInput = new IntegerField("Número de discos");
        disksInput.setValue(3);
        disksInput.setMin(MIN_DISKS);
        disksInput.setMax(MAX_DISKS);
        disksInput.setStepButtonsVisible(true);

        Button startButton = new Button("Comenzar", e -> {
            if (disksInput.getValue() != null && disksInput.getValue() >= MIN_DISKS && disksInput.getValue() <= MAX_DISKS) {
                startGame(disksInput.getValue());
            }
        });

        contentLayout.removeAll();
        contentLayout.add(title, disksInput, startButton);
    }

    private void startGame(int disks) {
        this.controller = new HanoiController(disks);
        this.boardContainer = new Div();
        this.towers = new Div[3];

        contentLayout.removeAll();

        HorizontalLayout gameLayout = new HorizontalLayout();
        gameLayout.setAlignItems(Alignment.CENTER);

        this.movesLabel = new H1("Movimientos: 0");
        movesLabel.getStyle().set("color", "white");

        this.solveButton = new Button("Resolver", e -> solveGame());
        this.resetButton = new Button("Reiniciar", e -> resetGame());

        VerticalLayout sideLayout = new VerticalLayout();
        sideLayout.add(movesLabel, solveButton, resetButton);
        sideLayout.getStyle().set("margin-left", "20px");

        gameLayout.add(boardContainer, sideLayout);
        contentLayout.add(gameLayout);

        drawBoard();
    }

    private void drawBoard() {
        boardContainer.removeAll();
        boardContainer.getStyle()
                .set("display", "flex")
                .set("justify-content", "center")
                .set("align-items", "flex-end")
                .set("height", "400px")
                .set("width", "600px")
                .set("background-color", "#424242")
                .set("border-radius", "10px")
                .set("padding", "20px");

        for (int i = 0; i < 3; i++) {
            Div towerContainer = new Div();
            towerContainer.getStyle()
                    .set("display", "flex")
                    .set("flex-direction", "column")
                    .set("align-items", "center")
                    .set("justify-content", "flex-end")
                    .set("width", "150px")
                    .set("height", "350px")
                    .set("margin", "0 10px")
                    .set("position", "relative");

            Div pole = new Div();
            pole.getStyle()
                    .set("width", "10px")
                    .set("height", "300px")
                    .set("background-color", "#795548")
                    .set("position", "absolute")
                    .set("bottom", "20px")
                    .set("left", "70px");

            Div base = new Div();
            base.getStyle()
                    .set("width", "120px")
                    .set("height", "20px")
                    .set("background-color", "#795548")
                    .set("border-radius", "5px")
                    .set("position", "absolute")
                    .set("bottom", "0")
                    .set("left", "15px");

            Div disksContainer = new Div();
            disksContainer.getStyle()
                    .set("display", "flex")
                    .set("flex-direction", "column-reverse")
                    .set("align-items", "center")
                    .set("width", "100%")
                    .set("height", "300px")
                    .set("position", "absolute")
                    .set("bottom", "20px")
                    .set("z-index", "1");

            final int towerIndex = i;
            towerContainer.addClickListener(e -> handleTowerClick(towerIndex));

            towers[i] = disksContainer;
            towerContainer.add(pole, base, disksContainer);
            boardContainer.add(towerContainer);
        }

        refreshBoard();
    }

    private void handleTowerClick(int towerIndex) {
        boolean validMove = controller.selectTower(towerIndex);
        refreshBoard();

        if (controller.hasWon()) {
            Notification.show("¡Felicidades! Has resuelto las Torres de Hanoi en " +
                            controller.getMoves() + " movimientos")
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }
    }

    private void refreshBoard() {
        movesLabel.setText("Movimientos: " + controller.getMoves());

        for (int i = 0; i < 3; i++) {
            towers[i].removeAll();

            for (Piece disk : controller.getBoard().getTower(i)) {
                HanoiPiece hanoiDisk = (HanoiPiece) disk;
                Div diskDiv = new Div();

                int size = hanoiDisk.getSize();
                int width = 30 + size * 15;
                String color = getDiskColor(size);

                diskDiv.getStyle()
                        .set("width", width + "px")
                        .set("height", "20px")
                        .set("background-color", color)
                        .set("border-radius", "10px")
                        .set("margin", "2px 0")
                        .set("border", "1px solid #000");

                if (controller.getSelectedTower() == i &&
                        disk.equals(controller.getBoard().peekDisk(i))) {
                    diskDiv.getStyle().set("border", "2px solid yellow");
                }

                towers[i].add(diskDiv);
            }
        }
    }

    private String getDiskColor(int size) {
        String[] colors = {
                "#FF5252",
                "#FF4081",
                "#E040FB",
                "#7C4DFF",
                "#536DFE",
                "#40C4FF",
                "#18FFFF",
                "#64FFDA",
                "#69F0AE",
                "#B2FF59",
                "#EEFF41",
                "#FFFF00",
                "#FFD740",
                "#FFAB40",
                "#FF6E40"
        };
        return colors[(size - 1) % colors.length];
    }

    private void solveGame() {
        controller.solve();
        refreshBoard();
        Notification.show("Problema resuelto automáticamente")
                .addThemeVariants(NotificationVariant.LUMO_PRIMARY);
    }

    private void resetGame() {
        controller.resetBoard();
        refreshBoard();
    }
}