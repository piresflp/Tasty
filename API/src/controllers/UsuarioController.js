const Usuario = require('../models/Usuario');
const Receita = require('../models/Receita');
const Favorito = require('../models/Favorito');
const bcrypt = require('bcrypt');

module.exports = {
    async consultarReceitasFavoritas(req, res){
        const { id } = req.params;

        const isUsuarioExistente = await Usuario.findByPk(id);
        if(!isUsuarioExistente)
            return res
                .status(400)
                .json({error: 'Usuário não encontrado!'});

        const listaReceitasFavoritadas = await Favorito.findAll({
            where: {idUsuario: id},
            attributes: [],
            include: [{
                association: 'fkFavoritoReceita',
                attributes: ['id', 'idUsuario', 'idCategoria', 'titulo', 'ingredientes', 'modoDePreparo', 'tempoDePreparo', 'rendimento'],
                include: [{
                    association: 'fkReceitaCategoria',
                    attributes: ['id', 'nome']
                },
                {
                    association: 'fkReceitaUsuario', 
                    attributes: ['id', 'nome', 'nomeDeUsuario']
                },]
            }]
        });


        /*const listaReceitasFavoritadas = await Receita.findAll({
            where: { idUsuario: id },
            attributes: ['id', 'idUsuario', 'idCategoria', 'titulo', 'ingredientes', 'modoDePreparo', 'tempoDePreparo', 'rendimento'],
            include: [{
                    association: 'fkReceitaUsuario', 
                    attributes: ['id', 'nome', 'nomeDeUsuario']
                },
                {
                    association: 'fkReceitaCategoria',
                    attributes: ['id', 'nome']
                }]    
        });*/

        if(!listaReceitasFavoritadas.length > 0)
            return res
                .status(400)
                .json({error: 'Nenhuma receita foi favoritada ainda.'});

        return res.json(listaReceitasFavoritadas);
    },

    async consultarDadosPerfilUsuario(req, res)
    {
        const {id} = req.params;

        const isUsuarioExistente = await Usuario.findByPk(id);
        if(!isUsuarioExistente)
            return res
                .status(400)
                .json({error: 'Usuário não encontrado!'});

        const qtdReceitasCriadas = await Receita.count({
            where: {idUsuario: id}
        });

        const qtdReceitasFavoritadas = await Favorito.count({
            where: {idUsuario: id}
        });

        return res.json({
            qtdReceitasCriadas,
            qtdReceitasFavoritadas
        })
    },

    async verificarFavorito(req, res)
    {
        const {id, idReceita} = req.params;

        const isFavorito = await Favorito.findAll({
            where: {
                idUsuario: id,
                idReceita: idReceita
            }
        })

        if(isFavorito.length > 0)
            return res
                .json("1")

        //else
        return res
            .json("0")
    },

    async inserir(req, res){
        const isNomeUsuarioExistente = await Usuario.findAll({ where: {nomeDeUsuario: req.body.nomeDeUsuario}});
        if(isNomeUsuarioExistente.length > 0 ){
            return res
                .status(401)
                .json({ error: 'Já existe um usuário com esse nome de usuário!' });
        }

        const isEmailExistente = await Usuario.findAll({ where: {email: req.body.email}});
        if(isEmailExistente.length > 0){
            return res
                .status(401)
                .json({ error: 'Já existe um usuário com esse email!' });
        }       

        const { id, nome, email, nomeDeUsuario, senha} = await Usuario.create(req.body); 
        return res
            .status(200)
            .json({
            id,
            nome,
            email,
            nomeDeUsuario,
            senha,
        });        
    },

    async autenticar(req, res){
        const usuarioDesejado = await Usuario.findAll({where: {email: req.body.email}}) /* sempre retornará so máximo 1 usuário
                                                                                         não foi utilizado "Usuario.findOne" por conta de defeitos no Sequelize*/
     
        if(usuarioDesejado.length == 0)
            return res
                .status(401)
                .json({error: 'Email ou senha incorretos.'});

        await bcrypt.compare(req.body.senha, usuarioDesejado[0].senha).then(result => {
            if(!result)
                return res
                .status(401)
                .json({error:'Email ou senha incorretos.'});

            const {id, nome, email, nomeDeUsuario, senha} = usuarioDesejado[0];
            return res
            .status(200)
            .json({
                id,
                nome,
                email,
                nomeDeUsuario
            });
        });
    }
};