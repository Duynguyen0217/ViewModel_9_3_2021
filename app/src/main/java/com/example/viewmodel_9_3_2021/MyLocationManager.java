package com.example.viewmodel_9_3_2021;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;


@SuppressLint("MissingPermission")
public class MyLocationManager implements LifecycleObserver {

    //OnlifecycleEvent : dùng để check vòng đời của Activity đang đứng ở đâu
//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    public void xuly(){
//        Log.d("BBBB","onCreate");
//    }
    private Context context;
    private  OnListenerLocation onListenerLocation;
    private LocationManager locationManager;

    public MyLocationManager(Context context, OnListenerLocation onListenerLocation) {
        this.context = context;
        this.onListenerLocation = onListenerLocation;
    }

    @SuppressLint("MissingPermission")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void start(){
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //nếu location ko trả về(bị null ) thì nhảy vào dòng lệnh dưới
        if(locationManager != null){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0 , 0f ,locationListener);
        }

        //nếu location có sự thay đổi thi dòng code bên dưới getlastknownlocation sẽ lấy location mới
        //và nếu location ko bị rỗng sẽ đc cập nhật lại location mới
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location != null){
            locationListener.onLocationChanged(location);
        }
        Toast.makeText(context, "MyLocation Started", Toast.LENGTH_SHORT).show();
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            onListenerLocation.callbackLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }
    };

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void stop(){
        if(locationManager == null){
            return;
        }
        locationManager.removeUpdates(locationListener);
        locationManager = null;
        Toast.makeText(context, "MyLocation Paused", Toast.LENGTH_SHORT).show();


    }

}
