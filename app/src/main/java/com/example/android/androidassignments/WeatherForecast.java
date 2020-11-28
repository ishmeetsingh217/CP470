package com.example.android.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.xmlpull.v1.XmlPullParser.FEATURE_PROCESS_NAMESPACES;

public class WeatherForecast extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView min_Temp;
    private TextView max_Temp;
    private TextView currentTemp;
    ImageView currWeatherImg;
    Bitmap temp;
    private TextView currCity;
    List<String> cities;
    private Spinner citySelect;
    private static final String ACTIVITY_NAME = "WeatherForecast";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressBar = findViewById(R.id.progressBar);
        min_Temp = findViewById(R.id.minTemptxt);
        max_Temp = findViewById(R.id.maxTempTxt);
        currentTemp = findViewById(R.id.currTempTxt);
        currWeatherImg = findViewById(R.id.currWeatherImg);
        citySelect = findViewById(R.id.citySelect);
        currCity = findViewById(R.id.currCity);
        progressBar.setVisibility(View.VISIBLE);

        cities = Arrays.asList(getResources().getStringArray(R.array.cities));
        currCity.setText("Current City:");

        citySelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                new ForecastQuery(cities.get(i)).execute();
               // currWeatherImg.setImageBitmap();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    class ForecastQuery extends AsyncTask<String, Integer, String>{
        private String city;
        private String minTemp;
        private String maxTemp;
        private String currTemp;
        private Bitmap incomingPic;
        String imgName;
        private static final String API_KEY = "79cecf493cb6e52d25bb7b7050ff723c";
        private static final String API = "https://api.openweathermap.org/data/2.5/weather?";
        private static final String DATA_TYPE = "xml";

        public ForecastQuery(String city){
            this.city = city + ",CA";
        }

        private Bitmap getImage(String imageUrl){
            HttpURLConnection conn = null;
            try{
                URL url = new URL(imageUrl);
                conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                    Log.i(ACTIVITY_NAME,"Got inputStream");
                    return BitmapFactory.decodeStream(conn.getInputStream());
                }
                return null;
            }catch (Exception e){
                Log.e(ACTIVITY_NAME, e.toString());
                return null;
            }finally {
                if(conn != null)
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i(ACTIVITY_NAME + "-onPostExecute", "String we got: " + s);
//            try {
//                Bitmap temp = BitmapFactory.decodeStream(openFileInput(imgName));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            currWeatherImg.setImageBitmap(incomingPic);
            currentTemp.setText("Current temp: " + currTemp);
            min_Temp.setText("Min temp: " + minTemp);
            max_Temp.setText("Max temp: " + maxTemp);
            progressBar.setVisibility(View.INVISIBLE);
            //super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(ACTIVITY_NAME, "Values we got: " + values[0]);
            progressBar.setProgress(values[0]);
            //super.onProgressUpdate(values);

        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }

        @Override
        protected String doInBackground(String... strings) {

            String urlString = API + "q=" + this.city + "&APPID=" + API_KEY + "&mode=" + DATA_TYPE + "&units=metric";
            Log.i(ACTIVITY_NAME + "-doInBackground","Data: " + urlString);
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                InputStream incomingData = conn.getInputStream();
                try {
                    XmlPullParser xmlParser = Xml.newPullParser();
                    xmlParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    xmlParser.setInput(incomingData, null);
                    while(xmlParser.getEventType() != XmlPullParser.END_DOCUMENT ){ //Start iterating through the XML data
                        if(xmlParser.getEventType() == XmlPullParser.START_TAG) {
                            if (xmlParser.getName().equals("temperature")) { //TODO: Get temperature data
                                Log.i(ACTIVITY_NAME, "Getting temps");
                                currTemp = xmlParser.getAttributeValue(null, "value");
                                publishProgress(25);
                                minTemp = xmlParser.getAttributeValue(null, "min");
                                publishProgress(50);
                                maxTemp = xmlParser.getAttributeValue(null, "max");
                                publishProgress(75);
                            } else if (xmlParser.getName().equals("weather")) { //TODO: Get Icon for weather
                                imgName = xmlParser.getAttributeValue(null, "icon") + ".png"; //Add ".png" to end of string
                                Log.i(ACTIVITY_NAME,"IMAGE: " + imgName);
                                if (fileExistance(imgName)) {
                                    FileInputStream fileData = null;
                                    try {
                                        fileData = openFileInput(imgName);
                                        Log.i(ACTIVITY_NAME,"HELLO");
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i(ACTIVITY_NAME, "Found " + imgName);
                                    incomingPic = BitmapFactory.decodeStream(fileData); //Open photo from before
                                    //currWeatherImg.setImageBitmap(incomingPic);
                                } else {

                                    String imgUrl = "https://openweathermap.org/img/w/" + imgName;
                                    incomingPic = getImage(imgUrl);

                                    FileOutputStream newFile = openFileOutput(imgName, Context.MODE_PRIVATE);
                                    incomingPic.compress(Bitmap.CompressFormat.PNG, 100, newFile);
                                    Log.i(ACTIVITY_NAME,"Downloaded " + imgName);
                                    //currWeatherImg.setImageBitmap(incomingPic);
                                    newFile.flush();
                                    newFile.close(); //Close the file we just created
                                }
                                publishProgress(100);
                            }
                        }
                        xmlParser.next(); //Go to next object
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }finally {
                    conn.disconnect();
                    incomingData.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}