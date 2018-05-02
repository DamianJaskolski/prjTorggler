package torggler;

import torggler.tables.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DataCreator {

    public static TabWetProduct tabWetProduct() {
        TabWetProduct tabWetProduct = new TabWetProduct();
        tabWetProduct.setName("Ekor96");
        tabWetProduct.setGrein_size(1.5);
        return tabWetProduct;
    }

    public static TabWetBase tabWetBase() {
        TabWetBase tabWetBase = new TabWetBase();
        tabWetBase.setName("B");
        return tabWetBase;
    }

    public static TabUsers tabUsers(){
        TabUsers tabUsers = new TabUsers();
        tabUsers.setLogin("djaskolski");
        tabUsers.setPassword("dagdam");
        tabUsers.setName("Damian");
        tabUsers.setSurname("Jaskólski");
        tabUsers.setDepartment("Administrator");
        tabUsers.setCreate_date(new Date());
        return tabUsers;
    }

    public static Status status(){
        Status status = new Status();
        status.setStatus("Zgodna");
        status.setS1("null");
        status.setI1(0);
        status.setCreateDate(new Date());
        return status;
    }

    public static TabWetGoods tabWetGoods(){
        TabWetGoods tabWetGoods = new TabWetGoods();
        tabWetGoods.setName("Produkt mokra nazwa");
        return tabWetGoods;
    }

    public static TabWetReport tabWetReport() {
        TabWetReport tabWetReport = new TabWetReport();
        tabWetReport.setCreateDateReport(new Date());
        //tabWetReport.setEditionDateReport(new Date());
        tabWetReport.setTabWetGoodsForegin(tabWetGoods());
        tabWetReport.setTabWetProductForeign(tabWetProduct());
        tabWetReport.setCompInfo ("Informacja uzupełniająca");
        tabWetReport.setTabWetBaseForegin(tabWetBase());
        tabWetReport.setTabUsersForegin(tabUsers());
        tabWetReport.setTabUsersEditForegin (tabUsers ());
        tabWetReport.setPack(25);
        tabWetReport.setOrder_quantity(100);
        tabWetReport.setOrder_realize(75);
        tabWetReport.setComment("To jest komentarz do zamówienia lub realizacji");
        tabWetReport.setLabComment ("Zgodny");
        tabWetReport.setTabUsersLabForegin (tabUsers ());
        tabWetReport.setStatusLabForegin(status());
        tabWetReport.setTabUFWarehouseman(tabUsers());
        tabWetReport.setAmount(0);
        tabWetReport.setStatusWhm("przyjęte");
        tabWetReport.setComment("To jest komentarz magazyniera");
        return tabWetReport;
    }
}
