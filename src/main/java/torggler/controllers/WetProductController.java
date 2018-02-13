package torggler.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import torggler.modelFx.ProductModel;



public class WetProductController {

    @FXML
    private TextField tfNameProduct;

    @FXML
    private TextField test;

    private ProductModel productModel;



    public void initialize() {

        this.productModel = new ProductModel();
        bindings();

    }

    public void bindings() {
        // #50 13:30

        // bindowanie dwustronne
        this.tfNameProduct.textProperty().bindBidirectional(this.productModel.getProductFxObjectProperty().nameProperty());
    }

    public void bthSaveToBase(ActionEvent actionEvent) {

        productModel.saveProductInDataBase(tfNameProduct.getText());
    }
}
