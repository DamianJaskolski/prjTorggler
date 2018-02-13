package torggler.modelFx;

import javafx.beans.property.*;

import java.time.LocalDate;

public class ProductFx {

    private IntegerProperty idProductWET = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    //private DoubleProperty grein_size = new SimpleDoubleProperty();

    //SET & GET --------------------------------------------------------------------------------------------------


    public int getIdProductWET() {
        return idProductWET.get();
    }

    public IntegerProperty idProductWETProperty() {
        return idProductWET;
    }

    public void setIdProductWET(int idProductWET) {
        this.idProductWET.set(idProductWET);
    }


    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

  /*  public double getGrein_size() {
        return grein_size.get();
    }

    public DoubleProperty grein_sizeProperty() {
        return grein_size;
    }

    public void setGrein_size(double grein_size) {
        this.grein_size.set(grein_size);
    }
*/
    @Override
    public String toString() {
        return name.getValue();
    }
}
