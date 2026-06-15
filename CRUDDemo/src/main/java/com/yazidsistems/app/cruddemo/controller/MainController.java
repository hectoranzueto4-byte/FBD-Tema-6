package com.yazidsistems.app.cruddemo.controller;

import com.yazidsistems.app.cruddemo.entity.Model;
import com.yazidsistems.app.cruddemo.repository.Repository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;


import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {

    @Autowired
    private Repository repo ;
    private int id;
    private Model item;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ListView<Model> lvRegister;
    @FXML
    private Button btnActualizar,btnEliminar,btnGuardar,btnLimpiar;

    @FXML
    private Label lblAdvertencia,lblTotal;

    @FXML
    private TextField txtID,txtName,txtPhone;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtID.setDisable(true);
        //esta seccion sirve para poder elegir los elementos existentes de la lista y poder modificarlos o borrarlos
        lvRegister();
        lvRegister.getSelectionModel().selectedItemProperty().addListener((obs,old, newvalue) ->{
            if (newvalue !=null){
                id = newvalue.getId();
                item = item();
                txtID.setText(String.valueOf(item.getId()));
                txtName.setText(item.getNombre());
                txtPhone.setText(item.getTelefono());
                btnGuardar.setVisible(false);
                btnActualizar.setVisible(true);
                btnEliminar.setVisible(true);
                btnLimpiar.setVisible(true);
                lblAdvertencia.setVisible(false);
            }
            }
        );

    }
    public Model item(){
        return repo.findById(id).get();
    }

    public void lvRegister(){
        lvRegister.setItems(FXCollections.observableArrayList(repo.findAll()));
        lblTotal.setText(String.valueOf(lvRegister.getItems().size()));
    }
    //accion para guardar un nuevo elemento en la base de datos
    @FXML
    public void OnSave(ActionEvent event) {
        if (!txtName.getText().trim().isEmpty()){
            Model mod = new Model();
            mod.setNombre(txtName.getText());
            mod.setTelefono(txtPhone.getText());
            repo.save(mod);
            lvRegister();
            Clean();
        } else {
            lblAdvertencia.setVisible(true);
        }

    }
    //accion para limpiar las cajas de texto despues de guardar
    @FXML
    public void Clean() {
        txtID.clear();
        txtName.clear();
        txtPhone.clear();
    }
    //accion para oprimir un boton y limpiar lo que este escrito en una caja de texto
    @FXML
    void OnClean(ActionEvent event) {
        txtID.clear();
        txtName.clear();
        txtPhone.clear();
        btnGuardar.setVisible(true);
        btnActualizar.setVisible(false);
        btnEliminar.setVisible(false);
        btnLimpiar.setVisible(false);
        lblAdvertencia.setVisible(false);
    }
//eliminar elemento de la base de datos
    @FXML
    void OnDelete(ActionEvent event) {
        repo.delete(item);
        Clean();
        lvRegister();
        btnGuardar.setVisible(true);
        btnActualizar.setVisible(false);
        btnEliminar.setVisible(false);
        btnLimpiar.setVisible(false);
        lblAdvertencia.setVisible(false);

    }
//cambiar los un elemento en la base de datos
    @FXML
    void OnUpdate(ActionEvent event) {
        item.setNombre(txtName.getText());
        item.setTelefono(txtPhone.getText());
        repo.save(item);
        lvRegister();
        txtID.clear();
        txtName.clear();
        txtPhone.clear();
        btnGuardar.setVisible(true);
        btnActualizar.setVisible(false);
        btnEliminar.setVisible(false);
        btnLimpiar.setVisible(false);
        lblAdvertencia.setVisible(false);

    }
}
