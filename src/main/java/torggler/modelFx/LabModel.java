package torggler.modelFx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import torggler.ApplicationException;
import torggler.UserSingleton;
import torggler.dao.*;
import torggler.tables.*;
import torggler.utils.converters.ConverterBase;
import torggler.utils.converters.ConverterGoods;
import torggler.utils.converters.ConverterReport;
import torggler.utils.converters.ConverterStatus;

import java.util.List;

public class LabModel {
//może stworzyć LabFx
    private ObjectProperty<OrderFx> labFxObjectProperty = new SimpleObjectProperty<> (new OrderFx ());
    private ObservableList<BaseFx> baseFxObservableList  = FXCollections.observableArrayList();
    private ObservableList<LabFx> statusFxObservableList = FXCollections.observableArrayList();
    private ObservableList<GoodsProperty> goodsPropertyObservableList = FXCollections.observableArrayList();


    public void initlab() throws ApplicationException {
       // initBaseList();
        initStatusList ();

    }


public void initBaseList()throws ApplicationException{

    BaseDao baseDao =  new BaseDao();
    List<TabWetBase> tabWetBasesList = baseDao.queryForAll(TabWetBase.class);
    this.baseFxObservableList.clear();
    tabWetBasesList.forEach(base->{
        BaseFx baseFx = ConverterBase.convertToBaseFx(base);
        this.baseFxObservableList.add(baseFx);
    });
}

public void iniGoods() throws ApplicationException {

    GoodsDao goodsDao = new GoodsDao ();
    List<TabWetGoods> tabWetGoods = goodsDao.queryForAll (TabWetGoods.class);
    this.goodsPropertyObservableList.clear ();
    tabWetGoods.forEach (goods->{
        GoodsProperty goodsProperty = ConverterGoods.convertToGoodsProperty (goods);
        this.goodsPropertyObservableList.add(goodsProperty);
    });

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


    public void saveLabInDataBase() throws ApplicationException {

        TabWetReport tabWetReport = ConverterReport.convertToOrder (this.getLabFxObjectProperty ());

        BaseDao baseDao = new BaseDao();
        TabWetBase tabWetBase = baseDao.findById(TabWetBase.class, this.getLabFxObjectProperty().getBaseFx().getId());
        tabWetReport.setTabWetBaseForegin(tabWetBase);

        StatusDao statusDao = new StatusDao();
        Status status = statusDao.findById(Status.class, this.getLabFxObjectProperty().getStatusFx().getIdStatus());
        tabWetReport.setStatusLabForegin(status);

        GoodsDao goodsDao = new GoodsDao ();
        TabWetGoods tabWetGoods = goodsDao.findById (TabWetGoods.class, this.getLabFxObjectProperty ()
                .getGoodsProperty ().getIdWetGoodsProperty ());
        tabWetReport.setTabWetGoodsForegin (tabWetGoods);

        UserSingleton us = UserSingleton.getInstance();
        IntegerProperty intProperty = new SimpleIntegerProperty (us.id);

        UserFx userFx = new UserFx ();
        userFx.setId (us.id);
        userFx.setLogin(us.log_in);
        userFx.setName(us.name);
        userFx.setSurname(us.surname);
        userFx.setPassword(us.password);
        userFx.setDepartment(us.department_lg);


        getLabFxObjectProperty ().getUserFxLab().idProperty ().bind(intProperty);

        //  tabWetReport.setTabUsersEditForegin (this.EditOrderFxObjectProperty.get ().getUserFxEdit ());

        UserDao userDao = new UserDao();
        TabUsers tabUsers = userDao.findById (TabUsers.class, this.getLabFxObjectProperty().getUserFxEdit ().getId());
        tabWetReport.setTabUsersEditForegin (tabUsers);


        TabUsers tabUsers2 = userDao.findById (TabUsers.class, this.getLabFxObjectProperty().getUserFxCreate ().getId());
        tabWetReport.setTabUsersForegin (tabUsers2);

        TabUsers tabLabUser = userDao.findById(TabUsers.class, this.getLabFxObjectProperty ().getUserFxLab().getId());
        tabWetReport.setTabUsersLabForegin (tabLabUser);


        TabUsers tabWhmUsers = userDao.findById(TabUsers.class, this.getLabFxObjectProperty().getUserFXWhm()
                .getId());
        tabWetReport.setTabUFWarehouseman(tabWhmUsers);

        tabWetReport.setEditionDateReport (tabWetReport.getEditionDateReport ());  // żeby nie modyfikować bazy


        ReportDao reportDao = new ReportDao ();
        reportDao.creatOrUpdate (tabWetReport);



    }


    public OrderFx getLabFxObjectProperty() {
        return labFxObjectProperty.get ( );
    }

    public ObjectProperty<OrderFx> labFxObjectPropertyProperty() {
        return labFxObjectProperty;
    }

    public void setLabFxObjectProperty(OrderFx labFxObjectProperty) {
        this.labFxObjectProperty.set (labFxObjectProperty);
    }

    public ObservableList<BaseFx> getBaseFxObservableList() {
        return baseFxObservableList;
    }

    public void setBaseFxObservableList(ObservableList<BaseFx> baseFxObservableList) {
        this.baseFxObservableList = baseFxObservableList;
    }

    public ObservableList<LabFx> getStatusFxObservableList() {
        return statusFxObservableList;
    }

    public void setStatusFxObservableList(ObservableList<LabFx> statusFxObservableList) {
        this.statusFxObservableList = statusFxObservableList;
    }

    public ObservableList<GoodsProperty> getGoodsPropertyObservableList() {
        return goodsPropertyObservableList;
    }

    public void setGoodsPropertyObservableList(ObservableList<GoodsProperty> goodsPropertyObservableList) {
        this.goodsPropertyObservableList = goodsPropertyObservableList;
    }
}
