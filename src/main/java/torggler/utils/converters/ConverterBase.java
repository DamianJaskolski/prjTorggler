package torggler.utils.converters;


import torggler.modelFx.BaseFx;
import torggler.tables.TabWetBase;


public class ConverterBase {

    public static BaseFx convertToBaseFx(TabWetBase tabWetBase){
        BaseFx baseFx = new BaseFx ();
        baseFx.setId(tabWetBase.getIdWetBase ());
        baseFx.setName(tabWetBase.getName());
        return baseFx;
    }
}
