const Categoria = require('../models/Categoria');

module.exports = {
    async consultarPorID(req, res){
        const { id } = req.params;

        const categoriaDesejada = await Categoria.findAll({
            where: {id: id},
            attributes: ['nome'],
            include: [{
                association: 'fkCategoriaReceita',
                as: 'listaReceitas'
            }]
        });

        if(!categoriaDesejada.length > 0)
            return res
                .status(404)
                .json({error: 'Categoria não encontrada.'});

        return res.json(categoriaDesejada);
    },  

    async consultarTodos(req, res){
        const listaCategorias = await Categoria.findAll({
            attributes: ['id', 'nome'],            
            order: ['nome']
        });

        if(listaCategorias.length == 0)
            return res
                .status(404)
                .json({error: 'Nenhuma categoria foi encontrada.'});

        return res.json(listaCategorias);
    },

    async inserir(req, res){
        const isNomeExistente = await Categoria.findAll({ where: {nome: req.body.nome}});
        if(isNomeExistente.length > 0)
            return res
                .status(401)
                .json({error: 'Nome de categoria já existe!'});

        const { id, nome } = await Categoria.create(req.body);
        return res.json({
            id,
            nome,
        })
    },

    async alterar(req, res){
        const { id } = req.params;

        const categoriaDesejada = await Categoria.findByPk(id);
        if(!categoriaDesejada)
            return res
            .status(404)
            .json({error: 'Categoria não encontrada.'});

        if(categoriaDesejada.nome != req.body.nome){
            const isNomeUtilizado = await Categoria.findAll({ where: {nome: req.body.nome}});
            if(isNomeUtilizado.length > 0){
                return res
                .status(401)
                .json({error: 'Já existe uma categoria com esse nome!'});
            }
        }

        const {nome} = await categoriaDesejada.update(req.body);

        return res.json({
            id,
            nome,
        })
    },

    async deletar(req, res){
        const {id} = req.params;
        
        const categoriaDesejada = await Categoria.findByPk(id);

        if(!categoriaDesejada)
            return res
            .status(404)
            .json({error: 'Categoria não encontrada.'});
        
        await categoriaDesejada.destroy();

        return res
            .json({message: 'Categoria deletada com sucesso!'});
    },
};