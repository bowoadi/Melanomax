package org.tensorflow.demo.ui.mainmenu;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.qiscus.sdk.Qiscus;

import org.tensorflow.demo.DetectorActivity;
import org.tensorflow.demo.R;
import org.tensorflow.demo.SaveModelPath;
import org.tensorflow.demo.util.SharedPrefUtil;
import org.tensorflow.demo.util.ShowAlert;

public class MainMenuActivity extends AppCompatActivity {

    private ImageButton btn_scan;
    private ImageButton btnGetModelPath, btnGetLabelPath;
    private TextView tvModelPath,tvLabelPath;
    private ImageButton btn_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        btn_scan = findViewById(R.id.bt_scan);
        btn_info = findViewById(R.id.btn_info);
        tvModelPath = findViewById(R.id.tv_model_path);
        btnGetModelPath = findViewById(R.id.btn_get_model_path);
        btnGetLabelPath = findViewById(R.id.btn_get_label_path);
        tvLabelPath = findViewById(R.id.tv_label_path);

        btnGetLabelPath.setVisibility(View.GONE);
        if(SaveModelPath.getInstance().getModelPath() != null){
            if(!SaveModelPath.getInstance().getModelPath().isEmpty()             ){
                tvModelPath.setText("Lokasi model : " +SaveModelPath.getInstance().getModelPath());
                tvModelPath.setVisibility(View.VISIBLE);
            }
        }
        if(SaveModelPath.getInstance().getLabelPath() != null){
            if(!SaveModelPath.getInstance().getLabelPath().isEmpty()             ){
                tvLabelPath.setText("Lokasi label : " +SaveModelPath.getInstance().getLabelPath());
                tvLabelPath.setVisibility(View.VISIBLE);
            }
        }

        btnGetModelPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(MainMenuActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(MainMenuActivity.this);
                }
                builder.setTitle("Perhatian").setMessage("Pilih File Model dan Label secara berurutan")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("*/*");
                                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                startActivityForResult(Intent.createChooser(intent, "Pilih File Model dan Label"),1001);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });



        btn_scan.setOnClickListener(v -> {
            if (SaveModelPath.getInstance().getModelPath() == null){
                ShowAlert.showToast(getApplicationContext(),"Silahkan pilih file model terlebih dahulu");
            } else  if(SaveModelPath.getInstance().getLabelPath() == null){
                ShowAlert.showToast(getApplicationContext(),"Silahkan pilih file label terlebih dahulu");
            }else {
                if(SaveModelPath.getInstance().getModelPath().isEmpty() ||
                        SaveModelPath.getInstance().getModelPath() == null){
                    ShowAlert.showToast(getApplicationContext(),"Silahkan pilih file model terlebih dahulu");
                }else if(SaveModelPath.getInstance().getLabelPath().isEmpty() ||
                        SaveModelPath.getInstance().getLabelPath() == null){
                    ShowAlert.showToast(getApplicationContext(),"Silahkan pilih file label terlebih dahulu");
                } else{
                    Intent intent = new Intent(MainMenuActivity.this, DetectorActivity.class);
                    startActivity(intent);
                }
            }

        });

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainMenuActivity.this);
                alertDialogBuilder.setMessage(
                        "Actinic Keratosis merupakan salah satu jenis penyakit kanker kulit yang disebabkan oleh pancaran radiasi sinar ultraviolet. Penyakit Actinic Keratosis memiliki sebutan lain yaitu solar keratosis. Kulit yang terkena Actinic Keratosis termasuk kedalam kondisi kulit prakanker. Ciri-ciri dari penyakit ini antara lain kulit tebal tambal, berkerak atau bersisik dengan warna kulit yang berubah menjadi kecoklatan, merah, merah muda atau kombinasi dari warna-warna tersebut. Kanker kulit ini diawali dengan lesi kulit yang dihasilkan dari profilerasi keratinosit epidermal atipikal. Actinic Keratosis apabila dibiarkan selama bertahun-tahun akan berkembang menjadi Squamous Cell Carcinoma (SCC)." +
                               "\n\nMelanoma menjadi penyakit yang berbahaya dan dapat menyebabkan kefatalan pada awal abad ke 21. Melanoma merupakan kanker ganas yang menyerang usia muda dengan rentang usia antara 25 hingga 50 tahun. Amerika Serikat menempatkan penyakit kanker ini menjadi penyakit yang umum kelima bagi pria dan keenam bagi wanita. Pria lebih berisiko terkena melanoma sebesar 1.5 kali dibandingkan wanita. Penyebab melanoma hingga saat ini diketahui berasal dari dua hal yaitu paparan lingkungan berupa radiasi sinar UV dan faktor genetik. Paparan sinar UV memiliki potensi yang paling besar dalam mengembangakan melanoma karena dapat menyebabkan efek genotoksik."
                        );
                        alertDialogBuilder.setNeutralButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if(data != null){
                ShowAlert.showToast(getApplicationContext(), "oke1");
                ClipData data1 = data.getClipData();
                if(data1 != null) {
                    int count = data1.getItemCount();
                    int currentItem = 0;
                    ShowAlert.showToast(getApplicationContext(), String.valueOf(count));
                    if(count > 2 || count < 2){
                        ShowAlert.showToast(getApplicationContext(), "Hanya dapat memilih 2 file");
                        tvLabelPath.setVisibility(View.GONE);
                        tvModelPath.setVisibility(View.GONE);
                    }else {
                        while(currentItem < count) {
                            Uri currFileURI = data.getClipData().getItemAt(currentItem).getUri();
                            String fileName = currFileURI.getPath();
                            String path=getRealPathFromURI(MainMenuActivity.this, currFileURI);
                            String pathChecker[] = fileName.split("\\.");
                            boolean checker = false;
                            String fileFormat = null;
                            System.out.println("cek "+path);
                            if((pathChecker[pathChecker.length-1].equals("pb") || pathChecker[pathChecker.length-1].equals("txt")) && !checker){
                                checker = true;
                                fileFormat = pathChecker[pathChecker.length-1];
                            }

                            if(checker){
                                if(fileFormat.equals("pb")){
                                    SaveModelPath.getInstance().saveModelPath(path);
                                    if(SaveModelPath.getInstance().getModelPath() != null){
                                        if(!SaveModelPath.getInstance().getModelPath().isEmpty()             ){
                                            tvModelPath.setText("Lokasi model : " +SaveModelPath.getInstance().getModelPath());
                                            tvModelPath.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }
                                if(fileFormat.equals("txt")){
                                    SaveModelPath.getInstance().saveLabelPath(path);
                                    if(SaveModelPath.getInstance().getLabelPath() != null){
                                        if(!SaveModelPath.getInstance().getLabelPath().isEmpty()             ){
                                            tvLabelPath.setText("Lokasi label : " +SaveModelPath.getInstance().getLabelPath());
                                            tvLabelPath.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }

                            }else {
                                ShowAlert.showToast(getApplicationContext(), "Format yang didukung hanya .pb");
                            }
//                            Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
//                            //do something with the image (save it to some directory or whatever you need to do with it here)
                            currentItem = currentItem + 1;
                        }
                    }

                } else{
                    String imagePath = data.getData().getPath();
                    ShowAlert.showToast(getApplicationContext(), "Empty data1");
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                }


            }else {
                ShowAlert.showToast(getApplicationContext(), "Empty data");
            }


        }

    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        String filePath = "";
        if (uri.getHost().contains("com.android.providers.media")) {
            // Image pick from recent
            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        } else {
            // image pick from gallery
            return  getRealPathFromURI_BelowAPI11(context,uri);
        }

    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
