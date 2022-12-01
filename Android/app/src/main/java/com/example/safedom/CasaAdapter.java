package com.example.safedom;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safedom.clases.Casa;
import com.example.safedom.clases.User;

import java.util.ArrayList;

public class CasaAdapter extends RecyclerView.Adapter<CasaAdapter.viewHolder> {
    ArrayList<Casa> casas;
    Context context;
    public CasaAdapter(ArrayList<Casa> arrayCasa, Context applicationContext) {
        this.casas=arrayCasa;
        context=applicationContext;
        //this.listener = listener;
    }
    @NonNull
    @Override
    public CasaAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_casa_single,null );
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CasaAdapter.viewHolder holder, int position) {
        final Casa casa =casas.get(position);
        holder.cdireccion.setText(casa.getDireccion());
        //holder.cpaciente.setText(casa.getCliente().getNombre());
        //holder.cmedico.setText(casa.getDoctor().getNombre());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),InfoCasa.class);
                intent.putExtra("casaInfo",casa);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return casas.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        TextView cdireccion,cpaciente,cmedico;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            cdireccion=(TextView)itemView.findViewById(R.id.direccioni);
            cpaciente=(TextView)itemView.findViewById(R.id.pacientei);
            cmedico=(TextView)itemView.findViewById(R.id.doctori);
        }
    }
}
