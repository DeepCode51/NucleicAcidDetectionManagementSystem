package com.yuanlrc.base.util;

import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;

public class ExportExcelUtil
{
    //默认文件名
    private String fileName = "default.xlsx";

    private String sheetName = "sheet";

    private String[] headers;

    private HSSFWorkbook workbook = new HSSFWorkbook();

    private HSSFSheet sheet;

    private Integer row;

    public HSSFSheet create()
    {
        this.sheet = workbook.createSheet(sheetName);

        HSSFRow row = sheet.createRow(0);
        for (int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        return sheet;
    }

    public void export(HttpServletResponse response)throws Exception
    {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }
}
