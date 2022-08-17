package et.com.act.unibillingandroid.Util;

public class Constants {
    public static final String DATABASE_NAME = "unibilling_database";
    public static final boolean REQUEST_SUCCESS = true;
    public static final boolean REQUEST_FAILURE = false;

    public static final String METERS = "meters";
    public static final String READING_INFO = "reading info";
    public static final String TRUE_STRING = "1";
    public static final String FALSE_STRING = "0";

    public static final int WARNING = 0;
    public static final int SUCCESS = 1;
    public static final int DANGER = 2;

    public static final int MAX_REASON_SIZE = 50;

    public static final int PERMISSION_RESPONSE_CODE = 1001;
    public static final int PERMISSION_REQUEST_CODE = 1002;
    public static final int ERROR_DIALOG_REQUEST_CODE = 9001;
    public static final int FILE_CREATE_REQUEST = 9002;

    public static final float DEFAULT_CAMERA_ZOOM = 15f;
    public static final String UNKNOWN = "----";
    public static final String UNKNOWN_NAME = "Unknown";
    public static final String ANONYMOUS = "anonymous";
    public static final float VISIBLE_METER_DISTANCE = 10f;
    public static final String CONTRACT_NO = "contract number";

    public static final int SIGNAL_CLOSE_APP = 0;

    public static final long GPS_UPDATE_MIN_DISTANCE = 2;
    public static final long MIN_UPDATE_TIME = 1000;
    public static final String FUSED_LOCATION_PROVIDER = "fused location provider";


    public static final String METERS_WITH_CLEAN_COORDINATES = "meters_with_clean_coordinate";
    public static final String METERS_WITH_UNCLEAN_COORDINATES = "meters_with_unclean_coordinates";

    public static final String[] EXCEL_EXPORT_FORMAT = {"meterId", "currentReading", "readOn","gps"};


}
