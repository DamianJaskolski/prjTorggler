package torggler.utils.converters;


import torggler.modelFx.OrderFx;
import torggler.tables.Status;
import torggler.tables.TabWetReport;
import torggler.utils.Utils;

/**
 * Created by ZacznijProgramowac.
 * https://www.youtube.com/zacznijprogramowac
 * http://zacznijprogramowac.net/
 */
public class ConverterReport {

    public static TabWetReport convertToOrder (OrderFx orderFx){

        TabWetReport tabWetReport = new TabWetReport();
        //tabWetReport.setCreateDateReport(new Date ());
        //tabWetReport.setEditionDateReport(new Date());
        tabWetReport.setIdWetReport (orderFx.getId ()); //??
        tabWetReport.setCompInfo (orderFx.getCompInfo ());
        tabWetReport.setPack(orderFx.getPack ());
        tabWetReport.setOrder_quantity(orderFx.getOrder_quantity ());
        tabWetReport.setOrder_realize(orderFx.getOrder_realize ());
        tabWetReport.setComment(orderFx.getComment ());
        tabWetReport.setCreateDateReport (ConverterDate.convertToDate (orderFx.getCreate_date ()));
        tabWetReport.setEditionDateReport (ConverterDate.convertToDate (orderFx.getEdit_date ()));
      // tabWetReport.setCreateDateReport (Utils.convertToDate(orderFx.getEdit_date ()));
        tabWetReport.setLabComment (orderFx.getLabcomment ());
        return tabWetReport;
    }

   /* public static BookFx convertToBookFx(Book book){
        BookFx bookFx = new BookFx();
        bookFx.setId(book.getId());
        bookFx.setTitle(book.getTitle());
        bookFx.setDescription(book.getDescription());
        bookFx.setRating(book.getRating());
        bookFx.setIsbn(book.getIsbn());
        bookFx.setReleaseDate(Utils.convertToLocalDate(book.getReleaseDate()));
        bookFx.setAuthorFx(ConverterAuthor.convertToAuthorFx(book.getAuthor()));
        bookFx.setCategoryFx(ConverterCategory.convertToCategoryFx(book.getCategory()));
        return bookFx;

        */
   public static OrderFx convertToOrderFX (TabWetReport tabWetReport){
       OrderFx orderFx = new OrderFx ();
       orderFx.setId (tabWetReport.getIdWetReport ());
       //stworz convertToProductFx w ProductFx orderFx.setProductFx ();
       //bookFx.setAuthorFx(ConverterAuthor.convertToAuthorFx(book.getAuthor()));

     //  orderFx.setProductFx (ConverterProduct.converterToProductFX (tabWetReport.getTabWetProductForeign ()));
       orderFx.setGoodsProperty(ConverterGoods.convertToGoodsProperty(tabWetReport.getTabWetGoodsForegin()));
       orderFx.setBaseFx(ConverterBase.convertToBaseFx(tabWetReport.getTabWetBaseForegin()));
       orderFx.setPack (tabWetReport.getPack ());
       orderFx.setCompInfo (tabWetReport.getCompInfo ());
       orderFx.setOrder_quantity (tabWetReport.getOrder_quantity ());
       orderFx.setOrder_realize (tabWetReport.getOrder_realize ());
       orderFx.setComment (tabWetReport.getComment ());
       orderFx.setUserFxCreate (ConverterUser.convertToUserFx (tabWetReport.getTabUsersForegin ()));
       orderFx.setUserFxEdit (ConverterUser.convertToUserFx (tabWetReport.getTabUsersEditForegin ()));
      // orderFx.setCreate_date (ConverterDate.convertToLocalDate (tabWetReport.getCreateDateReport ())) ;
       orderFx.setLabcomment (tabWetReport.getLabComment ());
       orderFx.setStatusFx((ConverterStatus.convertToLabFX(tabWetReport.getStatusLabForegin())));
       orderFx.setCreate_date (ConverterDate.convertToLocalDate (tabWetReport.getCreateDateReport ()));
       orderFx.setEdit_date  (ConverterDate.convertToLocalDate (tabWetReport.getEditionDateReport ()));
       //user
       //orderFx.setCreate_date (ConverterDate.convertToLocalDate (tabWetReport.getEditionDateReport ()));
        //user
       return orderFx;
    }




}
