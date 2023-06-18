/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopproject;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.Student;
import oopProjet.dbConnect;

/**
 *
 * @author glodi
 */
public class FXMLDocumentController implements Initializable {


    @FXML
    private TableView<Student> adminTable;
    
    @FXML
    private TableColumn<Student, Integer> idCol;

    @FXML
    private TableColumn<Student, String> nameCol;

    @FXML
    private TableColumn<Student, Integer> lengthCol;

    @FXML
    private TableColumn<Student, Integer> widthCol;

    @FXML
    private TableColumn<Student, Integer> heightCol;

    @FXML
    private TableColumn<Student, Integer> weightCol;

    @FXML
    private TableColumn<Student, String> receiverCol;

    @FXML
    private TableColumn<Student, String> cityCol;
    @FXML
    private TableColumn<Student, String> more;
    @FXML
    private TableColumn<Student, Integer> dimensionCol;;

    String query ;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Student student = null;
    private boolean update;
    int studentId;
    ObservableList<Student> studentList = FXCollections.observableArrayList();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      loadData();
    } 

    @FXML
     private void refreshTable() {
        try {
            studentList.clear();
            query = "SELECT * FROM `admin` ";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                studentList.add(new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("length"),
                        resultSet.getInt("width"),
                        resultSet.getInt("height"),
                        resultSet.getInt("weight"),
                        resultSet.getString("receiver"),
                        resultSet.getString("city"))); 
             adminTable.setItems(studentList);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadData() {
        connection = dbConnect.getConnect();
        refreshTable();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lengthCol.setCellValueFactory(new PropertyValueFactory<>("length"));
        widthCol.setCellValueFactory(new PropertyValueFactory<>("width"));
        heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
        weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
        receiverCol.setCellValueFactory(new PropertyValueFactory<>("receiverName"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));   
         //@FXML
   // private JFXTextField dimensionCol;  
        
         //add cell of button edit 
         Callback<TableColumn<Student, String>, TableCell<Student, String>> cellFoctory = (TableColumn<Student, String> param) -> {
             // make cell containing buttons
            final TableCell<Student, String> cell = new TableCell<Student, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                     //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    }else{
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        
                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                         editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                          deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                               try {
                                student = adminTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `admin` WHERE id  ="+student.getId();
                                connection = dbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                          });
                             editIcon.setOnMouseClicked((MouseEvent event) -> {
                            
                            student = adminTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/oopproject/addStudent.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                             
                            AddStudentController addStudentController = loader.getController();
                            addStudentController.setUpdate(true);
                            addStudentController.setTextField(student.getId(), student.getName(), 
                                    student.getLength(),student.getWidth(), student.getHeight(),
                                    student.getWeight(),student.getReceiverName(),student.getCity());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                        });
                             HBox managebtn = new HBox(editIcon, deleteIcon);
                             managebtn.setStyle("-fx-alignment:center");
                             HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                             HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                             setGraphic(managebtn);
                             setText(null);
                    }
                }
                
            };
            return cell;            
         };
         more.setCellFactory(cellFoctory);
         adminTable.setItems(studentList);
       
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getAddView(MouseEvent event) {
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

    @FXML
    private void print(MouseEvent event) throws SQLException {
        connection = dbConnect.getConnect();
     try {
            Parent parent = FXMLLoader.load(getClass().getResource("/oopproject/textArea.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
       // insert(type,dimension);
     
        
   }
 /*void getQuery() {  
        if(update == false){
            query = "INSERT INTO `view`(sender, receiver, dimension, type,city ) VALUES (?,?,?,?,?)";
            
        }else{
            query = "UPDATE `view` SET ,"
                    + "`sender`= ?"
                    + "`type` =?,"                   
                    + "`dimension`=? WHERE id = "+studentId+"";
        }
            
 }*/
    /*private void insert(String type,int dimension) {
        try{          
            preparedStatement = connection.prepareStatement(query);
           // preparedStatement.setString(1,idCol.getText());
            preparedStatement.setString(1, nameCol.getText());
            //preparedStatement.setInt(2, Integer.parseInt(lengthCol.getText()));
           // preparedStatement.setInt(3, Integer.parseInt(widthCol.getText()));
           // preparedStatement.setInt(4, Integer.parseInt(heightCol.getText()));
           // preparedStatement.setInt(5, Integer.parseInt(weightCol.getText()));    
           // preparedStatement.setString(6, receiverCol.getText());
           // preparedStatement.setString(7, cityCol.getText());  
         
            preparedStatement.setInt(2, Integer.parseInt(type));
            preparedStatement.setInt(3, dimension);
            preparedStatement.executeUpdate();
           // preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(AddStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
          
           
   }*/
void setTextField(int id,String name){
       
            studentId = id;
            nameCol.setText(name);
            
            //lengthCol.setText(Integer.toString(0));
            //widthCol.setText(Integer.toString(0));
           // heightCol.setText(Integer.toString(0));
           // weightCol.setText(Integer.toString(0));
           // receiverCol.setText("");
           // cityCol.setText(""); 
       
 }
    void setUpdate(boolean b) {
        this.update = b;

    } 
     
 }
    

