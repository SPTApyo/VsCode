#!/bin/bash

# Vérification que le script n'a pas d'arguments
if [ "$#" -ne 0 ]; then
    echo "Erreur : Ce script ne prend pas d'arguments."
    exit 1
fi

# Chemin du fichier historique
HISTORY_FILE="$HOME/history.txt"

# Gestion de l'historique précédent
if [ -f "$HISTORY_FILE" ]; then
    while true; do
        echo "Un fichier historique existe déjà. Veuillez saisir un nom pour le renommer :"
        read -r NEW_NAME
        NEW_FILE="$HOME/$NEW_NAME"

        if [ ! -e "$NEW_FILE" ]; then
            mv "$HISTORY_FILE" "$NEW_FILE"
            echo "Fichier historique renommé en $NEW_FILE."
            break
        else
            echo "Erreur : Le fichier $NEW_FILE existe déjà. Essayez un autre nom."
        fi
    done
fi

# Boucle principale pour saisir des commandes
COMMAND_COUNT=0

while true; do
    echo -n "Entrez une commande (ou 'Q'/'q' pour quitter) : "
    read -r COMMAND

    # Vérification si l'utilisateur souhaite quitter
    if [ "$COMMAND" = "Q" ]  | [ "$COMMAND" = "q" ]; then
        break 
    fi

    # Enregistrement de la commande dans le fichier historique
    echo "$COMMAND" >> "$HISTORY_FILE"
    COMMAND_COUNT=$((COMMAND_COUNT + 1))

done

# Affichage du nombre de commandes enregistrées
if [ -f "$HISTORY_FILE" ]; then
    echo "Nombre de commandes enregistrées dans $HISTORY_FILE : $COMMAND_COUNT"
    echo "Contenu du fichier :"
    cat $HISTORY_FILE
else
    echo "Aucune commande n'a été enregistrée."
fi

exit 0