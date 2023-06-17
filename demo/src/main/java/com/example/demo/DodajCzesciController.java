package com.example.demo;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DodajCzesciController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableColumn<Samochod,String> nr_vin;
    @FXML
    private TableColumn<Samochod,String> marka;
    @FXML
    private TableColumn<Samochod,String> model;
    @FXML
    private TableColumn<Samochod,String> rok;
    @FXML
    private  TableView<Samochod> tabela;

    @FXML
    private TextField nazwa;
    @FXML
    private TextField nr_vin_samochodu;
    @FXML
    private TextField miejsce_magazynowe;
    @FXML
    private TextField cena;
    @FXML
    private void wybierz(){
        SelectionModel<Samochod> selectionModel = tabela.getSelectionModel();
        Samochod samochod = selectionModel.getSelectedItem();
        String numerVin = samochod.getNr_vin();
        System.out.println(numerVin);

        nr_vin_samochodu.setText(numerVin);

    }

    @FXML
    private void powrotDoMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("StronaDlaPracownikow.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void wyczyscDane() {
        nr_vin_samochodu.clear();
        nazwa.clear();
        cena.clear();
        miejsce_magazynowe.clear();
    }
    private boolean sprawdzanieDanych() throws SQLException {
        if (nazwa.getText().trim().isEmpty() || nr_vin_samochodu.getText().trim().isEmpty() || miejsce_magazynowe.getText().trim().isEmpty() || cena.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Brak Zgodnosci danych");
            alert.setContentText("Dane w polach: Nazwa, Nr_Vin, Miejsce_Magazynowe, Cena.  MUSZĄ BYĆ WYPEŁNIONE!");
            alert.show();
            return false;
        }
        return true;
    }
    @FXML
    private void dodajCzesc(ActionEvent event) throws IOException, SQLException {
        try {
            String url = "jdbc:sqlite:C:\\Users\\rdxzse\\IdeaProjects\\KoniecProjekt\\demo\\src\\main\\resources\\com\\example\\demo\\BazaDanychProjekt.db";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement st = connection.prepareStatement("INSERT INTO Czesci (nr_vin_samochodu,nazwa,miejsce_magazynowe,cena) values(?,?,?,?)");
            st.setString(2, nazwa.getText());
            st.setString(1, nr_vin_samochodu.getText());
            st.setString(3, miejsce_magazynowe.getText());
            st.setFloat(4, Float.parseFloat(cena.getText()));
            if (sprawdzanieDanych()) {
                st.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Dodano");
                alert.setContentText("Pomyślnie dodano część do bazy danych!");
                alert.showAndWait();

                st = connection.prepareStatement("select * from Czesci where miejsce_magazynowe = ? ");
                st.setString(1,miejsce_magazynowe.getText());

                String id;
                String id_etykiety;
                ResultSet rs = null;
                rs=st.executeQuery();
                rs.next();
                id = rs.getString("id_czesci");

                String sciezka = KodKreskowy.Kod(id,nr_vin_samochodu.getText(),miejsce_magazynowe.getText());

                st = connection.prepareStatement("INSERT INTO Etykiety (id_czesci,sciezka) values(?,?)");
                st.setString(1,id);
                st.setString(2,sciezka);
                st.executeUpdate();

                st = connection.prepareStatement("Select id_etykiety from Etykiety where sciezka = ?");
                st.setString(1,sciezka);
                rs=st.executeQuery();
                rs.next();
                id_etykiety = rs.getString("id_etykiety");

                st = connection.prepareStatement("Update Czesci set id_etykiety = ? where id_czesci = ?");
                st.setString(1,id_etykiety);
                st.setString(2,id);
                st.executeUpdate();




                wyczyscDane();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


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

        try {
            tabela.setItems(initialData());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
