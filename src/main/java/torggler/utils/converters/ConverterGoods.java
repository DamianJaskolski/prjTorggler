package torggler.utils.converters;


import torggler.modelFx.BaseFx;
import torggler.modelFx.GoodsProperty;
import torggler.tables.TabWetBase;
import torggler.tables.TabWetGoods;


public class ConverterGoods {


    public static TabWetGoods convetToGoods (GoodsProperty goodsProperty){
     TabWetGoods tabWetGoods = new TabWetGoods();
     tabWetGoods.setIdWetGoods(goodsProperty.getIdWetGoodsProperty());
     tabWetGoods.setName(goodsProperty.getNameWetGoodsProperty());
     return tabWetGoods;
    }

    public static GoodsProperty convertToGoodsProperty (TabWetGoods tabWetGoods){

        GoodsProperty goodsProperty = new GoodsProperty();
        goodsProperty.setIdWetGoodsProperty(tabWetGoods.getIdWetGoods());
        goodsProperty.setNameWetGoodsProperty(tabWetGoods.getName());
        return goodsProperty;
    }


}
