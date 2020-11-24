const express = require('express');
const FavoritoController = require('../controllers/FavoritoController');

const routes = express.Router();

//routes.get('/favorito/:idUsuario', FavoritoController.consultarTodosDoUsuario);
routes.post('/favorito', FavoritoController.inserir);
routes.delete('/favorito/:id', FavoritoController.deletar);

module.exports = routes;