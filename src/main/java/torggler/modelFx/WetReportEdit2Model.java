package torggler.modelFx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import torggler.ApplicationException;
import torggler.UserSingleton;
import torggler.dao.*;
import torggler.tables.*;
import torggler.utils.converters.ConverterBase;
import torggler.utils.converters.ConverterReport;

import java.util.List;

public class WetReportEdit2Model {

    private ObjectProperty<OrderFx> Edit2OrderFxObjectProperty = new SimpleObjectProperty<> (new OrderFx ());
    private ObservableList<BaseFx> baseFxObservableList  = FXCollections.observableArrayList();

    public void initBaseList()throws ApplicationException{

        BaseDao baseDao =  new BaseDao();
        List<TabWetBase> tabWetBasesList = baseDao.queryForAll(TabWetBase.class);
        this.baseFxObservableList.clear();
        tabWetBasesList.forEach(base->{
            BaseFx baseFx = ConverterBase.convertToBaseFx(base);
            this.baseFxObservableList.add(baseFx);
        });
    }

    public void saveChangesInDataBase() throws ApplicationException {



        TabWetReport tabWetReport = ConverterReport.convertToOrder (this.getEdit2OrderFxObjectProperty ());

       // tabWetReport.setEditionDateReport (this.editOrderFxObjectPropertyProperty ().get ().getEdit_date ());

        BaseDao baseDao = new BaseDao();
        TabWetBase tabWetBase = baseDao.findById(TabWetBase.class, this.getEdit2OrderFxObjectProperty().getBaseFx()
                .getId());
        tabWetReport.setTabWetBaseForegin(tabWetBase);

        // ---------------nie zmieniam ale musi pobrać dane do zapisu -------------------

        StatusDao statusDao = new StatusDao();
        Status status = statusDao.findById(Status.class, this.getEdit2OrderFxObjectProperty().getStatusFx()
                .getIdStatus());
        tabWetReport.setStatusLabForegin(status);

        GoodsDao goodsDao = new GoodsDao ();
        TabWetGoods tabWetGoods = goodsDao.findById (TabWetGoods.class, this.getEdit2OrderFxObjectProperty ()
                .getGoodsProperty ().getIdWetGoodsProperty ());
        tabWetReport.setTabWetGoodsForegin (tabWetGoods);



        //  ----end pobieranie danych niezmienialnych ---------------------

//users
        UserSingleton us = UserSingleton.getInstance();
        IntegerProperty intProperty = new SimpleIntegerProperty (us.id);

        UserFx userFx = new UserFx ();
        userFx.setId (us.id);
        userFx.setLogin(us.log_in);
        userFx.setName(us.name);
        userFx.setSurname(us.surname);
        userFx.setPassword(us.password);
        userFx.setDepartment(us.department_lg);



        getEdit2OrderFxObjectProperty ().getUserFxEdit ().idProperty ().bind(intProperty);

        //  tabWetReport.setTabUsersEditForegin (this.EditOrderFxObjectProperty.get ().getUserFxEdit ());

        UserDao userDao = new UserDao();
        TabUsers tabUsers = userDao.findById (TabUsers.class, this.getEdit2OrderFxObjectProperty().getUserFxEdit ()
                .getId());
        tabWetReport.setTabUsersEditForegin (tabUsers);


        TabUsers tabUsers2 = userDao.findById (TabUsers.class, this.getEdit2OrderFxObjectProperty().getUserFxCreate ()
                .getId());

        //user end

        tabWetReport.setTabUsersForegin (tabUsers2);
        ReportDao reportDao = new ReportDao ();
        reportDao.creatOrUpdate (tabWetReport);



    }

    public OrderFx getEdit2OrderFxObjectProperty() {
        return Edit2OrderFxObjectProperty.get ( );
    }

    public ObjectProperty<OrderFx> edit2OrderFxObjectPropertyProperty() {
        return Edit2OrderFxObjectProperty;
    }

    public void setEdit2OrderFxObjectProperty(OrderFx edit2OrderFxObjectProperty) {
        this.Edit2OrderFxObjectProperty.set (edit2OrderFxObjectProperty);
    }

    public ObservableList<BaseFx> getBaseFxObservableList() {
        return baseFxObservableList;
    }

    public void setBaseFxObservableList(ObservableList<BaseFx> baseFxObservableList) {
        this.baseFxObservableList = baseFxObservableList;
    }
}
