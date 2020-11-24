const { Model, DataTypes } = require('sequelize');

class Avaliacao extends Model{
    static init(sequelize){
        super.init({
            nota: DataTypes.INTEGER,
            idReceita: DataTypes.INTEGER,
            idUsuario: DataTypes.INTEGER,
        }, {
            sequelize,
            tableName: 'Avaliacoes'
        });
    }

    static associate(models){
        this.belongsTo(models.Receita, {foreignKey: 'idReceita', as: 'fkAvaliacaoReceita'});
        this.belongsTo(models.Usuario, {foreignKey: 'idUsuario', as: 'fkAvaliacaoUsuario'});
    }
}

module.exports = Avaliacao;