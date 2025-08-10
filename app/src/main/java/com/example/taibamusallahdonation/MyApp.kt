package com.example.taibamusallahdonation

import android.app.Application
import com.squareup.sdk.mobilepayments.MobilePaymentsSdk

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize with the Application ID (sandbox ID while developing)
        // MobilePaymentsSdk.initialize("sandbox-sq0idb-RRE7AohQniHsuQQzVMEfJg", this)
        MobilePaymentsSdk.initialize(getString(R.string.mpsdk_application_id), this)
    }
}
