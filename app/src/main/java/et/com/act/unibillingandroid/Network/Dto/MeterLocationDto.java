package et.com.act.unibillingandroid.Network.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeterLocationDto {

    @SerializedName("meterId")
    @Expose
    private Integer meterId;
    @SerializedName("serialNo")
    @Expose
    private String serialNo;
    @SerializedName("contractNumber")
    @Expose
    private String contractNumber;
    @SerializedName("meterLat")
    @Expose
    private String meterLat;
    @SerializedName("meterLong")
    @Expose
    private String meterLong;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("readingTimePeriod")
    @Expose
    private String readingTimePeriod;
    @SerializedName("readOn")
    @Expose
    private String readOn;
    @SerializedName("wereda")
    @Expose
    private String wereda;
    @SerializedName("houseNumber")
    @Expose
    private String houseNumber;
    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;
    @SerializedName("kebele")
    @Expose
    private String kebele;
    @SerializedName("subcity")
    @Expose
    private String subcity;
    @SerializedName("currentReading")
    @Expose
    private String currentReading;
    @SerializedName("previousReading")
    @Expose
    private String previousReading;
    @SerializedName("readingStatus")
    @Expose
    private String readingStatus;
    @SerializedName("addressId")
    @Expose
    private Integer addressId;
    @SerializedName("consumptionbillchargepaymentviewModel")
    @Expose
    private ConsumptionbillchargepaymentviewModel consumptionbillchargepaymentviewModel;

    public Integer getMeterId() {
        return meterId;
    }

    public void setMeterId(Integer meterId) {
        this.meterId = meterId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getMeterLat() {
        return meterLat;
    }

    public void setMeterLat(String meterLat) {
        this.meterLat = meterLat;
    }

    public String getMeterLong() {
        return meterLong;
    }

    public void setMeterLong(String meterLong) {
        this.meterLong = meterLong;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getReadingTimePeriod() {
        return readingTimePeriod;
    }

    public void setReadingTimePeriod(String readingTimePeriod) {
        this.readingTimePeriod = readingTimePeriod;
    }

    public String getReadOn() {
        return readOn;
    }

    public void setReadOn(String readOn) {
        this.readOn = readOn;
    }

    public String getWereda() {
        return wereda;
    }

    public void setWereda(String wereda) {
        this.wereda = wereda;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getKebele() {
        return kebele;
    }

    public void setKebele(String kebele) {
        this.kebele = kebele;
    }

    public String getSubcity() {
        return subcity;
    }

    public void setSubcity(String subcity) {
        this.subcity = subcity;
    }

    public String getCurrentReading() {
        return currentReading;
    }

    public void setCurrentReading(String currentReading) {
        this.currentReading = currentReading;
    }

    public String getPreviousReading() {
        return previousReading;
    }

    public void setPreviousReading(String previousReading) {
        this.previousReading = previousReading;
    }

    public String getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(String readingStatus) {
        this.readingStatus = readingStatus;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public ConsumptionbillchargepaymentviewModel getConsumptionbillchargepaymentviewModel() {
        return consumptionbillchargepaymentviewModel;
    }

    public void setConsumptionbillchargepaymentviewModel(ConsumptionbillchargepaymentviewModel consumptionbillchargepaymentviewModel) {
        this.consumptionbillchargepaymentviewModel = consumptionbillchargepaymentviewModel;
    }

}
