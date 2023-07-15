package com.davidlares.emdk;

import com.symbol.emdk.barcode.ScannerException;
import com.symbol.emdk.barcode.ScannerConfig;
import com.davidlares.utils.Constants;
import android.util.Log;

public class Config {
    public static void setConfig() {
        if (Constants.scanner != null) {
            try {
                // Get scanner config
                ScannerConfig config = Constants.scanner.getConfig();
                // Enable haptic feedback
                if (config.isParamSupported("config.scanParams.decodeHapticFeedback")) {
                    config.scanParams.decodeHapticFeedback = true;
                }
                config.decoderParams.code128.enabled = true;
                config.decoderParams.code39.enabled = true;
                config.decoderParams.ean8.enabled = true;
                config.decoderParams.ean13.enabled = true;
                // Set scanner config
                Constants.scanner.setConfig(config);
            } catch (ScannerException e) {
                Log.d("Config error", e.getMessage());
            }
        }
    }
}

