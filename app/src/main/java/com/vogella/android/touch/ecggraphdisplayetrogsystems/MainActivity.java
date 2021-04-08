package com.vogella.android.touch.ecggraphdisplayetrogsystems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> ecgData;
    String link;
    String txt;
    String[] txt2;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        link = intent.getStringExtra("url");

        //Downloading data
        new DownloadTextFromWebTask().execute(link);

        //Design data in graph using GraphView library
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(txt == null)
                {
                    try {
                        Thread.sleep(3000);
                    }catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        txt2 = txt.split(",");
                        int size = txt2.length;
                        double y,x;

                        //The data is measured in 200 Hz, therefore every x is 0.005 sec
                        x = -0.005;

                        GraphView graphView = findViewById(R.id.graph_view);
                        graphView.getViewport().setXAxisBoundsManual(true);
                        graphView.getViewport().setMinX(0);
                        graphView.getViewport().setMaxX(10);
                        graphView.getViewport().setYAxisBoundsManual(true);
                        graphView.getViewport().setMinY(1000);
                        graphView.getViewport().setMaxY(3200);
                        graphView.getViewport().setScalable(true);

                        ecgData = new LineGraphSeries<DataPoint>();

                        for(int i=0; i<size; i++)
                        {
                            x += 0.005;
                            y = Double.parseDouble(txt2[i]);
                            ecgData.appendData(new DataPoint(x,y), true, size);
                        }

                        graphView.addSeries(ecgData);

                    }
                });

            }
        }).start();

         }

         //Class for downloading the data from url

         private class DownloadTextFromWebTask extends AsyncTask<String,Void,String> {

             @Override
             protected void onPreExecute() {
                 super.onPreExecute();

             }

             @Override
             protected void onPostExecute(String s) {
                 super.onPostExecute(s);

                 txt = s;

             }

             @Override
             protected String doInBackground(String... strings) {

                 //Using the private method below
                 InputStream inputStream = openGetConnection(strings[0]);

                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                 StringBuffer sb = new StringBuffer();
                 String current;

                 try {
                     while((current = bufferedReader.readLine()) != null)
                     {
                         sb.append(current);
                     }

                 }catch (IOException ex)
                 {
                     ex.printStackTrace();
                 }
                 return sb.toString();
             }
         }

    private InputStream openGetConnection(String urlStr)
    {
        InputStream inputStream = null;
        try {

            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }
}
