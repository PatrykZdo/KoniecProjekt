package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StronaDlaPracownikowController {
    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    protected void dodajSamochod(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("dodajSamochod.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void dodajCzesc(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("dodajCzesc.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void wydrukujEtykiete(ActionEvent event) throws  IOException{
        root = FXMLLoader.load(getClass().getResource("wydrukujEtykiete.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
