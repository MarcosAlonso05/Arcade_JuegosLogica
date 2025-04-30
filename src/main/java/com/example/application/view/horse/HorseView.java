package com.example.application.view.horse;

import com.example.application.controller.horse.HorseController;
import com.example.application.model.pieces.HorsePiece;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;

@Route("horse")
public class HorseView extends VerticalLayout {
    private HorseController controller;
    private Div boardContainer;
    private Button[][] buttons;
    private int size = 8;
    private final VerticalLayout contentLayout;
    private final int MIN_SIZE = 8;
    private final int MAX_SIZE = 15;
    private Button solveButton;
    private Button resetButton;
    private boolean solutionMode = false;


    public HorseView() {
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
        H1 title = new H1("Recorrido del Caballo");
        title.getStyle().set("color", "white");

        IntegerField sizeInput = new IntegerField("Tamaño del tablero");
        sizeInput.setValue(8);
        sizeInput.setMin(MIN_SIZE);
        sizeInput.setMax(MAX_SIZE);
        sizeInput.setStepButtonsVisible(true);

        Button startButton = new Button("Comenzar", e -> {
            if (sizeInput.getValue() != null && sizeInput.getValue() >= MIN_SIZE && sizeInput.getValue() <= MAX_SIZE) {
                size = sizeInput.getValue();
                startGame();
            }
        });

        contentLayout.removeAll();
        contentLayout.add(title, sizeInput, startButton);
    }

    private void startGame() {
        this.controller = new HorseController(size);
        this.boardContainer = new Div();
        this.buttons = new Button[size][size];
        this.solutionMode = false;

        contentLayout.removeAll();

        HorizontalLayout gameLayout = new HorizontalLayout();
        gameLayout.setAlignItems(Alignment.CENTER);

        this.solveButton = new Button("Resolver", e -> showSolution());
        this.resetButton = new Button("Reiniciar", e -> resetGame());

        VerticalLayout sideLayout = new VerticalLayout();
        sideLayout.add(solveButton, resetButton);
        sideLayout.getStyle().set("margin-left", "20px");

        gameLayout.add(boardContainer, sideLayout);
        contentLayout.add(gameLayout);

        drawBoard();
    }

    private void drawBoard() {
        // Limpieza completa del contenedor
        boardContainer.removeAll();
        boardContainer.getStyle()
                .set("display", "grid")
                .set("grid-template-columns", "repeat(" + size + ", 50px)")
                .set("gap", "2px")
                .set("justify-content", "center");

        // Reinicialización de la matriz de botones
        buttons = new Button[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Button cellButton = new Button();
                cellButton.getStyle()
                        .set("width", "50px")
                        .set("height", "50px")
                        .set("background-color", "#424242")
                        .set("color", "white")
                        .set("font-size", "24px")
                        .set("padding", "0");

                final int x = i;
                final int y = j;
                cellButton.addClickListener(e -> handleCellClick(x, y));

                buttons[i][j] = cellButton;
                boardContainer.add(cellButton);
                updateCellStyle(cellButton, x, y);
            }
        }
    }

    private void handleCellClick(int x, int y) {
        if (solutionMode) return;

        if (controller.getBoard().getHorse() == null) {
            controller.placeHorse(x, y);
        } else {
            controller.moveHorse(x, y);
        }
        refreshBoard();
        checkGameState();
    }

    private void disableAllButtons() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setEnabled(false);
            }
        }
    }

    private void enableAllButtons() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setEnabled(true);
            }
        }
    }



    private void styleButton(Button button) {
        button.getStyle()
                .set("width", "50px")
                .set("height", "50px")
                .set("background-color", "#424242")
                .set("color", "white")
                .set("font-size", "24px")
                .set("padding", "0");
    }

    private void updateCellStyle(Button button, int x, int y) {
        int[][] board = controller.getBoard().getBoard();
        HorsePiece horse = controller.getBoard().getHorse();

        // Resetear estilo
        button.getStyle()
                .set("background-color", "#424242")
                .set("color", "white");
        button.setText("");

        if (solutionMode) {
            // MODO SOLUCIÓN (números)
            if (board[x][y] > 0) {
                button.setText(String.valueOf(board[x][y]));
                if (board[x][y] == 1) {
                    button.getStyle().set("background-color", "#2196F3"); // Inicio (azul)
                } else if (board[x][y] == size * size) {
                    button.getStyle().set("background-color", "#4CAF50"); // Fin (verde)
                } else if (board[x][y] == 2) {
                    button.getStyle().set("background-color", "#FF9800"); // Segundo movimiento (naranja)
                }
            }
        } else {
            // MODO JUEGO NORMAL (caballos)
            if (horse != null && horse.getX() == x && horse.getY() == y) {
                button.setText("♞");
                button.getStyle().set("background-color", "#2196F3"); // Actual (azul)
            } else if (board[x][y] > 0) {
                button.setText("♞");
                if (board[x][y] == controller.getBoard().getLastMoveNumber()) {
                    button.getStyle().set("background-color", "#4CAF50"); // Último (verde)
                }
            }
        }
    }

    private void showSolution() {
        if (controller.getBoard().getHorse() == null) {
            Notification.show("Coloca primero el caballo").addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        solutionMode = true;
        refreshBoard(); // Actualizar visualización antes de resolver

        new Thread(() -> {
            boolean solved = controller.solve();

            getUI().ifPresent(ui -> ui.access(() -> {
                if (solved) {
                    refreshBoard();
                    Notification.show("Solución encontrada").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                } else {
                    Notification.show("No existe solución desde esta posición").addThemeVariants(NotificationVariant.LUMO_ERROR);
                    solutionMode = false;
                    refreshBoard();
                }
            }));
        }).start();
    }


    private void resetGame() {
        solutionMode = false;
        controller.resetBoard();
        enableAllButtons();
        refreshBoard();
    }

    private void refreshBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                updateCellStyle(buttons[i][j], i, j);
            }
        }
    }

    private void checkGameState() {
        if (controller.hasWon()) {
            showEndMessage("¡Victoria! Completaste el tablero.", "green");
        } else if (controller.hasLost()) {
            showEndMessage("¡Derrota! No hay movimientos posibles.", "red");
        }
    }

    private void showEndMessage(String message, String color) {
        Div messageDiv = new Div();
        messageDiv.setText(message);
        messageDiv.getStyle()
                .set("color", color)
                .set("font-size", "24px")
                .set("margin-top", "20px");

        contentLayout.add(messageDiv);
    }
}