package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Utils.ConstantesBiologiques;

import java.util.Random;

public abstract class OrganeThermique extends Organe {
    double Isolation;

    public OrganeThermique(Random r){
        super(r);
        this.Isolation = ConstantesBiologiques.isolationMin + (ConstantesBiologiques.isolationMax - ConstantesBiologiques.isolationMin) * r.nextDouble();
    }

    public OrganeThermique(OrganeThermique otMere, OrganeThermique otPere, Random r, double mutation){
        super(otMere, otPere, r, mutation);
        double alea = ConstantesBiologiques.isolationMin + (ConstantesBiologiques.isolationMax - ConstantesBiologiques.isolationMin) * r.nextDouble();
        this.Isolation = (otPere.Isolation + otMere.Isolation + alea * mutation) / (2 + mutation);
    }

    public abstract double getResistanceThermique(double tempExterieure);

}
