// Importation des modules \u004D\u0061\u0064\u0065\u0020\u0062\u0079\u0020\u005F\u0041\u0050\u0059\u004F
const {Client,GatewayIntentBits,Routes,EmbedBuilder,ActionRowBuilder,ButtonBuilder,ButtonStyle} = require("discord.js");
const { REST } = require("@discordjs/rest");
const client = new Client({
    intents: [GatewayIntentBits.Guilds,GatewayIntentBits.GuildMessages,GatewayIntentBits.MessageContent,],});

const app = require("./package.json");
const config = require('./config');
const moment = require("moment");
const stringSimilarity = require('string-similarity');
const fs = require("fs");
const ical = require("ical");

/**
 * Charge ou initialise un tableau de devoirs à partir d'un fichier JSON.
 * 
 * Cette ligne de code vérifie si un fichier `devoirs.json` existe dans le répertoire `./data`. 
 * Si le fichier existe, il est lu et son contenu est parsé en un objet JavaScript (un tableau de devoirs). 
 * Si le fichier n'existe pas, un tableau vide est initialisé à la place. 
 * Ce tableau est ensuite assigné à la variable `devoirs`.
 *
 * ### Étapes :
 * 1. **Vérification de l'existence du fichier** :
 *    - La fonction `fs.existsSync()` est utilisée pour vérifier si le fichier `./data/devoirs.json` existe dans le système de fichiers.
 *
 * 2. **Lecture du fichier et parsing** :
 *    - Si le fichier existe, la fonction `fs.readFileSync()` est utilisée pour lire son contenu. Ce contenu est ensuite parsé en JSON à l'aide de `JSON.parse()`.
 *    - Le contenu du fichier est supposé être un tableau d'objets représentant des devoirs.
 *
 * 3. **Initialisation d'un tableau vide** :
 *    - Si le fichier n'existe pas, un tableau vide est initialisé pour éviter toute erreur lors de l'utilisation de la variable `devoirs`.
 *
 * ### Exemple :
 * Si le fichier `devoirs.json` existe et contient un tableau JSON comme :
 * ```json
 * [
 *   { "devoir": "Maths - Exercice 5", "date": "2024-12-30" },
 *   { "devoir": "Anglais - Lecture", "date": "2024-12-28" }
 * ]
 * ```
 * Le tableau `devoirs` sera assigné à cet objet JSON parsé :
 * ```javascript
 * [
 *   { "devoir": "Maths - Exercice 5", "date": "2024-12-30" },
 *   { "devoir": "Anglais - Lecture", "date": "2024-12-28" }
 * ]
 * ```
 * 
 * ### Paramètres :
 * - Aucun paramètre n'est directement passé à cette ligne de code. Elle se base uniquement sur la vérification du fichier `devoirs.json`.
 *
 * ### Retour :
 * - Si le fichier existe et contient des données JSON valides, `devoirs` sera un tableau représentant ces données.
 * - Si le fichier n'existe pas ou est vide, `devoirs` sera un tableau vide.
 *
 * ### Erreurs :
 * - Si le fichier `devoirs.json` existe mais contient des données JSON invalides, un erreur de syntaxe sera lancée lors du parsing, et le tableau `devoirs` ne sera pas initialisé correctement.
 */
let devoirs = fs.existsSync("./data/devoirs.json")
  ? JSON.parse(fs.readFileSync("./data/devoirs.json", "utf8"))
  : [];

let logStream = fs.createWriteStream(
    `${config.directories.logDirectory}/log_${moment().format("YYYY-MM-DD_HH-mm-ss")}.txt`,
    { flags: "a" }
  );
/**
 * Enregistre et affiche un message de log avec un horodatage dans la console et dans un fichier de log.
 *
 * Cette fonction permet d'enregistrer des messages dans la console, en ajoutant un horodatage au format `YYYY/MM/DD HH:mm:ss` 
 * et en supprimant les codes de couleur avant de les enregistrer dans un fichier de log.
 * Le fichier de log est créé dans le répertoire spécifié par la configuration, avec un nom basé sur l'heure actuelle.
 *
 * ### Étapes :
 * 1. **Ajout de l'horodatage** :
 *    - Un horodatage au format `YYYY/MM/DD HH:mm:ss` est ajouté au message pour indiquer le moment précis de l'enregistrement.
 *    - Le message est préfixé avec un `- >` pour le distinguer clairement des autres messages.
 *
 * 2. **Affichage dans la console** :
 *    - Le message est directement affiché dans la console, incluant l'horodatage et le contenu.
 *
 * 3. **Nettoyage des codes de couleur** :
 *    - Tous les codes de couleur ANSI (par exemple, `\x1b[91m`) sont supprimés du message avant de l'enregistrer dans le fichier de log.
 *
 * 4. **Ajout dans le fichier de log** :
 *    - Le message nettoyé est ajouté à un fichier de log dont le nom est basé sur la date et l'heure actuelle. 
 *    - Le fichier de log est stocké dans le répertoire `logDirectory` défini dans la configuration. Un fichier est créé pour chaque minute, avec un nom formaté comme `log_YYYY-MM-DD_HH-mm-ss.txt`.
 *
 * 5. **Gestion des erreurs** :
 *    - En cas d'erreur lors de l'écriture dans le fichier de log, un message d'erreur est affiché dans la console.
 *
 * ### Exemple :
 * ```javascript
 * logMessage("[ INFO ] L'application a démarré avec succès");
 * logMessage("[ ERREUR ] Une erreur s'est produite dans la connexion à la base de données");
 * ```
 *
 * ### Paramètres :
 * - `message` (string) : Le message à afficher et à enregistrer dans les logs. Il peut contenir des codes de couleur ANSI pour formater l'affichage dans la console.
 *
 * ### Retour :
 * Aucune valeur retournée. La fonction sert uniquement à afficher et enregistrer le message de log.
 *
 * ### Erreurs :
 * Si une erreur se produit lors de l'écriture dans le fichier de log (par exemple, si le fichier est inaccessible), un message d'erreur est affiché dans la console.
 */
function logMessage(message) {
  try {
    if(message == ""){
      console.log("");
      logStream.write("\n");
    }else {
    const timestamp = moment().format("YYYY/MM/DD HH:mm:ss");
    message = `${timestamp} - > ${message}`;  // Préfixe ">" pour distinguer facilement les messages de log

    // Affichage dans la console
    if (message.includes("ERREUR")) {
      console.error(`\x1b[0m${message}`);  // Utilise console.error si le message contient "ERREUR"
    } else if (message.includes("INFO")) {
      console.warn(`\x1b[0m${message}`);  // Utilise console.warn si le message contient "INFO"
    } else {
      console.log(`${message}`);  // Sinon, utilise console.log
    }

    // Supprimer les codes de couleur avant de les écrire dans le fichier de log
    const cleanMessage = message.replace(/\x1b\[\d+m/g, "");

    // Ajout dans le fichier de log
    logStream.write(`${cleanMessage}\n`);
  }
  } catch (error) {
    console.error(`   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de l'écriture dans les logs : ${error}`);
  }
}
/**
 * Gère les fichiers de log en supprimant les fichiers excédentaires.
 *
 * Cette fonction vérifie et supprime les fichiers de log les plus anciens dans le répertoire 
 * de logs spécifié par `config.directories.logDirectory`, en ne conservant que les 10 fichiers 
 * de log les plus récents. Cela permet de limiter la quantité de fichiers de log en stockage.
 *
 * ### Étapes :
 * 1. **Liste des fichiers de log** :
 *    - La fonction utilise `fs.readdirSync()` pour obtenir une liste des fichiers dans le répertoire de logs.
 *    - Les fichiers sont triés par date de modification (du plus récent au plus ancien) grâce à `fs.statSync()` et une fonction de tri personnalisée.
 *
 * 2. **Filtrage des fichiers excédentaires** :
 *    - La méthode `slice(10)` est utilisée pour ne conserver que les 10 fichiers les plus récents. Tous les autres fichiers sont considérés comme excédentaires.
 *
 * 3. **Suppression des fichiers excédentaires** :
 *    - Pour chaque fichier excédentaire, la fonction `fs.unlinkSync()` est utilisée pour supprimer le fichier.
 *    - Des messages de log sont générés pour indiquer le succès ou l'échec de la suppression de chaque fichier.
 *
 * 4. **Gestion des erreurs** :
 *    - Si une erreur survient lors de la suppression d'un fichier ou de la gestion des fichiers de log en général, un message d'erreur est enregistré.
 *
 * ### Exemple :
 * Si le répertoire de logs contient les fichiers suivants :
 * ```
 * log_2024-12-20_10-20-30.txt
 * log_2024-12-19_14-10-25.txt
 * log_2024-12-18_13-05-20.txt
 * ```
 * Après l'exécution de la fonction, seuls les 10 fichiers les plus récents seront conservés et les autres seront supprimés.

 * ### Paramètres :
 * Aucun paramètre n'est directement passé à cette fonction. Elle utilise `config.directories.logDirectory` pour accéder au répertoire de logs.
 *
 * ### Retour :
 * La fonction n'a pas de valeur de retour. Elle effectue des actions sur les fichiers de log, principalement la suppression de fichiers excédentaires.
 *
 * ### Erreurs :
 * - Si une erreur survient lors de la suppression d'un fichier, un message d'erreur est enregistré, mais la fonction continue de supprimer les fichiers restants.
 * - Si une erreur se produit lors de la gestion des fichiers (lecture ou tri des fichiers), un message d'erreur est également enregistré.
 */
function manageLogFiles() {
  logMessage(
    "[\x1b[93m NOTICE \x1b[0m] Vérification des fichiers de log excédentaires en cours..."
  );

  try {
    const files = fs
      .readdirSync(config.directories.logDirectory)
      .sort(
        (a, b) =>
          fs.statSync(`${config.directories.logDirectory}/${b}`).mtime -
          fs.statSync(`${config.directories.logDirectory}/${a}`).mtime
      )
      .slice(10); // Ne garder que les 10 plus récents

    files.forEach((file) => {
      try {
        fs.unlinkSync(`${config.directories.logDirectory}/${file}`);
        logMessage(
          `[\x1b[92m SUCCESS \x1b[0m] Suppression du fichier de log excédentaire : ${file}`
        );
      } catch (unlinkError) {
        logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Impossible de supprimer le fichier ${file} : ${unlinkerror}`
        );
      }
    });
  } catch (error) {
    logMessage(
      `   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de la gestion des fichiers de log : ${error}`
    );
  }
}

/**
 * Crée un embed Discord structuré avec un titre, une description, un pied de page et un timestamp.
 *
 * Cette fonction génère un embed personnalisé pour être envoyé dans un canal Discord. Elle utilise les 
 * configurations définies dans le fichier de configuration (`config.appearance`) pour personnaliser 
 * l'apparence de l'embed, y compris la couleur, le texte du pied de page et l'horodatage.
 *
 * ### Étapes :
 * 1. **Création de l'embed** :
 *    - La fonction utilise la classe `EmbedBuilder` de Discord pour créer un objet embed.
 *    - Le titre et la description sont définis à partir des paramètres de la fonction.
 *  
 * 2. **Personnalisation de l'embed** :
 *    - La couleur de l'embed est définie en convertissant `config.appearance.embedColor` en un entier à 
 *      partir de sa valeur hexadécimale.
 *    - Le texte du pied de page est récupéré depuis `config.appearance.embedFooterText`.
 *    - Le timestamp (horodatage) est ajouté automatiquement à l'embed pour indiquer la date et l'heure de 
 *      la création du message.
 *
 * 3. **Retour de l'embed** :
 *    - La fonction retourne un objet `EmbedBuilder` qui peut ensuite être envoyé dans un canal Discord.
 *
 * ### Exemple :
 * Si vous appelez la fonction avec les paramètres suivants :
 * ```javascript
 * createEmbed("Devoir à rendre", "Soumettre le rapport avant le 30 décembre 2024.");
 * ```
 * L'embed généré contiendra :
 * - **Titre** : "Devoir à rendre"
 * - **Description** : "Soumettre le rapport avant le 30 décembre 2024."
 * - **Couleur** : Définie par `config.appearance.embedColor`
 * - **Pied de page** : Texte provenant de `config.appearance.embedFooterText`
 * - **Timestamp** : Date et heure actuelles
 *
 * ### Paramètres :
 * - **`title`** (String) : Le titre de l'embed. Il s'agit du texte principal affiché en haut de l'embed.
 * - **`description`** (String) : La description de l'embed. Ce texte apparaîtra sous le titre.
 *
 * ### Retour :
 * La fonction retourne un objet `EmbedBuilder` personnalisé, prêt à être utilisé pour l'envoi dans un canal Discord.
 *
 * ### Erreurs :
 * Aucune erreur spécifique n'est levée dans cette fonction. En cas d'erreur dans la création de l'embed, 
 * Discord gérera le problème en renvoyant une erreur lors de l'envoi du message.
 */
const createEmbed = (title, description, color) =>
  new EmbedBuilder()
    .setColor(parseInt(color, 16))
    .setTitle(title)
    .setDescription(description)
    .setFooter({
      text: config.appearance.embedFooterText,
    })
    .setTimestamp();

/**
 * Sauvegarde les données des devoirs dans un fichier JSON en supprimant les doublons.
 *
 * Cette fonction permet de sauvegarder les informations des devoirs dans un fichier JSON 
 * après avoir supprimé les doublons (en fonction du titre et de la matière), et après avoir régénéré 
 * les identifiants des devoirs. Les données sont ensuite sauvegardées dans le fichier `./data/devoirs.json`.
 *
 * ### Étapes :
 * 1. **Suppression des doublons** :
 *    - La fonction utilise la méthode `removeDuplicates()` pour filtrer les doublons dans le tableau `devoirs`.
 *    - Un devoir est considéré comme un doublon si un autre devoir avec le même titre et la même matière existe déjà.
 *  
 * 2. **Régénération des identifiants** :
 *    - Après la suppression des doublons, la fonction `regenerateIds()` est appelée pour régénérer les identifiants uniques pour chaque devoir.
 *
 * 3. **Sauvegarde des données** :
 *    - Les devoirs sont sauvegardés dans un fichier JSON situé à `./data/devoirs.json` en utilisant la méthode `fs.writeFileSync()`.
 *    - Le fichier est formaté avec une indentation de 2 espaces pour une lecture facile.
 *
 * 4. **Gestion des erreurs** :
 *    - Si une erreur survient lors de la suppression des doublons, un message d'erreur est enregistré.
 *    - Si une erreur survient lors de la sauvegarde des données dans le fichier JSON, un message d'erreur est également enregistré.
 *
 * ### Exemple :
 * Avant l'exécution de cette fonction, le tableau `devoirs` peut contenir des doublons comme :
 * ```json
 * [
 *   { "titre": "Maths - Exercice 1", "matiere": "Maths" },
 *   { "titre": "Maths - Exercice 1", "matiere": "Maths" },
 *   { "titre": "Physique - Exercice 2", "matiere": "Physique" }
 * ]
 * ```
 * Après l'exécution de `saveDevoirs()`, les doublons seront supprimés et le tableau final sera sauvegardé dans `devoirs.json`.
 *
 * ### Paramètres :
 * Aucun paramètre n'est directement passé à cette fonction. Elle travaille sur le tableau global `devoirs`.
 *
 * ### Retour :
 * La fonction n'a pas de valeur de retour. Elle effectue une action de sauvegarde sur le fichier JSON.
 *
 * ### Erreurs :
 * - Si une erreur survient lors de la suppression des doublons, un message d'erreur sera affiché dans la console.
 * - Si une erreur survient lors de la sauvegarde des données dans le fichier JSON, un message d'erreur sera également affiché dans la console.
 */
function UpdateJsonData() {
  try {
    function removeDuplicates(arr) {
      try {
        logMessage("[\x1b[93m NOTICE \x1b[0m] Suppression des doublons en cours...");
        // Assurez-vous que l'entrée est un tableau
        if (!Array.isArray(arr)) {
          throw new Error("L'entrée n'est pas un tableau.");
        }
    
        const seen = new Set();
        const filteredArr = arr.filter(devoir => {
          if (!devoir.devoir || !devoir.matiere) {
            // Vérifie que les propriétés 'titre' et 'matiere' existent dans l'objet
            throw new Error(`Un devoir (${devoir.id}) manque des propriétés nécessaires (titre ou matiere). `);
          }
    
          const uniqueKey = `${devoir.devoir}-${devoir.matiere}`;
          if (seen.has(uniqueKey)) {
            // Si c'est un doublon, notifie et ne l'ajoute pas
            logMessage(`[\x1b[92m SUCCESS \x1b[0m] Devoir supprimé (doublon) : ${devoir.devoir} - ${devoir.matiere}`);
            return false; // Le devoir est un doublon
          }
          seen.add(uniqueKey); // Ajoute la clé unique au Set
          return true; // Le devoir est unique, il est gardé
        });
        logMessage("[\x1b[92m SUCCESS \x1b[0m] Doublons supprimer avec succès !");
        // Retourne le tableau filtré sans doublons
        return filteredArr;
      } catch (error) {
        logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de la suppression des doublons : ${error}`);
        return arr; // Retourner l'original en cas d'erreur
      }
    }
    

    // Supprimer les doublons avant la sauvegarde
    devoirs = removeDuplicates(devoirs);
    regenerateIds();

    logMessage("[\x1b[93m NOTICE \x1b[0m] Sauvegarde des données en cours...");

    // Sauvegarder les données dans un fichier JSON
    try {
      fs.writeFileSync("./data/devoirs.json", JSON.stringify(devoirs, null, 2));
      logMessage("[\x1b[92m SUCCESS \x1b[0m] Sauvegarde des données effectuée avec succès !");
    } catch (error) {
      logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de la sauvegarde des données : ${error}`);
    }

  } catch (error) {
    logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de l'exécution de saveDevoirs : ${error}`);
  }
}

/**
 * Supprime les devoirs périmés de la liste.
 *
 * Cette fonction filtre les devoirs enregistrés dans le tableau `devoirs` pour ne conserver que ceux dont la date limite 
 * n'est pas dépassée par rapport à la date actuelle. Elle supprime tous les devoirs dont la date limite est antérieure 
 * à la journée d'hier.
 *
 * ### Étapes :
 * 1. **Filtrage des devoirs périmés** :
 *    - La fonction commence par obtenir la date actuelle avec `moment().startOf("day")`, ce qui fixe l'heure à 00:00:00 du jour actuel.
 *    - Les devoirs sont ensuite filtrés en comparant leur date limite (`d.date`) avec la date actuelle. Si un devoir a une date limite inférieure à aujourd'hui (moins un jour), il est considéré comme périmé et sera supprimé.
 *    - Si la date d'un devoir est invalide, un message d'erreur est généré et la suppression est interrompue.
 *
 * 2. **Sauvegarde des devoirs mis à jour** :
 *    - Une fois les devoirs périmés supprimés, la fonction `saveDevoirs()` est appelée pour sauvegarder la liste mise à jour des devoirs dans le fichier JSON.
 *
 * 3. **Gestion des erreurs** :
 *    - Si une erreur survient lors du filtrage des devoirs ou de la manipulation des dates, un message d'erreur est enregistré dans les logs.
 *    - Si une date invalide est détectée pour un devoir, une erreur est lancée avec le message correspondant.
 *
 * ### Exemple :
 * Avant l'exécution de cette fonction, le tableau `devoirs` peut contenir des devoirs avec des dates limites passées, par exemple :
 * ```json
 * [
 *   { "titre": "Maths - Exercice 1", "matiere": "Maths", "date": "20-12-2024" },
 *   { "titre": "Physique - Exercice 2", "matiere": "Physique", "date": "19-12-2024" }
 * ]
 * ```
 * Si la date actuelle est le 21 décembre 2024, après l'exécution de la fonction, les devoirs ayant des dates antérieures au 20 décembre seront supprimés.
 *
 * ### Paramètres :
 * Aucun paramètre n'est directement passé à cette fonction. Elle travaille sur le tableau global `devoirs`.
 *
 * ### Retour :
 * La fonction n'a pas de valeur de retour. Elle modifie le tableau `devoirs` en supprimant les devoirs périmés et sauvegarde les données mises à jour.
 *
 * ### Erreurs :
 * - Si une date invalide est rencontrée, un message d'erreur est généré et l'exécution de la fonction est arrêtée.
 * - Si une erreur survient lors de la suppression des devoirs périmés ou de la sauvegarde, un message d'erreur est enregistré.
 */
function removeExpiredDevoirs() {
  try {
    const now = moment().startOf("day"); // On prend le début du jour actuel
    logMessage("[\x1b[93m NOTICE \x1b[0m] Suppression des devoirs périmés en cours...");

    // Filtrer les devoirs qui ne sont pas périmés
    devoirs = devoirs.filter((d) => {
      const dueDate = moment(d.date, "DD-MM-YYYY");
      if (!dueDate.isValid()) {
        throw new Error(`Date invalide détectée : ${d.date}`);
      }
      return dueDate.isSameOrAfter(now.subtract(1, "day"));
    });

    logMessage("[\x1b[92m SUCCESS \x1b[0m] Suppression effectuée avec succès !");

  } catch (error) {
    logMessage(
      `   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de la suppression des devoirs périmés : ${error}`
    );
  }
}

/**
 * Trie la liste des devoirs par date.
 *
 * Cette fonction trie le tableau `devoirs` en fonction de la date limite de chaque devoir, du plus ancien au plus récent.
 * Elle utilise la bibliothèque `moment` pour manipuler et comparer les dates des devoirs. Si des dates invalides sont détectées, 
 * une erreur est lancée.
 *
 * ### Étapes :
 * 1. **Vérification des dates des devoirs** :
 *    - La fonction commence par vérifier si les dates de chaque devoir sont valides avec `moment(a.date, "DD-MM-YYYY").isValid()`. 
 *    - Si l'une des dates est invalide, une erreur est générée avec un message spécifique.
 *
 * 2. **Tri des devoirs** :
 *    - Les devoirs sont triés en fonction de leur date limite à l'aide de la méthode `diff` de `moment`, qui compare les dates.
 *    - Le tri se fait du devoir ayant la date la plus ancienne à celui ayant la date la plus récente.
 *
 * 3. **Sauvegarde des devoirs** :
 *    - Une fois les devoirs triés, la fonction `saveDevoirs()` est appelée pour sauvegarder les données mises à jour dans le fichier JSON.
 *
 * 4. **Gestion des erreurs** :
 *    - Si une erreur survient lors du tri des devoirs ou de la validation des dates, un message d'erreur est enregistré dans les logs.
 *
 * ### Exemple :
 * Si le tableau `devoirs` contient les devoirs suivants :
 * ```json
 * [
 *   { "titre": "Maths - Exercice 1", "matiere": "Maths", "date": "20-12-2024" },
 *   { "titre": "Physique - Exercice 2", "matiere": "Physique", "date": "19-12-2024" }
 * ]
 * ```
 * Après l'exécution de cette fonction, les devoirs seront triés de la manière suivante :
 * ```json
 * [
 *   { "titre": "Physique - Exercice 2", "matiere": "Physique", "date": "19-12-2024" },
 *   { "titre": "Maths - Exercice 1", "matiere": "Maths", "date": "20-12-2024" }
 * ]
 * ```
 *
 * ### Paramètres :
 * - `calledBy` (string) : Le nom de la personne ou de la fonction ayant initié l'appel de cette fonction (utilisé pour les logs).
 *
 * ### Retour :
 * La fonction n'a pas de valeur de retour. Elle modifie le tableau `devoirs` et le sauvegarde après le tri.
 *
 * ### Erreurs :
 * - Si une date invalide est rencontrée, un message d'erreur est généré.
 * - Si une erreur survient lors du tri ou de la sauvegarde des devoirs, un message d'erreur est enregistré.
 */
function sortDevoirs() {
  try {
    logMessage("[\x1b[93m NOTICE \x1b[0m] Tri des devoirs en cours...");
    devoirs.sort((a, b) => {
      if (!moment(a.date, "DD-MM-YYYY").isValid() || !moment(b.date, "DD-MM-YYYY").isValid()) {
        throw new Error(`Date invalide détectée dans les devoirs : ${a.date} ou ${b.date}`);
      }
      return moment(a.date, "DD-MM-YYYY").diff(moment(b.date, "DD-MM-YYYY"));
    });
    logMessage(`[\x1b[92m SUCCESS \x1b[0m] Devoirs triés avec succès !`);
  } catch (error) {
    logMessage(
      `   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors du tri des devoirs : ${error}`
    );
  }
}

/**
 * Vérifie si l'utilisateur possède au moins un des rôles spécifiés.
 *
 * Cette fonction vérifie si un membre d'une interaction Discord possède au moins un des rôles 
 * spécifiés dans un tableau donné. Elle est utilisée pour vérifier les permissions d'un utilisateur.
 *
 * ### Étapes :
 * 1. **Vérification de la validité des paramètres** :
 *    - La fonction vérifie que l'interaction et le membre de l'interaction sont valides.
 *    - Elle vérifie également que `roleNames` est un tableau non vide.
 *
 * 2. **Vérification des rôles** :
 *    - La fonction compare les rôles de l'utilisateur avec ceux spécifiés dans `roleNames` en utilisant la méthode `some()` sur le tableau des rôles.
 *    - Si l'utilisateur possède au moins un des rôles, la fonction renvoie `true`, sinon elle renvoie `false`.
 *
 * 3. **Enregistrement des résultats** :
 *    - Si l'utilisateur possède les rôles, un message de succès est enregistré dans les logs. Sinon, un message d'information est enregistré.
 *
 * 4. **Gestion des erreurs** :
 *    - Si une erreur survient lors de la vérification des permissions (interaction ou rôle invalide), un message d'erreur est enregistré dans les logs.
 *
 * ### Exemple :
 * Si un utilisateur possède les rôles "Admin" et "Modérateur", et que vous vérifiez les rôles suivants :
 * ```javascript
 * hasRole(interaction, ["Admin", "Modérateur"], "User1");
 * ```
 * La fonction renverra `true` si l'utilisateur a l'un des deux rôles spécifiés.
 *
 * ### Paramètres :
 * - `interaction` (object) : L'objet d'interaction Discord contenant les informations sur l'utilisateur.
 * - `roleNames` (array) : Un tableau contenant les noms des rôles à vérifier.
 * - `name` (string) : Le nom de l'utilisateur ou du bot pour l'affichage dans les logs.
 *
 * ### Retour :
 * - `true` si l'utilisateur possède au moins un des rôles spécifiés.
 * - `false` si l'utilisateur ne possède aucun des rôles spécifiés.
 *
 * ### Erreurs :
 * - Si l'interaction ou le membre est introuvable, un message d'erreur est généré.
 * - Si `roleNames` n'est pas un tableau non vide, une erreur est lancée.
 * - Si une erreur survient pendant la vérification des rôles, un message d'erreur est généré.
 */
function hasRole(interaction, roleNames, name) {
  try {
    if (!interaction || !interaction.member) {
      throw new Error("Interaction ou membre introuvable.");
    }

    logMessage(
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

    logMessage(
      hasRole
        ? `[\x1b[92m SUCCESS \x1b[0m] ${name} possède au moins un des rôles : ${roleNames.join(", ")}.`
        : `[\x1b[94m EVENT \x1b[0m] ${name} ne possède aucun des rôles : ${roleNames.join(", ")}.`
    );

    return hasRole;
  } catch (error) {
    logMessage(
      `   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de la vérification des permissions pour ${name} : ${error}`
    );
    return false;
  }
}

/**
 * Génère un identifiant unique pour un devoir.
 *
 * Cette fonction génère un identifiant unique en partant de `1` et en vérifiant qu'il n'est pas déjà utilisé 
 * parmi les IDs existants des devoirs. L'identifiant est incrémenté jusqu'à ce qu'un ID unique soit trouvé.
 *
 * ### Étapes :
 * 1. **Initialisation de l'ID** :
 *    - L'ID commence à `1`.
 *
 * 2. **Vérification des IDs existants** :
 *    - La fonction utilise un `Set` pour collecter les IDs existants dans la liste des devoirs.
 *    - La méthode `map` est utilisée pour extraire les IDs des devoirs, et `existingIds.has(id)` permet de vérifier si l'ID généré existe déjà.
 *
 * 3. **Génération d'un ID unique** :
 *    - L'ID est incrémenté jusqu'à ce qu'un identifiant unique soit trouvé (c'est-à-dire un identifiant qui n'existe pas déjà).
 *
 * 4. **Gestion des erreurs** :
 *    - Si une erreur survient lors de la génération de l'ID unique, un message d'erreur est enregistré dans les logs et la fonction retourne `null`.
 *
 * ### Exemple :
 * Si les devoirs existants ont les IDs suivants :
 * ```json
 * [
 *   { "id": 1, "titre": "Maths - Exercice 1" },
 *   { "id": 2, "titre": "Physique - Exercice 2" }
 * ]
 * ```
 * La fonction retournera `3`, car c'est le premier ID non utilisé.
 *
 * ### Paramètres :
 * Aucun paramètre n'est requis pour cette fonction.
 *
 * ### Retour :
 * - Un identifiant unique (nombre entier).
 * - `null` en cas d'erreur.
 *
 * ### Erreurs :
 * - Si une erreur se produit lors de la génération de l'ID (par exemple, lors de la collecte des IDs existants), un message d'erreur est généré.
 */
function generateUniqueId() {
  try {
    let id = 0; // Point de départ pour les IDs
    const existingIds = new Set(devoirs.map((devoir) => devoir.id)); // Collecte des IDs existants

    while (existingIds.has(id)) {
      id++; // Incrémente jusqu'à trouver un ID unique
    }

    return id;
  } catch (error) {
    logMessage(`   [\x1b[91m ERROR \x1b[0m] Problème lors de la génération d'un ID unique : ${error}`);
    return null; // Retourne null en cas d'erreur
  }
}

/**
 * Régénère les IDs des devoirs de manière séquentielle.
 *
 * Cette fonction trie les devoirs par date et réattribue des identifiants uniques en fonction de leur position 
 * dans le tableau trié. L'ID commence à 1 pour le devoir ayant la date la plus ancienne et s'incrémente pour chaque devoir.
 *
 * ### Étapes :
 * 1. **Tri des devoirs** :
 *    - Les devoirs sont triés par date (du plus ancien au plus récent) à l'aide de la méthode `sort`.
 *    - La comparaison des dates se fait en utilisant `new Date(a.date) - new Date(b.date)`.
 *
 * 2. **Réattribution des IDs** :
 *    - Après le tri, un ID séquentiel est attribué à chaque devoir en fonction de sa position dans le tableau trié.
 *    - L'ID du premier devoir sera `1`, le second sera `2`, et ainsi de suite.
 *
 * 3. **Enregistrement des modifications** :
 *    - Un message de succès est enregistré dans les logs pour indiquer que les IDs ont été régénérés avec succès.
 *
 * 4. **Gestion des erreurs** :
 *    - Si une erreur survient lors du tri ou de la réattribution des IDs, un message d'erreur est enregistré dans les logs.
 *
 * ### Exemple :
 * Si la liste des devoirs avant l'appel de cette fonction est :
 * ```json
 * [
 *   { "titre": "Maths - Exercice 1", "date": "20-12-2024" },
 *   { "titre": "Physique - Exercice 2", "date": "19-12-2024" }
 * ]
 * ```
 * Après l'exécution de la fonction, les devoirs auront les IDs suivants :
 * ```json
 * [
 *   { "id": 1, "titre": "Physique - Exercice 2", "date": "19-12-2024" },
 *   { "id": 2, "titre": "Maths - Exercice 1", "date": "20-12-2024" }
 * ]
 * ```
 *
 * ### Paramètres :
 * Aucun paramètre n'est requis pour cette fonction.
 *
 * ### Retour :
 * La fonction n'a pas de valeur de retour. Elle modifie directement la liste des devoirs.
 *
 * ### Erreurs :
 * - Si une erreur se produit lors du tri des devoirs ou de la réattribution des IDs, un message d'erreur est généré.
 */
function regenerateIds() {
  try {
    logMessage("[\x1b[93m NOTICE \x1b[0m] Régénération des IDs en cours...");
    // Trier les devoirs par date
    devoirs.sort((a, b) => new Date(a.date) - new Date(b.date));

    // Réattribuer les IDs de manière séquentielle
    devoirs.forEach((devoir, index) => {
      devoir.id = index;
    });

    logMessage("[\x1b[92m SUCCESS \x1b[0m] Les IDs des devoirs ont été régénérés avec succès !");
  } catch (error) {
    logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la régénération des IDs : ${error}`);
  }
}

/**
 * Sélectionne une réponse aléatoire parmi une liste de réponses.
 *
 * Cette fonction prend un tableau de réponses et retourne une réponse aléatoire choisie dans ce tableau.
 *
 * ### Étapes :
 * 1. **Sélection aléatoire** :
 *    - La fonction utilise `Math.floor(Math.random() * responses.length)` pour générer un index aléatoire dans le tableau `responses`.
 *    - L'élément correspondant à cet index est retourné comme réponse.
 *
 * 2. **Retour de la réponse** :
 *    - La réponse choisie aléatoirement est retournée.
 *
 * ### Exemple :
 * Si la liste des réponses est :
 * ```javascript
 * const responses = ["Oui", "Non", "Peut-être"];
 * ```
 * La fonction pourrait retourner `"Oui"`, `"Non"` ou `"Peut-être"`, de manière aléatoire.
 *
 * ### Paramètres :
 * - `responses` (array) : Un tableau de chaînes de caractères représentant les différentes réponses possibles.
 *
 * ### Retour :
 * - Une chaîne de caractères choisie aléatoirement dans le tableau `responses`.
 *
 * ### Erreurs :
 * - Aucune. Si le tableau est vide, la fonction retournera `undefined` (comportement JavaScript normal).
 */
function getRandomResponse(responses) {
  return responses[Math.floor(Math.random() * responses.length)];
}

// Gestion des interactions
client.on("interactionCreate", async (interaction) => {
  if (!interaction.isCommand()) return;

  const { commandName, user } = interaction;

  if (commandName === "add") {
    try {
      logMessage(
        `[\x1b[94m EVENT \x1b[0m] Interaction : commande "ADD" par ${user.username}`
      );
      const devoir = interaction.options.getString("devoir").trim().toUpperCase(); // Convertir en majuscules
      const matiere = interaction.options
        .getString("matiere")
        .trim()
        .toUpperCase(); // Convertir en majuscules
      const date = interaction.options.getString("date");
  
      // Vérifier si le devoir existe déjà
      if (
        devoirs.some(
          (d) => d.devoir === devoir && d.matiere === matiere && d.date === date
        )
      ) {
        logMessage(
          "   [\x1b[91m ERROR \x1b[0m] Erreur lors de la création de devoir : Devoir déjà existant"
        );
        return await interaction.reply(
          "Ce devoir existe déjà avec la même date et matière."
        );
      }
  
      // Vérifier si la date est valide
      if (!moment(date, "DD-MM-YYYY", true).isValid()) {
        logMessage("   [\x1b[91m ERROR \x1b[0m] Erreur lors de la création de devoir : Date invalide");
        return await interaction.reply(
          "Date invalide. Utilisez le format JJ-MM-AAAA."
        );
      }
  
      // Ajouter le devoir
      devoirs.push({
        id: generateUniqueId(),
        devoir,
        matiere,
        date,
        addedBy: user.username,
      });

      try {
        await removeExpiredDevoirs();
        await sortDevoirs();
        await UpdateJsonData();// Enregistrez les modifications
      } catch (error) {
        logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de l'enregistrement des données : ${error}`
        );
        return await interaction.reply("Erreur lors de l'enregistrement des données.");
      }
  
      // Créer l'embed pour la réponse
      const embed = createEmbed(
        "Devoir ajouté",
        `**Devoir :** ${devoir}\n**Matière :** ${matiere}\n**Date limite :** ${date}\n**Ajouté par :** ${user.username}`, config.appearance.embedGlobalColor
      );
      
      logMessage(
        `[\x1b[92m SUCCESS \x1b[0m] Devoir ajouté | Devoir : ${devoir}, Matière : ${matiere}, Date limite : ${date}, Ajouté par : ${user.username}`
      );
      await interaction.reply({
        embeds: [embed],
      });
  
    } catch (error) {
      // Gérer les erreurs globales
      logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur est survenue lors de l'exécution de la commande "ADD" : ${error}`);
      await interaction.reply(
        "Une erreur est survenue lors de l'ajout du devoir. Veuillez réessayer plus tard."
      );
    }
  } else if (commandName === "modify") {
    try {
      logMessage(
        `[\x1b[94m EVENT \x1b[0m] Interaction : commande "MODIFY" par ${user.username}`
      );
    
      const id = interaction.options.getInteger("id");
      const newDevoir =
        interaction.options.getString("devoir")?.trim().toUpperCase() || null;
      const newMatiere =
        interaction.options.getString("matiere")?.trim().toUpperCase() || null;
      const newDate = interaction.options.getString("date") || null;
    
      let devoirToModify;
      
      try {
        devoirToModify = devoirs.find((d) => d.id === id);
        if (!devoirToModify) {
          throw new Error(`Devoir non trouvé avec l'ID ${id}`);
        }
      } catch (error) {
        logMessage(
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
        logMessage(
          `   [\x1b[91m ERROR \x1b[0m] ${error}`
        );
        return await interaction.reply("Date invalide. Utilisez le format JJ-MM-AAAA.");
      }
    
      try {
        await removeExpiredDevoirs();
        await sortDevoirs();
        await UpdateJsonData();// Enregistrez les modifications
      } catch (error) {
        logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de l'enregistrement des données : ${error}`
        );
        return await interaction.reply("Erreur lors de l'enregistrement des données.");
      }
    
      try {
        const embed = createEmbed(
          "Devoir modifié",
          `**ID :** ${id}\n**Devoir :** ${devoirToModify.devoir}\n**Matière :** ${devoirToModify.matiere}\n**Date limite :** ${devoirToModify.date}`, config.appearance.embedGlobalColor
        );
        await interaction.reply({
          embeds: [embed],
        });
      } catch (error) {
        logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de la création de l'embed : ${error}`
        );
        return await interaction.reply("Erreur lors de la réponse.");
      }
    } catch (error) {
      logMessage(
        `   [\x1b[91m ERROR \x1b[0m] Erreur inattendue : ${error}`
      );
      return await interaction.reply("Une erreur inattendue est survenue.");
    }
    
  } else if (commandName === "list") {
    try {
      logMessage(
        `[\x1b[94m EVENT \x1b[0m] Interaction : commande "LIST" par ${user.username}`
      );
    
      let devoirList;
    
      try {
        devoirList = devoirs.length
          ? devoirs
              .map(
                (d) =>
                  `**ID :** ${d.id}   |   **${d.devoir}** en **${d.matiere}**, pour le ${d.date} (ajouté par ${d.addedBy})`
              )
              .join("\n\n")
          : "Aucun devoir n'a été ajouté.";
      } catch (error) {
        logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de la génération de la liste des devoirs : ${error}`
        );
        return await interaction.reply("Une erreur est survenue lors de la génération de la liste des devoirs.");
      }
    
      let embedList;
    
      try {
        embedList = createEmbed("Liste des devoirs", devoirList, config.appearance.embedGlobalColor);
      } catch (error) {
        logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de la création de l'embed : ${error}`
        );
        return await interaction.reply("Erreur lors de la création de l'embed.");
      }
    
      try {
        await interaction.reply({
          embeds: [embedList],
        });
      } catch (error) {
        logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de l'envoi de la réponse : ${error}`
        );
        return await interaction.reply("Erreur lors de l'envoi de la réponse.");
      }
    } catch (error) {
      logMessage(
        `   [\x1b[91m ERROR \x1b[0m] Erreur inattendue : ${error}`
      );
      return await interaction.reply("Une erreur inattendue est survenue.");
    }    
  } else if (commandName === "check") {
    try {
      logMessage(
        `[\x1b[94m EVENT \x1b[0m] Interaction : commande "CHECK" par ${user.username}`
      );
    
      await interaction.reply("Vérification des devoirs à venir en cours...");
    
      try {
        await checkUpcoming(interaction);
      } catch (error) {
        logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors de la vérification des devoirs à venir : ${error}`
        );
        return await interaction.reply("Une erreur est survenue lors de la vérification des devoirs à venir.");
      }
    
    } catch (error) {
      logMessage(
        `   [\x1b[91m ERROR \x1b[0m] Erreur inattendue : ${error}`
      );
      return await interaction.reply("Une erreur inattendue est survenue.");
    }    
  } else if (commandName === "sort") {
    try {
      if (!hasRole(interaction, config.permissions.adminRoles, user.username)) {
        // Vérifie si l'utilisateur a le rôle Admin
        logMessage(`   [\x1b[91m ERROR \x1b[0m] Permission refusée pour ${user.username}, Interaction : commande "SORT"`);
        return await interaction.reply({
          content: "Vous n'avez pas la permission d'utiliser cette commande.",
          ephemeral: true,
        });
      }
    
      logMessage(
        `[\x1b[94m EVENT \x1b[0m] Interaction : commande "SORT" par ${user.username}`
      );
    
      try {
        await sortDevoirs();
        await UpdateJsonData();
        await interaction.reply("Tri terminé avec succès !");
      } catch (error) {
        logMessage(
          `   [\x1b[91m ERROR \x1b[0m] Erreur lors du tri des devoirs : ${error}`
        );
        return await interaction.reply("Une erreur est survenue lors du tri des devoirs.");
      }
    } catch (error) {
      logMessage(
        `   [\x1b[91m ERROR \x1b[0m] Erreur inattendue : ${error}`
      );
      return await interaction.reply("Une erreur inattendue est survenue.");
    }    
  } else if (commandName === "regenerate") {
    try {
      if (!hasRole(interaction, config.permissions.adminRoles, user.username)) {
        // Vérifie si l'utilisateur a le rôle Admin
        logMessage(`   [\x1b[91m ERROR \x1b[0m] Permission refusée pour ${user.username}, Interaction : commande "REGENERATEIDS"`);
        return await interaction.reply({
          content: "Vous n'avez pas la permission d'utiliser cette commande.",
          ephemeral: true,
        });
      }
    
      logMessage(`[\x1b[94m EVENT \x1b[0m] Interaction : commande "REGENERATEIDS" par ${user.username}`);
    
      try {
        await UpdateJsonData();
        await interaction.reply("Les identifiants de devoirs ont été régénérés avec succès !");
        logMessage("[\x1b[92m SUCCESS \x1b[0m] Les identifiants de devoirs ont été régénérés avec succès !");
      } catch (error) {
        logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de la régénération des identifiants : ${error}`);
        await interaction.reply({
          content: "Une erreur est survenue lors de la régénération des identifiants. Veuillez réessayer plus tard.",
          ephemeral: true,
        });
      }
    } catch (error) {
      logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur inattendue s'est produite dans la commande "REGENERATEIDS" : ${error}`);
      await interaction.reply({
        content: "Une erreur inattendue est survenue. Veuillez contacter un administrateur.",
        ephemeral: true,
      });
    }    
  } else if (commandName === "purge") {
    try {
      if (!hasRole(interaction, config.permissions.adminRoles, user.username)) {
        // Vérifie si l'utilisateur a le rôle Admin
        logMessage(`   [\x1b[91m ERROR \x1b[0m] Permission refusée pour ${user.username}, Interaction : commande "PURGE"`);
        return await interaction.reply({
          content: "Vous n'avez pas la permission d'utiliser cette commande.",
          ephemeral: true,
        });
      }
    
      logMessage(`[\x1b[94m EVENT \x1b[0m] Interaction : commande "PURGE" par ${user.username}`);
    
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
        content: "Es-tu sûr de vouloir purger tous les devoirs ?",
        components: [row],
      });
    
      const filter = (i) => i.user.id === user.id;
      const collector = interaction.channel.createMessageComponentCollector({
        filter,
        time: 15000,
      });
    
      collector.on("collect", async (i) => {
        try {
          if (i.customId === "confirm_purge") {
            logMessage(`[\x1b[94m EVENT \x1b[0m] Interaction : commande "PURGE" confirmée par ${user.username}`);
            devoirs = [];
            await i.update({
              content: "Tous les devoirs ont été supprimés.",
              components: [],
            });
            UpdateJsonData();
            logMessage("[\x1b[92m SUCCESS \x1b[0m] Purge effectuée avec succès !");
          } else {
            await i.update({
              content: "Purge annulée.",
              components: [],
            });
            logMessage(`[\x1b[94m EVENT \x1b[0m] Commande "PURGE" annulée par ${user.username}`);
          }
        } catch (error) {
          logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la gestion de la commande "PURGE" : ${error}`);
          await i.update({
            content: "Une erreur est survenue lors du traitement de la commande.",
            components: [],
          });
        }
      });
    
      collector.on("end", (collected) => {
        try {
          if (!collected.size) {
            logMessage(`   [\x1b[91m ERROR \x1b[0m] Temps écoulé, aucune action effectuée, Interaction : commande "PURGE"`);
            interaction.editReply({
              content: "Temps écoulé, aucune action effectuée.",
              components: [],
            });
          }
        } catch (error) {
          logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la gestion de la fin de la collecte de la commande "PURGE" : ${error}`);
        }
      });
    } catch (error) {
      logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur inattendue dans la commande "PURGE" : ${error}`);
      await interaction.reply({
        content: "Une erreur inattendue est survenue lors du traitement de la commande.",
        ephemeral: true,
      });
    }    
  } else if (commandName === "about") {
    try {
      logMessage(
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
      logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la commande "ABOUT" : ${error}`);
      await interaction.reply({
        content: "Une erreur est survenue lors de l'exécution de la commande 'ABOUT'.",
        ephemeral: true,
      });
    }    
  } else if (commandName === "kill") {
    try {
      // Vérifie si l'utilisateur a le rôle Admin
      if (!hasRole(interaction, config.permissions.adminRoles, user.username)) {
        logMessage(`   [\x1b[91m ERROR \x1b[0m] Permission refusée pour ${user.username}, Interaction : commande "KILL"`);
        return await interaction.reply({
          content: "Vous n'avez pas la permission d'utiliser cette commande.",
          ephemeral: true,
        });
      }
    
      logMessage(`[\x1b[94m EVENT \x1b[0m] Interaction : commande "KILL" par ${user.username}`);
    
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
            logMessage(`[\x1b[94m EVENT \x1b[0m] Interaction : commande "KILL" confirmée par ${user.username}`);
            await i.update({
              content: "Arrêt du bot en cours...",
              components: [],
            });
            UpdateJsonData();
            logMessage(`\x1b[38;5;154mGoodBye World !`);
            client.destroy();
            process.exit(1); // Arrêt du bot
          } else if (i.customId === "cancel_kill") {
            // L'utilisateur a annulé
            await i.update({
              content: "Arrêt du bot annulé.",
              components: [],
            });
            logMessage(`[\x1b[94m EVENT \x1b[0m] Interaction : commande "KILL" annulée par ${user.username}`);
          }
        } catch (error) {
          logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la collecte de l'interaction : ${error}`);
        }
      });
    
      collector.on("end", (collected) => {
        try {
          if (!collected.size) {
            logMessage(`   [\x1b[91m ERROR \x1b[0m] Temps écoulé, aucune action effectuée, Interaction : commande "KILL"`);
            interaction.editReply({
              content: "Temps écoulé, aucune action effectuée.",
              components: [],
            });
          }
        } catch (error) {
          logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de l'expiration du collector : ${error}`);
        }
      });
    
    } catch (error) {
      logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur dans la commande "KILL" : ${error}`);
      await interaction.reply({
        content: "Une erreur s'est produite lors de l'exécution de la commande.",
        ephemeral: true,
      });
    }
    
  }
});

// Gestion des nouveaux messages
client.on('messageCreate', async (message) => {

  // Convertir le message en minuscules, enlever les espaces inutiles et les caractères spéciaux
  const content = message.content.replace(/[^\w\s]/g, '').trim().toLowerCase();

  // Liste des mots-clés à détecter avec un indicateur pour savoir s'ils doivent être vérifiés sur la fin du message
  const keywords = {
    quoi: { words: ['quoi', 'quois', 'quwa', 'kwah', 'koi', 'kwe', 'kwé', 'koii', 'kwai', 'kwé', 'quoi', 'quoua', 'kwahh', 'koa', 'quoii', 'qwa'], checkEnd: true },
    qui: { words: ['qui', 'ki', 'kwi', 'kwy', 'kuy', 'quii', 'kwéé'], checkEnd: true },
    oui: { words: ['oui', 'ooui', 'ouii', 'oooui', 'ouiii', 'owii', 'ouwi', 'wii', 'ouee', 'oowi', 'ouiy', 'oüi', 'ouy', 'ooüi', 'ouay', 'ouhi'],checkEnd: true},    
    hein: { words: ['hein', 'hien', 'hinn', 'hyn', 'heinou', 'heinn'], checkEnd: false },
    genre: { words: ['genre', 'genre', 'jener', 'jenre', 'jhenre'], checkEnd: false },
    non: { words: ['non', 'noo', 'nno', 'nann', 'nan', 'nann', 'nonn'], checkEnd: true }
  };

  // Fonction de détection des mots similaires
  const getBestMatch = (content, keywordList) => {
    const bestMatch = stringSimilarity.findBestMatch(content, keywordList);
    return bestMatch.bestMatch.rating > 0.5; // Seuil de similarité
  };

  // Fonction qui vérifie si un mot-clé est à la fin du message
  const endsWithKeyword = (content, keywordList) => {
    const regex = new RegExp(`(${keywordList.join('|')})$`, 'i'); // Vérifie si l'un des mots-clés est à la fin
    return regex.test(content);
  };

  // Fonction qui vérifie si un mot-clé est dans tout le message
  const containsKeyword = (content, keywordList) => {
    const regex = new RegExp(`(${keywordList.join('|')})`, 'i'); // Vérifie si l'un des mots-clés est n'importe où
    return regex.test(content);
  };

  // Vérification si le message vient de toi
  if (!config.permissions.myID.includes(message.author.id)) {
    // Réponse pour les autres utilisateurs
    switch (true) {
      case keywords.quoi.checkEnd ? endsWithKeyword(content, keywords.quoi.words) : containsKeyword(content, keywords.quoi.words) || getBestMatch(content, keywords.quoi.words): {
        const responses = ["Feur", "Coubeh", "Chi"];
        message.reply(getRandomResponse(responses));
        break;
      }
      case keywords.qui.checkEnd ? endsWithKeyword(content, keywords.qui.words) : containsKeyword(content, keywords.qui.words) || getBestMatch(content, keywords.qui.words): {
        const responses = ["Kette"];
        message.reply(getRandomResponse(responses));
        break;
      }
      case keywords.oui.checkEnd ? endsWithKeyword(content, keywords.oui.words) : containsKeyword(content, keywords.oui.words) || getBestMatch(content, keywords.oui.words): {
        const responses = ["Stiti"];
        message.reply(getRandomResponse(responses));
        break;
      }
      case keywords.hein.checkEnd ? endsWithKeyword(content, keywords.hein.words) : containsKeyword(content, keywords.hein.words) || getBestMatch(content, keywords.hein.words): {
        const responses = ["Deux"];
        message.reply(getRandomResponse(responses));
        break;
      }
      case keywords.genre.checkEnd ? endsWithKeyword(content, keywords.genre.words) : containsKeyword(content, keywords.genre.words) || getBestMatch(content, keywords.genre.words): {
        const responses = ["Raconte pas ta vie", "Genre quoi ?", "Pas de drama ici, merci."];
        message.reply(getRandomResponse(responses));
        break;
      }
      case keywords.non.checkEnd ? endsWithKeyword(content, keywords.non.words) : containsKeyword(content, keywords.non.words) || getBestMatch(content, keywords.non.words): {
        const responses = ["Bril"];
        message.reply(getRandomResponse(responses));
        break;
      }
      default:
        // Aucun cas ne correspond, pas de réponse
        break;
    }
  } else {
    const responses = ["Attention mon seigneur", "Subnautica > Raft", ":pinched_fingers:", "Ok boomer.", "UwU", "Giga cringe.", "Le roi a parlé ! 👑", "Raft, c'est chill, ok ?", "Quand est-ce que tu me fais un update ? 🤔", "Je vais appeler les admins.", "Ça mérite un ban, ça non ?"];

    switch (true) {
      case keywords.quoi.checkEnd ? endsWithKeyword(content, keywords.quoi.words) : containsKeyword(content, keywords.quoi.words) || getBestMatch(content, keywords.quoi.words): {
        message.reply(getRandomResponse(responses));
        break;
      }
      case keywords.qui.checkEnd ? endsWithKeyword(content, keywords.qui.words) : containsKeyword(content, keywords.qui.words) || getBestMatch(content, keywords.qui.words): {
        message.reply(getRandomResponse(responses));
        break;
      }
      case keywords.oui.checkEnd ? endsWithKeyword(content, keywords.oui.words) : containsKeyword(content, keywords.oui.words) || getBestMatch(content, keywords.oui.words): {
        message.reply(getRandomResponse(responses));
        break;
      }
      case keywords.hein.checkEnd ? endsWithKeyword(content, keywords.hein.words) : containsKeyword(content, keywords.hein.words) || getBestMatch(content, keywords.hein.words): {
        message.reply(getRandomResponse(responses));
        break;
      }
      case keywords.genre.checkEnd ? endsWithKeyword(content, keywords.genre.words) : containsKeyword(content, keywords.genre.words) || getBestMatch(content, keywords.genre.words): {
        message.reply(getRandomResponse(responses));
        break;
      }
      case keywords.non.checkEnd ? endsWithKeyword(content, keywords.non.words) : containsKeyword(content, keywords.non.words) || getBestMatch(content, keywords.non.words): {
        message.reply(getRandomResponse(responses));
        break;
      }
      default:
        // Aucun cas ne correspond, pas de réponse
        break;
    }
  }
});

/**
 * Événement déclenché lorsque le client Discord est prêt et connecté.
 *
 * Cet événement est émis une seule fois lorsque le bot se connecte avec succès à Discord et est prêt à fonctionner.
 * Lors de l'exécution, plusieurs tâches sont lancées pour s'assurer que le bot soit à jour et prêt à envoyer des rappels.
 *
 * ### Étapes :
 * 1. **Log d'initialisation** :
 *    - Lorsque le bot est prêt, un message est loggé pour indiquer que le bot s'est connecté avec succès.
 *
 * 2. **Mise à jour au démarrage** :
 *    - La fonction démarre une mise à jour initiale qui comprend plusieurs actions pour s'assurer que les informations du bot sont à jour.
 *    - Les tâches suivantes sont exécutées au démarrage :
 *      - **Suppression des devoirs expirés** : `removeExpiredDevoirs` est appelée pour retirer les devoirs expirés de la liste.
 *      - **Gestion des fichiers de log** : `manageLogFiles` est appelée pour gérer les fichiers de log du bot.
 *      - **Vérification des devoirs à venir** : `checkUpcoming` est appelée pour vérifier les devoirs à venir et envoyer des rappels si nécessaire.
 *      - **Planification des tâches récurrentes** : `scheduleTasks` est appelée pour planifier les tâches de mise à jour régulières.
 *
 * 3. **Log de réussite** :
 *    - Après avoir exécuté toutes les tâches de démarrage, un message est loggé pour confirmer que le bot est prêt et en ligne.
 *
 * ### Exemples :
 * ```javascript
 * client.once("ready", async () => {
 *   // Initialisation du bot
 * });
 * ```
 *
 * ### Paramètres :
 * Aucun paramètre n'est passé à cette fonction.
 *
 * ### Retour :
 * Aucun retour n'est fourni. Cette fonction sert uniquement à initier des actions lors du démarrage du bot.
 *
 * ### Erreurs :
 * - Si l'une des fonctions exécutées (comme `removeExpiredDevoirs`, `manageLogFiles`, `checkUpcoming`, ou `scheduleTasks`) échoue, une erreur sera loggée, mais le bot continue de fonctionner.
 * - Les erreurs survenues lors de l'exécution de ces fonctions seront traitées dans les fonctions respectives avec des logs d'erreur.
 */
client.once("ready", async () => {
  logMessage(`[\x1b[92m SUCCESS \x1b[0m] Connecté en tant que ${client.user.tag}\n`);
  
  // Mise à jour au démarrage
  logMessage("[\x1b[93m NOTICE \x1b[0m] Lancement de la mise à jour de démarrage : \n");
  await manageLogFiles();
  await removeExpiredDevoirs();
  await sortDevoirs();
  await UpdateJsonData();
  await checkUpcoming();
  await scheduleTasks();

  logMessage("[\x1b[92m SUCCESS \x1b[0m] Le bot est prêt et en ligne !");
});

/**
 * Vérifie les devoirs à venir et envoie des rappels groupés dans un canal Discord.
 *
 * Cette fonction récupère les devoirs depuis un fichier ICS, filtre ceux qui sont à venir dans les prochains jours,
 * et envoie des rappels sous forme d'un message groupé dans un canal spécifique Discord. Elle gère également la suppression
 * des rappels précédents et répond aux interactions Discord si elles sont disponibles.
 *
 * ### Étapes :
 * 1. **Téléchargement des devoirs depuis le fichier ICS** :
 *    - La fonction récupère les devoirs en appelant `fetchFromICS` avec l'URL du fichier ICS.
 *
 * 2. **Sélection du canal de Discord** :
 *    - Le canal où les rappels seront envoyés est sélectionné en fonction de l'environnement de test (`PROCESS_TEST_MODE`).
 *    - La fonction récupère l'ID du canal approprié dans la configuration.
 *
 * 3. **Filtrage des devoirs** :
 *    - Les devoirs sont filtrés pour ne garder que ceux dont la date limite est dans les 7 prochains jours, y compris aujourd'hui.
 *    - La date de chaque devoir est comparée à la date actuelle pour déterminer le nombre de jours restants.
 *
 * 4. **Suppression des rappels précédents** :
 *    - Si des rappels précédents existent dans le canal, ils sont récupérés (jusqu'à 50 messages) et supprimés en bloc.
 *
 * 5. **Création des rappels groupés** :
 *    - Un message groupé est créé en formattant les devoirs à venir et en ajoutant le nombre de jours restants.
 *    - Si des rappels existent, ils sont envoyés sous forme d'un seul message (embed) via `sendReminder`.
 *
 * 6. **Réponse aux interactions** :
 *    - Si la fonction est appelée en réponse à une interaction Discord, elle répond à l'utilisateur pour lui indiquer si des rappels ont été envoyés ou non.
 *    - Si aucune interaction n'est disponible, les rappels sont loggés mais ne sont pas envoyés.
 *
 * ### Exemples :
 *
 * ```javascript
 * checkUpcoming(interaction);
 * ```
 *
 * ### Paramètres :
 * - `interaction` (object, optional) : L'objet interaction Discord, utilisé pour répondre à l'utilisateur.
 *
 * ### Retour :
 * Aucun retour n'est fourni. Les messages sont envoyés directement au canal et les réponses sont faites à l'interaction, si applicable.
 *
 * ### Erreurs :
 * - Si une erreur se produit lors du téléchargement des devoirs, de la suppression des messages ou de l'envoi des rappels, un message d'erreur est loggé.
 * - Si aucune interaction n'est disponible, un log d'information est enregistré pour indiquer qu'aucun rappel automatique n'a été envoyé.
 */
async function checkUpcoming(interaction) {
  try {
    await fetchFromICS(config.urls.icsUrl);
    const channel = await client.channels.fetch(
      process.env.PROCESS_TEST_MODE === '1' ? config.ids.testChannelId : config.ids.devoirsChannelId
    );
    
    const now = moment(); // Garder now intact
    logMessage("[\x1b[93m NOTICE \x1b[0m] Vérification des devoirs à envoyer en cours...");

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
            logMessage(
              `\x1b[92m[\x1b[92m SUCCESS \x1b[0m] ${fetched.size} messages ont été supprimés dans le salon (${channel.name}) avec succès !\x1b[0m`
            );
          } catch (error) {
            logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la suppression des messages : ${error}`);
          }
      }

    }
    // Envoyer tous les rappels dans un seul message
    if (reminderMessages) {
      await sendReminder("Rappel de Devoirs", reminderMessages, config.appearance.embedDevoirsColor, channel); // Envoi groupé
    }

    if (qcmSection) {
      await sendReminder("Rappel de QCM", qcmSection, config.appearance.embedQCMColor, channel); // Envoi groupé
    }

    if (saeSection) {
      await sendReminder("Rappel de S.A.E", saeSection, config.appearance.embedSAEColor, channel); // Envoi groupé
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
      logMessage(
        devoirs.length
          ? "[\x1b[92m SUCCESS \x1b[0m] Tous les rappels de devoir ont été créer avec succès !"
          : "[\x1b[93m NOTICE \x1b[0m] Aucun rappels de devoir à créer."
      );
    }
  } catch (error) {
    logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de la vérification des devoirs à venir : ${error}`);
  }
}

/**
 * Envoie un rappel de devoirs dans un canal spécifique Discord.
 *
 * Cette fonction crée un embed contenant un message de rappel pour les devoirs et l'envoie
 * dans le canal Discord spécifié. Elle gère les erreurs de manière appropriée et logge les actions.
 *
 * ### Étapes :
 * 1. **Vérification du canal** :
 *    - La fonction vérifie si le canal est défini et existe avant de tenter d'envoyer un message.
 *
 * 2. **Création de l'embed** :
 *    - Un embed est créé à partir du message passé en paramètre.
 *    - L'embed est formaté pour contenir les informations nécessaires, comme le titre et le contenu du message.
 *
 * 3. **Envoi du message** :
 *    - Le message contenant l'embed est envoyé au canal spécifié.
 *    - Un message de succès est loggé si l'envoi est réussi.
 *
 * 4. **Gestion des erreurs** :
 *    - Si le canal n'existe pas ou si une erreur survient lors de l'envoi du message, un message d'erreur est loggé.
 *
 * ### Exemples :
 *
 * ```javascript
 * sendReminder("N'oubliez pas de rendre vos devoirs !", someChannel);
 * ```
 *
 * ### Paramètres :
 * - `message` (string) : Le message de rappel à envoyer dans l'embed.
 * - `channel` (object) : L'objet canal Discord où le message sera envoyé.
 *
 * ### Retour :
 * Aucun retour n'est fourni. Les messages sont envoyés directement au canal.
 *
 * ### Erreurs :
 * - Si le canal est introuvable ou si une erreur se produit lors de l'envoi du message, un message d'erreur est loggé.
 */
async function sendReminder(title, message, color, channel) {
  try {    
    // Vérifier si le canal existe
    if (channel) {
      // Créer un embed avec tous les devoirs
      const embed = createEmbed(title, message, color);

      // Envoi du message avec l'embed
      await channel.send({embeds: [embed]});
      logMessage(`[\x1b[92m SUCCESS \x1b[0m] Rappels de devoir pour ${title} envoyés avec succès !`);
    } else {
      logMessage("   [\x1b[91m ERROR \x1b[0m] Le canal n'a pas pu être trouvé.");
    }

  } catch (error) {
    logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur est survenue lors de l'envoi du rappel de devoirs : ${error}`);
  }
}

/**
 * Récupère un fichier ICS depuis une URL et analyse son contenu pour en extraire les devoirs.
 *
 * Cette fonction télécharge un fichier ICS, le parse et en extrait les devoirs s'ils répondent à certains critères,
 * puis les ajoute à la liste des devoirs.
 * 
 * ### Étapes :
 * 1. **Téléchargement du fichier ICS** :
 *    - La fonction envoie une requête HTTP `GET` à l'URL spécifiée pour récupérer le fichier ICS.
 *    - Si la requête échoue (erreur HTTP ou autre), une erreur est lancée et un message d'erreur est loggé.
 *
 * 2. **Analyse du contenu ICS** :
 *    - Le fichier ICS est parsé pour extraire les événements.
 *    - Si un événement est de type `VEVENT` et contient des informations valides (titre, date de fin, catégories),
 *      il est nettoyé pour supprimer des mentions inutiles (comme "DEADLINE", "WEEK", etc.) et ajouté à la liste des devoirs.
 *
 * 3. **Ajout des devoirs** :
 *    - Si un devoir est trouvé, il est ajouté à la liste des devoirs avec un identifiant unique généré.
 *    - Les devoirs sont ensuite triés.
 * 
 * ### Exemples :
 *
 * ```javascript
 * fetchFromICS("https://example.com/calendar.ics");
 * ```
 *
 * ### Paramètres :
 * - `url` (string) : L'URL du fichier ICS à télécharger.
 *
 * ### Retour :
 * Aucune valeur n'est retournée. La liste des devoirs est modifiée directement.
 *
 * ### Erreurs :
 * - Si une erreur survient lors du téléchargement ou du parsing du fichier ICS, un message d'erreur est loggé dans la console.
 */
async function fetchFromICS(url) {
  try {
    // Téléchargement du fichier ICS
    const response = await fetch(url);
    
    // Vérification de la réponse
    if (!response.ok) {
      throw new Error(`Erreur lors du téléchargement du fichier ICS, statut : ${response.status}`);
    }

    // Parsing du fichier ICS
    const parsedData = ical.parseICS(await response.text());
    logMessage("[\x1b[93m NOTICE \x1b[0m] Recherche de devoirs sur le calendrier en cours...");
    
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
        
          // Vérifier si le devoir existe déjà dans la liste
          const exists = devoirs.some(d => 
            d.devoir === cleanedTitle && d.matiere === matiere && d.date === formattedDate
          );
        
          if (!exists) {
            // Si le devoir n'existe pas déjà, l'ajouter à la liste
            devoirs.push({
              id: generateUniqueId(),
              devoir: cleanedTitle,
              matiere: matiere,
              date: formattedDate,
              addedBy: "Moodle",
            });
          }
        }        
      }
    }
    UpdateJsonData();
    logMessage("[\x1b[92m SUCCESS \x1b[0m] Devoirs récupérer du calendrier avec succès ! Nombre de devoirs récupérer : " + devoirs.length + "");
  } catch (error) {
    logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la récupération du fichier ICS : ${error}`);
  }
}

/**
 * Planifie et exécute des tâches automatiques de mise à jour.
 *
 * La fonction programme une tâche de mise à jour qui s'exécute à 6h00 chaque jour. Si l'heure actuelle est déjà passée, 
 * la tâche est programmée pour le lendemain. Elle exécute une série de tâches périodiques toutes les 24 heures après 
 * l'exécution initiale.
 *
 * ### Comportement :
 * 1. Vérifie si l'heure actuelle est après 6h00, auquel cas elle planifie la mise à jour pour 6h00 du jour suivant.
 * 2. Calcule le délai jusqu'à la prochaine exécution à 6h00.
 * 3. Exécute les mises à jour après ce délai.
 * 4. Répète les mises à jour toutes les 24 heures après la première exécution.
 *
 * La fonction gère également les erreurs qui peuvent survenir lors de la planification ou de l'exécution des mises à jour.
 *
 * ### Exemples :
 *
 * ```javascript
 * scheduleTasks();
 * ```
 *
 * #### Logique de mise à jour périodique :
 * - Toutes les 24 heures, les mises à jour suivantes sont exécutées :
 *   - `fetchFromICS()` pour récupérer les devoirs depuis l'URL ICS.
 *   - `removeExpiredDevoirs()` pour supprimer les devoirs périmés.
 *   - `checkUpcoming()` pour vérifier les devoirs à venir.
 *   - `manageLogFiles()` pour gérer les fichiers de log.
 *
 * #### Paramètres :
 * Aucun paramètre n'est nécessaire pour appeler cette fonction.
 *
 * #### Retour :
 * Aucune valeur n'est retournée. Les mises à jour sont effectuées de manière asynchrone, et des messages de log sont générés pour indiquer les étapes.
 *
 * @throws {Error} Si une erreur survient lors de la planification ou de l'exécution des mises à jour.
 */
function scheduleTasks() {
  logMessage("[\x1b[93m NOTICE \x1b[0m] Mise en place de la mise à jour automatique en cours...");
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
    logMessage(`[\x1b[93m NOTICE \x1b[0m] Prochaine mise à jour dans ${(delay / 1000 / 60 / 60).toFixed(2)} heures...`);

    // Exécute les tâches après le délai calculé
    setTimeout(async () => {
      try {
        logMessage("[\x1b[93m NOTICE \x1b[0m] Exécution des mises à jour périodique...");
        manageLogFiles();
        removeExpiredDevoirs();
        sortDevoirs();
        UpdateJsonData();
        await checkUpcoming();
      } catch (intervalError) {
        logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur est survenue lors de la mise à jour périodique : ${intervalerror}`);
      }
        setInterval(async () => {
          try {
            logMessage("[\x1b[93m NOTICE \x1b[0m] Exécution des mises à jour périodique...");
            manageLogFiles();
            removeExpiredDevoirs();
            sortDevoirs();
            UpdateJsonData();
            await checkUpcoming();
          } catch (intervalError) {
            logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur est survenue lors de la mise à jour périodique : ${intervalerror}`);
          }
        }, 86400000); // 24 heures en millisecondes
    }, delay);
    logMessage("[\x1b[92m SUCCESS \x1b[0m] Mise en place de la mise à jour effectuée avec succès !\n");
  } catch (error) {
    logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite dans la planification de la mise à jour automatique : ${error}`);
  }
}

/**
 * Initialise et configure le bot Discord.
 *
 * Cette fonction réalise plusieurs étapes pour préparer et démarrer le bot :
 * - Vérifie et crée les répertoires nécessaires (logs et données).
 * - Vérifie que le token Discord est défini dans les variables d'environnement.
 * - Déploie les commandes sur le serveur Discord.
 * - Tente de connecter le bot au serveur Discord et gère les erreurs de connexion avec des tentatives de réessai.
 * 
 * ### Étapes :
 * 1. **Vérification des répertoires nécessaires** :
 *    - Vérifie si les répertoires de logs et de données existent, et les crée si nécessaire.
 *    - En cas d'erreur, le processus est arrêté.
 * 
 * 2. **Vérification du token Discord** :
 *    - Si le token n'est pas trouvé dans les variables d'environnement, l'application s'arrête.
 *
 * 3. **Déploiement des commandes sur Discord** :
 *    - Tente de déployer les commandes à l'aide de l'API de Discord.
 *    - Si le déploiement échoue, le processus est arrêté.
 *
 * 4. **Connexion au bot Discord** :
 *    - Tente de connecter le bot à Discord. Si la connexion échoue, elle sera tentée à nouveau toutes les 5 secondes.
 *    - Si la connexion échoue après plusieurs tentatives, le programme s'arrête.
 * 
 * ### Exemples :
 *
 * ```javascript
 * initializeBot();
 * ```
 *
 * ### Paramètres :
 * Aucuns.
 *
 * ### Retour :
 * Aucune valeur n'est retournée. Les actions sont effectuées de manière asynchrone avec des logs indiquant l'état des différentes étapes.
 *
 * ### Erreurs :
 * - Si une erreur survient lors de l'une des étapes (répertoires, token, déploiement des commandes, connexion), un message d'erreur est loggé et le programme est arrêté avec `process.exit(1)`.
 */
function initializeBot() {
  logMessage("[\x1b[93m NOTICE \x1b[0m] Initialisation du bot en cours... \n");
  const rest = new REST({ version: "10" }).setToken(process.env.DISCORD_TOKEN);
  try {
    // Créer les dossiers s'ils n'existent pas
    try {
      logMessage(`[\x1b[93m NOTICE \x1b[0m] Vérification de l'existence des répertoires nécessaires en cours... `);
      [config.directories.logDirectory, config.directories.dataDirectory].forEach((dir) => {
        if (!fs.existsSync(dir)) {
          fs.mkdirSync(dir, { recursive: true });  // Utiliser { recursive: true } pour éviter les erreurs si le répertoire parent existe déjà
          logMessage(`[\x1b[92m SUCCESS \x1b[0m] Répertoire '${dir}' créé avec succès.`);
        } else {
          logMessage(`[\x1b[92m SUCCESS \x1b[0m] Le répertoire '${dir}' existe déjà.`);
        }
      });
    } catch (dirError) {
      logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur lors de la création des répertoires : ${dirError}`);
      client.destroy();
      process.exit(1);  // Sortir du programme si erreur dans la création des dossiers
    }

    // Recherche du process.env.DISCORD_TOKEN
    if (!process.env.DISCORD_TOKEN) {
      logMessage("   [\x1b[91m ERROR \x1b[0m] Le token n'est pas défini dans les variables d'environnement.");
      client.destroy();
      process.exit(1);
    }

    // Déploiement des commandes
    try {
      logMessage("[\x1b[93m NOTICE \x1b[0m] Tentative de déploiement des commandes en cours...");
      rest.put(Routes.applicationGuildCommands(config.ids.clientId, config.ids.guildId), { body: JSON.parse(fs.readFileSync('commands.json', 'utf-8')) })
        .then(() => {
          logMessage("[\x1b[92m SUCCESS \x1b[0m] Commandes déployées avec succès !");
        })
        .catch((deployError) => {
          logMessage(`   [\x1b[91m ERROR \x1b[0m] Échec du déploiement des commandes : ${deployError}`);
          client.destroy();
          process.exit(1);  // Sortir du programme si déploiement des commandes échoue
        });
    } catch (deployError) {
      logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur inattendue s'est produite lors du déploiement des commandes : ${deployError}`);
      client.destroy();
      process.exit(1);
    }

    // Tentative de connexion
    async function attemptLogin() {
      logMessage("[\x1b[93m NOTICE \x1b[0m] Tentative de connexion en cours...");
      while (true) {
        try {
          await client.login();
          logMessage("[\x1b[92m SUCCESS \x1b[0m] Bot connecté avec succès !\n");
          break;  // Connexion réussie, sortir de la boucle
        } catch (loginError) {
          logMessage(`   [\x1b[91m ERROR \x1b[0m] Erreur de connexion, réessai dans 5 secondes... ${loginError} `);
          await new Promise(resolve => setTimeout(resolve, 5000));  // Attendre 5 secondes avant de réessayer
        }
      }
    }

    attemptLogin().catch((loginAttemptError) => {
      logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur est survenue lors de la tentative de connexion : ${loginAttemptError}`);
      client.destroy();
      process.exit(1);  // Sortir si une erreur grave survient pendant la connexion
    });

  } catch (error) {
    logMessage(`   [\x1b[91m ERROR \x1b[0m] Une erreur inattendue s'est produite : ${error}`);
    client.destroy();
    process.exit(1);  // Sortir du programme si une erreur non gérée se produit
  }
}


// Demarrage du bot
initializeBot();

