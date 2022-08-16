package et.com.act.unibillingandroid.Util;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import et.com.act.unibillingandroid.Models.Auth;
import et.com.act.unibillingandroid.Models.Meter;
import et.com.act.unibillingandroid.Models.ReadingInfo;
import et.com.act.unibillingandroid.Network.Dto.ConsumptionbillchargepaymentviewModel;
import et.com.act.unibillingandroid.Network.Dto.MeterLocationDto;
import et.com.act.unibillingandroid.Network.Dto.MeterReadingDto;
import et.com.act.unibillingandroid.Network.Dto.ReaderInfo;
import et.com.act.unibillingandroid.Network.Dto.SignInResponseDto;

public class Mapper {

    private static final String TAG = "Mapper";

    public static Auth mapToMeter(SignInResponseDto responseDto){
            Auth auth = new Auth();
            auth.setToken(responseDto.getToken());
            auth.setAuthorities(responseDto.getAuthorities());
            auth.setPartyId(responseDto.getPartyId());
            auth.setName(responseDto.getName());
            auth.setStatus(responseDto.isStatus());
            return auth;
    }


    public static String toJson(Object object){
        return new Gson().toJson(object);
    }

    public static Object fromJson(String value, Type type){
        return new Gson().fromJson(value, type);
    }


    public static List<Meter> mapToMeter(List<MeterLocationDto> meterLocationDtos){
        Log.d(TAG, "mapToMeter: " + meterLocationDtos.size());
        List<Meter> meters = new ArrayList<>();
        for(MeterLocationDto meterLocationDto : meterLocationDtos){
            ConsumptionbillchargepaymentviewModel additionalInfo =
                    meterLocationDto.getConsumptionbillchargepaymentviewModel();

              Meter meter = new Meter();
              meter.setMeterId(meterLocationDto.getMeterId());
              meter.setSerialNo(meterLocationDto.getSerialNo());
              meter.setContractNumber(meterLocationDto.getContractNumber());
              meter.setMeterLat(meterLocationDto.getMeterLat());
              meter.setMeterLong(meterLocationDto.getMeterLong());
              meter.setCustomerName(meterLocationDto.getCustomerName());
              meter.setReadingTimePeriod(meterLocationDto.getReadingTimePeriod());
              meter.setReadOn(meterLocationDto.getReadOn());
              meter.setWereda(meterLocationDto.getWereda());
              meter.setHouseNumber(meterLocationDto.getHouseNumber());
              meter.setPhonenumber(meterLocationDto.getPhonenumber());
              meter.setKebele(meterLocationDto.getKebele());
              meter.setSubCity(meterLocationDto.getSubcity());
              meter.setCurrentReading(meterLocationDto.getCurrentReading());
              meter.setPreviousReading(meterLocationDto.getPreviousReading());
              meter.setReadingStatus(meterLocationDto.getReadingStatus());
              meter.setAddressId(meterLocationDto.getAddressId());

              if(additionalInfo != null){
                  meter.setTotalamount(String.valueOf(additionalInfo.getTotalamount()));
                  meter.setCustomerMeter(additionalInfo.getCustomerMeter());
                  meter.setContractid(additionalInfo.getContractid());
                  meter.setBillid(additionalInfo.getBillid());
                  meter.setConsumption(String.valueOf(additionalInfo.getConsumption()));
                  meter.setBillchargename(additionalInfo.getBillchargename());
                  meter.setBillcharge(String.valueOf(additionalInfo.getBillcharge()));
                  meter.setBillchargestatus(additionalInfo.getBillchargestatus());
                  meter.setBillPeriodId(additionalInfo.getBillPeriodId());
                  meter.setDescription(additionalInfo.getDescription());
                  meter.setPenality(String.valueOf(additionalInfo.getPenality()));
                  meter.setRegisteredat(additionalInfo.getRegisteredat());
              }

              meter.setIsRead(Constants.FALSE_STRING);
              meters.add(meter);
        }
        Log.d(TAG, "converted Meters: " + meters.size());
        return meters;
    }

    public static List<ReadingInfo> mapToReadingInfo(List<ReaderInfo> readerInfoList){
        List<ReadingInfo> readingInfoList = new ArrayList<>();
        for(ReaderInfo readerInfo : readerInfoList){
            ReadingInfo readingInfo = new ReadingInfo();
            readingInfo.setSubCity(readerInfo.getSubCity());
            readingInfo.setWereda(readerInfo.getWereda());
            readingInfo.setKebele(readerInfo.getKebele());
            readingInfo.setAddressId(readerInfo.getAddressId());
            readingInfo.setLocationGroupId(readerInfo.getLocationGroupId());
            readingInfo.setLocationGroupName(readerInfo.getLocationGroupName());
            readingInfo.setLocationGroupDescription(readerInfo.getLocationGroupDescription());
            readingInfoList.add(readingInfo);
        }
        return readingInfoList;
    }

    public static List<MeterReadingDto> mapToReadingRequestDto(List<Meter> meters){
        List<MeterReadingDto> requestDtoList = new ArrayList<>();
        for(Meter meter : meters){
             MeterReadingDto requestDto = new MeterReadingDto();
             requestDto.setMeterId(meter.getMeterId());
             requestDto.setContractNumber(meter.getContractNumber());
             requestDto.setReadOn(meter.getReadOn());
             requestDto.setCurrentReading(meter.getCurrentReading());
             requestDto.setGps(meter.getMeterLat() + "," + meter.getMeterLong());
             requestDtoList.add(requestDto);
        }
        return requestDtoList;
    }

}
