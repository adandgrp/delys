package mx.com.coffeebean_lab.DeliverySwift;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import mx.com.coffeebean_lab.DeliverySwift.R;

public class Register extends Activity {
    ImageView img;
    Button bmaps;
    Intent ic;

    final static int cons = 0;
    private Bitmap DownloadImage(String imageHttpAddress) {
        URL imageUrl = null;
        Bitmap imagen = null;
        try {
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return imagen;
    }

    /**
     * Background Async Task to download file
     */
    class DownloadFileFromURL extends AsyncTask<String, Void, Bitmap> {

        ProgressDialog pDialog;

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Cargando Imagen...");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            Log.i("doInBackground ", "Entra en doInBackground");
            String url = params[0];
            Bitmap imagen = DownloadImage(url);
            return imagen;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), result);
            roundedDrawable.setCornerRadius(result.getHeight());
            img.setImageDrawable(roundedDrawable);
            pDialog.dismiss();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Llamada al hilo de ejecución

        img = (ImageView) findViewById(R.id.imagedog);
       // new DownloadFileFromURL().execute("http://192.168.1.110/dogdog/avatars/suki.png");
        new DownloadFileFromURL().execute("http://coffeebean-lab.com.mx/dogdog/avatars/suki.png");


        bmaps = (Button) findViewById(R.id.busca_amigos);
        bmaps.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
               /* Intent u = new Intent(Register.this, buscaamigos.class);
                u.putExtra("user", "x");
                startActivity(u);*/
                startActivity(new Intent(getBaseContext(), buscaamigos.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }


        });

        img.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), fotoperfil.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
               /* Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Toast por defecto", Toast.LENGTH_SHORT);

                toast1.show();
                ic = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(ic, cons);*/
            }


        });

    }





}