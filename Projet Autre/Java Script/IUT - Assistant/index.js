// Importation des modules \u004D\u0061\u0064\u0065\u0020\u0062\u0079\u0020\u005F\u0041\u0050\u0059\u004F
const {Client,GatewayIntentBits,Routes,EmbedBuilder,ActionRowBuilder,ButtonBuilder,ButtonStyle, PermissionsBitField} = require("discord.js");
const { REST } = require("@discordjs/rest");
const client = new Client({
    intents: [GatewayIntentBits.Guilds,GatewayIntentBits.GuildMessages,GatewayIntentBits.MessageContent,],});

const moment = require("moment");
const stringSimilarity = require('string-similarity');
const fs = require("fs");
const ical = require("ical");

const app = require("./package.json");
const config = require('./config');
const { DevoirManager } = require("./DevoirManager");
const Logger = require("./Logger");

const logger = new Logger();


const createEmbed = (title, description, color) => {
  const embed = new EmbedBuilder()
    .setColor(parseInt(color, 16))
    .setTitle(title)
    .setDescription(description.replace(/\\n/g, '\n'))
    .setFooter({
      text: config.appearance.embedFooterText,
      iconURL: config.appearance.embedFooterIconUrl
    })
    .setTimestamp();

  return embed;
};

function hasRole(interaction, roleNames, name) {
  try {
    if (!interaction || !interaction.member) {
      throw new Error("Interaction ou membre introuvable.");
    }

    logger.logMessage(
      `[\x1b[93m NOTICE \x1b[0m] Vérification des permissions d'interaction pour ${name}...`
    );

    // Vérifier si roleNames est un tableau et s'assurer qu'il n'est pas vide
    if (!Array.isArray(roleNames) || roleNames.length === 0) {
      throw new Error("roleNames doit être un tableau non vide.");
    }

    // Vérifier si le membre possède au moins un des rôles
    const hasRole = interaction.member.roles.cache.some((role) =>
      roleNames.includes(role.name)
    );

    logger.logMessage(
      hasRole
        ? `[\x1b[92m SUCCESS \x1b[0m] ${name} possède au moins un des rôles : ${roleNames.join(", ")}.`
        : `[\x1b[94m EVENT \x1b[0m] ${name} ne possède aucun des rôles : ${roleNames.join(", ")}.`
    );

    return hasRole;
  } catch (error) {
    logger.logMessage(
      `   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de la vérification des permissions pour ${name} : ${error}`
    );
    return false;
  }
}

function getRandomResponse(responses) {
  return responses[Math.floor(Math.random() * responses.length)];
}

// Gestion des interactions
client.on("interactionCreate", async (interaction) => {
  if (!interaction.isCommand()) return;

  const { commandName, user } = interaction;

  if (commandName === "add") {
    try {
      const groupe = interaction.options.getString("groupe").trim().toUpperCase();
      const devoirManager = new DevoirManager(groupe);
      const devoir = interaction.options.getString("devoir").trim().toUpperCase(); // Convertir en majuscules
      const matiere = interaction.options
        .getString("matiere")
        .trim()
        .toUpperCase(); // Convertir en majuscules
      const date = interaction.options.getString("date");

      logger.logMessage(
        `[\x1b[94m EVENT \x1b[0m] Interaction : commande "ADD" par ${user.username} pour le groupe ${groupe}`
      );

      // Vérifier si la date est valide
      if (!moment(date, "DD-MM-YYYY", true).isValid()) {
        logger.logMessage("   [\x1b[91m ERROR \x1b[0m] Erreur lors de la création de devoir : Date invalide");
        return await interaction.reply(
          "Date invalide. Utilisez le format JJ-MM-AAAA."
        );
      }
  
      // Ajouter le devoir
      const addedDevoir = devoirManager.addDevoir({
        groupe,
        devoir,
        matiere,
        date,
        addedBy: user.username,
      });

      if (!addedDevoir) {
        logger.logMessage("   [\x1b[91m ERROR \x1b[0m] Le devoir existe déjà.");
        return await interaction.reply("Le devoir existe déjà.");
      }

      try {
        DevoirManager.applyToAll('removeExpiredDevoirs');
      } catch (error) {
        logger.logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de l'enregistrement des données : ${error}`
        );
        return await interaction.reply("Erreur lors de l'enregistrement des données.");
      }
  
      // Créer l'embed pour la réponse
      const embed = createEmbed(
        `Devoir ajouté (${groupe})`,
        `**Devoir :** ${devoir}\n**Matière :** ${matiere}\n**Date limite :** ${date}\n**Ajouté par :** ${user.username}`, config.appearance.embedGlobalColor
      );
      
      logger.logMessage(
        `[\x1b[92m SUCCESS \x1b[0m] Devoir ajouté (${groupe}) | Devoir : ${devoir}, Matière : ${matiere}, Date limite : ${date}, Ajouté par : ${user.username}`
      );
      await interaction.reply({
        embeds: [embed],
      });
  
    } catch (error) {
      // Gérer les erreurs globales
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur est survenue lors de l'exécution de la commande "ADD" : ${error}`);
      await interaction.reply(
        "Une erreur est survenue lors de l'ajout du devoir. Veuillez réessayer plus tard."
      );
    }
  } else if (commandName === "modify") {
    try {
      const id = interaction.options.getInteger("id");
      const groupe = interaction.options.getString("groupe").trim().toUpperCase();
      const devoirManager = new DevoirManager(groupe);
      const newDevoir =
        interaction.options.getString("devoir")?.trim().toUpperCase() || null;
      const newMatiere =
        interaction.options.getString("matiere")?.trim().toUpperCase() || null;
      const newDate = interaction.options.getString("date") || null;
      
      logger.logMessage(
        `[\x1b[94m EVENT \x1b[0m] Interaction : commande "MODIFY" par ${user.username} pour le groupe ${groupe}`
      );
      
      let devoirToModify;
      
      try {
        devoirToModify = devoirManager.devoirs.find((d) => d.id === id);
        if (!devoirToModify) {
          throw new Error(`Devoir non trouvé avec l'ID ${id}`);
        }
      } catch (error) {
        logger.logMessage(
          `   [\x1b[91m ERROR \x1b[0m] ${error}`
        );
        return await interaction.reply(`Aucun devoir trouvé avec l'ID ${id}.`);
      }
    
      try {
        if (newDevoir) devoirToModify.devoir = newDevoir;
        if (newMatiere) devoirToModify.matiere = newMatiere;
        if (newDate && moment(newDate, "DD-MM-YYYY", true).isValid()) {
          devoirToModify.date = newDate;
        } else if (newDate) {
          throw new Error("Date invalide");
        }
      } catch (error) {
        logger.logMessage(
          `   [\x1b[91m ERROR \x1b[0m] ${error}`
        );
        return await interaction.reply("Date invalide. Utilisez le format JJ-MM-AAAA.");
      }
    
      try {
        await DevoirManager.applyToAll('removeExpiredDevoirs');
        DevoirManager.applyToAll('sortDevoirs');
      } catch (error) {
        logger.logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de l'enregistrement des données : ${error}`
        );
        return await interaction.reply("Erreur lors de l'enregistrement des données.");
      }
    
      try {
        const embed = createEmbed(
          `Devoir modifié (${groupe})`,
          `**ID :** ${id}\n**Devoir :** ${devoirToModify.devoir}\n**Matière :** ${devoirToModify.matiere}\n**Date limite :** ${devoirToModify.date}`, config.appearance.embedGlobalColor
        );
        await interaction.reply({
          embeds: [embed],
        });
      } catch (error) {
        logger.logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de la création de l'embed : ${error}`
        );
        return await interaction.reply("Erreur lors de la réponse.");
      }
    } catch (error) {
      logger.logMessage(
        `   [\x1b[91m ERROR \x1b[0m] Erreur inattendue : ${error}`
      );
      return await interaction.reply("Une erreur inattendue est survenue.");
    }
    
  } else if (commandName === "list") {
    try {   
      const groupe = interaction.options.getString("groupe").trim().toUpperCase();
      const devoirManager = new DevoirManager(groupe);
      let devoirList;
    
      logger.logMessage(
        `[\x1b[94m EVENT \x1b[0m] Interaction : commande "LIST" par ${user.username} pour le groupe ${groupe}`
      );
      try {
        devoirList = devoirManager.devoirs.length
          ? devoirManager.devoirs
              .map(
                (d) =>
                  `**ID :** ${d.id}   |   **${d.devoir}** en **${d.matiere}**, pour le ${d.date} (ajouté par ${d.addedBy})`
              )
              .join("\n\n")
          : "Aucun devoir n'a été ajouté.";
      } catch (error) {
        logger.logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de la génération de la liste des devoirs : ${error}`
        );
        return await interaction.reply("Une erreur est survenue lors de la génération de la liste des devoirs.");
      }
    
      let embedList;
    
      try {
        embedList = createEmbed(`Liste des devoirs (${groupe})`, devoirList, config.appearance.embedGlobalColor);
      } catch (error) {
        logger.logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de la création de l'embed : ${error}`
        );
        return await interaction.reply("Erreur lors de la création de l'embed.");
      }
    
      try {
        await interaction.reply({
          embeds: [embedList],
        });
      } catch (error) {
        logger.logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de l'envoi de la réponse : ${error}`
        );
        return await interaction.reply("Erreur lors de l'envoi de la réponse.");
      }
    } catch (error) {
      logger.logMessage(
        `   [\x1b[91m ERROR \x1b[0m] Erreur inattendue : ${error}`
      );
      return await interaction.reply("Une erreur inattendue est survenue.");
    }    
  } else if (commandName === "check") {
    try {
      logger.logMessage(
        `[\x1b[94m EVENT \x1b[0m] Interaction : commande "CHECK" par ${user.username}`
      );
    
      await interaction.reply("Vérification des devoirs à venir en cours...");
    
      try {
        await checkAllGroups(interaction);
      } catch (error) {
        logger.logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de la vérification des devoirs à venir : ${error}`
        );
        return await interaction.reply("Une erreur est survenue lors de la vérification des devoirs à venir.");
      }
    
    } catch (error) {
      logger.logMessage(
        `   [\x1b[91m ERROR \x1b[0m] Erreur inattendue : ${error}`
      );
      return await interaction.reply("Une erreur inattendue est survenue.");
    }    
  } else if (commandName === "sort") {
    try {
      if (!hasRole(interaction, config.permissions.adminRoles, user.username)) {
        // Vérifie si l'utilisateur a le rôle Admin
        logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Permission refusée pour ${user.username}, Interaction : commande "SORT"`);
        return await interaction.reply({
          content: "Vous n'avez pas la permission d'utiliser cette commande.",
          flags: 64,
        });
      }
    
      logger.logMessage(
        `[\x1b[94m EVENT \x1b[0m] Interaction : commande "SORT" par ${user.username}`
      );
    
      try {
        DevoirManager.applyToAll('sortDevoirs');
        await interaction.reply("Tri terminé avec succès !");
      } catch (error) {
        logger.logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors du tri des devoirs : ${error}`
        );
        return await interaction.reply("Une erreur est survenue lors du tri des devoirs.");
      }
    } catch (error) {
      logger.logMessage(
        `   [\x1b[91m ERROR \x1b[0m] Erreur inattendue : ${error}`
      );
      return await interaction.reply("Une erreur inattendue est survenue.");
    }    
  } else if (commandName === "regenerate") {
    try {
      if (!hasRole(interaction, config.permissions.adminRoles, user.username)) {
        // Vérifie si l'utilisateur a le rôle Admin
        logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Permission refusée pour ${user.username}, Interaction : commande "REGENERATEIDS"`);
        return await interaction.reply({
          content: "Vous n'avez pas la permission d'utiliser cette commande.",
          flags: 64,
        });
      }
    
      const groupe = interaction.options.getString("groupe").trim().toUpperCase();
      const devoirManager = new DevoirManager(groupe);
      logger.logMessage(`[\x1b[94m EVENT \x1b[0m] Interaction : commande "REGENERATEIDS" par ${user.username} pour le groupe ${groupe}`);

      try {
        devoirManager.regenerateIds();
        await interaction.reply("Les identifiants de devoirs ont été régénérés avec succès !");
        logger.logMessage("[\x1b[92m SUCCESS \x1b[0m] Les identifiants de devoirs ont été régénérés avec succès !");
      } catch (error) {
        logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de la régénération des identifiants : ${error}`);
        await interaction.reply({
          content: "Une erreur est survenue lors de la régénération des identifiants. Veuillez réessayer plus tard.",
          flags: 64,
        });
      }
    } catch (error) {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur inattendue s'est produite dans la commande "REGENERATEIDS" : ${error}`);
      await interaction.reply({
        content: "Une erreur inattendue est survenue. Veuillez contacter un administrateur.",
        flags: 64,
      });
    }    
  } else if (commandName === "purge") {
    try {
      if (!hasRole(interaction, config.permissions.adminRoles, user.username)) {
        // Vérifie si l'utilisateur a le rôle Admin
        logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Permission refusée pour ${user.username}, Interaction : commande "PURGE"`);
        return await interaction.reply({
          content: "Vous n'avez pas la permission d'utiliser cette commande.",
          flags: 64,
        });
      }
        
      const groupe = interaction.options.getString("groupe").trim().toUpperCase();
      const devoirManager = new DevoirManager(groupe);
      logger.logMessage(`[\x1b[94m EVENT \x1b[0m] Interaction : commande "PURGE" par ${user.username} pour le groupe ${groupe}`);

      const row = new ActionRowBuilder().addComponents(
        new ButtonBuilder()
          .setCustomId("confirm_purge")
          .setLabel("Confirmer")
          .setStyle(ButtonStyle.Danger),
        new ButtonBuilder()
          .setCustomId("cancel_purge")
          .setLabel("Annuler")
          .setStyle(ButtonStyle.Secondary)
      );
    
      await interaction.reply({
        content: `Es-tu sûr de vouloir purger tous les devoirs du groupe ${groupe} ?`,
        components: [row],
        flags: 64,
      });
    
      const filter = (i) => i.user.id === user.id;
      const collector = interaction.channel.createMessageComponentCollector({
        filter,
        time: 15000,
      });
    
      collector.on("collect", async (i) => {
        try {
          if (i.customId === "confirm_purge") {
            logger.logMessage(`[\x1b[94m EVENT \x1b[0m] Interaction : commande "PURGE" confirmée par ${user.username} pour le groupe ${groupe}`);
            devoirManager.devoirs = [];
            devoirManager.saveDevoirs();
            await i.update({
              content: "Tous les devoirs du groupe ont été supprimés.",
              components: [],
              flags: 64,
            });
            logger.logMessage("[\x1b[92m SUCCESS \x1b[0m] Purge effectuée avec succès !");
          } else {
            await i.update({
              content: "Purge annulée.",
              components: [],
              flags: 64,
            });
            logger.logMessage(`[\x1b[94m EVENT \x1b[0m] Commande "PURGE" annulée par ${user.username}`);
          }
        } catch (error) {
          logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la gestion de la commande "PURGE" : ${error}`);
          await i.update({
            content: "Une erreur est survenue lors du traitement de la commande.",
            components: [],
            flags: 64,
          });
        }
      });
    
      collector.on("end", (collected) => {
        try {
          if (!collected.size) {
            logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Temps écoulé, aucune action effectuée, Interaction : commande "PURGE"`);
            interaction.editReply({
              content: "Temps écoulé, aucune action effectuée.",
              components: [],
              flags: 64,
            });
          }
        } catch (error) {
          logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la gestion de la fin de la collecte de la commande "PURGE" : ${error}`);
        }
      });
    } catch (error) {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur inattendue dans la commande "PURGE" : ${error}`);
      await interaction.reply({
        content: "Une erreur inattendue est survenue lors du traitement de la commande.",
        flags: 64,
      });
    }    
  } else if (commandName === "about") {
    try {
      logger.logMessage(
        `[\x1b[94m EVENT \x1b[0m] Interaction : commande "ABOUT" par ${user.username}`
      );
      
      // Vérification si les variables nécessaires sont définies
      if (!app.name || !app.author || !app.Appversion || !app.license) {
        throw new Error("Certaines informations sont manquantes pour afficher les détails de l'application.");
      }
    
      await interaction.reply(
        `Name : ${app.name}\nAuthor : ${app.author}\nVersion : ${app.Appversion}\nLicense : ${app.license}`
      );
    } catch (error) {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la commande "ABOUT" : ${error}`);
      await interaction.reply({
        content: "Une erreur est survenue lors de l'exécution de la commande 'ABOUT'.",
        flags: 64,
      });
    }    
  } else if (commandName === "kill") {
    try {
      // Vérifie si l'utilisateur a le rôle Admin
      if (!hasRole(interaction, config.permissions.adminRoles, user.username)) {
        logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Permission refusée pour ${user.username}, Interaction : commande "KILL"`);
        return await interaction.reply({
          content: "Vous n'avez pas la permission d'utiliser cette commande.",
          flags: 64,
        });
      }
    
      logger.logMessage(`[\x1b[94m EVENT \x1b[0m] Interaction : commande "KILL" par ${user.username}`);
    
      // Crée les boutons de confirmation et d'annulation
      const row = new ActionRowBuilder().addComponents(
        new ButtonBuilder()
          .setCustomId("confirm_kill")
          .setLabel("Confirmer")
          .setStyle(ButtonStyle.Danger),
        new ButtonBuilder()
          .setCustomId("cancel_kill")
          .setLabel("Annuler")
          .setStyle(ButtonStyle.Secondary)
      );
    
      // Demande à l'utilisateur de confirmer ou annuler l'arrêt du bot
      await interaction.reply({
        content: "Êtes-vous sûr de vouloir arrêter le bot ?",
        components: [row],
        flags: 64,
      });
    
      // Crée un collector pour écouter les boutons
      const filter = (i) => i.user.id === user.id; // Filtrer par l'utilisateur qui a exécuté la commande
      const collector = interaction.channel.createMessageComponentCollector({
        filter,
        time: 15000, // Temps de validité pour la confirmation (15 secondes)
      });
    
      collector.on("collect", async (i) => {
        try {
          if (i.customId === "confirm_kill") {
            // L'utilisateur a confirmé, on arrête le bot
            logger.logMessage(`[\x1b[94m EVENT \x1b[0m] Interaction : commande "KILL" confirmée par ${user.username}`);
            await i.update({
              content: "Arrêt du bot en cours...",
              components: [],
              flags: 64,
            });
            setTimeout(function() {
              client.destroy();
              process.exit(1); // Arrêt du bot
            }, 5000);
          } else if (i.customId === "cancel_kill") {
            // L'utilisateur a annulé
            await i.update({
              content: "Arrêt du bot annulé.",
              components: [],
              flags: 64,
            });
            logger.logMessage(`[\x1b[94m EVENT \x1b[0m] Interaction : commande "KILL" annulée par ${user.username}`);
          }
        } catch (error) {
          logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la collecte de l'interaction : ${error}`);
        }
      });
    
      collector.on("end", (collected) => {
        try {
          if (!collected.size) {
            logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Temps écoulé, aucune action effectuée, Interaction : commande "KILL"`);
            interaction.editReply({
              content: "Temps écoulé, aucune action effectuée.",
              components: [],
              flags: 64,
            });
          }
        } catch (error) {
          logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de l'expiration du collector : ${error}`);
        }
      });
    
    } catch (error) {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur dans la commande "KILL" : ${error}`);
      await interaction.reply({
        content: "Une erreur s'est produite lors de l'exécution de la commande.",
        flags: 64,
      });
    }
    
  } else if (commandName === "delete") {
    try {
      if (!hasRole(interaction, config.permissions.adminRoles, user.username)) {
        // Vérifie si l'utilisateur a le rôle Admin
        logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Permission refusée pour ${user.username}, Interaction : commande "DELETE"`);
        return await interaction.reply({
          content: "Vous n'avez pas la permission d'utiliser cette commande.",
          flags: 64,
        });
      }

      const id = interaction.options.getInteger("id");
      const groupe = interaction.options.getString("groupe").trim().toUpperCase();
      const devoirManager = new DevoirManager(groupe);
      logger.logMessage(`[\x1b[94m EVENT \x1b[0m] Interaction : commande "DELETE" par ${user.username} pour le groupe ${groupe}`);

      const deleted = devoirManager.deleteDevoir(id);

      if (deleted) {
        devoirManager.saveDevoirs();
        await interaction.reply(`Le devoir avec l'ID ${id} a été supprimé avec succès.`);
        logger.logMessage(`[\x1b[92m SUCCESS \x1b[0m] Devoir avec l'ID ${id} supprimé avec succès pour le groupe ${groupe}`);
      } else {
        await interaction.reply(`Aucun devoir trouvé avec l'ID ${id}.`);
        logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Aucun devoir trouvé avec l'ID ${id} pour le groupe ${groupe}`);
      }
    } catch (error) {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur inattendue dans la commande "DELETE" : ${error}`);
      await interaction.reply({
        content: "Une erreur inattendue est survenue lors du traitement de la commande.",
        flags: 64,
      });
    }
  } else if (commandName === "sendembed") {
    if (!hasRole(interaction, config.permissions.adminRoles, user.username)) {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Permission refusée pour ${user.username}, Interaction : commande "SEND"`);
      return await interaction.reply({
        content: "Vous n'avez pas la permission d'utiliser cette commande.",
        flags: 64,
      });
    }

    try {
      const channelId = interaction.options.getString("channel_id");
      const channelOption = interaction.options.getChannel("channel");
      const title = interaction.options.getString("title");
      const description = interaction.options.getString("description");
      const message = interaction.options.getString("message");

      if (!channelId && !channelOption) {
        return await interaction.reply({
          content: "Vous devez fournir soit l'ID du salon, soit sélectionner un salon.",
          flags: 64,
        });
      }

      const channel = channelOption || await client.channels.fetch(channelId);
      if (!channel) {
        return await interaction.reply({
          content: "Salon introuvable.",
          flags: 64,
        });
      }

      // Check if the bot has the necessary permissions
      const botPermissions = channel.permissionsFor(client.user);
      if (!botPermissions.has(PermissionsBitField.Flags.SendMessages) || !botPermissions.has(PermissionsBitField.Flags.EmbedLinks)) {
        return await interaction.reply({
          content: "Je n'ai pas les permissions nécessaires pour envoyer des messages ou des embeds dans ce salon.",
          flags: 64,
        });
      }

      const embed = createEmbed(
        title,
        description,
        config.appearance.embedGlobalColor
      );

      if (message) {
        await channel.send({ content: message, embeds: [embed] });
      } else {
        await channel.send({ embeds: [embed] });
      }

      await interaction.reply({
        content: "Message et embed envoyés avec succès.",
        flags: 64,
      });
    } catch (error) {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de l'exécution de la commande "sendembed" : ${error}`);
      await interaction.reply({
        content: "Une erreur est survenue lors de l'envoi de l'embed.",
        flags: 64,
      });
    }
  } else if (commandName === "send") {
    if (!hasRole(interaction, config.permissions.adminRoles, user.username)) {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Permission refusée pour ${user.username}, Interaction : commande "SEND"`);
      return await interaction.reply({
        content: "Vous n'avez pas la permission d'utiliser cette commande.",
        flags: 64,
      });
    }

    try {
      const channelId = interaction.options.getString("channel_id");
      const channelOption = interaction.options.getChannel("channel");
      const message = interaction.options.getString("message");

      if (!channelId && !channelOption) {
        return await interaction.reply({
          content: "Vous devez fournir soit l'ID du salon, soit sélectionner un salon.",
          flags: 64,
        });
      }

      const channel = channelOption || await client.channels.fetch(channelId);
      if (!channel) {
        return await interaction.reply({
          content: "Salon introuvable.",
          flags: 64,
        });
      }

      // Check if the bot has the necessary permissions
      const botPermissions = channel.permissionsFor(client.user);
      if (!botPermissions.has(PermissionsBitField.Flags.SendMessages)) {
        return await interaction.reply({
          content: "Je n'ai pas les permissions nécessaires pour envoyer des messages dans ce salon.",
          flags: 64,
        });
      }

      await channel.send(message);
      await interaction.reply({
        content: "Message envoyé avec succès.",
        flags: 64,
      });
    } catch (error) {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de l'exécution de la commande "send" : ${error}`);
      await interaction.reply({
        content: "Une erreur est survenue lors de l'envoi du message.",
        flags: 64,
      });
    }
  } else if (commandName === "modifyembed") {
    if (!hasRole(interaction, config.permissions.adminRoles, user.username)) {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Permission refusée pour ${user.username}, Interaction : commande "MODIFYEMBED"`);
      return await interaction.reply({
        content: "Vous n'avez pas la permission d'utiliser cette commande.",
        flags: 64,
      });
    }

    try {
      const messageId = interaction.options.getString("message_id");
      const channelId = interaction.options.getString("channel_id");
      const newTitle = interaction.options.getString("title");
      const newDescription = interaction.options.getString("description");
      const newMessage = interaction.options.getString("message");

      const channel = await client.channels.fetch(channelId);
      if (!channel) {
        return await interaction.reply({
          content: "Salon introuvable.",
          flags: 64,
        });
      }

      const message = await channel.messages.fetch(messageId);
      if (!message) {
        return await interaction.reply({
          content: "Message introuvable.",
          flags: 64,
        });
      }

      if (!message.embeds.length) {
        return await interaction.reply({
          content: "Aucun embed trouvé dans ce message.",
          flags: 64,
        });
      }

      const embed = message.embeds[0];
      const updatedEmbed = new EmbedBuilder(embed);

      if (newTitle) updatedEmbed.setTitle(newTitle);
      if (newDescription) updatedEmbed.setDescription(newDescription.replace(/\\n/g, '\n'));

      await message.edit({
        content: newMessage || message.content,
        embeds: [updatedEmbed],
      });

      await interaction.reply({
        content: "Embed modifié avec succès.",
        flags: 64,
      });
    } catch (error) {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de l'exécution de la commande "modifyembed" : ${error}`);
      await interaction.reply({
        content: "Une erreur est survenue lors de la modification de l'embed.",
        flags: 64,
      });
    }
  }
});

// Gestion des nouveaux messages
client.on('messageCreate', async (message) => {
  // Convertir le message en minuscules, enlever les espaces inutiles et les caractères spéciaux
  const content = message.content.replace(/[^\w\s]/g, '').trim().toLowerCase();
  const words = content.split(/\s+/); // Séparer le message en mots
  const maxWordsToCheck = 100; // Limite du nombre de mots à vérifier

  // Liste des mots-clés à détecter avec un indicateur pour savoir s'ils doivent être vérifiés sur la fin du message
  const keywords = {
    quoi: { words: ['quoi', 'quois', 'quwa', 'kwah', 'koi', 'kwe', 'kwé', 'koii', 'kwai', 'kwé', 'quoi', 'quoua', 'kwahh', 'koa', 'quoii', 'qwa'], checkEnd: true },
    qui: { words: ['qui', 'ki', 'kwi', 'kwy', 'kuy', 'quii', 'kwéé'], checkEnd: true },
    oui: { words: ['oui', 'ooui', 'ouii', 'oooui', 'ouiii', 'owii', 'ouwi', 'wii', 'ouee', 'oowi', 'ouiy', 'oüi', 'ouy', 'ooüi', 'ouay', 'ouhi'], checkEnd: true},    
    hein: { words: ['hein', 'hien', 'hinn', 'hyn', 'heinou', 'heinn'], checkEnd: false },
    genre: { words: ['genre', 'genre', 'jener', 'jenre', 'jhenre'], checkEnd: false },
    non: { words: ['non', 'noo', 'nno', 'nann', 'nan', 'nann', 'nonn'], checkEnd: true }
  };

  // Fonction de détection des mots similaires
  const getBestMatch = (word, keywordList) => {
    const bestMatch = stringSimilarity.findBestMatch(word, keywordList);
    return bestMatch.bestMatch.rating > 0.5; // Seuil de similarité
  };

  // Vérification si le message vient de toi
  if (!config.permissions.myID.includes(message.author.id)) {
    // Réponse pour les autres utilisateurs
    for (const [key, { words: keywordWords, checkEnd }] of Object.entries(keywords)) {
      if (checkEnd) {
        // Vérifier uniquement le dernier mot
        const lastWord = words[words.length - 1];
        if (getBestMatch(lastWord, keywordWords)) {
          const responses = getResponses(key);
          message.reply(getRandomResponse(responses));
          return;
        }
      } else {
        // Vérifier tous les mots jusqu'à la limite
        for (let i = 0; i < Math.min(words.length, maxWordsToCheck); i++) {
          if (getBestMatch(words[i], keywordWords)) {
            const responses = getResponses(key);
            message.reply(getRandomResponse(responses));
            return;
          }
        }
      }
    }
  } else {
    const responses = ["Attention mon seigneur", "Subnautica > Raft", ":pinched_fingers:", "Ok boomer.", "UwU", "Giga cringe.", "Le roi a parlé ! 👑", "Raft, c'est chill, ok ?", "Quand est-ce que tu me fais un update ? 🤔", "Je vais appeler les admins.", "Ça mérite un ban, ça non ?"];

    for (const [key, { words: keywordWords, checkEnd }] of Object.entries(keywords)) {
      if (checkEnd) {
        // Vérifier uniquement le dernier mot
        const lastWord = words[words.length - 1];
        if (getBestMatch(lastWord, keywordWords)) {
          message.reply(getRandomResponse(responses));
          return;
        }
      } else {
        // Vérifier tous les mots jusqu'à la limite
        for (let i = 0; i < Math.min(words.length, maxWordsToCheck); i++) {
          if (getBestMatch(words[i], keywordWords)) {
            message.reply(getRandomResponse(responses));
            return;
          }
        }
      }
    }
  }
});

// Fonction pour obtenir les réponses en fonction du mot-clé
const getResponses = (keyword) => {
  switch (keyword) {
    case 'quoi':
      return ["Feur", "Coubeh", "Chi"];
    case 'qui':
      return ["Kette"];
    case 'oui':
      return ["Stiti"];
    case 'hein':
      return ["Deux"];
    case 'genre':
      return ["Raconte pas ta vie", "Genre quoi ?", "Pas de drama ici, merci."];
    case 'non':
      return ["Bril"];
    default:
      return [];
  }
};

client.once("ready", async () => {
  logger.logMessage(`[\x1b[92m SUCCESS \x1b[0m] Connecté en tant que ${client.user.tag}\n`);
  
  // Mise à jour au démarrage
  logger.logMessage("[\x1b[93m NOTICE \x1b[0m] Lancement de la mise à jour de démarrage : \n");
  await logger.manageLogFiles();
  await DevoirManager.applyToAll('removeExpiredDevoirs');
  await DevoirManager.applyToAll('sortDevoirs');
  await checkAllGroups();
  await scheduleTasks();

  logger.logMessage("[\x1b[92m SUCCESS \x1b[0m] Le bot est prêt et en ligne !");
});

async function checkAllGroups(interaction = null) {
  for (const task of config.urls.tasks) {
    await checkUpcoming(task, interaction);
  }
}

async function checkUpcoming(task, interaction = null) {
  try {
    await fetchFromICS(task.icsUrl, task.Group);
    const channel = await client.channels.fetch(
      process.env.PROCESS_TEST_MODE === '1' ? config.ids.testChannelId : task.devoirsChannelId
    );

    const now = moment(); // Garder now intact
    logger.logMessage(`[\x1b[93m NOTICE \x1b[0m] Vérification des devoirs à envoyer pour le groupe ${task.Group} en cours...`);

    const devoirManager = new DevoirManager(task.Group);
    const devoirs = devoirManager.devoirs;

    // Créer des sections pour les devoirs sous 7 jours, QCM et SAE
    let reminderMessages = ""; // Section principale pour les devoirs sous 7 jours
    let qcmSection = ""; // Section QCM
    let saeSection = ""; // Section SAE
    
    for (const devoir of devoirs) {
      // Calculer les jours restants à partir de la date actuelle
      const daysRemaining = moment(devoir.date, "DD-MM-YYYY")
        .startOf("day")
        .diff(now.clone().startOf("day"), "days");
    
      // Ajouter chaque rappel dans la variable devoirMessage
      const devoirMessage = 
        `**Devoir :** ${devoir.devoir}\n**Matière :** ${devoir.matiere}\n**Date limite :** ${devoir.date}\n` +
        `**Temps restant : ${
          daysRemaining === 0
            ? "aujourd'hui"
            : daysRemaining > 0
            ? daysRemaining + " jour" + (daysRemaining > 1 ? "s" : "")
            : Math.abs(daysRemaining) +
              " jour" +
              (Math.abs(daysRemaining) > 1 ? "s" : "") +
              " de retard"
        }.**\n\n`;
    
      // Ajouter à la section QCM si "se termine" dans le champ devoir
      if (devoir.devoir.toLowerCase().includes("se termine") && (daysRemaining >= -2)) {
        qcmSection += devoirMessage;
        continue; // Exclure des autres sections
      }
    
      // Ajouter à la section SAE si "s" dans le champ matière
      if (devoir.matiere.toLowerCase().startsWith("s") && (daysRemaining >= -2)) {
        saeSection += devoirMessage;
        continue; // Exclure des autres sections
      }
    
      // Ajouter à la section principale si sous 7 jours
      if (daysRemaining <= 7 && daysRemaining >= -2) {
        reminderMessages += devoirMessage;
      }
    }
    if ((reminderMessages || qcmSection || saeSection) && process.env.PROCESS_TEST_MODE != '1'){
      if (channel) {
          try {
            // Récupérer les messages récents (jusqu'à 50)
            const fetched = await channel.messages.fetch({ limit: 50 });
            // Supprimer les messages en bloc
            await channel.bulkDelete(fetched);
            logger.logMessage(
              `\x1b[92m[\x1b[92m SUCCESS \x1b[0m] ${fetched.size} messages ont été supprimés dans le salon (${channel.name}) avec succès !\x1b[0m`
            );
          } catch (error) {
            logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la suppression des messages : ${error}`);
          }
      }

    }
    // Envoyer tous les rappels dans un seul message
    if (reminderMessages) {
      await sendReminder(`Rappel de Devoirs (${task.Group})`, reminderMessages, config.appearance.embedDevoirsColor, channel); // Envoi groupé
    }

    if (qcmSection) {
      await sendReminder(`Rappel de QCM (${task.Group})`, qcmSection, config.appearance.embedQCMColor, channel); // Envoi groupé
    }

    if (saeSection) {
      await sendReminder(`Rappel de S.A.E (${task.Group})`, saeSection, config.appearance.embedSAEColor, channel); // Envoi groupé
    }

    // Si interaction est disponible, envoyer la réponse appropriée
    if (interaction) {
      if (!interaction.replied) {
        await interaction.reply(
          devoirs.length
            ? "Rappels de devoir envoyés avec succès !"
            : "Aucun devoir à rappeler."
        );
      } else {
        await interaction.followUp(
          devoirs.length
            ? "Rappels de devoir envoyés avec succès !"
            : "Aucun devoir à rappeler."
        );
      }
    } else {
      // Si interaction n'est pas disponible, ne rien envoyer mais loguer le résultat
      logger.logMessage(
        devoirs.length
          ? "[\x1b[92m SUCCESS \x1b[0m] Tous les rappels de devoir ont été créer avec succès !"
          : "[\x1b[93m NOTICE \x1b[0m] Aucun rappels de devoir à créer."
      );
    }
  } catch (error) {
    logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de la vérification des devoirs à venir : ${error}`);
    if (interaction) {
      await interaction.reply({
        content: "Une erreur s'est produite lors de la vérification des devoirs à venir.",
        flags: 64,
      });
    }
  }
}

async function sendReminder(title, message, color, channel) {
  try {    
    // Vérifier si le canal existe
    if (channel) {
      // Créer un embed avec tous les devoirs
      const embed = createEmbed(title, message, color);

      // Envoi du message avec l'embed
      await channel.send({embeds: [embed]});
      logger.logMessage(`[\x1b[92m SUCCESS \x1b[0m] Rappels de devoir pour ${title} envoyés avec succès !`);
    } else {
      logger.logMessage("   [\x1b[91m ERROR \x1b[0m] Le canal n'a pas pu être trouvé.");
    }

  } catch (error) {
    logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur est survenue lors de l'envoi du rappel de devoirs : ${error}`);
  }
}

async function fetchFromICS(url, group) {
  try {
    // Téléchargement du fichier ICS
    const response = await fetch(url);
    
    // Vérification de la réponse
    if (!response.ok) {
      throw new Error(`Erreur lors du téléchargement du fichier ICS, statut : ${response.status}`);
    }

    // Parsing du fichier ICS
    const parsedData = ical.parseICS(await response.text());
    logger.logMessage(`[\x1b[93m NOTICE \x1b[0m] Recherche de devoirs sur le calendrier pour le groupe ${group} en cours...`);
    
    const devoirManager = new DevoirManager(group);

    // Parcours des événements du fichier ICS
    for (const key in parsedData) {
      const { [key]: event } = parsedData;

      // Vérifier si l'événement est bien un VEVENT et possède les informations nécessaires
      if (
        event.type === "VEVENT" &&
        event.summary &&
        event.end &&
        event.categories
      ) {
        // Nettoyage du titre pour supprimer les mentions "DEADLINE", "WEEk", les heures, etc.
        let cleanedTitle = event.summary.replace(/(DEADLINE.*|WEE?k\s*\d+.*|\d{1,2}(st|nd|rd|th)?\s*\w+\s*\d{2}:\d{2}.*)/gi, "").trim();
        
        // Vérifier si le titre ne contient pas "s'ouvre"
        if (!cleanedTitle.toLowerCase().includes("s'ouvre")) {
          let formattedDate = event.end.toLocaleDateString("fr-FR", { day: "2-digit", month: "2-digit", year: "numeric" }).replace(/\//g, "-");
          let matiere = Array.isArray(event.categories) ? event.categories.join(", ") : event.categories;
            devoirManager.addDevoir({
              devoir: cleanedTitle,
              matiere: matiere,
              date: formattedDate,
              addedBy: "Moodle",
            });
        }        
      }
    }
    logger.logMessage(`[\x1b[92m SUCCESS \x1b[0m] Devoirs récupérer du calendrier pour le groupe ${group} avec succès ! Nombre de devoirs récupérer : ${devoirManager.devoirs.length}`);
  } catch (error) {
    logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la récupération du fichier ICS pour le groupe ${group} : ${error}`);
  }
}

function scheduleTasks() {
  logger.logMessage("[\x1b[93m NOTICE \x1b[0m] Mise en place de la mise à jour automatique en cours...");
  try {
    const now = moment();
    const nextRun = moment().set({
      hour: 6,
      minute: 0,
      second: 0,
      millisecond: 0,
    });

    // Si l'heure actuelle est déjà passée, programme pour demain
    if (now.isAfter(nextRun)) {
      nextRun.add(1, "day");
    }

    // Calculez le délai jusqu'à la prochaine exécution
    const delay = nextRun.diff(now);

    // Logique de planification avec délai
    logger.logMessage(`[\x1b[93m NOTICE \x1b[0m] Prochaine mise à jour dans ${(delay / 1000 / 60 / 60).toFixed(2)} heures...`);

    // Exécute les tâches après le délai calculé
    setTimeout(async () => {
      try {
        logger.logMessage("[\x1b[93m NOTICE \x1b[0m] Exécution des mises à jour périodique...");
        logger.manageLogFiles();
        await DevoirManager.applyToAll('removeExpiredDevoirs');
        await checkAllGroups();
      } catch (intervalError) {
        logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur est survenue lors de la mise à jour périodique : ${intervalError}`);
      }
        setInterval(async () => {
          try {
            logger.logMessage("[\x1b[93m NOTICE \x1b[0m] Exécution des mises à jour périodique...");
            logger.manageLogFiles();
            DevoirManager.applyToAll('removeExpiredDevoirs');
            await checkAllGroups();
          } catch (intervalError) {
            logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur est survenue lors de la mise à jour périodique : ${intervalError}`);
          }
        }, 86400000); // 24 heures en millisecondes
    }, delay);
    logger.logMessage("[\x1b[92m SUCCESS \x1b[0m] Mise en place de la mise à jour effectuée avec succès !\n");
  } catch (error) {
    logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite dans la planification de la mise à jour automatique : ${error}`);
  }
}

function initializeBot() {
  logger.logMessage("[\x1b[93m NOTICE \x1b[0m] Initialisation du bot en cours... \n");
  const rest = new REST({ version: "10" }).setToken(process.env.DISCORD_TOKEN);
  try {
    // Créer les dossiers s'ils n'existent pas
    try {
      logger.logMessage(`[\x1b[93m NOTICE \x1b[0m] Vérification de l'existence des répertoires nécessaires en cours... `);
      [config.directories.logDirectory, config.directories.dataDirectory].forEach((dir) => {
        if (!fs.existsSync(dir)) {
          fs.mkdirSync(dir, { recursive: true });  // Utiliser { recursive: true } pour éviter les erreurs si le répertoire parent existe déjà
          logger.logMessage(`[\x1b[92m SUCCESS \x1b[0m] Répertoire '${dir}' créé avec succès.`);
        } else {
          logger.logMessage(`[\x1b[92m SUCCESS \x1b[0m] Le répertoire '${dir}' existe déjà.`);
        }
      });
    } catch (dirError) {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la création des répertoires : ${dirError}`);
      client.destroy();
      process.exit(1);  // Sortir du programme si erreur dans la création des dossiers
    }

    // Recherche du process.env.DISCORD_TOKEN
    if (!process.env.DISCORD_TOKEN) {
      logger.logMessage("   [\x1b[91m ERROR \x1b[0m] Le token n'est pas défini dans les variables d'environnement.");
      client.destroy();
      process.exit(1);
    }

    // Déploiement des commandes
    try {
      logger.logMessage("[\x1b[93m NOTICE \x1b[0m] Tentative de déploiement des commandes en cours...");
      rest.put(Routes.applicationGuildCommands(config.ids.clientId, config.ids.guildId), { body: JSON.parse(fs.readFileSync('commands.json', 'utf-8')) })
        .then(() => {
          logger.logMessage("[\x1b[92m SUCCESS \x1b[0m] Commandes déployées avec succès !");
        })
        .catch((deployError) => {
          logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Échec du déploiement des commandes : ${deployError}`);
          client.destroy();
          process.exit(1);  // Sortir du programme si déploiement des commandes échoue
        });
    } catch (deployError) {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur inattendue s'est produite lors du déploiement des commandes : ${deployError}`);
      client.destroy();
      process.exit(1);
    }

    // Tentative de connexion
    async function attemptLogin() {
      logger.logMessage("[\x1b[93m NOTICE \x1b[0m] Tentative de connexion en cours...");
      while (true) {
        try {
          await client.login();
          logger.logMessage("[\x1b[92m SUCCESS \x1b[0m] Bot connecté avec succès !\n");
          break;  // Connexion réussie, sortir de la boucle
        } catch (loginError) {
          logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur de connexion, réessai dans 5 secondes... ${loginError} `);
          await new Promise(resolve => setTimeout(resolve, 5000));  // Attendre 5 secondes avant de réessayer
        }
      }
    }

    attemptLogin().catch((loginAttemptError) => {
      logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur est survenue lors de la tentative de connexion : ${loginAttemptError}`);
      client.destroy();
      process.exit(1);  // Sortir si une erreur grave survient pendant la connexion
    });

  } catch (error) {
    logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur inattendue s'est produite : ${error}`);
    client.destroy();
    process.exit(1);  // Sortir du programme si une erreur non gérée se produit
  }
}

process.on('exit', () => {
  try {
    logger.logMessage("[\x1b[93m NOTICE \x1b[0m] Sauvegarde des données en cours ...");
    DevoirManager.applyToAll('saveDevoirs');
    logger.logMessage("[\x1b[92m SUCCESS \x1b[0m] Données sauvegardées avec succès !");
    logger.logMessage(`\x1b[38;5;154mGoodBye World !\x1b[0m`);
  } catch (exitError) {
    logger.logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la sauvegarde des données : ${exitError}`);
  }
});


// Demarrage du bot
initializeBot();