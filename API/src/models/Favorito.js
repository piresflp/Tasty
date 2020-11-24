const { Model, DataTypes } = require('sequelize');

class Favorito extends Model{
    static init(sequelize){
        super.init({
            idUsuario: DataTypes.INTEGER,
            idReceita: DataTypes.INTEGER,
        }, {
            sequelize,
        });
    }

    static associate(models){
        this.belongsTo(models.Usuario, {foreignKey: 'idUsuario', as: 'fkFavoritoUsuario'});
        this.belongsTo(models.Receita, {foreignKey: 'idReceita', as: 'fkFavoritoReceita'});
    }
}

module.exports = Favorito;