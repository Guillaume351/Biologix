package Utils.Stats;

import java.util.List;

public class Stat {
    double energiePerdueSubsistance = 0;
    double energiePerdueThermiquement = 0;
    double energiePerdueDeplacement = 0;
    double energiePerdueCombat = 0;
    double energieGagneeManger = 0;
    double energiePerdueReproduction = 0;
    double energiePerdueAccouchement = 0;
    double energieStockee;
    double quantiteOxygene;

    public Stat() {
        this.energiePerdueSubsistance = 0;
        this.energiePerdueThermiquement = 0;
        this.energiePerdueDeplacement = 0;
        this.energiePerdueCombat = 0;
        this.energiePerdueReproduction = 0;
        this.energiePerdueAccouchement = 0;
        this.quantiteOxygene = 0;
    }

    public Stat(List<Stat> stats) {
        this();
        for (Stat s : stats) {
            this.energiePerdueSubsistance += s.energiePerdueSubsistance;
            this.energiePerdueThermiquement += s.energiePerdueThermiquement;
            this.energiePerdueDeplacement += s.energiePerdueDeplacement;
            this.energiePerdueCombat += s.energiePerdueCombat;
            this.energieGagneeManger += s.energieGagneeManger;
            this.energiePerdueReproduction += s.energiePerdueReproduction;
            this.energiePerdueAccouchement += s.energiePerdueAccouchement;
            this.quantiteOxygene += s.quantiteOxygene;
        }
    }

    public double getQuantiteOxygene() {
        return quantiteOxygene;
    }

    public void setQuantiteOxygene(double quantiteOxygene) {
        this.quantiteOxygene = quantiteOxygene;
    }

    public double getEnergieStockee() {
        return energieStockee;
    }

    public void setEnergieStockee(double energieStockee) {
        this.energieStockee = energieStockee;
    }

    public void afficherStats() {
        double energiePerdueTotal = getEnergieDepenseeTotal();
        System.out.println("-----------------------------------------");
        System.out.println("Subsistance : " + energiePerdueSubsistance + " (" + energiePerdueSubsistance / energiePerdueTotal * 100 + " %)");
        System.out.println("Thermique : " + energiePerdueThermiquement + " (" + energiePerdueThermiquement / energiePerdueTotal * 100 + " %)");
        System.out.println("Deplacement : " + energiePerdueDeplacement + " (" + energiePerdueDeplacement / energiePerdueTotal * 100 + " %)");
        System.out.println("Combat : " + energiePerdueCombat + " (" + energiePerdueCombat / energiePerdueTotal * 100 + " %)");
        System.out.println("Reproduction : " + energiePerdueReproduction + " (" + energiePerdueReproduction / energiePerdueTotal * 100 + " %)");
        System.out.println("Acouchement : " + energiePerdueAccouchement + " (" + energiePerdueAccouchement / energiePerdueTotal * 100 + " %)");
        System.out.println("Total depenses : " + energiePerdueTotal);
        System.out.println("Gains : " + energieGagneeManger);
        System.out.println("Bilan : " + (energieGagneeManger - energiePerdueTotal));
        System.out.println("Stock : " + energieStockee);
        System.out.println("Gains/Stock : " + energieGagneeManger / energieStockee);
        System.out.println("O2 : " + quantiteOxygene);
    }

    public double getEnergiePerdueSubsistance() {
        return energiePerdueSubsistance;
    }

    public double getEnergieDepenseeTotal() {
        return energiePerdueAccouchement + energiePerdueCombat + energiePerdueDeplacement + energiePerdueReproduction + energiePerdueSubsistance + energiePerdueThermiquement;
    }
    public void setEnergiePerdueSubsistance(double energiePerdueSubsistance) {
        this.energiePerdueSubsistance = energiePerdueSubsistance;
    }

    public double getEnergiePerdueThermiquement() {
        return energiePerdueThermiquement;
    }

    public void setEnergiePerdueThermiquement(double energiePerdueThermiquement) {
        this.energiePerdueThermiquement = energiePerdueThermiquement;
    }

    public double getEnergiePerdueDeplacement() {
        return energiePerdueDeplacement;
    }

    public void setEnergiePerdueDeplacement(double energiePerdueDeplacement) {
        this.energiePerdueDeplacement = energiePerdueDeplacement;
    }

    public double getEnergiePerdueCombat() {
        return energiePerdueCombat;
    }

    public void setEnergiePerdueCombat(double energiePerdueCombat) {
        this.energiePerdueCombat = energiePerdueCombat;
    }

    public double getEnergieGagneeManger() {
        return energieGagneeManger;
    }

    public void setEnergieGagneeManger(double energieGagneeManger) {
        this.energieGagneeManger = energieGagneeManger;
    }

    public double getEnergiePerdueReproduction() {
        return energiePerdueReproduction;
    }

    public void setEnergiePerdueReproduction(double energiePerdueReproduction) {
        this.energiePerdueReproduction = energiePerdueReproduction;
    }

    public double getEnergiePerdueAccouchement() {
        return energiePerdueAccouchement;
    }

    public void setEnergiePerdueAccouchement(double energiePerdueAccouchement) {
        this.energiePerdueAccouchement = energiePerdueAccouchement;
    }

    public double getNiemeDepense(int n) {
        switch (n) {
            case 0:
                return getEnergiePerdueAccouchement();
            case 1:
                return getEnergiePerdueCombat();
            case 2:
                return getEnergiePerdueDeplacement();
            case 3:
                return getEnergiePerdueReproduction();
            case 4:
                return getEnergiePerdueSubsistance();
            case 5:
                return getEnergiePerdueThermiquement();
        }
        return 0;
    }
}

