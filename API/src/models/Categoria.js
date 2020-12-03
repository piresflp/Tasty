const { Model, DataTypes } = require('sequelize');

class Categoria extends Model{
    static init(sequelize){
        super.init({
            nome: DataTypes.STRING,
        }, {
            sequelize, 
        });
    }

    static associate(models){
        this.hasMany(models.Receita, {foreignKey: 'idCategoria', as: 'fkCategoriaReceita'});
    }
}

module.exports = Categoria;