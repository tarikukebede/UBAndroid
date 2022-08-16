package et.com.act.unibillingandroid.Util;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.Services.LocationTrackService;

public class MapHelper {
    private static final String TAG = "MapHelper";

    public static List<Meter> getNearByMeters(List<Meter> meters, Double readerLat, Double readerLong){
        List<Meter> cleanedMeters = cleanMeterData(meters);
        List<Meter> nearByMeters = new ArrayList<>();
        for(Meter meter : cleanedMeters){
            float[] distance = new float[1];
            double meterLat = Long.valueOf(meter.getMeterLat(), 10) / Math.pow(10, 6);
            double meterLon = Long.valueOf(meter.getMeterLong(), 10) / Math.pow(10, 6);
            Location.distanceBetween(readerLat, readerLong, meterLat, meterLon, distance);

            Log.d(TAG, "getNearByMeters: Calculated distance " + distance[0] + " Meters");
            if(distance[0] <= Constants.VISIBLE_METER_DISTANCE){
                nearByMeters.add(meter);
            }
        }

        return nearByMeters;
    }

    public static List<Meter> getNearByMeters(List<Meter> meters, Location location){
        if(location != null){
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            return getNearByMeters(meters, latitude, longitude);
        }else{
            Log.d(TAG, "getNearByMeters: location is null");
            return null;
        }
    }


    public static List<Meter> cleanMeterData(List<Meter> meters){

        List<Meter> cleanedMeters = new ArrayList<>();
        List<Meter> metersWithFaultyCoordinate = new ArrayList<>();

        for(Meter meter : meters){
            Log.d(TAG, "cleanMeterData: " + new GsonBuilder().create().toJson(meter));
            String meterLat = meter.getMeterLat();
            String meterLon = meter.getMeterLong();

            boolean hasNoHealthyLat = meterLat == null || meterLat.matches("NULL") || meterLat.matches("") || meterLat.matches("0");
            boolean hasNoHealthyLon = meterLon == null || meterLon.matches("NULL") || meterLat.matches("") || meterLat.matches("0");

            if(hasNoHealthyLat){
                continue;
            }

            if(hasNoHealthyLon){
                continue;
            }

            if(meterLon.contains(".")){
                int index = meterLon.indexOf(".");
                meterLon = meterLon.substring(0, index);
                meter.setMeterLong(meterLon);
            }

            if(meterLat.contains(".")){
                int index = meterLat.indexOf(".");
                meterLat = meterLat.substring(0, index);
                meter.setMeterLat(meterLat);
            }

            cleanedMeters.add(meter);
        }


        return cleanedMeters;
    }

    public static double cleanCo(String co){
        return Long.valueOf(co, 10) / Math.pow(10, 6);
    }

    public static Map<String, List<Meter>> separateMeterDataForSearchResult(List<Meter> meters){
        Map<String, List<Meter>> result = new HashMap<>();
        List<Meter> cleanMeters = cleanMeterData(meters);
        List<Meter> uncleanMeters = findMetersWithFaultyCoordinates(meters);

        result.put(Constants.METERS_WITH_CLEAN_COORDINATES, cleanMeters);
        result.put(Constants.METERS_WITH_UNCLEAN_COORDINATES, uncleanMeters);
        return result;
    }

    public static List<Meter> findMetersWithFaultyCoordinates(List<Meter> meters){
        List<Meter> metersWithFaultyCoordinates = new ArrayList<>();
        for(Meter meter : meters){
            String meterLat = meter.getMeterLat();
            String meterLon = meter.getMeterLong();

            boolean hasNoHealthyLat = meterLat == null || meterLat.matches("NULL") || meterLat.matches("") || meterLat.matches("0");
            boolean hasNoHealthyLon = meterLon == null || meterLon.matches("NULL") || meterLat.matches("") || meterLon.matches("0");

            if(hasNoHealthyLat || hasNoHealthyLon) {
                metersWithFaultyCoordinates.add(meter);
            }

        }

        return metersWithFaultyCoordinates;
    }

    public static List<Meter> sampleMeters(List<Meter> meters){
        return meters.subList(0, 10);
    }
}
