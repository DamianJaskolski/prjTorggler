package torggler;



import torggler.modelFx.OrderFx;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PrintCsv {


  public void printToCsv(List<OrderFx> orderFxList) throws IOException {
    StringBuilder csv = getHeader().append(getContent(orderFxList));
    saveToFile(csv);
  }

  private StringBuilder getContent(List<OrderFx> orderFxList) {
    StringBuilder sb = new StringBuilder();
    orderFxList.forEach(p -> {
      sb.append(p.getGoodsProperty().toString()).append(';');
      sb.append(p.getCompInfo()).append(';');
    //  sb.append(p.baseFxProperty()).append(';');
      sb.append(p.getPack()).append(';');
      sb.append(p.getOrder_quantity()).append(';');
      sb.append(p.getCommissionDate()).append("\n");
    });
    return sb;
  }

  private StringBuilder getHeader() {
    StringBuilder sb = new StringBuilder();
    sb.append("Produkt").append(';');
    sb.append("Info dodatkowe").append(';');
   // sb.append("Baza").append(';');
    sb.append("Opakowanie").append(';');
    sb.append("Ilość").append(';');
    sb.append("Zlecenie na dzień").append("\n");
    return sb;
  }

  private void saveToFile(StringBuilder csv) throws IOException {
    Path textFile = Paths.get("C:/TORGGLER/tabelka.csv");
    BufferedWriter writer = Files.newBufferedWriter(textFile, StandardCharsets.UTF_8);
    writer.write(csv.toString());
    DialogsUtils.dialogExportCSVApplication();
    writer.close();
  }


}
