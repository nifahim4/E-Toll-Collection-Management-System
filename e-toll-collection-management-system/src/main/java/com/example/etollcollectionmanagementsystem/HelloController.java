package com.example.etollcollectionmanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private TableView<DriverVehicle> driverVehicleTableView;

    @FXML
    private TableColumn<DriverVehicle, String> dateCol;

    @FXML
    private TableColumn<DriverVehicle, String> driverLisenceCol;

    @FXML
    private TextField driverLisenceText;

    @FXML
    private TableColumn<DriverVehicle, String> driverNameCol;

    @FXML
    private TextField driverNameText;

    @FXML
    private TableColumn<DriverVehicle, String> vehicleModelCol;

    @FXML
    private TextField vehicleModelText;

    @FXML
    private TableColumn<DriverVehicle, String> vehicleNumberCol;

    @FXML
    private TextField vehicleNumberText;

    @FXML
    private TableColumn<DriverVehicle, String> vehicleTypeCol;

    @FXML
    private DatePicker fromDatePick;

    @FXML
    private DatePicker toDatePick;

    @FXML
    private TableView<DriverVehicle> seachTableView;

    @FXML
    private TableColumn<DriverVehicle, String> vehicleTypeColSearch;

    @FXML
    private TableColumn<DriverVehicle, String> vehicleNumberColSearch;

    @FXML
    private TableColumn<DriverVehicle, String> driverNameColSearch;

    @FXML
    private TableColumn<DriverVehicle, String> driverLisenceColSearch;

    @FXML
    private TableColumn<DriverVehicle, String> vehicleModelColSearch;

    @FXML
    private ComboBox<String> vehicleTypeCombo;
    ObservableList<String> vehicleTypeList = FXCollections.observableArrayList("Motorcycle", "Car/JEEP", "Pickup", "Microbus",
            "Truck(up to 5 tonnes)", "Truck(5-10 tonnes)", "Truck(10-15 tonnes)", "MINI-Bus", "MEDIUM-Bus", "LARGE-Bus", "Trailer");

    @FXML
    public void handlesCollectAction(javafx.event.ActionEvent actionEvent) {
        String driverName = driverNameText.getText();
        String driverLisence = driverLisenceText.getText();
        String vehicleNumber = vehicleNumberText.getText();
        String vehicleModel = vehicleModelText.getText();
        String vehicleType = vehicleTypeCombo.getValue();

        String insertQuery = "INSERT INTO etoll (driverName, driverLisence, vehicleNumber, vehicleModel, vehicleType) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = DBConnection.getStatement().getConnection().prepareStatement(insertQuery)) {
            preparedStatement.setString(1, driverName);
            preparedStatement.setString(2, driverLisence);
            preparedStatement.setString(3, vehicleNumber);
            preparedStatement.setString(4, vehicleModel);
            preparedStatement.setString(5, vehicleType);
            preparedStatement.executeUpdate();
            driverVehicleTableView.setItems(getDriverVehicleList());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String tollMessage = "";
        switch (vehicleType) {
            case "Motorcycle":
                tollMessage = "Collect\n 100 Taka";
                break;
            case "Car/JEEP":
                tollMessage = "Collect\n 750 Taka";
                break;
            case "Pickup":
                tollMessage = "Collect\n 1,200 Taka";
                break;
            case "Microbus":
                tollMessage = "Collect\n 1,300 Taka";
                break;
            case "Truck(up to 5 tonnes)":
                tollMessage = "Collect\n 2,000 Taka";
                break;
            case "Truck(5-10 tonnes)":
                tollMessage = "Collect\n 3,000 Taka";
                break;
            case "Truck(10-15 tonnes)":
                tollMessage = "Collect\n 4,000 Taka";
                break;
            case "MINI-Bus":
                tollMessage = "Collect\n 1,400 Taka";
                break;
            case "MEDIUM-Bus":
                tollMessage = "Collect\n 2,000 Taka";
                break;
            case "LARGE-Bus":
                tollMessage = "Collect\n 2,400 Taka";
                break;
            case "Trailer":
                tollMessage = "Collect\n 5,000 Taka";
                break;
            default:
                tollMessage = "Collect\n 0 Taka";
                break;
        }

        if (!tollMessage.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Toll Collection");
            alert.setHeaderText(null);
            alert.setContentText(tollMessage);
            DialogPane dialogPane = alert.getDialogPane();
            Button button = (Button) dialogPane.lookupButton(ButtonType.OK);
            dialogPane.setStyle("-fx-font-size: 40px;");
            button.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px;");
            alert.showAndWait();
        }

        driverNameText.clear();
        driverLisenceText.clear();
        vehicleNumberText.clear();
        vehicleModelText.clear();
        vehicleTypeCombo.setValue(null);

    }

    private ObservableList<DriverVehicle> getDriverVehicleList() {
        ObservableList<DriverVehicle> driverVehicleList = FXCollections.observableArrayList();
        String selectQuery = "SELECT * FROM etoll";
        try {
            Statement statement = DBConnection.getStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while (resultSet.next()) {
                DriverVehicle driverVehicle = new DriverVehicle(resultSet.getString("driverName"),
                        resultSet.getString("driverLisence"), resultSet.getString("vehicleNumber"),
                        resultSet.getString("vehicleModel"), resultSet.getString("vehicleType"));
                driverVehicle.setDate(resultSet.getTimestamp("date").toLocalDateTime().toLocalDate().toString());
                driverVehicle.setId(resultSet.getInt("id"));
                driverVehicleList.add(driverVehicle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return driverVehicleList;
    }
    @FXML
    public void handlesSearchAction(javafx.event.ActionEvent actionEvent) {
        String fromDate = fromDatePick.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String toDate = toDatePick.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String selectQuery = "SELECT * FROM etoll WHERE date BETWEEN ? AND ?";
        try (PreparedStatement preparedStatement = DBConnection.getStatement().getConnection().prepareStatement(selectQuery)) {
            preparedStatement.setString(1, fromDate);
            preparedStatement.setString(2, toDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<DriverVehicle> driverVehicleList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                DriverVehicle driverVehicle = new DriverVehicle(resultSet.getString("driverName"),
                        resultSet.getString("driverLisence"), resultSet.getString("vehicleNumber"),
                        resultSet.getString("vehicleModel"), resultSet.getString("vehicleType"));
                driverVehicle.setDate(resultSet.getTimestamp("date").toLocalDateTime().toLocalDate().toString());
                driverVehicleList.add(driverVehicle);
            }
            seachTableView.setItems(driverVehicleList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vehicleTypeCombo.setItems(vehicleTypeList);

        driverNameCol.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        driverLisenceCol.setCellValueFactory(new PropertyValueFactory<>("driverLisence"));
        vehicleNumberCol.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        vehicleModelCol.setCellValueFactory(new PropertyValueFactory<>("vehicleModel"));
        vehicleTypeCol.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        driverVehicleTableView.setItems(getDriverVehicleList());

        driverNameColSearch.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        driverLisenceColSearch.setCellValueFactory(new PropertyValueFactory<>("driverLisence"));
        vehicleNumberColSearch.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        vehicleModelColSearch.setCellValueFactory(new PropertyValueFactory<>("vehicleModel"));
        vehicleTypeColSearch.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    public void handlesDeleteAction(javafx.event.ActionEvent actionEvent) {
        DriverVehicle driverVehicle = seachTableView.getSelectionModel().getSelectedItem();
        if (driverVehicle != null) {
            String deleteQuery = "DELETE FROM etoll WHERE id = ?";
            try (PreparedStatement preparedStatement = DBConnection.getStatement().getConnection().prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, String.valueOf(driverVehicle.getId()));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        seachTableView.setItems(getDriverVehicleList());
        driverVehicleTableView.setItems(getDriverVehicleList());
    }
}