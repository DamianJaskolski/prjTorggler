package torggler.controllers;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import torggler.ApplicationException;
import torggler.UserSingleton;
import torggler.modelFx.LoginModel;
import torggler.modelFx.UserFx;
import torggler.tables.TabUsers;


import java.net.URL;
import java.sql.*;

import java.util.List;
import java.util.ResourceBundle;

    public class LoginController implements Initializable{


        public static final String URL = "jdbc:postgresql://127.0.0.1:5432/dbTorggler";
        private static final String USER = "postgres";
        private static final String PASSWORD = "dagdam";


    public LoginModel loginModel = new LoginModel ();

    private ObservableList<UserFx> userFxObservableList = FXCollections.observableArrayList ();

    @FXML
    private PasswordField tf_password;

    @FXML
    private TextField tf_login;

    @FXML
    private Label lab_statuslogin;

    @FXML
    private Label lab_status_conn;

    private MainController mainController;



public void initialize(URL location, ResourceBundle resources) {

    if (loginModel.isDbConnected ()) {
        lab_status_conn.setText ("Połączony");}
        else{
        lab_status_conn.setText ("Nie połączony");
    }
/*
    try {
        loginModel.intUser ();
    } catch (ApplicationException e) {
        e.printStackTrace ( );
    }
*/
}

    public void actLogin(ActionEvent event) {

        Platform.runLater(new Runnable(){
            @Override
            public void run() {

                try {
                    if (loginModel.isLogin(tf_login.getText(), tf_password.getText()))
                    {
                        lab_statuslogin.setText("Login OK");

                        UserSingleton us = UserSingleton.getInstance();
                        System.out.println(us.log_in);

                        //-------

                        setLoginUser();;

                        //------


                        us.setIsLogged (true);

                        //pasek dolny w oknie glowny, informacja o zalogowanym użytkowniku

                        mainController.tf_info.setText(us.log_in);
                       //mainController.tf_info.setText(loginModel.getUserFxObservableList ().toString ());
                       // loginModel.getUserFxObjectProperty().setLogin (loginModel.getUserFxObservableList ()
                         //   .toString ());

                       // mainController.tf_info.setText(loginModel.userFxObjectPropertyProperty ().toString ());


                      //mainController.mainTreeView.disabledProperty ().not ();

                       // mainController.mainTreeView.setShowRoot (true);

                    } else {
                        lab_statuslogin.setText("Wrong login");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }


        });

    }
        public void setLoginUser() throws SQLException {

            UserSingleton us = UserSingleton.getInstance();
            ConnectionSource connectionSource = new JdbcConnectionSource(URL, USER, PASSWORD);
            Dao<TabUsers, Integer> userDao = DaoManager.createDao(connectionSource, TabUsers.class);

            QueryBuilder<TabUsers, Integer> queryBuilder = userDao.queryBuilder();
            queryBuilder.where().eq("login", us.log_in);
            PreparedQuery<TabUsers> preparedQuery = queryBuilder.prepare(); // możliwość zapisanie w krótszej formie
            // 3.1 QueryBuilerBasic

            List<TabUsers> userFxList = userDao.query(preparedQuery);
            userFxList.forEach(u -> {
                UserFx userFx = new UserFx ();
                System.out.println("Login : " + us.log_in);
                userFx.setId (u.getId_user ());
                us.id = u.getId_user ();
                System.out.println("ID: "+ us.id);
                userFx.setLogin (u.getLogin ());
                userFx.setDepartment (u.getDepartment ());
                us.department_lg = u.getDepartment ();
                System.out.println("Dział: " + us.department_lg);
                this.userFxObservableList.add (userFx); //nie używam ale zostawiam
            });

        }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

        public ObservableList<UserFx> getUserFxObservableList() {
            return userFxObservableList;
        }

        public void setUserFxObservableList(ObservableList<UserFx> userFxObservableList) {
            this.userFxObservableList = userFxObservableList;
        }
    }

