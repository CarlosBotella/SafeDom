package com.example.safedom;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safedom.clases.User;

import java.util.ArrayList;
import java.util.List;

public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.viewHolder>{
    ArrayList<User> users;
    Context context;
    public PacienteAdapter(ArrayList<User> arrayUser, Context applicationContext/*,PacienteAdapter.OnItemClickListener listener*/) {
        this.users=arrayUser;
        context=applicationContext;
        //this.listener = listener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_paciente_single,null );
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final User user =users.get(position);
        holder.unombre.setText(user.getNombre()+" "+user .getApellido());
        holder.ucorreo.setText(user.getUserEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(),InfoPaciente.class);
                intent.putExtra("pacienteInfo",user);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView unombre,ucorreo;
        //ConstraintLayout ItemView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            unombre=(TextView)itemView.findViewById(R.id.Nombrep);
            ucorreo=(TextView)itemView.findViewById(R.id.Correop);
            //ItemView=(ConstraintLayout)itemView.findViewById(R.id.itemView);
        }
    }
}
