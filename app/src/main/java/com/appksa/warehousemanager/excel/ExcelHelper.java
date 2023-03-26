package com.appksa.warehousemanager.excel;

import android.content.Context;

import com.appksa.warehousemanager.R;
import com.appksa.warehousemanager.model.DispatchEvent;
import com.appksa.warehousemanager.model.SupplyItem;
import com.appksa.warehousemanager.model.WarehouseState;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    private static final String FILE_NAME = "warehouse_positions_data.xlsx";
    public static String finalReport;
    XSSFRow row;

    public boolean externalExportXSSFPositionDataFile(Context context, List<SupplyItem> currentSupplyList){

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Warehouse state");

            //begin creation
            int rowInd = 0;
            XSSFRow row = spreadsheet.createRow(++rowInd);
            row.createCell(0).setCellValue("Название позиции");
            row.createCell(1).setCellValue("Дата прихода");
            row.createCell(2).setCellValue("Приход, кол-во");
            row.createCell(3).setCellValue("Расходник");
            row.createCell(4).setCellValue("Id заднего фона");
            row.createCell(5).setCellValue("Комментарий");
            row.createCell(7).setCellValue("- Поля позиций склада");
            row = spreadsheet.createRow(++rowInd);
            row.createCell(2).setCellValue("Контрагент");
            row.createCell(3).setCellValue("Дата отгрузки");
            row.createCell(4).setCellValue("Количество");
            row.createCell(5).setCellValue("Запланированная");
            row.createCell(7).setCellValue("- Поля отгрузок позиций");

            for(SupplyItem currentItem : currentSupplyList){
                row = spreadsheet.createRow(++rowInd);
                row.createCell(0).setCellValue(currentItem.getTitle());//"Название позиции"
                row.createCell(1).setCellValue(currentItem.getDate());//"Дата прихода"
                row.createCell(2).setCellValue(currentItem.getStartAmount());//"Приход, кол-во"
                if(currentItem.isConsumableMaterial()) {
                    row.createCell(3).setCellValue("+");//"Расходник"
                }
                row.createCell(4).setCellValue(currentItem.getBgColor());//"Id заднего фона"
                row.createCell(5).setCellValue(currentItem.getComment());//"Комментарий"
                List<DispatchEvent> currentDispatchList = currentItem.getDispatchEventsList();
                for(DispatchEvent currentEvent : currentDispatchList){
                    row = spreadsheet.createRow(++rowInd);
                    row.createCell(2).setCellValue(currentEvent.getContractor());//"Контрагент"
                    row.createCell(3).setCellValue(currentEvent.getDispatchDate());//"Дата отгрузки"
                    row.createCell(4).setCellValue(currentEvent.getAmount());//"Количество"
                    if(currentEvent.isPlaned()) {
                        row.createCell(5).setCellValue("+");//"Запланированная"
                    }
                }
            }
            //end creation

            File externalDir = new File(context.getExternalFilesDir(null), FILE_NAME);
            FileOutputStream fileOutputStream = new FileOutputStream(externalDir);

            workbook.write(fileOutputStream);

            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public WarehouseState externalImportXSSFDataFile(Context context){
        boolean incorrect = false;
        List<SupplyItem> currentSupplyList = new ArrayList<>();
        WarehouseState bufferWarehouseState = new WarehouseState(0L, currentSupplyList);
        StringBuilder report = new StringBuilder("");
        try {
            File externalDir = new File(context.getExternalFilesDir(null), FILE_NAME);
            FileInputStream fileInputStream = new FileInputStream(externalDir);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet spreadsheet = workbook.getSheetAt(0);
            System.out.println(spreadsheet.toString());

            SupplyItem currentItem = new SupplyItem();//пиздец
            DispatchEvent currentEvent;

            //begin creation
            Iterator<Row> rowIterator = spreadsheet.iterator();
            System.out.println(rowIterator.toString());
            Cell cell;
            String sBuf;
            int nBuf;
            rowIterator.next();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                row = (XSSFRow) rowIterator.next();
                System.out.println(row.toString());
                cell = row.getCell(0);
                if(cell == null){
                    List<DispatchEvent> currentEventsList = currentItem.getDispatchEventsList();
                    currentEvent = new DispatchEvent(bufferWarehouseState.getIdGen());

                    cell = row.getCell(2);
                    if(cell != null) {
                        if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            sBuf = cell.getStringCellValue().trim();
                            if (sBuf.length() >= 4) {
                                currentEvent.setContractor(sBuf);
                            } else {
                                report.append("Error: ").append(row.getRowNum() + 1).append(" строка, некорректное название контрагента.\n\n");
                                incorrect = true;
                            }
                        }else{
                            report.append("Error: ").append(row.getRowNum() + 1).append(" строка, некорректный тип ячейки названия контрагента. Необходимо текстовое значение.\n\n");
                            incorrect = true;
                        }
                    }else{
                        report.append("Error: ").append(row.getRowNum() + 1).append(" строка, пустая ячейка названия контрагента.\n\n");
                        incorrect = true;
                    }

                    cell = row.getCell(3);
                    if(cell != null) {
                        if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            sBuf = cell.getStringCellValue().trim();
                            if (sBuf.length() >= 8) {
                                currentEvent.setDispatchDate(sBuf);
                            } else {
                                report.append("Error: ").append(row.getRowNum() + 1).append(" строка, некорректная дата отгрузки.\n\n");
                                incorrect = true;
                            }
                        }else{
                            report.append("Error: ").append(row.getRowNum() + 1).append(" строка, некорректный тип ячейки даты отгрузки. Необходимо текстовое значение.\n\n");
                            incorrect = true;
                        }
                    }else{
                        report.append("Error: ").append(row.getRowNum() + 1).append(" строка, пустая ячейка даты отгрузки.\n\n");
                        incorrect = true;
                    }

                    cell = row.getCell(4);
                    if(cell != null) {
                        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            try {
                                nBuf = (int) cell.getNumericCellValue();
                                if (nBuf < 0) throw new NumberFormatException();
                                currentEvent.setAmount(nBuf);
                            } catch (Exception ex) {
                                report.append("Error: ").append(row.getRowNum() + 1).append(" строка, некорректное количество отгрузки.\n\n");
                                incorrect = true;
                            }
                        }else{
                            report.append("Error: ").append(row.getRowNum() + 1).append(" строка, некорректный тип ячейки Количество. Необходимо числовое значение.\n\n");
                            incorrect = true;
                        }
                    }else{
                        report.append("Error: ").append(row.getRowNum() + 1).append(" строка, пустая ячейка Количество.\n\n");
                        incorrect = true;
                    }

                    cell = row.getCell(5);
                    if(cell != null) {
                        if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            sBuf = cell.getStringCellValue().trim();
                            if (sBuf.equals("+")) {
                                currentEvent.setPlaned(true);
                            } else {
                                if (!sBuf.equals("")) {
                                    report.append("Info: ").append(row.getRowNum() + 1).append(" строка, не распознано значение в ячейке Запланированная, установлено значение по умолчанию - не запланированная отгрузка.\n\n");
                                }
                                currentEvent.setPlaned(false);
                            }
                        }else{
                            report.append("Info: ").append(row.getRowNum() + 1).append(" строка, не распознано значение в ячейке Запланированная, установлено значение по умолчанию - не запланированная отгрузка.\n\n");
                            currentEvent.setPlaned(false);
                        }
                    }else {
                        currentEvent.setPlaned(false);
                    }

                    currentEventsList.add(currentEvent);
                    currentItem.setCorrectRestAmounts();

                }else{
                    currentItem = new SupplyItem(bufferWarehouseState.getIdGen());
                    currentItem.setDispatchEventsList(new ArrayList<DispatchEvent>());

                    cell = row.getCell(0);
                    if(cell != null) {
                        if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            sBuf = cell.getStringCellValue().trim();
                            if (sBuf.length() >= 6) {
                                currentItem.setTitle(sBuf);
                            } else {
                                report.append("Error: ").append(row.getRowNum() + 1).append(" строка, некорректное название позиции.\n\n");
                                incorrect = true;
                            }
                        }else{
                            report.append("Error: ").append(row.getRowNum() + 1).append(" строка, некорректный тип ячейки названия позиции. Необходимо строковое значение.\n\n");
                            incorrect = true;
                        }
                    }else{
                        report.append("Error: ").append(row.getRowNum() + 1).append(" строка, пустая ячейка названия позиции.\n\n");
                        incorrect = true;
                    }

                    cell = row.getCell(1);
                    if(cell != null) {
                        if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            sBuf = cell.getStringCellValue().trim();
                            if (sBuf.length() >= 8) {
                                currentItem.setDate(sBuf);
                            } else {
                                report.append("Error: ").append(row.getRowNum() + 1).append(" строка, некорректная дата прихода.\n\n");
                                incorrect = true;
                            }
                        }else{
                            report.append("Error: ").append(row.getRowNum() + 1).append(" строка, некорректный тип ячейки даты прихода. Необходимо строковое значение.\n\n");
                            incorrect = true;
                        }
                    }else{
                        report.append("Error: ").append(row.getRowNum() + 1).append(" строка, пустая ячейка даты прихода.\n\n");
                        incorrect = true;
                    }


                    cell = row.getCell(2);
                    if(cell != null) {
                        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            try {
                                nBuf = (int) cell.getNumericCellValue();
                                if (nBuf < 0) throw new NumberFormatException();
                                currentItem.setStartAmount(nBuf);
                            } catch (Exception ex) {
                                report.append("Error: ").append(row.getRowNum() + 1).append(" строка, некорректное изначальное количество.\n\n");
                                incorrect = true;
                            }
                        }else{
                            report.append("Error: ").append(row.getRowNum() + 1).append(" строка, некорректный тип ячейки изначального количества. Необходимо числовое значение.\n\n");
                            incorrect = true;
                        }
                    }else{
                        report.append("Error: ").append(row.getRowNum() + 1).append(" строка, пустая ячейка изначального количества.\n\n");
                        incorrect = true;
                    }

                    cell = row.getCell(3);
                    if(cell != null) {
                        if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            sBuf = cell.getStringCellValue().trim();
                            if (sBuf.equals("+")) {
                                currentItem.setConsumableMaterial(true);
                            } else {
                                if (!sBuf.equals("")) {
                                    report.append("Info: ").append(row.getRowNum() + 1).append(" строка, не распознано значение в ячейке Расходник, установлено значение по умолчанию - не расходник.\n\n");
                                }
                                currentItem.setConsumableMaterial(false);
                            }
                        }else{
                            report.append("Info: ").append(row.getRowNum() + 1).append(" строка, не распознано значение в ячейке Расходник, установлено значение по умолчанию - не расходник.\n\n");
                            currentItem.setConsumableMaterial(false);
                        }
                    }else {
                        currentItem.setConsumableMaterial(false);
                    }

                    cell = row.getCell(4);
                    if(cell != null) {
                        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            try {
                                nBuf = (int) cell.getNumericCellValue();
                                if(nBuf == R.color.app_custom_background_light_grey ||
                                        nBuf == R.color.app_custom_background_grey ||
                                        nBuf == R.color.app_custom_background_red ||
                                        nBuf == R.color.app_custom_background_orange ||
                                        nBuf == R.color.app_custom_background_yellow ||
                                        nBuf == R.color.app_custom_background_green ||
                                        nBuf == R.color.app_custom_background_blue ||
                                        nBuf == R.color.app_custom_background_purple){
                                    currentItem.setBgColor(nBuf);
                                }else{
                                    throw new Exception();
                                }
                            } catch (Exception ex) {
                                report.append("Info: ").append(row.getRowNum() + 1).append(" строка, не распознано значение в ячейке Id заднего фона, установлено значение по умолчанию - светло-серый цвет.\n\n");
                                currentItem.setBgColor(R.color.app_custom_background_light_grey);
                            }
                        }else{
                            report.append("Info: ").append(row.getRowNum() + 1).append(" строка, не распознано значение в ячейке Id заднего фона, установлено значение по умолчанию - светло-серый цвет.\n\n");
                            currentItem.setBgColor(R.color.app_custom_background_light_grey);
                        }
                    }else{
                        report.append("Info: ").append(row.getRowNum() + 1).append(" строка, в ячейке Id заднего фона установлено значение по умолчанию - светло-серый цвет.\n\n");
                        currentItem.setBgColor(R.color.app_custom_background_light_grey);
                    }

                    cell = row.getCell(5);
                    if(cell != null) {
                        if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
                            sBuf = cell.getStringCellValue().trim();
                            currentItem.setComment(sBuf);
                        }else{
                            report.append("Error: ").append(row.getRowNum() + 1).append(" строка, некорректный тип ячейки комментария. Необходимо строковое значение.\n\n");
                            incorrect = true;
                        }
                    }else{
                        currentItem.setComment("");
                    }

                    currentSupplyList.add(currentItem);
                }
            }
            finalReport = report.toString();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            if(row == null){
                report.append("Fatal Error: Файл \"").append(FILE_NAME).append("\" не найден в директории по пути /Android/data/com.appksa.warehousemanager/files\n\n");
            }else {
                report.append("Fatal Error: ").append(row.getRowNum() + 1).append(" строка, выполнение загрузки принудительно остановлено из-за критической ошибки в коде. Пришлите этот отчет и exel файл с нынешним содержимым для решения проблемы.\n\n");
            }
            finalReport = report.toString();
            incorrect = true;
        }
        if(incorrect){
            return null;
        }else{
            bufferWarehouseState.setSupplyItemsList(currentSupplyList);//в теории он и так установлен
            return bufferWarehouseState;
        }
    }
}
