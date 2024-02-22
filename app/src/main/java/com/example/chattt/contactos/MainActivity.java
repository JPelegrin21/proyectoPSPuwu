package com.example.chattt.contactos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.chattt.R;
import com.example.chattt.adaptadores.AdaptadorContactos;
import com.example.chattt.chat.ChatActivity;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView chatsPersonas;
    ArrayList<DatosUsuario> lista = new ArrayList<DatosUsuario>(); //Lista contactos
    AdaptadorContactos ac;
    ActivityResultLauncher<Intent> lanzador;
    final static String FICHERO = "ficheroDatos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File f = getBaseContext().getFileStreamPath(FICHERO);
        if (f.exists()) {
            leerFichero();
            Log.d( "XD", "Ha entrado en el f.exists");
        }else {
            Log.d("XD", "Va mal");
            lista.add(new DatosUsuario("Pepito", "kys bro"));
            lista.add(new DatosUsuario("UwU", "ffs fuck off"));
            lista.add(new DatosUsuario("XD", "kys bro"));
        }

        chatsPersonas = findViewById(R.id.recicladora);
        chatsPersonas.setLayoutManager(new LinearLayoutManager(this));




        lanzador = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult resultado) {
                        DatosUsuario du;
                        du = lista.get(resultado.getData().getIntExtra("POS", 0));
                        String ultimoMensaje = resultado.getData().getStringExtra("MENSAJE");
                        du.setUltimoMensaje(ultimoMensaje);
                        ac.notifyDataSetChanged();
                        grabarFichero();
                    }
                }
        );

        ac = new AdaptadorContactos(this, lista, lanzador);
        chatsPersonas.setAdapter(ac);
        /*
        ac.inicializarListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Al hacer clic en un elemento de la lista, abrir el chat
                posicionEdicion = chatsPersonas.getChildAdapterPosition(v);
                DatosUsuario usuario = lista.get(posicionEdicion);
                abrirChat(usuario);
            }
            private void abrirChat(DatosUsuario usuario) {
                // Abrir el chat al hacer clic en un elemento de la lista
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("nombreUsuario", usuario.getNombre());
                lanzador.launch(intent);
            }
        });
        */
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


    public void leerFichero() {
        ObjectInputStream f = null;

        try {
            f = new ObjectInputStream(openFileInput(FICHERO));
            lista = (ArrayList<DatosUsuario>) f.readObject();
            Log.d( "XD", "Lee bien");
        } catch (Exception e) {
            Log.d( "XD", "Algo ha ido mal leyendo: " + e.getMessage());
        } finally {
            try {
                if (f != null) {
                    f.close();
                }
            } catch (Exception e) {
            }
        }
    }
    public void grabarFichero() {
        ObjectOutputStream f = null;
        try {
            f = new ObjectOutputStream(openFileOutput(FICHERO, MODE_PRIVATE));
            f.writeObject(lista);
            Log.d( "XD", "Graba bien");
        } catch (Exception e) {
            Log.d( "XD", "Algo ha ido mal grabando: " + e.getMessage());
        } finally {
            try {
                f.close();
            } catch (Exception e){
            }
        }
    }
}
