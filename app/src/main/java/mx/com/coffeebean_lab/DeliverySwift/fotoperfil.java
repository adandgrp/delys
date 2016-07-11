package mx.com.coffeebean_lab.DeliverySwift;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mx.com.coffeebean_lab.DeliverySwift.R;

/**
 * Created by cofeebeanslab on 31/07/15.
 */
public class fotoperfil extends Activity {
    ImageButton img;
    ImageButton imgallery;
    ImageView imf;
    Intent ic;
    Bitmap bmp;
    final static int cons = 0;
    private int SELECT_IMAGE = 237487;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_fotoperfil);

        img = (ImageButton)findViewById(R.id.btncamera);
        imgallery = (ImageButton)findViewById(R.id.btngallery);
        imf = (ImageView)findViewById(R.id.imgfinal);

        img.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Activando camara fotografica", Toast.LENGTH_SHORT);
                toast1.show();

                ic = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(ic, cons);
            }


        });

        imgallery.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Abriendo gallerya de imagenes", Toast.LENGTH_SHORT);
                toast1.show();

                ic = new Intent(getIntent().ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(ic, SELECT_IMAGE);
            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle ext = data.getExtras();
                bmp = (Bitmap) ext.get("data");
               /* RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), bmp);
                roundedDrawable.setCornerRadius(bmp.getHeight());
                imf.setImageDrawable(roundedDrawable);*/
                imf.setImageBitmap(getRoundedCornerBitmap(bmp, true));
            }
        }else{
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] fileColumn = { MediaStore.Images.Media.DATA };

                Cursor imageCursor = getContentResolver().query(selectedImage,fileColumn, null, null, null);
                imageCursor.moveToFirst();

                int fileColumnIndex = imageCursor.getColumnIndex(fileColumn[0]);
                String picturePath = imageCursor.getString(fileColumnIndex);

                Bitmap pictureObject = BitmapFactory.decodeFile(picturePath);

               /* RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), pictureObject);
                roundedDrawable.setCornerRadius(pictureObject.getHeight());
                imf.setImageDrawable(roundedDrawable);*/
               // imf.setImageURI(selectedImage);
                Bitmap imgfinal = getRoundedCornerBitmap(pictureObject, true);
                imf.setImageBitmap(imgfinal);

                /*    UploaderFoto nuevaTarea = new UploaderFoto(pictureObject);
                    nuevaTarea.execute(imgfinal);*/


            }
            }
        }



    public static Bitmap getRoundedCornerBitmap( Bitmap drawable, boolean square) {
        int width = 0;
        int height = 0;

        Bitmap bitmap = drawable ;

        if(square){
            if(bitmap.getWidth() < bitmap.getHeight()){
                width = bitmap.getWidth();
                height = bitmap.getWidth();
            } else {
                width = bitmap.getHeight();
                height = bitmap.getHeight();
            }
        } else {
            height = bitmap.getHeight();
            width = bitmap.getWidth();
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);
        final float roundPx = 90;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        //canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(width/2,height/2,width/2,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

class UploaderFoto extends AsyncTask< String, String, String > {

        ProgressDialog pDialog;
        String miFoto = "";

        @Override
        protected String doInBackground(String... params) {
            miFoto = params[0];
            try {
                HttpClient httpclient = new DefaultHttpClient();
                httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
                HttpPost httppost = new HttpPost("http://localhost/dogdog/getavatar.php");
                File file = new File(miFoto);
                MultipartEntity mpEntity = new MultipartEntity();
              //  ContentBody foto = new FileBody(file, "image/jpeg");
               // mpEntity.addPart("fotoUp", foto);
                httppost.setEntity(mpEntity);
                httpclient.execute(httppost);
                httpclient.getConnectionManager().shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPreExecute() {
            super.onPreExecute();
           /* pDialog = new ProgressDialog(fotoperfil.this );
            pDialog.setMessage("Subiendo la imagen, espere." );
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setCancelable(true);
            pDialog.show();*/
        }
        protected void onPostExecute(Void result) {
           /* super.onPostExecute(result);*/
       /*     pDialog.dismiss();*/
        }
    }

    private static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

}


