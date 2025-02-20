const fs = require("fs");
const moment = require("moment");
const config = require('./config');

class Logger {
  constructor() {
    this.logFileName = `${config.directories.logDirectory}/log_${moment().format("YYYY-MM-DD_HH-mm-ss")}.txt`;
  }

  async logMessage(message) {
    try {
      // Ouvrir le fichier log en mode append
      const logStream = fs.createWriteStream(this.logFileName, { flags: "a" });

      if (message == "") {
        console.log("");
        logStream.write("\n");
      } else {
        const timestamp = moment().format("YYYY/MM/DD HH:mm:ss");
        message = `${timestamp} - > ${message}`;  // Préfixe ">" pour distinguer facilement les messages de log

        // Affichage dans la console
        if (message.includes("ERREUR")) {
          console.error(`\x1b[0m${message}`);  // Utilise console.error si le message contient "ERREUR"
        } else if (message.includes("NOTICE")) {
          console.warn(`\x1b[0m${message}`);  // Utilise console.warn si le message contient "INFO"
        } else {
          console.log(`${message}`);  // Sinon, utilise console.log
        }

        // Supprimer les codes de couleur avant de les écrire dans le fichier de log
        const cleanMessage = message.replace(/\x1b\[\d+m/g, "");

        // Ajout dans le fichier de log
        logStream.write(`${cleanMessage}\n`);
      }

      // Fermer le fichier log
      logStream.end();
    } catch (error) {
      console.error(`   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de l'écriture dans les logs : ${error}`);
    }
  }

  manageLogFiles() {
    this.logMessage(
      " [\x1b[93m NOTICE \x1b[0m] Vérification des fichiers de log excédentaires en cours..."
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
          this.logMessage(
            `[\x1b[92m SUCCESS \x1b[0m] Suppression du fichier de log excédentaire : ${file}`
          );
        } catch (unlinkError) {
          this.logMessage(
            `   [\x1b[91m ERROR \x1b[0m] Impossible de supprimer le fichier ${file} : ${unlinkError}`
          );
        }
      });
    } catch (error) {
      this.logMessage(
        `   [\x1b[91m ERROR \x1b[0m] Une erreur s'est produite lors de la gestion des fichiers de log : ${error}`
      );
    }
  }
}

module.exports = Logger;
