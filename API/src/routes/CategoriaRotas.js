const express = require('express');
const CategoriaController = require('../controllers/CategoriaController');

const routes = express.Router();

routes.get('/categoria/:id', CategoriaController.consultarPorID);
routes.get('/categoria', CategoriaController.consultarTodos);
routes.post('/categoria', CategoriaController.inserir);
routes.put('/categoria/:id', CategoriaController.alterar);
routes.delete('/categoria/:id', CategoriaController.deletar);

module.exports = routes;