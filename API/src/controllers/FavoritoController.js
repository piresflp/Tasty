const Favorito = require('../models/Favorito');
const Receita = require('../models/Receita');
const Usuario = require('../models/Usuario');

module.exports = {
    async inserir(req, res){
        const isUsuarioExistente = await Usuario.findByPk(req.body.idUsuario);
        if(!isUsuarioExistente)
            return res
                .status(400)
                .json({error: 'Usuário não encontrado!'});

        const isReceitaExistente = await Receita.findByPk(req.body.idReceita);
        if(!isReceitaExistente)
            return res
                .status(400)
                .json({error: 'Receita não encontrada!'});

        const { idUsuario, idReceita } = await Favorito.create(req.body);
        return res.json({
            idUsuario,
            idReceita,
        });
    },

    async deletar(req, res){
        const {idUsuario, idReceita} = req.params;

        await Favorito.destroy({
            where: {
                idUsuario: idUsuario,
                idReceita: idReceita
            }
        });

        return res
            .json({message: 'Favorito deletado com sucesso!'});
    } 
};