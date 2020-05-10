package Entites.Ressources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class RessourceRenderer {

    private SpriteBatch spriteBatchCreature;

    ArrayList<Ressource> ressources;


    private Texture textureViande;
    private Texture textureViandeMoyenPourrie;
    private Texture textureViandePourrie;

    private Texture textureFruit;
    private Texture textureArbre;


    public RessourceRenderer(ArrayList<Ressource> tableauRessources, SpriteBatch batch) {
        this.ressources = tableauRessources;
        this.spriteBatchCreature = batch;
        this.textureViande = new Texture(Gdx.files.internal("viande.png"));
        this.textureViandeMoyenPourrie = new Texture(Gdx.files.internal("viande_moyen_fraîche.png"));
        this.textureViandePourrie = new Texture(Gdx.files.internal("rotten_flesh.png"));
        this.textureFruit = new Texture(Gdx.files.internal("pomme.png"));
        this.textureArbre = new Texture(Gdx.files.internal("arbre.png"));
    }


    public void renduRessource() {
        spriteBatchCreature.begin();
        dessinerViande();
        spriteBatchCreature.end();
    }

    /**
     * Dessine les ressources
     */
    public void dessinerViande() {

        for (Ressource ressource : this.ressources) {
            //TODO : faire un switch avec les différents types de ressources
            if (ressource instanceof Viande) {
                if (((Viande) ressource).estPourrie()) {
                    spriteBatchCreature.draw(textureViandePourrie, 32 * ressource.getPosition().x, 32 * ressource.getPosition().y, 32 * 2, 32 * 2);
                } else {
                    spriteBatchCreature.draw(textureViande, 32 * ressource.getPosition().x, 32 * ressource.getPosition().y, 32 * 2, 32 * 2);
                }
            }
            if (ressource instanceof Fruit) {
                spriteBatchCreature.draw(textureFruit, 32 * ressource.getPosition().x, 32 * ressource.getPosition().y, 32 * 2, 32 * 2);
            }
            if (ressource instanceof Arbre) {
                spriteBatchCreature.draw(textureArbre, 32 * ressource.getPosition().x, 32 * ressource.getPosition().y, 32 * 2, 96 * 2);
            }

        }
    }

}
