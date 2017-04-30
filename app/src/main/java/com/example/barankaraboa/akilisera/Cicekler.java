package com.example.barankaraboa.akilisera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.barankaraboa.akilisera.icerikler.elmaVeri;
import com.example.barankaraboa.akilisera.icerikler.kirazVeri;
import com.example.barankaraboa.akilisera.icerikler.portakalVeri;

/**
 * Created by Baran on 7.05.2016.
 */
public class Cicekler extends AppCompatActivity {

    private Button elma,portakal,kiraz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cicekler);

        elma = (Button) findViewById(R.id.buttonElma);
        kiraz = (Button) findViewById(R.id.buttonKiraz);
        portakal = (Button) findViewById(R.id.buttonPortakal);

        elma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Cicekler.this,elmaVeri.class);
                startActivity(i);

            }
        });

        kiraz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Cicekler.this,kirazVeri.class);
                startActivity(i);
            }
        });


        portakal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Cicekler.this,portakalVeri.class);
                startActivity(i);

            }
        });

    }

    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.anasayfa) {

            Intent i = new Intent(Cicekler.this,MainActivity.class);
            startActivity(i);

        } else if (id == R.id.cicekler) {

            Intent i = new Intent(Cicekler.this,Cicekler.class);
            startActivity(i);

        } else if (id == R.id.agaclar) {

        } else if (id == R.id.hakkimda) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
