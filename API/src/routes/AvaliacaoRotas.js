const express = require('express');
const AvaliacaoController = require('../controllers/AvaliacaoController');

const routes = express.Router();

//routes.get('/avaliacao', AvaliacaoController.inserir);
routes.post('/avaliacao', AvaliacaoController.inserir);
//routes.put('/avaliacao', AvaliacaoController.inserir);
//routes.delete('/avaliacao', AvaliacaoController.inserir);

module.exports = routes;