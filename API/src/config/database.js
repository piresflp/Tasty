module.exports = {
    dialect: 'mssql',
    dialectOptions: {
        options: {
            encrypt: false,            
            validateBulkLoadParameters: true
        },
    },
    host: 'regulus.cotuca.unicamp.br',
    username: 'BD19169',
    password: 'BD19169',
    database: 'BD19169',
    define: {
        timestamps: true,
        freezeTableName: false,
    }
};
