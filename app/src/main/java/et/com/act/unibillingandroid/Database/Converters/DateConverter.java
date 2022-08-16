package et.com.act.unibillingandroid.Database.Converters;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long fromDate(Date value){
        return value == null ? null : value.getTime();
    }
}
