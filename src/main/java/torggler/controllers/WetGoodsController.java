package torggler.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import torggler.modelFx.GoodsModel;
import torggler.modelFx.ProductModel;


public class WetGoodsController {

    @FXML
    private TextField tfNameProduct;

    @FXML
    private TextField test;

    private GoodsModel goodsModel;



    public void initialize() {


        this.goodsModel = new GoodsModel(); //Spr
        bindings();

    }

    public void bindings() {
        // #50 13:30

        this.tfNameProduct.textProperty().bindBidirectional(this.goodsModel.getGoodsPropObjectProperty().nameWetGoodsPropertyProperty());
    }

    public void bthSaveToBase(ActionEvent actionEvent) {

        goodsModel.saveDataInTableGoods(tfNameProduct.getText());
    }
}
