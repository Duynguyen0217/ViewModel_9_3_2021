package com.example.viewmodel_9_3_2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    int REQUEST_CODE_LOCATION = 123;
    MyLocationManager myLocationManager;
    TextView mTvlocation;

//    MylifeCycleActivity mylifeCycleActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Lifecycle aware Component

        mTvlocation = findViewById(R.id.textviewlocation);


//        mylifeCycleActivity = new MylifeCycleActivity();
//        //getlifecycle : dùng để lấy vòng đời
//        getLifecycle().addObserver(mylifeCycleActivity);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //nếu permission != package.permission_granted :ko đồng ý
            //thì vào xin phép
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION
            );
        }else{
            myLocationManager = new MyLocationManager(this, new OnListenerLocation() {
                @Override
                public void callbackLocation(Location location) {
                    mTvlocation.setText(location.getLatitude() + "," +location.getLongitude());
                }
            });
            //truyen getlifecycle.addobserver để lắng nghe vòng đời
            getLifecycle().addObserver(myLocationManager);
        }


    }

   // hộp thoại bên trên xác thực như thế nào sẽ trả về trong hàm onrequestpermissionresult bên dưới
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_LOCATION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                myLocationManager = new MyLocationManager(this, new OnListenerLocation() {
                    @Override
                    public void callbackLocation(Location location) {
                        mTvlocation.setText(location.getLatitude() + "," +location.getLongitude());
                    }
                });
                getLifecycle().addObserver(myLocationManager);
            }else{
                Toast.makeText(this, "Bạn chưa có quyền truy cập vào GPS của thiết bị", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}