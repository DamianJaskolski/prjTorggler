package torggler.modelFx;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.util.Date;

public class UserFx {

    private IntegerProperty id = new SimpleIntegerProperty ( );
    private StringProperty login = new SimpleStringProperty ( );
    private StringProperty name = new SimpleStringProperty ( );
    private StringProperty surname = new SimpleStringProperty ( );
    private StringProperty password = new SimpleStringProperty ( );
    private StringProperty department = new SimpleStringProperty ( );
    private ObjectProperty<LocalDateTime> create_date = new SimpleObjectProperty<> (LocalDateTime.now ( ));



    //GET&SET-----------------------------------------------------------------------------------------------------------

    public int getId() {
        return id.get ( );
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set (id);
    }

    public String getLogin() {
        return login.get ( );
    }

    public StringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set (login);
    }

    public String getName() {
        return name.get ( );
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set (name);
    }

    public String getSurname() {
        return surname.get ( );
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set (surname);
    }

    public String getPassword() {
        return password.get ( );
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set (password);
    }

    public String getDepartment() {
        return department.get ( );
    }

    public StringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set (department);
    }

    public LocalDateTime getCreate_date() {
        return create_date.get ( );
    }

    public ObjectProperty<LocalDateTime> create_dateProperty() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date.set (create_date);
    }

    @Override
    public String toString() { return login.getValue(); }
}