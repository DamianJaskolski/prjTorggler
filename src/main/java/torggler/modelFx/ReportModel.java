package torggler.modelFx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import torggler.ApplicationException;
import torggler.dao.BaseDao;
import torggler.dao.GoodsDao;
import torggler.dao.ReportDao;
import torggler.dao.UserDao;
import torggler.tables.TabUsers;
import torggler.tables.TabWetBase;
import torggler.tables.TabWetGoods;
import torggler.tables.TabWetReport;
import torggler.utils.converters.ConverterBase;
import torggler.utils.converters.ConverterGoods;
import torggler.utils.converters.ConverterReport;
import torggler.utils.converters.ConverterUser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReportModel {


    private ObservableList<OrderFx> orderFxObservableList = FXCollections.observableArrayList ();
    private ObjectProperty<OrderFx> orderFxObjectProperty = new SimpleObjectProperty<> (new OrderFx ( ));


    //FILTER-------

    //wypełnianie listą ComboBoxu
    private ObservableList<BaseFx> baseFxObservableList = FXCollections.observableArrayList ( );
    private ObservableList<GoodsProperty> goodsPropertyObservableList = FXCollections.observableArrayList ();
    private ObservableList<UserFx> userFxObservableList = FXCollections.observableArrayList ();

    // 1. przechowywanie wyboru/wybranie jakies pozycji zapisze wartość do baseFxObjectProperty -> dodane GET & SET
    // 2. baseFxObjectProperty bindujemy -> w sekcji FILTER INITIALIZE "bindowanie"
    private ObjectProperty<BaseFx> baseFxObjectProperty = new SimpleObjectProperty<> ( );
    private ObjectProperty<GoodsProperty> goodsPropertyObjectProperty = new SimpleObjectProperty<> ();
    private ObjectProperty<UserFx> userFxObjectProperty = new SimpleObjectProperty<> ();
    //lista przechowuje pełną listę - przytrzymana do odświeżenia
    private List<OrderFx> orderFxList = new ArrayList<> ( );

    //Ta lista będzie przechowywać CAŁĄ zawartość do przywrócenia po filtrowaniu javafx#48 1400

    public void initnew() throws ApplicationException {
        ReportDao reportDao = new ReportDao ();
        List<TabWetReport> tabWetReports = reportDao.queryForAll (TabWetReport.class);
        orderFxList.clear ();
        tabWetReports.forEach (dataraport ->{
            //this.orderFxObservableList.add (ConverterReport.convertToOrderFX (dataraport));
        this.orderFxList.add (ConverterReport.convertToOrderFX (dataraport));
        });
        this.orderFxObservableList.setAll (orderFxList);

        initBase();
        intGoods ();
        intUser();

    }

    //Wypełnienie baseFxObservableList (rodzaje bazy do tynków)
    private void initBase() throws ApplicationException {

        // Bazy
        BaseDao baseDao = new BaseDao ( );
        List<TabWetBase> bases = baseDao.queryForAll (TabWetBase.class);
        // this.baseList.clear ( );
        this.baseFxObservableList.clear ();

        //javafx#48 3:17 NOWA metoda przypisywania
        bases.forEach (c -> {
            BaseFx baseFx = ConverterBase.convertToBaseFx (c);
            this.baseFxObservableList.add (baseFx);
        });
        // odwołanie do listy z table view

        //STARA metoda przypisywania
        /*
        bases.forEach (c -> {
            BaseFx baseFx = new BaseFx ( );
            baseFx.setId (c.getIdWetBase ( ));
            baseFx.setName (c.getName ( ));
            this.baseList.add (baseFx);
        });
        */

    }


    private void intGoods() throws ApplicationException {
        GoodsDao goodsDao = new GoodsDao();
        List<TabWetGoods> goods = goodsDao.queryForAll (TabWetGoods.class);
        this.goodsPropertyObservableList.clear ();

        goods.forEach (c->{
            GoodsProperty goodsProperty = ConverterGoods.convertToGoodsProperty (c);
            this.goodsPropertyObservableList.add (goodsProperty);
        });

    }

    private void intUser() throws ApplicationException {
        UserDao userDao = new UserDao ( );
        this.userFxObservableList.clear ( );
        List<TabUsers> users = userDao.queryForAll (TabUsers.class);
        users.forEach (usr -> {
            UserFx userFx = ConverterUser.convertToUserFx (usr);
            userFxObservableList.add (userFx);
        });
    }
    public void filterReportsList() {

        //spr większą ilość filtrów javafx #48 17:00
        if (getBaseFxObjectProperty ( ) != null && getGoodsPropertyObjectProperty () != null &&
                getUserFxObjectProperty () != null) {
            filterPredicate (baseFxPredicate ( ).and (goodsFxPredicate ( )) .and (userFxPredicate ()));
        }
        else if (getGoodsPropertyObjectProperty ()!= null){ filterPredicate (goodsFxPredicate ());}
        else if (getUserFxObjectProperty () != null) {filterPredicate (userFxPredicate ());}
        else if (getBaseFxObjectProperty () != null) {filterPredicate (baseFxPredicate ());}
        else {this.orderFxObservableList.setAll (this.orderFxList);} //jeżeli nic nie wybrane to skasuj i załaduj
        // wszystko, bo przetrzymuje całą zawartość w orderFxObservableList

    }


    // porównanie czy baza wybrana ma takie samo id == jak ten znadujący sie w tab base----------------------------
    private Predicate<OrderFx> baseFxPredicate() {

     /* wersja rozszeżona skrócona poniżej javafx #48 10:00
     Predicate<OrderFx> predicate = orderFx -> orderFx.getBaseFx ().getId () == getBaseFxObjectProperty ().getId ();
     return predicate;
     */
        return orderFx -> orderFx.getBaseFx ( ).getId ( ) == getBaseFxObjectProperty ( ).getId ( );
    }

    private Predicate<OrderFx> goodsFxPredicate(){
        return orderFx -> orderFx.getGoodsProperty ().getIdWetGoodsProperty () == getGoodsPropertyObjectProperty ()
                .getIdWetGoodsProperty ();
    }

    private Predicate<OrderFx> userFxPredicate(){
        return orderFx -> orderFx.getUserFxCreate ().getId () == getUserFxObjectProperty ().getId ();
    }


    private void filterPredicate(Predicate<OrderFx> predicate) {
        List<OrderFx> newList = orderFxList.stream ().filter (predicate).collect(Collectors.toList());
        this.orderFxObservableList.setAll (newList);
    }

    public void deleteReport(OrderFx orderFx) throws ApplicationException {
    ReportDao reportDao = new ReportDao ();
    reportDao.deleteById (TabWetReport.class, orderFx.getId ());
    initnew ();
    }


    // SET & GET -------------------------------------------------------------------------------------------------------

    public ObservableList<OrderFx> getOrderFxObservableList() {
        return orderFxObservableList;
    }

    public void setOrderFxObservableList(ObservableList<OrderFx> orderFxObservableList) {
        this.orderFxObservableList = orderFxObservableList;
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

    //Filter GET & SET-------------------------------------------------------------------------------------------------
    public ObservableList<BaseFx> getBaseFxObservableList() {
        return baseFxObservableList;
    }

    public void setBaseFxObservableList(ObservableList<BaseFx> baseFxObservableList) {
        this.baseFxObservableList = baseFxObservableList;
    }

    public BaseFx getBaseFxObjectProperty() {
        return baseFxObjectProperty.get ( );
    }

    public ObjectProperty<BaseFx> baseFxObjectProperty() {
        return baseFxObjectProperty;
    }

    public void setBaseFxObjectProperty(BaseFx baseFxObjectProperty) {
        this.baseFxObjectProperty.set (baseFxObjectProperty);
    }

    public GoodsProperty getGoodsPropertyObjectProperty() {
        return goodsPropertyObjectProperty.get ( );
    }

    public ObjectProperty<GoodsProperty> goodsPropertyObjectPropertyProperty() {
        return goodsPropertyObjectProperty;
    }

    public void setGoodsPropertyObjectProperty(GoodsProperty goodsPropertyObjectProperty) {
        this.goodsPropertyObjectProperty.set (goodsPropertyObjectProperty);
    }

    public ObservableList<GoodsProperty> getGoodsPropertyObservableList() {
        return goodsPropertyObservableList;
    }

    public void setGoodsPropertyObservableList(ObservableList<GoodsProperty> goodsPropertyObservableList) {
        this.goodsPropertyObservableList = goodsPropertyObservableList;
    }

    public UserFx getUserFxObjectProperty() {
        return userFxObjectProperty.get ( );
    }

    public ObjectProperty<UserFx> userFxObjectPropertyProperty() {
        return userFxObjectProperty;
    }

    public void setUserFxObjectProperty(UserFx userFxObjectProperty) {
        this.userFxObjectProperty.set (userFxObjectProperty);
    }

    public ObservableList<UserFx> getUserFxObservableList() {
        return userFxObservableList;
    }

    public void setUserFxObservableList(ObservableList<UserFx> userFxObservableList) {
        this.userFxObservableList = userFxObservableList;
    }
    //END Filter GET & SET---------------------------------------------------------------------------------------------

}
