package com.ida.actividad03android2026;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

//Intermediario de la lista de tareas y la pantalla.
public class Tareasfuncion extends RecyclerView.Adapter<Tareasfuncion.CajaTarea> {

    //Aquí se guardan las tareas del archivo Task
    private List<Task> taskList;
    //Recibe la tarea el adaptador
    public Tareasfuncion(List<Task> taskList) {
        this.taskList = taskList;
    }

    //Nomas hace subrayar cuando es completada la actividad, con el getpaintflags
    private void subrayado(TextView tv, boolean isCompleted) {
        if (isCompleted) {
            tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tv.setPaintFlags(tv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    //Se crea la vista de las tablas, crea las que pide digamos hace 5 pero todavia no pone los datos en ella.
    @NonNull
    @Override
    public CajaTarea onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //hace el XML en una vista real
        View view = LayoutInflater.from(parent.getContext())
                //hace aqui que si la pantalla solo soloprta 4 pestanas de tareas entonces al hacer scroll, la tarea 1 se convierte en la 5, la 2 en la 6 y asi.
                .inflate(R.layout.tablita_task, parent, false);
        return new CajaTarea(view);
    }
    //
    @Override
    public void onBindViewHolder(@NonNull CajaTarea holder, int position) {
        //Obtienes la tarea correcta según la posición.
        Task task = taskList.get(position);

        holder.textoTarea.setText(task.getTitle());

        // RecyclerView reutiliza vistas, no intenciones.
        //Si no limpias todo, heredas el pasado.
        holder.checkListo.setOnCheckedChangeListener(null);

        // 2. Marcamos el checkbox según el estado de la tarea
        holder.checkListo.setChecked(task.isCompleted());

        // 3. Aplicamos el tachado según el estado inicial
        subrayado(holder.textoTarea, task.isCompleted());

        // 4. Escuchamos cuando el usuario hace clic
        holder.checkListo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Actualizamos el dato en la lista
            task.setCompleted(isChecked);

            // ¡Actualizamos el estilo visual de inmediato!
            subrayado(holder.textoTarea, isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // Es el molde donde se crea la vista.
    public static class CajaTarea extends RecyclerView.ViewHolder {
        TextView textoTarea;
        CheckBox checkListo;

        public CajaTarea(@NonNull View itemView) {
            super(itemView);
            textoTarea = itemView.findViewById(R.id.tvTitle);
            checkListo = itemView.findViewById(R.id.cbCompleted);
        }
    }


    //XML define la fila
    //
    //ViewHolder conecta variables con los id del XML
    //
    //Adapter mete datos en esas variables
    //
    //RecyclerView reutiliza el mismo molde
}