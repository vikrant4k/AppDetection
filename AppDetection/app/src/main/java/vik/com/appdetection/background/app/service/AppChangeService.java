package vik.com.appdetection.background.app.service;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
public class AppChangeService {

private String prevPackageName="";

public String findAppByPackage(String packageName, PackageManager manager)
{
    if(packageName!=null) {
        if (!packageName.equals(prevPackageName)) {
            prevPackageName = packageName;
            ApplicationInfo ai;
            try {
                ai = manager.getApplicationInfo(packageName, 0);
            } catch (final PackageManager.NameNotFoundException e) {
                ai = null;
            }
            String applicationName = (String) (ai != null ? manager.getApplicationLabel(ai) : "(unknown)");
            Log.d("com.vik", applicationName);
            return applicationName;
        }
    }
    return null;
}



}
