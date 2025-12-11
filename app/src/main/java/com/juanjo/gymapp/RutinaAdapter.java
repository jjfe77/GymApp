package com.juanjo.gymapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.juanjo.gymapp.RutinaEjercicio;


public class RutinaAdapter extends RecyclerView.Adapter<RutinaAdapter.ViewHolder> {

    private List<RutinaEjercicio> ejercicios;
    private OnItemClickListener listener;

    public RutinaAdapter(List<RutinaEjercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public interface OnItemClickListener {
        void onItemClick(RutinaEjercicio ejercicio, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RutinaEjercicio re = ejercicios.get(position);

        holder.text1.setText(re.getNombre());
        holder.text2.setText(
                "Series: " + re.getSeries() +
                        " | Reps: " + re.getRepeticiones() +
                        " | Carga: " + re.getCarga() + "kg"
        );

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(re, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ejercicios.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;

        ViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}