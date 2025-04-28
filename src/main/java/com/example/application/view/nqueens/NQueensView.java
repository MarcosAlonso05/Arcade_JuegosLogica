package com.example.application.view.nqueens;

import com.example.application.controller.nqueens.NqueensController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;

@Route("nqueens")
public class NQueensView extends VerticalLayout {

    private NqueensController controller;
    private Div boardContainer;
    private Button[][] buttons;
    private int size = 8;
    private final VerticalLayout contentLayout;
    private final int MIN_SIZE = 8;
    private final int MAX_SIZE = 15;

    public NQueensView() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();
        getStyle().set("background-color", "black");

        contentLayout = new VerticalLayout();
        contentLayout.setAlignItems(Alignment.CENTER);
        contentLayout.setSpacing(true);
        add(contentLayout);

        showSizeSelector();
    }

    private void showSizeSelector() {
        H1 title = new H1("Introduce el tamaño del tablero:");
        title.getStyle().set("color", "white");

        IntegerField sizeInput = new IntegerField();
        sizeInput.setValue(8); // Valor por defecto
        sizeInput.setMin(MIN_SIZE);
        sizeInput.setMax(MAX_SIZE);
        sizeInput.setStepButtonsVisible(true);

        Button startButton = new Button("Crear tablero", e -> {
            Integer value = sizeInput.getValue();
            if (value != null && value >= MIN_SIZE && value <= MAX_SIZE) {
                size = value;
                startGame();
            } else {
                sizeInput.setErrorMessage("Introduce un número entre " + MIN_SIZE + " y " + MAX_SIZE);
                sizeInput.setInvalid(true);
            }
        });

        contentLayout.removeAll();
        contentLayout.add(title, sizeInput, startButton);
    }

    private void startGame() {
        this.controller = new NqueensController(size);
        this.boardContainer = new Div();
        this.buttons = new Button[size][size];

        contentLayout.removeAll();

        HorizontalLayout gameLayout = new HorizontalLayout();
        gameLayout.setAlignItems(Alignment.CENTER);

        Button resetButton = new Button("Resetear Tablero", e -> resetBoard());

        VerticalLayout sideLayout = new VerticalLayout();
        sideLayout.setAlignItems(Alignment.CENTER);
        sideLayout.add(resetButton);
        sideLayout.getStyle().set("margin-left", "20px");

        gameLayout.add(boardContainer, sideLayout);
        contentLayout.add(gameLayout);

        drawBoard();
    }

    private void drawBoard() {
        boardContainer.removeAll();
        boardContainer.getStyle()
                .set("display", "grid")
                .set("grid-template-columns", "repeat(" + size + ", 50px)")
                .set("gap", "2px")
                .set("justify-content", "center")
                .set("align-items", "center");

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Button cellButton = new Button();
                styleButton(cellButton);
                updateCellStyle(cellButton, i, j);

                final int x = i;
                final int y = j;
                cellButton.addClickListener(e -> {
                    if (controller.placeQueen(x, y)) {
                        refreshBoard();
                    }
                });

                buttons[i][j] = cellButton;
                boardContainer.add(cellButton);
            }
        }
    }

    private void refreshBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                updateCellStyle(buttons[i][j], i, j);
            }
        }
    }

    private void updateCellStyle(Button button, int x, int y) {
        if (controller.getBoard().getPossibilitys(x, y)) {
            button.getStyle().set("background-color", "rgba(255,255,255,0.7)");
        } else {
            button.getStyle().set("background-color", "rgba(255,0,0,0.5)");
        }

        if (controller.getBoard().getPiece(x, y) != null) {
            button.setText("♛");
            button.getStyle().set("color", "white"); // La reina ahora es blanca
        } else {
            button.setText("");
        }
    }

    private void styleButton(Button button) {
        button.getStyle()
                .set("width", "50px")
                .set("height", "50px")
                .set("min-width", "0")
                .set("min-height", "0")
                .set("padding", "0")
                .set("margin", "0")
                .set("border", "none")
                .set("box-sizing", "border-box")
                .set("flex-shrink", "0")
                .set("overflow", "hidden")
                .set("line-height", "1")
                .set("font-size", "24px");
    }

    private void resetBoard() {
        controller.resetBoard();
        refreshBoard();
    }
}