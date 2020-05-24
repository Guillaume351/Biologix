package Entites.Creatures;

import Utils.ConstantesBiologiques;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class CreatureRenderer {


    public ArrayList<Creature> creatures;

    private SpriteBatch spriteBatchCreature;
    private Texture textureCreatureTerrestre;
    private Texture textureCreatureAquatique1;
    private Texture textureCreatureAquatique2;

    public CreatureRenderer(ArrayList<Creature> creatures, SpriteBatch batch) {
        this.creatures = creatures;
        this.spriteBatchCreature = batch;
        this.textureCreatureTerrestre = new Texture(Gdx.files.internal("scarab.png"));
        this.textureCreatureAquatique1 = new Texture(Gdx.files.internal("jellyfish.png"));
        this.textureCreatureAquatique2 = new Texture(Gdx.files.internal("baleine.png"));
    }

    public void setCreatures(ArrayList<Creature> creatures) {
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
            Texture texture;
            if (creat.appareilRespiratoire.estAquatique()) {
                if (creat.cerveau.getComportement_amphibien() > 0.5) {
                    texture = this.textureCreatureAquatique2;
                } else {
                    texture = this.textureCreatureAquatique1;
                }
            } else {
                texture = textureCreatureTerrestre;
            }


            spriteBatchCreature.draw(texture, x, y, taille, taille);
        }
    }

}
