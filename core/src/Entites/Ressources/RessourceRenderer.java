package Entites.Ressources;

import java.util.ArrayList;

import Entites.Creatures.Creature;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RessourceRenderer {

    private SpriteBatch spriteBatchCreature;
    public ArrayList[] tableauRessources = new ArrayList[3];  //3 types de resources différents

    ArrayList<Viande> listeViande;
    ArrayList<Fruit> listeFruit;
    ArrayList<Arbre> listeArbre;


    private Texture textureViande;
    private Texture textureFruit;
    private Texture textureArbre;


    public RessourceRenderer(ArrayList[] tableauRessources, SpriteBatch batch){
        this.listeViande = tableauRessources[0];
        this.listeViande = tableauRessources[1];
        this.listeViande = tableauRessources[2];
        this.spriteBatchCreature = batch;
        this.textureViande = new Texture(Gdx.files.internal("viande.png"));
        this.textureFruit = new Texture(Gdx.files.internal("pomme.png"));
        this.textureArbre = new Texture(Gdx.files.internal("arbre.png"));
    }


    public void renduRessource() {
        spriteBatchCreature.begin();
        dessinerViande();
        spriteBatchCreature.end();
    }

    /**
     * Dessine les viandes
     */
    public void dessinerViande() {

        for (Viande viande : this.listeViande) {
            spriteBatchCreature.draw(textureViande, 32 * viande.getPosition().x, 32 * viande.getPosition().y, 32 * 2, 32 * 2);
        }
    }

}
