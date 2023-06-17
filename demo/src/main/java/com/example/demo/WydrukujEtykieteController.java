package com.example.demo;

import com.example.demo.Objekty.Etykieta;
import com.example.demo.Objekty.Samochod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class WydrukujEtykieteController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Etykieta> tabela;
    @FXML
    private TableColumn<Etykieta,String> nazwa;
    @FXML
    private TableColumn<Etykieta,String> marka;
    @FXML
    private TableColumn<Etykieta,String> model;
    @FXML
    private TableColumn<Etykieta,String> rocznik;
    @FXML
    private TableColumn<Etykieta,String> miejsceMagazynowe;

    @FXML
    private void powrotDoMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("StronaDlaPracownikow.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    ObservableList<Etykieta> initialData()throws SQLException {

        ObservableList<Etykieta> etykietas;
        etykietas = FXCollections.observableArrayList();
        ResultSet rs = null;

        String sql = "Select s.marka, s.model, s.rocznik, c.nazwa, c.miejsce_magazynowe , s.nr_vin, c.id_czesci from Samochody s join Czesci c on c.nr_vin_samochodu = s.nr_vin ";

        String url = "jdbc:sqlite:D:/dam rade/Programowanie_Projekt/sqlite-tools-win32-x86-3420000/BazaDanychProjekt.db";
        Connection conn = null;
        conn = DriverManager.getConnection(url);
        try{
            PreparedStatement prst = conn.prepareStatement(sql);
            rs = prst.executeQuery();
            while (rs.next() && rs!=null){
                etykietas.add(new Etykieta(rs.getString("nazwa"),rs.getString("marka"),rs.getString("model"),rs.getString("rocznik"),rs.getString("miejsce_magazynowe"), rs.getString("nr_vin"), rs.getString("id_czesci")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return etykietas;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nazwa.setCellValueFactory(new PropertyValueFactory<Etykieta,String>("nazwa"));
        marka.setCellValueFactory(new PropertyValueFactory<Etykieta,String>("marka"));
        model.setCellValueFactory(new PropertyValueFactory<Etykieta,String>("model"));
        miejsceMagazynowe.setCellValueFactory(new PropertyValueFactory<Etykieta,String>("miejsceMagazynowe"));
        rocznik.setCellValueFactory(new PropertyValueFactory<Etykieta,String>("rocznik"));

        try {
            tabela.setItems(initialData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Etykieta wybierz(){
        SelectionModel<Etykieta> selectionModel = tabela.getSelectionModel();
        Etykieta etykieta = selectionModel.getSelectedItem();

        return etykieta;
    }
    @FXML
    private void drukuj(ActionEvent event) throws IOException, SQLException {
        Etykieta dane = wybierz();
        Connection conn ;
        ResultSet rs = null;
        try {
            String url = "jdbc:sqlite:D:/dam rade/Programowanie_Projekt/sqlite-tools-win32-x86-3420000/BazaDanychProjekt.db";
            conn = DriverManager.getConnection(url);
            PreparedStatement st = conn.prepareStatement("SELECT sciezka from etykiety where id_czesci = ?");
            st.setString(1, dane.getId_czesci());
            rs = st.executeQuery();
            rs.next();
            String sciezka = rs.getString("sciezka");

            File file = new File(sciezka);

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
