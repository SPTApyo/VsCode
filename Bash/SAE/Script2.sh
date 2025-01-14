#!/bin/bash

if [ $# -ne 0 ]; then
    echo "Erreur : Nombre de paramètres erroné."
    exit 1
fi

# Fonctions pour chaque option (case)
#
# Code d'erreur :La dernière valeur dans le code d'erreur désigne l'ordre dans la fonction
#                   les premières valeurs désigne la fonction dans l'ordre du code
#
#   exemple : exit 11 designe la premiere fonction et le premier code d'erreur
#                12 designe la premiere fonction et deuxieme code d'erreur
#                       112 designe la 11 eme fonction et deuxieme code d'erreur

# CASE 1
saisir_repertoire() {
    read -p "Entrez le chemin du répertoire contenant les fichiers source : " chemin
    
    # Vérification si l'entrée est vide
    if [ -z "$chemin" ]; then
        echo "Erreur : Aucun chemin de répertoire fourni."
        exit 11
    fi
    
    # Vérification si le chemin pointe vers un répertoire existant
    if [ -d "$chemin" ]; then
        # Vérification des permissions
        if [ -r "$chemin" ] && [ -w "$chemin" ] && [ -x "$chemin" ]; then
            REP="$chemin"
            echo "Répertoire défini : $REP"
        else
            echo "Erreur : Vous n'avez pas les droits nécessaires pour accéder à ce répertoire."
            echo "Vérifiez que le répertoire est lisible, modifiable et exécutable."
            exit 12
        fi
    else
        # Vérification si c'est un fichier ou un chemin inexistant
        if [ -f "$chemin" ]; then
            echo "Erreur : Le chemin fourni pointe vers un fichier, pas un répertoire."
            exit 13
        else
            echo "Erreur : Répertoire inexistant."
            exit 14
        fi
    fi
}

# CASE 2
lister_fichiers() {
    # Vérifier si la variable REP est définie et non vide
    if [ -z "$REP" ]; then
        echo "Erreur : La variable REP n'est pas définie. Veuillez d'abord définir un répertoire."
        exit 21
    fi
    
    # Vérifier si REP pointe vers un répertoire existant
    if [ -d "$REP" ]; then
        # Vérifier si le répertoire est lisible
        if [ -r "$REP" ]; then
            echo "Fichiers dans $REP :"
            ls -l "$REP"
        else
            echo "Erreur : Le répertoire $REP n'est pas accessible en lecture. Vérifiez les permissions."
            exit 22
        fi
    else
        echo "Erreur : Répertoire REP inexistant ou incorrectement défini."
        exit 23
    fi
}


# CASE 3
afficher_contenu_fichier() {
    # Vérifier si la variable REP est définie et non vide
    if [ -z "$REP" ]; then
        echo "Erreur : La variable REP n'est pas définie. Veuillez d'abord définir un répertoire."
        exit 31
    fi
    
    # Demander le nom du fichier
    read -p "Entrez le nom du fichier dans $REP : " fichier
    
    # Vérifier si le fichier existe et est un fichier régulier
    if [ -f "$REP/$fichier" ]; then
        # Vérifier si le fichier est lisible
        if [ -r "$REP/$fichier" ]; then
            echo "Contenu de $REP/$fichier :"
            cat "$REP/$fichier"
            echo
        else
            echo "Erreur : Le fichier $fichier dans $REP n'est pas accessible en lecture. Vérifiez les permissions."
            exit 32
        fi
    else
        echo "Erreur : Fichier $fichier introuvable dans $REP."
        exit 33
    fi
}

# CASE 4
afficher_tous_fichiers() {
    # Vérifier si la variable REP est définie et non vide
    if [ -z "$REP" ]; then
        echo "Erreur : La variable REP n'est pas définie. Veuillez d'abord définir un répertoire."
        exit 41
    fi
    
    # Vérifier si le répertoire existe et est un répertoire
    if [ -d "$REP" ]; then
        # Vérifier si le répertoire est accessible en lecture
        if [ -r "$REP" ]; then
            # Vérifier s'il y a des fichiers dans le répertoire
            fichiers=$(ls "$REP"/* 2>/dev/null)
            if [ -z "$fichiers" ]; then
                echo "Aucun fichier trouvé dans le répertoire $REP."
                exit 42
            fi
            
            # Afficher le contenu de chaque fichier trié
            for fichier in $(ls "$REP"/* | sort -t'^' -k2,2n); do
                if [ -f "$fichier" ]; then
                    echo "=== Contenu de $fichier ==="
                    cat "$fichier"
                    echo
                fi
            done
        else
            echo "Erreur : Le répertoire $REP n'est pas accessible en lecture."
            exit 43
        fi
    else
        echo "Erreur : Répertoire $REP non défini ou inexistant."
        exit 44
    fi
}


# CASE 5
compter_fichiers_rep() {
    # Vérifier si la variable REP est définie et non vide
    if [ -z "$REP" ]; then
        echo "Erreur : La variable REP n'est pas définie. Veuillez d'abord définir un répertoire."
        exit 51
    fi
    
    # Vérifier si le répertoire existe
    if [ -d "$REP" ]; then
        # Vérifier si le répertoire est accessible en lecture
        if [ -r "$REP" ]; then
            # Compter les fichiers dans le répertoire principal (en excluant les répertoires)
            nb_fichiers_principal=$(find "$REP" -maxdepth 1 -type f | wc -l)
            echo "Nombre de fichiers dans $REP : $nb_fichiers_principal"
            
            # Compter les fichiers dans les sous-répertoires (en excluant les répertoires)
            nb_fichiers_sousrep=$(find "$REP" -mindepth 2 -type f | wc -l)
            echo "Nombre de fichiers dans les sous-répertoires de $REP : $nb_fichiers_sousrep"
            
            # Calculer le total
            total_fichiers=$((nb_fichiers_principal + nb_fichiers_sousrep))
            echo "Nombre total de fichiers de $REP : $total_fichiers"
        else
            echo "Erreur : Le répertoire $REP n'est pas accessible en lecture."
            exit 52
        fi
    else
        echo "Erreur : Répertoire $REP non défini ou inexistant."
        exit 53
    fi
}


# CASE 6
saisir_parametres_decoupage() {
    # Demander à l'utilisateur de saisir un nombre
    read -p "Entrez un nombre entre 1 et 30 pour le découpage : " nb
    
    # Vérifier si l'entrée est un nombre entier valide
    if [[ ! "$nb" =~ ^[0-9]+$ ]]; then
        echo "Erreur : La valeur saisie n'est pas un nombre valide."
        exit 61
    fi
    
    # Vérifier si le nombre est dans l'intervalle de 1 à 30
    if [ "$nb" -ge 1 ] && [ "$nb" -le 30 ]; then
        echo "Paramètre de découpage défini : $nb"
    else
        echo "Erreur : Valeur hors intervalle (1-30)."
        exit 62
    fi
}

# CASE 7
creer_repertoire_save() {
    # Demander à l'utilisateur d'entrer le nom du répertoire SAVE
    read -p "Entrez le nom du répertoire SAVE : " SAVE
    
    # Vérifier si le nom du répertoire est vide
    if [ -z "$SAVE" ]; then
        echo "Erreur : Le nom du répertoire ne peut pas être vide."
        exit 71
    fi
    
    # Essayer de créer le répertoire
    if mkdir -p "$SAVE"; then
        # Vérifier si le répertoire a bien été créé
        if [ -d "$SAVE" ]; then
            echo "Répertoire $SAVE créé."
        else
            echo "Erreur : Le répertoire n'a pas pu être créé malgré la tentative."
            exit 72
        fi
    else
        echo "Erreur : Impossible de créer $SAVE."
        exit 73
    fi
}


# CASE 8
decouper_fichiers() {
    # Vérifier si les répertoires REP et SAVE sont définis et existent
    if [ -d "$REP" ] && [ -d "$SAVE" ]; then
        # Vérifier si REP contient des fichiers
        if [ -z "$(ls -A "$REP")" ] 2>/dev/null; then
            echo "Erreur : Aucun fichier trouvé dans $REP."
            exit 81
        fi
        if [ -z "$nb" ] || [ "$nb" -lt 1 ] || [ "$nb" -gt 30 ] 2>/dev/null; then
            echo "Erreur : La variable 'nb' n'est pas définie ou sa valeur doit être entre 1 et 30."
            exit 82
        fi
        
        for fichier in "$REP"/*; do
            if [ -f "$fichier" ]; then
                base_name=$(basename "$fichier")
                echo "Découpage de $base_name en cours..."
                
                # Mettre tout le contenu du fichier en mémoire
                contenu=$(<"$fichier")
                
                # Vérifier si le fichier est vide
                if [ -z "$contenu" ]; then
                    echo "Avertissement : Le fichier $fichier est vide. Découpage ignoré."
                    continue
                fi
                
                # Supprimer les retours à la ligne, tabulations et remplacer par un seul espace
                contenu_sans_ligne=$(echo "$contenu" | tr -s '\n\t ' ' ')
                
                # Retirer les espaces supplémentaires en début et fin
                contenu_sans_ligne=$(echo "$contenu_sans_ligne" | sed 's/^ *//;s/ *$//')
                
                # Extraire uniquement les "mots" valides
                mots=($(echo "$contenu_sans_ligne" | grep -oE '\S+'))  # Sépare les mots selon les espaces
                
                total_mots=${#mots[@]}  # Nombre total de mots dans le fichier
                
                # Vérifier s'il y a des mots à découper
                if [ "$total_mots" -eq 0 ]; then
                    echo "Avertissement : Aucun mot valide trouvé dans $fichier. Découpage ignoré."
                    continue
                fi
                
                # Découper les mots en paquets de nb mots
                compteur=1
                for ((i = 0; i < total_mots; i += nb)); do
                    # Créer un fichier pour chaque découpage, ^ comme char de séparation nom/indice pour éviter les erreurs
                    partie="${SAVE}/${base_name}^${compteur}"
                    # Extrait un sous-ensemble de nb mots ou les mots restants
                    echo "${mots[@]:i:nb}" > "$partie"
                    echo "Fichier créé : $partie"
                    compteur=$((compteur + 1))
                done
            fi
        done
        echo "Découpage terminé. Tous les fichiers sont enregistrés dans $SAVE."
    else
        echo "Erreur : Répertoires REP ou SAVE non définis ou inexistant."
        exit 83
    fi
}


# CASE 9
afficher_nombre_mots() {
    # Vérifier si la variable nb est définie et contient une valeur numérique valide
    if [[ -n "$nb" && "$nb" =~ ^[0-9]+$ && "$nb" -ge 1 && "$nb" -le 30 ]]; then
        echo "Nombre de mots par découpage : $nb"
    else
        echo "Erreur : Le paramètre de découpage (nb) n'est pas défini ou est invalide."
        echo "Veuillez définir une valeur entre 1 et 30 pour le paramètre de découpage."
        exit 91
    fi
}

# CASE 10
lister_fichiers_save() {
    # Vérifier si le répertoire SAVE existe
    if [ -d "$SAVE" ]; then
        # Vérifier si le répertoire SAVE contient des fichiers
        if [ -z "$(ls -A "$SAVE")" ]; then
            echo "Aucun fichier découpé trouvé dans $SAVE."
        else
            echo "Fichiers découpés dans $SAVE :"
            ls -l "$SAVE"
        fi
    else
        echo "Erreur : Répertoire SAVE inexistant."
        exit 101
    fi
}

# CASE 11
compter_fichiers_save() {
    if [ -d "$SAVE" ]; then
        # Compter les fichiers dans le répertoire SAVE
        nb_decoupes=$(find "$SAVE" -type f | wc -l)
        
        # Afficher le nombre de fichiers découpés, ou un message si aucun fichier n'a été trouvé
        if [ "$nb_decoupes" -eq 0 ]; then
            echo "Aucun fichier découpé trouvé dans $SAVE."
        else
            echo "Nombre de fichiers découpés dans $SAVE : $nb_decoupes"
        fi
    else
        echo "Erreur : Répertoire SAVE inexistant."
        exit 111
    fi
}

# CASE 12
afficher_contenu_save() {
    read -p "Entrez le nom du fichier dans $SAVE : " fichier
    
    # Vérifier si le fichier existe dans le répertoire SAVE
    if [ -f "$SAVE/$fichier" ]; then
        echo "Contenu de $SAVE/$fichier :"
        cat "$SAVE/$fichier"
        echo
    else
        echo "Erreur : Fichier introuvable dans $SAVE."
        exit 121
    fi
}

# CASE 13
afficher_premiere_ligne_save() {
    if [ -d "$SAVE" ]; then
        # Boucle à travers les fichiers dans le répertoire SAVE, triés par nom
        for fichier in $(ls "$SAVE"/* | sort -t'^' -k2,2n); do
            if [ -f "$fichier" ]; then
                echo "=== Première ligne de $fichier ==="
                head -n 1 "$fichier"  # Afficher la première ligne
                echo
            fi
        done
    else
        echo "Erreur : Répertoire SAVE inexistant."
        exit 131
    fi
}

# CASE 14
afficher_derniere_ligne_save() {
    if [ -d "$SAVE" ]; then
        # Boucle à travers les fichiers dans le répertoire SAVE, triés par nom
        for fichier in $(ls "$SAVE"/* | sort -t'^' -k2,2n); do
            if [ -f "$fichier" ]; then
                echo "=== Dernière ligne de $fichier ==="
                tail -n 1 "$fichier"  # Afficher la dernière ligne
                echo
            fi
        done
    else
        echo "Erreur : Répertoire SAVE inexistant."
        exit 141
    fi
}


# CASE 15
reconstituer_fichier() {
    read -p "Entrez le nom du fichier source à reconstituer (sans extension) : " nom_fichier
    if [ -z "$nom_fichier" ]; then
        echo "Erreur : Aucun nom de fichier spécifié."
        exit 151
    fi
    fichiers_correspondants=($SAVE/${nom_fichier}^*)
    if [ ${#fichiers_correspondants[@]} -eq 0 ]; then
        echo "Erreur : Aucun fichier correspondant à $nom_fichier dans $SAVE."
        exit 152
    fi
    if [ -d "$SAVE" ]; then
        echo "Reconstitution du fichier $nom_fichier..."
        contenu_complet=""
        
        # Rassembler les sous-parties de fichiers dans SAVE
        if [ -d "$SAVE" ]; then
            for partie in $(ls "$SAVE"/"${nom_fichier}"^* | sort -t'^' -k2,2n); do
                if [ -f "$partie" ]; then
                    contenu_complet+=$(cat "$partie")
                    contenu_complet+=" "
                fi
            done
        else
            echo "Erreur : Répertoire SAVE inexistant."
            return exit 153
        fi
        
        # Afficher le contenu reconstitué
        echo 
        echo "=== Reconstitution $nom_fichier ==="
        echo
        echo "$contenu_complet"
        echo
        echo "==================================="
        echo

        # Demander si l'utilisateur veut sauvegarder le fichier reconstitué
        read -p "Souhaitez-vous enregistrer ce fichier ? (oui/non) : " reponse
        if [[ "$reponse" == "oui" || "$reponse" == "o" ]]; then
            read -p "Quel répertoire souhaitez-vous créer/utiliser pour enregistrer la reconstitution : " VERIF
            if [ ! -d "$VERIF" ]; then
                mkdir -p "$VERIF"
            fi
            
            # Demander à l'utilisateur le nom du fichier à enregistrer
            read -p "Entrez le nom du fichier à créer dans $VERIF : " nom_fichier_verif
            echo "$contenu_complet" > "$VERIF/$nom_fichier_verif"
            echo "Fichier enregistré dans $VERIF/$nom_fichier_verif"
        fi
    else
        echo "Erreur : Répertoire SAVE inexistant."
        exit 155
    fi
}

# CASE 16
nettoyer() {
    if [ -d "$SAVE" ]; then
        if [ -r "$SAVE" ] && [ -w "$SAVE" ]; then
            rm -rf "$SAVE"/*
            echo "Contenu de SAVE supprimé."
        else
            echo "Erreur : Vous n'avez pas les droits nécessaires pour supprimer dans le répertoire SAVE."
            exit 161
        fi
    else
        echo "Erreur : Répertoire SAVE inexistant."
        exit 162
    fi
    
    if [ -d "$VERIF" ]; then
        if [ -r "$VERIF" ] && [ -w "$VERIF" ]; then
            rm -rf "$VERIF"/*
            echo "Contenu de VERIF supprimé."
        else
            echo "Erreur : Vous n'avez pas les droits nécessaires pour supprimer dans le répertoire VERIF."
            exit 163
        fi
    else
        echo "Erreur : Répertoire VERIF inexistant."
        exit 164
    fi
}

# CASE 17
creer_fichiers_source() {
    if [ ! -d "$REP" ]; then
        echo "Erreur : Répertoire REP inexistant ou non défini."
        exit 171
    fi
    
    read -p "Combien de fichiers source voulez-vous créer ? " nb_fichiers
    if [[ "$nb_fichiers" =~ ^[0-9]+$ ]] && [ "$nb_fichiers" -gt 0 ]; then
        for ((i = 1; i <= nb_fichiers; i++)); do
            read -p "Entrez le texte à écrire dans le fichier $i : " texte
            nom_fichier="fichier_source_$i.txt"
            
            if echo "$texte" > "$REP/$nom_fichier"; then
                echo "Fichier $nom_fichier créé dans $REP."
            else
                echo "Erreur : Impossible de créer le fichier $nom_fichier dans $REP."
                exit 172
            fi
        done
    else
        echo "Erreur : Veuillez entrer un nombre valide de fichiers."
        exit 173
    fi
}


# CASE 18
breakfunc() {
    echo "Fin du script"
    exit 0
}

main() {
    echo "Bienvenue en S.A.E, nous sommes l’équipe 7"
    echo
    # Menu principal
    while true; do
        echo "=============================================="
        echo "|                    MENU                    |"
        echo "=============================================="
        echo ">  1) Saisir un nom de répertoire"
        echo ">  2) Lister les fichiers dans REP"
        echo ">  3) Afficher le contenu d'un fichier dans REP"
        echo ">  4) Afficher le contenu de tous les fichiers dans REP"
        echo ">  5) Compter les fichiers dans REP"
        echo ">  6) Saisir les paramètres de découpage (nb)"
        echo ">  7) Créer un répertoire SAVE"
        echo ">  8) Découper les fichiers source dans REP et les stocker dans SAVE"
        echo ">  9) Afficher le nombre de mots par découpage (nb)"
        echo ">  10) Lister les fichiers découpés dans SAVE"
        echo ">  11) Compter les fichiers découpés dans SAVE"
        echo ">  12) Afficher le contenu d'un fichier dans SAVE"
        echo ">  13) Afficher la première ligne de chaque fichier dans SAVE"
        echo ">  14) Afficher la dernière ligne de chaque fichier dans SAVE"
        echo ">  15) Reconstituer un fichier source depuis les sous-parties"
        echo ">  16) Nettoyer les répertoires SAVE et VERIF"
        echo ">  17) Créer des fichiers source dans REP"
        echo ">  18) Quitter"
        echo "=============================================="
        echo
        
        read -p "Choisissez une option : " choix
        echo "-------------------------------------------"
        case $choix in
            1) saisir_repertoire ;;
            2) lister_fichiers ;;
            3) afficher_contenu_fichier ;;
            4) afficher_tous_fichiers ;;
            5) compter_fichiers_rep ;;
            6) saisir_parametres_decoupage ;;
            7) creer_repertoire_save ;;
            8) decouper_fichiers ;;
            9) afficher_nombre_mots ;;
            10) lister_fichiers_save ;;
            11) compter_fichiers_save ;;
            12) afficher_contenu_save ;;
            13) afficher_premiere_ligne_save ;;
            14) afficher_derniere_ligne_save ;;
            15) reconstituer_fichier ;;
            16) nettoyer ;;
            17) creer_fichiers_source ;;
            18) breakfunc ;;
            *) echo "Option invalide. Veuillez réessayer." ;;
        esac
        echo "-------------------------------------------"
    done
}

main