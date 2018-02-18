package torggler;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import torggler.tables.*;

import java.time.format.DateTimeFormatter;


public class Main extends Application {

    public static final String MAIN_MENU_FXML = "/fxml/main.fxml";
    public static final String MAIN_LOGIN_FXML = "/fxml/login.fxml";

    public static final String URL = "jdbc:postgresql://127.0.0.1:5432/dbTorggler";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dagdam";


    public static void main(String[] args) {
        launch(args);
    }

    //private TabUsers tabUsers;
    //private TabWetProduct tabWetProduct;
    //private TabWetBase tabWetBase;
    //private TabWetReport tabWetReport;

    public void start(Stage primaryStage) throws Exception {


        ConnectionSource connectionSource = new JdbcConnectionSource(URL, USER, PASSWORD);
/*
     //User
        TableUtils.dropTable(connectionSource, TabUsers.class, true);
        TableUtils.createTableIfNotExists(connectionSource, TabUsers.class);
        Dao<TabUsers, Integer> tabUsersIntegerDao = DaoManager.createDao(connectionSource, TabUsers.class);
        TabUsers tabUsers = DataCreator.tabUsers();
        tabUsersIntegerDao.createOrUpdate(tabUsers);
        connectionSource.close();
*/

/*      //Product
        TableUtils.dropTable(connectionSource, TabWetProduct.class, true);
        TableUtils.createTableIfNotExists(connectionSource, TabWetProduct.class);
        Dao<TabWetProduct, Integer> tabWetProductIntegerDao = DaoManager.createDao(connectionSource, TabWetProduct
       .class);
        TabWetProduct tabWetProduct = DataCreator.tabWetProduct(); //Pamietaj DC to tylko Clasa
        tabWetProductIntegerDao.createOrUpdate(tabWetProduct);
        connectionSource.close();
*/

/*      //Base
        TableUtils.dropTable(connectionSource, TabWetBase.class, true);
        TableUtils.createTableIfNotExists(connectionSource, TabWetBase.class);
        Dao<TabWetBase, Integer> tabWetBaseIntegerDao = DaoManager.createDao(connectionSource, TabWetBase.class);
        TabWetBase tabWetBase = DataCreator.tabWetBase();
        tabWetBaseIntegerDao.createOrUpdate(tabWetBase);
        connectionSource.close();
*/

/*
    //Goods
    TableUtils.dropTable(connectionSource,TabWetGoods.class,true);
    TableUtils.createTableIfNotExists(connectionSource,TabWetGoods.class);
    Dao<TabWetGoods, Integer> tabWetGoodsIntegerDao = DaoManager.createDao(connectionSource,TabWetGoods.class);
    TabWetGoods tabWetGoods = DataCreator.tabWetGoods();
    tabWetGoodsIntegerDao.createOrUpdate(tabWetGoods);
    connectionSource.close();
*/
/*
    //Report
        TableUtils.dropTable(connectionSource, TabWetReport.class, true);
        TableUtils.createTable (connectionSource, TabWetReport.class);
        Dao<TabWetReport, Integer> tabWetReportIntegerDao = DaoManager.createDao(connectionSource, TabWetReport.class);
        TabWetReport tabWetReport = DataCreator.tabWetReport();
        tabWetReportIntegerDao.createOrUpdate(tabWetReport);
        connectionSource.close();

*/
/*
        //Status
        TableUtils.dropTable(connectionSource,Status.class,true);
        TableUtils.createTableIfNotExists(connectionSource, Status.class);
        Dao<Status,Integer> statusIntegerDao = DaoManager.createDao(connectionSource,Status.class);
        Status status = DataCreator.status();
        statusIntegerDao.createOrUpdate(status);
        connectionSource.close();
*/

        //Load main stage
        BorderPane mainborderPane = FXMLLoader.load(getClass().getResource(MAIN_MENU_FXML));
       // BorderPane mainborderPane = FXMLLoader.load(getClass().getResource(MAIN_LOGIN_FXML));

        primaryStage.setScene(new Scene(mainborderPane));
        primaryStage.show();

    }
}
