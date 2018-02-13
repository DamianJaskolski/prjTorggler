package torggler.modelFx;

import javafx.beans.property.*;

import java.time.LocalDate;

public class LabFx {

    private IntegerProperty idStatus = new SimpleIntegerProperty ();
    private StringProperty status = new SimpleStringProperty ();
    private StringProperty s1 = new SimpleStringProperty ();
    private IntegerProperty i1 = new SimpleIntegerProperty ();
    private ObjectProperty<LocalDate> create_date = new SimpleObjectProperty <>(LocalDate.now ());

    //SET &GET --------------------------------------------------------------------------------------------------------

    public int getIdStatus() {
        return idStatus.get ( );
    }

    public IntegerProperty idStatusProperty() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus.set (idStatus);
    }

    public String getStatus() {
        return status.get ( );
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set (status);
    }

    public String getS1() {
        return s1.get ( );
    }

    public StringProperty s1Property() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1.set (s1);
    }

    public int getI1() {
        return i1.get ( );
    }

    public IntegerProperty i1Property() {
        return i1;
    }

    public void setI1(int i1) {
        this.i1.set (i1);
    }

    public LocalDate getCreate_date() {
        return create_date.get ( );
    }

    public ObjectProperty<LocalDate> create_dateProperty() {
        return create_date;
    }

    public void setCreate_date(LocalDate create_date) {
        this.create_date.set (create_date);
    }

    @Override
    public String toString() {
        return status.getValue();
    }
}
