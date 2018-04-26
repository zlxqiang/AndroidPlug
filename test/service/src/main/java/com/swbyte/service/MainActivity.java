package com.swbyte.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    public IMyAidlInterface iMyAidlInterface;
    private EditText mNumberOneView;
    private EditText mNumberTowView;
    private TextView tv;
    private ServiceConnection connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        tv = (TextView) findViewById(R.id.sample_text);

        connect=new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                iMyAidlInterface = null;
            }
        };

        bindService(new Intent(this, MyService.class),connect, BIND_AUTO_CREATE);


        mNumberOneView = findViewById(R.id.number1);
        mNumberTowView = findViewById(R.id.number2);
        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iMyAidlInterface!=null) {
                    String number1 = mNumberOneView.getText().toString().trim();
                    String number2 = mNumberTowView.getText().toString().trim();
                    int i = 0;
                    int j = 0;
                    if (!TextUtils.isEmpty(number1)) {
                        i = Integer.valueOf(number1).intValue();
                    }
                    if (!TextUtils.isEmpty(number2)) {
                        j = Integer.valueOf(number2).intValue();
                    }
                    try {
                        int result = iMyAidlInterface.add(i, j);
                        tv.setText("结果："+result);
                        Toast.makeText(MainActivity.this, "Service中计算", Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"绑定失败",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        unbindService(connect);
        super.onDestroy();
    }
}
