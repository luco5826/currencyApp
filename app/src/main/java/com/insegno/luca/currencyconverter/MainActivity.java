package com.insegno.luca.currencyconverter;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void convertiValuta(View view)
    {
//        Log.i("VALUTA", "Funziona!"); // Log di prova, per testare che l'app scaricata da git funzioni, eliminato dopo averlo verificato

        //usage: https://free.currencyconverterapi.com/api/v6/convert?q=USD_PHP&compact=ultra&apiKey=

        double fattoreDiCambio = 0;
        try{
            URL url = new URL("https://free.currencyconverterapi.com/api/v6/convert?q=EUR_USD&compact=ultra&apiKey=");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            BufferedReader buff = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String valoreDiRitorno = buff.readLine();   // {"EUR_USD":1131323131}

            JSONObject json = new JSONObject(valoreDiRitorno);

            fattoreDiCambio = json.getDouble("EUR_USD");

            buff.close();
            conn.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        double importo = Double.parseDouble(((EditText)findViewById(R.id.importo)).getText().toString());
        double risultato = importo*fattoreDiCambio;

        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        ((TextView)findViewById(R.id.risultato)).setText(String.valueOf(formatter.format(risultato)));
    }
}

