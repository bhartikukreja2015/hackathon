package abc;
import java.io.FileInputStream;
import java.io.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; //New imports to read XLSX format
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet; //New imports to read XLSX format

//import org.apache.poi.ss.usermodel.*;
import java.util.Iterator;
public class tryTest {  
        public static void main(String[] args) throws Exception{
             FileInputStream input_document = new FileInputStream(new File("D://Hackathon-SQL.xlsx")); //Read XLSX document - Office 2007, 2010 format     
         //File input_document= new File ("D://Hackathon-SQL.xlsx");
             OPCPackage pkg = OPCPackage.open("D://Hackathon-SQL.xlsx");
             //XSSFWorkbook  my_xlsx_workbook = new XSSFWorkbook(pkg);
        XSSFWorkbook my_xlsx_workbook =  new XSSFWorkbook(input_document); //Read the Excel Workbook in a instance object    
                XSSFSheet my_worksheet = my_xlsx_workbook.getSheet("Sheet1"); //This will read the sheet for us into another object
                Iterator<Row> rowIterator = my_worksheet.iterator(); // Create iterator object
                while(rowIterator.hasNext()) {
                        Row row = rowIterator.next(); //Read Rows from Excel document       
                        Iterator<Cell> cellIterator = row.cellIterator();//Read every column for every row that is READ
                                while(cellIterator.hasNext()) {
                                        Cell cell = cellIterator.next(); //Fetch CELL
                                        switch(cell.getCellType()) { //Identify CELL type
                                        case Cell.CELL_TYPE_NUMERIC:
                                                System.out.print(cell.getNumericCellValue() + "\t\t"); //print numeric value
                                                break;
                                        case Cell.CELL_TYPE_STRING:
                                                System.out.print(cell.getStringCellValue() + "\t\t"); //print string value
                                                break;
                                        }
                                }
                System.out.println(""); // To iterate over to the next row
                }
               input_document.close(); //Close the XLS file opened for printing
               my_xlsx_workbook.close();
        }
}
