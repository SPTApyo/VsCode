#!/bin/bash

# Initialisation du compteur
compteur=0

while true; do
    # Afficher le menu
    echo
    echo "1) Changer de repertoire"
    echo "2) Creer un repertoire"
    echo "3) Liste et nombre de fichiers du repertoire courant"
    echo "4) Afficher le contenu d'un fichier texte"
    echo "5) Lancer un programme executable"
    echo "6) Quitter"
    echo
    read -p "Choisissez une option : " choix
    echo

    # Incrémenter le compteur
    compteur=$((compteur + 1))

    case $choix in
        1)
            read -p "Entrez le chemin du repertoire : " chemin
            if [ -d "$chemin" ]; then
                cd "$chemin" || exit
                echo "Repertoire change en $(pwd)"
            else
                echo "Erreur : Repertoire inexistant."
                exit 1
            fi
            ;;
        2)
            read -p "Entrez le nom du repertoire a creer : " nom_dossier
            if mkdir "$nom_dossier" 2>/dev/null; then
                echo "Repertoire $nom_dossier cree."
            else
                echo "Erreur : Impossible de creer le repertoire."
                exit 2
            fi
            ;;
        3)
            echo "Liste des fichiers :"
            if ! ls; then
              echo "Erreur : Impossible de lister les fichiers."
              exit 3
            fi

            nb_fichiers=$(ls 2>/dev/null | wc -l)
            echo "Nombre de fichiers/répertoires : $nb_fichiers"
            ;;
        4)
            read -p "Entrez le nom du fichier texte a afficher : " fichier
            if [ -f "$fichier" ]; then
                cat "$fichier" || echo "Erreur : Impossible d'afficher le fichier."
            else
                echo "Erreur : Fichier inexistant ou non valide."
                exit 4
            fi
            ;;
        5)
            read -p "Entrez le nom du programme a executer : " programme
            if [ -x "$programme" ]; then
                ./"$programme" || echo "Erreur : echec de l'execution du programme."
            else
                echo "Erreur : Fichier non executable ou inexistant."
                exit 5
            fi
            ;;
        6)
	    compteur=$((compteur - 1))
            echo "Nombre total d'appels au menu : $compteur"
            echo "Au revoir !"
            exit 0
            ;;
        *)
            echo "Option invalide. Veuillez reessayer."
            ;;
    esac
done
