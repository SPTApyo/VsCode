<script>
      // Code pour afficher l'adresse IP publique du Raspberry Pi (inchangé)
      const publicIpAddressElement = document.getElementById("public-ip-address");
      fetch("https://api.ipify.org?format=json").then(response => response.json()).then(data => {
        const publicIpAddress = data.ip;
        publicIpAddressElement.textContent = publicIpAddress;
      }).catch(error => {
        console.error("Erreur lors de la récupération de l'adresse IP publique :", error);
        publicIpAddressElement.textContent = "Non disponible";
      });
      // Vérification du statut du site
      function checkSiteStatus(url, statusElement) {
        fetch(url, {
          method: "HEAD"
        }).then(response => {
          if (response.ok) {
            statusElement.classList.add("status-dot-green");
          } else {
            statusElement.classList.add("status-dot-red");
          }
        }).catch(error => {
          statusElement.classList.add("status-dot-red");
        });
      }
      // Vérification du statut pour chaque site
      const monitoringStatusElement = document.getElementById("status-monitoring");
      const dnsStatusElement = document.getElementById("status-dns");
      // Obtenez automatiquement l'adresse du site en fonction de l'accès
      const urlRaspberryPiLocal = window.location.origin;
      checkSiteStatus(urlRaspberryPiLocal, monitoringStatusElement);
      checkSiteStatus(urlRaspberryPiLocal, dnsStatusElement);
    </script>