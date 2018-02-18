package torggler.tables;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import torggler.BaseModel;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;

@DatabaseTable(tableName = "tabWetReport")

public class TabWetReport implements BaseModel {

    @DatabaseField(generatedId = true)
    private int idWetReport;


    //date
    @DatabaseField(columnName = "CREATE_DATE_REPORT")  // wycięte ,mdataType = DataType.LONG_STRING
    private Date createDateReport;
/*
    @DatabaseField(columnName = "TIME")
    private Timer createTimeReport;
*/
    @DatabaseField(columnName = "EDITION_DATE_REPORT") //// wycięte ,mdataType = DataType.LONG_STRING
    private Date editionDateReport;


  //produkt
    @DatabaseField(columnName = "idWetProductForeign", foreign = true, foreignAutoCreate = true, foreignAutoRefresh =
            true,
            canBeNull = true)
    private TabWetProduct tabWetProductForeign;

    @DatabaseField(columnName = "idWetGoodsForeign", foreign = true, foreignAutoCreate = true, foreignAutoRefresh =
           true, canBeNull = true)
    private TabWetGoods tabWetGoodsForegin;


    @DatabaseField(columnName = "COMPLEMENTARY_INFORMATION")
    private String compInfo;

    @DatabaseField(columnName = "idWetBaseForeign", foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true,
            canBeNull = true)
    private TabWetBase tabWetBaseForegin;

    //user
    @DatabaseField(columnName = "idUserForeign", foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true,
            canBeNull = true)
    private TabUsers tabUsersForegin;

    @DatabaseField(columnName = "idUserEditForeign", foreign = true, foreignAutoCreate = true, foreignAutoRefresh =
            true,
            canBeNull = true)
    private TabUsers tabUsersEditForegin;

    //---
    @DatabaseField(columnName = "PACK")
    private int pack;

    @DatabaseField(columnName = "ORDER_QUANITY")
    private double order_quantity;

    @DatabaseField(columnName = "ORDER_REALIZE")
    private double order_realize;

    @DatabaseField(columnName = "COMMENT")
    private String comment;


//lab part

    @DatabaseField(columnName = "idStatusForeign", foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true,
            canBeNull = true)
    private Status statusLabForegin;
/*
    @DatabaseField(columnName = "idUserForeign", foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true,
            canBeNull = false)
    private TabUsers tabUsersLabForegin;
*/
    @DatabaseField(columnName = "LAB_COMMENT")
    private String labComment;


    //Get&Set


    public int getIdWetReport() {
        return idWetReport;
    }

    public void setIdWetReport(int idWetReport) {
        this.idWetReport = idWetReport;
    }

    public Date getCreateDateReport() {
        return createDateReport;
    }

    public void setCreateDateReport(Date createDateReport) {
        this.createDateReport = createDateReport;
    }

    public Date getEditionDateReport() {
        return editionDateReport;
    }

    public void setEditionDateReport(Date editionDateReport) {
        this.editionDateReport = editionDateReport;
    }

    public TabWetProduct getTabWetProductForeign() {
        return tabWetProductForeign;
    }

    public void setTabWetProductForeign(TabWetProduct tabWetProductForeign) {
        this.tabWetProductForeign = tabWetProductForeign;
    }

    public String getCompInfo() {
        return compInfo;
    }

    public void setCompInfo(String compInfo) {
        this.compInfo = compInfo;
    }

    public TabWetBase getTabWetBaseForegin() {
        return tabWetBaseForegin;
    }

    public void setTabWetBaseForegin(TabWetBase tabWetBaseForegin) {
        this.tabWetBaseForegin = tabWetBaseForegin;
    }

    public TabUsers getTabUsersForegin() {
        return tabUsersForegin;
    }

    public void setTabUsersForegin(TabUsers tabUsersForegin) {
        this.tabUsersForegin = tabUsersForegin;
    }

    public int getPack() {
        return pack;
    }

    public void setPack(int pack) {
        this.pack = pack;
    }

    public double getOrder_quantity() {
        return order_quantity;
    }

    public void setOrder_quantity(double order_quantity) {
        this.order_quantity = order_quantity;
    }

    public double getOrder_realize() {
        return order_realize;
    }

    public void setOrder_realize(double order_realize) {
        this.order_realize = order_realize;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Status getStatusLabForegin() {
        return statusLabForegin;
    }

    public void setStatusLabForegin(Status statusLabForegin) {
        this.statusLabForegin = statusLabForegin;
    }

    public String getLabComment() {
        return labComment;
    }

    public void setLabComment(String labComment) {
        this.labComment = labComment;
    }

    public TabWetGoods getTabWetGoodsForegin() {
        return tabWetGoodsForegin;
    }

    public void setTabWetGoodsForegin(TabWetGoods tabWetGoodsForegin) {
        this.tabWetGoodsForegin = tabWetGoodsForegin;
    }

    public TabUsers getTabUsersEditForegin() {
        return tabUsersEditForegin;
    }

    public void setTabUsersEditForegin(TabUsers tabUsersEditForegin) {
        this.tabUsersEditForegin = tabUsersEditForegin;
    }
}
