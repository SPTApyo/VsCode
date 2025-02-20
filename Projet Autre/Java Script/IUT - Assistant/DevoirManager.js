const fs = require("fs");
const path = require("path");
const moment = require("moment");

class DevoirManager {
  constructor(group) {
    this.group = group;
    this.filePath = path.join(__dirname, 'data', `devoirs_${group}.json`);
    this.devoirs = this.loadDevoirs();
    this.saveInterval = setInterval(() => this.saveDevoirs(), 120000); // Save every 120 seconds
  }

  static loadAllDevoirManagers() {
    const dataDirectory = path.join(__dirname, 'data');
    const files = fs.readdirSync(dataDirectory).filter(file => file.startsWith('devoirs_') && file.endsWith('.json'));
    return files.map(file => {
      const group = file.replace('devoirs_', '').replace('.json', '');
      return new DevoirManager(group);
    });
  }

  loadDevoirs() {
    if (fs.existsSync(this.filePath)) {
      return JSON.parse(fs.readFileSync(this.filePath, 'utf8'));
    } else {
      return [];
    }
  }

  saveDevoirs() {
    fs.writeFileSync(this.filePath, JSON.stringify(this.devoirs, null, 2));
  }

  addDevoir(devoir) {
    // Vérifier si le devoir existe déjà
    const existingDevoir = this.devoirs.find(d => 
      d.devoir === devoir.devoir && d.matiere === devoir.matiere
    );

    if (existingDevoir) {
      // Mettre à jour la date du devoir existant
      existingDevoir.date = devoir.date;
      this.saveDevoirs();
      return existingDevoir; // Retourner le devoir mis à jour
    }

    devoir.id = this.generateUniqueId();
    this.devoirs.push(devoir);
    this.saveDevoirs();
    return devoir; // Retourner le devoir ajouté
  }

  deleteDevoir(id) {
    const index = this.devoirs.findIndex(d => d.id === id);
    if (index !== -1) {
      this.devoirs.splice(index, 1);
      this.saveDevoirs();
      return true; // Retourner true si le devoir a été supprimé
    }
    return false; // Retourner false si le devoir n'a pas été trouvé
  }

  removeExpiredDevoirs() {
    const now = moment().startOf("day");
    this.devoirs = this.devoirs.filter((d) => {
      const dueDate = moment(d.date, "DD-MM-YYYY");
      return dueDate.isSameOrAfter(now.subtract(1, "day"));
    });
    this.saveDevoirs();
  }

  sortDevoirs() {
    this.devoirs.sort((a, b) => moment(a.date, "DD-MM-YYYY").diff(moment(b.date, "DD-MM-YYYY")));
    this.saveDevoirs();
  }

  regenerateIds() {
    this.devoirs.forEach((devoir, index) => {
      devoir.id = index;
    });
    this.saveDevoirs();
  }

  removeDuplicates() {
    const seen = new Set();
    this.devoirs = this.devoirs.filter(devoir => {
      const uniqueKey = `${devoir.devoir}-${devoir.matiere}`;
      if (seen.has(uniqueKey)) {
        return false;
      }
      seen.add(uniqueKey);
      return true;
    });
    this.saveDevoirs();
  }

  generateUniqueId() {
    try {
      let id = 0; // Point de départ pour les IDs
      const existingIds = new Set(this.devoirs.map((devoir) => devoir.id)); // Collecte des IDs existants

      while (existingIds.has(id)) {
        id++; // Incrémente jusqu'à trouver un ID unique
      }

      return id;
    } catch (error) {
      console.error(`   [\x1b[91m ERROR \x1b[0m] Problème lors de la génération d'un ID unique : ${error}`);
      return null; // Retourne null en cas d'erreur
    }
  }

  static applyToAll(methodName) {
    const managers = DevoirManager.loadAllDevoirManagers();
    managers.forEach(manager => {
      if (typeof manager[methodName] === 'function') {
        manager[methodName]();
      }
    });
  }
}

module.exports = { DevoirManager };