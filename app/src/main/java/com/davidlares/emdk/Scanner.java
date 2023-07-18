package com.davidlares.emdk;

import com.symbol.emdk.barcode.Scanner.StatusListener;
import com.symbol.emdk.barcode.Scanner.DataListener;
import com.symbol.emdk.barcode.Scanner.TriggerType;
import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.ScannerException;
import com.symbol.emdk.barcode.BarcodeManager;
import com.symbol.emdk.barcode.ScannerResults;
import com.davidlares.testapp.MainActivity;
import com.symbol.emdk.barcode.StatusData;
import com.davidlares.utils.Constants;
import com.davidlares.utils.Listener;
import java.util.ArrayList;
import android.util.Log;
import android.widget.Toast;

public class Scanner implements DataListener, StatusListener {

    private static Scanner scanner;
    private Listener listener;

    // constructor
    private Scanner() {
        this.listener = null;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public static Scanner getInstance() {
        if(scanner == null) {
            scanner = new Scanner();
        }
        return scanner;
    }

    public void initScanner() {
        if (Constants.scanner == null) {
            Constants.scanner = Constants.barcodeManager.getDevice(BarcodeManager.DeviceIdentifier.DEFAULT);
            if(Constants.scanner != null) {
                Constants.scanner.addDataListener(this);
                Constants.scanner.addStatusListener(this);
                Constants.scanner.triggerType = TriggerType.HARD;
                try {
                    Constants.scanner.enable();
                } catch (ScannerException e) {
                    Log.d("Scanner", e.getMessage());
                    this.deInitScanner();
                }
            } else {
                Log.d("Device problem", "Failed to get the specified scanner device! Please close and restart the application.");
            }
        }
    }

    public void deInitScanner() {
        if (Constants.scanner != null) {
            try {
                Constants.scanner.disable();
                Constants.scanner.addDataListener(this);
                Constants.scanner.addStatusListener(this);
                Constants.scanner.release();
            } catch (Exception e)   {
                Log.d("Scanner", e.getMessage());
            }
            Constants.scanner = null;
        }
    }

    @Override
    public void onData(ScanDataCollection scanDataCollection) {
        // receiving scan
        String dataStr = "";
        if ((scanDataCollection != null) && (scanDataCollection.getResult() == ScannerResults.SUCCESS)) {
            ArrayList<ScanDataCollection.ScanData> scanData =  scanDataCollection.getScanData();
            // Iterate through scanned data and prepare the data.
            for (ScanDataCollection.ScanData data : scanData) {
                String barcodeData = data.getData();
                if(barcodeData.length() > 0) {
                    listener.gettingValue(barcodeData);
                }
            }
        }

    }

    @Override
    public void onStatus(StatusData statusData) {
        StatusData.ScannerStates state = statusData.getState();
        String statusStr = "";
        // scenarios
        switch(state) {
            case IDLE:
                statusStr = statusData.getFriendlyName() + "is enabled and idle.";
                Config.setConfig();
                try {
                    Constants.scanner.read();
                } catch(ScannerException e) {
                    Log.d("Scan temp Result", e.getMessage());
                }
                break;
            case WAITING:
                Log.d("Scanner status","Scanner is waiting for trigger press.");
                break;
            case SCANNING:
                Log.d("Scanner status","Scanning in progress.");
                break;
            case DISABLED:
                Log.d("Scanner status", "The scanner is disabled");
                break;
            case ERROR:
                Log.d("Scanner status","An error has occurred.");
                break;
            default:
                break;
        }
    }
}
