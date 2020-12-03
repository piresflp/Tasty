package com.example.tasty.retrofit.models;

public class Favorito {
    int id, idUsuario, idReceita;
    Receita fkFavoritoReceita;

    public Favorito(){}

    public Favorito(int idUsuario, int idReceita) throws Exception{
        setIdUsuario(idUsuario);
        setIdReceita(idReceita);
    }

    public int getId() {
        return this.id;
    }

    public int getIdReceita() {
        return this.idReceita;
    }

    public int getIdUsuario() {
        return this.idUsuario;
    }

    public Receita getFkFavoritoReceita() {return this.fkFavoritoReceita;}

    public void setId(int id) throws Exception{
        if(id <= 0)
            throw new Exception("ID inválido!");
        this.id = id;
    }

    public void setIdUsuario(int idUsuario) throws Exception {
        if(idUsuario <= 0)
            throw new Exception("Id de usuário inválido!");
        this.idUsuario = idUsuario;
    }

    public void setIdReceita(int idReceita) throws Exception{
        if(idReceita <= 0)
            throw new Exception("Id de receita inválido!");
        this.idReceita = idReceita;
    }

    public void setFkFavoritoReceita(Receita fkFavoritoReceita){
        this.fkFavoritoReceita = fkFavoritoReceita;
    }
}
