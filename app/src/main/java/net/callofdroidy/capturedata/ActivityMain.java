package net.callofdroidy.capturedata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onAudioClick(View view){
        startActivity(new Intent(ActivityMain.this, ActivityCaptureAudio.class));
    }

    public void onImageClick(View view){
        startActivity(new Intent(ActivityMain.this, ActivityCaptureImage.class));
    }
}
