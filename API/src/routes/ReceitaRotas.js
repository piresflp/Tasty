const express = require('express');
const ReceitaController = require('../controllers/ReceitaController');
const routes = express.Router();

routes.get('/receita/:id', ReceitaController.consultarPorID);
routes.get('/receita', ReceitaController.consultarTodos);
routes.post('/receita', ReceitaController.inserir);
routes.put('/receita/:id', ReceitaController.alterar);
routes.delete('/receita/:id', ReceitaController.deletar);

module.exports = routes;