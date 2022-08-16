package et.com.act.unibillingandroid.Util;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import et.com.act.unibillingandroid.Activities.MainActivity;
import et.com.act.unibillingandroid.Network.Dto.MeterReadingDto;

public class FilesHelper {

    private static final String TAG = "FilesHelper";

    public static void exportToExcel(MainActivity activity, List<MeterReadingDto> requestDtoList){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();

        HSSFRow fistRow = sheet.createRow(0);
        for(int i =0; i < Constants.EXCEL_EXPORT_FORMAT.length; i ++){
            HSSFCell cell = fistRow.createCell(i);
            cell.setCellValue(Constants.EXCEL_EXPORT_FORMAT[i]);
        }

        for (int i=0; i < requestDtoList.size(); i++){
             MeterReadingDto meterReading =  requestDtoList.get(i);
             HSSFRow row = sheet.createRow(i + 1);
             row.createCell(0).setCellValue(meterReading.getMeterId());
             row.createCell(1).setCellValue(meterReading.getCurrentReading());
             row.createCell(2).setCellValue(meterReading.getReadOn());
             row.createCell(3).setCellValue(meterReading.getGps());
        }

        File filePath = new File(Environment.getExternalStoragePublicDirectory("Download"),  "Bill_Reading_"+ new Date() + ".xls");

        try{
            if(!filePath.exists()){
                boolean result = filePath.createNewFile();
                Log.d(TAG, "exportToExcel: File Created " + result);
            }

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            activity.updateLoadingScreen("Export complete!", "Successfully exported "+ requestDtoList.size(),0);


        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "exportToExcel: " + e.getMessage());
            //activity.updateLoadingScreen("Export error!", "Error encountered during export, Please try again ",0);
           // Toast.makeText(context, "Export Failed Due to " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
