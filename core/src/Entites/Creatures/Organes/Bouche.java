package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Entites.Ressources.Ressource;

//Ramasser la nourriture efficacement
public class Bouche extends Organe {

    private double capaciteVoracite;
    private double coeffVoracite;

    public double getCapaciteVoracite(){
        return this.capaciteVoracite;
    }

    public double getCoeffVoracite() {
        return coeffVoracite;
    }

    public double getEnergieDepenseeManger(){
        return this.capaciteVoracite * this.coeffVoracite;
    }

    // TODO : faire cette méthode
    public void manger(Ressource nourriture){

    }


}
