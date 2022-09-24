
import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClientLog {
   List<Integer> products = new ArrayList<>();
   List<Integer> amounts = new ArrayList<>();
   // метод сохранения покупок
   public void log(int productNum, int amount) {
      products.add(productNum);
      amounts.add(amount);
   }

   // метод сохранения всех действий в формате csv

   public void exportAsCSV(File txtFile) {
      boolean writeHeader = !txtFile.exists();
      try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true), ',', CSVWriter.NO_QUOTE_CHARACTER)) {
         if(writeHeader) {
            String [] heading = "productNum,amount".split(",");
            writer.writeNext(heading);
         }
         for (int i = 0; i < products.size(); i++) {
            String s = (products.get(i)+1) + "," + amounts.get(i);
            writer.writeNext(s.split(","));
         }
      } catch (IOException e) {
         e.printStackTrace();

      }
   }

}
