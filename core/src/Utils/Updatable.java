package Utils;

public interface Updatable {

    /**
     * Mettre à jour l'objet
     * Appelé à chaque pas de la simulation
     *
     * @param delta_t : Le temps écoulé
     */
    void update(double delta_t);
}
