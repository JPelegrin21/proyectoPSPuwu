package com.example.chattt;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView chatsPersonas;
    ArrayList<DatosUsuario> lista = new ArrayList<>();
    AdaptadorContactos ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatsPersonas = findViewById(R.id.recicladora);
        chatsPersonas.setLayoutManager(new LinearLayoutManager(this));

        lista.add(new DatosUsuario("Pepito", "kys bro"));
        lista.add(new DatosUsuario("UwU", "ffs fuck off"));
        lista.add(new DatosUsuario("XD", "kys bro"));

        ac = new AdaptadorContactos(this, lista);
        ac.inicializarListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manejar el clic en un elemento de la lista (si es necesario)
            }
        });

        chatsPersonas.setAdapter(ac);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menusito, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.config) {
            // Aquí puedes manejar la acción del menú
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void abrirChat(View view) {
        DatosUsuario usuario = lista.get(chatsPersonas.getChildAdapterPosition(view));
        abrirChat(usuario);
    }

    private void abrirChat(DatosUsuario usuario) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("nombreUsuario", usuario.getNombre());
        startActivity(intent);
    }
}
