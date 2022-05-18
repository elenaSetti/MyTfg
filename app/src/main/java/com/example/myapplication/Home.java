package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.MyAdapter;
import com.example.myapplication.entities.Producto;
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
    String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //PARA LLAMAR EL NOMBRE DEL USUARIO

        firebaseAuth= FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        System.out.println(" user.getDisplayName()");
        textViewNombre =(TextView) findViewById(R.id.editTextNombre) ;

        //GESTIÓN DEL RECYCLER VIEW
    recyclerView= (RecyclerView) findViewById(R.id.listaProductos);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Producto> options =
                new FirebaseRecyclerOptions.Builder<Producto>().setQuery(FirebaseDatabase.getInstance().getReference("Producto"),Producto.class).build();

    myAdapter = new MyAdapter(options);
    recyclerView.setAdapter(myAdapter);
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

    //MÉTODO PARA MOSTRAR SESION DEL USUARIO

    /*private void getUserInfo(){

        databaseReference.child("Usuario").child().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
}
