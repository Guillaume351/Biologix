package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;
import Utils.ConstantesBiologiques;

import java.util.Random;

//Se proteger des attaques
public class Defensif extends Organe {

    private double densiteDefense;
    private double volonteDefense;

    public Defensif(Random r){
        super(r);
        this.volonteDefense = 0;
        this.densiteDefense = ConstantesBiologiques.densiteDefenseMin + (ConstantesBiologiques.densiteDefenseMax - ConstantesBiologiques.densiteDefenseMin) * r.nextDouble();
    }

    public Defensif(Defensif defensifMere, Defensif defensifPere, Random r, double mutation){
        super(defensifMere, defensifPere, r, mutation);
        Defensif defensifAlea = new Defensif(r);
        this.volonteDefense = 0;
        this.densiteDefense = (defensifMere.densiteDefense + defensifPere.densiteDefense + defensifAlea.densiteDefense * mutation)/(2 + mutation);
    }


    public double getEnergieDepenseeDefense() {
        return this.volonteDefense * getPuissanceDefense() * this.getCreatureHote().getFoie().getProportionPv();
    }


    public double getPuissanceDefense(){
        return this.densiteDefense * this.getCreatureHote().getMasse();
    }

    public double getVolonteDefense(){return this.volonteDefense;}

    public void updateDefensif(OutputsCerveau sorties){
        this.volonteDefense = sorties.getVolonteDefense();
    }
}
