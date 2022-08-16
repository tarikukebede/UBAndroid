package et.com.act.unibillingandroid.Util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import et.com.act.unibillingandroid.R;

public class Helper {
    public static void openFragment(FragmentManager manager, int containerId, Fragment fragment){
        manager.beginTransaction().replace(containerId,fragment).setReorderingAllowed(true).addToBackStack(null).commit();
    }

    public static void openFragmentWithSlideAnim(FragmentManager manager, int containerId, Fragment fragment){
        manager.beginTransaction().replace(containerId, fragment).setCustomAnimations(R.anim.slide_out_left, R.anim.slide_in_left).commit();
    }

    public static String createUserInitial(String name){
        if(name != null){
            if(name.length() > 2){
                return name.substring(0, 2).toUpperCase();
            }
            return name.toUpperCase();
        }
        return  Constants.UNKNOWN_NAME;
    }


    public static String generateUserName(String name){
        if(name != null){
            return name.toUpperCase() + "@" + "Billing";
        }
        return Constants.ANONYMOUS;
    }


    public static String dateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return simpleDateFormat.format(date);
    }




}
