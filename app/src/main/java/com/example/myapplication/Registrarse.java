package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.entities.Producto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registrarse extends AppCompatActivity {

    // VARIABLES DE VISTA
    private EditText userR;
    private EditText emailR;
    private EditText passwordR;
    private EditText phone;
    private EditText conpasswordR;
    private Button btnregistroR;
    private FirebaseAuth firebaseAuth;

    // VARIABLES
    private String contenidoEmail;
    private String contenidoPaswor;
    private String contenidoPasworConf;
    private String contenidoUser;
    private String contenidoPhone;
    private String uid;
    private Intent intent;
    Map<String,Object> map= new HashMap<>();

    // CLASES PROPIAS
    private Producto usuario;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        //INSTANCIAMOS FIREBASE AUTH
        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        // PARA QUE LA PANTALLA NO SE GIRE
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // INICIALIZAMOS VARIABLES
        userR = findViewById(R.id.txtUsuario);
        emailR = findViewById(R.id.txtREmail);
        passwordR = findViewById(R.id.txtRContrasena);
        conpasswordR = findViewById(R.id.txtconfContrasena);
        phone = findViewById(R.id.txtPhone);
        btnregistroR = findViewById(R.id.btnregistroR);

        btnregistroR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoUser= userR.getText().toString();
                contenidoPaswor= passwordR.getText().toString();
                contenidoPhone= phone.getText().toString();
                contenidoPasworConf=conpasswordR.getText().toString();
                contenidoEmail=emailR.getText().toString();

                //Controlamos que no haya campos vacíos
                if (contenidoEmail.isEmpty() || contenidoPaswor.isEmpty() || contenidoUser.isEmpty() || contenidoPhone.isEmpty() || contenidoPasworConf.isEmpty()) {
                    Toast.makeText(Registrarse.this, "Por favor introduce todos los campos", Toast.LENGTH_SHORT).show();
                } else if(contenidoPaswor.length()<6) {
                         Toast.makeText(Registrarse.this, "La contraseña tiene que tener más 6 caracteres", Toast.LENGTH_SHORT).show();
                } else if (!contenidoPaswor.equals(contenidoPasworConf)) {
                    Toast.makeText(Registrarse.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else {
                registerUser();
                }
            }
        });


                /*databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //Chequeamos que no hay un usuario con telefono igual
                        if(snapshot.hasChild(contenidoPhone)){
                            Toast.makeText(Registro.this,"El telefono ya está registrado",Toast.LENGTH_SHORT).show();
                        }else{



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/


    }
    private void registerUser(){
    firebaseAuth.createUserWithEmailAndPassword(contenidoEmail,contenidoPaswor).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                map.put("nombre",contenidoUser);
                map.put("email",contenidoEmail);
                map.put("password",contenidoPaswor);
                uid=firebaseAuth.getCurrentUser().getUid();
                databaseReference.child("Usuario").child(uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if(task2.isSuccessful()){
                            startActivity(new Intent(Registrarse.this,MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(Registrarse.this, "No se crearon los datos correctamente", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }else{
                Toast.makeText(Registrarse.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();

            }
        }
    }); {




    }
}

}