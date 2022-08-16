package et.com.act.unibillingandroid.Network.Dto;

public class MeterReadingDto {
    private long meterId;
    private String contractNumber;
    private String currentReading;
    private String readOn;
    private String gps;


    public MeterReadingDto() {
    }

    public MeterReadingDto(String contractNumber, long meterId, String currentReading, String readOn, String gps) {
        this.contractNumber = contractNumber;
        this.meterId = meterId;
        this.currentReading = currentReading;
        this.readOn = readOn;
        this.gps = gps;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public long getMeterId() {
        return meterId;
    }

    public void setMeterId(long meterId) {
        this.meterId = meterId;
    }

    public String getCurrentReading() {
        return currentReading;
    }

    public void setCurrentReading(String currentReading) {
        this.currentReading = currentReading;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getReadOn() {
        return readOn;
    }

    public void setReadOn(String readOn) {
        this.readOn = readOn;
    }

}