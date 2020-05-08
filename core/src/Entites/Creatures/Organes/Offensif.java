package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;
import Utils.ConstantesBiologiques;

import java.util.Random;

//Attaquer
public class Offensif extends Organe {

    private double puissanceAttaque;
    private double volonteAttaque;

    public Offensif(Random r){
        super(r);
        this.puissanceAttaque = ConstantesBiologiques.puissanceAttaqueMin + (ConstantesBiologiques.puissanceAttaqueMax - ConstantesBiologiques.puissanceAttaqueMin) * r.nextDouble();
        this.volonteAttaque = 0;
    }

    public Offensif(Offensif offensifMere, Offensif offensifPere, Random r, double mutation){
        super(offensifMere, offensifMere, r, mutation);
        Offensif alea = new Offensif(r);
        this.volonteAttaque = 0;
        this.puissanceAttaque = (offensifMere.puissanceAttaque + offensifPere.puissanceAttaque + alea.puissanceAttaque * mutation) / (2 + mutation);
    }

    public double getVolonteAttaque(){return this.volonteAttaque;}

    public double getEnergieDepenseeAttaque() {
        return this.volonteAttaque * this.puissanceAttaque * this.getCreatureHote().getFoie().getProportionPv();
    }


    public double getPuissanceAttaque(){
        return this.puissanceAttaque;
    }

    public void updateOffensif(OutputsCerveau sorties){
        this.volonteAttaque = sorties.getVolonteAttaque();
    }
}
