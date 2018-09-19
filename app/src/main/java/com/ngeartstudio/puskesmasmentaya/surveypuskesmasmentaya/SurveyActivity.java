package com.ngeartstudio.puskesmasmentaya.surveypuskesmasmentaya;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.GifTextView;

public class SurveyActivity extends AppCompatActivity {
   // ImageView imageTidakPuas;
    GifTextView imagePuas, imageTidakPuas;
    TextView textJam, textTanggal, textPuas, textTidakPuas;
    int success;
    String url_tambah = Server.URL + "tambah.php";
    String url_lihatsurvey = Server.URL + "lihatsurvey.php";
    String url_lihatsurveytidakpuas = Server.URL + "lihatsurveytidakpuas.php";
    String tag_json_obj = "json_obj_req", survey, formattedDate, localTime,tanggal;
    private static final String TAG = "Survey";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imagePuas = (GifTextView) findViewById(R.id.imagePuas);
        imageTidakPuas=(GifTextView) findViewById(R.id.imageTidakPuas);
        textJam =(TextView) findViewById(R.id.textJam);
        textTanggal = (TextView) findViewById(R.id.textTanggal);
        textPuas = (TextView) findViewById(R.id.textPuas);
        textTidakPuas = (TextView) findViewById(R.id.textTidakPuas);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy.MM.dd");
        formattedDate = df.format(c);
        tanggal = df2.format(c);

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        // you can get seconds by adding  "...:ss" to it
        date.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));

        localTime = date.format(currentLocalTime);

        textTanggal.setText(formattedDate);
        textJam.setText(localTime);
        getSurvey();
//        getTidakPuas();

        imagePuas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                survey = "PUAS";
                update();
                Intent intent = new Intent(SurveyActivity.this, ThanksActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageTidakPuas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                survey = "TIDAK PUAS";
                update();
                Intent intent = new Intent(SurveyActivity.this, ThanksActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getSurvey() {
        JsonArrayRequest jsonObjReq2 = new JsonArrayRequest(Request.Method.GET,
                url_lihatsurvey, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DEBUGS", response.toString());
                try {
                    String puas = response.getJSONObject(0).getString("jumlah_puas");
                    String tidakpuas = response.getJSONObject(1).getString("jumlah_tidakpuas");
                    //String total = response.getJSONObject(2).getString("jumlah_totalsurvey");
                    //int puasint = Integer.parseInt(response.getJSONObject(0).getString("jumlah_puas"));
                    //int tidakpuasint = Integer.parseInt(tidakpuas);
                    textPuas.setText(puas);
                    textTidakPuas.setText(tidakpuas);
                    //CreateGraphHarian(150, 100);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),"Gagal Dapat Data", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq2);
    }


    // fungsi untuk tambah data
    private void update() {
        // jika id kosong maka simpan, jika id ada nilainya maka ubah
        String url = url_tambah;

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.d(TAG, "Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Cek error pada json
                    if (success == 1) {
                        Log.d("Add/update", jObj.toString());
                        //Toast.makeText(SurveyActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(SurveyActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(SurveyActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Calendar cal = Calendar.getInstance();
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("HH:mm a");
                // Posting parameter ke post url
                Map<String, String> params = new HashMap<String, String>();
                    params.put("survey", survey);
                    params.put("jam", date.format(currentLocalTime));
                    params.put("tanggal", tanggal);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }
}
