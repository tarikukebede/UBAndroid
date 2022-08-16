package et.com.act.unibillingandroid.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import et.com.act.unibillingandroid.Database.Converters.DateConverter;
import et.com.act.unibillingandroid.Database.Converters.StringListConverter;
import et.com.act.unibillingandroid.Models.Auth;
import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.Models.ReadingInfo;
import et.com.act.unibillingandroid.Util.Constants;


@Database(entities = { Auth.class, Meter.class, ReadingInfo.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, StringListConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract AppDao appDao();

    private static volatile AppDatabase instance;
    public static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(final Context context){
        if(instance == null){
            synchronized (AppDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, Constants.DATABASE_NAME)
                            .build();
                }
            }
        }

        return instance;
    }
}
