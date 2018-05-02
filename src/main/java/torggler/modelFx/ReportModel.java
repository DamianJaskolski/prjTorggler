package torggler.modelFx;

import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import torggler.ApplicationException;
import torggler.controllers.WetReportController;
import torggler.dao.BaseDao;
import torggler.dao.GoodsDao;
import torggler.dao.ReportDao;
import torggler.dao.UserDao;
import torggler.tables.TabUsers;
import torggler.tables.TabWetBase;
import torggler.tables.TabWetGoods;
import torggler.tables.TabWetReport;
import torggler.utils.converters.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ReportModel {

    //filtrowanie daty
    private ObservableList<OrderFx> orderFxObservableList = FXCollections.observableArrayList ();

    public FilteredList<OrderFx> filteredData = new FilteredList<>(orderFxObservableList, person -> true);
    public FilteredList<OrderFx> filteredDataCommission = new FilteredList<>(orderFxObservableList, p -> true);



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

   public WetReportController wetReportController;




    public boolean isDayToday(OrderFx person) {
        return person.getCreate_date ().toLocalDate().isEqual(LocalDate.now());
    }

    public boolean isDayTodayCommission (OrderFx person){
        //return p.getCommissionDate().isEqual(LocalDate.now());
        return person.getCommissionDate().isEqual(LocalDate.now());
    }

    public void showDates(){
        filteredData.setPredicate(person -> dateBetween(person));
        this.wetReportController.tableViewWetReport.setItems(filteredData);
    }

    public boolean dateBetween(OrderFx person) {
        LocalDate date1 = this.wetReportController.date1.getValue();
        LocalDate date2 = this.wetReportController.date2.getValue();
       // LocalDateTime dataPrzekonwertowana = ConverterDate.convertToLocalDate (person.getCreate_date ());
        boolean isAfter = person.getCommissionDate().isAfter(date1) || person.getCommissionDate().isEqual (date1);

        boolean isBefore = person.getCommissionDate().isBefore(date2)|| person.getCommissionDate().isEqual(date2);
        return isAfter && isBefore;
    }


    public void initnew() throws ApplicationException {

        ReportDao reportDao = new ReportDao ();
        List<TabWetReport> tabWetReports = reportDao.queryForAll (TabWetReport.class);
        orderFxList.clear ();
        tabWetReports.forEach (dataraport ->{
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

    public FilteredList<OrderFx> getFilteredData() {
        return filteredData;
    }

    public FilteredList<OrderFx> getFilteredDataCommission() {
        return filteredDataCommission;
    }

    public void setFilteredDataCommission(FilteredList<OrderFx> filteredDataCommission) {
        this.filteredDataCommission = filteredDataCommission;
    }

    //END Filter GET & SET---------------------------------------------------------------------------------------------

}
