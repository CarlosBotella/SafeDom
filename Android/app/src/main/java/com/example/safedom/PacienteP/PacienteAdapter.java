package com.example.safedom.PacienteP;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safedom.R;
import com.example.safedom.clases.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.viewHolder>{
    ArrayList<User> users;
    ArrayList<User> usero;
    Context context;
    public PacienteAdapter(ArrayList<User> arrayUser, Context applicationContext/*,PacienteAdapter.OnItemClickListener listener*/) {
        this.users=arrayUser;
        usero = new ArrayList<>();
        usero.addAll(arrayUser);
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
        //DocumentSnapshot documentSnapshot = getSnapshots.getSnapshot(holder.getAdapterPosition());
        //final String id= documentSnapshot.getId();
        final User user =users.get(position);
        holder.unombre.setText(user.getNombre()+" "+user.getApellido());
        holder.ucorreo.setText(user.getUserEmail());
        if (!user.getFoto().equals("")) {
            Picasso.get().load(user.getFoto()).into(holder.ufoto);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), InfoPaciente.class);
                intent.putExtra("pacienteInfo",user);
                holder.itemView.getContext().startActivity(intent);
            }
        });

       /* holder.be.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }


    public void filtrado(String txtBuscar){
        int longitud=txtBuscar.length();
        if(longitud==0){
            users.clear();
            users.addAll(usero);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<User> collecion = users.stream()
                        .filter(i->i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                users.clear();
                users.addAll(collecion);
            }else{
                for(User u: usero){
                    if(u.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())){
                        users.add(u);
                    }
                }
            }
        }
        notifyDataSetChanged();

    }
    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView unombre,ucorreo;
        ImageView ufoto;
        Button be;
        //ConstraintLayout ItemView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            unombre=(TextView)itemView.findViewById(R.id.Nombrep);
            ucorreo=(TextView)itemView.findViewById(R.id.Correop);
            ufoto=(ImageView)itemView.findViewById(R.id.imageviewperfil);
            be=(Button) itemView.findViewById(R.id.eliminar);
            //ItemView=(ConstraintLayout)itemView.findViewById(R.id.itemView);
        }
    }
}
