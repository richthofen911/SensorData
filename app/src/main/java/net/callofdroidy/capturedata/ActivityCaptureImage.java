package net.callofdroidy.capturedata;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.security.Permission;

public class ActivityCaptureImage extends AppCompatActivity {
    static final String TAG = "ActCaptureImage";

    CameraManager cameraManager;

    CameraDevice cameraDevice;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);

        cameraManager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        //ContextCompat.checkSelfPermission(this, android.perm)

    }

    @SuppressWarnings("MissingPermission")
    public void openCamera(){
        try{
            cameraManager.openCamera("0", new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    cameraDevice = camera;
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {

                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {

                }
            }, handler);
        }catch (CameraAccessException e){
            Log.e(TAG, "openCamera: " + e.toString());
        }

    }

}
