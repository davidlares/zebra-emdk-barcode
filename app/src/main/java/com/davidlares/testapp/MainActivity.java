package com.davidlares.testapp;

import androidx.appcompat.app.AppCompatActivity;
import com.davidlares.utils.Constants;
import com.davidlares.utils.Listener;
import com.davidlares.emdk.Manager;
import com.davidlares.emdk.Wrapper;
import com.davidlares.emdk.Scanner;
import android.content.Context;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.os.Build;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static MainActivity instance;
    private Button startScanner;
    private Button stopScanner;
    private Scanner scanner;
    private Wrapper wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // grabbing instance (appContext)
        instance = this;

        // button instances
        Button startScanner = findViewById(R.id.startScanner);
        Button stopScanner = findViewById(R.id.stopScanner);

        // handling the event
        startScanner.setOnClickListener(this);
        stopScanner.setOnClickListener(this);

        // loading wrapper
        if (Build.MANUFACTURER.contains("Zebra Technologies") || Build.MANUFACTURER.contains("Motorola Solutions")) {
            // wrapper instance
            this.wrapper = new Wrapper();
            // setting up the listener
            this.scanner.setListener(new Listener() {
                @Override
                public void gettingValue(String value) {
                    // printing something here
                    Toast.makeText(instance, value, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // zebra
        if (Constants.emdkManager != null) {
            Manager.initBarcodeManager();
            this.scanner.initScanner();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // zebra
        if(Constants.emdkManager != null) {
            Manager.deInitBarcodeManager();
            this.scanner.deInitScanner();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // zebra
        if (Constants.emdkManager != null) {
            Constants.emdkManager.release();
            this.scanner.deInitScanner();
            Constants.emdkManager = null;
        }
    }

    // context
    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onClick(View view) {
        Log.d("TestApp", "Handling the click event");
        switch(view.getId()) {
            case R.id.startScanner:
                Log.d("TestApp", "Start scanner");
                if(Constants.emdkManager != null) {
                    this.scanner.initScanner();
                }
                break;
            case R.id.stopScanner:
                Log.d("TestApp", "Stop scanner");
                if(Constants.emdkManager != null) {
                    this.scanner.deInitScanner();
                }
                break;
        }
    }
}