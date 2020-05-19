package Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class EcranOptions implements Screen {

    private Stage stage;
    private Table table;

    private TextField nombreCreatures;
    private TextField.TextFieldStyle styleNombreCreatures;

    private Label labelNbCreatures;
    private Label.LabelStyle styleLabel;
    private BitmapFont bitmapLabel;

    private ImageButton boutonValider;

    private boolean nbCreatureValide;
    private boolean retourEcranDemarrage;
    private boolean pasDeChangement;

    private Texture styleBg;

    public EcranOptions(){

        /* Initialisation des booléens */
        this.pasDeChangement = true;
        this.retourEcranDemarrage = false;
        this.nbCreatureValide = false;

        this.table = new Table();
        this.stage = new Stage();

        /* Label "Nombre de créatures" */
        this.bitmapLabel = new BitmapFont(Gdx.files.internal("default.fnt"));
        this.styleLabel = new Label.LabelStyle(this.bitmapLabel, Color.WHITE);
        this.labelNbCreatures = new Label("Nombre de créatures :", this.styleLabel);

        /* Style du champ à remplir "Nombre de créatures" */
        this.styleNombreCreatures = new TextField.TextFieldStyle(new BitmapFont(Gdx.files.internal("default.fnt")), Color.WHITE, null, null, null);
        this.styleNombreCreatures.fontColor = Color.BLACK;
        this.styleBg = new Texture(Gdx.files.internal("ui/case_nb_creatures.png"));
        this.styleNombreCreatures.background = new TextureRegionDrawable(this.styleBg);
        this.styleNombreCreatures.cursor = new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/curseur_nb_creatures.png")));

        /* Champ à remplir "Nombre de créatures */
        this.nombreCreatures = new TextField("", this.styleNombreCreatures);
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

        /* Bouton "Valider les options" */
        this.boutonValider = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/retour_ecran_dem.png"))));
        this.boutonValider.addListener(new GestionBoutonValider());

        /* Ajout des éléments à la table */
        this.table.setFillParent(true);
        this.table.add(this.labelNbCreatures);
        this.table.row();
        this.table.add(this.nombreCreatures);
        this.table.row();
        this.table.add(this.boutonValider).pad(100);

        /* Ajout de la table à la stage */
        this.stage.addActor(this.table);
        //Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        //Gdx.input.setInputProcessor(this.stage);
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
        //this.stage.dispose();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }

    public TextField getNombreCreatures() {
        return this.nombreCreatures;
    }

    public boolean isNbCreatureValide() {
        return nbCreatureValide;
    }

    public void setNbCreatureValide(boolean nbCreatureValide) {
        this.nbCreatureValide = nbCreatureValide;
    }

    public boolean isRetourEcranDemarrage() {
        return retourEcranDemarrage;
    }

    public void setRetourEcranDemarrage(boolean retourEcranDemarrage) {
        this.retourEcranDemarrage = retourEcranDemarrage;
    }

    public boolean isPasDeChangement() {
        return pasDeChangement;
    }

    public void setPasDeChangement(boolean pasDeChangement) {
        this.pasDeChangement = pasDeChangement;
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
