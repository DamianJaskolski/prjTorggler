package torggler.utils.converters;

import torggler.modelFx.ProductFx;
import torggler.tables.TabWetProduct;

public class ConverterProduct {

    public static TabWetProduct convertToProduct (ProductFx productFx)
    {
        TabWetProduct tabWetProduct = new TabWetProduct();
        tabWetProduct.setIdWetProduct(productFx.getIdProductWET());
        tabWetProduct.setName(productFx.getName());
      //  tabWetProduct.setGrein_size(productFx.getGrein_size());
        return  tabWetProduct;
    }


    public static ProductFx converterToProductFX(TabWetProduct tabWetProduct){
        ProductFx productFx =  new ProductFx();
        productFx.setIdProductWET(tabWetProduct.getIdWetProduct());
        productFx.setName(tabWetProduct.getName());
       // productFx.setGrein_size(tabWetProduct.getGrein_size());
        return productFx;
    }

}
