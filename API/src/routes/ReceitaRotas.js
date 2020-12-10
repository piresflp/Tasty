const express = require('express');
const ReceitaController = require('../controllers/ReceitaController');
const routes = express.Router();

routes.post('/receita/pesquisar', ReceitaController.pesquisarReceitas);
routes.get('/receita/favorito/:id', ReceitaController.consultarQuantidadeFavoritos);
routes.get('/receita/:id', ReceitaController.consultarPorID);
routes.get('/receita', ReceitaController.consultarTodos);
routes.get('/home', ReceitaController.buscarReceitasHome);
routes.post('/receita', ReceitaController.inserir);
routes.put('/receita/:id', ReceitaController.alterar);
routes.delete('/receita/:id', ReceitaController.deletar);

module.exports = routes;