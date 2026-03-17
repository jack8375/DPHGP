package DPHGP;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WriteToExcel {

    public static void writeArrayToExcel(double[][] data, List<String> rowNames, List<String> columnNames) throws IOException {

        String path = "D:\\收敛图";
        String Path = path + ".xlsx";
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        // 创建工作表
        Sheet sheet = workbook.createSheet("DataSheet");

        // 写入列名
        Row firstRow = sheet.createRow(0);
        for (int i = 0; i < columnNames.size(); i++) {
            Cell cell = firstRow.createCell(i + 1);
            cell.setCellValue(columnNames.get(i));
        }

        // 写入行名和数据
        for (int i = 0; i < rowNames.size(); i++) {
            Row row = sheet.createRow(i + 1);
            // 写入行名
            Cell cell = row.createCell(0);
            cell.setCellValue(rowNames.get(i));

            // 写入数据
            for (int j = 0; j < data[i].length; j++) {
                cell = row.createCell(j + 1);
                cell.setCellValue(data[i][j]);
            }
        }

        // 将工作簿写入文件
        FileOutputStream out = new FileOutputStream(Path);
        workbook.write(out);
        out.close();
        System.out.println("Excel 文件生成成功！");

    }
}