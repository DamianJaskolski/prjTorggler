package torggler.tables;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import torggler.BaseModel;

import java.time.format.DateTimeFormatter;
import java.util.Date;

@DatabaseTable(tableName = "tab_users")
public class TabUsers implements BaseModel {

    @DatabaseField(generatedId = true)
    private int id_user;

    @DatabaseField(columnName = "login")
    private String login;

    @DatabaseField(columnName = "password")
    private String password;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "surname")
    private String surname;

    @DatabaseField(columnName = "department")
    private String department;

    @DatabaseField(columnName = "create_date")
    //private DateTimeFormatter create_date;
      private Date create_date;


    //GET & SET --------------------------------------------------------------------------------------------------------


    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}
