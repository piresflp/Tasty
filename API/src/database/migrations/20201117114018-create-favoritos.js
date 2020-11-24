'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
     await queryInterface.createTable('Favoritos', {
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
        allowNull: false,
      },
      idReceita: {
        type: Sequelize.INTEGER,
        references: { model: 'Receitas', key: 'id'},
        allowNull: false,
      },      
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
      await queryInterface.dropTable('Favoritos');     
  }
};
