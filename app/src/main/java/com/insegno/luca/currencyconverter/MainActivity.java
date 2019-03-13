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

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void convertiValuta (View view ){
        // Log.i("VALUTA", "Funziona");

        // https://free.currencyconverterapi.com/api/v6/convert?q=USD_PHP&compact=ultra&apiKey=87784f8a93d46ca27ec5

        // qui sopra originale, poi cambiare la key, e metti euro=dollaro

        double fattoreDiCambio = 0;

        try{
            URL url = new URL("https://free.currencyconverterapi.com/api/v6/convert?q=EUR_USD&compact=ultra&apiKey=87784f8a93d46ca27ec5");
            HttpURLConnection conn = (HttpsURLConnection) url.openConnection();

            BufferedReader buff = new BufferedReader(new InputStreamReader (conn.getInputStream()));

            String valoreDiRitorno = buff.readLine();
            //conn.getInputStream();

            JSONObject json = new JSONObject(valoreDiRitorno);

            //double fattoreDiCambio = json.getDouble("EUR_USD");
             fattoreDiCambio = json.getDouble("EUR_USD");

             buff.close();
            conn.disconnect();
            // chiudiamo tutto il chiudibile
        } catch (IOException | JSONException e ) {
          Log.e("HTTP", "Problemi nella connessione HTTP");
          Log.e("HTTP", e.getMessage() );
        }
        String importoStringa =((EditText) findViewById(R.id.importo)).getText().toString();
        double importo =  Double.parseDouble(importoStringa);



        //double nuovoValore = 100 * fattoreDiCambio;
        double nuovoValore = importo * fattoreDiCambio;

        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

//        ((TextView) (findViewById(R.id.risultato))).setText(Sting.valueOf(nuovoValore));
        ((TextView) (findViewById(R.id.risultato))).setText(formatter.format(nuovoValore));
    }

}
