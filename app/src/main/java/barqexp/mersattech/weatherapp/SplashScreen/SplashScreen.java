package barqexp.mersattech.weatherapp.SplashScreen;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;

import barqexp.mersattech.weatherapp.Graphics.LoadingHelper;
import barqexp.mersattech.weatherapp.Location.Location;
import barqexp.mersattech.weatherapp.WeatherActivity.WeatherActivity;
import barqexp.mersattech.weatherapp.R;

public class SplashScreen extends AppCompatActivity {
    ImageView loadingIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loadingIV = findViewById(R.id.loading_IV);


        loadingIV.startAnimation(LoadingHelper.load());
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    10);
            return;

        }else {
            makeLocationCall();
        }




    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case 10:
                makeLocationCall();
                break;

        }

    }

    public void makeLocationCall(){
        Location.getInstance(this).getLocation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingIV.clearAnimation();
                startActivity(new Intent(SplashScreen.this, WeatherActivity.class));
                finish();
            }
        },5000);
    }
}
