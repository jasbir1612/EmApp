package com.androidtechies.model;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidtechies.emapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**<p>
 * Created by Angad on 11/07/2015.
 * </p>
 */
public class CustomerAdapter extends BaseAdapter
{   private ArrayList<CandidListData> listData;
    private Context context;

    public CustomerAdapter(Context context, ArrayList<CandidListData> listData)
    {   this.context=context;
        this.listData=listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public CandidListData getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.customer_item, parent, false);
        }
        AppCompatTextView cus=(AppCompatTextView)convertView.findViewById(R.id.cus);
//        AppCompatTextView phno=(AppCompatTextView)convertView.findViewById(R.id.phno);
//        AppCompatTextView email=(AppCompatTextView)convertView.findViewById(R.id.time);
        cus.setText("Name: "+listData.get(position).getName());
//        phno.setText("Phone No.: " + listData.get(position).getPhno());
//        double d=Double.parseDouble(listData.get(position).getEnterTime());
//        long stime=(long)(d*1000);
//        Log.e("MainActivity", stime + "");
//        Calendar cal=Calendar.getInstance();
//        cal.setTime(new Date(stime));
//        String hour=String.format("%02d",cal.get(Calendar.HOUR));
//        String minute=String.format("%02d", cal.get(Calendar.MINUTE));
//        String am=(cal.get(Calendar.AM_PM)==0?"AM":"PM");
//        String day=String.format("%02d",cal.get(Calendar.DAY_OF_MONTH));
//        String month=String.format("%02d",cal.get(Calendar.MONTH));
//        time.setText("Start Time: "+hour+":"+minute+" "+am+", "+day+"/"+month);
//        email.setText("Email" + listData.get(position).getEmail());
        return convertView;
    }
}