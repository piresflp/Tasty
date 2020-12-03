const Receita = require('../models/Receita');
const Usuario = require('../models/Usuario');
const Categoria = require('../models/Categoria');
const Favorito = require('../models/Favorito');

module.exports = {
    async consultarQuantidadeFavoritos(req, res){
        const { id } = req.params;

        const qtdFavoritos = await Favorito.count({
            where: { idReceita: id }
        })

        return res.json(qtdFavoritos.toString());
    },

    async consultarPorID(req, res){
        const { id } = req.params;

        const receitaDesejada = await Receita.findByPk(id, {
            include: [{
                association: 'fkReceitaUsuario', 
                attributes: ['id', 'nome', 'nomeDeUsuario']
            }]        
        });

        if(!receitaDesejada)
            return res
                .status(404)
                .json({error: 'Receita não encontrada.'});

        return res.json(receitaDesejada);
    },

    async consultarTodos(req, res){
        const listaReceitas = await Receita.findAll({
            include: [{
                association: 'fkReceitaCategoria',
                attributes: ['id', 'nome']
            },
            {
                association: 'fkReceitaUsuario', 
                attributes: ['id', 'nome', 'nomeDeUsuario']
            }]
        });

        if(listaReceitas.length == 0)
            return res
                .status(404)
                .json({error: 'Nenhuma receita foi encontrada.'});

        return res.json(listaReceitas);
    },

    async inserir(req, res){
        const isUsuarioExistente = await Usuario.findByPk(req.body.idUsuario);
        if(!isUsuarioExistente)
            return res
                .status(400)
                .json({error: 'Usuário não encontrado!'});

        const isCategoriaExistente = await Categoria.findByPk(req.body.idCategoria);
        if(!isCategoriaExistente)
            return res
                .status(400)
                .json({error: 'Categoria não encontrada!'});

        const { id, titulo, ingredientes, modoDePreparo, tempoDePreparo, rendimento, fkIdUsuario, fkIdCategoria } = await Receita.create(req.body);
        return res.json({
            id,
            titulo,
            ingredientes,
            modoDePreparo,
            tempoDePreparo,
            rendimento,
            fkIdUsuario,
            fkIdCategoria,
        })
    },

    async alterar(req, res){
        const { id } = req.params;

        const receitaDesejada = await Receita.findByPk(id);
        if(!receitaDesejada)
            return res
            .status(404)
            .json({error: 'Receita não encontrada.'});       

        const {titulo, ingredientes, modoDePreparo, tempoDePreparo, rendimento, fkIdUsuario, fkIdCategoria} = await receitaDesejada.update(req.body);

        return res.json({
            id,
            titulo,
            ingredientes,
            modoDePreparo,
            tempoDePreparo,
            rendimento,
            fkIdUsuario,
            fkIdCategoria,
        })
    },

    async deletar(req, res){
        const {id} = req.params;
        
        const receitaDesejada = await Receita.findByPk(id);

        if(!receitaDesejada)
            return res
            .status(404)
            .json({error: 'Receita não encontrada.'});
        
        await receitaDesejada.destroy();
        return res
            .json({message: 'Receita deletada com sucesso!'});
    }
};