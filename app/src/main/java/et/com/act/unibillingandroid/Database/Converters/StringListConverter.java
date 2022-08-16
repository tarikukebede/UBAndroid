package et.com.act.unibillingandroid.Database.Converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StringListConverter {
    @TypeConverter
    public static List<String> fromString(String value){
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String toArrayList(List<String> value){
        return new Gson().toJson(value);
    }
}
