package torggler.modelFx;

import com.j256.ormlite.dao.Dao;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import torggler.ApplicationException;
import torggler.UserSingleton;
import torggler.controllers.WarehousemanController;
import torggler.dao.*;
import torggler.tables.*;
import torggler.utils.converters.ConverterBase;
import torggler.utils.converters.ConverterReport;
import torggler.utils.converters.ConverterStatus;

import java.util.List;

public class WarehousemanModel {
    private ObjectProperty<OrderFx> whmOrderFxObjectProperty = new SimpleObjectProperty<>(new OrderFx ());
    private ObservableList<LabFx> statusFxObservableList = FXCollections.observableArrayList();
    private WarehousemanController warehousemanController;
    public String statusLab;

    public void initialize() throws ApplicationException {
        this.warehousemanController = new WarehousemanController ();
        initStatusList ();
    }

    public void initStatusList() throws ApplicationException {

        StatusDao statusDao = new StatusDao ();
        List<Status> statuses = statusDao.queryForAll(Status.class);
        this.statusFxObservableList.clear ();
        statuses.forEach (status -> {
            LabFx labFx = ConverterStatus.convertToLabFX (status);
            this.statusFxObservableList.add (labFx);
        });
    }

    public void saveChangesInDataBaseWHM() throws ApplicationException {



        TabWetReport tabWetReport = ConverterReport.convertToOrder (this.getWhmOrderFxObjectProperty ());



        BaseDao baseDao = new BaseDao();
        TabWetBase tabWetBase = baseDao.findById(TabWetBase.class, this.getWhmOrderFxObjectProperty ().getBaseFx()
                .getId());
        tabWetReport.setTabWetBaseForegin(tabWetBase);


        StatusDao statusDao = new StatusDao();
        Status status1 = statusDao.findById(Status.class, this.getWhmOrderFxObjectProperty ().getStatusFx()
                .getIdStatus());
        statusLab = String.valueOf(status1);
        tabWetReport.setStatusLabForegin(status1);

        GoodsDao goodsDao = new GoodsDao ();
        TabWetGoods tabWetGoods = goodsDao.findById (TabWetGoods.class, this.getWhmOrderFxObjectProperty ()
                .getGoodsProperty ().getIdWetGoodsProperty ());
        tabWetReport.setTabWetGoodsForegin (tabWetGoods);



        UserDao userDao = new UserDao();


        // tych użytkowników nie zmieniam
        TabUsers tabUsers = userDao.findById (TabUsers.class, this.getWhmOrderFxObjectProperty ().getUserFxEdit ().getId());
        tabWetReport.setTabUsersEditForegin (tabUsers);

        TabUsers tabUsers2 = userDao.findById (TabUsers.class, this.getWhmOrderFxObjectProperty ().getUserFxCreate ().getId());
        tabWetReport.setTabUsersForegin (tabUsers2);

        TabUsers tabLabUser = userDao.findById(TabUsers.class, this.getWhmOrderFxObjectProperty ().getUserFxLab().getId());
        tabWetReport.setTabUsersLabForegin (tabLabUser);

        //WARTOŚCI KTÓRE ULEGAJĄ ZMIANIE

        // Magazynier to nowy użytkowinik
        UserSingleton us = UserSingleton.getInstance();
        IntegerProperty intProperty = new SimpleIntegerProperty (us.id);

        UserFx userFx = new UserFx ();
        userFx.setId (us.id);
        userFx.setLogin(us.log_in);
        userFx.setName(us.name);
        userFx.setSurname(us.surname);
        userFx.setPassword(us.password);
        userFx.setDepartment(us.department_lg);

        getWhmOrderFxObjectProperty ().getUserFXWhm ().idProperty ().bind(intProperty);

        TabUsers tabWhmUsers = userDao.findById(TabUsers.class, this.getWhmOrderFxObjectProperty ().getUserFXWhm()
                .getId());
        tabWetReport.setTabUFWarehouseman(tabWhmUsers);


       // warehousemanController.cmbStatus.setItems(status);
       // String output = warehousemanController.cmbStatus.getSelectionModel().getSelectedItem().toString();
      //  String output = warehousemanController.cmbStatus.getValue ();

       // System.out.println (output );
       // tabWetReport.setStatusWhm (output);

        ReportDao reportDao = new ReportDao ();
        reportDao.creatOrUpdate (tabWetReport);



    }






    //SET & GET
    public OrderFx getWhmOrderFxObjectProperty() { return whmOrderFxObjectProperty.get(); }

    public ObjectProperty<OrderFx> whmOrderFxObjectPropertyProperty() {
        return whmOrderFxObjectProperty;
    }

    public void setWhmOrderFxObjectProperty(OrderFx whmOrderFxObjectProperty) {
        this.whmOrderFxObjectProperty.set(whmOrderFxObjectProperty);

    }

    public ObservableList<LabFx> getStatusFxObservableList() {
        return statusFxObservableList;
    }

}
