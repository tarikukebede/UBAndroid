package et.com.act.unibillingandroid.Network;

import et.com.act.unibillingandroid.Models.Auth;

public class NetworkConstants {
    public static final Long HTTP_TIMEOUT = 2L;
    public static final String HTTP_HEADER_FORMAT = "Content-type: application/json;charset=UTF-8";
    public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";



    public static String ACCESS_TOKEN (Auth auth){
        return TOKEN_PREFIX + auth.getToken();
    }
}
