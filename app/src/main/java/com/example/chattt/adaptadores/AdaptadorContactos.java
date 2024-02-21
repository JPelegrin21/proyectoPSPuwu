package com.example.chattt.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chattt.chat.ChatActivity;
import com.example.chattt.contactos.DatosUsuario;
import com.example.chattt.R;

import java.util.ArrayList;

public class AdaptadorContactos extends RecyclerView.Adapter<AdaptadorContactos.ContenedorChat> {

    Context contexto;
    ArrayList<DatosUsuario> datos;
    ActivityResultLauncher<Intent> lanzador;

    public AdaptadorContactos(Context contexto, ArrayList<DatosUsuario> datos, ActivityResultLauncher<Intent> lanzador) {
        this.contexto = contexto;
        this.datos = datos;
        this.lanzador = lanzador;
    }

    @NonNull
    @Override
    public ContenedorChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflador.inflate(R.layout.chat_persona, parent, false);
        return new ContenedorChat(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContenedorChat holder, int position) {
        holder.tvNombre.setText(datos.get(position).getNombre());
        holder.tvUltimoMensaje.setText(datos.get(position).getUltimoMensaje());
        holder.imagen.setImageResource(R.drawable.uwusito);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ContenedorChat extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener , View.OnClickListener {

        TextView tvNombre, tvUltimoMensaje;
        ImageView imagen;

        public ContenedorChat(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.textoUsuario);
            tvUltimoMensaje = itemView.findViewById(R.id.textoMensaje);
            imagen = itemView.findViewById(R.id.imagen);

            itemView.setOnClickListener(this);
        }



        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            final int BORRARCHAT = 200; // Esto si
            final int ANCLAR = 300; // Fregao, no sé si lo haremos
            menu.add(getAdapterPosition(), BORRARCHAT, 0, "Borrar Chat");
            menu.add(getAdapterPosition(), ANCLAR, 1, "Anclar");
        }

        @Override
        public void onClick(View v) {
            int posicion = getAdapterPosition();
            DatosUsuario usuario = datos.get(posicion);
            abrirChat(usuario, posicion);
        }
        private void abrirChat(DatosUsuario usuario, int posicion) {

            Intent intent = new Intent(contexto, ChatActivity.class);
            intent.putExtra("nombreUsuario", usuario.getNombre());
            intent.putExtra("posicion_contenedor", posicion);
            lanzador.launch(intent);
        }


    }

}
