package et.com.act.unibillingandroid.Network.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchMeterListResponseDto {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("readerInfos")
    @Expose
    private List<ReaderInfo> readerInfos = null;
    @SerializedName("meterLocationDtos")
    @Expose
    private List<MeterLocationDto> meterLocationDtos = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ReaderInfo> getReaderInfos() {
        return readerInfos;
    }

    public void setReaderInfos(List<ReaderInfo> readerInfos) {
        this.readerInfos = readerInfos;
    }

    public List<MeterLocationDto> getMeterLocationDtos() {
        return meterLocationDtos;
    }

    public void setMeterLocationDtos(List<MeterLocationDto> meterLocationDtos) {
        this.meterLocationDtos = meterLocationDtos;
    }

}

