package com.example.application.view.nqueens;

import com.example.application.controller.nqueens.NqueensController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("nqueens")
public class NQueensView extends VerticalLayout {

    private final NqueensController controller;
    private final Div boardContainer;
    private Button[][] buttons;
    private int size = 8;

    public NQueensView() {
        this.controller = new NqueensController(size);
        this.boardContainer = new Div();
        this.buttons = new Button[size][size];

        getStyle().set("background-color", "black");
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        add(boardContainer);
        drawBoard();
    }

    private void drawBoard() {
        boardContainer.removeAll();
        boardContainer.getStyle()
                .set("display", "grid")
                .set("justify-content", "center")
                .set("align-items", "center")
                .set("grid-template-columns", "repeat(" + size + ", 50px)")
                .set("gap", "2px");

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
                .set("flex-shrink", "0") // <-- que no se encoja
                .set("overflow", "hidden")
                .set("line-height", "1") // <-- para que el texto no expanda
                .set("font-size", "24px") // ajusta el tamaño del símbolo de la reina
        ;
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
            button.getStyle().set("background-color", "rgba(255, 255, 255, 0.3)");
        } else {
            button.getStyle().set("background-color", "rgba(255, 0, 0, 0.3)");
        }

        if (controller.getBoard().getPiece(x, y) != null) {
            button.setText("♛");
            button.getStyle().set("font-size", "24px");
            button.getStyle().set("color", "white");
        } else {
            button.setText("");
        }
    }

    private void styleBaseButton(Button button) {
        button.getStyle().set("border", "1px solid rgba(255, 255, 255, 0.2)");
        button.getStyle().set("border-radius", "4px");
        button.getStyle().set("transition", "0.3s ease all");

        button.getElement().getStyle().set("cursor", "pointer");
        button.getElement().executeJs(
                "this.addEventListener('mouseenter', function() {" +
                        "this.style.filter = 'brightness(1.5)';" +
                        "});" +
                        "this.addEventListener('mouseleave', function() {" +
                        "this.style.filter = 'brightness(1)';" +
                        "});"
        );
    }
}
