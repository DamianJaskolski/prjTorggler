package torggler.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import javafx.stage.Stage;
import torggler.ApplicationException;
import torggler.modelFx.*;


import java.io.IOException;

//odpowiednik #44

public class LabController {


    @FXML
    private TextArea labComment;

    @FXML
    private ComboBox<LabFx> labStatusCB;

    @FXML
    private ComboBox<BaseFx> labBaseCB;

    @FXML
    private ComboBox<GoodsProperty> cmboBoxProdukt;

    @FXML
    private ToggleButton btnSave;


    @FXML
    private TextField test;

    private OrderModel orderModel;
    private LabModel labModel;

    public void initialize() {

        this.labModel = new LabModel();
        bindings();


        try {
            this.labModel.initStatusList();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }


    }


    public void bindings() {
        // #50 13:30
        // bidnowanie jednostronne pobierające dane z textProperty do getOrderFxObjectProperty
        // this.orderModel.getOrderFxObjectProperty ().labcommentProperty ().bind (this.labComment.textProperty ());

        // bindowanie dwustronne
        this.labComment.textProperty().bindBidirectional(this.labModel.getLabFxObjectProperty().labcommentProperty());

        this.cmboBoxProdukt.setItems((this.labModel.getGoodsPropertyObservableList ()));
        this.cmboBoxProdukt.valueProperty().bindBidirectional(this.labModel.getLabFxObjectProperty().goodsPropertyProperty ());
// uwaga blad lbl z cmbobox
        this.labBaseCB.setItems((this.labModel.getBaseFxObservableList()));
        this.labBaseCB.valueProperty().bindBidirectional(this.labModel.getLabFxObjectProperty().baseFxProperty());

        this.labStatusCB.setItems((this.labModel.getStatusFxObservableList()));
        this.labStatusCB.valueProperty().bindBidirectional(this.labModel.getLabFxObjectProperty().statusFxProperty());

    }


    public void saveOnAction(ActionEvent actionEvent) {
        // binduje się jedno pole, uzywac tylko do edycji
        try {
            this.labModel.saveLabInDataBase();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

        //zamknięcie okna
        Stage stage = (Stage)
                this.btnSave.getScene().getWindow();
        stage.close();
    }


    //GET
    public OrderModel getOrderModel() {
        return orderModel;
    }

    public LabModel getLabModel() {
        return labModel;
    }



}
