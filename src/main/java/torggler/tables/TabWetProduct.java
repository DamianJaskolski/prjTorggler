package torggler.tables;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import torggler.BaseModel;

@DatabaseTable(tableName = "tabWetProduct")
public class TabWetProduct implements BaseModel {

    @DatabaseField(generatedId = true)
    private int idWetProduct;

    @DatabaseField(columnName = "NAME")
    private String name;

    @DatabaseField(columnName = "GREIN_SIZE")
    private double grein_size;


    //----------GET AND SET


    public int getIdWetProduct() {
        return idWetProduct;
    }

    public void setIdWetProduct(int idWetProduct) {
        this.idWetProduct = idWetProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGrein_size() {
        return grein_size;
    }

    public void setGrein_size(double grein_size) {
        this.grein_size = grein_size;
    }
}
