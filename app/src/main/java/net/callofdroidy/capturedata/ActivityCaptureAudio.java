package net.callofdroidy.capturedata;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ActivityCaptureAudio extends AppCompatActivity {
    final static String TAG = "ActCaptureAudio";

    TextView tvCaptureAudio;

    File audioCaptureDir;
    File audioFile;

    boolean isRecording = false;

    int bufferSize;

    AudioRecord audioRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_audio);

        initViews();

        int audioSource = MediaRecorder.AudioSource.MIC;
        int sampleRateInHz = 11025;
        int channelConfig = AudioFormat.CHANNEL_IN_MONO;
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        bufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        audioRecord = new AudioRecord(
                audioSource,
                sampleRateInHz,
                channelConfig,
                audioFormat,
                bufferSize
        );

        audioCaptureDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AudioRecordTest");
        if(!audioCaptureDir.exists()) audioCaptureDir.mkdir();
    }

    public void initViews(){
        tvCaptureAudio = (TextView) findViewById(R.id.tv_capture_audio);
    }

    public void onAudioRecordStart(View view){
        tvCaptureAudio.setText("Recording...");
        record();
    }

    public void onAudioRecordStop(View view){
        tvCaptureAudio.setText("Not Recording");
        stop();
    }

    public void record(){
        isRecording = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                isRecording = true;

                audioFile = new File(audioCaptureDir, "myaudio.pcm");
                if(audioFile.exists()) audioFile.delete();
                try{
                    audioFile.createNewFile();
                }catch (IOException e){
                    Log.e(TAG, "run: fail to create new file");
                }

                try{
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(audioFile)));
                    byte[] buffer = new byte[bufferSize];
                    audioRecord.startRecording();
                    int r = 0;
                    while(isRecording){
                        int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
                        for(int i = 0; i < bufferReadResult; i++)
                            dos.write(buffer[i]);
                        r++;
                    }
                    audioRecord.stop();
                    dos.close();
                }catch (Throwable t){
                    Log.e(TAG, "run: recording failed");
                }
            }
        }).start();
    }

    public void stop(){
        isRecording = false;
    }
}
