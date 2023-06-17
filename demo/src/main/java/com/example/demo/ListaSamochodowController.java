package com.example.demo;

import com.example.demo.Objekty.Produkty;
import com.example.demo.Objekty.Samochod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ListaSamochodowController implements Initializable{

    @FXML
    private TableColumn<Samochod,String> nr_vin;
    @FXML
    private TableColumn<Samochod,String> marka;
    @FXML
    private TableColumn<Samochod,String> model;
    @FXML
    private TableColumn<Samochod,String> rok;
    @FXML
    private TableColumn<Samochod,String> przebieg;
    @FXML
    private TableColumn<Samochod,String> pojemnosc;
    @FXML
    private TableColumn<Samochod,String> kSilnika;
    @FXML
    private TableColumn<Samochod,String> kSkrzyni;
    @FXML
    private TableColumn<Samochod,String> kLakieru;
    @FXML
    private  TableView<Samochod> tabela;




    ObservableList<Samochod> initialData()throws SQLException {

        ObservableList<Samochod> Samochod;
        Samochod = FXCollections.observableArrayList();
        ResultSet rs = null;

        String sql = "Select * from Samochody ";

        String url = "jdbc:sqlite:C:\\Users\\rdxzse\\IdeaProjects\\KoniecProjekt\\demo\\src\\main\\resources\\com\\example\\demo\\BazaDanychProjekt.db";
        Connection conn = null;
        conn = DriverManager.getConnection(url);
        try{
            PreparedStatement prst = conn.prepareStatement(sql);
            rs = prst.executeQuery();
            while (rs.next() && rs!=null){
                Samochod.add(new Samochod(rs.getString("nr_vin"),rs.getString("marka"),rs.getString("model"),String.valueOf(rs.getInt("rocznik")),String.valueOf(rs.getInt("przebieg")),String.valueOf(rs.getInt("pojemnosc")),rs.getString("kod_silnika"),rs.getString("kod_skrzyni"),rs.getString("kod_lakieru")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Samochod;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nr_vin.setCellValueFactory(new PropertyValueFactory<Samochod,String>("nr_vin"));
        marka.setCellValueFactory(new PropertyValueFactory<Samochod,String>("marka"));
        model.setCellValueFactory(new PropertyValueFactory<Samochod,String>("model"));
        rok.setCellValueFactory(new PropertyValueFactory<Samochod,String>("rocznik"));
        przebieg.setCellValueFactory(new PropertyValueFactory<Samochod,String>("przebieg"));
        pojemnosc.setCellValueFactory(new PropertyValueFactory<Samochod,String>("pojemnosc"));
        kSilnika.setCellValueFactory(new PropertyValueFactory<Samochod,String>("kod_silnika"));
        kSkrzyni.setCellValueFactory(new PropertyValueFactory<Samochod,String>("kod_skrzyni"));
        kLakieru.setCellValueFactory(new PropertyValueFactory<Samochod,String>("kod_lakieru"));

        try {
            tabela.setItems(initialData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
