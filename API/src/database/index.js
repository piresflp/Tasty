const Sequelize = require('sequelize');
const dbConfig = require('../config/database');
const Avaliacao = require('../models/Avaliacao');
const Categoria = require('../models/Categoria');
const Usuario = require('../models/Usuario');
const Receita = require('../models/Receita');
const Favorito = require('../models/Favorito');

const connection = new Sequelize(dbConfig);

Usuario.init(connection);
Categoria.init(connection);
Receita.init(connection);
Avaliacao.init(connection);
Favorito.init(connection);

Usuario.associate(connection.models);
Categoria.associate(connection.models);
Receita.associate(connection.models);
Avaliacao.associate(connection.models);
Favorito.associate(connection.models);

module.exports = connection;