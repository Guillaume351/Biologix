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
    private List<Double> nbBlessures;
    private List<Double> nbAccouchement;
    private List<Double> nbReproduction;

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
                while (indice < valeurs.size() && valeurs.get(indice) <= borneMax) {
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

        Pixmap pix = new Pixmap(xSize, ySize, Pixmap.Format.RGBA8888);
        pix.setColor(fond);
        pix.fill();
        if (n != 0) {
            double min = Collections.min(valeurs);
            double max = Collections.max(valeurs);
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
        }
        Texture retour = new Texture(pix);
        pix.dispose();
        return retour;
    }

    public static Texture graphiqueStat(List<Stat> valeurs, Color fond, int xSize, int ySize) {
        int n = valeurs.size();
        try {
            Pixmap pix = new Pixmap(xSize, ySize, Pixmap.Format.RGBA8888);

            if (n != 0) {
                double min = customMin(valeurs);
                double max = customMax(valeurs);

                pix.setColor(fond);
                pix.fill();

                //Courbe de depenses
                double largeur = xSize / (double) valeurs.size();

                for (int i = 0; i < valeurs.size(); i++) {
                    Stat st = valeurs.get(i);
                    double yd = 0;
                    int x0_ = (int) (i * largeur);
                    double sommeVal = 0;
                    for (int j = 0; j < 6; j++) {
                        double val_double = st.getNiemeDepense(j);
                        sommeVal += val_double;
                        double upper = (sommeVal - min) / (max - min);
                        double etendue = (val_double) / (max - min);
                        pix.setColor(getColorN(j));
                        pix.fillRectangle(x0_, (int) ((1.0 - upper) * (double) ySize), (int) largeur, (int) (etendue * ySize));
                    }
                }
            }
            Texture retour = new Texture(pix);
            pix.dispose();
            return retour;
        } catch (Exception e) {

        }
        return null;
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
        double ret = valeurs.get(0).getEnergieDepenseeTotal();

        for (int i = 0; i < valeurs.size(); i++) {
            double v = valeurs.get(i).getEnergieDepenseeTotal();

            if (v < ret) {
                ret = v;
            }
        }
        return ret;
    }

    private static double customMax(List<Stat> valeurs) {
        double ret = valeurs.get(0).getEnergieDepenseeTotal();

        for (int i = 0; i < valeurs.size(); i++) {
            double v = valeurs.get(i).getEnergieDepenseeTotal();

            if (v > ret) {
                ret = v;
            }
        }
        return ret;
    }

    public Texture getHistogrameAges(int ancienete, int xSize, int ySize) {
        if (historiqueAges.size() > ancienete) {
            return (graphique(histogramme(historiqueAges.get(historiqueAges.size() - ancienete - 1), 10), Color.WHITE, Color.BLACK, xSize, ySize));
        } else {
            return new Texture(new Pixmap(xSize, ySize, Pixmap.Format.RGBA8888));
        }
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
        nbBlessures.add((double) bless);
        nbAccouchement.add((double) accouch);
        nbReproduction.add((double) repro);
    }

    public Texture getGraphiqueNbBlessures(int xSize, int ySize) {
        return graphique(nbBlessures, Color.WHITE, Color.BLACK, xSize, ySize);
    }

    public Texture getGraphiqueNbAccouchement(int xSize, int ySize) {
        return graphique(nbAccouchement, Color.WHITE, Color.BLACK, xSize, ySize);
    }

    public Texture getGraphiquenbReproductions(int xSize, int ySize) {
        return graphique(nbReproduction, Color.WHITE, Color.BLACK, xSize, ySize);
    }

    public Texture getGraphiqueHistoriqueEnergiesDispo(int xSize, int ySize) {
        return graphique(historiqueEnergiesDisponibles, Color.WHITE, Color.BLACK, xSize, ySize);
    }

    public Texture getHistoriqueEnergetique(int xSize, int ySize) {
        return graphiqueStat(historiqueStatsGlobales, Color.WHITE, xSize, ySize);
    }
}
