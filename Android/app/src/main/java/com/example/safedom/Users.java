package com.example.safedom;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;


public class Users implements RepositorioUsuarios {
    protected List<User> listaUsers;
    public Users() {
        listaUsers = new ArrayList<User>();
        //añadeEjemplos();
    }

    public User elemento(int id) {
        return listaUsers.get(id);
    }

    public void añade(User user) {
        listaUsers.add(user);
    }

    public int nuevo() {
        User user = new User();
        listaUsers.add(user);
        return listaUsers.size()-1;
    }

    public void borrar(int id) {
        listaUsers.remove(id);
    }

    public int tamaño() {
        return listaUsers.size();
    }
    public void actualiza(int id, User user) {
        listaUsers.set(id, user);
    }


   /* public void añadeEjemplos() {
        añade(new User("Paco@gmail.com","123456","Paco","Martinez",666666666,"01/01/1980","Paciente"));
        añade(new User("Lucas@gmail.com","123456","Lucas","Perez",666666666,"01/01/1980","Paciente"));

    }*/
}
