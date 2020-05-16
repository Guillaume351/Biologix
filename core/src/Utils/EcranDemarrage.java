package Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MyGdxGame;

public class EcranDemarrage implements Screen {

    private Stage stage;
    public boolean lancerJeu;

    public GestionBoutonLancer gestionBoutonLancer;
    public ImageButton boutonLancer;
    public TextureRegionDrawable imageBoutonLancer;

    public GestionBoutonOptions gestionBoutonOptions;
    public ImageButton boutonOptions;
    public TextureRegionDrawable imageBoutonOptions;

    public EcranDemarrage(){
        this.lancerJeu = false;
        this.stage = new Stage();

        this.imageBoutonLancer = new TextureRegionDrawable(new Texture(Gdx.files.internal("arbre.png")));
        this.boutonLancer = new ImageButton(this.imageBoutonLancer);
        this.gestionBoutonLancer = new GestionBoutonLancer();
        this.boutonLancer.addListener(this.gestionBoutonLancer);
        this.boutonLancer.setSize(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
        this.boutonLancer.setPosition(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
        this.boutonLancer.setTransform(true);
        /*utton2.scaleBy(0.5f); */

        this.imageBoutonOptions = new TextureRegionDrawable(new Texture(Gdx.files.internal("banane.png")));
        this.boutonOptions = new ImageButton(this.imageBoutonOptions);
        this.gestionBoutonOptions = new GestionBoutonOptions();
        this.boutonOptions.addListener(this.gestionBoutonOptions);
        //this.boutonOptions

        Gdx.input.setInputProcessor(stage);
        this.stage.addActor(this.boutonLancer);
        this.stage.addActor(this.boutonOptions);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(1, 1, 1, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //stage.act();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(this.boutonLancer);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public class GestionBoutonLancer extends InputListener {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            lancerJeu = true;
            return true;
        }

    }

    public class GestionBoutonOptions extends InputListener {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            //lancerJeu = true;
            return true;
        }

    }
}
