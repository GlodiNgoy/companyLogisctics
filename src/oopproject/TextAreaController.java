/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopproject;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.LoadBox;
import models.Student;
import oopProjet.dbConnect;

/**
 * FXML Controller class
 *
 * @author glodi
 */
public class TextAreaController implements Initializable {
    @FXML
    private TableView<LoadBox> clientTable;
    @FXML
    private TableColumn<LoadBox,Integer> idArea;

    @FXML
    private TableColumn<LoadBox, String> senderArea;

    @FXML
    private TableColumn<LoadBox, String> receiverArea;

    @FXML
    private TableColumn<LoadBox, String> cityArea;

    @FXML
    private TableColumn<LoadBox, Integer> dimensionArea;

    @FXML
    private TableColumn<LoadBox, String> vehicleArea;
    String query ;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Student student = null;
    int studentId;
    ObservableList<LoadBox> boxList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
     try {
            connection = dbConnect.getConnect();
            boxList.clear();
            query = "SELECT * FROM `client_screen` ";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
          while(resultSet.next()){
                boxList.add(new LoadBox(
                resultSet.getInt("id"),
                resultSet.getString("sender"),
                resultSet.getString("receiver"),
                resultSet.getInt("dimension"),
                resultSet.getString("type"),
                resultSet.getString("city")));
                clientTable.setItems(boxList); 
                loadData();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TextAreaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        private void loadData() {
            idArea.setCellValueFactory(new PropertyValueFactory<>("id"));
            senderArea.setCellValueFactory(new PropertyValueFactory<>("sender"));
            receiverArea.setCellValueFactory(new PropertyValueFactory<>("receiver"));
            dimensionArea.setCellValueFactory(new PropertyValueFactory<>("dimension"));
            vehicleArea.setCellValueFactory(new PropertyValueFactory<>("type"));
            cityArea.setCellValueFactory(new PropertyValueFactory<>("city"));

  }
        @FXML
    void close(MouseEvent event) {
       try {
            Parent parent = FXMLLoader.load(getClass().getResource("/oopproject/addStudent.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
}
    

