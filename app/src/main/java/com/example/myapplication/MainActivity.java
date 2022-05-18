package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity  {
    private Intent intent;
    private EditText telefono;
    private EditText password;
    private String contenidoTele;
    private String contenidoPass;
    private Button btnLogin;
    private Button btnRegistro;
    private String getPassword;
    private DatabaseReference databaseReference;

    //PARA INICIAR SESION CON GOOGLE
    private static final String TAG="GoogleActivity";
    private static final int RC_SIGN_IN=9001;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ethiwo-tfg-app-default-rtdb.firebaseio.com");

        btnLogin = findViewById(R.id.btnEntrar);
        btnRegistro=findViewById(R.id.btnregistro);
        telefono = findViewById(R.id.txtTelefono);
        password = findViewById(R.id.txtContrasena);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    contenidoTele = telefono.getText().toString();
                    contenidoPass = password.getText().toString();
                    //Controlamos que no haya campos vacíos
                    if (contenidoTele.isEmpty() || contenidoPass.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Por favor introduce tu telefono y contraseña", Toast.LENGTH_SHORT).show();
                    } else {
                  databaseReference.child("Usuario").addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot snapshot) {
                          //Comprueba si el correo existe en firebase
                          //Si existe se coge la contraseña para ver si es la misma
                            System.out.println(snapshot.hasChild(contenidoTele));
                          if(snapshot.hasChild(contenidoTele)){
                              getPassword= snapshot.child(contenidoTele).child("password").getValue(String.class);
                              if(getPassword.equals(contenidoPass)){
                                  Toast.makeText(MainActivity.this, "Has iniciado sesión", Toast.LENGTH_SHORT).show();
                              //LLevamos al usuario a la página principa
                                startActivity(new Intent(MainActivity.this,Home.class));

                                finish();
                              }else{
                                  Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

                              }
                          }else{
                              Toast.makeText(MainActivity.this, "No existe el usuario", Toast.LENGTH_SHORT).show();

                          }
                      }

                      @Override
                      public void onCancelled(@NonNull DatabaseError error) {

                      }
                  });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Registrarse.class));
            }
        });

    }
}