package com.example.safedom;

public interface RepositorioUsuarios {
    User elemento(int id);
    void añade(User user);
    int nuevo(); //Añade un elemento en blanco y devuelve su id
    void borrar(int id); //Elimina el elemento con el id indicado
    int tamaño(); //Devuelve el número de elementos
    void actualiza(int id, User user); //Reemplaza un elemento
}
