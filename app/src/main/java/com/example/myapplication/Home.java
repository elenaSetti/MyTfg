package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.MyAdapter;
import com.example.myapplication.entities.Producto;
import com.example.myapplication.entities.Usuario;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home  extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    TextView textViewNombre;
    Button btnSubir;
    String nombre;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //PARA LLAMAR EL NOMBRE DEL USUARIO

        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        textViewNombre =(TextView) findViewById(R.id.editTextNombre) ;
        btnSubir=(Button)findViewById(R.id.btnSubir);

        String contenidoEmail= MainActivity.contenidoCorreo;

        if(MainActivity.contenidoCorreo.equals("tamara@gmail.com")|| MainActivity.contenidoCorreo.equals("elena@gmail.com")) {
            btnSubir.setVisibility(View.VISIBLE);
        }else{
            System.out.println("Error");
        }
        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this,RegistroProducto.class));
            }
        });


        //GESTIÓN DEL RECYCLER VIEW
    recyclerView= (RecyclerView) findViewById(R.id.listaProductos);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Producto> options =
                new FirebaseRecyclerOptions.Builder<Producto>().setQuery(FirebaseDatabase.getInstance().getReference("Producto"),Producto.class).build();

    myAdapter = new MyAdapter(options);
    recyclerView.setAdapter(myAdapter);

    getUserInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }

    private void getUserInfo(){
        id=firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("Usuario").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    nombre=snapshot.child("nombre").getValue().toString();

                    textViewNombre.setText(nombre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
