package et.com.act.unibillingandroid.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "auth_table")
public class Auth implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String token;
    private String partyId;
    private List<String> authorities;
    private String message;
    private boolean status;


    public Auth() {
    }

    public Auth(String name, String token, String partyId, List<String> authorities, String message, boolean status) {
        this.name = name;
        this.token = token;
        this.partyId = partyId;
        this.authorities = authorities;
        this.status = status;
        this.message = message;
    }

    public Auth(Parcel in){
        this.id = in.readInt();
        this.name = in.readString();
        this.token = in.readString();
        this.partyId = in.readString();
        this.authorities = in.createStringArrayList();
        this.message = in.readString();
        this.status = in.readString().matches("true");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static final Creator<Auth> CREATOR = new Creator<Auth>() {
        @Override
        public Auth createFromParcel(Parcel parcel) {
            return new Auth(parcel);
        }

        @Override
        public Auth[] newArray(int i) {
            return new Auth[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(token);
        parcel.writeString(partyId);
        parcel.writeStringList(authorities);
        parcel.writeString(message);
        parcel.writeString(String.valueOf(status));
    }
}
