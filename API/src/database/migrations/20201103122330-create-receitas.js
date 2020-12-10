'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {    
      await queryInterface.createTable('Receitas', {
      id: {
          type: Sequelize.INTEGER,
          primaryKey: true,
          autoIncrement: true,
          allowNull: false,           
      },      
      idUsuario: {
        type: Sequelize.INTEGER,
        references: { model: 'Usuarios', key: 'id'},
        onUpdate: 'CASCADE',
        onDelete: 'CASCADE',
        allowNull: false
      },
      idCategoria:{
        type: Sequelize.INTEGER,
        references: { model: 'Categoria', key: 'id'},
        onUpdate: 'CASCADE',
        onDelete: 'CASCADE',
        allowNull: false,
      },
      titulo: {
          type: Sequelize.STRING,
          allowNull: false,
      },
      ingredientes: {
          type: Sequelize.STRING,
          allowNull: false,
      },
      modoDePreparo: {
          type: Sequelize.STRING,
          allowNull: false,
      },
      tempoDePreparo: {
          type: Sequelize.INTEGER,
          allowNull: false,
      },
      rendimento: {
          type: Sequelize.INTEGER,
          allowNull: false,
      },
      /*img: {
        type: Sequelize.BLOB('long'),
        allowNull: false,
      },*/
      createdAt: {
        type: Sequelize.DATE,
        allowNull: false,
      },
      updatedAt: {
        type: Sequelize.DATE,
        allowNull: false,
      },
  });
  },

  down: async (queryInterface, Sequelize) => {      
      await queryInterface.dropTable('Receitas');     
  }
};
