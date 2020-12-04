package com.example.tasty.retrofit.models;

import java.util.List;

public class PesquisaReceita {
    String input;
    List<Receita> receitasEncontradas;

    public PesquisaReceita(){}

    public PesquisaReceita(String input){
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public List<Receita> getReceitasEncontradas() {
        return receitasEncontradas;
    }

    public void setReceitasEncontradas(List<Receita> receitasEncontradas) {
        this.receitasEncontradas = receitasEncontradas;
    }
}
