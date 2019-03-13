package com.insegno.luca.currencyconverter;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    double fattoreDiCambio = 0;
    public void convertiValuta (View view){
        Log.i("VALUTA", "funziona");
        try {
            URL url = new URL("https://free.currencyconverterapi.com/api/v6/convert?q=EUR_USD&compact=ultra&apiKey=87784f8a93d46ca27ec5");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader buff = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String valoreDiRitorno = buff.readLine();
            JSONObject json = new JSONObject(valoreDiRitorno);
            fattoreDiCambio = json.getDouble("EUR_USD");

            buff.close();
            conn.disconnect();
        } catch (IOException | JSONException e) {
            Log.e("HTTP", "problemi nella connessione HTTP");
        }
        String importoStringa = ((EditText)findViewById(R.id.importo)).getText().toString();
        Double importo = Double.parseDouble(importoStringa);


        double nuovoValore = importo*fattoreDiCambio;
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        ((TextView)findViewById(R.id.risultato)).setText(formatter.format(nuovoValore));
    }

}
