'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {    
     await queryInterface.createTable('Usuarios', {
      id: {
          type: Sequelize.INTEGER,
          primaryKey: true,
          autoIncrement: true,
          allowNull: false,
      },
      nome: Sequelize.STRING,
      email: {
        type: Sequelize.STRING,
        unique: true,
      },
      nomeDeUsuario: {
          type: Sequelize.STRING,
          unique: true,
      },
      senha: {
          type: Sequelize.STRING,
          allowNull: false
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
     await queryInterface.dropTable('Usuarios');     
  }
};
