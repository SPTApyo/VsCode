// Importation des modules
const { Client, GatewayIntentBits, SlashCommandBuilder, Routes, EmbedBuilder } = require('discord.js');
const { REST } = require('@discordjs/rest');
const { token, clientId, guildId, salonId } = require('./config.json');
const moment = require('moment');
const fs = require('fs');

// Configuration des embeds
const EMBED_COLOR = 0x0099FF;
const EMBED_FOOTER_TEXT = 'IUTAssistant';
const logDirectory = './log';
const logFileName = `${logDirectory}/log_${moment().format('YYYY-MM-DD_HH-mm-ss')}.txt`;

// Créer le dossier log s'il n'existe pas
if (!fs.existsSync(logDirectory)) fs.mkdirSync(logDirectory);

// Fonction pour logger les messages
function logMessage(message) {
    console.log(message);
    fs.appendFileSync(logFileName, `${moment().format('YYYY-MM-DD HH:mm:ss')} - ${message}\n`);
}

// Initialisation du client du bot
const client = new Client({ intents: [GatewayIntentBits.Guilds] });

client.once('ready', () => logMessage('\x1b[32m> Le bot est prêt ! \x1b[0m'));

// Définition des commandes
const commands = [
    new SlashCommandBuilder()
        .setName('add')
        .setDescription('Ajoute un devoir')
        .addStringOption(option => option.setName('devoir').setDescription('Le devoir à faire').setRequired(true))
        .addStringOption(option => option.setName('matiere').setDescription('La matière du devoir').setRequired(true))
        .addStringOption(option => option.setName('date').setDescription('La date limite (format: JJ-MM-AAAA)').setRequired(true)),
    new SlashCommandBuilder().setName('list').setDescription('Affiche la liste des devoirs ajoutés'),
    new SlashCommandBuilder().setName('check').setDescription('Vérifie les devoirs à venir et envoie des rappels'),
    new SlashCommandBuilder().setName('sort').setDescription('Trie du fichier Json')
].map(command => command.toJSON());

// Déploiement des commandes
const rest = new REST({ version: '10' }).setToken(token);
(async () => {
    try {
        logMessage('\x1b[33m> Déploiement des commandes...\x1b[0m');
        await rest.put(Routes.applicationGuildCommands(clientId, guildId), { body: commands });
        logMessage('\x1b[32m> Commandes déployées avec succès!\x1b[0m');
    } catch (error) {
        console.error(error);
        logMessage(`Erreur lors du déploiement des commandes: ${error}`);
    }
})();

// Chargement et sauvegarde des devoirs
let devoirs = [];
const filePath = './devoirs.json';

if (fs.existsSync(filePath)) {
    devoirs = JSON.parse(fs.readFileSync(filePath, 'utf8'));
}

function saveDevoirs() {
    fs.writeFileSync(filePath, JSON.stringify(devoirs, null, 2));
}

function removeExpiredDevoirs() {
    devoirs = devoirs.filter(devoir => moment(devoir.date, 'D-M-YYYY').isAfter(moment()));
    saveDevoirs();
}

// Vérifier les devoirs périmés
removeExpiredDevoirs();
setInterval(removeExpiredDevoirs, 3600000); // 1 heure

// Fonction pour créer un embed
function createEmbed(title, description) {
    return new EmbedBuilder()
        .setColor(EMBED_COLOR)
        .setTitle(title)
        .setDescription(description)
        .setFooter({ text: EMBED_FOOTER_TEXT })
        .setTimestamp();
}

// Gestion des interactions
client.on('interactionCreate', async interaction => {
    if (!interaction.isCommand()) return;

    const { commandName } = interaction;

    if (commandName === 'add') {
        const devoir = interaction.options.getString('devoir').trim();
        const matiere = interaction.options.getString('matiere');
        const date = interaction.options.getString('date');

        const devoirRegex = /^[\p{Ll}\p{Lm}\p{Lo}\p{N}_-]+$/u;
        if (!devoirRegex.test(devoir)) {
            return await interaction.reply('Le devoir contient des caractères non autorisés.');
        }

        const dateRegex = /^(?:[0-2]?\d|3[01])-(?:0?[1-9]|1[0-2])-\d{4}$/;
        if (!dateRegex.test(date) || !moment(date, 'D-M-YYYY', true).isValid()) {
            return await interaction.reply('La date fournie est invalide. Veuillez utiliser le format JJ-MM-AAAA.');
        }

        devoirs.push({ devoir, matiere, date, addedBy: interaction.user.username });
        saveDevoirs();

        const embed = createEmbed('Devoir ajouté', `**Devoir :** ${devoir}\n**Matière :** ${matiere || 'Non spécifiée'}\n**Date limite :** ${date}\n**Ajouté par :** ${interaction.user.username}`);
        logMessage(`Interaction enregistrée par ${interaction.user.username} > Devoir ajouté avec succès ! (${devoir}, ${matiere}, pour le ${date})`);
        await interaction.reply({ embeds: [embed] });
    } else if (commandName === 'list') {
        const devoirList = devoirs.length === 0 ? 'Aucun devoir n\'a été ajouté.' :
            devoirs.map(d => `${d.devoir} ${d.matiere ? `en ${d.matiere}` : ''}, pour le ${d.date} (ajouté par ${d.addedBy})`).join('\n\n');

        const embedList = createEmbed('Liste des devoirs', devoirList);
        await interaction.reply({ embeds: [embedList] });
    } else if (commandName === 'check') {
        await interaction.reply('Vérification des devoirs à venir...');
        await checkForUpcomingDevoirs(interaction);
    }
});

// Vérification des devoirs à venir
async function checkForUpcomingDevoirs(interaction) {
    const now = moment();
    let remindersSent = false;

    for (const devoir of devoirs) {
        const devoirDate = moment(devoir.date, 'D-M-YYYY');
        const daysUntilDue = devoirDate.diff(now, 'days');

        if ([7, 3, 2, 1, 0].includes(daysUntilDue)) {
            await sendReminder(devoir, `Rappel : Date limite du devoir dans ${daysUntilDue === 0 ? 'aujourd\'hui' : daysUntilDue + ' jour' + (daysUntilDue > 1 ? 's' : '')} !`);
            remindersSent = true;
        }
    }

    removeExpiredDevoirs();

    if (interaction) {
        await interaction.followUp(remindersSent ? 'Vérification terminée. Vérifiez le salon pour les rappels !' : 'Aucun devoir à rappeler pour le moment.');
    }
}

// Fonction pour envoyer un rappel
async function sendReminder(devoir, message) {
    const channel = await client.channels.fetch(salonId);
    if (channel) {
        const embed = createEmbed('Rappel de Devoir', `${message}\n**Devoir :** ${devoir.devoir}\n**Matière :** ${devoir.matiere || 'Non spécifiée'}\n**Date limite :** ${devoir.date}\n**Ajouté par :** ${devoir.addedBy}`);
        await channel.send({ embeds: [embed] });
    }
}

// Vérifier les devoirs chaque jour
setInterval(checkForUpcomingDevoirs, 86400000); // 1 jour
checkForUpcomingDevoirs();

// Lancer le bot
client.login(token);