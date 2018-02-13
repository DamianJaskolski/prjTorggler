package torggler.modelFx;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import torggler.ApplicationException;
import torggler.UserSingleton;
import torggler.dao.UserDao;
import torggler.tables.TabUsers;
import torggler.utils.converters.ConverterUser;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginModel {

    private ObjectProperty<UserFx> userFxObjectProperty = new SimpleObjectProperty<>();

    Connection connection;


    public LoginModel(){

        connection = SqlConnection.Conector ();
        if (connection == null) System.exit (1);
    }

    public boolean isDbConnected(){

        try {
            return !connection.isClosed ();
        } catch (SQLException e) {
            e.printStackTrace ( );
            return false;
        }
    }


    public boolean isLogin (String login, String password) throws SQLException {

        //https://www.youtube.com/watch?v=_eCn4pLw350

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM tab_users WHERE login = ? and password = ?";


        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);


            resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet);

            // Ver.1
            UserSingleton us = UserSingleton.getInstance();
            us.log_in = login.toString();


            if (resultSet.next()) {
                return  true;
            }else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println ("Blad inny" );
            return  false;
        }

        finally {

            try {
                preparedStatement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // Próba pobrania wszystkich wartości
    /*
    public void intUser() throws ApplicationException {
        UserDao userDao = new UserDao ( );
        this.userFxObservableList.clear ( );
        List<TabUsers> users = userDao.queryForAll (TabUsers.class);
        users.forEach (usr -> {
            UserFx userFx = ConverterUser.convertToUserFx (usr);
            userFxObservableList.add (userFx);
        });
    }

    */


    //Lista Users G&S-----------------



    public UserFx getUserFxObjectProperty() {
        return userFxObjectProperty.get ( );
    }

    public ObjectProperty<UserFx> userFxObjectPropertyProperty() {
        return userFxObjectProperty;
    }

    public void setUserFxObjectProperty(UserFx userFxObjectProperty) {
        this.userFxObjectProperty.set (userFxObjectProperty);
    }


}
