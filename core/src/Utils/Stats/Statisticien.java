package Utils.Stats;

import Entites.Creatures.Creature;
import Entites.Entite;
import Entites.Ressources.Ressource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statisticien {
    List<List<Double>> historiqueAges;
    List<Double> historiqueAgeMoyen;
    List<Stat> historiqueStatsGlobales;
    List<List<Stat>> historiqueStatsIndividuelles;
    List<Double> historiqueEnergiesDisponibles;
    List<Integer> nbBlessures;
    List<Integer> nbAccouchement;
    List<Integer> nbReproduction;

    public Statisticien() {
        historiqueAges = new ArrayList<>();
        historiqueAgeMoyen = new ArrayList<>();
        historiqueStatsGlobales = new ArrayList<>();
        historiqueStatsIndividuelles = new ArrayList<>();
        historiqueEnergiesDisponibles = new ArrayList<>();
        nbBlessures = new ArrayList<>();
        nbAccouchement = new ArrayList<>();
        nbReproduction = new ArrayList<>();
    }

    private static List<Integer> histogramme(List<Double> valeurs, int nbSubdiv) {
        List<Integer> retour = new ArrayList<>();
        if (valeurs.size() > 0) {
            Collections.sort(valeurs);
            double min = valeurs.get(0);
            double max = valeurs.get(valeurs.size() - 1);
            int indice = 0;
            for (int i = 1; i <= nbSubdiv; i++) {
                int indivDansClasse = 0;
                double borneMax = i * (max - min) / nbSubdiv + min;
                while (valeurs.get(indice) <= borneMax && indice < valeurs.size()) {
                    indivDansClasse++;
                    indice++;
                }
                retour.add(indivDansClasse);
            }
        }
        return retour;
    }


    public void collecter(List<Entite> entites) {
        List<Stat> statsActuelles = new ArrayList<Stat>();
        List<Double> ages = new ArrayList<Double>();
        double sommeAge = 0;
        double nbCrea = 0;
        double energieDispo = 0;
        int bless = 0;
        int accouch = 0;
        int repro = 0;
        for (Entite e : entites) {
            if (e instanceof Creature) {
                ages.add(((Creature) e).getAge());
                statsActuelles.add(((Creature) e).getStat());
                nbCrea++;
                sommeAge += ((Creature) e).getAge();
                if (((Creature) e).blessure_) {
                    bless++;
                }
                if (((Creature) e).reproduction_) {
                    accouch++;
                }
                if (((Creature) e).accouchement_) {
                    repro++;
                }
            } else if (e instanceof Ressource) {
                energieDispo += ((Ressource) e).getQuantiteEnergie();
            }
        }
        historiqueStatsIndividuelles.add(statsActuelles);
        historiqueStatsGlobales.add(new Stat(statsActuelles));
        historiqueAgeMoyen.add(sommeAge / nbCrea);

        historiqueAges.add(ages);
        historiqueEnergiesDisponibles.add(energieDispo);
        nbBlessures.add(bless);
        nbAccouchement.add(accouch);
        nbReproduction.add(repro);
    }
}
