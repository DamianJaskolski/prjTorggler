package torggler.modelFx;

import javafx.beans.property.*;
import sun.util.resources.LocaleData;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class OrderFx {

    private ObjectProperty<BaseFx> cmbBoxBaseFX = new SimpleObjectProperty<> ( );
    private ObjectProperty<GoodsProperty> cmbBoxGoodsProperty = new SimpleObjectProperty<> ();

    private IntegerProperty id = new SimpleIntegerProperty ( );
    private ObjectProperty<ProductFx> productFx = new SimpleObjectProperty<> ( );
    private ObjectProperty<GoodsProperty> goodsProperty = new SimpleObjectProperty<>();
    private StringProperty compInfo = new SimpleStringProperty ( );
    private ObjectProperty<BaseFx> baseFx = new SimpleObjectProperty<> ( );
    private IntegerProperty pack = new SimpleIntegerProperty ( );
    private DoubleProperty order_quantity = new SimpleDoubleProperty ( );
    private DoubleProperty order_realize = new SimpleDoubleProperty ( );
    private StringProperty comment = new SimpleStringProperty ( );
    private ObjectProperty<LocalDateTime> create_date = new SimpleObjectProperty<> (LocalDateTime.now ());
   // private ObjectProperty<Time> timeObjectProperty = new SimpleObjectProperty<>(Lo)
    private ObjectProperty<UserFx> userFxCreate = new SimpleObjectProperty<> (new UserFx () );
    private ObjectProperty<LocalDateTime> edit_date = new SimpleObjectProperty<> (LocalDateTime.now ());
    private ObjectProperty<UserFx> userFxEdit = new SimpleObjectProperty<> ( );

    private StringProperty labcomment = new SimpleStringProperty ( );
    private ObjectProperty<LabFx> statusFx = new SimpleObjectProperty<> ( );


    //GET&SET

    public BaseFx getCmbBoxBaseFX() {
        return cmbBoxBaseFX.get ( );
    }

    public ObjectProperty<BaseFx> cmbBoxBaseFXProperty() {
        return cmbBoxBaseFX;
    }

    public void setCmbBoxBaseFX(BaseFx cmbBoxBaseFX) {
        this.cmbBoxBaseFX.set (cmbBoxBaseFX);
    }

    public int getId() {
        return id.get ( );
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set (id);
    }


    public ProductFx getProductFx() {
        return productFx.get ( );
    }

    public ObjectProperty<ProductFx> productFxProperty() {
        return productFx;
    }

    public void setProductFx(ProductFx productFx) {
        this.productFx.set (productFx);
    }



    public BaseFx getBaseFx() {
        return baseFx.get ( );
    }

    public ObjectProperty<BaseFx> baseFxProperty() {
        return baseFx;
    }

    public void setBaseFx(BaseFx baseFx) {
        this.baseFx.set (baseFx);
    }



    public int getPack() {
        return pack.get ( );
    }

    public IntegerProperty packProperty() {
        return pack;
    }

    public void setPack(int pack) {
        this.pack.set (pack);
    }

    public double getOrder_quantity() {
        return order_quantity.get ( );
    }

    public DoubleProperty order_quantityProperty() {
        return order_quantity;
    }

    public void setOrder_quantity(double order_quantity) {
        this.order_quantity.set (order_quantity);
    }

    public double getOrder_realize() {
        return order_realize.get ( );
    }

    public DoubleProperty order_realizeProperty() {
        return order_realize;
    }

    public void setOrder_realize(double order_realize) {
        this.order_realize.set (order_realize);
    }

    public String getComment() {
        return comment.get ( );
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set (comment);
    }

    public LocalDateTime getCreate_date() {
        return create_date.get();
    }

    public ObjectProperty<LocalDateTime> create_dateProperty() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date.set(create_date);
    }

    public UserFx getUserFxCreate() {
        return userFxCreate.get ( );
    }

    public ObjectProperty<UserFx> userFxCreateProperty() {
        return userFxCreate;
    }

    public void setUserFxCreate(UserFx userFxCreate) {
        this.userFxCreate.set (userFxCreate);
    }


    public UserFx getUserFxEdit() {
        return userFxEdit.get ( );
    }

    public ObjectProperty<UserFx> userFxEditProperty() {
        return userFxEdit;
    }

    public void setUserFxEdit(UserFx userFxEdit) {
        this.userFxEdit.set (userFxEdit);
    }

    public String getCompInfo() {
        return compInfo.get ( );
    }

    public StringProperty compInfoProperty() {
        return compInfo;
    }

    public void setCompInfo(String compInfo) {
        this.compInfo.set (compInfo);
    }

    public LocalDateTime getEdit_date() { return edit_date.get ( ); }

    public ObjectProperty<LocalDateTime> edit_dateProperty() {
        return edit_date;
    }

    public void setEdit_date(LocalDateTime edit_date) {
        this.edit_date.set (edit_date);
    }


    //lab get & set----------------------------------------------------------------------------------------------------


    public LabFx getStatusFx() {
        return statusFx.get ( );
    }

    public ObjectProperty<LabFx> statusFxProperty() {
        return statusFx;
    }

    public void setStatusFx(LabFx statusFx) {
        this.statusFx.set (statusFx);
    }


    public String getLabcomment() {
        return labcomment.get ( );
    }

    public StringProperty labcommentProperty() {
        return labcomment;
    }

    public void setLabcomment(String labcomment) {
        this.labcomment.set (labcomment);
    }

    //--------------------------------


    public GoodsProperty getCmbBoxGoodsProperty() {
        return cmbBoxGoodsProperty.get();
    }

    public ObjectProperty<GoodsProperty> cmbBoxGoodsPropertyProperty() {
        return cmbBoxGoodsProperty;
    }

    public void setCmbBoxGoodsProperty(GoodsProperty cmbBoxGoodsProperty) {
        this.cmbBoxGoodsProperty.set(cmbBoxGoodsProperty);
    }

    public GoodsProperty getGoodsProperty() {
        return goodsProperty.get();
    }

    public ObjectProperty<GoodsProperty> goodsPropertyProperty() {
        return goodsProperty;
    }

    public void setGoodsProperty(GoodsProperty goodsProperty) {
        this.goodsProperty.set(goodsProperty);
    }
}