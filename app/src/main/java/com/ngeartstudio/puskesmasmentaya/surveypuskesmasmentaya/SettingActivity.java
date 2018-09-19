package com.ngeartstudio.puskesmasmentaya.surveypuskesmasmentaya;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
    TextView textPuasHari, textTidakPuasHari, textTotalHari, textTotalBulan, textPuasBulan, textTidakPuasBulan, textViewHarian, textViewBulanan;
    public String puas, tidakpuas, total;
    ImageView buttonBack;
    HorizontalBarChart chartharian, chartbulanan;
    private static final String TAG = "Survey";

    String url_lihatsurveyhari = Server.URL + "lihatsurveyhari.php";
    String url_lihatsurveybulan = Server.URL + "lihatsurveybulan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        textPuasHari = (TextView) findViewById(R.id.textPuas);
        textTidakPuasHari = (TextView) findViewById(R.id.textTidakPuas);
        textTotalHari = (TextView) findViewById(R.id.textTotal);
        textTotalBulan = (TextView) findViewById(R.id.textTotalBulan);
        textPuasBulan = (TextView) findViewById(R.id.textPuasBulan);
        textTidakPuasBulan = (TextView) findViewById(R.id.textTidakPuasBulan);
        buttonBack = (ImageView) findViewById(R.id.buttonBack);
        textViewHarian = (TextView) findViewById(R.id.textViewHarian);
        textViewBulanan = (TextView) findViewById(R.id.textViewBulanan);
        chartharian = (HorizontalBarChart) findViewById(R.id.chartharian);
        chartbulanan = (HorizontalBarChart) findViewById(R.id.chartbulanan);

        // custom description
        Description description = new Description();
        description.setText("Kepuasan");
        chartharian.setDescription(description);
        chartbulanan.setDescription(description);

        // hide legend
        chartharian.getLegend().setEnabled(false);
        chartbulanan.getLegend().setEnabled(false);

        chartharian.animateY(1000);
        chartharian.invalidate();
        chartbulanan.animateY(1000);
        chartbulanan.invalidate();

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat df2 = new SimpleDateFormat("MMM-yyyy");
        String formattedDate = df.format(c);
        String formattedDate2 = df2.format(c);


        textViewHarian.setText("HARIAN" + "("+ formattedDate +")");
        textViewBulanan.setText("BULANAN" + "("+ formattedDate2 +")");

        getSurveyBulan();
        getSurveyHari();
    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int intValue = (int) value;
            if (mValues.length > intValue && intValue >= 2) return mValues[intValue];

            return "";
        }

    }

    public void CreateGraphHarian(int puasHarian, int tidakPuasHarian) {
        BarDataSet set1;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(1, puasHarian);
        BarEntry v1e2 = new BarEntry(2, tidakPuasHarian);
        valueSet1.add(v1e1);
        valueSet1.add(v1e2);

        set1 = new BarDataSet(valueSet1, "Survey Kepuasan");

        set1.setColors(Color.parseColor("#F78B5D"), Color.parseColor("#A0C25A"));

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        // hide Y-axis
        YAxis left = chartharian.getAxisLeft();
        left.setDrawLabels(false);

        // custom X-axis labels
        String[] values = new String[] { "Puas", "Tidak Puas"};
        XAxis xAxis = chartharian.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(values));

        chartharian.setData(data);

    }

    public void CreateGraphBulan(int puasBulan, int tidakPuasBulan) {
        BarDataSet set2;
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(1, puasBulan);
        BarEntry v1e2 = new BarEntry(2, tidakPuasBulan);
        valueSet2.add(v1e1);
        valueSet2.add(v1e2);

        set2 = new BarDataSet(valueSet2, "Survey Kepuasan");

        set2.setColors(Color.parseColor("#F78B5D"), Color.parseColor("#A0C25A"));

        ArrayList<IBarDataSet> dataSets2 = new ArrayList<IBarDataSet>();
        dataSets2.add(set2);

        BarData data2 = new BarData(dataSets2);

        // hide Y-axis
        YAxis left = chartbulanan.getAxisLeft();
        left.setDrawLabels(false);

        // custom X-axis labels
        String[] values = new String[] { "Puas", "Tidak Puas"};
        XAxis xAxis2 = chartbulanan.getXAxis();
        xAxis2.setValueFormatter(new MyXAxisValueFormatter(values));

        chartbulanan.setData(data2);

    }

     private void getSurveyHari() {
        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                url_lihatsurveyhari, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DEBUGS", response.toString());
                try {
                    String puas = response.getJSONObject(0).getString("jumlah_puas");
                    String tidakpuas = response.getJSONObject(1).getString("jumlah_tidakpuas");
                    String total = response.getJSONObject(2).getString("jumlah_totalsurvey");
                    int puasint = Integer.parseInt(response.getJSONObject(0).getString("jumlah_puas"));
                    int tidakpuasint = Integer.parseInt(tidakpuas);
                    textPuasHari.setText(puas);
                    textTidakPuasHari.setText(tidakpuas);
                    textTotalHari.setText(total);
                    CreateGraphHarian(Integer.parseInt(response.getJSONObject(1).getString("jumlah_tidakpuas")), Integer.parseInt(response.getJSONObject(0).getString("jumlah_puas")));

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
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void getSurveyBulan() {
        JsonArrayRequest jsonObjReq2 = new JsonArrayRequest(Request.Method.GET,
                url_lihatsurveybulan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("DEBUGS", response.toString());
                try {
                    String puas = response.getJSONObject(0).getString("jumlah_puas");
                    String tidakpuas = response.getJSONObject(1).getString("jumlah_tidakpuas");
                    String total = response.getJSONObject(2).getString("jumlah_totalsurvey");
                    textPuasBulan.setText(puas);
                    textTidakPuasBulan.setText(tidakpuas);
                    textTotalBulan.setText(total);
                    CreateGraphBulan(Integer.parseInt(response.getJSONObject(1).getString("jumlah_tidakpuas")), Integer.parseInt(response.getJSONObject(0).getString("jumlah_puas")));

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
}
