package com.example.tasty.retrofit.models;

public class Receita {
    private int id, idUsuario, idCategoria;
    private int rendimento;
    private int tempoDePreparo;
    private String titulo;
    private String ingredientes;
    private String modoDePreparo;
    private Categoria fkReceitaCategoria;
    private Usuario fkReceitaUsuario;
    //private byte[] img;


    public Receita() {
    }

    public Receita(int rendimento, int tempoDePreparo, String titulo, String ingredientes, String modoDePreparo) throws Exception{
        this.setRendimento(rendimento);
        this.setTempoDePreparo(tempoDePreparo);
        this.setTitulo(titulo);
        this.setIngredientes(ingredientes);
        this.setModoDePreparo(modoDePreparo);
        //this.setImg(img);
    }

    public Receita(int id, int rendimento, int tempoDePreparo, String titulo, String ingredientes, String modoDePreparo) throws Exception{
        this.setId(id);
        this.setRendimento(rendimento);
        this.setTempoDePreparo(tempoDePreparo);
        this.setTitulo(titulo);
        this.setIngredientes(ingredientes);
        this.setModoDePreparo(modoDePreparo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws  Exception{
        if(id < 0)
            throw new Exception("ID inválido!");
        this.id = id;
    }

    public int getIdUsuario() {return idUsuario;}

    public void setIdUsuario(int idUsuario) throws Exception{
        if(idUsuario < 0)
            throw new Exception("Id de usuário inválido!");
        this.idUsuario = idUsuario;
    }

    public int getIdCategoria() {return idCategoria;}

    public void setIdCategoria(int idCategoria) throws Exception{
        if(idCategoria < 0)
            throw new Exception("Id de categoria inválido!");
        this.idCategoria = idCategoria;
    }

    public int getRendimento() {
        return this.rendimento;
    }

    public void setRendimento(int rendimento) throws Exception{
        if(rendimento < 0)
            throw new Exception("Rendimento da receita inválido!");
        this.rendimento = rendimento;
    }

    public int getTempoDePreparo() {
        return this.tempoDePreparo;
    }

    public void setTempoDePreparo(int tempoDePreparo) throws Exception{
        if(tempoDePreparo < 0)
            throw new Exception("Tempo de preparo inválido!");
        this.tempoDePreparo = tempoDePreparo;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) throws Exception{
        if(titulo == null || titulo.equals(""))
            throw new Exception("Titulo inválido");
        this.titulo = titulo;
    }

    public Categoria getCategoria(){return this.fkReceitaCategoria;}

    public void setCategoria(Categoria categoria) throws Exception{
        if(categoria == null)
            throw new Exception("Categoria inválida!");
        this.fkReceitaCategoria = categoria;
    }

    public String getIngredientes() {
        return this.ingredientes;
    }

    public void setIngredientes(String ingredientes) throws Exception{
        if(ingredientes == null || ingredientes.equals(""))
            throw new Exception("Título inválido!");
        this.ingredientes = ingredientes;
    }

    public String getModoDePreparo() {
        return this.modoDePreparo;
    }

    public Usuario getFkReceitaUsuario() {return this.fkReceitaUsuario;}

    public void setFkReceitaUsuario(Usuario fkReceitaUsuario) throws Exception{
        if (fkReceitaUsuario ==  null)
            throw new Exception("Usuario nulo!");
        this.fkReceitaUsuario = fkReceitaUsuario;
    }

    public void setModoDePreparo(String modoDePreparo) throws Exception{
        if(modoDePreparo == null || modoDePreparo.equals(""))
            throw new Exception("Modo de preparo inválido!");
        this.modoDePreparo = modoDePreparo;
    }

    /*public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img)throws Exception {
        if(img == null)
            throw new Exception("Imagem nula!");
        this.img = img;
    }*/

    public String toString(){
        return "Id: "+this.id+"; Titulo: "+this.titulo+"; Usuario: "+this.fkReceitaUsuario.toString()+"; Categoria: "+this.fkReceitaCategoria.toString();
    }

    /*public boolean equals(Object obj){
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
    }*/
}
