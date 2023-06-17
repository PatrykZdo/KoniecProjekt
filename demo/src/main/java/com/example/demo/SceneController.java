package com.example.demo;

import com.example.demo.Database_Connect.hash;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import jakarta.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SceneController {
    @FXML
    private Scene scene;
    private Stage stage;
    private Parent root;
    @FXML
    private TextField emailLogin;
    @FXML
    private PasswordField passwordLogin;


    // Strona logowania
    @FXML
    protected void switchToNewEmployeeScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("NowyPracownik.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void Login(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException {

        String email = emailLogin.getText();
        String password = passwordLogin.getText();

        if (emailExists(email) && passwordCorrect(email,hash.hashingPassword(password))) {
            System.out.println("Podany użytkownik istnieje w bazie danych: " + email + " " + password);
            root = FXMLLoader.load(getClass().getResource("StronaDlaPracownikow.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else if(!emailExists(email)){
            System.out.println("Podany użytkownik nie istnieje w bazie danych");
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Email");
            alert.setContentText("Użytkownik o takim adresie email NIE ISTNIEJE");
            alert.show();

        } else if (!passwordCorrect(email,hash.hashingPassword(password))) {
            System.out.println("Złe hasło");
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Email");
            alert.setContentText("Podane hasło jest niepoprawne");
            alert.show();
        }
    }

    private boolean emailExists(String email) throws SQLException {
        try {
            String url = "jdbc:sqlite:C:\\Users\\rdxzse\\IdeaProjects\\KoniecProjekt\\demo\\src\\main\\resources\\com\\example\\demo\\BazaDanychProjekt.db";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement st = connection.prepareStatement("SELECT * FROM Pracownicy where email = ?");
            st.setString(1, email);
            ResultSet resultSet = st.executeQuery();

            connection.close();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean passwordCorrect(String email, String password) throws SQLException{
        try {
            String url = "jdbc:sqlite:C:\\Users\\rdxzse\\IdeaProjects\\KoniecProjekt\\demo\\src\\main\\resources\\com\\example\\demo\\BazaDanychProjekt.db";
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement st = connection.prepareStatement("SELECT * FROM Pracownicy where email = ? and haslo = ?");
            st.setString(1, email);
            st.setString(2, password);
            ResultSet resultSet = st.executeQuery();

            connection.close();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @FXML
    protected void LoginAsGuest(ActionEvent event) throws  IOException{
        root = FXMLLoader.load(getClass().getResource("StronaDlaGosci.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    // strona zakladania konta dla pracownika
    @FXML
    private TextField newEmployeeEmail;
    @FXML
    private PasswordField newEmployeePassword;
    @FXML
    private PasswordField newEmployeeRepeatPassword;
    @FXML
    private TextField newEmployeeName;
    @FXML
    private TextField newEmployeeFamilyName;
    @FXML
    private TextField newEmployeePhoneNumber;
    @FXML
    private Label errorMessage;

    @FXML
    protected void switchToMainScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void NewEmployeeLogged(ActionEvent event)throws IOException{
        try {
            String phoneNumber;

            if(newEmployeePhoneNumber.getText().trim().isEmpty()){
                phoneNumber = "0";
            }else{
                phoneNumber = newEmployeePhoneNumber.getText();
                if(phoneNumber.length() < 9 || phoneNumber.length() > 9 ){

                }
            }

            String newemail = newEmployeeEmail.getText();
            String password = newEmployeePassword.getText();
            String repeatPassword = newEmployeeRepeatPassword.getText();
            String name = newEmployeeName.getText();
            String familyName = newEmployeeFamilyName.getText();

            String hashPassword = hash.hashingPassword(password);
            System.out.println(hashPassword);

            if(checkingEmail(newemail) && checkingPassword(password , repeatPassword) && checkingPhoneNumber(phoneNumber)){
                String url = "jdbc:sqlite:C:\\Users\\rdxzse\\IdeaProjects\\KoniecProjekt\\demo\\src\\main\\resources\\com\\example\\demo\\BazaDanychProjekt.db";
                Connection connection = DriverManager.getConnection(url);
                PreparedStatement prst = connection.prepareStatement("INSERT INTO Pracownicy(email,imie,nazwisko,haslo,nr_telefonu) values(?,?,?,?,?)");
                prst.setString(1,newemail);
                prst.setString(2,checkingName(name));
                prst.setString(3,familyName);
                prst.setString(4,hashPassword);
                prst.setInt(5,Integer.parseInt(phoneNumber));
                prst.executeUpdate();
                System.out.println("utworzono konto");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkingEmail( String newemail) throws SQLException {
        try {
            String url = "jdbc:sqlite:C:\\Users\\rdxzse\\IdeaProjects\\KoniecProjekt\\demo\\src\\main\\resources\\com\\example\\demo\\BazaDanychProjekt.db";
            Connection conn = DriverManager.getConnection(url);
            if (newemail.length() > 6) {
                ResultSet rs = null;
                PreparedStatement prst = conn.prepareStatement("SELECT * FROM Pracownicy where email = ?");
                prst.setString(1, newemail);
                rs = prst.executeQuery();
                rs.next();
                int index ;
                index = newemail.indexOf('@');
                if(index != -1) {
                    if (rs.getString("email") != null) {

                        Alert alert= new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Błąd Email");
                        alert.setContentText("Konto o takim E-mail'u już istnieje");
                        alert.show();

                        conn.close();
                        return false;
                    } else {

                        conn.close();
                        return true;
                    }
                }else{
                    Alert alert= new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd Email");
                    alert.setContentText("Email nieprawdziwy!\n Podaj prawdziwego Maila");
                    alert.show();

                    conn.close();
                    return false;
                }
            }
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd Email");
            alert.setContentText("Email nieprawdziwy!\n Podaj prawdziwego Maila");
            alert.show();

            conn.close();
            return false;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    private boolean checkingPassword(String password, String repeatPassword) throws NoSuchAlgorithmException, IOException {
        Stage errosStage = new Stage();
        if(password.length() > 6){
            Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).+$");
            Matcher matcher = pattern.matcher(password);
            boolean matchFound = matcher.find();
            if(matchFound){
                if(hash.hashingPassword(password).equals(hash.hashingPassword(repeatPassword))){
                    return true;
                }
                else{
                    Alert alert= new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd hasła");
                    alert.setContentText("Hasła sie nie zgadzaja. Sprawdź jeszcze raz hasła!");
                    alert.show();

                    return false;
                }
            }
            else{
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd hasła");
                alert.setContentText("W haśle brakuje znaku. Wymagane znaki : \n - Wielka litera\n -Mała Litera \n - @,#,$,%,^,&,+,= \n - Cyfra [0-9]");
                alert.show();
                return false;
            }
        }else{

            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd hasła");
            alert.setContentText("Hasło musi zawierać minimum 6 znaków");
            alert.show();

            return false;
        }
    }

    private boolean checkingPhoneNumber(String phoneNumber){
        if((phoneNumber.length() < 9 && !phoneNumber.equals("0")) || phoneNumber.length() > 9){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setContentText("Niepoprawny numer telefonu");
            alert.show();
            return false;
        }else{
            return true;
        }
    }

    private  String checkingName(String name){
        if(name!=null){
            return name;
        }
        else{
            return "Pracownik";
        }
    }




}