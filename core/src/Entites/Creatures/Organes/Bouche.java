package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Entites.Ressources.Ressource;
import Utils.ConstantesBiologiques;
import Utils.Position.Localisable;

import java.util.*;

//Ramasser la nourriture efficacement
public class Bouche extends Organe {

    private double capaciteVoracite;    // entre min et max
    private Ressource nourritureMangee;

    public Bouche(Random r){
        super(r);
        this.nourritureMangee = null;
        this.capaciteVoracite = ConstantesBiologiques.capaciteVoraciteMin + (ConstantesBiologiques.capaciteVoraciteMax - ConstantesBiologiques.capaciteVoraciteMin) * r.nextDouble();
    }

    public Bouche(Bouche boucheMere, Bouche bouchePere, Random r, double mutation){
        super(boucheMere, bouchePere, r, mutation);
        Bouche boucheAlea = new Bouche(r);
        this.nourritureMangee = null;
        this.capaciteVoracite = (boucheMere.capaciteVoracite + bouchePere.capaciteVoracite + boucheAlea.capaciteVoracite * mutation)/(2 + mutation);
    }


    public double getCapaciteVoracite(){
        return this.capaciteVoracite;
    }

    public Ressource getNourritureMangee(){return this.nourritureMangee;}

    public void setNourritureMangee(Ressource nouvelleNourritureMangee){this.nourritureMangee = nouvelleNourritureMangee;}


    /**
     * Energie engagee pour manger de la nourriture
     *
     * @param coeffVoracite: Volonte de la creature de manger
     * @return energie depensee lors de l'action manger
     */
    public double getEnergieDepenseeManger(double coeffVoracite) {
        return this.capaciteVoracite * coeffVoracite;
    }

    /**
     * Renvoie la liste ordonnee de toutes les nourritures et l'energie qui peut en etre extraite par le systeme digestif de la creature
     *
     * @param nourritureAccesible : Liste de la nourriture a portee de la creature
     * @return TreeMap avec les energies potentielles en Key, et les nourritures en Values.
     */
    private TreeMap<Double, Ressource> calculerEnergiesDisponibles(List<Localisable> nourritureAccesible) {
        TreeMap<Double, Ressource> mapRessources = new TreeMap<Double, Ressource>();
        Digestion dig = this.getCreatureHote().getDigestion();
        for (Localisable ressource : nourritureAccesible) {
            mapRessources.put(dig.extraireEnergie((Ressource) ressource), (Ressource) ressource);
        }
        return mapRessources;
    }

    /**
     * Maximum d'energie qui peut etre contenue dans la bouche
     *
     * @param coeffVoracite : Volonte de la creature de manger
     * @return energie maximale
     */
    public double getEnergieMaxMangeable(double coeffVoracite) {
        return ConstantesBiologiques.efficaciteVoracite * getEnergieDepenseeManger(coeffVoracite);
    }

    /**
     * Retourne l'energie qui est extraite de la digestion de la nourriture la plus rentable dans la liste.
     *
     * @param nourritureAccesible : Liste des nourritures que peut manger la creature
     * @param coeffVoracite       : Volonte de la creature de manger
     * @return energie extraite moins energie depensee pour manger.
     */
    public double manger(List<Localisable> nourritureAccesible, double coeffVoracite) {
        Localisable nouvelleNourritureMangee = null;
        //Energie max que l'on peut mettre dans la bouche
        double EnergieMaxMangeable = getEnergieMaxMangeable(coeffVoracite);
        //energies RECUPERABLES par la creature triees dans l'ordre croissant
        TreeMap<Double, Ressource> mapRessources = calculerEnergiesDisponibles(nourritureAccesible);
        //recuperer l'iterateur
        Iterator iterator = mapRessources.entrySet().iterator();
        //recuperer les nb premiers
        double energieRecuperee = 0;
        while (iterator.hasNext()) {
            Map.Entry<Double, Ressource> mapentry = (Map.Entry<Double, Ressource>) iterator.next();
            if (mapentry.getValue().getQuantiteEnergie() < EnergieMaxMangeable && this.getCreatureHote().getTerrain().entitePresente(mapentry.getValue())) {
                //Il existe une nourriture ou l'on peut repurerer plus d'energie, et que l'on peut mettre dans la bouche.
                nouvelleNourritureMangee = mapentry.getValue();
                energieRecuperee = mapentry.getKey();
            }
        }
        this.nourritureMangee = (Ressource) nouvelleNourritureMangee;
        if (nourritureMangee != null) {
            this.getCreatureHote().getTerrain().retirerEntite(nourritureMangee);
        }
        return energieRecuperee - getEnergieDepenseeManger(coeffVoracite);
    }



}
