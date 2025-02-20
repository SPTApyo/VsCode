#!/bin/bash
(
echo "\033[1;36mLancement de la réparation des packages Raspberry Pi \033[0m"
echo "\033[1;32mPremière étape \033[0m"
sudo apt --fix-missing update -y 
echo "\033[1;32mDeuxième étape \033[0m"
sudo apt install -f
echo "\033[1;32mTroisième étape \033[0m"
sudo apt --fix-broken install
echo "\033[1;36mLancement de la mise a jour du Raspberry Pi \033[0m"
sudo apt update && sudo apt full-upgrade -y 
echo "\033[1;36mLancement de la mise a jour Pihole \033[0m"
sudo pihole -up
echo "\033[1;36mLancement de la mise a jour Pihole\033[0m \033[1;31m(ADLIST)\033[0m"
sudo pihole -g
echo "\033[1;36mLancement du nettoyage du Raspberry Pi \033[0m"
sudo apt autoremove -y
sudo apt autoclean -y
echo "\033[1;36mLancement de la Backup Pihole \033[0m"
cd /home/shares/Private/Sauvegardes/PiholesBackup
sudo pihole -a -t
cd
echo "\033[1;36mRedémarrage Raspberry Pi dans 10 secondes \033[0m"
) 2>&1 | tee home/Grace/logs/Updatelog
sleep 10s
sudo reboot
