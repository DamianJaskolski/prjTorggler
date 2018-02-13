package torggler.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import torggler.BaseModel;

@DatabaseTable(tableName = "tabWetBase")

public class TabWetBase implements BaseModel{

    @DatabaseField(generatedId = true)
    private int idWetBase;

    @DatabaseField(columnName = "NAME")
    private String name;

    public int getIdWetBase() {
        return idWetBase;
    }

    public void setIdWetBase(int idWetBase) {
        this.idWetBase = idWetBase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
