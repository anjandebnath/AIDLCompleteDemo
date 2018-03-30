package com.example.user06.aidlclient;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.anjan.server.IMultiply;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    EditText firstNumEditText;

    EditText secondNumEditText;

    Button btnAdd;

    protected IMultiply addService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstNumEditText = findViewById(R.id.editText_First);
        secondNumEditText = findViewById(R.id.editText_Second);
        btnAdd = findViewById(R.id.addButton);
        initView();
    }

    private void initView() {

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNumber = firstNumEditText.getText().toString().trim();
                String secondNumber = secondNumEditText.getText().toString().trim();
                int multiply= -1;
                if (firstNumber.isEmpty() && secondNumber.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please enter first and second number.", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        multiply =  addService.multiply(Integer.valueOf(firstNumber), Integer.valueOf(secondNumber));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, ""+multiply, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initConnection() {
        if (addService == null) {
            Intent intent = new Intent(IMultiply.class.getName());

            /*this is service name*/
            intent.setAction("service.multiply");

            /*From 5.0 annonymous intent calls are suspended so replacing with server app's package name*/
            intent.setPackage("com.anjan.server");

            // binding to remote service
            bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        initConnection();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        unbindService(serviceConnection);
    }

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        //Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        //Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        //Get component info and create ComponentName
        ResolveInfo serviceInfo = (ResolveInfo) resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        //Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        //Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getApplicationContext(),"Service Connected", Toast.LENGTH_LONG).show();
            addService =  IMultiply.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(getApplicationContext(),"Service DisConnected", Toast.LENGTH_LONG).show();
            addService = null;
        }
    };
}
