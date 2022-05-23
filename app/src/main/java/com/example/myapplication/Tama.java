package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class Tama extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private Button btn;
   Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tam);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawer = findViewById(R.id.drawer_layout);
        btn= (Button)findViewById(R.id.btnEntrar) ;
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new InicioFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_inicio);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Tama.this,Home.class));
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_inicio:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new InicioFragment()).commit();
                break;

            case R.id.nav_perfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PerfilFragment()).commit();
                break;
            case R.id.nav_about_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NosotrosFragment()).commit();
                break;
            case R.id.nav_ayuda:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AyudaFragment()).commit();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Cerrar Sesion", Toast.LENGTH_SHORT).show();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void  onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }



}
