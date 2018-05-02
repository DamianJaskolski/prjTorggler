package torggler.controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.NumberStringConverter;
import torggler.ApplicationException;
import torggler.UserSingleton;
import torggler.WetGoodsSingleton;
import torggler.dao.GoodsDao;
import torggler.dao.StatusDao;
import torggler.modelFx.GoodsProperty;
import torggler.modelFx.LabFx;
import torggler.modelFx.WarehousemanModel;
import torggler.modelFx.WetReportEditModel;
import torggler.tables.Status;
import torggler.tables.TabUsers;
import torggler.tables.TabWetGoods;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class WarehousemanController {

    @FXML
    private TextField txtFldAmount;

    @FXML
    public ComboBox<LabFx> cmbStatus;

    @FXML
    private Label lblAmountRealize;

    @FXML
    private Label lblAmountQuanity;

    @FXML
    private Label lblProduct;

    @FXML
    private Label lblWarehouseman;

    @FXML
    private TextArea txtAComment;

    @FXML
    private Label lblData;

    @FXML
    private Label lblStatus;

    @FXML
    private ToggleButton btnSaveChanges;

    public static final String URL = "jdbc:postgresql://localhost:5432/dbTorggler";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dagdam";

    private WarehousemanModel warehousemanModel;
    public String valueStatus;

    ObservableList<String> status = FXCollections.observableArrayList("Prawidłowy", "Nie prawidłowy");
    public static final ObservableList data = FXCollections.observableArrayList();

    public void initialize() {
        this.warehousemanModel = new WarehousemanModel();

    }

    public void enableordisable(){

        valueStatus = cmbStatus.getSelectionModel().getSelectedItem().toString();
       // System.out.println(valueStatus);
        lblStatus.setText(valueStatus);

        if ( "Zgodna".equals(valueStatus) || "Nie podlega".equals(valueStatus) ) {
            txtFldAmount.setDisable(false);
           // System.out.println("OK");
        }else {
            txtFldAmount.setDisable(true);
           // System.out.println("NO");
        }
    }

    public void bindings(){
        //1 magazynier
        lblWarehouseman.setText(UserSingleton.getInstance().log_in);

        //2 data utworzenia zlecenia - podpowiedz
        this.lblData.textProperty ().bindBidirectional (warehousemanModel.getWhmOrderFxObjectProperty ().create_dateProperty (), new
                LocalDateTimeStringConverter () );

        //3 produkt
       // this.lblProduct.setText (WetGoodsSingleton.getInstance ().name);
        lblProduct.textProperty ().bind (warehousemanModel.getWhmOrderFxObjectProperty ().goodsPropertyProperty ()
                .getValue ().nameWetGoodsPropertyProperty ());


        //4 wartosc zlecon
        lblAmountQuanity.textProperty().bindBidirectional(this.warehousemanModel.getWhmOrderFxObjectProperty()
                .order_quantityProperty(), new NumberStringConverter());

        //5 ilosc wykonana
        lblAmountRealize.textProperty().bindBidirectional(this.warehousemanModel.getWhmOrderFxObjectProperty()
                .order_realizeProperty(), new NumberStringConverter());

        //6 ilosc odebrana przez magazyniera
        txtFldAmount.textProperty().bindBidirectional(this.warehousemanModel.getWhmOrderFxObjectProperty()
                        .amountProperty(), new NumberStringConverter( ));

        //7 status
        this.cmbStatus.setItems ((this.warehousemanModel.getStatusFxObservableList ( )));
        this.cmbStatus.valueProperty ( ).bindBidirectional (this.warehousemanModel.getWhmOrderFxObjectProperty()
                .statusFxProperty ( ));

        enableordisable();

        //8 komentarz magazyniera
        txtAComment.textProperty().bindBidirectional(this.warehousemanModel.getWhmOrderFxObjectProperty()
               .whmCommentProperty());

        // cmbStatus.setValue("wartość na wypełnienie startowe"); //-> ustawiam wartość
        // cmbStatus.setValue (t);
        //String output = cmbStatus.getSelectionModel ().getSelectedItem ().toString ();

    }

    @FXML
    void onActSave() {

        try {
            warehousemanModel.saveChangesInDataBaseWHM ();
        } catch (ApplicationException e) {
            e.printStackTrace ( );
        }

        //zamknięcie okna
        Stage stage = (Stage)
                this.btnSaveChanges.getScene().getWindow();
        stage.close();

    }

    public void onActCancel( ) {
        //zamknięcie okna
        Stage stage = (Stage)
                this.btnSaveChanges.getScene().getWindow();
        stage.close();
    }


    public void cmbo(ActionEvent actionEvent) {
     cmbStatus.getSelectionModel ().getSelectedItem ();
    }

    //SET&GET
    public WarehousemanModel getWarehousemanModel() { return warehousemanModel; }


}
