package com.example.tasty.retrofit.models;

import java.io.Serializable;

public class Usuario implements Serializable, Cloneable {
    private int id;
    private String nome;
    private String email;
    private String nomeDeUsuario;
    private String senha;

    public Usuario(){
    }

    public Usuario(String nome, String email, String nomeDeUsuario, String senha) throws Exception{
        this.setNome(nome);
        this.setEmail(email);
        this.setNomeDeUsuario(nomeDeUsuario);
        this.setSenha(senha);
    }

    public Usuario(int id, String nome, String email, String nomeDeUsuario, String senha) throws Exception{
        this.setId(id);
        this.setNome(nome);
        this.setEmail(email);
        this.setNomeDeUsuario(nomeDeUsuario);
        this.setSenha(senha);
    }

    public int getId(){ return this.id; }

    public void setId(int id){
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) throws Exception{
        if(nome == null || nome.equals(""))
            throw new Exception("Nome invalido!");
        this.nome = nome;
    }

    public String getNomeDeUsuario() {return this.nomeDeUsuario;}

    public void setNomeDeUsuario(String nomeDeUsuario) throws Exception{
        if(nomeDeUsuario == null || nomeDeUsuario.equals(""))
            throw new Exception("Nome de usuario invalido!");
        this.nomeDeUsuario = nomeDeUsuario;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) throws Exception{
        if(email == null || email.equals(""))
            throw new Exception("Email invalido!");
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) throws Exception{
        if(senha == null || senha.equals(""))
            throw new Exception("Senha invalida!");
        this.senha = senha;
    }

    public String toString(){
        return "Id: "+this.id+"; Nome: "+this.nome+"; Email: "+this.email+"; Nome de Usuario: "+this.nomeDeUsuario+"; Senha: "+this.senha;
    }

    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if(obj == null)
            return false;
        if(!(obj instanceof Usuario))
            return false;

        Usuario outro = (Usuario) obj;
            if(!this.nome.equals(outro.nome))
                return false;
            if(!this.email.equals(outro.email))
                return false;
            if(!this.senha.equals(outro.senha))
                return false;
         return true;
    }

    public int hashCode(){
        int ret = 23;
        ret = ret*7 + this.nome.hashCode();
        ret = ret*7 + this.email.hashCode();
        ret = ret*7 + this.senha.hashCode();

        return ret;
    }

    public Usuario(Usuario modelo) throws Exception{
        this.nome = modelo.nome;
        this.email = modelo.email;
        this.senha = modelo.senha;
    }

    public Object clone(){
        Usuario ret = null;
        try{
            ret = new Usuario(this);
        }catch(Exception e){}

        return ret;
    }
}
