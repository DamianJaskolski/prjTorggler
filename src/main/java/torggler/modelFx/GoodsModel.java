package torggler.modelFx;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import torggler.tables.TabWetGoods;
import torggler.tables.TabWetReport;
import torggler.utils.converters.ConverterGoods;

import java.io.IOException;
import java.sql.SQLException;

public class GoodsModel {

    public static final String URL = "jdbc:postgresql://127.0.0.1:5432/dbTorggler";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dagdam";

    private ObjectProperty<GoodsProperty> goodsPropObjectProperty = new SimpleObjectProperty<>(new GoodsProperty());

    public void saveDataInTableGoods(String name) {

        // ustawianie połączenia z bazą----
        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //----


        try {
            //dao łącznik ----
            Dao<TabWetGoods, Integer> tabWetGoodsIntegerDao = DaoManager.createDao(connectionSource, TabWetGoods.class);

            TabWetGoods tabWetGoods = new TabWetGoods();
            tabWetGoods.setName(name);
            tabWetGoodsIntegerDao.createOrUpdate(tabWetGoods);

            /*
            // converter z Property do Klasy tabeli
            TabWetGoods tabWetGoods = ConverterGoods.convetToGoods(this.getGoodsPropObjectProperty());
            tabWetGoods.setIdWetGoods(this.goodsPropObjectProperty.get().getIdWetGoodsProperty());
            tabWetGoods.setName(this.goodsPropObjectProperty.get().getNameWetGoodsProperty());
            tabWetGoodsIntegerDao.createOrUpdate(tabWetGoods);
            */

        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            connectionSource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //----

    }

    //GET & SET


    public GoodsProperty getGoodsPropObjectProperty() {
        return goodsPropObjectProperty.get();
    }

    public ObjectProperty<GoodsProperty> goodsPropObjectPropertyProperty() {
        return goodsPropObjectProperty;
    }

    public void setGoodsPropObjectProperty(GoodsProperty goodsPropObjectProperty) {
        this.goodsPropObjectProperty.set(goodsPropObjectProperty);
    }
}
