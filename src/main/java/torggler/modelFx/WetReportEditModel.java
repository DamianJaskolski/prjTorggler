package torggler.modelFx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import torggler.ApplicationException;
import torggler.UserSingleton;
import torggler.controllers.WetReportController;
import torggler.dao.*;
import torggler.tables.*;
import torggler.utils.converters.ConverterBase;
import torggler.utils.converters.ConverterReport;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class WetReportEditModel {
    private ObjectProperty<OrderFx> EditOrderFxObjectProperty = new SimpleObjectProperty<> (new OrderFx ());
    private ObservableList<BaseFx> baseFxObservableList  = FXCollections.observableArrayList();

    public WetReportController wetReportController;

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



        TabWetReport tabWetReport = ConverterReport.convertToOrder (this.getEditOrderFxObjectProperty ());

       // tabWetReport.setEditionDateReport (this.editOrderFxObjectPropertyProperty ().get ().getEdit_date ());

        BaseDao baseDao = new BaseDao();
        TabWetBase tabWetBase = baseDao.findById(TabWetBase.class, this.getEditOrderFxObjectProperty().getBaseFx().getId());
        tabWetReport.setTabWetBaseForegin(tabWetBase);

        // ---------------nie zmieniam ale musi pobrać dane do zapisu -------------------

        StatusDao statusDao = new StatusDao();
        Status status = statusDao.findById(Status.class, this.getEditOrderFxObjectProperty().getStatusFx().getIdStatus());
        tabWetReport.setStatusLabForegin(status);

        GoodsDao goodsDao = new GoodsDao ();
        TabWetGoods tabWetGoods = goodsDao.findById (TabWetGoods.class, this.getEditOrderFxObjectProperty ()
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



        getEditOrderFxObjectProperty ().getUserFxEdit ().idProperty ().bind(intProperty);

        //  tabWetReport.setTabUsersEditForegin (this.EditOrderFxObjectProperty.get ().getUserFxEdit ());

        UserDao userDao = new UserDao();
        TabUsers tabUsers = userDao.findById (TabUsers.class, this.getEditOrderFxObjectProperty().getUserFxEdit ()
                .getId());
        tabWetReport.setTabUsersEditForegin (tabUsers);


        TabUsers tabUsers2 = userDao.findById (TabUsers.class, this.getEditOrderFxObjectProperty().getUserFxCreate ()
                .getId());

        tabWetReport.setTabUsersForegin (tabUsers2);


        //  ----end pobieranie danych niezmienialnych ---------------------


        ReportDao reportDao = new ReportDao ();
        reportDao.creatOrUpdate (tabWetReport);


      // Filt sprawdzić !!!!!!! wetReportController.loadTable ();
    }


    public OrderFx getEditOrderFxObjectProperty() {
        return EditOrderFxObjectProperty.get ( );
    }

    public ObjectProperty<OrderFx> editOrderFxObjectPropertyProperty() {
        return EditOrderFxObjectProperty;
    }

    public void setEditOrderFxObjectProperty(OrderFx editOrderFxObjectProperty) {
        this.EditOrderFxObjectProperty.set (editOrderFxObjectProperty);
    }

    public ObservableList<BaseFx> getBaseFxObservableList() {
        return baseFxObservableList;
    }

    public void setBaseFxObservableList(ObservableList<BaseFx> baseFxObservableList) {
        this.baseFxObservableList = baseFxObservableList;
    }
}
