package Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.MyGdxGame;

import javax.xml.soap.Text;

public class EcranDemarrage implements Screen {

    private MyGdxGame jeu;
    private Stage stage;
    private TextButton.TextButtonStyle styleBouton;
    public TextButton boutonLancer;
    public GestionBouton gestionBouton;
    private TextureAtlas boutonAtlas;

    public EcranDemarrage(MyGdxGame jeu){
        this.stage = new Stage();
        this.boutonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        this.styleBouton = new TextButton.TextButtonStyle();
        this.styleBouton.font = new BitmapFont();
        this.boutonLancer = new TextButton("Lancer la simulation", this.styleBouton);
        this.gestionBouton = new GestionBouton();
        this.boutonLancer.addListener(this.gestionBouton);

        boutonLancer.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/5);
        boutonLancer.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        boutonLancer.setTransform(true);
        /*utton2.scaleBy(0.5f); */

        Gdx.input.setInputProcessor(stage);
        stage.addActor(this.boutonLancer);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(1, 1, 1, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //stage.act();
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

    public class GestionBouton extends InputListener {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            System.out.println("test");
            jeu.setScreen(jeu.ecranSimulation);
            return true;
        }

    }
}
