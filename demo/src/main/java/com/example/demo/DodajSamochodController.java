package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DodajSamochodController {
    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    private TextField nr_vin;
    @FXML
    private TextField marka;
    @FXML
    private TextField model;
    @FXML
    private TextField rocznik;
    @FXML
    private TextField przebieg;
    @FXML
    private TextField pojemnosc;
    @FXML
    private TextField kodSilnika;
    @FXML
    private TextField kodSkrzyni;
    @FXML
    private TextField kodLakieru;
    @FXML
    private TextField uwagi;


    @FXML
    private void powrotDoMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("StronaDlaPracownikow.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void pokazListeSamochodow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("listaSamochodow.fxml"));
        Parent root = loader.load();

        Scene scene1 = new Scene(root);

        Stage stage1 = new Stage();
        stage1.setTitle("Lista Samochodow");
        stage1.setScene(scene1);

        stage1.show();
    }

    private boolean sprawdzanieDanych() throws SQLException {
        if (nr_vin.getText().trim().isEmpty() || marka.getText().trim().isEmpty() || model.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Brak Zgodnosci danych");
            alert.setContentText("Dane w polach: Nr_Vin, Marka, Model.  MUSZĄ BYĆ WYPEŁNIONE!");
            alert.show();
            return false;
        }else {
            try{
            String url = "jdbc:sqlite:D:/dam rade/Programowanie_Projekt/sqlite-tools-win32-x86-3420000/BazaDanychProjekt.db";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement st = connection.prepareStatement("SELECT nr_vin FROM samochody where nr_vin = ?");
            st.setString(1, nr_vin.getText());
            ResultSet resultSet = st.executeQuery();
            System.out.println(resultSet.getString("nr_vin"));
            if (resultSet.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Bład dodania samochodu");
                alert.setContentText("W naszej bazie danych posiadamy już samochód o tym numerze VIN. Dodaj kolejny samochód");
                alert.show();
                wyczyscDane();
                return false;
            }
            return true;
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }


    private void wyczyscDane() {
        nr_vin.clear();
        marka.clear();
        model.clear();
        rocznik.clear();
        przebieg.clear();
        pojemnosc.clear();
        kodLakieru.clear();
        kodSilnika.clear();
        kodSkrzyni.clear();
        uwagi.clear();
    }

    @FXML
    private void dodajPojazd(ActionEvent event) throws IOException, SQLException {
        try {
            String url = "jdbc:sqlite:D:/dam rade/Programowanie_Projekt/sqlite-tools-win32-x86-3420000/BazaDanychProjekt.db";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement st = connection.prepareStatement("INSERT INTO Samochody (nr_vin,marka,model,rocznik,przebieg,pojemnosc,kod_silnika,kod_skrzyni,kod_lakieru,uwagi) values(?,?,?,?,?,?,?,?,?,?)");
            st.setString(1, nr_vin.getText());
            st.setString(2, marka.getText());
            st.setString(3, model.getText());
            if(rocznik.getText().trim().isEmpty()) {
                st.setNull(4,Types.NULL);
            }else{
                st.setInt(4, Integer.parseInt(rocznik.getText()));
            }
            if(przebieg.getText().trim().isEmpty()) {
                st.setNull(4,Types.NULL);
            }else{
                st.setInt(5, Integer.parseInt(przebieg.getText()));
            }
            if(pojemnosc.getText().trim().isEmpty()){
                st.setNull(4,Types.NULL);
            }else {
                st.setInt(6, Integer.parseInt(pojemnosc.getText()));
            }
            if (kodSilnika.getText().trim().isEmpty()) {
                st.setNull(7, Types.NULL);
            } else {
                st.setString(7, kodSilnika.getText());
            }
            if (kodSkrzyni.getText().trim().isEmpty()) {
                st.setNull(8, Types.NULL);

            } else {
                st.setString(8, kodSkrzyni.getText());
            }
            if (kodLakieru.getText().trim().isEmpty()) {
                st.setNull(9, Types.NULL);
            } else {
                st.setString(9, kodLakieru.getText());
            }
            if (uwagi.getText().trim().isEmpty()) {
                st.setNull(10, Types.NULL);
            } else {
                st.setString(10, uwagi.getText());
            }

            if (sprawdzanieDanych()) {
                st.executeUpdate();
                wyczyscDane();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dodano");
                alert.setContentText("Pomyślnie dodano Samochód do bazy danych!");
                alert.show();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
