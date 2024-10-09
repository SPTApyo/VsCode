// Importation des modules \u004D\u0061\u0064\u0065\u0020\u0062\u0079\u0020\u005F\u0041\u0050\u0059\u004F
const {
    Client,
    GatewayIntentBits,
    SlashCommandBuilder,
    Routes,
    EmbedBuilder,
    ActionRowBuilder,
    ButtonBuilder,
    ButtonStyle
} = require('discord.js');
const {
    REST
} = require('@discordjs/rest');
const {
    clientId,
    guildId,
    rappelsalonId
} = require('./config.json');

const {
    name,
    author,
    Appversion,
    license
} = require('./package.json');
const moment = require('moment');
const fs = require('fs');

// Configuration des dossiers et fichiers et constantes
const EMBED_COLOR = 0xd830ef;
const PERM_role = "nabil";
const EMBED_FOOTER_TEXT = 'IUTAssistant : \u004D\u0061\u0064\u0065\u0020\u0062\u0079\u0020\u005F\u0041\u0050\u0059\u004F';
const logDirectory = './log';
const dataDirectory = './data';
const logFileName = `${logDirectory}/log_${moment().format('YYYY-MM-DD_HH-mm-ss')}.txt`;

// Recherche du token
const token = process.env.DISCORD_TOKEN;
if (!token) {
    logMessage('\x1b[31m Erreur : le token n\'est pas défini dans les variables d\'environnement.\x1b[0m');
    process.exit(1); // Arrête le bot si le token n'est pas défini
}

// Créer les dossiers s'ils n'existent pas
[logDirectory, dataDirectory].forEach(dir => !fs.existsSync(dir) && fs.mkdirSync(dir));

// Fonction pour logger les messages
function logMessage(message) {
    const timestamp = moment().format('YYYY-MM-DD HH:mm:ss');
    message = ">" + message;
    console.log(timestamp, message);

    // Supprimer les codes de couleur
    const cleanMessage = message.replace(/\x1b\[\d+m/g, '');

    fs.appendFileSync(logFileName, `${timestamp} - ${cleanMessage}\n`);
}

// Gérer les fichiers de log : suppression des 5 derniers si plus de 15
function manageLogFiles() {
    logMessage(`\x1b[33m Vérification des fichiers de log excédentaire en cours ...\x1b[0m`);
    const logFiles = fs.readdirSync(logDirectory)
        .sort((a, b) => fs.statSync(`${logDirectory}/${b}`).mtime - fs.statSync(`${logDirectory}/${a}`).mtime)
        .slice(10); // Ne garder que les 10 plus récents

    logFiles.forEach(file => {
        fs.unlinkSync(`${logDirectory}/${file}`);
        logMessage(`\x1b[32m Suppression du fichier de log excédentaire : ${file}\x1b[0m`);
    });
}

// Initialisation du client du bot
const client = new Client({
    intents: [GatewayIntentBits.Guilds]
});
client.once('ready', () => {
    logMessage('\x1b[32m Le bot est prêt !\x1b[0m');
    // Mise a jour au démarrage
    logMessage('\x1b[33m Mise à jour de démarrage en cours ...\x1b[0m');
    removeExpiredDevoirs();
    manageLogFiles();
    checkForUpcomingDevoirs();
    scheduleDailyTasks();
    logMessage('\x1b[32m Bot déployé avec succès !\x1b[0m');
});

// Définition des commandes
const commands = [
    new SlashCommandBuilder()
    .setName('add')
    .setDescription('Ajoute un devoir')
    .addStringOption(option => option.setName('devoir').setDescription('Le devoir à faire').setRequired(true))
    .addStringOption(option => option.setName('matiere').setDescription('La matière du devoir').setRequired(true))
    .addStringOption(option => option.setName('date').setDescription('La date limite (format: JJ-MM-AAAA)').setRequired(true)),
    new SlashCommandBuilder()
    .setName('modify')
    .setDescription('Modifie un devoir')
    .addIntegerOption(option => option.setName('id').setDescription('L\'ID du devoir à modifier').setRequired(true))
    .addStringOption(option => option.setName('devoir').setDescription('Le nouveau devoir (laisser vide pour ne pas modifier)'))
    .addStringOption(option => option.setName('matiere').setDescription('La nouvelle matière (laisser vide pour ne pas modifier)'))
    .addStringOption(option => option.setName('date').setDescription('La nouvelle date limite (format: JJ-MM-AAAA, laisser vide pour ne pas modifier)')),
    new SlashCommandBuilder().setName('list').setDescription('Affiche la liste des devoirs ajoutés'),
    new SlashCommandBuilder().setName('check').setDescription('Vérifie les devoirs à venir et envoie des rappels'),
    new SlashCommandBuilder().setName('sort').setDescription('Trie du fichier JSON'),
    new SlashCommandBuilder().setName('regenerate').setDescription('Régénère les IDs de chaque devoir'),
    new SlashCommandBuilder().setName('purge').setDescription('Purge tous les devoirs enregistrés dans le fichier JSON'),
    new SlashCommandBuilder().setName('about').setDescription('Affiche les informations du bot')
].map(command => command.toJSON());

// Déploiement des commandes
const rest = new REST({
    version: '10'
}).setToken(token);
(async () => {
    try {
        logMessage('\x1b[33m Déploiement des commandes en cours ...\x1b[0m\x1b[0m');
        await rest.put(Routes.applicationGuildCommands(clientId, guildId), {
            body: commands
        });
        logMessage('\x1b[32m Commandes déployées avec succès !\x1b[0m');
    } catch (error) {
        logMessage(`\x1b[31m Erreur lors du déploiement des commandes : ${error}\x1b[0m`);
    }
})();

// Gestion des devoirs
let devoirs = fs.existsSync('./data/devoirs.json') ? JSON.parse(fs.readFileSync('./data/devoirs.json', 'utf8')) : [];
// Sauvegarde du fichier devoirs
function saveDevoirs() {
    regenerateIds();
    logMessage('\x1b[33m Sauvegarde des données en cours ...\x1b[0m');
    fs.writeFileSync('./data/devoirs.json', JSON.stringify(devoirs, null, 2));
    logMessage('\x1b[32m Sauvegarde des données effectuée avec succès !\x1b[0m');
}

// Retirer les devoirs périmés
function removeExpiredDevoirs() {
    const now = moment().startOf('day');  // On prend le début du jour actuel
    logMessage('\x1b[33m Suppression des devoirs périmés en cours ...\x1b[0m');
    devoirs = devoirs.filter(d => moment(d.date, 'DD-MM-YYYY').isSameOrAfter(now.subtract(1, 'day')));
    logMessage('\x1b[32m Suppression effectuée avec succès !\x1b[0m');
    saveDevoirs();
}

// Trie des devoirs par date dans le JSON
function sortDevoirs(calledBy) {
    logMessage('\x1b[33m Trie des devoirs en cours ...\x1b[0m');
    devoirs.sort((a, b) => moment(a.date, 'DD-MM-YYYY').diff(moment(b.date, 'DD-MM-YYYY')));
    logMessage(`\x1b[32m Devoirs triés avec succès par la fonction : ${calledBy} !\x1b[0m`);
    saveDevoirs();
}

// Fonction pour vérifier les rôles de l'utilisateur
function hasRole(interaction, roleName, name) {
    const member = interaction.member;
    logMessage(`\x1b[32m Vérifications des permissions d\'interaction pour ${name}\x1b[0m`);
    return member.roles.cache.some(role => role.name === roleName);
}

function generateUniqueId() {
    let id = 1; // Commence à 1, ou un autre numéro initial
    while (devoirs.some(devoir => devoir.id === id)) {
        id++; // Incrémente jusqu'à ce qu'un ID unique soit trouvé
    }
    return id;
}

function regenerateIds() {
    devoirs.sort((a, b) => new Date(a.date) - new Date(b.date)); 
    for (let i = 0; i < devoirs.length; i++) {
        devoirs[i].id = i + 1; // Réattribuer les ID en ordre croissant
    }
    
}

// Créer un embed
const createEmbed = (title, description) =>
    new EmbedBuilder().setColor(EMBED_COLOR).setTitle(title).setDescription(description).setFooter({
        text: EMBED_FOOTER_TEXT
    }).setTimestamp();

// Gestion des interactions
client.on('interactionCreate', async interaction => {
    if (!interaction.isCommand()) return;

    const {
        commandName,
        user
    } = interaction;

    if (commandName === 'add') {
        logMessage(`\x1b[34m Interaction : commande "ADD" par ${user.username}\x1b[0m`);
        const devoir = interaction.options.getString('devoir').trim().toUpperCase(); // Convertir en majuscules
        const matiere = interaction.options.getString('matiere').trim().toUpperCase(); // Convertir en majuscules
        const date = interaction.options.getString('date');

        // Vérifier si le devoir existe déjà
        if (devoirs.some(d => d.devoir === devoir && d.matiere === matiere && d.date === date)) {
            logMessage('\x1b[31m Erreur création de devoir : Devoir déjà existant\x1b[0m');
            return await interaction.reply('Ce devoir existe déjà avec la même date et matière.');
        }

        // Vérifier si la date est valide
        if (!moment(date, 'DD-MM-YYYY', true).isValid()) {
            logMessage('\x1b[31m Erreur création de devoir : Date invalide\x1b[0m');
            return await interaction.reply('Date invalide. Utilisez le format JJ-MM-AAAA.');
        }

        // Ajouter le devoir
        devoirs.push({
            id: generateUniqueId(),
            devoir,
            matiere,
            date,
            addedBy: user.username
        });
        sortDevoirs(commandName); // Trie les devoirs après l'ajout


        const embed = createEmbed('Devoir ajouté', `**Devoir :** ${devoir}\n**Matière :** ${matiere}\n**Date limite :** ${date}\n**Ajouté par :** ${user.username}`);
        logMessage('\x1b[32m Devoir ajouté', `**Devoir :** ${devoir}\n**Matière :** ${matiere}\n**Date limite :** ${date}\n**Ajouté par :** ${user.username}\x1b[0m`);
        await interaction.reply({
            embeds: [embed]
        });


    } 
    
    else if (commandName === 'modify') {
        logMessage(`\x1b[34m Interaction : commande "MODIFY" par ${user.username}\x1b[0m`);
        const id = interaction.options.getInteger('id');
        const newDevoir = interaction.options.getString('devoir')?.trim().toUpperCase() || null;
        const newMatiere = interaction.options.getString('matiere')?.trim().toUpperCase() || null;
        const newDate = interaction.options.getString('date') || null;

        const devoirToModify = devoirs.find(d => d.id === id);

        if (!devoirToModify) {
            logMessage(`\x1b[31m Erreur modification de devoir : Devoir non trouvé avec l'ID ${id}\x1b[0m`);
            return await interaction.reply(`Aucun devoir trouvé avec l'ID ${id}.`);
        }

        if (newDevoir) devoirToModify.devoir = newDevoir;
        if (newMatiere) devoirToModify.matiere = newMatiere;
        if (newDate && moment(newDate, 'DD-MM-YYYY', true).isValid()) {
            devoirToModify.date = newDate;
        } else if (newDate) {
            logMessage('\x1b[31m Erreur modification de devoir : Date invalide\x1b[0m');
            return await interaction.reply('Date invalide. Utilisez le format JJ-MM-AAAA.');
        }

        saveDevoirs(); // Enregistrez les modifications
        const embed = createEmbed('Devoir modifié', `**ID :** ${id}\n**Devoir :** ${devoirToModify.devoir}\n**Matière :** ${devoirToModify.matiere}\n**Date limite :** ${devoirToModify.date}`);
        await interaction.reply({
            embeds: [embed]
        });
    } 
    
    else if (commandName === 'list') {
        logMessage(`\x1b[34m Interaction : commande "LIST" par ${user.username}\x1b[0m`);
        sortDevoirs(commandName);
        const devoirList = devoirs.length ?
            devoirs.map(d => `**ID :** ${d.id}   |   **${d.devoir}** en **${d.matiere}**, pour le ${d.date} (ajouté par ${d.addedBy})`).join('\n\n') :
            'Aucun devoir n\'a été ajouté.';

        const embedList = createEmbed('Liste des devoirs', devoirList);
        await interaction.reply({
            embeds: [embedList]
        });
    } 
    
    else if (commandName === 'check') {
        logMessage(`\x1b[34m Interaction : commande "CHECK" par ${user.username}\x1b[0m`);
        removeExpiredDevoirs();
        sortDevoirs(commandName);
        await interaction.reply('Vérification des devoirs à venir en cours ...');
        await checkForUpcomingDevoirs(interaction);
    } 
    
    else if (commandName === 'sort') {
        if (!hasRole(interaction, PERM_role, user.username)) { // Vérifie si l'utilisateur a le rôle Admin
            logMessage(`\x1b[31m Permission refusée pour ${user.username}\x1b[0m`);
            return await interaction.reply({
                content: 'Vous n\'avez pas la permission d\'utiliser cette commande.',
                ephemeral: true
            });
        }
        logMessage(`\x1b[34m Interaction : commande "SORT" par ${user.username}\x1b[0m`);
        sortDevoirs(commandName);
        await interaction.reply('Tri terminé avec succès !');

    } 

    if (commandName === 'regenerate') {
        if (!hasRole(interaction, PERM_role, user.username)) { // Vérifie si l'utilisateur a le rôle Admin
            logMessage(`\x1b[31m Permission refusée pour ${user.username}\x1b[0m`);
            return await interaction.reply({
                content: 'Vous n\'avez pas la permission d\'utiliser cette commande.',
                ephemeral: true
            });
        }
        logMessage(`\x1b[34m Interaction : commande "REGENERATEIDS" par ${user.username}\x1b[0m`);
        logMessage('\x1b[33m Régénération des IDs de devoirs en cours ...\x1b[0m');
        regenerateIds();
        await interaction.reply('Les identifiants de devoirs ont été régénérés avec succès !');
        logMessage('\x1b[32m Les identifiants de devoirs ont été régénérés avec succès !\x1b[0m');
        saveDevoirs();
    }
    
    else if (commandName === 'purge') {
        if (!hasRole(interaction, PERM_role, user.username)) { // Vérifie si l'utilisateur a le rôle Admin
            logMessage(`\x1b[31m Permission refusée pour ${user.username}\x1b[0m`);
            return await interaction.reply({
                content: 'Vous n\'avez pas la permission d\'utiliser cette commande.',
                ephemeral: true
            });
        }
        logMessage(`\x1b[34m Interaction : commande "PURGE" par ${user.username}\x1b[0m`);
        const row = new ActionRowBuilder()
            .addComponents(
                new ButtonBuilder().setCustomId('confirm_purge').setLabel('Confirmer').setStyle(ButtonStyle.Danger),
                new ButtonBuilder().setCustomId('cancel_purge').setLabel('Annuler').setStyle(ButtonStyle.Secondary)
            );

        await interaction.reply({
            content: 'Es-tu sûr de vouloir purger tous les devoirs ?',
            components: [row]
        });

        const filter = i => i.user.id === user.id;
        const collector = interaction.channel.createMessageComponentCollector({
            filter,
            time: 15000
        });

        collector.on('collect', async i => {
            if (i.customId === 'confirm_purge') {
                devoirs = [];
                await i.update({
                    content: 'Tous les devoirs ont été supprimés.',
                    components: []
                });
                logMessage('\x1b[32m Purge effectuée avec succès !\x1b[0m');
                saveDevoirs();
            } else {
                await i.update({
                    content: 'Purge annulée.',
                    components: []
                });
                logMessage('\x1b[32m Purge annulée\x1b[0m');
            }
        });

        collector.on('end', collected => {
            if (!collected.size) interaction.editReply({
                content: 'Temps écoulé, aucune action effectuée.',
                components: []
            });
        });
    } 
    
    else if (commandName === 'about') {
        logMessage(`\x1b[34m Interaction : commande "ABOUT" par ${user.username}\x1b[0m`);
        await interaction.reply(`Name : ${name}\nAuthor : ${author}\nVersion : ${Appversion}\nLicense : ${license}`);
    }
});

// Vérification des devoirs à venir
async function checkForUpcomingDevoirs(interaction) {
    const channel = await client.channels.fetch(rappelsalonId);
    const now = moment();  // Garder now intact
    logMessage('\x1b[33m Vérification des devoirs à venir en cours ...\x1b[0m');

    // Filtrer les devoirs en fonction de leur date
    const reminders = devoirs.filter(d => {
        // Ajouter un jour à la date du devoir pour l'inclure jusqu'à la fin du jour
        const dueDate = moment(d.date, 'DD-MM-YYYY').endOf('day');
        return [7, 6, 5, 4, 3, 2, 1, 0, -1].includes(dueDate.diff(now.clone().startOf('day'), 'days'));
    });

    if (channel) {
        if (reminders.length > 0) {
            try {
                // Récupérer les messages récents (jusqu'à 50)
                const fetched = await channel.messages.fetch({ limit: 50 });
                // Supprimer les messages en bloc
                await channel.bulkDelete(fetched);
                logMessage(`\x1b[32m ${fetched.size} messages ont été supprimés dans le salon (${channel.name}) avec succès !\x1b[0m`);
            } catch (error) {
                logMessage('\x1b[31m Erreur lors de la suppression des messages : \x1b[0m', error);
            }
        }
    }

    // Envoyer des rappels groupés dans un seul embed
    let reminderMessages = ""; // Variable pour stocker tous les rappels

    for (const devoir of reminders) {
        // Calculer les jours restants à partir de la date actuelle
        const daysRemaining = moment(devoir.date, 'DD-MM-YYYY').startOf('day').diff(now.clone().startOf('day'), 'days');
    
        // Ajouter chaque rappel dans la variable reminderMessages
        reminderMessages += `**Devoir :** ${devoir.devoir}\n**Matière :** ${devoir.matiere}\n**Date limite :** ${devoir.date}\n` +
        `**Temps restant : ${
            daysRemaining === 0 
                ? 'aujourd\'hui' 
                    : daysRemaining > 0 
                        ? daysRemaining + ' jour' + (daysRemaining > 1 ? 's' : '') 
                        : Math.abs(daysRemaining) + ' jour' + (Math.abs(daysRemaining) > 1 ? 's' : '') + ' de retard'
        }.**\n\n`;
    }

    // Envoyer tous les rappels dans un seul message
    if (reminderMessages) {
        await sendReminder(reminderMessages); // Envoi groupé
    }

    // Si interaction est disponible, envoyer la réponse appropriée
    if (interaction) {
        if (!interaction.replied) {
            await interaction.reply(reminders.length ? 'Rappels de devoir envoyés avec succès !' : 'Aucun devoir à rappeler.');
            logMessage(reminders.length ? '\x1b[32m Rappels de devoir envoyés avec succès !\x1b[0m' : '\x1b[32m Aucun devoir à rappeler.\x1b[0m');
        } else {
            await interaction.followUp(reminders.length ? 'Rappels de devoir envoyés avec succès !' : 'Aucun devoir à rappeler.');
            logMessage(reminders.length ? '\x1b[32m Rappels de devoir envoyés avec succès !\x1b[0m' : '\x1b[32m Aucun devoir à rappeler.\x1b[0m');
        }
    } else {
        // Si interaction n'est pas disponible, ne rien envoyer mais loguer le résultat
        logMessage(reminders.length ? '\x1b[32m Rappels automatiques envoyés avec succès !\x1b[0m' : '\x1b[32m Aucun rappel automatique à envoyer\x1b[0m');
    }
}


// Envoi des rappels de devoirs groupés
async function sendReminder(message) {
    const channel = await client.channels.fetch(rappelsalonId);
    if (channel) {
        // Créer un embed avec tous les devoirs
        const embed = createEmbed('Rappel de Devoirs', message);
        logMessage('\x1b[32m Rappels groupés de devoirs envoyés avec succès !\x1b[0m');
        await channel.send({
            content: "@everyone Nouveau rappel de devoir !",
            embeds: [embed]
        });
    }
}

// Lancement autonome des mises a jours
function scheduleDailyTasks() {
    logMessage('\x1b[33m Mise à jour automatique en cours...\x1b[0m');
    const now = moment();
    const nextRun = moment().set({
        hour: 6,
        minute: 0,
        second: 0,
        millisecond: 0
    });

    // Si l'heure actuelle est déjà passée, programme pour demain
    if (now.isAfter(nextRun)) {
        nextRun.add(1, 'day');
    }

    // Calculez le délai jusqu'à la prochaine exécution
    const delay = nextRun.diff(now);

    // Exécutez les fonctions une fois
    setTimeout(async () => {
        // Appel des fonctions de mise à jour
        removeExpiredDevoirs();
        checkForUpcomingDevoirs();
        manageLogFiles();

        // Mettre à jour le message avec la dernière date/heure

        // Répéter toutes les 24 heures
        setInterval(async () => {
            removeExpiredDevoirs();
            checkForUpcomingDevoirs();
            manageLogFiles();
        }, 86400000); // 24 heures en millisecondes
    }, delay);
    logMessage('\x1b[32m Mise à jour effectuée avec succès !\x1b[0m');
}


// Lancer le bot
async function loginWithRetry() {
    while (true) {
        try {
            await client.login(token);
            logMessage('\x1b[32m Bot connecté avec succès !\n\n\x1b[0m');
            break;
        } catch (error) {
            logMessage(`\x1b[31m Erreur de connexion, réessai dans 5 secondes... ${error} \x1b[0m`);
            await new Promise(resolve => setTimeout(resolve, 5000));
        }
    }
}

// Lancer la tentative de connexion
loginWithRetry();