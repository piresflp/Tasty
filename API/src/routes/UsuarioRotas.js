const express = require('express');
const UsuarioController = require('../controllers/UsuarioController');

const routes = express.Router();

routes.get('/usuario/perfil/:id', UsuarioController.consultarDadosPerfilUsuario);
routes.get('/usuario/:id', UsuarioController.consultarReceitasFavoritas);
routes.get('/usuario/favorito/:id/:idReceita', UsuarioController.verificarFavorito)
routes.get('/usuario/receitas/:id', UsuarioController.buscarReceitasPorIdUsuario);
routes.post('/usuario', UsuarioController.inserir);
routes.post('/usuario/login', UsuarioController.autenticar);

module.exports = routes;