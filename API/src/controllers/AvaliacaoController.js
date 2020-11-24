const Avaliacao = require('../models/Avaliacao');
const Receita  = require('../models/Receita');
const Usuario = require('../models/Usuario')

module.exports = {
    async inserir(req, res){
        const {nota, idReceita, idUsuario } = req.body;

        const isReceitaExistente = await Receita.findByPk(idReceita);
        if(!isReceitaExistente)
            return res
                .status(400)
                .json({error: 'Receita não encontrada!'});

        const isUsuarioExistente = await Usuario.findByPk(idUsuario);
        if(!isUsuarioExistente)
            return res
                .status(400)
                .json({error: 'Usuario não encontrado!'});

        const novaAvaliacao = await Avaliacao.create({
            nota,
            idReceita,
            idUsuario,
        })
        return res.json(novaAvaliacao);
    },

    async alterar(req, res){
        
    }
};