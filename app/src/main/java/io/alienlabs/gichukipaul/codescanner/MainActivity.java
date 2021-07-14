package io.alienlabs.gichukipaul.codescanner;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_CODE = 101;
    private static final int ACTIVITY_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainActivity.this, Scanner.class);
                    startActivityForResult(intent, ACTIVITY_CODE);
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)) {
                    Toast.makeText(MainActivity.this, " Enable permissions ! ", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_CODE && resultCode == RESULT_OK) {
            String answer = data.getData().toString();
            showAlertDialog(answer);
        }
    }

    private void showAlertDialog(String answer) {
        new AlertDialog.Builder(this)
                .setTitle(" RESULTS ")
                .setMessage(answer)
                .setIcon(R.mipmap.ic_alert)
                .setCancelable(false)
                .setPositiveButton(" VISIT ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(answer));
                        //intent.setType("text/plain");
                        //intent.setPackage("com.whatsapp");
                        startActivity(Intent.createChooser(intent, " Open with.."));
                    }
                })
                .setNeutralButton(" OK ", null)
                .show();
    }
}