package torggler;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import torggler.tables.TabUsers;

public class WetGoodsSingleton {

    //zmienna "instance" będzie przechowywać instacje klasy
    private static WetGoodsSingleton instance = null;

    public Integer idWetGoods;
    public String name;



    //konstruktor
    private WetGoodsSingleton(){


    }

    //metoda zwraca instacje lub tworzy jeśli jeszcze jej nie ma
    //jeżeli wiele wątków zechce skorzystać z metody getInstace to wtedy trzeba by to zsynchronizować
    public static synchronized WetGoodsSingleton getInstance() {
        if (instance == null) {
            instance = new WetGoodsSingleton();
        }
        return instance;
    }





    //GET&SET----


    public static void setInstance(WetGoodsSingleton instance) {
        WetGoodsSingleton.instance = instance;
    }

    public Integer getIdWetGoods() {
        return idWetGoods;
    }

    public void setIdWetGoods(Integer idWetGoods) {
        this.idWetGoods = idWetGoods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
