package com.davidlares.utils;

import com.symbol.emdk.barcode.ScanDataCollection;
import com.symbol.emdk.barcode.BarcodeManager;
import com.symbol.emdk.barcode.ScannerInfo;
import com.symbol.emdk.barcode.StatusData;
import com.symbol.emdk.barcode.Scanner;
import com.symbol.emdk.EMDKManager;
import java.util.List;

public class Constants {
    // zebra
    public static ScanDataCollection scanDataCollection = null;
    public static BarcodeManager barcodeManager = null;
    public static List<ScannerInfo> deviceList = null;
    public static EMDKManager emdkManager = null;
    public static StatusData statusData = null;
    public static Scanner scanner = null;
}
