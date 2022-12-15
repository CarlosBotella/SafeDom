package com.example.safedom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.safedom.PacienteP.UsuarioActivity;


public class MainActivity extends AppCompatActivity {
    public void lanzarinfo(View view) {
        Intent i = new Intent(this, InfoActivity.class);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main );

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.menu_usuario) {
            Intent intent = new Intent(this, UsuarioActivity.class);
            startActivity(intent);
        }
        if (id == R.id.info) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}