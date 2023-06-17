package com.example.demo;

import com.example.demo.Database_Connect.Connect;
import com.example.demo.Objekty.Produkty;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StronaDlaGosciController implements Initializable {
    @FXML
    private TableColumn<Produkty,String> nazwa;
    @FXML
    private TableColumn<Produkty,String> marka;
    @FXML
    private TableColumn<Produkty,String> model;
    @FXML
    private TableColumn<Produkty, String> cena;
    @FXML
    private TableView <Produkty> tabela;

    @FXML
    private TextField tfMarka;
    @FXML
    private TextField tfModel;
    @FXML
    private TextField tfRocznik;
    @FXML
    private TextField tfCena;
    @FXML
    private TextField tfSzukaj;

    @FXML
    private Label Kontakt;


    private String sql = "SELECT s.marka, s.model, c.nazwa, c.cena FROM Czesci c join Samochody s on s.nr_vin=c.nr_vin_samochodu where 1=1";

    @FXML
    private void Wyszukiwanie (ActionEvent event) throws SQLException {
        ObservableList<Produkty> Produkty = tabela.getItems();
        Produkty.clear();

        String url = "jdbc:sqlite:D:/dam rade/Programowanie_Projekt/sqlite-tools-win32-x86-3420000/BazaDanychProjekt.db";
        Connection conn = null;
        conn = DriverManager.getConnection(url);
        ResultSet rs = null;

        if(!tfMarka.getText().trim().isEmpty()){
            sql = sql + " AND s.marka Like '%" + tfMarka.getText() +"%'";
        }
        if(!tfModel.getText().trim().isEmpty()){
            sql = sql + " AND s.model Like '%" + tfModel.getText() +"%'";
        }
        if(!tfRocznik.getText().trim().isEmpty()){
            sql = sql + " AND s.rocznik = " + tfRocznik.getText();
        }
        if(!tfCena.getText().trim().isEmpty()){
            sql = sql + " AND c.cena < " + tfCena.getText();
        }
        if(!tfSzukaj.getText().trim().isEmpty()){
            sql = sql + " AND c.nazwa Like '%" + tfSzukaj.getText() +"%'";
        }

        try{
            PreparedStatement prst = conn.prepareStatement(sql);
            rs = prst.executeQuery();
            while (rs.next() && rs!=null){
                Produkty.add(new Produkty(rs.getString("nazwa"),rs.getString("marka"),rs.getString("model"),String.valueOf(rs.getFloat("cena"))));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(sql);
        sql = "SELECT s.marka, s.model, c.nazwa, c.cena FROM Czesci c join Samochody s on s.nr_vin=c.nr_vin_samochodu where 1=1";

        tabela.setItems(Produkty);
    }

    @FXML
    private void showKontakt(ActionEvent event) throws SQLException {

        String url = "jdbc:sqlite:D:/dam rade/Programowanie_Projekt/sqlite-tools-win32-x86-3420000/BazaDanychProjekt.db";
        String Wiadomosc = "";
        Connection conn = null;
        conn = DriverManager.getConnection(url);
        ResultSet rs = null;

        String sql = "select imie , nr_telefonu from Pracownicy Limit 3";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next() && rs!=null ){
                Wiadomosc =  Wiadomosc+rs.getString("imie") +": "+ rs.getString("nr_telefonu" ) + "\n";
            }
            Kontakt.setText(Wiadomosc);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void wyjscie( ActionEvent event){
        Platform.exit();
    }


    ObservableList<Produkty> initialData() throws SQLException {
        ObservableList<Produkty> Produkty;
        Produkty = FXCollections.observableArrayList();
        ResultSet rs = null;

        String url = "jdbc:sqlite:D:/dam rade/Programowanie_Projekt/sqlite-tools-win32-x86-3420000/BazaDanychProjekt.db";
        Connection conn = null;
        conn = DriverManager.getConnection(url);
        try{
            PreparedStatement prst = conn.prepareStatement(sql);
            rs = prst.executeQuery();
            while (rs.next() && rs!=null){
                Produkty.add(new Produkty(rs.getString("nazwa"),rs.getString("marka"),rs.getString("model"),String.valueOf(rs.getFloat("cena"))));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return Produkty;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nazwa.setCellValueFactory(new PropertyValueFactory<Produkty,String>("nazwa"));
        marka.setCellValueFactory(new PropertyValueFactory<Produkty,String>("marka"));
        model.setCellValueFactory(new PropertyValueFactory<Produkty,String>("model"));
        cena.setCellValueFactory(new PropertyValueFactory<Produkty,String>("cena"));

        try {
            tabela.setItems(initialData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
