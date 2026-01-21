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


public class Tareasfuncion extends RecyclerView.Adapter<Tareasfuncion.TaskViewHolder> {

    private List<Task> taskList;

    public Tareasfuncion(List<Task> taskList) {
        this.taskList = taskList;
    }

    private void updateStroke(TextView tv, boolean isCompleted) {
        if (isCompleted) {
            // Añade el efecto de tachado
            tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            // Quita el efecto (lo deja normal)
            tv.setPaintFlags(tv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    // --- AQUÍ VAN LOS MÉTODOS DEL JEFE (ADAPTER) ---

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tablita_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.tvTitle.setText(task.getTitle());
        holder.tvDate.setText(task.getDate());

        // 1. Quitamos el listener para evitar errores al reciclar la vista
        holder.cbCompleted.setOnCheckedChangeListener(null);

        // 2. Marcamos el checkbox según el estado de la tarea
        holder.cbCompleted.setChecked(task.isCompleted());

        // 3. Aplicamos el tachado según el estado inicial
        updateStroke(holder.tvTitle, task.isCompleted());

        // 4. Escuchamos cuando el usuario hace clic
        holder.cbCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Actualizamos el dato en la lista
            task.setCompleted(isChecked);

            // ¡Actualizamos el estilo visual de inmediato!
            updateStroke(holder.tvTitle, isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // --- AQUÍ EMPIEZA EL AYUDANTE (VIEWHOLDER) ---
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDate;
        CheckBox cbCompleted;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            cbCompleted = itemView.findViewById(R.id.cbCompleted);
        }
    }
}