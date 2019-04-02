package com.zwh.manage.util;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 */
public class FileUtil {


    /**
     * 导出excel
     * @param dataList
     * @param fileName
     * @param response
     */
    public static void exportExcel(List<List<String>> dataList, String fileName,
                                   HttpServletResponse response) throws IOException {
        // 生成单元格
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet1");
        // 轮询数据列表并写入excel
        IntStream.range(0, dataList.size()).forEach(i -> {
            HSSFRow row = sheet.createRow(i);
            List<String> rowData = dataList.get(i);
            // 写入一行数据
            IntStream.range(0, rowData.size()).forEach(j -> {
                row.createCell(j).setCellValue(rowData.get(j));
            });
        });
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename="
                + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

}
