'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
     await queryInterface.createTable('Avaliacoes', {
      id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true,
        allowNull: false,           
      },
      idReceita: {
        type: Sequelize.INTEGER,
        references: { model: 'Receitas', key: 'id'},
        onUpdate: 'CASCADE',
        onDelete: 'CASCADE',
        allowNull: false,
      },
      idUsuario: {
        type: Sequelize.INTEGER,
        references: { model: 'Usuarios', key: 'id'},
        allowNull: false,
      },
      nota: Sequelize.INTEGER,
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
      await queryInterface.dropTable('Avaliacoes');     
  }
};
