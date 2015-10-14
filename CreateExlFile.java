package abc;

import  java.io.*;
import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import  org.apache.poi.hssf.usermodel.HSSFRow;
import  org.apache.poi.hssf.usermodel.HSSFCell;

public class CreateExlFile{
    public static void main(String[]args) {
        try {
            String filename = "D:/hackathon.xls" ;
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");  

            HSSFRow rowStartPointer = sheet.createRow((short)0);
            rowStartPointer.createCell((short)0).setCellValue("first");
            rowStartPointer.createCell((short)1).setCellValue("second");
            rowStartPointer.createCell((short)2).setCellValue("third");
            rowStartPointer.createCell((short)3).setCellValue("fourth");

            HSSFRow rowVal = sheet.createRow((short)1);
            rowVal.createCell((short)0).setCellValue("1wd");
            rowVal.createCell((short)1).setCellValue("bac");
            rowVal.createCell((short)2).setCellValue("ef");
            rowVal.createCell((short)3).setCellValue("ewff");

            FileOutputStream fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("success generation");

        } catch ( Exception ex ) {
            System.out.println(ex);
        }
    }
}
