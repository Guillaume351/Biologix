package Entites.Creatures;

import Utils.ConstantesBiologiques;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class CreatureRenderer {


    public ArrayList<Creature> creatures;

    private SpriteBatch spriteBatchCreature;
    private Texture textureCreature;

    public CreatureRenderer(ArrayList<Creature> creatures, SpriteBatch batch){
        this.creatures = creatures;
        this.spriteBatchCreature = batch;
        this.textureCreature = new Texture(Gdx.files.internal("creature1.png"));
    }

    public void setCreatures(ArrayList<Creature> creatures){
        this.creatures = creatures;
    }


    public void renduCreature(int nbRendus) {
        spriteBatchCreature.begin();
        dessinerCreature(nbRendus, ConstantesBiologiques.deltaT);
        spriteBatchCreature.end();
    }

    /**
     * Dessine toutes les créatures
     */
    public void dessinerCreature(int nbRendus, double dt) {
        //spriteBatchCreature.draw(textureCreature, 32 * this.creatureHote.getPosition().x, 32 * this.creatureHote.getPosition().y, 500, 500);


        for (Creature creat : this.creatures) {
            int x = (int) (ConstantesBiologiques.PixelsParCoord * creat.getPosition(nbRendus, dt).x);
            int y = (int) (ConstantesBiologiques.PixelsParCoord * creat.getPosition(nbRendus, dt).y);
            int taille = (int) (creat.getTaille() * ConstantesBiologiques.PixelsParCoord);

            spriteBatchCreature.draw(textureCreature, x, y, taille, taille);
        }
    }

}
