package Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class EcranOptions implements Screen {

    private Stage stage;
    private Table table;

    private TextField nombreCreatures;
    private TextField.TextFieldStyle styleNombreCreatures;

    private Label labelNbCreatures;
    private Label.LabelStyle styleLabel;
    private BitmapFont bitmapLabel;

    private Label labelNbCreaturesNonValide;

    private ImageButton boutonValider;

    private Label labelAltLayer;

    private CheckBox altLayer;
    private CheckBox.CheckBoxStyle styleAltLayer;

    private boolean nbCreatureValide;
    private boolean retourEcranDemarrage;
    private boolean pasDeChangement;
    private boolean altLayerChecked;



    public EcranOptions(){

        ConstantesBiologiques.AltLayer = false;

        /* Initialisation des booléens */
        this.pasDeChangement = true;
        this.retourEcranDemarrage = false;
        this.nbCreatureValide = false;
        this.altLayerChecked = false;

        this.table = new Table();
        this.stage = new Stage();

        /* Label "Nombre de créatures" */
        this.bitmapLabel = new BitmapFont(Gdx.files.internal("default.fnt"));
        this.styleLabel = new Label.LabelStyle(this.bitmapLabel, Color.WHITE);
        this.labelNbCreatures = new Label("Nombre de creatures :", this.styleLabel);

        /* Style du champ à remplir "Nombre de créatures" */
        this.styleNombreCreatures = new TextField.TextFieldStyle(new BitmapFont(Gdx.files.internal("default.fnt")), Color.WHITE, null, null, null);
        this.styleNombreCreatures.fontColor = Color.BLACK;
        this.styleNombreCreatures.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/case_nb_creatures.png")));
        this.styleNombreCreatures.cursor = new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/curseur_nb_creatures.png")));

        /* Champ à remplir "Nombre de créatures */
        this.nombreCreatures = new TextField("", this.styleNombreCreatures);
        this.nombreCreatures.setWidth(200);
        this.nombreCreatures.setMessageText("100");  // nombre de créatures par défaut
        this.nombreCreatures.setTextFieldListener(new GestionNombreCreatures());

        /* Label "Nombre de créatures non valide !" */
        this.labelNbCreaturesNonValide = new Label("Nombre de creatures non valide !", this.styleLabel);
        this.labelNbCreaturesNonValide.setVisible(false);

        /* Label "Affichage des altitudes" */
        this.labelAltLayer = new Label("Affichage des altitudes :", this.styleLabel);

        /* Style de la checkbox "Affichage de l'altitude" */
        this.styleAltLayer = new CheckBox.CheckBoxStyle(null, null, new BitmapFont(Gdx.files.internal("default.fnt")), Color.WHITE);
        this.styleAltLayer.checkboxOn = new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/checkedOn.png")));
        this.styleAltLayer.checkboxOff = new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/checkedOff.png")));

        /* CheckBox "Affichage de l'altitude" */
        this.altLayer = new CheckBox("", this.styleAltLayer);
        this.altLayer.addListener(new GestionCheckedBox());

        /* Bouton "Valider les options" */
        this.boutonValider = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/retour_ecran_dem.png"))));
        this.boutonValider.addListener(new GestionBoutonValider());

        /* Ajout des éléments à la table */
        this.table.setFillParent(true);
        this.table.add(this.labelNbCreatures);
        this.table.row();
        this.table.add(this.nombreCreatures);
        this.table.row();
        this.table.add(this.labelNbCreaturesNonValide);
        this.table.row();
        this.table.add(this.labelAltLayer).pad(50);
        this.table.row();
        this.table.add(this.altLayer).pad(-50);
        this.table.row();
        this.table.add(this.boutonValider).pad(100);



        /* Ajout de la table à la stage */
        this.stage.addActor(this.table);
        //Gdx.input.setInputProcessor(stage);
        //this.stage.
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        //Gdx.input.setInputProcessor(this.stage);
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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


    public boolean isRetourEcranDemarrage() {
        return retourEcranDemarrage;
    }

    public void setRetourEcranDemarrage(boolean retourEcranDemarrage) {
        this.retourEcranDemarrage = retourEcranDemarrage;
    }

    public boolean getAltLayerChecked(){
        return this.altLayerChecked;
    }

    public class GestionNombreCreatures implements TextField.TextFieldListener{

        @Override
        public void keyTyped(TextField textField, char c) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                try {
                    int nbCreat = Integer.parseInt(nombreCreatures.getText());
                    nbCreatureValide = true;
                    pasDeChangement = false;
                    labelNbCreaturesNonValide.setVisible(false);
                }
                catch (NumberFormatException e)
                {
                    labelNbCreaturesNonValide.setVisible(true);
                    nbCreatureValide = false;
                }
            }
        }
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

    public class GestionCheckedBox extends ChangeListener {

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            ConstantesBiologiques.AltLayer = !ConstantesBiologiques.AltLayer;
            altLayerChecked = !altLayerChecked;
        }
    }

}
