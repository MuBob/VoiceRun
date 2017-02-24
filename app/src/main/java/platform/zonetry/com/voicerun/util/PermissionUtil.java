package platform.zonetry.com.voicerun.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2017/2/21.
 */

public class PermissionUtil {
    private Context context;

    protected void permission(){
        if(Build.VERSION.SDK_INT>=23){
            //检查权限
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                //进入到这里代表没有权限.
//                context.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE);
            } else {

            }
        }
    }
}
