package com.ida.actividad03android2026;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

// Esta es la pantalla principal de tu app, donde esta todo implmentado XML, java, etc.
public class MainActivityJava extends AppCompatActivity {

    //Son las variables globales:
    // rvTasks → donde se muestran las tareas
    // adapter → el intermediario entre datos y pantalla
    // listaDatos → la lista real de tareas (los datos)
    // TituloTarea → donde escribes la tarea
    // botonAgregar → botón para agregar

    private RecyclerView rvTasks;
    private Tareasfuncion adapter;
    private List<Task> listaDatos;
    private EditText TituloTarea;
    private Button botonAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se carga el layout con el mainActivity
        setContentView(R.layout.activity_main);

        // Se crea la lista
        listaDatos = new ArrayList<>();
        // se pone el recycle de forma vertical
        rvTasks = findViewById(R.id.rvTasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        //Conecta el input y el boton
        TituloTarea = findViewById(R.id.etTaskTitle);
        botonAgregar = findViewById(R.id.btnAdd);

        //Aqui pone el boton, obtiene el texto y lo pone en la lista
        botonAgregar.setOnClickListener(v -> {
            String title = TituloTarea.getText().toString().trim();

            if (!title.isEmpty()) {
                Task newTask = new Task(listaDatos.size() + 1, title, false);

                listaDatos.add(newTask);

                // avisa al adaptador por que se agrego una tarea
                adapter.notifyItemInserted(listaDatos.size() - 1);
                TituloTarea.setText("");
                rvTasks.scrollToPosition(listaDatos.size() - 1);
            }
        });

        // Aqui se hace todo lo de borrar, deslizandose a la izquierda o derecha
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // No usamos mover arriba/abajo
            }

            //Checa si se dezliozo y borra la posicion y le dice al adaptador
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int position = viewHolder.getAdapterPosition();

                listaDatos.remove(position);

                adapter.notifyItemRemoved(position);
            }
        };

// Conectamos el borrado al RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvTasks);

                //

        // Aquí está la caja con los datos (adaptadro) y se conecta con el recycle
        adapter = new Tareasfuncion(listaDatos);
        rvTasks.setAdapter(adapter);
    }
}