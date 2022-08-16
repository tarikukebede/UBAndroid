package et.com.act.unibillingandroid.Network;

public class Url {
    public static final String BATI = "http://196.189.53.130:28080/BillingPaymentModule-Bati/rest/";
    public static final String MOLALE = "http://196.189.53.130:28080/BillingPaymentModule-molale/rest/";
    public static final String ESTE = "http://196.189.53.130:28080/BillingPaymentModule-este/rest/";
    public static final String MEHAL_MEDA = "http://196.189.53.130:28080/BillingPaymentModule-Mehalmeda/rest/";
    public static final String JAMMA = "http://196.189.53.130:28080/BillingPaymentModule-jama/rest/";
    public static final String DEBRESINA = "http://196.189.53.130:28080/BillingPaymentModule-debresina/rest/";


    public static final String OVER_HOTSPOT = "http://10.42.0.1";
    public static final String OVER_INTERNET = "http://192.168.1.43";
    public static final String BASE = OVER_INTERNET + ":8197/BillingPaymentModule/rest/";

    public static final String SING_IN = "AuthResource/signIn";
    public static final String CHANGE_PASSWORD = "passwordRelatives/changeLostPassword";
    public static final String FETCH_METERS = "BillReadingResource/fetchReadingList";
    public static final String SUBMIT_METER_READINGS = "BillReadingResource/readingUpdate";
}
