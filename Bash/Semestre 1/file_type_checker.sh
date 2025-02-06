#!/bin/bash

if [ $# -ne 1 ]; then
        echo "Erreur : Nombre de paramètres erroné."
        exit 1
fi 

# Récupérer le paramètre
nom_fichier="$1"

# Vérifier si c'est un fichier ou un répertoire
if [ -d "$nom_fichier" ]; then
    echo "$nom_fichier est un fichier de type répertoire"
elif [ -f "$nom_fichier" ]; then
    # Vérifier si le fichier est exécutable
    if [ -x "$nom_fichier" ]; then
        echo "$nom_fichier est un fichier ordinaire exécutable"
    else
        echo "$nom_fichier est un fichier ordinaire"
    fi
else
    echo "Erreur : '$nom_fichier' n'existe pas ou n'est ni un fichier ni un répertoire."
    exit 2
fi