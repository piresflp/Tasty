package com.example.tasty.retrofit.models;

import java.util.List;

public class Categoria {
    private int id;
    private String nome;
    private List<Receita> fkCategoriaReceita;

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

    public List<Receita> getFkCategoriaReceita(){return this.fkCategoriaReceita;}

    public void setFkCategoriaReceita(List<Receita> listaReceitas) throws Exception{
        if(listaReceitas == null)
            throw new Exception("Lista de receitas inválida!");
        this.fkCategoriaReceita = listaReceitas;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
