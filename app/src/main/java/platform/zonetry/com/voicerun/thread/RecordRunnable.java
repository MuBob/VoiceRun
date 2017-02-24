package platform.zonetry.com.voicerun.thread;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Administrator on 2017/2/21.
 */

public class RecordRunnable implements Runnable {
    private static final String TAG = "RecordRunnable";
    private AudioRecord mAudioRecord;
    private Handler mHandler;
    public static final int MSG_WHAT_VOLUME=12; //发送分贝值的msg.what，在obj中以double类型接收
    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
    private boolean isGetVoiceRun;
    private Object mLock;

    public RecordRunnable(AudioRecord mAudioRecord, Handler handler) {
        this.mAudioRecord = mAudioRecord;
        mHandler=handler;
        mLock=new Object();
    }

    @Override
    public void run() {
        mAudioRecord.startRecording();
        short[] buffer = new short[BUFFER_SIZE];
        isGetVoiceRun=true;
        while (isGetVoiceRun) {
            //r是实际读取的数据长度，一般而言r会小于buffersize
            int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
            long v = 0;
            // 将 buffer 内容取出，进行平方和运算
            for (int i = 0; i < buffer.length; i++) {
                v += buffer[i] * buffer[i];
            }
            // 平方和除以数据总长度，得到音量大小。
            double mean = v / (double) r;
            double volume = 10 * Math.log10(mean);
            Log.d(TAG, "分贝值:" + volume);
            Message message = mHandler.obtainMessage(MSG_WHAT_VOLUME);
            message.obj=volume;
            mHandler.sendMessage(message);

            // 大概一秒十次
            synchronized (mLock) {
                try {
                    mLock.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        mAudioRecord.stop();
        mAudioRecord.release();
        mAudioRecord = null;
    }

    public void stop(){
        isGetVoiceRun=false;
    }
}
