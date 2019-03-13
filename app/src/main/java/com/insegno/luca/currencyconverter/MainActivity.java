package com.insegno.luca.currencyconverter;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity<fattoreDiCambio> extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    public void converti(View view) {

        double fattoreDiCambio = 0;

        try{
        URL url = new URL("https://free.currencyconverterapi.com/api/v6/convert?q=EUR_USD&compact=ultra&apiKey=");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        BufferedReader buff = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String valoreRitorno = buff.readLine();

        JSONObject json = new JSONObject(valoreRitorno);

        fattoreDiCambio = json.getDouble("EUR_USD");

    } catch(IOException | JSONException e) {

    }


    String importoStringa = ((EditText) findViewById(R.id.importo)).getText().toString();
    double risultato = Double.parseDouble (importoStringa);

    double nuovoValore = risultato * fattoreDiCambio;

    NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

        ((TextView) findViewById (R.id.converted)).setText(formatter.format(nuovoValore));

    }
}
