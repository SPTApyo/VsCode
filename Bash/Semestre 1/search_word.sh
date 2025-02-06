#!/bin/bash 

if [ $# -ne 2 ]; then
        echo "Erreur : Nombre de paramètres erroné."
        exit 1
fi 


recherche_mot="$1"
fichier_recherche="$2"

# Vérifier si c'est un fichier
if [ -f "$fichier_recherche" ]; then
    if grep -c "$recherche_mot" "$fichier_recherche" > /dev/null ; then
        echo "Le fichier "$fichier_recherche" contient le mot "$recherche_mot""
    else
        echo "Le fichier "$fichier_recherche" ne contient pas le mot "$recherche_mot""
fi
else
    echo "Erreur : '$fichier_recherche' n'existe pas ou n'est n'est pas un fichier ordinaire."
    exit 2
fi