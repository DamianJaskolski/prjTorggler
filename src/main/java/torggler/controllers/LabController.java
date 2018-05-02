package torggler.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;
import torggler.ApplicationException;
import torggler.UserSingleton;
import torggler.modelFx.*;


import java.io.IOException;
import java.time.LocalDateTime;

//odpowiednik #44

public class LabController {

    @FXML
    private Label lblUser;

    @FXML
    private TextArea labComment;

    @FXML
    private ComboBox<LabFx> labStatusCB;


    @FXML
    private Label lblProdukt;

    @FXML
    private Label lblBase;

    @FXML
    private Label lblDate;

    @FXML
    private ToggleButton btnSave;


    @FXML
    private TextField test;

    private OrderModel orderModel;
    private LabModel labModel;

    public void initialize() {

        this.labModel = new LabModel ( );



        try {
            this.labModel.initStatusList ( );
        } catch (ApplicationException e) {
            e.printStackTrace ( );
        }

        this.lblUser.setText (UserSingleton.getInstance ( ).log_in);

    }


    public void bindings() {
        // #50 13:30
        // bidnowanie jednostronne pobierające dane z textProperty do getOrderFxObjectProperty
        // this.orderModel.getOrderFxObjectProperty ().labcommentProperty ().bind (this.labComment.textProperty ());


        // bindowanie dwustronne
        this.labComment.textProperty ( ).bindBidirectional (this.labModel.getLabFxObjectProperty ( ).labcommentProperty ( ));

        lblProdukt.textProperty().bindBidirectional (this.labModel.getLabFxObjectProperty ().goodsPropertyProperty ()
                .getValue ().nameWetGoodsPropertyProperty ());

        this.lblBase.textProperty ().bindBidirectional (this.labModel.getLabFxObjectProperty ().baseFxProperty ().getValue ()
                .nameProperty ());

        this.labStatusCB.setItems ((this.labModel.getStatusFxObservableList ( )));
        this.labStatusCB.valueProperty ( ).bindBidirectional (this.labModel.getLabFxObjectProperty ( ).statusFxProperty ( ));

        this.lblDate.textProperty ().bindBidirectional (this.labModel.getLabFxObjectProperty ().create_dateProperty (), new
                LocalDateTimeStringConverter () );


}


    public void saveOnAction(ActionEvent actionEvent) {
        // binduje się jedno pole, uzywac tylko do edycji
        try {
            this.labModel.saveLabInDataBase ( );
        } catch (ApplicationException e) {
            e.printStackTrace ( );
        }

        //zamknięcie okna
        Stage stage = (Stage)
                this.btnSave.getScene ( ).getWindow ( );
        stage.close ( );
    }

    public void cancelOnAction() {
        //zamknięcie okna
        Stage stage = (Stage)
                this.btnSave.getScene ( ).getWindow ( );
        stage.close ( );
    }

    //GET
    public OrderModel getOrderModel() {
        return orderModel;
    }

    public LabModel getLabModel() {
        return labModel;
    }
}