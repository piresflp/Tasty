const express = require('express');
const UsuarioController = require('../controllers/UsuarioController');

const routes = express.Router();

routes.get('/usuario/:id', UsuarioController.consultarReceitasFavoritas);
routes.post('/usuario', UsuarioController.inserir);
routes.post('/usuario/login', UsuarioController.autenticar);

module.exports = routes;