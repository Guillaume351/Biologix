package Environnement.Meteo;

public class Meteo {
    private MeteoMap temp;
    private MeteoMap humidite;
    private TypeMeteo meteo;
    private double DureeJour;
    private double DureeNuit;

    public Meteo(MeteoMap temp, MeteoMap humidite, TypeMeteo meteo, double dureeJour, double dureeNuit, double densiteNuages) {
        this.temp = temp;
        this.humidite = humidite;
        this.meteo = meteo;
        DureeJour = dureeJour;
        DureeNuit = dureeNuit;
        this.densiteNuages = densiteNuages;
    }

    public MeteoMap getTemp() {
        return temp;
    }

    public MeteoMap getHumidite() {
        return humidite;
    }

    public TypeMeteo getMeteo() {
        return meteo;
    }

    public double getDureeJour() {
        return DureeJour;
    }

    public double getDureeNuit() {
        return DureeNuit;
    }

    public double getDensiteNuages() {
        return densiteNuages;
    }

    public void setDensiteNuages(double densiteNuages) {
        this.densiteNuages = densiteNuages;
    }

    /**
     * Indique la luminosité entre 0 et 1 en fonction du temps depuis le debut de la simulation
     *
     * @param temps : Temps depuis le debut de la simulation
     * @return Luminosite entre 0 et 1
     */
    public double getLuminosite(double temps) {
        //Part du cycle jour nuit parcouru
        double tempsCycle = (temps % (DureeJour + DureeNuit));
        if (tempsCycle < DureeJour) {
            //Jour
            double k = tempsCycle / DureeJour;
            return ((1.0 - Math.cos(Math.PI * 2.0 * k)) / 2.0) * (1 - densiteNuages);
        } else {
            //nuit
            return 0;
        }
    }

    /**
     * Densité des nuages (dépend de la météo : ensoleillé 0, très nuageux 1)
     */
    double densiteNuages;

    /**
     * Différents types de météo possibles
     */
    public enum TypeMeteo {
        SOLEIL, NUAGEUX, PLUIE, ORAGE, TEMPETE, NEIGE
    }


    /**
     * Change la meteo de façon fluide entre un etat A et B
     * @param meteoApres la meteo qu'il fera après
     * @param deltaT le temps au bout du quel la météo a fini de changer
     */
    // la fonction est rappele par elle même pour evoluer entre chaque render

    public void transitionMeteo(TypeMeteo meteoApres, double deltaT) {

        TypeMeteo meteoActuel = getMeteo();
        double densiteNuageActuel = getDensiteNuages();

        if (meteoActuel != meteoApres)
            switch (meteoApres) {
                case SOLEIL :
                    //Pour moi on change pas la temperature ni l'humidite dans ce cas la

                    changerDensiteNuage(deltaT,densiteNuageActuel,0.0);
                    // Le -1 correspond au render en moins apres, il faudrait donc convertir deltaT en
                    // nb de render pendant ce deltaT
                    transitionMeteo(meteoApres,deltaT-1);
                    break;
                case NUAGEUX:
                    changerDensiteNuage(deltaT,densiteNuageActuel,0.5);
                    //meme remarque que dans le cas soleil pour le -1
                    //reste humidite à modifier
                    transitionMeteo(meteoApres, deltaT-1);
                    break;
                case PLUIE:
                    changerDensiteNuage(deltaT,densiteNuageActuel,0.75);
                    //meme remarque que dans le cas soleil pour le -1
                    //reste humidite à modifier
                    transitionMeteo(meteoApres, deltaT-1);
                    break;
                case ORAGE:
                    changerDensiteNuage(deltaT,densiteNuageActuel,0.9);
                    //meme remarque que dans le cas soleil pour le -1
                    //reste humidite à modifier
                    transitionMeteo(meteoApres, deltaT-1);
                    break;
                case TEMPETE:
                    changerDensiteNuage(deltaT,densiteNuageActuel,1.0);
                    //meme remarque que dans le cas soleil pour le -1
                    //reste humidite à modifier
                    transitionMeteo(meteoApres, deltaT-1);
                    break;
                case NEIGE:
                    changerDensiteNuage(deltaT,densiteNuageActuel,0.75);
                    //meme remarque que dans le cas soleil pour le -1
                    //reste temperature à modifier
                    transitionMeteo(meteoApres, deltaT-1);
                    break;
                default:
                    break;
            }
    }

    /**
     * Change la densite de nuage à chaque render.
     * @param deltaT periode sur laquelle elle doit modifier la meteo
     * @param densiteNuagesActuel ce qu'on a en terme de nuage
     * @param densiteNuageVoulue ce qu'on veut en terme de nuage
     */
    public void changerDensiteNuage(double deltaT,double densiteNuagesActuel, double densiteNuageVoulue) {

        double ecartDensiteNuage = densiteNuageVoulue - densiteNuagesActuel;
        double evolutionParAppel = ecartDensiteNuage/deltaT;

        //Par rigoureux car la fonction transition meteo va etre rappeler à  chaque render pour faire
        //evoluer de facon continue donc il faudrait mieux faire
        //evolutionParAppel = ecartDensiteNuage/(nb de render dans deltaT)
        setDensiteNuages(densiteNuagesActuel + evolutionParAppel);
    }
}
