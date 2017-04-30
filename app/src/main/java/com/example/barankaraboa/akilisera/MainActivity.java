package com.example.barankaraboa.akilisera;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {


    Button Connect;
    ToggleButton OnOff;
    TextView Result;
    private String dataToSend;

    private String nemDegerim , sicaklikDegerim;
    private int sayar=0 ;
    private String  gelen;


    private TextView sicaklik,nem;
    private ArrayList<String> degersc;

    private static final String TAG = "Baran";
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;
    private static String address = "20:16:02:14:30:29";
    private static final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private InputStream inStream = null;
    Handler handler = new Handler();
    byte delimiter = 10;
    boolean stopWorker = false;
    int readBufferPosition = 0;
    byte[] readBuffer = new byte[1024];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        degersc = new ArrayList<>();
        setTitle("Akilli Sera");

        Connect = (Button) findViewById(R.id.buttonBaglan);
        OnOff = (ToggleButton) findViewById(R.id.tgBtn);
        sicaklik = (TextView) findViewById(R.id.textViewSicaklik);
        nem = (TextView) findViewById(R.id.textViewNem);
        Result = (TextView) findViewById(R.id.sonucText);



        Connect.setOnClickListener(this);
        OnOff.setOnClickListener(this);

        CheckBt();
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        Log.e("Baran", device.toString());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onClick(View control) {
        switch (control.getId()) {
            case R.id.buttonBaglan:
                Connect();
                break;
            case R.id.tgBtn:
                if (OnOff.isChecked()) {
                    dataToSend = "1";
                    writeData(dataToSend);
                } else if (!OnOff.isChecked()) {
                    dataToSend = "0";
                    writeData(dataToSend);
                }
                break;
        }
    }



    private void CheckBt() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Bluetooth Kapalı !",
                    Toast.LENGTH_SHORT).show();
        }

        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),
                    "Bluetooth bulunamadı !", Toast.LENGTH_SHORT)
                    .show();
        }
    }






    public void Connect() {
        Log.d(TAG, address);
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        Log.d(TAG, "Baglanıyor  ... " + device);
        mBluetoothAdapter.cancelDiscovery();
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            btSocket.connect();
            Log.d(TAG, "Baglanti okey.");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                Log.d(TAG, "Bağlantı durdu");
            }
            Log.d(TAG, "Sokette problem");
        }

        beginListenForData();
    }




    private void writeData(String data) {
        try {
            outStream = btSocket.getOutputStream();
        } catch (IOException e) {
            Log.d(TAG, "Bug BEFORE Sending stuff", e);
        }

        String message = data;
        byte[] msgBuffer = message.getBytes();

        try {
            outStream.write(msgBuffer);
        } catch (IOException e) {
            Log.d(TAG, "Bug while sending stuff", e);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
                btSocket.close();
        } catch (IOException e) {
        }
    }




    public void beginListenForData()   {
        try {
            inStream = btSocket.getInputStream();
        } catch (IOException e) {
        }

        Thread workerThread = new Thread(new Runnable()
        {
            public void run()
            {



                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = inStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            inStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;
                                    handler.post(new Runnable()
                                    {


                                        public void run()
                                        {
                                            sayar++;

                                            if(Result.getText().toString().equals("..")) {
                                                //Result.setText(data);
                                                degersc.add(data);




                                            } else

                                            {
                                                //degersc.add(data);
                                             //   Result.append("\n"+data);
                                                degersc.add(data);

                                                // nem.setText(degersc.get(0));


                                                if(degersc.size() > 1)
                                                {

                                                    nemDegerim = degersc.get(sayar-1);
                                                    sicaklikDegerim = degersc.get(sayar-1);

                                                    if(nemDegerim.charAt(0) == '1')
                                                    {
                                                        String nemG = degersc.get(sayar-1);
                                                        nem.setText(nemG.substring(1,nemG.length()));
                                                    }else if(sicaklikDegerim.charAt(0) == '2')
                                                    {

                                                        String sicakG = degersc.get(sayar-1);
                                                        sicaklik.setText(sicakG.substring(1,sicakG.length()));
                                                    }

                                                    // sicaklik.setText(degersc.get(1));
                                                }





                                            }


                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.anasayfa) {

            Intent i = new Intent(MainActivity.this,MainActivity.class);
            startActivity(i);

        } else if (id == R.id.cicekler) {

            Intent i = new Intent(MainActivity.this,Cicekler.class);
           startActivity(i);

        } else if (id == R.id.agaclar) {

            Intent i2 = new Intent(MainActivity.this,Agaclar.class);
            startActivity(i2);

        } else if (id == R.id.hakkimda)
        {

            Intent i3 = new Intent(MainActivity.this,hakkimizda.class);
            startActivity(i3);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






}
