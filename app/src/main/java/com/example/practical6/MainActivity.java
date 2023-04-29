package com.example.practical6;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =111 ;
    private EditText mobileNo, message;
    private Button sendSMS;
    String phoneNo;
    String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mobileNo = (EditText) findViewById(R.id.editTextPhone);
        message = (EditText) findViewById(R.id.message);
        sendSMS = (Button) findViewById(R.id.button1);
        sendSMS.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS))
        {
            sendSMS.setEnabled(true);
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
        sendSMS.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                msg = message.getText().toString();
                phoneNo = mobileNo.getText().toString();
                if(!TextUtils.isEmpty(msg) && !TextUtils.isEmpty(phoneNo)) {
                    if (checkPermission(Manifest.permission.SEND_SMS))
                    {
                        SmsManager smsmgr = SmsManager.getDefault();
                        smsmgr.sendTextMessage(phoneNo,null,msg,null,null);

                        Toast.makeText(MainActivity.this, "SMS Sent..", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Mobile no. and Message should non empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean checkPermission(String permission)
    {
        int checkPermission = ContextCompat.checkSelfPermission(this,permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
                if(grantResults.length > 0 && (grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) )
                {
                    sendSMS.setEnabled(true);
                }
                break;
        }
    }

}



