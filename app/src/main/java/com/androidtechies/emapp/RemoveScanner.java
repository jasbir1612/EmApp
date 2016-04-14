package com.androidtechies.emapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**<p>
 * Created by Angad on 21/03/2016.
 * </p>
 */
public class RemoveScanner extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);
        mScannerView.setAutoFocus(true);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult)
    {   Intent returnIntent = new Intent();
        String id = rawResult.getText();
        setResult(Activity.RESULT_OK, returnIntent);
        returnIntent.putExtra("gatesid", id);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScannerView.stopCameraPreview();
    }
}