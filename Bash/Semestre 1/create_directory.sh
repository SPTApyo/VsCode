#!/bin/bash 

if [ $# -ne 1 ]; then
        echo "Erreur : Nombre de paramètres erroné."
        exit 1
fi 

nom_repertoire="$1"

if [ -e "$nom_repertoire" ]; then
        echo "Erreur : Fichier deja existant"
        exit 2
fi

if mkdir "$nom_repertoire" 2>/dev/null ; then
        echo "Le fichier de type répertoire '$nom_repertoire' à été crée."
else
        echo "Erreur : Impossible de crée le fichier de typé répertoire '$nom_repertoire'."
        exit 3 
fi