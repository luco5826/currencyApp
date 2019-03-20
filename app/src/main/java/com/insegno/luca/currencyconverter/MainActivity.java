package com.insegno.luca.currencyconverter;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


    public void convertiValuta(View view) {

        double fattoreDiCambio = 0;
        String importoStringa = ((EditText) findViewById(R.id.importo)).getText().toString();
        String from = ((EditText) findViewById(R.id.from)).getText().toString();
        String to = ((EditText) findViewById(R.id.to)).getText().toString();

        if (importoStringa.isEmpty()) {
            Toast.makeText(MainActivity.this, "Inserire un importo!", Toast.LENGTH_LONG).show();
            return;
        }

        if (from.isEmpty() || to.isEmpty()) {
            Toast.makeText(MainActivity.this, "Inserire una valuta!", Toast.LENGTH_LONG).show();
            return;
        }

        double importo = 0;
        try{
            importo = Double.parseDouble(importoStringa);
        } catch (NumberFormatException e){
            Toast.makeText(MainActivity.this, "Inserire un importo valido!!", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            URL url = new URL("https://free.currencyconverterapi.com/api/v6/convert?q=" + from + "_" + to + "&compact=ultra&apiKey=87784f8a93d46ca27ec5");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            BufferedReader buff = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String valoreDiRitorno = buff.readLine();

            JSONObject json = new JSONObject(valoreDiRitorno);

            fattoreDiCambio = json.getDouble(from + "_" + to);

            buff.close();
            conn.disconnect();

        } catch (IOException e) {
            Log.e("CURRENCY", "Problemi nella connessione HTTP");
            Log.e("CURRENCY", e.getMessage());
            return;
        } catch (JSONException e) {
            Log.e("CURRENCY", "Errore nel parsing del file JSON");
            Toast.makeText(MainActivity.this, "Nessun valore restituito", Toast.LENGTH_LONG).show();
            return;
        }


        double nuovoValore = importo * fattoreDiCambio;

//        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

        ((TextView) findViewById(R.id.risultato)).setText(String.valueOf(nuovoValore));

    }


}
