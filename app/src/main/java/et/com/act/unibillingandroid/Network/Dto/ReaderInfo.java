package et.com.act.unibillingandroid.Network.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReaderInfo {

    @SerializedName("subCity")
    @Expose
    private String subCity;
    @SerializedName("wereda")
    @Expose
    private String wereda;
    @SerializedName("kebele")
    @Expose
    private String kebele;
    @SerializedName("addressId")
    @Expose
    private Integer addressId;
    @SerializedName("locationGroupId")
    @Expose
    private Integer locationGroupId;
    @SerializedName("locationGroupName")
    @Expose
    private String locationGroupName;
    @SerializedName("locationGroupDescription")
    @Expose
    private String locationGroupDescription;
    @SerializedName("count")
    @Expose
    private Integer count;

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

    public Integer getLocationGroupId() {
        return locationGroupId;
    }

    public void setLocationGroupId(Integer locationGroupId) {
        this.locationGroupId = locationGroupId;
    }

    public String getLocationGroupName() {
        return locationGroupName;
    }

    public void setLocationGroupName(String locationGroupName) {
        this.locationGroupName = locationGroupName;
    }

    public String getLocationGroupDescription() {
        return locationGroupDescription;
    }

    public void setLocationGroupDescription(String locationGroupDescription) {
        this.locationGroupDescription = locationGroupDescription;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
