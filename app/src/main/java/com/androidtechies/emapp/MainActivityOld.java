package com.androidtechies.emapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

public class MainActivityOld extends AppCompatActivity implements AdapterView.OnItemClickListener,SwipeRefreshLayout.OnRefreshListener
{   private int ADD_CODE=1,REMOVE_CODE=2;
    private Context context;
    private ListView listView;
    private RelativeLayout parent;
    private SharedPreferences sharedpreferences;
    private String gatesid,eventid="NoEvent";
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
        sharedpreferences = getSharedPreferences("eventid", Context.MODE_PRIVATE);
        eventid=sharedpreferences.getString("emkey","NoEvent");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final AppCompatEditText id = new AppCompatEditText(context);
                id.setHint("Enter ID Here");
                builder.setTitle("Enter: Gates ID");
                builder.setView(id);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gatesid = id.getText().toString();
                        if (gatesid.equals("")) {
                            try {
                                Snackbar snack = Snackbar.make(parent, "Cannot Be Blank", Snackbar.LENGTH_LONG);
                                snack.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {   //Make add request
                            pdialog=new ProgressDialog(context);
                            pdialog.setMessage("Loading...");
                            pdialog.show();
                            String url = "http://emapi.herokuapp.com/sofmsvnjfskdfjs";
                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
//                                    Log.e(TAG, response);
                                    if(pdialog.isShowing())
                                    {   pdialog.dismiss();
                                    }
                                    try {
                                        JSONObject object = new JSONObject(response);
                                        String message = object.getString("message");
                                        try {
                                            Snackbar snackbar = Snackbar.make(parent, message+". Pull down to see changes.", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        } catch (Exception e1) {
                                            e1.printStackTrace();
//                                            Toast.makeText(context, e1.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
//                                        Log.v(TAG, "" + e);
//                                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                                        try {
                                            Snackbar snackbar = Snackbar.make(parent, "Oops, Something went wrong.", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        } catch (Exception e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if(pdialog.isShowing())
                                    {   pdialog.dismiss();
                                    }
//                                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                                    try {
                                        Snackbar snackbar = Snackbar.make(parent, "Oops, Something went wrong.", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("gatesid", gatesid);
                                    params.put("eventid", eventid);
                                    return params;
                                }
                            };
                            request.setRetryPolicy(new DefaultRetryPolicy(RESPONSE_TIME, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue().add(request);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.setNeutralButton("Scan QR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent I = new Intent(MainActivityOld.this, AddScanner.class);
                        startActivityForResult(I, ADD_CODE);
                    }
                });
                AppCompatDialog dialog = builder.create();
                dialog.show();
            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        String url="http://emapi.herokuapp.com/xfdfgdfg";
        pdialog=new ProgressDialog(context);
        pdialog.setMessage("Loading...");
        pdialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.e(TAG, response);
                candidListData=new ArrayList<>();
                if(pdialog.isShowing())
                {   pdialog.dismiss();
                }
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0 ; i < array.length(); i++){
                        CandidListData data=new CandidListData();
                        JSONObject object = array.optJSONObject(i);
                        data.setId(object.getString("gatesid"));
                        data.setName(object.getString("name"));
                        data.setPhno(object.getString("contact"));
                        data.setEnterTime(object.getString("stime"));
                        candidListData.add(data);
                    }
                    listView.setAdapter(new ListAdapter(context, candidListData));
                    listView.setOnItemClickListener(MainActivityOld.this);
                }
                catch (JSONException e) {
//                    Log.v(TAG, "" + e);
//                    Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("eventid",eventid);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(RESPONSE_TIME, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue().add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==AppCompatActivity.RESULT_OK)
        {   if(requestCode==ADD_CODE)
            {   //Toast.makeText(this,"ADD:"+data.getStringExtra("gatesid")+":"+eventid,Toast.LENGTH_LONG).show();
                gatesid=data.getStringExtra("gatesid");
                String url="http://emapi.herokuapp.com/sofmsvnjfskdfjs";
                pdialog=new ProgressDialog(context);
                pdialog.setMessage("Loading...");
                pdialog.show();
                StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e(TAG, response);
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
//                            Log.v(TAG, "" + e);
                            try {
                                Snackbar snackbar = Snackbar.make(parent, "Oops, Something went wrong.", Snackbar.LENGTH_LONG);
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
                            Snackbar snackbar = Snackbar.make(parent, "Oops, Something went wrong.", Snackbar.LENGTH_LONG);
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
                        params.put("gatesid",gatesid);
                        params.put("eventid",eventid);
                        return params;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(RESPONSE_TIME, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue().add(request);
            }
            if(requestCode==REMOVE_CODE)
            {   //Toast.makeText(this,"REMOVE:"+data.getStringExtra("gatesid"), Toast.LENGTH_LONG).show();
                gatesid=data.getStringExtra("gatesid");
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
                                Snackbar snackbar = Snackbar.make(parent, "Oops, Something went wrong.", Snackbar.LENGTH_LONG);
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
                            Snackbar snackbar = Snackbar.make(parent, "Oops, Something went wrong.", Snackbar.LENGTH_LONG);
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
                        params.put("gatesid",gatesid);
                        params.put("eventid",eventid);
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
                                    else
                                    {   SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("emkey",emid);
                                        editor.apply();
                                        eventid=emid;
                                        try
                                        {   Snackbar snack=Snackbar.make(parent,"ID Applied. Pull down to see changes.",Snackbar.LENGTH_LONG);
                                            snack.show();
                                        }
                                        catch(Exception e)
                                        {   e.printStackTrace();
                                        }
                                    }
                                }
                            });
                            builder.setNegativeButton("Cancel", null);
                            AppCompatDialog dialog=builder.create();
                            dialog.show();
                            break;

            case R.id.delete:   builder=new AlertDialog.Builder(context);
                                final AppCompatEditText id1=new AppCompatEditText(context);
                                id1.setHint("Enter ID Here");
                                builder.setTitle("Exit: Gates ID");
                                builder.setView(id1);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        gatesid = id1.getText().toString();
                                        if(gatesid.equals(""))
                                        {   try
                                            {   Snackbar snack=Snackbar.make(parent,"Cannot Be Blank",Snackbar.LENGTH_LONG);
                                                snack.show();
                                            }
                                            catch(Exception e)
                                            {   e.printStackTrace();
                                            }
                                        }
                                        else
                                        {   String url="http://emapi.herokuapp.com/jhgdfjs";
                                            pdialog=new ProgressDialog(context);
                                            pdialog.setMessage("Loading...");
                                            pdialog.show();
                                            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    if(pdialog.isShowing())
                                                    {   pdialog.dismiss();
                                                    }
//                                                    Log.e(TAG, response);
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
//                                                        Log.v(TAG, "" + e);
                                                        try {
                                                            Snackbar snackbar = Snackbar.make(parent, "Oops, Something went wrong.", Snackbar.LENGTH_LONG);
                                                            snackbar.show();
                                                        }
                                                        catch (Exception e1)
                                                        {   e1.printStackTrace();
                                                        }
                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    if(pdialog.isShowing())
                                                    {   pdialog.dismiss();
                                                    }
                                                    try {
                                                        Snackbar snackbar = Snackbar.make(parent, "Oops, Something went wrong.", Snackbar.LENGTH_LONG);
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
                                                    params.put("gatesid",gatesid);
                                                    params.put("eventid",eventid);
                                                    return params;
                                                }
                                            };
                                            request.setRetryPolicy(new DefaultRetryPolicy(RESPONSE_TIME, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                            VolleySingleton.getInstance(context.getApplicationContext()).getRequestQueue().add(request);
                                        }
                                    }
                                });
                                builder.setNegativeButton("Cancel", null);
                                builder.setNeutralButton("Scan QR", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent I = new Intent(MainActivityOld.this, RemoveScanner.class);
                                        startActivityForResult(I, REMOVE_CODE);
                                    }
                                });
                                dialog=builder.create();
                                dialog.show();
                                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        Thread T1=new Thread(new Runnable() {
            @Override
            public void run() {
                String url="http://emapi.herokuapp.com/xfdfgdfg";
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.e(TAG, response);
                        candidListData=new ArrayList<>();
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0 ; i < array.length(); i++){
                                CandidListData data=new CandidListData();
                                JSONObject object = array.optJSONObject(i);
                                data.setId(object.getString("gatesid"));
                                data.setName(object.getString("name"));
                                data.setPhno(object.getString("contact"));
                                data.setEnterTime(object.getString("stime"));
                                candidListData.add(data);
                            }
                            listView.setOnItemClickListener(MainActivityOld.this);
                            swipeRefreshLayout.setOnRefreshListener(MainActivityOld.this);
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
                        params.put("eventid",eventid);
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