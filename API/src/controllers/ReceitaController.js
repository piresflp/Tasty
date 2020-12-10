const Receita = require('../models/Receita');
const Usuario = require('../models/Usuario');
const Categoria = require('../models/Categoria');
const Favorito = require('../models/Favorito');
const Sequelize = require("sequelize")

module.exports = {
    async pesquisarReceitas(req, res){	
        const input = req.body.input;	

        const Op = Sequelize.Op;	
        const receitasEncontradas = await Receita.findAll({	
            where: {	
                [Op.or]: [	
                    {	
                        titulo: {	
                            [Op.like]: `%`+input+`%`	
                        }	
                    },	
                    {	
                        ingredientes: {	
                            [Op.like]: `%`+input+`%`	
                        }	
                    }	
                ]	
            },	
            attributes: ['id', 'idUsuario', 'idCategoria', 'titulo', 'ingredientes', 'modoDePreparo', 'tempoDePreparo', 'rendimento'],	
            include: [{	
                association: 'fkReceitaCategoria',	
                attributes: ['id', 'nome']	
            },	
            {	
                association: 'fkReceitaUsuario', 	
                attributes: ['id', 'nome', 'nomeDeUsuario']	
            },]	
        })	
        return res.json(receitasEncontradas);	
    },

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
            //img,
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
    },

    async buscarReceitasHome(req, res){
        const qtdReceitas = await Receita.count();
        idReceita1 = Math.floor(Math.random()*qtdReceitas +1);
        idReceita2 = Math.floor(Math.random()*qtdReceitas +1);
        idReceita3 = Math.floor(Math.random()*qtdReceitas +1);
        receita1 = await Receita.findByPk(idReceita1, {
            attributes: ['id', 'idUsuario', 'idCategoria', 'titulo', 'ingredientes', 'modoDePreparo', 'tempoDePreparo', 'rendimento'],	
            include: [{	
                association: 'fkReceitaCategoria',	
                attributes: ['id', 'nome']	
            },	
            {	
                association: 'fkReceitaUsuario', 	
                attributes: ['id', 'nome', 'nomeDeUsuario']	
            },]	
        });
        receita2 = await Receita.findByPk(idReceita2, {
            attributes: ['id', 'idUsuario', 'idCategoria', 'titulo', 'ingredientes', 'modoDePreparo', 'tempoDePreparo', 'rendimento'],	
            include: [{	
                association: 'fkReceitaCategoria',	
                attributes: ['id', 'nome']	
            },	
            {	
                association: 'fkReceitaUsuario', 	
                attributes: ['id', 'nome', 'nomeDeUsuario']	
            },]	
        });
        receita3 = await Receita.findByPk(idReceita3, {
            attributes: ['id', 'idUsuario', 'idCategoria', 'titulo', 'ingredientes', 'modoDePreparo', 'tempoDePreparo', 'rendimento'],	
            include: [{	
                association: 'fkReceitaCategoria',	
                attributes: ['id', 'nome']	
            },	
            {	
                association: 'fkReceitaUsuario', 	
                attributes: ['id', 'nome', 'nomeDeUsuario']	
            },]	
        });

        const listaReceitas = [receita1, receita2, receita3];
        return res.json(listaReceitas);
    }
};