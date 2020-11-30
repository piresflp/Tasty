const express = require('express');
const FavoritoController = require('../controllers/FavoritoController');

const routes = express.Router();

routes.post('/favorito', FavoritoController.inserir);
routes.delete('/favorito/:idUsuario/:idReceita', FavoritoController.deletar);

module.exports = routes;