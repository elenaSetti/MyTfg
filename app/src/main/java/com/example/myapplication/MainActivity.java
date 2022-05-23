package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
    static String contenidoCorreo;
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

                contenidoCorreo = telefono.getText().toString();
                contenidoPass = password.getText().toString();
                //Controlamos que no haya campos vacíos
                if (contenidoCorreo.isEmpty() || contenidoPass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor introduce tu telefono y contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser();
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
    private void loginUser(){
        firebaseAuth.signInWithEmailAndPassword(contenidoCorreo,contenidoPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, Home.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "No se puede iniciar sesión, comprueba que sea correcto", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseAuth.getCurrentUser()!=null){

            startActivity(new Intent(MainActivity.this, Home.class));
            finish();
        }
    }
}