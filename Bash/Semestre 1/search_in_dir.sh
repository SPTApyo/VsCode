#!/bin/bash 
#parcourt tous les fichiers du répertoire courant

if [ $# -ne 1 ]; then
    echo "Erreur : Un seul paramètre attendu (le mot à rechercher)."
    exit 1
fi 

recherche_mot="$1"

# Parcourir tous les fichiers du répertoire courant
for fichier in *; do
    # Vérifier si c'est un fichier ordinaire
    if [ -f "$fichier" ]; then
        if grep -q "$recherche_mot" "$fichier"; then
            echo "Le fichier \"$fichier\" contient le mot \"$recherche_mot\"."
        else
            echo "Le fichier \"$fichier\" ne contient pas le mot \"$recherche_mot\"."
        fi
    fi
done
exit 0

