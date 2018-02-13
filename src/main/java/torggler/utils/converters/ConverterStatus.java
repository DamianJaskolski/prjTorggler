package torggler.utils.converters;


import torggler.modelFx.LabFx;
import torggler.tables.Status;


public class ConverterStatus {


    public static LabFx convertToLabFX(Status status){
        LabFx labFx = new LabFx ();
        labFx.setIdStatus (status.getIdStatus ());
        labFx.setStatus (status.getStatus ());
        labFx.setS1 (status.getS1 ());
        labFx.setI1 (status.getI1 ());
        //labFx.setCreate_date (Utils.convertToDate(labFx.getCreate_date ()));
        return labFx;
    }

}