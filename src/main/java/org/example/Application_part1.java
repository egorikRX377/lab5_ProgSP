package org.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Application_part1 extends Application {

    private ListView<String> list1;
    private ListView<String> list2;
    private ListView<String> list3;

    @Override
    public void start(Stage primaryStage) {
        list1 = new ListView<>();
        list2 = new ListView<>();
        list3 = new ListView<>();

        ObservableList<String> items1 = FXCollections.observableArrayList("Item 1", "Item 2", "Item 3");
        ObservableList<String> items2 = FXCollections.observableArrayList();
        ObservableList<String> items3 = FXCollections.observableArrayList();

        list1.setItems(items1);
        list2.setItems(items2);
        list3.setItems(items3);

        Button moveItemButton = new Button("Переместить выбранный элемент");
        moveItemButton.setOnAction(e -> moveSelectedItem());

        Button moveAllButton = new Button("Переместить все элементы в следующий список");
        moveAllButton.setOnAction(e -> moveAllItems());

        Button addItemButton = new Button("Добавить элемент в список 2");
        addItemButton.setOnAction(e -> addItemToList2());

        Button editItemButton = new Button("Изменить выбранный элемент из списка 2");
        editItemButton.setOnAction(e -> editSelectedItemInList2());

        Button deleteItemButton = new Button("Удалить выбранный элемент из списка 2");
        deleteItemButton.setOnAction(e -> deleteSelectedItemFromList2());

        HBox listsBox = new HBox(10, list1, list2, list3);
        VBox controlBox = new VBox(10, moveItemButton, moveAllButton, addItemButton, editItemButton, deleteItemButton);

        VBox root = new VBox(10, listsBox, controlBox);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("List Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void moveSelectedItem() {
        moveItem(list1, list2);
        moveItem(list2, list3);
        moveItem(list3, list1);
    }

    private void moveAllItems() {
        ObservableList<String> tempList1 = FXCollections.observableArrayList(list1.getItems());
        ObservableList<String> tempList2 = FXCollections.observableArrayList(list2.getItems());
        ObservableList<String> tempList3 = FXCollections.observableArrayList(list3.getItems());

        list1.getItems().clear();
        list2.getItems().clear();
        list3.getItems().clear();

        list2.getItems().addAll(tempList1);
        list3.getItems().addAll(tempList2);
        list1.getItems().addAll(tempList3);
    }

    private void addItemToList2() {
        list2.getItems().add("Новый элемент");
    }

    private void editSelectedItemInList2() {
        String selectedItem = list2.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TextInputDialog dialog = new TextInputDialog(selectedItem);
            dialog.setTitle("Редактировать элемент");
            dialog.setHeaderText("Введите новое имя для элемента:");
            dialog.setContentText("Новое имя:");

            dialog.showAndWait().ifPresent(newName -> {
                int index = list2.getSelectionModel().getSelectedIndex();
                list2.getItems().set(index, newName);
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Пожалуйста, выберите элемент для редактирования.");
            alert.showAndWait();
        }
    }

    private void deleteSelectedItemFromList2() {
        String selectedItem = list2.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            list2.getItems().remove(selectedItem);
        }
    }

    private void moveItem(ListView<String> fromList, ListView<String> toList) {
        String selectedItem = fromList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            fromList.getItems().remove(selectedItem);
            toList.getItems().add(selectedItem);
            fromList.getSelectionModel().clearSelection();
        }
    }
}