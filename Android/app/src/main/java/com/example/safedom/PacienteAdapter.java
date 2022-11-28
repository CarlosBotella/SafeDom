/* Clase para ver los usuarios en el RecyclerView del Medico */
package com.example.safedom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.safedom.clases.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PacienteAdapter  extends FirestoreRecyclerAdapter<User, PacienteAdapter.ViewHolder> {
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PacienteAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull User model) {
        holder.nombrep.setText(  model.getNombre());
        holder.apellidop.setText(  model.getApellido());
        holder.correop.setText(  model.getUserEmail());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_paciente_single,parent,false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombrep,apellidop,correop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombrep=itemView.findViewById(R.id.Nombrep);
            apellidop=itemView.findViewById(R.id.Apellidop);
            correop=itemView.findViewById(R.id.Correop);
        }
    }
}
