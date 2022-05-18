package com.example.myapplication.Adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.entities.Producto;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyAdapter extends FirebaseRecyclerAdapter<Producto,MyAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapter(@NonNull FirebaseRecyclerOptions<Producto> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Producto model) {
        holder.nombre.setText(model.getNombre());
        holder.descripcion.setText(model.getDescripcion());
        holder.precio.setText(model.getPrecio());
        Glide.with(holder.img.getContext()).load(model.getFoto()).placeholder(R.drawable.common_google_signin_btn_icon_dark).circleCrop().error(R.drawable.common_google_signin_btn_icon_dark_normal).into(holder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_producto, parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView nombre;
        TextView descripcion;
        TextView precio;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            //VINCULAMOS LAS VARIABLES CON SU ID DE LA VISTA
            img = (ImageView) itemView.findViewById(R.id.imgProducto);
            nombre = (TextView) itemView.findViewById(R.id.txtViewProducto);
            descripcion = (TextView) itemView.findViewById(R.id.txtViewDescripcion);
            precio = (TextView) itemView.findViewById(R.id.txtViewPrecio);
        }
    }
}
