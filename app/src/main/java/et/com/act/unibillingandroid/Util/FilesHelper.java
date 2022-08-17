package et.com.act.unibillingandroid.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

import et.com.act.unibillingandroid.Activities.MainActivity;
import et.com.act.unibillingandroid.Network.Dto.MeterReadingDto;

public class FilesHelper {

    private static final String TAG = "FilesHelper";

    public static void exportToExcelLegacy(MainActivity activity, List<MeterReadingDto> requestDtoList) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        HSSFRow fistRow = sheet.createRow(0);
        for (int i = 0; i < Constants.EXCEL_EXPORT_FORMAT.length; i++) {
            HSSFCell cell = fistRow.createCell(i);
            cell.setCellValue(Constants.EXCEL_EXPORT_FORMAT[i]);
        }

        for (int i = 0; i < requestDtoList.size(); i++) {
            MeterReadingDto meterReading = requestDtoList.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(meterReading.getMeterId());
            row.createCell(1).setCellValue(meterReading.getCurrentReading());
            row.createCell(2).setCellValue(meterReading.getReadOn());
            row.createCell(3).setCellValue(meterReading.getGps());
        }

            File filePath = new File(Environment.getExternalStoragePublicDirectory("Download"), "Bill_Reading_" + new Date() + ".xls");
            try {
                if (!filePath.exists()) {
                    boolean result = filePath.createNewFile();
                    Log.d(TAG, "exportToExcel: File Created " + result);
                }

                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                workbook.write(fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                activity.updateLoadingScreen("Export complete!", "Successfully exported " + requestDtoList.size(), 0);


            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "exportToExcel: " + e.getMessage());
                //activity.updateLoadingScreen("Export error!", "Error encountered during export, Please try again ",0);
                // Toast.makeText(context, "Export Failed Due to " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
    }


    public static void exportToExcel(MainActivity activity, List<MeterReadingDto> requestDtoList, Uri uri) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        HSSFRow fistRow = sheet.createRow(0);
        for (int i = 0; i < Constants.EXCEL_EXPORT_FORMAT.length; i++) {
            HSSFCell cell = fistRow.createCell(i);
            cell.setCellValue(Constants.EXCEL_EXPORT_FORMAT[i]);
        }

//        for (int i = 0; i < requestDtoList.size(); i++) {
//            MeterReadingDto meterReading = requestDtoList.get(i);
//            HSSFRow row = sheet.createRow(i + 1);
//            row.createCell(0).setCellValue(meterReading.getMeterId());
//            row.createCell(1).setCellValue(meterReading.getCurrentReading());
//            row.createCell(2).setCellValue(meterReading.getReadOn());
//            row.createCell(3).setCellValue(meterReading.getGps());
//        }

//        MeterReadingDto meterReading = requestDtoList.get(0);
        HSSFRow row = sheet.createRow(1);
        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("Age");
        row.createCell(2).setCellValue("Gender");
        row.createCell(3).setCellValue("DOB");

        try {
            Log.i(TAG, "exportToExcel: " + uri);
//            InputStream inputStream = activity.getContentResolver().openInputStream(uri);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder stringBuilder = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            reader.close();
//            inputStream.close();
//            stringBuilder.append("\nHello World");
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(activity.getContentResolver().openOutputStream(uri));
//            outputStreamWriter.write(stringBuilder.toString());
//            outputStreamWriter.close();

            FileOutputStream fileOutputStream =  activity.openFileOutput("test.txt", Context.MODE_PRIVATE);
//            FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(uri));
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            String contents = "";
            try {
                FileInputStream fin = activity.openFileInput("test.txt");
                int i;
                while ((i = fin.read()) != -1) {
                    contents = contents + (char) i;
                }
            }
            catch (IOException e) {
                Log.e(TAG, "exportToExcel: ", e);
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(activity.getContentResolver().openOutputStream(uri));
            outputStreamWriter.write(contents);
            outputStreamWriter.close();

            activity.updateLoadingScreen("Export complete!", "Successfully exported " + requestDtoList.size(), 0);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "exportToExcel: " + e.getMessage());
            //activity.updateLoadingScreen("Export error!", "Error encountered during export, Please try again ",0);
            // Toast.makeText(context, "Export Failed Due to " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }



}
