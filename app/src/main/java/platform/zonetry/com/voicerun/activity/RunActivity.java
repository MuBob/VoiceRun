package platform.zonetry.com.voicerun.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import platform.zonetry.com.voicerun.AudioRecordDemo;
import platform.zonetry.com.voicerun.R;
import platform.zonetry.com.voicerun.thread.RecordRunnable;

public class RunActivity extends BaseActivity {

    private AudioRecordDemo audioRecordDemo;
    private Handler mHandler;
    private ImageView personImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        permission(new PermissionCallback() {
            @Override
            public void call(boolean isPermission) {
                if(!isPermission){
                    Toast.makeText(RunActivity.this, "尚未获取权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }, Manifest.permission.RECORD_AUDIO);
        mHandler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == RecordRunnable.MSG_WHAT_VOLUME){
                    Object obj = msg.obj;
                    if(obj instanceof Double){
                        double volume=(Double) obj;
                        jump(volume);
                        Log.i("TAG", "RunActivity.handleMessage: getVolume="+volume);
                    }else{
                        Log.e("TAG", "RunActivity.handleMessage: get wrong type of msg");
                    }
                }
                return true;
            }
        });
        audioRecordDemo=new AudioRecordDemo(mHandler);
        personImage = (ImageView) findViewById(R.id.person_image_activity_run);

    }

    private static final String TAG = "RunActivity";
    private void jump(double volume) {
        float height= -((float) (volume/35)*100);
        Log.i(TAG, "RunActivity.jump: height="+height);
        TranslateAnimation animation = new TranslateAnimation(
                //X轴初始位置
                Animation.RELATIVE_TO_SELF, 0f,
                //X轴移动的结束位置
                Animation.RELATIVE_TO_SELF, 0f,
                //y轴开始位置
                Animation.RELATIVE_TO_SELF, 0f,
                //y轴移动后的结束位置
                Animation.RELATIVE_TO_SELF, height);

        //3秒完成动画
        animation.setRepeatMode(Animation.REVERSE);
        animation.setDuration(2000);
//        animation.start();
        personImage.clearAnimation();
        personImage.setAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        audioRecordDemo.startNoiseLevel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        audioRecordDemo.stopNoiseLevel();
    }
}
