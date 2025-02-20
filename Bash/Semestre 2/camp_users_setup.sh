#!/bin/bash

input_file="inscriptions.csv"
default_password="Temp123"

# Vérifier et créer les groupes s'ils ne sont pas déjà existant
getent group animator >/dev/null || groupadd animator
getent group participant >/dev/null || groupadd participant

# Lire le fichier CSV et traiter chaque ligne
while IFS=',' read -r prenom nom date_inscription pays anim equipe
do
    # Remplacer les valeurs vides par un texte par défaut
    prenom="${prenom:-user}"
    nom="${nom:-unknown}"

    # Générer un login Prenom_Nom (sans espaces ni caractères spéciaux)
    username=$(echo "${prenom}_${nom}" | tr '[:upper:]' '[:lower:]' | sed 'y/áàäâéèêëíïîóòôöúùüûçñ/aaaaeeeeiiiooouuuucn/')

    # Déterminer le groupe principal (animator si Anim. contient quelque chose, sinon participant)
    if [[ -n "$anim" ]]; then
        main_group="animator"
    else
        main_group="participant"
    fi

    # Vérifier et créer le groupe de l'équipe si nécessaire
    getent group "$equipe" >/dev/null || groupadd "$equipe"

    # Vérifier si l'utilisateur existe déjà
    if id "$username" &>/dev/null; then
        echo "[ NOK ] : Utilisateur $username existe déjà, ligne ignorée."
        continue
    fi

    # Créer l'utilisateur avec groupe principal (participant/animator) et groupe secondaire (équipe)
    home_dir="/home/$main_group/$username"
    useradd -m -d "$home_dir" -G "$main_group,$equipe" -s /bin/bash "$username"
    mkdir -p "$home_dir"
    chown "$username:$main_group" "$home_dir"
    setfacl -m g:animator:r "$home_dir"
    chmod 700 "$home_dir"

    # Définir un mot de passe générique et forcer le changement au premier login
    echo "$username:$default_password" | chpasswd
    chage -d 0 "$username"

    info_file="$home_dir/Info"

    echo -e "Bienvenue $prenom $nom !\n" > "$info_file"
    echo -e "Votre compte a été créé avec succès.\n" >> "$info_file"
    echo -e "Informations personnelles :" >> "$info_file"
    echo -e "➡ Nom : $nom" >> "$info_file"
    echo -e "➡ Prénom : $prenom" >> "$info_file"
    echo -e "➡ Pays : $pays" >> "$info_file"
    echo -e "➡ Date d'inscription : $date_inscription\n" >> "$info_file"

    # Gestion du dossier partagé uniquement si l'utilisateur a une équipe
    if [[ -n "$equipe" ]]; then
        shared_folder="/home/dossier_partager/Groupe_$equipe"
        mkdir -p "$shared_folder"
        chown root:"$equipe" "$shared_folder"
        setfacl -m g:animator:r "$shared_folder"
        chmod 770 "$shared_folder"  # Accès complet au groupe, mais pas aux autres utilisateurs

        echo -e "Accès au dossier partagé :" >> "$info_file"
        echo -e "Un dossier partagé est disponible pour votre groupe ($equipe)." >> "$info_file"
        echo -e "Chemin du dossier : $shared_folder" >> "$info_file"
        echo -e "Vous pouvez y accéder avec la commande : cd $shared_folder\n" >> "$info_file"
    else
        echo -e "En tant qu'animateur, vous n'êtes pas affecté à un groupe spécifique." >> "$info_file"
        echo -e "Vous pouvez aider les participants et accéder aux ressources communes.\n" >> "$info_file"
    fi

    # Protection du fichier Info contre les modifications
    chown root:"participant" "$info_file"
    setfacl -m g:animator:r "$info_file"
    chmod 444 "$info_file"  # Lecture seule pour l'utilisateur

    echo "[ OK ] :  Compte créé : $username (Groupe : $main_group, Équipe : $equipe)"

done < <(tail -n +2 "$input_file")  # Ignore l'entête du CSV

echo "[ OK ] :  Création des comptes terminée !"
