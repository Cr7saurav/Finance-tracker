package com.financialreality;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class FinancialRealityApp extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        // Firebase initialization is commented out to prevent crashes until google-services.json is added.
        // Once you add the file and the plugin, you can uncomment this.
        // FirebaseApp.initializeApp(this);
    }
}
