package Utils.Stats;

import Entites.Creatures.Creature;
import Entites.Entite;
import Entites.Ressources.Ressource;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statisticien {
    private List<List<Double>> historiqueAges;
    private List<Double> historiqueAgeMoyen;
    private List<Stat> historiqueStatsGlobales;
    private List<Double> historiqueEnergiesDisponibles;
    private List<Integer> nbBlessures;
    private List<Integer> nbAccouchement;
    private List<Integer> nbReproduction;

    public Statisticien() {
        historiqueAges = new ArrayList<>();
        historiqueAgeMoyen = new ArrayList<>();
        historiqueStatsGlobales = new ArrayList<>();
        historiqueEnergiesDisponibles = new ArrayList<>();
        nbBlessures = new ArrayList<>();
        nbAccouchement = new ArrayList<>();
        nbReproduction = new ArrayList<>();
    }

    private static List<Double> histogramme(List<Double> valeurs, int nbSubdiv) {
        List<Double> retour = new ArrayList<>();
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
                retour.add(indivDansClasse / (double) valeurs.size());
            }
        }
        return retour;
    }

    public static Texture graphique(List<Double> valeurs, Color fond, Color ligne, int xSize, int ySize) {
        int n = valeurs.size();
        double min = Collections.min(valeurs);
        double max = Collections.max(valeurs);
        Pixmap pix = new Pixmap(xSize, ySize, Pixmap.Format.RGBA8888);
        pix.setColor(fond);
        pix.fill();
        pix.setColor(ligne);
        double y0 = valeurs.get(0);
        for (int i = 1; i < valeurs.size(); i++) {
            double progression = i / (double) (valeurs.size() - 1);
            double depart = (i - 1) / (double) (valeurs.size() - 1);
            double y1 = valeurs.get(i);

            int x0_ = (int) (depart * xSize);
            int x1_ = (int) (progression * xSize);
            int y0_ = (int) (ySize * (1.0 - (y0 - min) / (max - min)));
            int y1_ = (int) (ySize * (1.0 - (y1 - min) / (max - min)));

            pix.drawLine(x0_, y0_, x1_, y1_);

            y0 = y1;
        }

        Texture retour = new Texture(pix);
        pix.dispose();
        return retour;
    }

    public static Texture graphiqueStat(List<Stat> valeurs, Color fond, int xSize, int ySize) {
        int n = valeurs.size();
        Pixmap pix = new Pixmap(xSize, ySize, Pixmap.Format.RGBA8888);


        double min = customMin(valeurs);
        double max = customMax(valeurs);

        pix.setColor(fond);
        pix.fill();

        //Courbe de depenses
        int largeur = xSize / valeurs.size();
        for (int i = 0; i < valeurs.size(); i++) {
            Stat st = valeurs.get(i);
            double yd = 0;
            int x0_ = i * largeur;
            for (int j = 0; j < 6; j++) {
                double val_double = st.getNiemeDepense(j);
                double yu = yd + (val_double - min) / (max - min);
                pix.setColor(getColorN(j));
                pix.drawRectangle(x0_, ySize - (int) (yu * ySize), largeur, (int) ((yu - yd) * ySize));
                yd = yu;
            }
        }

        //Courbe d'energie gagnee
        pix.setColor(Color.GREEN);
        double y0 = valeurs.get(0).energieGagneeManger;
        for (int i = 1; i < valeurs.size(); i++) {
            double progression = i / (double) (valeurs.size() - 1);
            double depart = (i - 1) / (double) (valeurs.size() - 1);
            double y1 = valeurs.get(i).energieGagneeManger;

            int x0_ = (int) (depart * xSize);
            int x1_ = (int) (progression * xSize);
            int y0_ = (int) (ySize * (1.0 - (y0 - min) / (max - min)));
            int y1_ = (int) (ySize * (1.0 - (y1 - min) / (max - min)));

            pix.drawLine(x0_, y0_, x1_, y1_);

            y0 = y1;
        }

        Texture retour = new Texture(pix);
        pix.dispose();
        return retour;
    }

    private static Color getColorN(int n) {
        switch (n) {
            case 0:
                return Color.YELLOW;//Accouchement
            case 1:
                return Color.RED;//Combat
            case 2:
                return Color.BLUE;//Deplacement
            case 3:
                return Color.PINK;//Reproduction
            case 4:
                return Color.CYAN;//Subsistance
            case 5:
                return Color.GOLD;//Thermiquement
        }
        return Color.WHITE;
    }

    private static double customMin(List<Stat> valeurs) {
        double ret = Math.min(valeurs.get(0).getEnergieDepenseeTotal(), valeurs.get(0).getEnergieGagneeManger());

        for (int i = 0; i < valeurs.size(); i++) {
            double v = Math.min(valeurs.get(i).getEnergieDepenseeTotal(), valeurs.get(i).getEnergieGagneeManger());

            if (v < ret) {
                ret = v;
            }
        }
        return ret;
    }

    private static double customMax(List<Stat> valeurs) {
        double ret = Math.max(valeurs.get(0).getEnergieDepenseeTotal(), valeurs.get(0).getEnergieGagneeManger());

        for (int i = 0; i < valeurs.size(); i++) {
            double v = Math.max(valeurs.get(i).getEnergieDepenseeTotal(), valeurs.get(i).getEnergieGagneeManger());

            if (v > ret) {
                ret = v;
            }
        }
        return ret;
    }

    public Texture getHistogrameAges(int ancienete, int xSize, int ySize) {
        return (graphique(histogramme(historiqueAges.get(historiqueAges.size() - ancienete), 10), Color.WHITE, Color.BLACK, xSize, ySize));
    }

    public Texture getGraphiqueAgeMoyen(int xSize, int ySize) {
        return graphique(historiqueAgeMoyen, Color.WHITE, Color.BLACK, xSize, ySize);
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
        historiqueStatsGlobales.add(new Stat(statsActuelles));
        historiqueAgeMoyen.add(sommeAge / nbCrea);

        historiqueAges.add(ages);
        historiqueEnergiesDisponibles.add(energieDispo);
        nbBlessures.add(bless);
        nbAccouchement.add(accouch);
        nbReproduction.add(repro);
    }

    public Texture getGraphiqueNbBlessures(int xSize, int ySize) {
        return graphique((List) nbBlessures, Color.WHITE, Color.BLACK, xSize, ySize);
    }

    public Texture getGraphiqueNbAccouchement(int xSize, int ySize) {
        return graphique((List) nbAccouchement, Color.WHITE, Color.BLACK, xSize, ySize);
    }

    public Texture getGraphiquenbReproductions(int xSize, int ySize) {
        return graphique((List) nbReproduction, Color.WHITE, Color.BLACK, xSize, ySize);
    }

    public Texture getGraphiqueHistoriqueEnergiesDispo(int xSize, int ySize) {
        return graphique(historiqueEnergiesDisponibles, Color.WHITE, Color.BLACK, xSize, ySize);
    }

    public Texture getHistoriqueEnergetique(int xSize, int ySize) {
        return graphiqueStat(historiqueStatsGlobales, Color.WHITE, xSize, ySize);
    }
}
