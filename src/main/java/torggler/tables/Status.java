package torggler.tables;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import torggler.BaseModel;

import java.util.Date;


@DatabaseTable(tableName = "tabStatus")

public class Status implements BaseModel {


    @DatabaseField(generatedId = true)
    private int idStatus;

    @DatabaseField(columnName = "STATUS")
    private String status;

    @DatabaseField(columnName = "S1")
    private String s1;

    @DatabaseField(columnName = "I1")
    private int i1;

    @DatabaseField(columnName = "CREATE_DATE") // wycięte ,mdataType = DataType.LONG_STRING
    private Date createDate;

    //GET&SET


    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public int getI1() {
        return i1;
    }

    public void setI1(int i1) {
        this.i1 = i1;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
