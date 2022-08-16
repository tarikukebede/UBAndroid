package et.com.act.unibillingandroid.Models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meter_table")
public class Meter {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String subCity;
    private String wereda;
    private String kebele;
    private Integer addressId;
    private Integer meterId;
    private String serialNo;
    private String contractNumber;
    private String meterLat;
    private String meterLong;
    private String customerName;
    private String readingTimePeriod;
    private String readOn;
    private String houseNumber;
    private String phonenumber;
    private String currentReading;
    private String previousReading;
    private String readingStatus;

    private String totalamount;
    private String customerMeter;
    private Integer contractid;
    private String billid;
    private Integer difference;
    private String consumption;
    private String billchargename;
    private String billcharge;
    private Boolean billchargestatus;
    private Integer billPeriodId;
    private String description;
    private String penality;
    private String registeredat;
    private String isRead;

    private String reasonForNotReading;

    public Meter() {
    }

    public Meter(String subCity, String wereda, String kebele, Integer addressId, Integer meterId, String serialNo, String contractNumber, String meterLat, String meterLong, String customerName, String readingTimePeriod, String readOn, String houseNumber, String phonenumber, String currentReading, String previousReading, String readingStatus, String totalamount, String customerMeter, Integer contractid, String billid, Integer difference, String consumption, String billchargename, String billcharge, Boolean billchargestatus, Integer billPeriodId, String description, String penality, String registeredat, String isRead, String reasonForNotReading) {
        this.subCity = subCity;
        this.wereda = wereda;
        this.kebele = kebele;
        this.addressId = addressId;
        this.meterId = meterId;
        this.serialNo = serialNo;
        this.contractNumber = contractNumber;
        this.meterLat = meterLat;
        this.meterLong = meterLong;
        this.customerName = customerName;
        this.readingTimePeriod = readingTimePeriod;
        this.readOn = readOn;
        this.houseNumber = houseNumber;
        this.phonenumber = phonenumber;
        this.currentReading = currentReading;
        this.previousReading = previousReading;
        this.readingStatus = readingStatus;
        this.totalamount = totalamount;
        this.customerMeter = customerMeter;
        this.contractid = contractid;
        this.billid = billid;
        this.difference = difference;
        this.consumption = consumption;
        this.billchargename = billchargename;
        this.billcharge = billcharge;
        this.billchargestatus = billchargestatus;
        this.billPeriodId = billPeriodId;
        this.description = description;
        this.penality = penality;
        this.registeredat = registeredat;
        this.isRead = isRead;
        this.reasonForNotReading = reasonForNotReading;

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubCity() {
        return subCity;
    }

    public void setSubCity(String subCity) {
        this.subCity = subCity;
    }

    public String getWereda() {
        return wereda;
    }

    public void setWereda(String wereda) {
        this.wereda = wereda;
    }

    public String getKebele() {
        return kebele;
    }

    public void setKebele(String kebele) {
        this.kebele = kebele;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

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

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getCustomerMeter() {
        return customerMeter;
    }

    public void setCustomerMeter(String customerMeter) {
        this.customerMeter = customerMeter;
    }

    public Integer getContractid() {
        return contractid;
    }

    public void setContractid(Integer contractid) {
        this.contractid = contractid;
    }

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }


    public Integer getDifference() {
        return difference;
    }

    public void setDifference(Integer difference) {
        this.difference = difference;
    }

    public String getConsumption() {
        return consumption;
    }

    public void setConsumption(String consumption) {
        this.consumption = consumption;
    }

    public String getBillchargename() {
        return billchargename;
    }

    public void setBillchargename(String billchargename) {
        this.billchargename = billchargename;
    }

    public String getBillcharge() {
        return billcharge;
    }

    public void setBillcharge(String billcharge) {
        this.billcharge = billcharge;
    }

    public Boolean getBillchargestatus() {
        return billchargestatus;
    }

    public void setBillchargestatus(Boolean billchargestatus) {
        this.billchargestatus = billchargestatus;
    }

    public Integer getBillPeriodId() {
        return billPeriodId;
    }

    public void setBillPeriodId(Integer billPeriodId) {
        this.billPeriodId = billPeriodId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPenality() {
        return penality;
    }

    public void setPenality(String penality) {
        this.penality = penality;
    }

    public String getRegisteredat() {
        return registeredat;
    }

    public void setRegisteredat(String registeredat) {
        this.registeredat = registeredat;
    }

//    public String isRead() {
//        return isRead;
//    }

    public void setIsRead(String read) {
        isRead = read;
    }

    public String getIsRead() {
        return isRead;
    }

    public String getReasonForNotReading() {
        return reasonForNotReading;
    }

    public void setReasonForNotReading(String reasonForNotReading) {
        this.reasonForNotReading = reasonForNotReading;
    }
}
