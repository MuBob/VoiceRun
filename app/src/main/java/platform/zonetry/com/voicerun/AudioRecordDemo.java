package platform.zonetry.com.voicerun;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;

import platform.zonetry.com.voicerun.thread.RecordRunnable;

/**
 * Created by Administrator on 2017/2/21.
 */

public class AudioRecordDemo {
    private static final String TAG = "AudioRecord";
    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE
            = AudioRecord.getMinBufferSize(
            SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT,
            AudioFormat.ENCODING_PCM_16BIT);
    AudioRecord mAudioRecord;
    private RecordRunnable runnable;
    private Handler mHandler;

    public AudioRecordDemo(Handler handler) {
        mHandler=handler;
    }

    public void startNoiseLevel() {
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        if (mAudioRecord == null) {
            Log.e("sound", "mAudioRecord初始化失败");
            return;
        }
        runnable=new RecordRunnable(mAudioRecord, mHandler);
        new Thread(runnable).start();
    }

    public void stopNoiseLevel(){
        runnable.stop();
    }

}
