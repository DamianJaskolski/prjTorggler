package torggler.modelFx;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import torggler.ApplicationException;
import torggler.DbManager;
import torggler.DialogsUtils;
import torggler.UserSingleton;
import torggler.controllers.WetReportController;
import torggler.dao.*;
import torggler.tables.*;
import torggler.utils.converters.*;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class OrderModel {

    public static final String URL = "jdbc:postgresql://localhost:5432/dbTorggler";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dagdam";

    private ObjectProperty<OrderFx> orderFxObjectProperty = new SimpleObjectProperty<> (new OrderFx ( ));
    public ObjectProperty<OrderFx> orderFxObjectPropertyEdit = new SimpleObjectProperty<> (new OrderFx ());

    private ObservableList<BaseFx> baseFxObservableList = FXCollections.observableArrayList();
    private ObservableList<GoodsProperty> goodsPropertyObservableList = FXCollections.observableArrayList ();
    private ObservableList<OrderFx> orderFxObservableList = FXCollections.observableArrayList ();
    private ObservableList<LabFx> labFxObservableList = FXCollections.observableArrayList();
    private ObservableList<UserFx> userFxObservableList = FXCollections.observableArrayList ();



    private WetReportController wetReportController;

public void initTable() throws ApplicationException {
    //ReportDao reportDao = new ReportDao (DbManager.getConnectionSource ());

    ConnectionSource connectionSource = null;
    try {
        connectionSource = new JdbcConnectionSource (URL, USER, PASSWORD);
    } catch (SQLException e) {
        e.printStackTrace ( );
    }
    Dao<TabWetReport, Integer> tabWetReportIntegerDao = null;
    try {
        tabWetReportIntegerDao = DaoManager.createDao (connectionSource, TabWetReport.class);
    } catch (SQLException e) {
        e.printStackTrace ( );
    }

    List<TabWetReport> reports = null;
    try {
        reports = tabWetReportIntegerDao.queryForAll ();
    } catch (SQLException e) {
        e.printStackTrace ( );
    }
    this.orderFxObservableList.clear ();

        reports.forEach (order->{
            OrderFx orderFx = ConverterReport.convertToOrderFX (order);

        /* Wszystko przeniesione do converters->ConverterReport
        OrderFx orderFx = new OrderFx ();
        orderFx.setId (order.getIdWetProduct ());
       // orderFx.setCmbBoxBaseFX (order.getTabWetBaseForegin (ba));
        orderFx.setPack (order.getPack ());
        orderFx.setOrder_quantity (order.getOrder_quantity () );
        orderFx.setOrder_realize (order.getOrder_realize () );
        orderFx.setComment (order.getComment ());
        */

            this.orderFxObservableList.add (orderFx);
        });



}

    public void init() throws ApplicationException {
        initBaseList ();
        initProduct ();
        initStatus();
        initUser ();
    }

    private void initBaseList() throws ApplicationException {
        BaseDao baseDao = new BaseDao();
        this.baseFxObservableList.clear ();
        List<TabWetBase> bases = baseDao.queryForAll(TabWetBase.class);
        bases.forEach(c -> {
            BaseFx baseFx1 = ConverterBase.convertToBaseFx  (c);
            baseFxObservableList.add (baseFx1);

        });
    }

    private void initProduct() throws ApplicationException {

        GoodsDao goodsDao = new GoodsDao();
        this.goodsPropertyObservableList.clear ();
        List<TabWetGoods> goods = goodsDao.queryForAll (TabWetGoods.class);
        goods.forEach (product->{
            GoodsProperty goodsProperty = ConverterGoods.convertToGoodsProperty(product);
            goodsPropertyObservableList.add (goodsProperty);
        });
    }

    private void initUser() throws ApplicationException {
    UserDao userDao = new UserDao ();
    this.userFxObservableList.clear ();
    List<TabUsers> users = userDao.queryForAll (TabUsers.class);
    users.forEach (usr->{
        UserFx userFx = ConverterUser.convertToUserFx (usr);
        userFxObservableList.add(userFx);
    });

    }

    private void initStatus() throws ApplicationException {
    StatusDao statusDao = new StatusDao();
    //this.labFxObservableList.clear();

        List<Status> statuses = statusDao.queryForAll(Status.class);
    statuses.forEach(s->{
        LabFx labFx = ConverterStatus.convertToLabFX(s);
        labFxObservableList.add (labFx);
    });
    }

//ZAPISYWANIE DO BAZY--------------------------------------------------------------------------------------------------
    public void saveReportInDataBase() throws ApplicationException {


        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource (URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        try {
            Dao<TabWetReport, Integer> tabWetReportIntegerDao = DaoManager.createDao (connectionSource, TabWetReport.class);

            // int zmienna = Integer.parseInt (wetReportController.WetOrderPacket.getText ());
            //tabWetReport.setTabWetBaseForegin (this.orderFxObjectProperty.get ().getCmbBoxBaseFX ());

            //TabWetReport tabWetReport = new TabWetReport ( );
            TabWetReport tabWetReport = ConverterReport.convertToOrder (this.getOrderFxObjectProperty ( ));
/*
            ProductDao productDao = new ProductDao ();
            TabWetProduct tabWetProduct = productDao.findById (TabWetProduct.class, this.getOrderFxObjectProperty ()
                    .getCmbBoxProductFX ().getIdProductWET());
            tabWetReport.setTabWetProductForeign (tabWetProduct);
*/
            StatusDao statusDao = new StatusDao();
            Status status =  statusDao.findById(Status.class, this.getOrderFxObjectProperty().getStatusFx().getIdStatus());
            tabWetReport.setStatusLabForegin(status);
/*
            UserDao userDao = new UserDao ();
            TabUsers tabUsers = userDao.findById (TabUsers.class, this.getOrderFxObjectProperty ().getUserFxCreate ()
                    .getId ());
*/

            BaseDao baseDao = new BaseDao ( );
            TabWetBase tabWetBase = baseDao.findById (TabWetBase.class, this.getOrderFxObjectProperty ( ).getCmbBoxBaseFX ( ).getId ( ));
            tabWetReport.setTabWetBaseForegin (tabWetBase);

            GoodsDao goodsDao = new GoodsDao();
            TabWetGoods tabWetGoods = goodsDao.findById(TabWetGoods.class, this.getOrderFxObjectProperty()
                    .getGoodsProperty().getIdWetGoodsProperty());
            tabWetReport.setTabWetGoodsForegin(tabWetGoods);


            UserDao userDao = new UserDao();
            TabUsers tabUsers = userDao.findById(TabUsers.class, this.getOrderFxObjectProperty().getUserFxCreate().getId());
            tabWetReport.setTabUsersForegin(tabUsers);

            TabUsers tabUsers2 = userDao.findById(TabUsers.class, this.getOrderFxObjectProperty().getUserFxEdit ().getId());
            tabWetReport.setTabUsersEditForegin (tabUsers2);

            TabUsers tabLabUser = userDao.findById(TabUsers.class, this.getOrderFxObjectProperty().getUserFxLab().getId());
            tabWetReport.setTabUsersLabForegin (tabLabUser);

            TabUsers tabWhmUsers = userDao.findById(TabUsers.class, this.getOrderFxObjectProperty().getUserFXWhm()
                    .getId());
            tabWetReport.setTabUFWarehouseman(tabWhmUsers);

            Date date =  ConverterDate.convertToDate(this.orderFxObjectProperty.get().getCommissionDate()
                    .atStartOfDay());
            tabWetReport.setCommissionDateReport(date);

            tabWetReport.setCompInfo (this.orderFxObjectProperty.get ().getCompInfo ());
            tabWetReport.setPack (this.orderFxObjectProperty.get ( ).getPack ( ));
            tabWetReport.setOrder_quantity (this.orderFxObjectProperty.get ( ).getOrder_quantity ( ));
            tabWetReport.setOrder_realize (this.orderFxObjectProperty.get ( ).getOrder_realize ( ));
            tabWetReport.setComment (this.orderFxObjectProperty.get ( ).getComment ( ));

            tabWetReportIntegerDao.createOrUpdate (tabWetReport);

            try {
                connectionSource.close ( );
            } catch (IOException e) {
                e.printStackTrace ( );
            }
        } catch (SQLException e) {
            e.printStackTrace ( );
        }

        DialogsUtils.dialogSaveApplication();

    }


    //END ZAPISYWANIE DO BAZY----------------------------------------------------------------------------------------------



    public void deleteOrderInDataBase(){
        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource (URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        try {
            Dao<TabWetReport, Integer> tabWetReportDao= DaoManager.createDao (connectionSource,
                    TabWetReport.class);

            tabWetReportDao.deleteById (this.getOrderFxObjectPropertyEdit ().getId ());


        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        try {
            connectionSource.close ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }

    }

    public void saveReportInDataBaseEdit()  {


        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource (URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        try {
            Dao<TabWetReport, Integer> tabWetReportIntegerDao = DaoManager.createDao (connectionSource, TabWetReport.class);

            TabWetReport tabWetReport = ConverterReport.convertToOrder (getOrderFxObjectPropertyEdit ());

           // BaseDao baseDao = new BaseDao ();
           // TabWetBase tabWetBase = baseDao.findById (TabWetBase .class, wetReportController
                 //   .getOrderFxObjectPropertyEdit().getCmbBoxBaseFX ().getId ());

            //tabWetReport.setTabWetBaseForegin (tabWetBase);
            tabWetReport.setPack (orderFxObjectPropertyEdit.get ().getPack ());
           // tabWetReport.setOrder_quantity (wetReportController.orderFxObjectPropertyEdit.get ().getOrder_quantity
            // ());
           // tabWetReport.setOrder_realize (wetReportController.orderFxObjectPropertyEdit.get ().getOrder_realize ());
            tabWetReport.setComment (orderFxObjectPropertyEdit.get ().getComment ());
            tabWetReportIntegerDao.createOrUpdate (tabWetReport);

            try {
                connectionSource.close ( );
            } catch (IOException e) {
                e.printStackTrace ( );
            }
        } catch (SQLException e) {
            e.printStackTrace ( );
        }



    }





    public OrderFx getOrderFxObjectProperty() {
        return orderFxObjectProperty.get ( );
    }

    public ObjectProperty<OrderFx> orderFxObjectPropertyProperty() {
        return orderFxObjectProperty;
    }

    public void setOrderFxObjectProperty(OrderFx orderFxObjectProperty) {
        this.orderFxObjectProperty.set (orderFxObjectProperty);
    }

    public ObservableList<BaseFx> getBaseFxObservableList() {
        return baseFxObservableList;
    }

    public void setBaseFxObservableList(ObservableList<BaseFx> baseFxObservableList) {
        this.baseFxObservableList = baseFxObservableList;
    }

    public ObservableList<OrderFx> getOrderFxObservableList() {
        return orderFxObservableList;
    }

    public void setOrderFxObservableList(ObservableList<OrderFx> orderFxObservableList) {
        this.orderFxObservableList = orderFxObservableList;
    }

    public OrderFx getOrderFxObjectPropertyEdit() {
        return orderFxObjectPropertyEdit.get ( );
    }

    public ObjectProperty<OrderFx> orderFxObjectPropertyEditProperty() {
        return orderFxObjectPropertyEdit;
    }

    public void setOrderFxObjectPropertyEdit(OrderFx orderFxObjectPropertyEdit) {
        this.orderFxObjectPropertyEdit.set (orderFxObjectPropertyEdit);
    }

    public ObservableList<GoodsProperty> getGoodsPropertyObservableList() {
        return goodsPropertyObservableList;
    }

    public void setGoodsPropertyObservableList(ObservableList<GoodsProperty> goodsPropertyObservableList) {
        this.goodsPropertyObservableList = goodsPropertyObservableList;
    }

    public ObservableList<LabFx> getLabFxObservableList() {
        return labFxObservableList;
    }

    public void setLabFxObservableList(ObservableList<LabFx> labFxObservableList) {
        this.labFxObservableList = labFxObservableList;
    }
}
