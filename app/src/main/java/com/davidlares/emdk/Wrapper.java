package com.davidlares.emdk;

import com.symbol.emdk.EMDKManager.EMDKListener;
import com.davidlares.testapp.MainActivity;
import com.davidlares.utils.Constants;
import com.symbol.emdk.EMDKManager;
import com.symbol.emdk.EMDKResults;
import android.util.Log;

public class Wrapper implements EMDKListener {

    private Scanner scanner;

    public Wrapper() {
        EMDKResults results = Constants.emdkManager.getEMDKManager(MainActivity.getAppContext(), this);
        if (results.statusCode == EMDKResults.STATUS_CODE.SUCCESS) {
            this.scanner = Scanner.getInstance();
            Log.d("EDMK Log", "Wrapper started");
        } else {
            Log.d("EDMK Log", "EDMK Results failed to load");
        }
    }

    @Override
    public void onOpened(EMDKManager emdkManager) {
        Constants.emdkManager = emdkManager;
        // init manager
        Manager.initBarcodeManager();
        // init scanner
        this.scanner.initScanner();
    }

    @Override
    public void onClosed() {
        if (Constants.emdkManager != null) {
            Constants.emdkManager.release();
            Constants.emdkManager = null;
        }
        Log.d("EDMK Log","EMDK closed unexpectedly! Please close and restart the application.");
    }
}
