package com.example.tasty.retrofit.models;

public class DadosPerfilUsuario {
    private int qtdReceitasCriadas, qtdReceitasFavoritadas;

    public DadosPerfilUsuario(){}

    public DadosPerfilUsuario(int qtdReceitasCriadas, int qtdReceitasFavoritadas) throws Exception{
        this.setQtdReceitasCriadas(qtdReceitasCriadas);
        this.setQtdReceitasFavoritadas(qtdReceitasFavoritadas);
    }

    public int getQtdReceitasCriadas() {
        return qtdReceitasCriadas;
    }

    public int getQtdReceitasFavoritadas() {
        return qtdReceitasFavoritadas;
    }

    public void setQtdReceitasCriadas(int qtdReceitasCriadas) throws Exception{
        if(qtdReceitasCriadas < 0)
            throw new Exception("Quantidade de receitas criadas inválida!");
        this.qtdReceitasCriadas = qtdReceitasCriadas;
    }

    public void setQtdReceitasFavoritadas(int qtdReceitasFavoritadas) throws Exception{
        if(qtdReceitasFavoritadas < 0)
            throw new Exception("Quantidade de receitas favoritadas inválida!");
        this.qtdReceitasFavoritadas = qtdReceitasFavoritadas;
    }
}
