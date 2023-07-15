package com.davidlares.emdk;

import com.symbol.emdk.barcode.BarcodeManager;
import com.davidlares.utils.Constants;
import com.symbol.emdk.EMDKManager;
import android.util.Log;

public class Manager {

    public static void initBarcodeManager() {
        Constants.barcodeManager = (BarcodeManager) Constants.emdkManager.getInstance(EMDKManager.FEATURE_TYPE.BARCODE);
        if (Constants.barcodeManager == null) {
            Log.d("Barcode", "Barcode scanning is not supported");
        }
    }

    public static void deInitBarcodeManager() {
        if (Constants.emdkManager != null) {
            Constants.emdkManager.release(EMDKManager.FEATURE_TYPE.BARCODE);
        }
    }
}
