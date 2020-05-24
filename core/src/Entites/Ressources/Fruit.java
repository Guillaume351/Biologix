package Entites.Ressources;

import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.Random;

public class Fruit extends Ressource {
    boolean estEmpoisonne; //les fruits peuvent être empoisonnées
    Color couleur;

    Random rand;

    /**
     * Constructeur d'un fruit
     *
     * @param
     */
    public Fruit(Random r, Color couleur, boolean estEmpoisonne, Terrain terrain, boolean estDansArbre) {
        super(new Vector2((float) (ConstantesBiologiques.XMAX * r.nextDouble()), (float) (ConstantesBiologiques.YMAX * r.nextDouble())));
        this.quantiteEnergie = ConstantesBiologiques.energieMaxStockable * r.nextDouble();
        this.terrain = terrain;
        this.estEmpoisonne = estEmpoisonne;
        this.couleur = couleur;
        this.estDansArbre = estDansArbre;
        tempsDepuisChute = 0;
        rand = r;
    }

    boolean estDansArbre;
    double dureeDeVie = ConstantesBiologiques.dureeDeVieFruit;


    double tempsDepuisChute;


    public double getTempsDepuisChute() {
        return tempsDepuisChute;
    }

    public boolean isEstDansArbre() {
        return estDansArbre;
    }

    public void setEstDansArbre(boolean estDansArbre) {
        this.estDansArbre = estDansArbre;
    }

    boolean estEmpoisonne() {
        return estEmpoisonne;
    }

    public void update(double dt) {
        if (!estDansArbre) {
            tempsDepuisChute += dt;
        }
        if (tempsDepuisChute >= dureeDeVie) {
            //Creer un arbre
            this.getTerrain().retirerEntite(this);
            this.getTerrain().ajouterEntite(new Arbre(rand, this));     //TODO vérifier que l'arbre est dans le terrain
        }
    }

    @Override
    public String getTypeRessource() {
        return "fruit";
    }
}