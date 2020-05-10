package Entites.Creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class CreatureRenderer {

    public Creature creatureHote;

    public ArrayList<Creature> creatures;

    private SpriteBatch spriteBatchCreature;
    private Texture textureCreature;

    public CreatureRenderer(ArrayList<Creature> creatures, SpriteBatch batch){
        this.creatures = creatures;
        //this.creatureHote = creatureHote;
        this.spriteBatchCreature = batch;
        this.textureCreature = new Texture(Gdx.files.internal("creature1.png"));
    }


    public void renduCreature() {
        spriteBatchCreature.begin();
        dessinerCreature();
        spriteBatchCreature.end();
    }

    /**
     * Dessine toutes les créatures
     */
    public void dessinerCreature() {
        //spriteBatchCreature.draw(textureCreature, 32 * this.creatureHote.getPosition().x, 32 * this.creatureHote.getPosition().y, 500, 500);

        for (Creature creat : this.creatures) {
            //TODO : remplacer 32
            spriteBatchCreature.draw(textureCreature, 32 * creat.getPosition().x, 32 * creat.getPosition().y, 32 * 2, 32 * 2);
        }
    }

}
