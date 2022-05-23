package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.entities.Producto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroProducto extends AppCompatActivity{

    EditText nombreProducto, descripcion,precio,foto,id;
    Button btnRegistroProd;
    private String contenidoProducto;
    private String contenidoDescripcion;
    private String contenidoPrecio;
    private String contenidoFoto;
    private String contenidoId;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registroproducto);
        id=findViewById(R.id.idproducto);
        nombreProducto=findViewById(R.id.txtProducto);
        descripcion=findViewById(R.id.txtDescripcion);
        precio=findViewById(R.id.txtPrecio);
        foto=findViewById(R.id.txtFoto);
        btnRegistroProd = findViewById(R.id.btnregistroProducto);
        inicializarFirebase();



        btnRegistroProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoId=id.getText().toString();
                contenidoProducto= nombreProducto.getText().toString();
                contenidoDescripcion= descripcion.getText().toString();
                contenidoPrecio=precio.getText().toString();
                contenidoFoto=foto.getText().toString();

                if(contenidoProducto.isEmpty()||contenidoFoto.isEmpty()||contenidoPrecio.isEmpty()||contenidoDescripcion.isEmpty()){
                    Toast.makeText(RegistroProducto.this, "Por favor introduce todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                Producto producto= new Producto();
                producto.setId_producto(contenidoId);
                producto.setNombre(contenidoProducto);
                producto.setDescripcion(contenidoDescripcion);
                producto.setPrecio(contenidoPrecio);
                producto.setFoto(contenidoFoto);
                databaseReference.child("Producto").child(producto.getId_producto()).setValue(producto).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegistroProducto.this, "Producto registrado", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistroProducto.this,MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(RegistroProducto.this, "No se crearon los datos correctamente", Toast.LENGTH_SHORT).show();

                        }
                    }
                });



                }
            }
        });
    }
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= firebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference();
    }
}
