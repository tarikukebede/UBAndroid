package et.com.act.unibillingandroid.Database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import et.com.act.unibillingandroid.Models.Auth;
import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.Models.ReadingInfo;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAuth(Auth auth);

    @Query("SELECT * FROM auth_table")
    LiveData<Auth> getAuth();

    @Query("DELETE FROM auth_table")
    void deleteAuth();


    ////////////////////////

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   Long insertMeter(Meter meter);

   @Update
   void updateMeter(Meter meter);

   @Query("SELECT * FROM meter_table WHERE id =:id ORDER BY id DESC")
   LiveData<Meter> getMeterById(int id);

   @Query("SELECT * FROM meter_table WHERE contractNumber LIKE :contractNumber AND  isRead = :readingStatus ORDER BY contractNumber DESC")
   List<Meter> getMeterByContractNo(String contractNumber, String readingStatus);


   @Query("SELECT * FROM meter_table")
   LiveData<List<Meter>> getAllMeters();

   @Query("SELECT * FROM meter_table WHERE isRead =:readingStatus")
   List<Meter> getMetersByReadingStatus(String readingStatus);

   @Query("SELECT * FROM meter_table WHERE isRead = '0'")
   List<Meter> getAllUnreadMeters();

   @Query("SELECT * FROM meter_table WHERE isRead = '1'")
   LiveData<List<Meter>> getAllReadMeters();

   @Query("SELECT COUNT(isRead) FROM meter_table WHERE isRead = '1'")
   LiveData<Integer> getReadMeterCount();

   @Query("SELECT COUNT(isRead) FROM meter_table WHERE isRead = '0'")
   LiveData<Integer> getUnreadMeterCount();

   @Query("DELETE FROM meter_table WHERE meterId =:meterId")
   void deleteMeter(int meterId);

   @Delete
   void deleteMeter(Meter meter);

   @Query("DELETE FROM meter_table")
   void deleteAllMeters();

    ////////////////////////

   @Insert
   void insertReadingInfo(ReadingInfo readingInfo);

   @Query("SELECT * FROM reading_info_table")
   LiveData<ReadingInfo> getReadingInfo();

   @Query("SELECT * FROM reading_info_table ORDER BY id")
   LiveData<List<ReadingInfo>> getAllReadingInfo();

   @Delete
   void deleteReadingInfo(ReadingInfo readingInfo);

   @Query("DELETE FROM reading_info_table")
   void deleteReadingInfo();
}
