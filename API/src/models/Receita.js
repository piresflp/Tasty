const { Model, DataTypes } = require('sequelize');

class Receita extends Model{
    static init(sequelize){
        super.init({            
            titulo: DataTypes.STRING,
            ingredientes: DataTypes.STRING,
            modoDePreparo: DataTypes.STRING,
            tempoDePreparo: DataTypes.STRING,
            rendimento: DataTypes.INTEGER,
            idUsuario: DataTypes.INTEGER,
            idCategoria: DataTypes.INTEGER,
            //img: DataTypes.BLOB('long'),
        }, {
            sequelize,
            tableName: 'Receitas'
        });
    }

    static associate(models){
        this.hasMany(models.Avaliacao, {foreignKey: 'idReceita', as: 'fkReceitaAvaliacao'});
        this.hasMany(models.Favorito, {foreignKey: 'idReceita', as: 'fkReceitaFavorito'});
        this.belongsTo(models.Usuario, {foreignKey: 'idUsuario', as: 'fkReceitaUsuario'});
        this.belongsTo(models.Categoria, {foreignKey: 'idCategoria', as: 'fkReceitaCategoria'});
    }
}

module.exports = Receita;