package torggler.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.NumberStringConverter;
import torggler.ApplicationException;
import torggler.modelFx.BaseFx;
import torggler.modelFx.WetReportEdit2Model;
import torggler.modelFx.WetReportEditModel;

import java.time.LocalDateTime;
import java.util.Date;

public class WetReportEdit2Controller {

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



    private WetReportEdit2Model wetReportEdit2Model;


    public void initialize(){
        this.wetReportEdit2Model = new WetReportEdit2Model ();


        try {
            wetReportEdit2Model.initBaseList ();
        } catch (ApplicationException e) {
            e.printStackTrace ( );
        }

        bindigsTime ();
    }

    public void bindigsTime(){
        this.lbl_edit_time.textProperty().bindBidirectional(wetReportEdit2Model.getEdit2OrderFxObjectProperty ()
                .edit_dateProperty (), new LocalDateTimeStringConverter());
    }

    public void bindings(){

        wetReportEdit2Model.getEdit2OrderFxObjectProperty ().edit_dateProperty ().set (LocalDateTime.now ());

        //Produkt a jak do labelki

        //this.wetReportEditModel.getEditOrderFxObjectProperty ().goodsPropertyProperty().bind(this.labelEditProduct
          //      .textProperty ());

        //Informacja uzupełniająca
        this.textAreaEditExtendedInfo.textProperty ().bindBidirectional (this.wetReportEdit2Model
                .getEdit2OrderFxObjectProperty ().compInfoProperty ());

        //Użyta baza
      this.comboBoxEditBase.setItems((this.wetReportEdit2Model.getBaseFxObservableList()));
       this.comboBoxEditBase.valueProperty().bindBidirectional(this.wetReportEdit2Model.getEdit2OrderFxObjectProperty
               ().baseFxProperty());

        //Opakowanie
        this.textFieldEditPack.textProperty ().bindBidirectional (this.wetReportEdit2Model
                .getEdit2OrderFxObjectProperty ().packProperty (), new NumberStringConverter ( ));

        //Ilość zamówiona
        this.textFieldEditOrderQantity.textProperty ().bindBidirectional (this.wetReportEdit2Model
                .getEdit2OrderFxObjectProperty ().order_quantityProperty (), new NumberStringConverter ( ));

        //Ilość wykonana
        this.textFieledEditcolOrderRealize.textProperty ().bindBidirectional (this.wetReportEdit2Model
                .getEdit2OrderFxObjectProperty ().order_realizeProperty (), new NumberStringConverter ());

        //Komentarz
        this.textAreaEditComment.textProperty ().bindBidirectional (this.wetReportEdit2Model
                .getEdit2OrderFxObjectProperty ().commentProperty ());

    }

    @FXML
    void onActionSaveChanges(ActionEvent event) {

        //uwaga brakuje usera do prawidlowego zapisu!!!!!!!!! //w bazie rozdzielic userow

        try {
            this.wetReportEdit2Model.saveChangesInDataBase ();
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

    public WetReportEdit2Model getWetReportEdit2Model() {
        return wetReportEdit2Model;
    }

    public void setWetReportEdit2Model(WetReportEdit2Model wetReportEditModel) {
        this.wetReportEdit2Model = wetReportEditModel;
    }


}
