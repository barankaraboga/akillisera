package com.example.barankaraboa.akilisera;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.barankaraboa.akilisera.icerikler.gul;
import com.example.barankaraboa.akilisera.icerikler.lale;
import com.example.barankaraboa.akilisera.icerikler.zambak;

/**
 * Created by Baran on 10.05.2016.
 */
public class Agaclar extends AppCompatActivity {

    private Button btnLale,btnGul,btnZambak;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cicekler_main);

        btnLale = (Button) findViewById(R.id.buttonLale);
        btnGul = (Button)findViewById(R.id.buttonGul);
        btnZambak = (Button) findViewById(R.id.buttonZambak);

        btnLale.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent k = new Intent(Agaclar.this,lale.class);
                                           startActivity(k);
                                       }
                                   }
        );
        btnGul.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent kk = new Intent(Agaclar.this,gul.class);
                                           startActivity(kk);
                                       }
                                   }
        );
        btnZambak.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent kkk = new Intent(Agaclar.this,zambak.class);
                                           startActivity(kkk);
                                       }
                                   }
        );


    }
}
