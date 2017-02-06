package com.androidtechies.emapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.androidtechies.model.CandidListData;
import com.androidtechies.model.ListAdapter;
import com.androidtechies.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener
{   private int ADD_CODE=1,REMOVE_CODE=2;
    private Context context;
    private ListView listView;
    private RelativeLayout parent;
    private SharedPreferences sharedpreferences;
    String sid, name, email, phno, length, crosschest, chest, waist, hipp, armhole, shoulder, neck, seelves, salwar, mori, knee, calf;
    String theigh, blength, bchest, bwaist, bshoulder, bneck, bsleeves, bdadpoint;
    //private String TAG="MainActivity";
    private ArrayList<CandidListData> candidListData;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog pdialog;
    private final int RESPONSE_TIME=10000;


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        parent=(RelativeLayout)findViewById(R.id.parent);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        Drawable overflow;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            overflow = getResources().getDrawable(R.drawable.ic_action_overflow,getTheme());
        }
        else
        {   overflow=getResources().getDrawable(R.drawable.ic_action_overflow);
        }
        if(overflow!=null)
        {   toolbar.setOverflowIcon(overflow);
        }
        listView=(ListView)findViewById(R.id.list);
        FloatingActionButton add=(FloatingActionButton)findViewById(R.id.add);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
//        sharedpreferences = getSharedPreferences("eventid", Context.MODE_PRIVATE);
//        eventid=sharedpreferences.getString("emkey","NoEvent");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(MainActivity.this, AddScanner.class);
//                I.putExtra()
                startActivityForResult(I, ADD_CODE);
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        final String url="http://bindyaboutique.com/AndroidData/GetAll.php";
        pdialog=new ProgressDialog(context);
        pdialog.setMessage("Loading...");
        pdialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                candidListData = new ArrayList<>();
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


                        candidListData.add(data);
                    }
                    listView.setAdapter(new ListAdapter(context, candidListData));
                    listView.setOnItemClickListener(MainActivity.this);
                        Snackbar snackbar = Snackbar.make(parent, "List Updated", Snackbar.LENGTH_LONG);
                        snackbar.show();
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
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I = new Intent(MainActivity.this, AddScanner.class);
                I.putExtra("sid", sid);
                I.putExtra("name", name);
                I.putExtra("phno", phno);
                I.putExtra("email", email);
                I.putExtra("length", length);
                I.putExtra("crosschest", crosschest);
                I.putExtra("chest", chest);
                I.putExtra("waist", waist);
                I.putExtra("hipp", hipp);
                I.putExtra("armhole", armhole);
                I.putExtra("shoulder", shoulder);
                I.putExtra("neck", neck);
                I.putExtra("seelves", seelves);
                I.putExtra("salwar", salwar);
                I.putExtra("mori", mori);
                I.putExtra("knee", knee);
                I.putExtra("calf", calf);
                I.putExtra("theigh", theigh);
                I.putExtra("blength", blength);
                I.putExtra("bchest", bchest);
                I.putExtra("bwaist", bwaist);
                I.putExtra("bshoulder", bshoulder);
                I.putExtra("bneck", bneck);
                I.putExtra("bsleeves", bsleeves);
                I.putExtra("bdadpoint", bdadpoint);

                startActivityForResult(I, ADD_CODE);
            }
        });
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==AppCompatActivity.RESULT_OK)
        {   if(requestCode==ADD_CODE)
            {   //Toast.makeText(this,"ADD:"+data.getStringExtra("sid")+":"+eventid,Toast.LENGTH_LONG).show();
                sid=data.getStringExtra("sid");
                String url="http://bindyaboutique.com/AndroidData/GetAll.php";
                pdialog=new ProgressDialog(context);
                pdialog.setMessage("Loading...");
                pdialog.show();
                StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("test2", response);
                        if(pdialog.isShowing())
                        {   pdialog.dismiss();
                        }
                        try
                        {   JSONObject object = new JSONObject(response);
                            String message=object.getString("message");
                            try {
                                Snackbar snackbar = Snackbar.make(parent, message+". Pull down to see changes.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                            catch (Exception e1)
                            {   e1.printStackTrace();
                            }
                        } catch (JSONException e) {
                            Log.e("test1 ", "" + e);
                            try {
                                Snackbar snackbar = Snackbar.make(parent, "Oops1, Something went wrong.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                            catch (Exception e1)
                            {   e1.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(pdialog.isShowing())
                        {   pdialog.dismiss();
                        }
                        try {
                            Snackbar snackbar = Snackbar.make(parent, "Oops2, Something went wrong.", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        catch(Exception e)
                        {   e.printStackTrace();
                        }
                    }
                })
                {   @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("sid",sid);
//                        params.put("eventid",eventid);
                        return params;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(RESPONSE_TIME, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue().add(request);
            }




            if(requestCode==REMOVE_CODE)
            {   //Toast.makeText(this,"REMOVE:"+data.getStringExtra("sid"), Toast.LENGTH_LONG).show();
                sid=data.getStringExtra("sid");
                pdialog=new ProgressDialog(context);
                pdialog.setMessage("Loading...");
                pdialog.show();
                String url="http://emapi.herokuapp.com/jhgdfjs";
                StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e(TAG, response);
                        if(pdialog.isShowing())
                        {   pdialog.dismiss();
                        }
                        try {
                            JSONObject object = new JSONObject(response);
                            String message=object.getString("message");
                            try {
                                Snackbar snackbar = Snackbar.make(parent, message+". Pull down to see changes.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                            catch (Exception e1)
                            {   e1.printStackTrace();
                            }
                        } catch (JSONException e) {
//                            Log.v(TAG, "" + e);
                            try {
                                Snackbar snackbar = Snackbar.make(parent, "Oops3, Something went wrong.", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                            catch (Exception e1)
                            {   e1.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(pdialog.isShowing())
                        {   pdialog.dismiss();
                        }
                        try {
                            Snackbar snackbar = Snackbar.make(parent, "Oops4, Something went wrong.", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        catch(Exception e)
                        {   e.printStackTrace();
                        }
                    }
                })
                {   @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("sid",sid);
//                        params.put("eventid",eventid);
                        return params;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(RESPONSE_TIME, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue().add(request);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent1, View view, int position, long id1) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {   MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {   case R.id.cred: AlertDialog.Builder builder=new AlertDialog.Builder(context);
                            final AppCompatEditText id=new AppCompatEditText(context);
                            id.setHint("Enter Event ID Here");
                            builder.setTitle("Event ID");
                            builder.setView(id);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String emid = id.getText().toString();
                                    if(emid.equals(""))
                                    {   try
                                        {   Snackbar snack=Snackbar.make(parent,"Cannot Be Blank",Snackbar.LENGTH_LONG);
                                            snack.show();
                                        }
                                        catch(Exception e)
                                        {   e.printStackTrace();
                                        }
                                    }
//                                    else
//                                    {   SharedPreferences.Editor editor = sharedpreferences.edit();
//                                        editor.putString("emkey",emid);
//                                        editor.apply();
//                                        eventid=emid;
//                                        try
//                                        {   Snackbar snack=Snackbar.make(parent,"ID Applied. Pull down to see changes.",Snackbar.LENGTH_LONG);
//                                            snack.show();
//                                        }
//                                        catch(Exception e)
//                                        {   e.printStackTrace();
//                                        }
//                                    }
                                }
                            });
                            builder.setNegativeButton("Cancel", null);
                            AppCompatDialog dialog=builder.create();
                            dialog.show();
                            break;

            case R.id.delete:   Intent I = new Intent(MainActivity.this, RemoveScanner.class);
                                startActivityForResult(I, REMOVE_CODE);
                                break;

            case R.id.htu:
                            break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        Thread T1=new Thread(new Runnable() {
            @Override
            public void run() {
                String url="http://bindyaboutique.com/AndroidData/GetAll.php";
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e(TAG, response);
                        candidListData=new ArrayList<>();
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0 ; i < array.length(); i++){
                                CandidListData data=new CandidListData(sid, name, email, phno, length, crosschest, chest, waist, hipp, armhole, shoulder, neck, seelves, salwar, mori, knee, calf, theigh, blength, bchest, bwaist, bshoulder, bneck, bsleeves, bdadpoint);
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
//
                                candidListData.add(data);
                            }
                            try {
                                Snackbar snackbar = Snackbar.make(parent, "List Updated", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                            catch(Exception e)
                            {   e.printStackTrace();
                            }
                            listView.setOnItemClickListener(MainActivity.this);
                            swipeRefreshLayout.setOnRefreshListener(MainActivity.this);
                        }
                        catch (JSONException e) {
//                            Log.v(TAG, "" + e);
                            try{
                                JSONObject object = new JSONObject(response);
                                String message=object.getString("message");
                                try {
                                    Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                                catch (Exception e1)
                                {   e1.printStackTrace();
                                }
                            }
                            catch (JSONException e1)
                            {   e1.printStackTrace();
                                try {
                                    Snackbar snackbar = Snackbar.make(parent, "Failed To Fetch Data. Pull Down to Retry", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                                catch (Exception e2)
                                {   e2.printStackTrace();
                                }
                            }
                        }
                        finally
                        {   listView.setAdapter(new ListAdapter(context, candidListData));
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(context, error.toString(),Toast.LENGTH_LONG).show();
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
//                        Toast.makeText(context, "E"+ error, Toast.LENGTH_LONG).show();
                        try {
                            Snackbar snackbar = Snackbar.make(parent, "Failed To Fetch Data. Pull Down to Retry", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                        catch(Exception e)
                        {   e.printStackTrace();
                        }
                    }
                })
                {   @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
//                        params.put("eventid",eventid);
                        return params;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(RESPONSE_TIME, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue().add(request);
            }
        });
        T1.start();
    }
}