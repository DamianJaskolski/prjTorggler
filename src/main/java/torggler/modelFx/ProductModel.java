package torggler.modelFx;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import torggler.ApplicationException;
import torggler.dao.BaseDao;
import torggler.dao.ReportDao;
import torggler.tables.TabWetBase;
import torggler.tables.TabWetProduct;
import torggler.tables.TabWetReport;
import torggler.utils.converters.ConverterProduct;
import torggler.utils.converters.ConverterReport;

import java.io.IOException;
import java.sql.SQLException;

public class ProductModel {


    public static final String URL = "jdbc:postgresql://localhost:5432/dbTorggler";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dagdam";

    private ObjectProperty<ProductFx> productFxObjectProperty = new SimpleObjectProperty<>(new ProductFx());


    public void saveProductInDataBase(String name) {

        ConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        try {
            Dao<TabWetProduct, Integer> tabWetProducts = DaoManager.createDao (connectionSource, TabWetProduct.class);

            TabWetProduct tabWetProduct = new TabWetProduct();
            tabWetProduct.setName(name);
            tabWetProducts.createOrUpdate (tabWetProduct);

            try {
                connectionSource.close ( );
            } catch (IOException e) {
                e.printStackTrace ( );
            }



        } catch (SQLException e) {
            e.printStackTrace ( );
        }


    }



    public ProductFx getProductFxObjectProperty() {
        return productFxObjectProperty.get();
    }

    public ObjectProperty<ProductFx> productFxObjectPropertyProperty() {
        return productFxObjectProperty;
    }

    public void setProductFxObjectProperty(ProductFx productFxObjectProperty) {
        this.productFxObjectProperty.set(productFxObjectProperty);
    }
}
