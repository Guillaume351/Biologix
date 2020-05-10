package Entites.Creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CreatureRenderer {

    public Creature creatureHote;
    private SpriteBatch spriteBatchCreature;
    private Texture textureCreature;

    public CreatureRenderer(Creature creatureHote, SpriteBatch batch){
        this.creatureHote = creatureHote;
        this.spriteBatchCreature = batch;
        this.textureCreature = new Texture(Gdx.files.internal("creature1.png"));
    }


    public void renduCreature(){
        spriteBatchCreature.begin();
        dessinerCreature();
        spriteBatchCreature.end();
    }

    public void dessinerCreature(){
        spriteBatchCreature.draw(textureCreature, 32 * this.creatureHote.getPosition().x, 32 * this.creatureHote.getPosition().y, 500, 500);
    }

}
