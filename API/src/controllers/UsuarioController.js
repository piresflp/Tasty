const Usuario = require('../models/Usuario');
const Receita = require('../models/Receita');
const bcrypt = require('bcrypt');

module.exports = {
    async consultarReceitasFavoritas(req, res){
        const { id } = req.params;

        const isUsuarioExistente = await Usuario.findByPk(id);
        if(!isUsuarioExistente)
            return res
                .status(400)
                .json({error: 'Usuário não encontrado!'});

        const listaReceitaFavoritadas = await Receita.findAll({
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
        });

        if(!listaReceitaFavoritadas.length > 0)
            return res
                .status(400)
                .json({error: 'Nenhuma receita foi favoritada ainda.'});

        return res.json(listaReceitaFavoritadas);
    },

    async inserir(req, res){
        if(req.body == null)
            return res
                .code(400)
                .json({error: 'Informações inválidas!'});


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