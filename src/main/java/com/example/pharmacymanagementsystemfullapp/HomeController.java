package com.example.pharmacymanagementsystemfullapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane home_form;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AnchorPane addMedicines_form;

    @FXML
    private AnchorPane customer_form;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private Label dashboard_income;

    @FXML
    private Button dashboard_week;

    @FXML
    private Button dashboard_month;

    @FXML
    private Button dashboard_quarter;

    @FXML
    private Button dashboard_year;

    @FXML
    private TextField addMedicines_medicineID;

    @FXML
    private TextField addMedicines_productName;

    @FXML
    private ComboBox<?> addMedicines_category;

    @FXML
    private  TextField addMedicines_quantity;

    @FXML
    private TextField addMedicines_price;

    @FXML
    private ComboBox<?> addMedicines_status;

    @FXML
    private TextField addMedicines_search;

    @FXML
    private TableView<medicineData> addMedicines_tableView;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_medicineID;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_productName;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_category;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_quantity;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_price;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_status;
    
    @FXML
    private TextField customerFullName;

    @FXML
    private TextField customerPhoneNumber;

    @FXML
    private TextField customerTotal;

    @FXML
    private TextField customerPoints;

    @FXML
    private TextField customer_search;

    @FXML
    private TableView<customerData> customer_tableView;

    @FXML
    private TableColumn<customerData, String> customer_col_date;

    @FXML
    private TableColumn<customerData, String> customer_col_fullName;

    @FXML
    private TableColumn<customerData, String> customer_col_id;

    @FXML
    private TableColumn<customerData, String> customer_col_phoneNumber;

    @FXML
    private TableColumn<customerData, String> customer_col_points;

    @FXML
    private Label username;

    @FXML
    private Button logout;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button medicines_btn;

    @FXML
    private Button customer_btn;

    @FXML
    private Button purchase_btn;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private double x = 0;
    private double y = 0;

    @FXML
    void addMedicineAdd() {
        String sql = "INSERT INTO medicine (medicine_id, productName, category, quantity, price, status) "
                + "VALUES(?,?,?,?,?,?)";

        connect = database.connectDb();

        try{
            Alert alert;

            if (addMedicines_medicineID.getText().isEmpty()
                    || addMedicines_productName.getText().isEmpty()
                    || addMedicines_category.getSelectionModel().getSelectedItem() == null
                    || addMedicines_quantity.getText().isEmpty()
                    || addMedicines_price.getText().isEmpty()
                    || addMedicines_status.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else{
                // CHECK IF THE MEDICINE ID YOU WANT TO INSERT EXIST
                String checkData = "SELECT medicine_id FROM medicine WHERE medicine_id = '"
                        + addMedicines_medicineID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Medicine ID: " + addMedicines_medicineID.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addMedicines_medicineID.getText());
                    prepare.setString(2, addMedicines_productName.getText());
                    prepare.setString(3, (String) addMedicines_category.getSelectionModel().getSelectedItem());
                    prepare.setString(4, addMedicines_quantity.getText());
                    prepare.setString(5, addMedicines_price.getText());
                    prepare.setString(6, (String) addMedicines_status.getSelectionModel().getSelectedItem());

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    addMedicineShowListData();
                    addMedicineReset();

                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void addMedicineUpdate() {
        String sql = "UPDATE medicine SET productName = ?, category = ?, quantity = ?, price = ?, status = ? WHERE medicine_id = ?";

        connect = database.connectDb();

        try {
            Alert alert;

            if (addMedicines_medicineID.getText().isEmpty()
                    || addMedicines_productName.getText().isEmpty()
                    || addMedicines_category.getSelectionModel().getSelectedItem() == null
                    || addMedicines_quantity.getText().isEmpty()
                    || addMedicines_price.getText().isEmpty()
                    || addMedicines_status.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Medicine ID:" + addMedicines_medicineID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addMedicines_productName.getText());
                    prepare.setString(2, (String) addMedicines_category.getSelectionModel().getSelectedItem());
                    prepare.setString(3, addMedicines_quantity.getText());
                    prepare.setString(4, addMedicines_price.getText());
                    prepare.setString(5, (String) addMedicines_status.getSelectionModel().getSelectedItem());
                    prepare.setString(6, addMedicines_medicineID.getText());

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    addMedicineShowListData();
                    addMedicineReset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addMedicineDelete() {
        String sql = "DELETE FROM medicine WHERE medicine_id = '" + addMedicines_medicineID.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Medicine ID:" + addMedicines_medicineID.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Deleted!");
                alert.showAndWait();

                addMedicineShowListData();
                addMedicineReset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addMedicineReset() {
        addMedicines_medicineID.setText("");
        addMedicines_productName.setText("");
        addMedicines_category.getSelectionModel().clearSelection();
        addMedicines_quantity.setText("");
        addMedicines_price.setText("");
        addMedicines_status.getSelectionModel().clearSelection();
    }

    private String[] addMedicineListC = {"Hydrocodone", "Antibiotics", "Metformin", "Losartan", "Albuterol"};

    @FXML
    void addMedicineListCategory() {
        List<String> listC = new ArrayList<>();

        for (String data : addMedicineListC) {
            listC.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listC);
        addMedicines_category.setItems(listData);
    }

    private String[] addMedicineListS = {"Available", "Not Available"};

    @FXML
    void addMedicineListStatus() {
        List<String> listS = new ArrayList<>();

        for (String data : addMedicineListS) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        addMedicines_status.setItems(listData);
    }

    @FXML
    ObservableList<medicineData> addMedicineListData(){
        String sql = "SELECT * FROM medicine";

        ObservableList<medicineData> listData = FXCollections.observableArrayList();

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            medicineData medData;

            while (result.next()) {
                medData = new medicineData(result.getString("medicine_id"),
                        result.getString("productName"),
                        result.getString("category"),
                        result.getInt("quantity"),
                        result.getInt("price"),
                        result.getString("status"));

                listData.add(medData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<medicineData> addMedicineList;

    @FXML
    void addMedicineShowListData(){
        addMedicineList = addMedicineListData();

        addMedicines_col_medicineID.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        addMedicines_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addMedicines_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        addMedicines_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        addMedicines_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        addMedicines_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        addMedicines_tableView.setItems(addMedicineList);
    }

    @FXML
    void addMedicineSearch() {
        String sql = "SELECT * FROM medicine WHERE medicine_id LIKE ? or productName LIKE ? or category LIKE ? or price LIKE ? or status LIKE ?";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);

            prepare.setString(1, "%" + addMedicines_search.getText() + "%");
            prepare.setString(2, "%" + addMedicines_search.getText() + "%");
            prepare.setString(3, "%" + addMedicines_search.getText() + "%");
            prepare.setString(4, "%" + addMedicines_search.getText() + "%");
            prepare.setString(5, "%" + addMedicines_search.getText() + "%");

            result = prepare.executeQuery();

            ObservableList<medicineData> listData = FXCollections.observableArrayList();

            medicineData medData;

            while (result.next()) {
                medData = new medicineData(result.getString("medicine_id"),
                        result.getString("productName"),
                        result.getString("category"),
                        result.getInt("quantity"),
                        result.getInt("price"),
                        result.getString("status"));

                listData.add(medData);
            }

            addMedicines_tableView.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addMedicineSelect(MouseEvent event) {
        medicineData medData = addMedicines_tableView.getSelectionModel().getSelectedItem();
        int num = addMedicines_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }

        addMedicines_medicineID.setText(String.valueOf(medData.getMedicineId()));
        addMedicines_productName.setText(medData.getProductName());
        addMedicines_quantity.setText(String.valueOf(medData.getQuantity()));
        addMedicines_price.setText(String.valueOf(medData.getPrice()));
    }

    public ObservableList<customerData> customerListData() {
        String sql = "SELECT * FROM Customer";

        ObservableList<customerData> listData = FXCollections.observableArrayList();

        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            customerData cusData;
            while (result.next()) {
                cusData = new customerData(
                        result.getInt("id"),
                        result.getString("fullName"),
                        result.getString("phoneNum"),
                        result.getDate("registrationDate"),
//                        result.getDouble("total"),
                        result.getInt("loyaltyPoints")
                );

                listData.add(cusData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<customerData> customerList;
    public void customerShowListData(){
        customerList = customerListData();

        customer_col_id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customer_col_fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        customer_col_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customer_col_date.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
//        customer_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customer_col_points.setCellValueFactory(new PropertyValueFactory<>("loyaltyPoints"));

        customer_tableView.setItems(customerList);
    }

    public void customerSelect(){
        customerData cusData = customer_tableView.getSelectionModel().getSelectedItem();
        int num = customer_tableView.getSelectionModel().getSelectedIndex();

        if( (num-1) < -1) return;

        customerFullName.setText(cusData.getFullName());
        customerPhoneNumber.setText(cusData.getPhoneNumber());
        customerPoints.setText(String.valueOf(cusData.getLoyaltyPoints()));
    }

    public void customerAdd(){
        String sql = "INSERT INTO Customer (fullName,phoneNum,loyaltyPoints)"
                + " VALUES (?,?,?)";

        connect = database.connectDb();

        try{
            Alert alert;

            if(customerFullName.getText().isEmpty() || customerPhoneNumber.getText().isEmpty() || customerTotal.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            } else {
                String checkData = "SELECT * FROM Customer WHERE phoneNum = '" + customerPhoneNumber.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Customer already exists");
                    alert.showAndWait();
                }else{
                    double total = Double.parseDouble(customerTotal.getText());
                    int loyaltyPoints = (int) (total / 100000);

                    prepare = connect.prepareStatement(sql);

                    prepare.setString(1, customerFullName.getText());
                    prepare.setString(2, customerPhoneNumber.getText());
//                    prepare.setDouble(3, Double.parseDouble(customerTotal.getText()));
                    prepare.setInt(3, loyaltyPoints);

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added!");
                    alert.showAndWait();

                    customerShowListData();
                    customerReset();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public void customerReset(){
        customerFullName.setText("");
        customerPhoneNumber.setText("");
        customerTotal.setText("");
        customerPoints.setText("");
    }

    public Integer getSelectedCustomerId() {
        customerData selectedCustomer = customer_tableView.getSelectionModel().getSelectedItem();
        return (selectedCustomer != null) ? selectedCustomer.getCustomerId() : null;
    }

    public void customerUpdate(){
        String sql = "UPDATE Customer SET fullName = ?, phoneNum = ? WHERE id = ?";

        connect = database.connectDb();

        try{
            Alert alert;

            if(customerFullName.getText().isEmpty() || customerPhoneNumber.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update this customer?");
                Optional<ButtonType> option= alert.showAndWait();

                if(option.get() == ButtonType.OK){

                    Integer customerId = getSelectedCustomerId();

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, customerFullName.getText());
                    prepare.setString(2, (String) customerPhoneNumber.getText());
                    prepare.setInt(3, customerId);

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated!");
                    alert.showAndWait();

                    customerShowListData();
                    customerReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void customerDelete(){
        String sql = "DELETE FROM Customer WHERE phoneNum = '" + customerPhoneNumber.getText() + "'";
        connect = database.connectDb();

        try{
            Alert alert;

            if(customerFullName.getText().isEmpty() || customerPhoneNumber.getText().isEmpty() || customerPoints.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this customer?");
                Optional<ButtonType> option= alert.showAndWait();

                if(option.get() == ButtonType.OK){
                    statement = connect.prepareStatement(sql);
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully deleted!");
                    alert.showAndWait();

                    customerShowListData();
                    customerReset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void customerSearch(){
        String sql = "SELECT * FROM Customer WHERE fullName LIKE ? or phoneNum LIKE ?";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);

            prepare.setString(1, "%" + customer_search.getText() + "%");
            prepare.setString(2, "%" + customer_search.getText() + "%");

            result = prepare.executeQuery();

            ObservableList<customerData> listData = FXCollections.observableArrayList();

            customerData cusData;

            while (result.next()) {
                cusData = new customerData(
                        result.getInt("id"),
                        result.getString("fullName"),
                        result.getString("phoneNum"),
                        result.getDate("registrationDate"),
                        result.getInt("loyaltyPoints")
                );


                listData.add(cusData);
            }

            customer_tableView.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchForm(ActionEvent event) {
        if(event.getSource() == dashboard_btn){
            dashboard_form.setVisible(true);
            addMedicines_form.setVisible(false);
            home_form.setVisible(false);
            customer_form.setVisible(false);
            purchase_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #fff; -fx-text-fill: #C85F77; -fx-background-radius: 40;");
            medicines_btn.setStyle("-fx-background-color: #333856");
            customer_btn.setStyle("-fx-background-color: #333856;");
            purchase_btn.setStyle("-fx-background-color: #333856;");

            addMedicineShowListData();
            addMedicineListCategory();
            addMedicineListStatus();
            addMedicineSearch();
            addMedicineReset();
        }

        if(event.getSource() == medicines_btn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(true);
            home_form.setVisible(false);
            customer_form.setVisible(false);
            purchase_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #333856;");
            medicines_btn.setStyle("-fx-background-color: #fff; -fx-text-fill: #C85F77; -fx-background-radius: 40;");
            customer_btn.setStyle("-fx-background-color: #333856;");
            purchase_btn.setStyle("-fx-background-color: #333856;");

            addMedicineShowListData();
            addMedicineListCategory();
            addMedicineListStatus();
            addMedicineSearch();
            addMedicineReset();
        }

        if(event.getSource() == customer_btn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(false);
            home_form.setVisible(false);
            customer_form.setVisible(true);
            purchase_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #333856;");
            medicines_btn.setStyle("-fx-background-color: #333856;");
            customer_btn.setStyle("-fx-background-color: #fff; -fx-text-fill: #C85F77; -fx-background-radius: 40;");
            purchase_btn.setStyle("-fx-background-color: #333856;");

            customerShowListData();
//            customerSearch();
        }

        if(event.getSource() == purchase_btn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(false);
            home_form.setVisible(false);
            customer_form.setVisible(false);
            purchase_form.setVisible(true);

            dashboard_btn.setStyle("-fx-background-color: #333856;");
            medicines_btn.setStyle("-fx-background-color: #333856;");
            customer_btn.setStyle("-fx-background-color: #333856;");
            purchase_btn.setStyle("-fx-background-color: #fff; -fx-text-fill: #C85F77; -fx-background-radius: 40;");
        }
    }

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void minimize(ActionEvent event) {
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void displayUsername(){
        String user = getData.username;

        //the first letter is big, the others are small
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));
    }

    @FXML
    void logout() throws Exception {
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to log out?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();


                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                root.setOnMouseClicked(event -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged(event -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased(event -> {
                    stage.setOpacity(1);
                });
                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
        addMedicineShowListData();
        addMedicineListCategory();
        addMedicineListStatus();
        home_form.setVisible(true);
        dashboard_form.setVisible(false);
        addMedicines_form.setVisible(false);
        customer_form.setVisible(false);
        purchase_form.setVisible(false);

        customerShowListData();
    }
}