package com.example.tasty.retrofit.models;

public class Categoria {
    private int id;
    private String nome;

    public Categoria(int id, String nome) throws Exception{
        this.setId(id);
        this.setNome(nome);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) throws Exception{
        if(id < 0)
            throw new Exception("ID inválido!");
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) throws Exception{
        if(nome == null || nome.equals(""))
            throw new Exception("Nome inválido!");
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
