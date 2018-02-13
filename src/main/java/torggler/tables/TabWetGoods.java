package torggler.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import torggler.BaseModel;

@DatabaseTable(tableName = "tabWetGoods")

public class TabWetGoods implements BaseModel{

    @DatabaseField(generatedId = true)
    private int idWetGoods;

    @DatabaseField(columnName = "NAME")
    private String name;

    public int getIdWetGoods() {
        return idWetGoods;
    }

    public void setIdWetGoods(int idWetGoods) {
        this.idWetGoods = idWetGoods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
