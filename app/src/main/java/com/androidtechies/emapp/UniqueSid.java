package com.androidtechies.emapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.androidtechies.model.CandidListData;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidtechies.model.CandidListData;
import com.androidtechies.model.CustomerAdapter;
import com.androidtechies.model.ListAdapter;
import com.androidtechies.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UniqueSid extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    int id;
    private RelativeLayout parent;
    private Context context;
    int i = 0;



    String url;

    TextView tsid, tname, tphno, temail, tlenght, tcrosschest, tchest, twaist, thip, tarmhole, tshoulder, tneck, tsleeves, tsalwar, tmori;
    TextView tknee, tcalf, ttheigh, tblength, tbchest, tbwaist, tbshoulder, tbneck, tbsleeves, tbdaatpoint;
//    private ListView listView;
//    private SwipeRefreshLayout swipeRefreshLayout;

    String  sid, name, email, phno, length, crosschest, chest, waist, hipp, armhole, shoulder, neck, seelves, salwar, mori, knee, calf;
    String theigh, blength, bchest, bwaist, bshoulder, bneck, bsleeves, bdadpoint;
//    Context context;
    private final int RESPONSE_TIME=10000;
    private ProgressDialog pdialog;

    ArrayList<CandidListData> abc;

    String []ar = {sid, name, email, phno};
    ArrayAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unique_sid);
//        listView = (ListView) findViewById(R.id.cus_list);

        context = this;
        tsid = (TextView) findViewById(R.id.tsid);
        tname = (TextView) findViewById(R.id.tname);
        tphno = (TextView) findViewById(R.id.tphno);
        temail = (TextView) findViewById(R.id.temail);
        tlenght = (TextView) findViewById(R.id.tlenght);
        tcrosschest= (TextView) findViewById(R.id.tcrosschest);
        tchest = (TextView) findViewById(R.id.tchest);
        twaist = (TextView) findViewById(R.id.twaist);
        thip = (TextView) findViewById(R.id.thipp);
        tarmhole = (TextView) findViewById(R.id.tarmhole);
        tshoulder = (TextView) findViewById(R.id.tshoulder);
        tneck = (TextView) findViewById(R.id.tneck);
        tsleeves = (TextView) findViewById(R.id.tseelves);
        tsalwar = (TextView) findViewById(R.id.tsalwar);
        tmori = (TextView) findViewById(R.id.tmori);
        tknee= (TextView) findViewById(R.id.tknee);
        tcalf = (TextView) findViewById(R.id.tcalf);
        ttheigh = (TextView) findViewById(R.id.ttheigh);
        tblength = (TextView) findViewById(R.id.tblength);
        tbchest = (TextView) findViewById(R.id.tbchest);
        tbwaist = (TextView) findViewById(R.id.tbwaist);
        tbshoulder = (TextView) findViewById(R.id.tbshoulder);
        tbneck = (TextView) findViewById(R.id.tbneck);
        tbsleeves = (TextView) findViewById(R.id.tbsleeves);
        tbdaatpoint = (TextView) findViewById(R.id.tbdaatpoint);




//        context = this;
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
//        swipeRefreshLayout.setOnRefreshListener(this);

//        uniqueid = (TextView) findViewById(R.id.sid);

        sid = getIntent().getStringExtra("sid");
        tsid.setText(sid);
//        id = Integer.parseInt(sid);


        final String url="http://bindyaboutique.com/AndroidData/GetAll.php";
        pdialog=new ProgressDialog(context);
        pdialog.setMessage("Loading...");
        pdialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                candidListData = new ArrayList<>();
                if(pdialog.isShowing())
                {   pdialog.dismiss();
                }
                try {
                    Log.e("volley", ""+url);
                    JSONArray array = new JSONArray(response);
                    for (int i = 0 ; i < array.length(); i++){
                        CandidListData data = new CandidListData(sid, name, email, phno, length, crosschest, chest, waist, hipp, armhole, shoulder, neck, seelves, salwar, mori, knee, calf, theigh, blength, bchest, bwaist, bshoulder, bneck, bsleeves, bdadpoint);
                        JSONObject object = array.optJSONObject(i);
                        data.setId(object.getString("sid"));
                        data.setName(object.getString("name"));
                        data.setPhno(object.getString("phno"));
                        data.setEmail(object.getString("email"));
                        data.setLength(object.getString("lenght"));
                        data.setCrosschest(object.getString("crosschest"));
                        data.setChest(object.getString("chest"));
                        data.setWaist(object.getString("waist"));
                        data.setHipp(object.getString("hipp"));
                        data.setArmhole(object.getString("armhole"));
                        data.setShoulder(object.getString("shoulder"));
                        data.setNeck(object.getString("neck"));
                        data.setSeelves(object.getString("seelves"));
                        data.setSalwar(object.getString("salwar"));
                        data.setMori(object.getString("mori"));
                        data.setKnee(object.getString("knee"));
                        data.setCalf(object.getString("calf"));
                        data.setTheigh(object.getString("theigh"));
                        data.setBlength(object.getString("blenght"));
                        data.setBchest(object.getString("bchest"));
                        data.setBWaist(object.getString("bwaist"));
                        data.setBShoulder(object.getString("bshoulder"));
                        data.setBNeck(object.getString("bneck"));
                        data.setBSleeves(object.getString("bsleeves"));
                        data.setBdadpoint(object.getString("bdaatpoint"));




                        sid = object.getString("sid");
                        name = (object.getString("name"));
                        phno = (object.getString("phno"));
                        email = object.getString("email");
                        length = (object.getString("lenght"));
                        crosschest = (object.getString("crosschest"));
                        chest = (object.getString("chest"));
                        waist = (object.getString("waist"));
                        hipp = (object.getString("hipp"));
                        armhole = (object.getString("armhole"));
                        shoulder = (object.getString("shoulder"));
                        neck = (object.getString("neck"));
                        seelves = (object.getString("seelves"));
                        salwar = (object.getString("salwar"));
                        mori = (object.getString("mori"));
                        knee = (object.getString("knee"));
                        calf = (object.getString("calf"));
                        theigh = (object.getString("theigh"));
                        blength = (object.getString("blenght"));
                        bchest = (object.getString("bchest"));
                        bwaist = (object.getString("bwaist"));
                        bshoulder = (object.getString("bshoulder"));
                        bneck = (object.getString("bneck"));
                        bsleeves = (object.getString("bsleeves"));
                        bdadpoint = (object.getString("bdaatpoint"));

                        Log.d("abbc", " " +sid +name +email +phno +length +crosschest +chest +waist +hipp +armhole +shoulder +neck +seelves +salwar +mori +knee +calf +theigh +blength +bchest +bwaist +bshoulder +bneck +bsleeves +bdadpoint);


//                        candidListData.add(data);
                    }
//                    listView.setAdapter(new ListAdapter(context, candidListData));
//                    listView.setOnItemClickListener(MainActivity.this);
//                    Snackbar snackbar = Snackbar.make(parent, "List Updated", Snackbar.LENGTH_LONG);
//                    snackbar.show();
                }
                catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, "E"+ error, Toast.LENGTH_LONG).show();
                if(pdialog.isShowing())
                {   pdialog.dismiss();
                }
                try {
                    Snackbar snackbar = Snackbar.make(parent, "Failed To Fetch Data. Pull Down to Retry", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                catch(Exception e)
                {   e.printStackTrace();
                }
            }
        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
////                params.put("eventid",eventid);
//                return params;
//            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(RESPONSE_TIME, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue().add(request);


        StringRequest scannerRequest = new StringRequest(Request.Method.POST, "url",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("sid", sid);
                return params;
            }
        };

        VolleySingleton.getInstance(this).getRequestQueue().add(scannerRequest);



//        sid = getIntent().getStringExtra("sid");
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
//        Log.e("abbccc", " " +sid +name +email +phno +length +crosschest +chest +waist +hipp +armhole +shoulder +neck +seelves +salwar +mori +knee +calf +theigh +blength +bchest +bwaist +bshoulder +bneck +bsleeves +bdadpoint);
//
//






//        abc = new ArrayList<>();
//
//        CandidListData a = new CandidListData(sid, name, email, phno, length, crosschest, chest, waist, hipp, armhole, shoulder, neck, seelves, salwar, mori, knee, calf, theigh, blength, bchest, bwaist, bshoulder, bneck, bsleeves, bdadpoint);
//        a.setId(sid);
//        a.setName(name);
//        a.setEmail(email);
//        a.setPhno(phno);
//        a.setLength(length);
//        a.setCrosschest(crosschest);
//        a.setChest(chest);
//        a.setWaist(waist);
//        a.setHipp(hipp);
//        a.setArmhole(armhole);
//        a.setShoulder(shoulder);
//        a.setNeck(neck);
//        a.setSeelves(seelves);
//        a.setSalwar(salwar);
//        a.setMori(mori);
//        a.setKnee(knee);
//        a.setCalf(calf);
//        a.setTheigh(theigh);
//        a.setBlength(blength);
//        a.setBchest(bchest);
//        a.setBWaist(bwaist);
//        a.setBShoulder(bshoulder);
//        a.setBNeck(bneck);
//        a.setBSleeves(bsleeves);
//        a.setBdadpoint(bdadpoint);
//
//
//        abc.add(a);
//
//        Log.d("context", "" +abc);
//
//        listView.setAdapter(new CustomerAdapter(context, abc));
//        Toast.makeText(UniqueSid.this, "the name is: "+name, Toast.LENGTH_SHORT).show();

//        uniqueid.setText(sid);


    }

    @Override
    public void onRefresh() {

    }
}

//sid, name, email, phno, length, crosschest, chest, waist, hipp, armhole, shoulder, neck, seelves, salwar, mori, knee, calf, theigh, blength, bchest, bwaist, bshoulder, bneck, bsleeves, bdadpoint;