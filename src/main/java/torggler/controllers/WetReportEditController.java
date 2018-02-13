package torggler.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.NumberStringConverter;
import torggler.ApplicationException;
import torggler.modelFx.BaseFx;
import torggler.modelFx.WetReportEditModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class WetReportEditController {

    @FXML
    private Label labelEditProduct;

    @FXML
    private Label lbl_edit_time;

    @FXML
    private TextArea textAreaEditExtendedInfo;

    @FXML
    private ComboBox<BaseFx> comboBoxEditBase;

    @FXML
    private TextField textFieldEditPack;

    @FXML
    private TextField textFieldEditOrderQantity;

    @FXML
    private TextField textFieledEditcolOrderRealize;

    @FXML
    private TextArea textAreaEditComment;

    @FXML
    private ToggleButton btnSaveChanges;



    private WetReportEditModel wetReportEditModel;

    public void initialize(){
        this.wetReportEditModel = new WetReportEditModel ();


        try {
            wetReportEditModel.initBaseList ();
        } catch (ApplicationException e) {
            e.printStackTrace ( );
        }

        bindigsTime ();
    }

    public void bindigsTime(){
        this.lbl_edit_time.textProperty().bindBidirectional(wetReportEditModel.getEditOrderFxObjectProperty ()
                .edit_dateProperty (), new LocalDateTimeStringConverter());
    }

    public void bindings(){


        //Data i Czas
       // V1
        String date = new Date().toString();
       // lbl_edit_time.setText(date);

        // V2
       // lbl_edit_time.setText (LocalDateTime.now ().format (DateTimeFormatter.ISO_DATE_TIME));

        // V3
        //lbl_edit_time.setText (wetReportEditModel.getEditOrderFxObjectProperty ().edit_dateProperty ().toString ());



        wetReportEditModel.getEditOrderFxObjectProperty ().edit_dateProperty ().set (LocalDateTime.now ());

        //Produkt a jak do labelki

        //this.wetReportEditModel.getEditOrderFxObjectProperty ().goodsPropertyProperty().bind(this.labelEditProduct
          //      .textProperty ());

        //Informacja uzupełniająca
        this.textAreaEditExtendedInfo.textProperty ().bindBidirectional (this.wetReportEditModel
                .getEditOrderFxObjectProperty ().compInfoProperty ());

        //Użyta baza
        this.comboBoxEditBase.setItems((this.wetReportEditModel.getBaseFxObservableList()));
        this.comboBoxEditBase.valueProperty().bindBidirectional(this.wetReportEditModel.getEditOrderFxObjectProperty ().baseFxProperty());

        //Opakowanie
        this.textFieldEditPack.textProperty ().bindBidirectional (this.wetReportEditModel
                .getEditOrderFxObjectProperty ().packProperty (), new NumberStringConverter ( ));

        //Ilość zamówiona
        this.textFieldEditOrderQantity.textProperty ().bindBidirectional (this.wetReportEditModel
                .getEditOrderFxObjectProperty ().order_quantityProperty (), new NumberStringConverter ( ));

        //Ilość wykonana
        this.textFieledEditcolOrderRealize.textProperty ().bindBidirectional (this.wetReportEditModel
                .getEditOrderFxObjectProperty ().order_realizeProperty (), new NumberStringConverter ());

        //Komentarz
        this.textAreaEditComment.textProperty ().bindBidirectional (this.wetReportEditModel
                .getEditOrderFxObjectProperty ().commentProperty ());

    }

    @FXML
    void onActionSaveChanges(ActionEvent event) {

        //uwaga brakuje usera do prawidlowego zapisu!!!!!!!!! //w bazie rozdzielic userow

        try {
            this.wetReportEditModel.saveChangesInDataBase ();
        } catch (ApplicationException e) {
            e.printStackTrace ( );
        }

        //zamknięcie okna
        Stage stage = (Stage)
                this.btnSaveChanges.getScene().getWindow();
        stage.close();
    }

    public void btnCancel(ActionEvent actionEvent) {
        Stage stage = (Stage)
                this.btnSaveChanges.getScene().getWindow();
        stage.close();
    }

    public WetReportEditModel getWetReportEditModel() {
        return wetReportEditModel;
    }

    public void setWetReportEditModel(WetReportEditModel wetReportEditModel) {
        this.wetReportEditModel = wetReportEditModel;
    }


}
