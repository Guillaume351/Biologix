package Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.MyGdxGame;

import java.util.Random;

public class EcranDemarrage implements Screen {

    private MyGdxGame jeu;

    private Stage stage;
    private Table table;
    public boolean lancerJeu;
    public boolean options;

    public GestionBoutonLancer gestionBoutonLancer;
    public ImageButton boutonLancer;
    public TextureRegionDrawable imageBoutonLancer;
    private Texture petittest;

    public GestionBoutonOptions gestionBoutonOptions;
    public ImageButton boutonOptions;
    public TextureRegionDrawable imageBoutonOptions;

    private SpriteBatch spriteBatchFond;
    private Texture textureFond;

    private int i;


    public EcranDemarrage(MyGdxGame jeu){
        this.i = 0;
        this.jeu = jeu;

        this.lancerJeu = false;
        this.stage = new Stage();
        this.table = new Table();

        this.spriteBatchFond = new SpriteBatch();
        this.textureFond = new Texture(Gdx.files.internal("creature1.png"));

        this.petittest = new Texture(Gdx.files.internal("ui/bouton_lancer_partie.png"));
        this.imageBoutonLancer = new TextureRegionDrawable(this.petittest);
        this.boutonLancer = new ImageButton(this.imageBoutonLancer);
        this.gestionBoutonLancer = new GestionBoutonLancer();
        this.boutonLancer.addListener(this.gestionBoutonLancer);
        this.boutonLancer.setSize(300, 30);
        this.boutonLancer.setPosition(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3);
        this.boutonLancer.setTransform(true);

        this.imageBoutonOptions = new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/bouton_options.png")));
        this.boutonOptions = new ImageButton(this.imageBoutonOptions);
        this.gestionBoutonOptions = new GestionBoutonOptions();
        this.boutonOptions.addListener(this.gestionBoutonOptions);

        this.table.setFillParent(true);
        this.table.add(this.boutonLancer);
        this.table.row();
        this.table.add(this.boutonOptions).pad(50);
        this.table.row();

        Gdx.input.setInputProcessor(this.stage);
        this.stage.addActor(this.table);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
        stage.draw();
    }

    @Override
    public void render(float delta) {
        if (i % 50 == 0) {
            Gdx.gl.glClearColor((float) 0.2, (float) 0.2, (float) 0.2, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            //stage.act();
            Random rand = new Random();
            this.spriteBatchFond.begin();
            for (int j = 0; j < 10; j++) {
                this.spriteBatchFond.draw(this.textureFond, rand.nextInt(500), rand.nextInt(500), 100, 100);
            }
            this.spriteBatchFond.end();
        }
        Gdx.input.setInputProcessor(this.stage);
        stage.draw();
        i++;

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
        if (!this.jeu.ecranOptions.retourEcranDemarrage) {
            Gdx.gl.glClearColor(0, 0, 0, 0);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
    }

    @Override
    public void dispose() {
        this.stage.dispose();

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
            options = true;
            return true;

        }

    }
}
