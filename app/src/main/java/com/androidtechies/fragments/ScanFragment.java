package com.androidtechies.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidtechies.emapp.QRScannerActivity;
import com.androidtechies.emapp.R;

/**
 * Created by Angad on 09/03/2016.
 */
public class ScanFragment extends Fragment {
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_entered,container,false);
        Intent I=new Intent(context, QRScannerActivity.class);
        context.startActivity(I);
        //Start Activity for Result
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }
}
