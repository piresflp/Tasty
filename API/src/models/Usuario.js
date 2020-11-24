const { Model, DataTypes } = require('sequelize');
const bcrypt = require('bcrypt');

class Usuario extends Model{
    static init(sequelize){
        super.init({
            nome: DataTypes.STRING,
            email: DataTypes.STRING,
            nomeDeUsuario: DataTypes.STRING,
            senha: DataTypes.STRING,
        }, {
            sequelize,
        });
        this.addHook('beforeSave', async client => {
            if (client.senha) {
              client.senha = await bcrypt.hash(client.senha, 8);
            }
          });
          return this;
    }

    static associate(models){
        this.hasMany(models.Avaliacao, {foreignKey: 'idUsuario', as: 'fkUsuarioAvaliacao'});
        this.hasMany(models.Receita, {foreignKey: 'idUsuario', as: 'fkUsuarioReceita'});
        this.hasMany(models.Favorito, {foreignKey: 'idUsuario', as: 'fkUsuarioFavorito'});
    }
}

module.exports = Usuario;