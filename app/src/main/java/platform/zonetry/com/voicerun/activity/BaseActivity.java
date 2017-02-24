package platform.zonetry.com.voicerun.activity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/2/21.
 */

public abstract class BaseActivity extends AppCompatActivity{
    private final static int REQUEST_CODE=123;
    private PermissionCallback callback;
    private String[] permissions;

    /**
     * 在每个界面启动前动态申请权限
     * @param callback
     * @param permissions
     */
    protected void permission(PermissionCallback callback, String... permissions){
        this.callback=callback;
        this.permissions=permissions;
        onPermissionRequest();
    }

    private void onPermissionRequest() {
        if(Build.VERSION.SDK_INT>=23){
            //检查权限
            boolean isGranted=true;
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    isGranted = false;
                    break;
                }
            }
            if (isGranted) {
                //进入到这里代表没有权限.
                requestPermissions(permissions, REQUEST_CODE);
            } else {
                callback.call(true);
            }
        }else {
            callback.call(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length >0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //用户同意授权
                    callback.call(true);
                }else{
                    //用户拒绝授权
                    callback.call(false);
                }
                break;
        }
    }

    public interface PermissionCallback{
        void call(boolean isPermission);
    }
}
