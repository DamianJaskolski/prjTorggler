package torggler;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import torggler.dao.UserDao;
import torggler.modelFx.UserFx;
import torggler.tables.TabUsers;
import torggler.utils.converters.ConverterUser;

import java.util.List;

public class UserSingleton {



    //stan enable or disable menu true or false
    private BooleanProperty isLogged = new SimpleBooleanProperty ();


    //zmienna "instance" będzie przechowywać instacje klasy
    private static UserSingleton instance = null;

    public Integer id;
    public String log_in;
    public String department_lg;




      //  this.isLogged.set(true);

    //blokowanie tworzenia instacji przez konstruktora domyślnego

    //konstruktor
    private UserSingleton(){


    }

    //metoda zwraca instacje lub tworzy jeśli jeszcze jej nie ma
    //jeżeli wiele wątków zechce skorzystać z metody getInstace to wtedy trzeba by to zsynchronizować
    public static synchronized UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }





    //GET&SET----

    //isLogged
    public boolean isIsLogged() {
        return isLogged.get ( );
    }

    public BooleanProperty isLoggedProperty() {
        return isLogged;
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged.set (isLogged);
    }


}
