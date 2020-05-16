package Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.awt.*;

public class EcranOptions implements Screen {

    private Stage stage;
    private Table table;
    private Skin skin;

    public TextField nombreCreatures;
    private TextField.TextFieldStyle style;

    private Label labelNbCreatures;
    private Label.LabelStyle styleLabel;
    private BitmapFont bitmapLabel;

    private ImageButton boutonValider;

    public boolean nbCreatureValide;
    public boolean retourEcranDemarrage;
    public boolean pasDeChangement;

    private Texture styleBg;

    public EcranOptions(){
        this.pasDeChangement = true;
        this.retourEcranDemarrage = false;
        this.nbCreatureValide = false;
        this.bitmapLabel = new BitmapFont(Gdx.files.internal("default.fnt"));
        this.styleLabel = new Label.LabelStyle(this.bitmapLabel, Color.WHITE);
        this.labelNbCreatures = new Label("Nombre de créatures :", this.styleLabel);
        this.style = new TextField.TextFieldStyle(new BitmapFont(Gdx.files.internal("default.fnt")), Color.WHITE, null, null, null);
        this.style.fontColor = Color.BLACK;
        this.styleBg = new Texture(Gdx.files.internal("ui/case_nb_creatures.png"));
        this.style.background = new TextureRegionDrawable(this.styleBg);
        this.style.cursor = new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/curseur_nb_creatures.png")));
        this.table = new Table();
        this.stage = new Stage();
        this.nombreCreatures = new TextField("", this.style);
        this.nombreCreatures.setWidth(200);
        this.nombreCreatures.setMessageText("100");  // nombre de créatures par défaut
        this.nombreCreatures.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                if (c == '\t'){
                    try {
                        int nbCreat = Integer.parseInt(nombreCreatures.getText());
                        nbCreatureValide = true;
                        pasDeChangement = false;
                    }
                    catch (NumberFormatException e)
                    {
                        // TODO : il faudra ajouter un label pour dire d'entrer un entier
                        System.out.println("Il faut entrer un entier !");
                        nbCreatureValide = false;
                    }
                }
            }
        });

        this.boutonValider = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/retour_ecran_dem.png"))));
        this.boutonValider.addListener(new GestionBoutonValider());
        this.table.setFillParent(true);
        this.table.add(this.labelNbCreatures);
        this.table.row();
        this.table.add(this.nombreCreatures);
        this.table.row();
        this.table.add(this.boutonValider).pad(100);

        this.stage.addActor(this.table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show(){

    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(this.stage);
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
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }

    public class GestionBoutonValider extends InputListener {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            if (nbCreatureValide || pasDeChangement) {
                retourEcranDemarrage = true;
            }
            return true;
        }


    }

}
