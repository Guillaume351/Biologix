package Utils;

public class ConstantesBiologiques {

    public static double mutationGenerale = 0.5;
    public static double tempsGestation = 1;

    public static double rayonInteraction = 5;

    public static double rapportMasseConsommationOxygene = 0.1;
    public static double rapportMasseProductionOxygene = 10;

    public static double tailleOrganeMin = 0.5;
    public static double tailleOrganeMax = 1;

    public static double tempsCroissanceMin = 100;
    public static double tempsCroissanceMax = 10000;

    public static double densiteMin = 0.3;
    public static double densiteMax = 0.8;

    public static double coutSubsistanceRelatif = 0.3;


    public static double efficaciteReproductiveMin = 0.1;
    public static double efficaciteReproductiveMax = 2;

    /* Appareil respiratoire */
    public static double densiteOxygeneMin = 1;
    public static double densiteOxygeneMax = 3;

    /* Bouche */
    public static double efficaciteVoracite = 1000;    // sera > 1 // avant  : 20
    public static double capaciteVoraciteMin = 0.3;       // avant 2
    public static double capaciteVoraciteMax = 0.4;      // avant 3

    /* Defensif */
    public static double densiteDefenseMin = 2;
    public static double densiteDefenseMax = 2;

    /* Foie */
    public static double coeffDeBlessure = 100;
    public static double densiteDeSoinMin = 1;
    public static double densiteDeSoinMax = 2;
    public static double densiteDePvMin = 32;
    public static double densiteDePvMax = 64;

    /* Graisse */
    public static double efficaciteEnrgetiqueMin = 200;
    public static double efficaciteEnergetiqueMax = 9000;
    public static double rendementAccouchement = 3;
    public static double coutCroissanceRelatif = 0.75;


    /*Mouvement*/
    public static double coeffFrottement = 1;
    public static double vitesseMaxMax = 3;
    public static double vitesseMaxMin = 1;

    /*Sexe*/
    public static double distanceGenetiqueMaxRepro = 1;

    /*Offensif*/
    public static double puissanceAttaqueMax = 0.9;
    public static double puissanceAttaqueMin = 0.5;

    /*Thermique*/
    public static double isolationMax = 10;
    public static double isolationMin = 6;

    /*Vision*/
    public static double densiteDistVision = 0.5;
    public static double densiteChampVision = 30;

    /*Creature*/
    public static double tempInterneMax = 43;
    public static double tempInterneMin = 30;

    /*POSITION*/
    public static double XMAX = 300;
    public static double YMAX = 300;

    /*RESSOURCES*/
    public static double energieMaxStockable = 1000;  // 100 est vraiment pas assez

    /*arbre*/
    public static double tailleArbreMin = 3;
    public static double tailleArbreMax = 10;

    /*fruit*/
    public static double dureeDeVieFruit = 5;

    /*viande*/
    public static double energieMaxStockableViande = 2000;

    public static double vitessePourritureMax = 0.3;
    public static double stabilitePourriture = 3;
    public static double tempsDispawnPostPourriture = 20;

    //Vegetal
    public static int toleranceTemperatureVegetal = 3;
    public static int temperatureIdealeVegetal = 20;
    public static double croissanceMaxVegetal = 5;
    public static double tempsProdFruitMin = 25;
    public static double tempsProdFruitMax = 40;
    public static double rayonChuteFruitMax = 10;
    public static double tempsEntreChuteFruitMax = 50;

    /*Rendu*/
    public static double PixelsParCoord = 32;
    public static boolean AltLayer = true;
    public static double deltaT = 0.1;
    public static int ratioAffichageSimulation = 1;

}
