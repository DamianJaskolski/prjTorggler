package torggler.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import torggler.ApplicationException;
import torggler.dao.BaseDao;
import torggler.dao.GoodsDao;
import torggler.dao.ReportDao;
import torggler.dao.StatusDao;
import torggler.tables.Status;
import torggler.tables.TabWetBase;
import torggler.tables.TabWetGoods;
import torggler.tables.TabWetReport;
import torggler.utils.converters.ConverterBase;
import torggler.utils.converters.ConverterReport;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class WetReportEditModel {
    private ObjectProperty<OrderFx> EditOrderFxObjectProperty = new SimpleObjectProperty<> (new OrderFx ());
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

        //  ----end pobieranie danych niezmienialnych ---------------------


        ReportDao reportDao = new ReportDao ();
        reportDao.creatOrUpdate (tabWetReport);



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
