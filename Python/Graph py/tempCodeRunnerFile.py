# Réimportation des bibliothèques nécessaires
import numpy as np
import matplotlib.pyplot as plt
from sklearn.metrics import mean_squared_error
from sklearn.ensemble import RandomForestRegressor
from xgboost import XGBRegressor

# Données mises à jour
tours_complets = np.array([0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11])
demandes_completes = np.array([0, 130, 156, 188, 175, 181, 174, 174, 190, 166, 197, 167])

# Ajustement linéaire avec les nouvelles données
coeffs_lineaire_complet = np.polyfit(tours_complets, demandes_completes, 1)
polynome_lineaire_complet = np.poly1d(coeffs_lineaire_complet)

# Ajustement quadratique avec les nouvelles données
coeffs_quadratique_complet = np.polyfit(tours_complets, demandes_completes, 2)
polynome_quadratique_complet = np.poly1d(coeffs_quadratique_complet)

# Ajustement cubique avec les nouvelles données
coeffs_cubique_complet = np.polyfit(tours_complets, demandes_completes, 3)
polynome_cubique_complet = np.poly1d(coeffs_cubique_complet)

# Ajustement du modèle Random Forest Regressor
tours_complets_reshaped = tours_complets.reshape(-1, 1)
rf_model = RandomForestRegressor(n_estimators=100, random_state=42)
rf_model.fit(tours_complets_reshaped, demandes_completes)

# Ajustement du modèle XGBoost
xgb_model = XGBRegressor(n_estimators=100, learning_rate=0.1, max_depth=4, random_state=42)
xgb_model.fit(tours_complets_reshaped, demandes_completes)

# Préparation des données pour le graphique
tours_graph_complet = np.linspace(min(tours_complets), max(tours_complets) + 2, 200)
demande_lineaire_complet = polynome_lineaire_complet(tours_graph_complet)
demande_quadratique_complet = polynome_quadratique_complet(tours_graph_complet)
demande_cubique_complet = polynome_cubique_complet(tours_graph_complet)
demande_rf_complet = rf_model.predict(tours_graph_complet.reshape(-1, 1))
demande_xgb_complet = xgb_model.predict(tours_graph_complet.reshape(-1, 1))



# Calcul des prédictions pour le dernier point réel
dernier_tour = tours_complets[-1]
prediction_lineaire_dernier = polynome_lineaire_complet(dernier_tour)
prediction_quadratique_dernier = polynome_quadratique_complet(dernier_tour)
prediction_cubique_dernier = polynome_cubique_complet(dernier_tour)
prediction_rf_dernier = rf_model.predict([[dernier_tour]])
prediction_xgb_dernier = xgb_model.predict([[dernier_tour]])

# Calcul de l'erreur quadratique moyenne (RMSE) pour chaque modèle
rmse_lineaire = np.sqrt(mean_squared_error(demandes_completes, polynome_lineaire_complet(tours_complets)))
rmse_quadratique = np.sqrt(mean_squared_error(demandes_completes, polynome_quadratique_complet(tours_complets)))
rmse_cubique = np.sqrt(mean_squared_error(demandes_completes, polynome_cubique_complet(tours_complets)))
rmse_rf = np.sqrt(mean_squared_error(demandes_completes, rf_model.predict(tours_complets_reshaped)))
rmse_xgb = np.sqrt(mean_squared_error(demandes_completes, xgb_model.predict(tours_complets_reshaped)))

# Configuration du graphique
fig, ax = plt.subplots(figsize=(26, 16))  # Taille agrandie
plt.scatter(tours_complets, demandes_completes, color='red', label='Données réelles', zorder=5)
plt.plot([], [], label='RMSE = Erreur Quadratique Moyenne', color='white')
plt.plot(tours_graph_complet, demande_lineaire_complet, color='navy', label=f'Linéaire (RMSE= {rmse_lineaire:.2f})', linestyle='--')
plt.plot(tours_graph_complet, demande_quadratique_complet, color='lime', label=f'Quadratique (RMSE= {rmse_quadratique:.2f})', linestyle=':')
plt.plot(tours_graph_complet, demande_cubique_complet, color='magenta', label=f'Cubique (RMSE= {rmse_cubique:.2f})', linestyle='-.')
plt.plot(tours_graph_complet, demande_rf_complet, color='darkorange', label=f'Random Forest (RMSE= {rmse_rf:.2f})', linestyle='-.')
plt.plot(tours_graph_complet, demande_xgb_complet, color='teal', label=f'XGBoost (RMSE= {rmse_xgb:.2f})', linestyle='-')


# Ajout des prédictions pour le dernier point
plt.scatter(dernier_tour, prediction_lineaire_dernier, color='navy', label=f'Linéaire (Tour {dernier_tour+1}:  {prediction_lineaire_dernier:.2f})')
plt.scatter(dernier_tour, prediction_quadratique_dernier, color='lime', label=f'Quadratique (Tour {dernier_tour+1}: {prediction_quadratique_dernier:.2f})')
plt.scatter(dernier_tour, prediction_cubique_dernier, color='magenta', label=f'Cubique (Tour {dernier_tour+1}:  {prediction_cubique_dernier:.2f})')
plt.scatter(dernier_tour, prediction_rf_dernier, color='darkorange', label=f'Random Forest (Tour {dernier_tour+1}:  {prediction_rf_dernier[0]:.2f})')
plt.scatter(dernier_tour, prediction_xgb_dernier, color='teal', label=f'XGBoost (Tour {dernier_tour+1}:  {prediction_xgb_dernier[0]:.2f})')


# Labels et légendes
plt.title(f"Prédiction de la demande pour le tour {dernier_tour+1}", fontsize=16)
plt.xlabel("Tour", fontsize=14)
plt.ylabel("Demande", fontsize=14)
plt.legend(loc='lower center', fontsize=12)
plt.grid(True, linestyle='-', alpha=1)

plt.show()
