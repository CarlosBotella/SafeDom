package com.example.safedom.CasaP;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safedom.R;
import com.example.safedom.clases.Casa;
import com.example.safedom.clases.User;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CasaAdapter extends RecyclerView.Adapter<CasaAdapter.viewHolder> {
    ArrayList<Casa> casas;
    ArrayList<Casa> casao;
    Context context;
    private DatabaseReference dbSensores;
    public CasaAdapter(ArrayList<Casa> arrayCasa, Context applicationContext) {
        this.casas=arrayCasa;
        casao= new ArrayList<>();
        casao.addAll(arrayCasa);
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
        holder.cpaciente.setText(casa.getPaciente());
        holder.cmedico.setText(casa.getMedico());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), InfoCasa.class);
                intent.putExtra("casaInfo",casa);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return casas.size();
    }
    public void filtrado(String txtBuscar){
        int longitud=txtBuscar.length();
        if(longitud==0){
            casas.clear();
            casas.addAll(casao);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Casa> collecion = casas.stream()
                        .filter(i->i.getDireccion().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                casas.clear();
                casas.addAll(collecion);
            }else{
                for(Casa u: casao){
                    if(u.getDireccion().toLowerCase().contains(txtBuscar.toLowerCase())){
                        casas.add(u);
                    }
                }
            }
        }
        notifyDataSetChanged();

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
