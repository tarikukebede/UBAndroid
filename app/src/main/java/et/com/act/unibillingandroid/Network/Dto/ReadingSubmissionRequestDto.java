package et.com.act.unibillingandroid.Network.Dto;

public class ReadingSubmissionRequestDto {

    private long meterId;
    private long currentReading;
    private String readOn;
    private String gps;

    public ReadingSubmissionRequestDto() {
    }

    public ReadingSubmissionRequestDto(long meterId, long currentReading, String readOn, String gps) {
        this.meterId = meterId;
        this.currentReading = currentReading;
        this.readOn = readOn;
        this.gps = gps;
    }


    public long getMeterId() {
        return meterId;
    }

    public void setMeterId(long meterId) {
        this.meterId = meterId;
    }

    public long getCurrentReading() {
        return currentReading;
    }

    public void setCurrentReading(long currentReading) {
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
