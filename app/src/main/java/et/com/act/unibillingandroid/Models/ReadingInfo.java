package et.com.act.unibillingandroid.Models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reading_info_table")
public class ReadingInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String subCity;
    private String wereda;
    private String kebele;
    private Integer addressId;
    private Integer locationGroupId;
    private String locationGroupName;
    private String locationGroupDescription;
    private Integer count;

    public ReadingInfo() {
    }

    public ReadingInfo(String subCity, String wereda, String kebele, Integer addressId, Integer locationGroupId, String locationGroupName, String locationGroupDescription, Integer count) {
        this.subCity = subCity;
        this.wereda = wereda;
        this.kebele = kebele;
        this.addressId = addressId;
        this.locationGroupId = locationGroupId;
        this.locationGroupName = locationGroupName;
        this.locationGroupDescription = locationGroupDescription;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
