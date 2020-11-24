const express = require('express');
const usuario = require('./routes/UsuarioRotas');
const categoria = require('./routes/CategoriaRotas');
const avaliacao = require('./routes/AvaliacaoRotas');
const receita = require('./routes/ReceitaRotas');
const favorito = require('./routes/FavoritoRotas');

require('./database');

const app = express();

app.use(express.json());
app.use(usuario);
app.use(categoria);
app.use(avaliacao);
app.use(receita);
app.use(favorito);

app.listen(3333, function(){
    console.log('API ligada!');
});