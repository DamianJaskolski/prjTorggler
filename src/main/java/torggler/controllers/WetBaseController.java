package torggler.controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.support.ConnectionSource;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import torggler.ApplicationException;
import torggler.dao.ReportDao;
import torggler.modelFx.BaseFx;
import torggler.BaseModel;
import torggler.DialogsUtils;
import torggler.dao.BaseDao;
import torggler.modelFx.OrderFx;
import torggler.tables.TabWetBase;
import torggler.tables.TabWetReport;
import torggler.utils.converters.ConverterBase;
import torggler.utils.converters.ConverterReport;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class WetBaseController implements BaseModel {

    public static final String URL = "jdbc:postgresql://localhost:5432/dbTorggler";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dagdam";
    public ComboBox cmbBoxWetProductBase;



    @FXML
    private BorderPane Tab1BorderPane;

    @FXML
    private TextField textFieldNewBase;

    @FXML
    private ToggleButton btnWetProdDelete;

    @FXML
    private ToggleButton btnWetProdBaseAdd;

    @FXML
    private ToggleButton btnWetProdEdit;


    @FXML
    private TableView<BaseFx> tabela;

    @FXML
    private TableColumn<BaseFx, Integer> C1;

    @FXML
    private TableColumn<BaseFx, String> C2;

    private TabWetBase tabWetBase;
    private WetReportController wetReportController;

    private ObjectProperty<BaseFx> currentBase = new SimpleObjectProperty<> ( );

    private ObservableList<BaseFx> baseFxObservableList = FXCollections.observableArrayList ();
    private ObservableList<OrderFx> orderFxObservableList = FXCollections.observableArrayList ( );



    public void initialize() {

        setCenterProd ("/fxml/addWetProduct.fxml");

        tabela.setItems (getBaseFxObservableList ());
        C1.setCellValueFactory (cellData->cellData.getValue ().idProperty ().asObject ());
        C2.setCellValueFactory (cellData->cellData.getValue ().nameProperty ());
        tabLoad ();

        initBindings ( );

        try {
            this.wetReportController = new WetReportController ( );
            this.wetReportController.init ( );
            this.cmbBoxWetProductBase.setItems (this.wetReportController.getBaseList ( ));

        } catch (ApplicationException e) {
            e.printStackTrace ( );


        }
    }


    //załadowanie formatki lab.fxml #27 11:00
    public void setCenterProd(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader (this.getClass ( ).getResource (fxmlPath));


        AnchorPane anchorPane = null;
        try {
            anchorPane = loader.load ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }

        Tab1BorderPane.setCenter (anchorPane);
    }


    private void tabLoad(){
        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource (URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        try {
            Dao<TabWetBase, Integer> tabWetBaseIntegerDao = DaoManager.createDao (connectionSource, TabWetBase.class);
            List<TabWetBase> tabWetBases = tabWetBaseIntegerDao.queryForAll ();
            tabWetBases.forEach (c->{
                BaseFx baseFx = new BaseFx ();
                baseFx.setId (c.getIdWetBase ());
                baseFx.setName (c.getName ());
                baseFxObservableList.add (baseFx);
            });



        } catch (SQLException e) {
            e.printStackTrace ( );
        }
    }



    public void onMouseClicedWetBaseSave(MouseEvent mouseEvent) {

        saveBaseinDataBase (textFieldNewBase.getText ( ));
    }

    public void saveBaseinDataBase(String name) {

        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource (URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        try {
            Dao<TabWetBase, Integer> tabWetBaseIntegerDao = DaoManager.createDao (connectionSource, TabWetBase.class);
            TabWetBase tabWetBase = new TabWetBase ( );
            tabWetBase.setName (name);
            tabWetBaseIntegerDao.createOrUpdate (tabWetBase);

            try {
                connectionSource.close ( );
            } catch (IOException e) {
                e.printStackTrace ( );
            }

            try {
                this.wetReportController.init ( );
            } catch (ApplicationException e) {
                e.printStackTrace ( );
            }

        } catch (SQLException e) {
            e.printStackTrace ( );
        }


    }


    public void onActWetProductBaseDelete(ActionEvent actionEvent) throws ApplicationException {
        deleteBaseById ( );
    }




    public void deleteBaseById() throws ApplicationException {
        BaseDao baseDao = new BaseDao ( );
        baseDao.deleteById (TabWetBase.class, currentBase.getValue ( ).getId ( ));
        this.wetReportController.init ( );


    }

    public void updateCategoryInDataBase() throws ApplicationException {
        BaseDao baseDao = new BaseDao ();
        TabWetBase tempBase = baseDao.findById(TabWetBase.class, getCurrentBase ().getId());
        tempBase.setName(getCurrentBase ().getName());
        baseDao.creatOrUpdate(tempBase);
        this.wetReportController.init ( );
    }

    public void onActWetProductBaseComBoxWhatIsChoose() {
        BaseFx choose = (BaseFx) cmbBoxWetProductBase.getSelectionModel ( ).getSelectedItem ( );
        setCurrentBase (choose);
        System.out.println (choose);
    }

    public void initBindings() {
        this.btnWetProdBaseAdd.disableProperty ( ).bind (textFieldNewBase.textProperty ( ).isEmpty ( ));
        this.btnWetProdDelete.disableProperty ( ).bind (currentBaseProperty ( ).isNull ( ));
        this.btnWetProdEdit.disableProperty ( ).bind (currentBaseProperty ( ).isNull ( ));
    }


    public void onActWetProductBaseEdit() throws ApplicationException {
        String newBaseName = DialogsUtils.editDialog (getCurrentBase ( ).getName ( ));
        if(newBaseName != null){
            getCurrentBase ().setName (newBaseName);
            updateCategoryInDataBase ();
        }

    }


    public BaseFx getCurrentBase() {
        return currentBase.get ( );
    }

    public ObjectProperty<BaseFx> currentBaseProperty() {
        return currentBase;
    } //sprawdzanie czy jest null uzyte

    public void setCurrentBase(BaseFx currentBase) {
        this.currentBase.set (currentBase);
    }


    public ObservableList<BaseFx> getBaseFxObservableList() {
        return baseFxObservableList;
    }

    public void setBaseFxObservableList(ObservableList<BaseFx> baseFxObservableList) {
        this.baseFxObservableList = baseFxObservableList;
    }
}