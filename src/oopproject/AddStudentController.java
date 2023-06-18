/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopproject;

import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Student;
import oopProjet.dbConnect;

/**
 * FXML Controller class
 *
 * @author glodi
 */
public class AddStudentController implements Initializable {
    ObservableList list = FXCollections.observableArrayList();
  
    @FXML
    private TableView<Student> adminTable;
    @FXML
    private JFXTextField nameFld;
    @FXML
    private JFXTextField lengthFld;
    @FXML
    private JFXTextField widthFld;
    @FXML
    private JFXTextField heightFld;
    @FXML
    private JFXTextField weightFld;
    @FXML
    private JFXTextField receiverFld;
    @FXML
    private JFXTextField cityFld;
    //@FXML
   // private JFXTextField dimensionCol;
    
    
    String query, viewQuery ;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Student student = null;
    private boolean update;
    int studentId, id;
    ObservableList<Student> studentList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    @FXML
    private void save(MouseEvent event) throws SQLException, IOException {
        /*ArrayList<Student>details = new ArrayList<>();
         StringBuilder sb = new StringBuilder();
         sb.append(nameFld.getText()) ;
         sb.append(lengthFld.getText());
         sb.append(widthFld.getText());
         sb.append(heightFld.getText());
         sb.append(weightFld.getText());
         sb.append(receiverFld.getText());
         sb.append(cityFld.getText());
         
           File file = new File ("C:\\Users\\glodi\\OneDrive\\Documents\\reader\\result.csv");
           FileWriter fw = new FileWriter(file);
           fw.write(sb.toString());*/         
        /* nameFld.getText();
         PrintWriter writer;
         
         writer = new PrintWriter("C:\\Users\\glodi\\OneDrive\\Documents\\reader\\result.csv");
         for (int i = 0; i < details.size(); i++) {
             writer.println(nameFld.getText());            
        }*/
            //FileWriter fw = new FileWriter(file);
        // for (String detail : details) {
        //String line =  nameFld.getText();
        // bw.write(line);
        //fw.write(sb.toString());
       // try (BufferedWriter bw = new BufferedWriter(fw)) {
            // for (String detail : details) {
            //String line =  nameFld.getText();
            // bw.write(line);
            //fw.write(sb.toString());
            //String line = sb.toString();
            //String data;
            //data = sb.toString();
            //bw.write(data);
            //fw.write(data+",");
            // bw.newLine();
        //}           
           // bw.close();
           // fw.write(sb.toString());
           // fw.close();  
        connection = dbConnect.getConnect();
        String name = nameFld.getText();
        String length = lengthFld.getText();
        String width = widthFld.getText();
        String height = heightFld.getText();
        String weight = weightFld.getText();
        String receiver = receiverFld.getText();
        String city = cityFld.getText(); 
        String carType;
        int dimension, l, h,w;
        l = Integer.parseInt(length);
        w = Integer.parseInt(width);
        h = Integer.parseInt(height);
        dimension = l*w*h;     
        if(name.isEmpty()||length.isEmpty()||width.isEmpty()||height.isEmpty()||weight.isEmpty()||receiver.isEmpty()||city.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All Data");
            alert.showAndWait();
        }else{
     try {
            Parent parent = FXMLLoader.load(getClass().getResource("/oopproject/FXMLDocument.fxml"));
           // Parent parent2 = FXMLLoader.load(getClass().getResource("/oopproject/FXMLDocument.fxml"));
           // Parent parent = FXMLLoader.load(getClass().getResource("/oopproject/textArea.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //check the condition to determine the car type
        if (dimension <=200) {
            carType ="Sedan";
            
        }else if(dimension >200 && dimension <=1000){
            carType = "Bakkie";
        }else{
            carType="Truck";
        }            
            getQuery();           
            insert();  
            insertView(dimension, carType); 
            clear();  
        }
        
    }
    @FXML
    private void clear() {
      nameFld.setText(null);
      lengthFld.setText(null);
      widthFld.setText(null);
      heightFld.setText(null);
      weightFld.setText(null);
      receiverFld.setText(null);
      cityFld.setText(null);
     // dimensionCol.setText(null);
           
    }

    void getQuery() {  
        if(update == false){
            
            query = "INSERT INTO `admin`(name, length, width, height, weight, receiver,"
                + "city ) VALUES (?,?,?,?,?,?,?)";
            viewQuery ="INSERT INTO `client_screen`(sender, receiver, dimension, type,city ) VALUES (?,?,?,?,?)";
            
        }else{
            query = "UPDATE `admin` SET "
                    + "`name`=?,"
                    + "`length`=?,"
                    + "`width`=?,"
                    + "`height`=?,"
                    + "`weight`=?,"
                    + "`receiver`=?,"
                    + "`city`=? WHERE `id` = '"+studentId+"'";
            viewQuery = "UPDATE `client_screen` SET "
                    + "`sender`=?,"
                    + "`receiver`=?,"
                    + " `dimension` = ?"
                    + " `type` = ?"
                    + "`city`=? WHERE `id` = '"+studentId+"'";
        }
            
 }
    private void insertView(int dimension, String carType) {
        try{
          
            preparedStatement = connection.prepareStatement(viewQuery);            
            preparedStatement.setString(1, nameFld.getText());
            preparedStatement.setString(2, receiverFld.getText());
            preparedStatement.setInt(3, dimension); 
            preparedStatement.setString(4, carType);
            preparedStatement.setString(5, cityFld.getText()); 
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    
   private void insert() {
        try{
          
            preparedStatement = connection.prepareStatement(query);            
            preparedStatement.setString(1, nameFld.getText());
            preparedStatement.setInt(2, Integer.parseInt(lengthFld.getText()));              
            preparedStatement.setInt(3,Integer.parseInt(widthFld.getText()));
            preparedStatement.setInt(4,Integer.parseInt(heightFld.getText()));
            preparedStatement.setInt(5,Integer.parseInt(weightFld.getText()));
            preparedStatement.setString(6, receiverFld.getText());
            preparedStatement.setString(7, cityFld.getText()); 
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
  void setTextField(int id,String name,int length,int width,int height,int weight,String receiver,String city){
       
       studentId = id;
       nameFld.setText(name);
       lengthFld.setText(Integer.toString(length));
       widthFld.setText(Integer.toString(width));
       heightFld.setText(Integer.toString(height));
       weightFld.setText(Integer.toString(weight));
       receiverFld.setText(receiver);
       cityFld.setText(city);
       //dimensionCol.setText(Integer.toString(dimension));
       
   }
    void setUpdate(boolean b) {
        this.update = b;

    }   

   
}
    

