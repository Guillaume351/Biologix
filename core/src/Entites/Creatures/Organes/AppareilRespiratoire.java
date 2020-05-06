package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;

//Respiration Aquatique et Terrestre
public class AppareilRespiratoire extends Organe {

    double potentielBranchie;
    double densiteOxygene;
    double quantiteOxygene;


    public double getQuantiteOxygene() {
        return this.quantiteOxygene;
    }

    public double getQuantiteOxygeneMax(){
        return this.getMasse(this.getCreatureHote().getAge()) * this.densiteOxygene;
    }

    public boolean perteOxygene(double dt){
        double pertePotentielle = this.getCreatureHote().getMasse() * ConstantesBiologiques.rapportMasseConsommationOxygene * dt;
        if (this.quantiteOxygene - pertePotentielle < 0){
            this.quantiteOxygene = 0;
            return false;
        }
        else {
            this.quantiteOxygene -= pertePotentielle;
            return true;
        }
    }

    public boolean gainOxygene(Terrain terrain, double dt){
        double gainPotentiel;
        if (terrain.estDansEau(this.getCreatureHote())){
            gainPotentiel = this.getMasse(this.getCreatureHote().getAge()) * potentielBranchie * ConstantesBiologiques.rapportMasseProductionOxygene * dt;
        } else {
            gainPotentiel = this.getMasse(this.getCreatureHote().getAge()) * (1 - potentielBranchie) * ConstantesBiologiques.rapportMasseProductionOxygene * dt;
        }
        if (this.quantiteOxygene + gainPotentiel > this.getQuantiteOxygeneMax()){
            this.quantiteOxygene = this.getQuantiteOxygeneMax();
            return false;
        } else {
            this.quantiteOxygene += gainPotentiel;
            return true;
        }
    }
}
