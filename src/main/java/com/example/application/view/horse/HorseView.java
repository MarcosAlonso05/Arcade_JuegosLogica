package com.example.application.view.horse;

import com.example.application.controller.TimerController;
import com.example.application.controller.horse.HorseController;
import com.example.application.model.pieces.HorsePiece;
import com.example.application.service.GameRecordService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

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

    private TimerController timerController = new TimerController();

    @Autowired
    private GameRecordService recordService;


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

        add(createBackButton());
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
        boardContainer.removeAll();
        boardContainer.getStyle()
                .set("display", "grid")
                .set("grid-template-columns", "repeat(" + size + ", 50px)")
                .set("gap", "2px")
                .set("justify-content", "center")
                .set("align-items", "center");

        buttons = new Button[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Button cellButton = new Button();
                cellButton.getStyle()
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

        button.getStyle()
                .set("background-color", "#424242")
                .set("color", "white");
        button.setText("");

        if (solutionMode) {
            if (board[x][y] > 0) {
                button.setText(String.valueOf(board[x][y]));
                if (board[x][y] == 1) {
                    button.getStyle().set("background-color", "#2196F3");
                } else if (board[x][y] == size * size) {
                    button.getStyle().set("background-color", "#4CAF50");
                } else if (board[x][y] == 2) {
                    button.getStyle().set("background-color", "#FF9800");
                }
            }
        } else {
            if (horse != null && horse.getX() == x && horse.getY() == y) {
                button.setText("♞");
                button.getStyle().set("background-color", "#2196F3");
            } else if (board[x][y] > 0) {
                button.setText("♞");
                if (board[x][y] == controller.getBoard().getLastMoveNumber()) {
                    button.getStyle().set("background-color", "#4CAF50");
                }
            }
        }
    }

    private void showSolution() {
        if (controller.getBoard().getHorse() == null) {
            Notification.show("Coloca primero el caballo").addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        disableAllButtons();
        solutionMode = true;
        refreshBoard();

        boolean solved = controller.solve();

        if (solved) {
            refreshBoard();
            Notification.show("Solución encontrada").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else {
            Notification.show("No existe solución desde esta posición").addThemeVariants(NotificationVariant.LUMO_ERROR);
            solutionMode = false;
            refreshBoard();
        }
        enableAllButtons();
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
            String tableroFinal = generarTextoDelTablero(size);
            saveGameResult(tableroFinal);
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

    private void saveGameResult(String finalBoard) {
        timerController.stopTimer();
        recordService.saveRecord(
                "KINGSTOUR",
                finalBoard,
                timerController.getElapsedTimeMillis(),
                timerController.getFormattedTime()
        );
    }
    private String generarTextoDelTablero(int size) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < size; row++) {
            sb.append("C".repeat(1)).append(".".repeat(size - 1));
            sb.append("\n");
        }
        return sb.toString();
    }
}