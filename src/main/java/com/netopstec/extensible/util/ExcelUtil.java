package com.netopstec.extensible.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author zhenye 2019/4/16
 */
@Slf4j
public class ExcelUtil {

    private final static String COMMA = ".";
    private final static String EXCEL_TYPE_XSL = ".xsl";
    private final static String EXCEL_TYPE_XLSX = ".xlsx";
    private final static String DEFAULT_SHEET_NAME = "sheet1";

    /**
     * 读取excel文件的整个工作簿，返回该工作簿按顺序排列的表格数据的集合
     * @param fileAbsolutePath 文件的全路径名称
     * @return 该工作簿按顺序排列的表格数据的集合（key---表格名称，value---表格数据）
     */
    public static LinkedHashMap<String, List<List<String>>> readWorkbook(String fileAbsolutePath) throws Exception{
        File file = new File(fileAbsolutePath);
        if(!file.exists()) {
            throw new RuntimeException("The file cannot be found.");
        }
        try(
                InputStream is = new FileInputStream(file);
                Workbook workbook = isExcel(file, is)
        ){
            LinkedHashMap <String, List<List<String>>> thisWorkbookValueMap = new LinkedHashMap<>();
            // 测算该excel工作簿有多少个表格
            int sheetSize = workbook.getNumberOfSheets();
            for(int i = 0; i < sheetSize; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                List<List<String>> thisSheetRowValueList = readSheet(sheet);
                String key = sheet.getSheetName();
                thisWorkbookValueMap.put(key, thisSheetRowValueList);
            }
            return thisWorkbookValueMap;
        }
    }

    /**
     * 判断该文件的excel格式，并返回该格式的excel文件对应的工作簿对象workbook
     * @param file 待判定的excel文件
     * @param is 包含该excel文件的输入流
     * @return 该excel文件对应的工作簿对象workbook
     * @throws Exception
     */
    private static Workbook isExcel(File file, InputStream is) throws Exception {
        String filenameExtension = getFilenameExtension(file);
        Workbook wb;
        switch (filenameExtension) {
            case EXCEL_TYPE_XSL:
                wb = new HSSFWorkbook(is);
                break;
            case EXCEL_TYPE_XLSX:
                wb = new XSSFWorkbook(is);
                break;
            default:
                throw new RuntimeException("The type of the file[" + file.getName() + "] is wrong.");
        }
        return wb;
    }

    /**
     * 获取文件扩展名
     * @param file 文件
     * @return 文件扩展名
     */
    private static String getFilenameExtension(File file) {
        String filePath = file.getPath();
        return filePath.substring(filePath.lastIndexOf(COMMA));
    }

    /**
     * 读取excel的表格数据，并返回该表格按顺序排列的行数据的集合
     * @param sheet 待读取表格
     * @return 该行按顺序排列的行数据的集合
     */
    private static List<List<String>> readSheet(Sheet sheet) {
        if (sheet == null) {
            return null;
        }
        List<List<String>> thisSheetRowValueList = new ArrayList<>();
        int rowSize = sheet.getLastRowNum();
        for(int j = 0; j < rowSize; j++) {
            Row row = sheet.getRow(j);
            List<String> thisRowCellValueList = readRow(row);
            thisSheetRowValueList.add(thisRowCellValueList);
        }
        // 当某一行中的所有单元格数据为空时，判定这一行为无效数据行，这样的行数据需要过滤掉
        thisSheetRowValueList = thisSheetRowValueList.stream()
                .filter(rowValueList -> rowValueList.stream().anyMatch(cell -> cell != null && !"".equals(cell)))
                .collect(Collectors.toList());
        return thisSheetRowValueList;
    }


    /**
     * 读取excel的行数据，并返回该行按顺序排列的单元格数据的集合
     * @param row 待读取行
     * @return 该行按顺序排列的单元格数据的集合
     */
    private static List<String> readRow(Row row) {
        if (row == null) {
            return null;
        }
        List<String> thisRowCellValueList = new ArrayList<>();
        int cellSize = row.getLastCellNum();
        for(int k = 0; k < cellSize; k++) {
            Cell cell = row.getCell(k);
            String cellValue = readCell(cell);
            thisRowCellValueList.add(cellValue);
        }
        return thisRowCellValueList;
    }

    /**
     * 读取某一个单元格，并以字符串的形式返回其内容
     * @param cell 待读取单元格
     * @return 单元格内容
     */
    private static String readCell(Cell cell) {
        if(cell == null) {
            return null;
        }
        String cellValue;
        try {
            CellType cellTypeEnum = cell.getCellTypeEnum();
            switch(cellTypeEnum) {
                case BOOLEAN:
                    cellValue = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case NUMERIC:
                case FORMULA:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case _NONE :
                case BLANK:
                case ERROR:
                default:
                    cellValue = "";
            }
            return cellValue;
        } catch (Exception e) {
            String sheetName = cell.getSheet().getSheetName();
            int rowNum = cell.getRowIndex() + 1;
            int columnNum = cell.getColumnIndex() + 1;
            log.warn("error occurred in read the value of the cell. the position of this cell is: " + sheetName  + "[" + rowNum + "," + columnNum + "]");
        }
        return null;
    }

    /**
     * 读取excel文件工作簿中的第一个表格的内容，返回该表格按顺序排列的行数据的集合
     * @param fileAbsolutePath 文件的全路径名称
     * @return 该工作簿第一个表格按顺序排列的行数据的集合
     */
    public static List<List<String>> readFirstSheet(String fileAbsolutePath) throws Exception{
        File file = new File(fileAbsolutePath);
        if(!file.exists()) {
            throw new RuntimeException("The file cannot be found.");
        }
        try(
                InputStream is = new FileInputStream(file);
                Workbook workbook = isExcel(file, is)
        ){
            Sheet sheet = workbook.getSheetAt(0);
            return readSheet(sheet);
        }
    }

    /**
     * 根据工作簿结构的数据，在指定位置生成excel文件
     * @param excelData excel数据，其数据结构为`LinkedHashMap<String, List<List<String>>>`。
     *                  key：为表名（非空）。value: 表数据，可以看成行列组成的二维数组。
     * @param filePath 要生成的excel所在路径
     * @param fileName 要生成的excel文件名称
     * @param fileType 要生成的excel文件类型，默认是".xlsx"
     */
    public static void writeWorkbook(LinkedHashMap<String, List<List<String>>> excelData, String filePath, String fileName, String fileType) throws Exception {
        Workbook workbook;
        String fileAbsolutePath;
        // 2007版本之前默认的excel格式是".xsl"，这种格式对应的工作簿对象是"HSSFWorkbook"
        // 2007及之后版本默认的excel格式是".xlsx"，这种格式对应的工作簿对象是"XSSFWorkbook"
        switch (fileType) {
            case EXCEL_TYPE_XSL:
                workbook = new HSSFWorkbook();
                fileAbsolutePath = filePath + fileName + EXCEL_TYPE_XSL;
                break;
            case EXCEL_TYPE_XLSX:
            default:
                workbook = new XSSFWorkbook();
                fileAbsolutePath = filePath + fileName + EXCEL_TYPE_XLSX;
        }
        // 将excel的数据封装进workbook
        if (excelData != null && excelData.size() != 0) {
            excelData.forEach((String key, List<List<String>> value) -> {
                Sheet sheet = workbook.createSheet(key);
                for(int i = 0; i < value.size(); i++) {
                    Row row = sheet.createRow(i);
                    List<String> rowDataList = value.get(i);
                    for(int j = 0; j < rowDataList.size(); j++) {
                        Cell cell = row.createCell(j);
                        cell.setCellValue(rowDataList.get(j));
                    }
                }
            });
        } else {
            workbook.createSheet(DEFAULT_SHEET_NAME);
        }
        // 创建excel文件，并将数据写进该文件中
        File file = new File(fileAbsolutePath);
        if (file.exists()){
            file.delete();
        }
        file.createNewFile();
        OutputStream os = new FileOutputStream(file);
        workbook.write(os);
        workbook.close();
        os.close();
    }
}
