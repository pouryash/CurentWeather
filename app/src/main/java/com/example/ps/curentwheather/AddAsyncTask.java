package com.example.ps.curentwheather;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.example.ps.curentwheather.Data.DAO;
import com.example.ps.curentwheather.Data.DBSchema;
import com.example.ps.curentwheather.Model.Weather;

import java.util.List;

public class AddAsyncTask extends AsyncTask<Void, Integer, Void> {

    private Context context;
    private List<Weather> weatherList;
    private DBSchema dbSchema;
    ProgressDialog progress;
    onInsertTaskFinished onInsertTaskFinished;

    public AddAsyncTask(Context context, List<Weather> weatherList,onInsertTaskFinished onInsertTaskFinished) {
        this.context = context;
        this.weatherList = weatherList;
        this.dbSchema = new DBSchema(context);
        this.onInsertTaskFinished = onInsertTaskFinished;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progress = new ProgressDialog(context);
//        progress.setMessage("Updating...");
//        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progress.setIndeterminate(true);
//        progress.setIndeterminate(false);
//        progress.setCanceledOnTouchOutside(false);
//        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progress.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ContentValues value = new ContentValues();


        for (int i = 0; i < weatherList.size(); i++) {
            value.put(DBSchema.COLUMN_WEATHER_TEMPRTURE, weatherList.get(i).getWeatherTemprature());
            value.put(DBSchema.COLUMN_TIME, weatherList.get(i).getTime());
            value.put(DBSchema.COLUMN_ICON, weatherList.get(i).getIcon());

            long id = dbSchema.getWritableDatabase().insert(dbSchema.TABLE_HOURS_WEATHER, null, value);

            publishProgress(((i+1) * 100) / weatherList.size());
        }
        dbSchema.getReadableDatabase().close();
        return null;
    }
    public Weather fakeData() {

        Weather weather = new Weather();
        weather.setCityName("sanandaj");
        weather.setWeatherName("sunny");
        weather.setWeatherDescription("sun");
        weather.setDay("sunday");
        weather.setMaxTemprature(39);
        weather.setMinTemprature(15);
        weather.setIcon("1d0");
        weather.setCountry("IR");
        weather.setPressure(10);
        weather.setHumidity(15);
        weather.setId(1);

        return weather;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
//        progress.dismiss();
        onInsertTaskFinished.onInsertTaskFinished();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
//        progress.setProgress(values[0]);
    }

    public interface onInsertTaskFinished{
        public void onInsertTaskFinished();
    }
}
