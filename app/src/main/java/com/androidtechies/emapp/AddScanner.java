package com.androidtechies.emapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**<p>
 * Created by Angad on 16/01/2016.
 * </p>
 */
public class AddScanner extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    String sid, name, email, phno, length, crosschest, chest, waist, hipp, armhole, shoulder, neck, seelves, salwar, mori, knee, calf;
    String theigh, blength, bchest, bwaist, bshoulder, bneck, bsleeves, bdadpoint;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);
        mScannerView.setAutoFocus(true);
        setContentView(mScannerView);

//        name = (getIntent().getStringExtra("name"));
//        phno = (getIntent().getStringExtra("phno"));
//        email = getIntent().getStringExtra("email");
//        length = getIntent().getStringExtra("length");
//        crosschest = (getIntent().getStringExtra("crosschest"));
//        chest = (getIntent().getStringExtra("chest"));
//        waist = (getIntent().getStringExtra("waist"));
//        hipp = (getIntent().getStringExtra("hipp"));
//        armhole = (getIntent().getStringExtra("armhole"));
//        shoulder = (getIntent().getStringExtra("shoulder"));
//        neck = (getIntent().getStringExtra("neck"));
//        seelves = (getIntent().getStringExtra("seelves"));
//        salwar = (getIntent().getStringExtra("salwar"));
//        mori = (getIntent().getStringExtra("mori"));
//        knee = (getIntent().getStringExtra("knee"));
//        calf = (getIntent().getStringExtra("calf"));
//        theigh = (getIntent().getStringExtra("theigh"));
//        blength = (getIntent().getStringExtra("blength"));
//        bchest = (getIntent().getStringExtra("bchest"));
//        bwaist = (getIntent().getStringExtra("bwaist"));
//        bshoulder = (getIntent().getStringExtra("bshoulder"));
//        bneck = (getIntent().getStringExtra("bneck"));
//        bsleeves = (getIntent().getStringExtra("bsleeves"));
//        bdadpoint = (getIntent().getStringExtra("bdadpoint"));




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
    {   //TODO: QR code scan result comes here
        //TODO: This is the scanned string
        String id = rawResult.getText();

        //TODO: Do a network call and store the measurements with name in database


        Intent I = new Intent(AddScanner.this, UniqueSid.class);
        setResult(Activity.RESULT_OK, I);
        I.putExtra("sid", id);
//        I.putExtra("name", name);
//        I.putExtra("phno", phno);
//        I.putExtra("email", email);
//        I.putExtra("length", length);
//        I.putExtra("crosschest", crosschest);
//        I.putExtra("chest", chest);
//        I.putExtra("waist", waist);
//        I.putExtra("hipp", hipp);
//        I.putExtra("armhole", armhole);
//        I.putExtra("shoulder", shoulder);
//        I.putExtra("neck", neck);
//        I.putExtra("seelves", seelves);
//        I.putExtra("salwar", salwar);
//        I.putExtra("mori", mori);
//        I.putExtra("knee", knee);
//        I.putExtra("calf", calf);
//        I.putExtra("theigh", theigh);
//        I.putExtra("blength", blength);
//        I.putExtra("bchest", bchest);
//        I.putExtra("bwaist", bwaist);
//        I.putExtra("bshoulder", bshoulder);
//        I.putExtra("bneck", bneck);
//        I.putExtra("bsleeves", bsleeves);
//        I.putExtra("bdadpoint", bdadpoint);
        Toast.makeText(AddScanner.this, id, Toast.LENGTH_SHORT).show();
        finish();
//        Log.e("name", "name is: "+name);
        startActivity(I);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScannerView.stopCameraPreview();
    }
}