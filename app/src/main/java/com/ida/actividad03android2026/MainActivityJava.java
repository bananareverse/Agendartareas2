package com.ida.actividad03android2026;

// Estas son las "piezas" que necesitamos traer
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

// 1. IMPORTANTE: Agregamos 'extends AppCompatActivity'
public class MainActivityJava extends AppCompatActivity {

    private RecyclerView rvTasks;
    private Tareasfuncion adapter;
    private List<Task> taskList;
    private EditText etTaskTitle;
    private Button btnAdd;


    // 2. Este es el "Capítulo 1" (onCreate)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 3. Conectamos con el diseño XML
        setContentView(R.layout.activity_main);

        // 4. Ahora sí, inicializamos la lista
        taskList = new ArrayList<>();
        taskList.add(new Task(1, "Estudiar Java", "20/01/26", false));
        taskList.add(new Task(2, "Hacer el Adapter", "21/01/26", true));

        // 5. Configuramos el RecyclerView
        rvTasks = findViewById(R.id.rvTasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));

        etTaskTitle = findViewById(R.id.etTaskTitle);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> {
            // 1. Obtenemos el texto que escribió el usuario
            String title = etTaskTitle.getText().toString().trim();

            // 2. Validamos que no esté vacío
            if (!title.isEmpty()) {
                // 3. Creamos una nueva tarea
                // Por ahora inventamos el ID y la fecha
                Task newTask = new Task(taskList.size() + 1, title, "20/01/26", false);

                // 4. La añadimos a nuestra lista
                taskList.add(newTask);

                // 5. ¡AVISAMOS AL ADAPTER! (Esto es lo más importante)
                // Le decimos que se insertó un elemento al final
                adapter.notifyItemInserted(taskList.size() - 1);

                //6. Limpiamos el cuadrito de texto
                etTaskTitle.setText("");

                // 7. Hacemos scroll automático hasta el final para ver la nueva tarea
                rvTasks.scrollToPosition(taskList.size() - 1);
            }
        });

        // 5. Función para borrar deslizando
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // No usamos mover arriba/abajo
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Obtenemos la posición del elemento deslizado
                int position = viewHolder.getAdapterPosition();

                // Lo borramos de la lista
                taskList.remove(position);

                // Avisamos al adapter que se eliminó una fila
                adapter.notifyItemRemoved(position);
            }
        };

// Conectamos el borrado al RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvTasks);

                //

        // 6. Creamos el adapter y lo conectamos
        adapter = new Tareasfuncion(taskList);
        rvTasks.setAdapter(adapter);
    }
}