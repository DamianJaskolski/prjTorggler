package torggler.modelFx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class GoodsProperty {

    private IntegerProperty idWetGoodsProperty = new SimpleIntegerProperty();
    private StringProperty nameWetGoodsProperty = new SimpleStringProperty();


    public int getIdWetGoodsProperty() {
        return idWetGoodsProperty.get();
    }

    public IntegerProperty idWetGoodsPropertyProperty() {
        return idWetGoodsProperty;
    }

    public void setIdWetGoodsProperty(int idWetGoodsProperty) {
        this.idWetGoodsProperty.set(idWetGoodsProperty);
    }

    public String getNameWetGoodsProperty() {
        return nameWetGoodsProperty.get();
    }

    public StringProperty nameWetGoodsPropertyProperty() {
        return nameWetGoodsProperty;
    }

    public void setNameWetGoodsProperty(String nameWetGoodsProperty) {
        this.nameWetGoodsProperty.set(nameWetGoodsProperty);
    }

    @Override
    public String toString() {
        return nameWetGoodsProperty.getValue();
    }
}
