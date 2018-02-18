package torggler.controllers;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.util.converter.NumberStringConverter;
import sun.util.calendar.LocalGregorianCalendar;
import torggler.ApplicationException;
import torggler.FxmlUtils;
import torggler.UserSingleton;
import torggler.dao.ReportDao;
import torggler.modelFx.*;
import torggler.BaseModel;
import torggler.dao.BaseDao;
import torggler.tables.Status;
import torggler.tables.TabUsers;
import torggler.tables.TabWetBase;
import torggler.tables.TabWetReport;
import torggler.utils.converters.ConverterBase;
import torggler.utils.converters.ConverterReport;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class WetReportController implements BaseModel {

    private MainController mainController;

    @FXML
    private BorderPane LabBorderPane;

    public ToggleButton btnAddWetOrder;


    @FXML
    public Label create_date;

    @FXML
    private Label lblUser;

    @FXML
    private Label lblUserID;

    @FXML
    private ComboBox<GoodsProperty> cmbWetProduct;

    @FXML
    private ComboBox<BaseFx> cmbBoxBase;

    @FXML
    private TextField wetOrderedCompInfo;

    @FXML
    public TextField wetOrderPacket;

    @FXML
    private TextField wetOrderedQuantity;

    @FXML
    private TextField wedMadeQuantity;

    @FXML
    private TextArea wetOrderComment;

    @FXML
    public TableView<OrderFx> tabViewWetReport;

    @FXML
    private TableColumn<OrderFx, Integer> colPack;

    @FXML
    private TableColumn<OrderFx, String> colComment;

    @FXML
    private MenuItem deleteMenuItem;

    @FXML
    private DatePicker date1;

    @FXML
    private DatePicker date2;

    @FXML
    public CheckBox checkBox_today;


    private ConnectionSource connectionSource;

    public static final String URL = "jdbc:postgresql://127.0.0.1:5432/dbTorggler";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dagdam";

    private ObservableList<BaseFx> baseList = FXCollections.observableArrayList ( );
    private ObservableList<OrderFx> orderFxObservableList = FXCollections.observableArrayList ( );
    private ObservableList<UserFx> userFxObservableList = FXCollections.observableArrayList (); //lista przechowuje
    // Usera ktory jest zalogowany. Jest to druga metoda. Teraz wygwiazdkowana


    //---------------------------------------Table------------------------------------------

    @FXML
    private TableView<OrderFx> tableViewWetReport;

    @FXML
    private TableColumn<OrderFx, Integer> ColIDORDER;


    @FXML
    private TableColumn<OrderFx, GoodsProperty> colIDWETPRODUCTFOREIGN;

    @FXML
    private TableColumn<OrderFx, String> colWETPRODUCTEXTINFO;

    @FXML
    private TableColumn<OrderFx, BaseFx> colIDWETBASEFOREIGN;

    @FXML
    private TableColumn<OrderFx, Integer> colPACK;

    @FXML
    private TableColumn<OrderFx, Double> colORDER_QUANITY;

    @FXML
    private TableColumn<OrderFx, Double> colORDER_REALIZE;

    @FXML
    private TableColumn<OrderFx, OrderFx> colWETPRODUCTLABEDIT2;

    @FXML
    private TableColumn<OrderFx, String> colCOMMENT;

    @FXML
    private TableColumn<OrderFx, LocalDateTime> colCREATE_DATE_REPORT;

    @FXML
    private TableColumn<OrderFx, UserFx> colIDUSERFOREIGNCreate;

    @FXML
    private TableColumn<OrderFx, LocalDateTime> colEDITION_DATE_REPORT;

    @FXML
    private TableColumn<OrderFx, UserFx> colIDUSERFOREIGNEdit;

    @FXML
    private TableColumn<OrderFx, String> colWETPRODUCTLABCOMMENT;

    @FXML
    private TableColumn<OrderFx, OrderFx> colWETPRODUCTLABEDIT;

    @FXML
    private TableColumn<OrderFx, OrderFx> colWET_PRODUCT_EDIT;

    @FXML
    private TableColumn<OrderFx, LabFx> colLAB_STATUS;

    @FXML
    private TableColumn<OrderFx, OrderFx> colWETPRODUCT_DELETE;

    //Tab Default value-------------------------------------------------------------

    @FXML
    public ComboBox<LabFx> cmboBoxStatusStart;


    //------------------------------------------------------------------------------

    //towrzymy instancje
    private ReportModel reportModel;

    private OrderModel orderModel;

    private LabModel labModel;

    private WetReportEditModel wetReportEditModel;


    //-------------------------------------Table end ---------------------------------------


    //---------------------------------------FILTER-----------------------------------------

    @FXML
    private ComboBox bazaComboBox;

    @FXML
    private ComboBox cmbBoxFilterProduct;

    @FXML
    private ComboBox<UserFx> cmbBoxFilterUsers;

    private ObservableList<BaseFx> baseFxObservableList = FXCollections.observableArrayList ( );
    private ObservableList<GoodsProperty> goodsPropertyObservableList  = FXCollections.observableArrayList ( );

    //SET & GET
    //wypełnianie CM metoda 2

    private List<BaseFx> baseFxList = new ArrayList<> ();
    //Ta lista służy do przechowania pełnej listy bazy do przywrócenia po filtrowaniu
    //1. wypełniamy listę danymi -> w sekcji FILTER INTIALIZE "wypełnianie danymi"
    private List<GoodsProperty> goodsPropertyList = new ArrayList<> ();
    //filtrowanie daty
    FilteredList<OrderFx> filteredData = new FilteredList<>(orderFxObservableList, person -> true);
    //---------------------------------------END FILTER-------------------------------------

    public void initialize() {

        /* druga metoda uzupełnia UserFx, do pobrania id
        try {
            setLoginUser();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */

        this.checkBox_today.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {

                filteredData.setPredicate (person -> isDayToday(person));
                this.tabViewWetReport.setItems (filteredData);

            } else {
                this.tabViewWetReport.setItems (orderFxObservableList);

            }
        });


        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        String text = date.format(formatter);

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!UZUPEŁNIANIE TABELI V2!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //modelFx/ReportModel

        loadTable ();

        //END

        //FILTER INITIALIZE---------------------------------------------------------------------------------------------

        //metoda 1 STARA
        //this.bazaComboBox.setItems (this.getBaseList ( ));
        //metoda 2 NOWA z użyciem baserFxObservableList
        //wypełnienie CB danymi z baseFxObservableList -> wypełnienie tej listy jest w modelFx/ReportModel

        //wypełnianie ComboBox
        this.bazaComboBox.setItems (this.reportModel.getBaseFxObservableList ( ));
        this.cmbBoxFilterProduct.setItems (this.reportModel.getGoodsPropertyObservableList ());
        this.cmbBoxFilterUsers.setItems(this.reportModel.getUserFxObservableList ());

        //bindowanie javafx #48 6:20
        //powiązanie z vartością jako została obecnie wybrana FXProperty przechowują
        this.reportModel.baseFxObjectProperty ( ).bind(this.bazaComboBox.valueProperty ());
        this.reportModel.goodsPropertyObjectPropertyProperty ().bind(this.cmbBoxFilterProduct.valueProperty ());
        this.reportModel.userFxObjectPropertyProperty ().bind(this.cmbBoxFilterUsers.valueProperty ());

        //END FILTER INITIALIZE-----------------------------------------------------------------------------------------


        //UZUPEŁNIANIE COMBOBOX

            // Spr.init !!!!!!!!!!!!!!!!!!! czy tu potrzebny
           //this.init ( );
            // uzupełnienie listy może być przez List z init() lub ObservableList
            this.cmbBoxBase.setItems (this.getBaseList ( ));

        //END COMBOBOX

        //BINDOWANIE FORMULARZ TAB1 - ZAKŁADKA WPROWADZANIE DANYCH------------------------------------------------------
        this.orderModel = new OrderModel ( );
        try {
            this.orderModel.init ( );
        } catch (ApplicationException e) {
            e.printStackTrace ( );
        }

        //userid---------------------

        this.lblUser.setText (UserSingleton.getInstance ().log_in);
        this.lblUserID.setText (UserSingleton.getInstance ().id.toString ());

        UserSingleton us = UserSingleton.getInstance();
        IntegerProperty intProperty = new SimpleIntegerProperty(us.id);

        UserFx userFx = new UserFx ();
        userFx.setId (us.id);
        userFx.setLogin(us.log_in);
        userFx.setName(us.name);
        userFx.setSurname(us.surname);
        userFx.setPassword(us.password);
        userFx.setDepartment(us.department_lg);

        this.orderModel.getOrderFxObjectProperty ().getUserFxCreate ().idProperty ().bind (intProperty);
        this.orderModel.getOrderFxObjectProperty ().getUserFxEdit ().idProperty ().bind(intProperty);

/*
        this.orderModel.getOrderFxObjectProperty ().getUserFxCreate ().idProperty ().bind (userFxObservableList.get
              (0).idProperty());
*/
        //end userid-----------------

        this.wetOrderedQuantity.textProperty ( ).bindBidirectional (this.orderModel.getOrderFxObjectProperty ( )
                .order_quantityProperty ( ), new NumberStringConverter ( ));

        //this.lblUserID.textProperty ().bindBidirectional (this.orderModel.getOrderFxObjectProperty ()
          //      .userFxCreateProperty ());


     //   this.orderModel.getOrderFxObjectProperty ().userFxCreateProperty ().bind ();
        //this.cmbBoxBase.getSelectionModel ().select (1);
        this.cmbBoxBase.setItems (this.orderModel.getBaseFxObservableList ( ));
        this.orderModel.getOrderFxObjectProperty ( ).cmbBoxBaseFXProperty ( ).bind (this.cmbBoxBase.valueProperty ( ));

        /*
        Label loadLabel = new Label("brak");
        this.cmboBoxStatusStart.setPlaceholder(loadLabel);
        this.orderModel.getOrderFxObjectProperty().cmbBoxProductFXProperty().bind();
        this.orderModel.getOrderFxObjectProperty().cmbBoxProductFXProperty().addListener(cmboBoxStatusStart.setItems());
*/

        this.cmboBoxStatusStart.setItems(this.orderModel.getLabFxObservableList());
        cmboBoxStatusStart.getSelectionModel().select(1);
        this.orderModel.getOrderFxObjectProperty().statusFxProperty().bind(this.cmboBoxStatusStart.valueProperty());


        this.cmbWetProduct.setItems (this.orderModel.getGoodsPropertyObservableList());
        this.orderModel.getOrderFxObjectProperty().goodsPropertyProperty().bind(this.cmbWetProduct.valueProperty());


        DateTimeFormatter formatterr = DateTimeFormatter.ISO_DATE_TIME;
        this.create_date.textProperty().bindBidirectional(this.orderModel.getOrderFxObjectProperty().create_dateProperty(), new
                //LocalDateStringConverter(formatterr,formatterr));
                LocalDateTimeStringConverter());

        this.wetOrderPacket.textProperty ( ).bindBidirectional (this.orderModel.getOrderFxObjectProperty ( ).packProperty ( ), new
                NumberStringConverter ( ));
        this.wetOrderedQuantity.textProperty ( ).bindBidirectional (this.orderModel.getOrderFxObjectProperty ( )
                .order_quantityProperty ( ), new NumberStringConverter ( ));

        // Podczas składania zamówienia nie wiadomo ile będzie zrealizowane
        // this.wedMadeQuantity.textProperty ( ).bindBidirectional (this.orderModel.getOrderFxObjectProperty ( )
        //      .order_realizeProperty ( ), new NumberStringConverter ( ));

        this.orderModel.getOrderFxObjectProperty ( ).commentProperty ( ).bind (this.wetOrderComment.textProperty ( ));
        this.orderModel.getOrderFxObjectProperty ( ).compInfoProperty ( ).bind (this.wetOrderedCompInfo.textProperty ( ));
        //this.orderModel.getOrderFxObjectProperty().statusFxProperty().bind()(this.tfDefaultValueLab.textProperty());


        //END BINDOWANIE-----------------------------------------------------------------------------------------------

/*

        //TAB PRZEGLADANIE ZAMÓWIEŃ Z UŻYCIEM MODEL

        this.orderModel = new OrderModel ();
        try {
            this.orderModel.initTable ();
        } catch (ApplicationException e) {
            e.printStackTrace ( );
        }

        this.tabViewWetReport.setItems (this.orderModel.getBaseFxObservableList ());
        this.colPack.setCellValueFactory (cellData->cellData.getValue().packProperty ().asObject ());
        this.colComment.setCellValueFactory (callData->callData.getValue ().commentProperty ());

  */

// DELETE--------------------------------------------------------------------------------------------------------------
//#49
        this.colWETPRODUCT_DELETE.setCellFactory (param -> new TableCell<OrderFx, OrderFx> ( ) {
            Button button = createButton ("/img/delete.png");

            @Override
            protected void updateItem(OrderFx item, boolean empty) {
                super.updateItem (item, empty);

                // kasowanie grfiki jeśli komórka pusta #49 12:00
                if (empty) {
                    setGraphic (null);
                    return;
                }

                //Gdy komórka ma wartości wstaw przycisk w przeciwynym razie nie dodawaj-------------------------------
                if (!empty) {
                    setGraphic (button);

                    button.setOnAction (event -> {
                        try {
                            reportModel.deleteReport (item);

                        } catch (ApplicationException e) {
                            e.printStackTrace ( );
                        }
                    });
                }
                //End wstawianie przycisku w tabele -------------------------------------------------------------------


            }
        });

// END DELETE----------------------------------------------------------------------------------------------------------

// EDIT----------------------------------------------------------------------------------------------------------------
//#50
        this.colWETPRODUCTLABEDIT.setCellFactory (param -> new TableCell<OrderFx, OrderFx> ( ) {
            Button button = createButton ("/img/edit.png");

            @Override
            protected void updateItem(OrderFx item, boolean empty) {
                super.updateItem (item, empty);

                // kasowanie grfiki jeśli komórka pusta #49 12:00
                if (empty) {
                    setGraphic (null);
                    return;
                }

                //Gdy komórka ma wartości wstaw przycisk w przeciwynym razie nie dodawaj-------------------------------
                if (!empty) {
                    setGraphic (button);

                    button.setOnAction (event -> {
                    FXMLLoader loader = FxmlUtils.getLoader ("/fxml/lab.fxml");
                    Scene scene = null;

                    try {
                            scene = new Scene (loader.load ());
                        } catch (IOException e) {
                            e.printStackTrace ( );
                        }

                        LabController controller = loader.getController ();
                        controller.getLabModel ().setLabFxObjectProperty (item);
                        controller.bindings ();
                       // controller.getOrderModel ().setOrderFxObjectProperty (item);
                        //controller.bindings ();

                        Stage stage = new Stage ();
                        stage.setScene (scene);
                        stage.initModality (Modality.APPLICATION_MODAL);
                        stage.showAndWait ();

                    });
                }
                //End wstawianie przycisku w tabele -------------------------------------------------------------------


            }
        });

// END EDIT------------------------------------------------------------------------------------------------------------

        // EDIT REPORT2
        // ----------------------------------------------------------------------------------------------------------------
//#50
        this.colWETPRODUCTLABEDIT2.setCellFactory (param -> new TableCell<OrderFx, OrderFx> ( ) {
            Button button = createButton ("/img/edit_report._32G.png");

            @Override
            protected void updateItem(OrderFx item, boolean empty) {
                super.updateItem (item, empty);

                // kasowanie grfiki jeśli komórka pusta #49 12:00
                if (empty) {
                    setGraphic (null);
                    return;
                }

                //Gdy komórka ma wartości wstaw przycisk w przeciwynym razie nie dodawaj-------------------------------
                if (!empty) {
                    setGraphic (button);

                    button.setOnAction (event -> {
                        FXMLLoader loader = FxmlUtils.getLoader ("/fxml/wetReportEdit2.fxml");
                        Scene scene = null;

                        try {
                            scene = new Scene (loader.load ());
                        } catch (IOException e) {
                            e.printStackTrace ( );
                        }


                        WetReportEdit2Controller reportEdit2Controller = loader.getController ();
                        reportEdit2Controller.getWetReportEdit2Model ().setEdit2OrderFxObjectProperty (item);
                        reportEdit2Controller.bindings(); //!!! drugie bindowanie z vartoscią = i

                        Stage stage = new Stage ();
                        stage.setScene (scene);
                        stage.initModality (Modality.APPLICATION_MODAL);
                        stage.showAndWait ();
                        loadTable ();

                    });
                }
                //End wstawianie przycisku w tabele -------------------------------------------------------------------


            }
        });

// END EDIT------------------------------------------------------------------------------------------------------------

        // EDIT REPORT
        // ----------------------------------------------------------------------------------------------------------------
//#50
        this.colWET_PRODUCT_EDIT.setCellFactory (param -> new TableCell<OrderFx, OrderFx> ( ) {
            Button button = createButton ("/img/edit_report_32B.png");

            @Override
            protected void updateItem(OrderFx item, boolean empty) {
                super.updateItem (item, empty);

                // kasowanie grfiki jeśli komórka pusta #49 12:00
                if (empty) {
                    setGraphic (null);
                    return;
                }

                //Gdy komórka ma wartości wstaw przycisk w przeciwynym razie nie dodawaj-------------------------------
                if (!empty) {
                    setGraphic (button);

                    button.setOnAction (event -> {
                        FXMLLoader loader = FxmlUtils.getLoader ("/fxml/wetReportEdit.fxml");
                        Scene scene = null;

                        try {
                            scene = new Scene (loader.load ());
                        } catch (IOException e) {
                            e.printStackTrace ( );
                        }


                        WetReportEditController reportEditController = loader.getController ();
                        reportEditController.getWetReportEditModel ().setEditOrderFxObjectProperty (item);
                        reportEditController.bindings(); //!!! drugie bindowanie z vartoscią = i

                        Stage stage = new Stage ();
                        stage.setScene (scene);
                        stage.initModality (Modality.APPLICATION_MODAL);
                        stage.showAndWait ();
                        loadTable ();

                    });
                }
                //End wstawianie przycisku w tabele -------------------------------------------------------------------


            }
        });

// END EDIT------------------------------------------------------------------------------------------------------------



    }
/*
    private boolean dateBetween(Person person) {
        LocalDate date1 = this.date1.getValue();
        LocalDate date2 = this.date2.getValue();
        boolean isAfter = person.getBirthday().toLocalDate().isAfter(date1) || person.getBirthday().toLocalDate().isEqual(date1);
        boolean isBefore = person.getBirthday().toLocalDate().isBefore(date2)|| person.getBirthday().toLocalDate().isEqual(date2);
        return isAfter && isBefore;
    }
  */

    private boolean isDayToday(OrderFx person) {
        return person.getCreate_date ().toLocalDate().isEqual(LocalDate.now());
    }

    public void loadTable(){
        this.reportModel = new ReportModel ( );
        try {
            this.reportModel.initnew ( );
        } catch (ApplicationException e) {
            e.printStackTrace ( );
        }

        //uzp. v2 bindowanie


        this.tableViewWetReport.setItems (this.reportModel.getOrderFxObservableList ( ));
        this.ColIDORDER.setCellValueFactory (cellData -> cellData.getValue ( ).idProperty ( ).asObject ( ));
        this.colIDWETPRODUCTFOREIGN.setCellValueFactory (cellData -> cellData.getValue ( ).goodsPropertyProperty());
        this.colWETPRODUCTEXTINFO.setCellValueFactory (cellData -> cellData.getValue ( ).compInfoProperty ( ));
        this.colIDWETBASEFOREIGN.setCellValueFactory (cellData -> cellData.getValue ( ).baseFxProperty ( ));
        this.colPACK.setCellValueFactory (cellData -> cellData.getValue ( ).packProperty ( ).asObject ( ));
        this.colORDER_QUANITY.setCellValueFactory (cellData -> cellData.getValue ( ).order_quantityProperty ( ).asObject ( ));
        this.colORDER_REALIZE.setCellValueFactory (cellData -> cellData.getValue ( ).order_realizeProperty ( ).asObject ( ));
        this.colWETPRODUCTLABEDIT2.setCellValueFactory (cellData->new SimpleObjectProperty<> (cellData.getValue ()));
        this.colCOMMENT.setCellValueFactory (param -> param.getValue ( ).commentProperty ( ));
        this.colIDUSERFOREIGNCreate.setCellValueFactory (cellData ->cellData.getValue ().userFxCreateProperty ());
        this.colIDUSERFOREIGNEdit.setCellValueFactory (cellData->cellData.getValue ().userFxEditProperty ());
        this.colCREATE_DATE_REPORT.setCellValueFactory (cellData -> cellData.getValue ( ).create_dateProperty ( ));
        this.colEDITION_DATE_REPORT.setCellValueFactory (cellData -> cellData.getValue ( ).edit_dateProperty ( ));
        this.colWETPRODUCTLABCOMMENT.setCellValueFactory (cellData -> cellData.getValue ( ).labcommentProperty ( ));
        this.colWET_PRODUCT_EDIT.setCellValueFactory (cellData->new SimpleObjectProperty<> (cellData.getValue ()));
        this.colWETPRODUCTLABEDIT.setCellValueFactory (cellData -> new SimpleObjectProperty<> (cellData.getValue ( )));
        this.colWETPRODUCT_DELETE.setCellValueFactory (cellData -> new SimpleObjectProperty<> (cellData.getValue ( )));
        this.colLAB_STATUS.setCellValueFactory(cellData->cellData.getValue().statusFxProperty());



    }

    private Button createButton(String path) {
        Button button = new Button ( );
        Image image = new Image (this.getClass ( ).getResource (path).toString ( ));
        ImageView imageView = new ImageView (image);
        button.setGraphic (imageView);
        return button;
    }

    public void onEditC1(TableColumn.CellEditEvent<OrderFx, Integer> orderFxIntegerCellEditEvent) {
        orderModel.getOrderFxObjectPropertyEdit ( ).setPack (orderFxIntegerCellEditEvent.getNewValue ( ));
        updateInDataBaseEdit ( );
    }

    public void onEditC2(TableColumn.CellEditEvent<OrderFx, String> orderFxStringCellEditEvent) {
        orderModel.getOrderFxObjectPropertyEdit ( ).setComment (orderFxStringCellEditEvent.getNewValue ( ));
        updateInDataBaseEdit ( );
    }

    private void updateInDataBaseEdit(){orderModel.saveReportInDataBaseEdit ( );}

    //FILTROWANIE-------------------------------------------------------------------------------------------------------

    public void init() throws ApplicationException {  //użyty do wypełniania w produkty mokra usun baza


        BaseDao baseDao = new BaseDao ( );
        this.baseList.clear ( );
        List<TabWetBase> bases = baseDao.queryForAll (TabWetBase.class);
        bases.forEach (c -> {
            BaseFx baseFx = new BaseFx ( );
            baseFx.setId (c.getIdWetBase ( ));
            baseFx.setName (c.getName ( ));
            this.baseList.add (baseFx);
            //#48 3:17 druga metoda Listowania
        });
    }

    //filtrowanie bazy na tabeli javafx#48------------------------------------------------------------------------------
    public void filterOnActionComboBox(){ reportModel.filterReportsList ( );}

    public void clearBaseComboBox(){ this.bazaComboBox.getSelectionModel ( ).clearSelection ( ); }
    public void clearProductComboBox(){ this.cmbBoxFilterProduct.getSelectionModel ( ).clearSelection ( ); }
    public void clearUserComboBox(){ this.cmbBoxFilterUsers.getSelectionModel ().clearSelection ();}

    //END FILTROWANIE--------------------------------------------------------------------------------------------------


    public void onSlectionChangedTabZlecProd() {

        try {
            reportModel.initnew ();
        } catch (ApplicationException e) {
            e.printStackTrace ( );
        }
        /*
        //TABELA
        tabViewWetReport.setItems (getOrderFxObservableList ( ));
        colWETPRODUCTEXTINFO.setCellValueFactory (cellData -> cellData.getValue ( ).compInfoProperty ( ));
        colPack.setCellValueFactory (cellData -> cellData.getValue ( ).packProperty ( ).asObject ( ));
        colComment.setCellValueFactory (cellData -> cellData.getValue ( ).commentProperty ( ));
        ///Aktywowanie komórek tabeli
        colWETPRODUCTEXTINFO.setCellFactory ((TextFieldTableCell.forTableColumn ( )));
        colComment.setCellFactory ((TextFieldTableCell.forTableColumn ( )));
        colPack.setCellFactory (TextFieldTableCell.forTableColumn (new IntegerStringConverter ( )));
        this.tabViewWetReport.getSelectionModel ( ).selectedItemProperty ( ).addListener (((observable, oldValue,
                                                                                            newValue) -> {
            orderModel.setOrderFxObjectPropertyEdit (newValue);
        }));

        //Sprawdzanie czy tabela nie jest pusta aby przekazać wartosci true/false do deleteMenuItem
        deleteMenuItem.disableProperty ( ).bind (tabViewWetReport.getSelectionModel ( ).selectedItemProperty ( ).isNull ( ));

        tabViewWetReport.getItems ( ).clear ( );
        tabLoad ( );

        //END TABELA
        */
        }


    public void tabLoad() {
        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource (URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        try {
            Dao<TabWetReport, Integer> tabWetReports = DaoManager.createDao (connectionSource, TabWetReport.class);
            List<TabWetReport> tabWetReportList = tabWetReports.queryForAll ( );
            tabWetReportList.forEach (c -> {
                OrderFx orderFx = new OrderFx ( );
                orderFx.setId (c.getIdWetReport ( ));
                orderFx.setCompInfo (c.getCompInfo ( ));
                orderFx.setPack (c.getPack ( ));
                orderFx.setComment (c.getComment ( ));
                orderFxObservableList.add (orderFx);

            });
        } catch (SQLException e) {
            e.printStackTrace ( );
        }

        try {
            connectionSource.close ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }

    }


    public void onActAddWetOrder() throws ApplicationException {
        //  int zmienna = Integer.parseInt (WetOrderPacket.getText ());
        //  WetOrderPacket.setText (String.valueOf (zmienna));
        orderModel.saveReportInDataBase ( );

    }

    public void onActDeleteOrder() {
        orderModel.deleteOrderInDataBase ( );
        tabViewWetReport.getItems ( ).clear ( );
        tabLoad ( );
    }




    //--------------------------------------------------------------------------------------------------------------

    //załadowanie formatki lab.fxml #27 11:00
    public void setCenterLab(String fxmlPath2) {
        FXMLLoader loader = new FXMLLoader (this.getClass ( ).getResource (fxmlPath2));
        BorderPane borderPane = null;
        try {
            borderPane = loader.load ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }

        LabBorderPane.setCenter (borderPane);
    }
    public void setLoginUser() throws SQLException {

        UserSingleton us = UserSingleton.getInstance();
        ConnectionSource connectionSource = new JdbcConnectionSource(URL, USER, PASSWORD);
        Dao<TabUsers, Integer> userDao = DaoManager.createDao(connectionSource, TabUsers.class);

        QueryBuilder<TabUsers, Integer> queryBuilder = userDao.queryBuilder();
        queryBuilder.where().eq("login", us.log_in);
        PreparedQuery<TabUsers> preparedQuery = queryBuilder.prepare(); // możliwość zapisanie w krótszej formie
        // 3.1 QueryBuilerBasic

        List<TabUsers> userFxList = userDao.query(preparedQuery);
        userFxList.forEach(u -> {
            UserFx userFx = new UserFx ();

            userFx.setLogin (u.getLogin ());
            System.out.println("Login : " + us.log_in);

            userFx.setId (u.getId_user ());
            us.id = u.getId_user ();
            System.out.println("ID: "+ us.id);

            userFx.setName(u.getName());
            us.name = u.getName();
            System.out.println("Name: " + us.name);

            userFx.setSurname(u.getSurname());
            us.surname = u.getSurname();
            System.out.println("Surname: " + us.surname);

            userFx.setPassword(u.getPassword());
            us.password = u.getPassword();
            System.out.println("Password:" + us.password);

            userFx.setDepartment (u.getDepartment ());
            us.department_lg = u.getDepartment ();
            System.out.println("Dział: " + us.department_lg);

            this.userFxObservableList.add (userFx);
        });
    }


    //wczytywanie lab.fxml
    public void OnSelectionChangedTab3(Event event) {
        setCenterLab ("/fxml/lab.fxml");
    }

    public ComboBox<BaseFx> getCmbBoxBase() {
        return cmbBoxBase;
    }

    public void setCmbBoxBase(ComboBox<BaseFx> cmbBoxBase) {
        this.cmbBoxBase = cmbBoxBase;
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public void setConnectionSource(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    public ObservableList<BaseFx> getBaseList() {
        return baseList;
    }

    public void setBaseList(ObservableList<BaseFx> baseList) {
        this.baseList = baseList;
    }


    public ObservableList<OrderFx> getOrderFxObservableList() {
        return orderFxObservableList;
    }

    public void setOrderFxObservableList(ObservableList<OrderFx> orderFxObservableList) {
        this.orderFxObservableList = orderFxObservableList;
    }


    public ObservableList<BaseFx> getBaseFxObservableList() {
        return baseFxObservableList;
    }

    public void setBaseFxObservableList(ObservableList<BaseFx> baseFxObservableList) {
        this.baseFxObservableList = baseFxObservableList;
    }


    //SET & GET WHO IS LOG IN ---------------------------------------------------------


    public ObservableList<UserFx> getUserFxObservableList() {
        return userFxObservableList;
    }

    public void setUserFxObservableList(ObservableList<UserFx> userFxObservableList) {
        this.userFxObservableList = userFxObservableList;
    }

}



/*
        this.orderModel.orderFxObjectPropertyProperty ().get ().commentProperty ().bind (this.WetOrderComment.textProperty ());


        this.orderModel.getOrderFxObjectProperty ().packProperty ().bind (this.wetOrderPacket.valueProperty());
        this.orderModel.getOrderFxObjectProperty ().order_quantityProperty ().bind(this.wetOrderedQuantity.valueProperty());
        this.orderModel.getOrderFxObjectProperty ().order_realizeProperty ().bind(this.wedMadeQuantity.valueProperty());
*/