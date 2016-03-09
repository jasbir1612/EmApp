package com.androidtechies.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Angad on 09/03/2016.
 */
public class CandidListAdapter extends BaseAdapter
{   private ArrayList<CandidListData> listItem=new ArrayList<>();
    private Context context;

    public CandidListAdapter(ArrayList<CandidListData> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public CandidListData getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
