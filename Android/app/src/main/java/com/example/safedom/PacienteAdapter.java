package com.example.safedom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safedom.clases.User;

import java.util.ArrayList;

public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.viewHolder>{
    ArrayList<User> users;
    Context context;
    public PacienteAdapter(ArrayList<User> arrayUser, Context applicationContext) {
        this.users=arrayUser;
        context=applicationContext;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_paciente_single,null );
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.unombre.setText(users.get(position).getNombre());
        holder.uapellido.setText(users.get(position).getApellido());
        holder.ucorreo.setText(users.get(position).getUserEmail());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView unombre,uapellido,ucorreo;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            unombre=(TextView)itemView.findViewById(R.id.Nombrep);
            uapellido=(TextView)itemView.findViewById(R.id.Apellidop);
            ucorreo=(TextView)itemView.findViewById(R.id.Correop);
        }
    }
}
