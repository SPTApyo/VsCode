#!/bin/bash
#analyse uniquement les fichiers spécifiés en argument

if [ $# -lt 2 ]; then
    echo "Erreur : Au moins deux parametres attendus."
    exit 1
fi

recherche_mot="$1"
shift 

for fichier in "$@"; do
    if [ -f "$fichier" ]; then
        if grep -q "$recherche_mot" "$fichier"; then
            echo "Le fichier \"$fichier\" contient le mot \"$recherche_mot\"."
        else
            echo "Le fichier \"$fichier\" ne contient pas le mot \"$recherche_mot\"."
        fi
    else
        echo "Erreur : \"$fichier\" n'est pas un fichier ordinaire."
    fi
done