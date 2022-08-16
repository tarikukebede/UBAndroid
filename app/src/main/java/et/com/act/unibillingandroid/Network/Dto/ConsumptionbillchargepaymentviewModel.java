package et.com.act.unibillingandroid.Network.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConsumptionbillchargepaymentviewModel {

    @SerializedName("totalamount")
    @Expose
    private Double totalamount;
    @SerializedName("customerMeter")
    @Expose
    private String customerMeter;
    @SerializedName("contractid")
    @Expose
    private Integer contractid;
    @SerializedName("contractnumber")
    @Expose
    private String contractnumber;
    @SerializedName("billid")
    @Expose
    private String billid;
    @SerializedName("currentreading")
    @Expose
    private Integer currentreading;
    @SerializedName("previousreading")
    @Expose
    private Integer previousreading;
    @SerializedName("difference")
    @Expose
    private Integer difference;
    @SerializedName("consumption")
    @Expose
    private Double consumption;
    @SerializedName("billchargename")
    @Expose
    private String billchargename;
    @SerializedName("billcharge")
    @Expose
    private Double billcharge;
    @SerializedName("billchargestatus")
    @Expose
    private Boolean billchargestatus;
    @SerializedName("billPeriodId")
    @Expose
    private Integer billPeriodId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("penality")
    @Expose
    private Double penality;
    @SerializedName("registeredat")
    @Expose
    private String registeredat;

    public Double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(Double totalamount) {
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

    public String getContractnumber() {
        return contractnumber;
    }

    public void setContractnumber(String contractnumber) {
        this.contractnumber = contractnumber;
    }

    public String getBillid() {
        return billid;
    }

    public void setBillid(String billid) {
        this.billid = billid;
    }

    public Integer getCurrentreading() {
        return currentreading;
    }

    public void setCurrentreading(Integer currentreading) {
        this.currentreading = currentreading;
    }

    public Integer getPreviousreading() {
        return previousreading;
    }

    public void setPreviousreading(Integer previousreading) {
        this.previousreading = previousreading;
    }

    public Integer getDifference() {
        return difference;
    }

    public void setDifference(Integer difference) {
        this.difference = difference;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }

    public String getBillchargename() {
        return billchargename;
    }

    public void setBillchargename(String billchargename) {
        this.billchargename = billchargename;
    }

    public Double getBillcharge() {
        return billcharge;
    }

    public void setBillcharge(Double billcharge) {
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

    public Double getPenality() {
        return penality;
    }

    public void setPenality(Double penality) {
        this.penality = penality;
    }

    public String getRegisteredat() {
        return registeredat;
    }

    public void setRegisteredat(String registeredat) {
        this.registeredat = registeredat;
    }

}
