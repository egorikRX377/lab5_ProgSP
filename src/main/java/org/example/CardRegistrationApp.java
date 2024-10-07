package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class CardRegistrationApp extends Application {
    private TextField cardNumberField;
    private TextField cardHolderField;
    private ComboBox<String> expiryMonthBox;
    private ComboBox<String> expiryYearBox;
    private TextArea logArea;
    private CardDataHandler cardDataHandler;

    @Override
    public void start(Stage primaryStage) {
        cardDataHandler = new CardDataHandler("card_data.txt");

        Label cardNumberLabel = new Label("Номер карты:");
        cardNumberField = new TextField();

        Label cardHolderLabel = new Label("Держатель карты:");
        cardHolderField = new TextField();

        Label expiryDateLabel = new Label("Срок действия:");
        expiryMonthBox = new ComboBox<>();
        expiryMonthBox.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");

        expiryYearBox = new ComboBox<>();
        expiryYearBox.getItems().addAll("2024", "2025", "2026", "2027", "2028");

        Button saveButton = new Button("Зарегистрировать карту");
        saveButton.setOnAction(e -> saveCardData());

        Button loadButton = new Button("Загрузить данные о карте");
        loadButton.setOnAction(e -> loadCardData());

        logArea = new TextArea();
        logArea.setEditable(false);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(cardNumberLabel, cardNumberField, cardHolderLabel, cardHolderField,
                expiryDateLabel, expiryMonthBox, expiryYearBox, saveButton, loadButton, logArea);

        Scene scene = new Scene(layout, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Регистрация банковской карты");
        primaryStage.show();

        primaryStage.setOnCloseRequest(this::handleWindowClose);
    }

    private void saveCardData() {
        try {
            cardDataHandler.saveCardData(cardNumberField.getText(), cardHolderField.getText(),
                    expiryMonthBox.getValue(), expiryYearBox.getValue());
            logArea.setText("Карта зарегистрирована успешно!");
        } catch (IOException e) {
            logArea.setText("Ошибка сохранения карты: " + e.getMessage());
        }
    }

    private void loadCardData() {
        try {
            String data = cardDataHandler.loadCardData();
            logArea.setText(data);
        } catch (IOException e) {
            logArea.setText("Ошибка загрузки данных о карте: " + e.getMessage());
        }
    }

    private void handleWindowClose(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Закрыть окно");
        alert.setHeaderText("Правда хотите выйти?");
        alert.setContentText("Несохраненные данные будут утеряны!");

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result != ButtonType.OK) {
            event.consume();
        }
    }
}