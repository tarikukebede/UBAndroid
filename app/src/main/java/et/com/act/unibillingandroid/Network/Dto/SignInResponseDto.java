package et.com.act.unibillingandroid.Network.Dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignInResponseDto {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("authorities")
    @Expose
    private List<String> authorities = null;
    @SerializedName("partyId")
    @Expose
    private String partyId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("message")
    @Expose
    private String message;

    public SignInResponseDto() {
    }

    public SignInResponseDto(String token, List<String> authorities, String partyId, String name, boolean status, String message) {
        this.token = token;
        this.authorities = authorities;
        this.partyId = partyId;
        this.name = name;
        this.status = status;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
