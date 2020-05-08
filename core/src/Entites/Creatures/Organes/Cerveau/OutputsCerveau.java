package Entites.Creatures.Organes.Cerveau;

import com.badlogic.gdx.math.Vector2;

public class OutputsCerveau {
    double vitesse;
    //Deplacement
    private Vector2 direction;
    //Bouche
    private double coeffVoracite;

    //Defensif
    private double volonteDefense;

    //Offensif
    private double volonteAttaque;

    //Perception
    private double champVision;

    private double distanceVision;

    public double getDistanceVision() {
        return distanceVision;
    }

    public void setDistanceVision(double distanceVision) {
        this.distanceVision = distanceVision;
    }
    //Reproduction
    private double volonteReproductive;

    public double getVolonteReproductive() {
        return volonteReproductive;
    }

    public void setVolonteReproductive(double volonteReproductive) {
        this.volonteReproductive = volonteReproductive;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public double getVitesse() {
        return vitesse;
    }

    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    public double getCoeffVoracite() {
        return coeffVoracite;
    }

    public void setCoeffVoracite(double coeffVoracite) {
        this.coeffVoracite = coeffVoracite;
    }

    public double getVolonteDefense() {
        return volonteDefense;
    }

    public void setVolonteDefense(double volonteDefense) {
        this.volonteDefense = volonteDefense;
    }

    public double getVolonteAttaque() {
        return volonteAttaque;
    }

    public void setVolonteAttaque(double volonteAttaque) {
        this.volonteAttaque = volonteAttaque;
    }

    public double getChampVision() {
        return champVision;
    }

    public void setChampVision(double champVision) {
        this.champVision = champVision;
    }
}
