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

    /* La table et sa stage associée */
    private Stage stage;
    private Table table;

    /* Le style utilisé pour les labels */
    private Label.LabelStyle styleLabel;
    private BitmapFont bitmapLabel;

    /* Le style utilisé pour les champs à remplir */
    private TextField.TextFieldStyle styleTextField;

    /* Les champs à remplir */
    private TextField nombreCreatures;
    private TextField nombreRessources;

    /* Les labels associés aux champs à remplir */
    private Label labelNbCreatures;
    private Label labelNbRessources;

    /* Les labels s'affichant quand les champs à remplir ne sont pas corrects */
    private Label labelNbCreaturesNonValide;
    private Label labelNbRessourcesNonValide;

    /* La checkbox, son style et son label */
    private Label labelAltLayer;
    private CheckBox.CheckBoxStyle styleAltLayer;
    private CheckBox altLayer;

    /* Le bouton */
    private ImageButton boutonValider;

    /* Les booleens d'état de la classe */
    private boolean nbCreatureValide;
    private boolean nbRessourceValide;
    private boolean retourEcranDemarrage;
    private boolean pasDeChangement;



    public EcranOptions(){

        ConstantesBiologiques.AltLayer = false;

        /* Initialisation des booléens */
        this.pasDeChangement = true;
        this.retourEcranDemarrage = false;
        this.nbCreatureValide = false;

        /* Initialisation de la table et de la stage */
        this.table = new Table();
        this.stage = new Stage();

        /* Style des champs à remplir */
        this.styleTextField = new TextField.TextFieldStyle(new BitmapFont(Gdx.files.internal("default.fnt")), Color.WHITE, null, null, null);
        this.styleTextField.fontColor = Color.BLACK;
        this.styleTextField.background = new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/case.png")));
        this.styleTextField.cursor = new TextureRegionDrawable(new Texture(Gdx.files.internal("ui/curseur.png")));

        /* Label "Nombre de créatures" */
        this.bitmapLabel = new BitmapFont(Gdx.files.internal("default.fnt"));
        this.styleLabel = new Label.LabelStyle(this.bitmapLabel, Color.WHITE);
        this.labelNbCreatures = new Label("Nombre de creatures :", this.styleLabel);

        /* Champ à remplir "Nombre de créatures */
        this.nombreCreatures = new TextField("", this.styleTextField);
        //this.nombreCreatures.setWidth(200);
        this.nombreCreatures.setMessageText("100");  // nombre de créatures par défaut
        this.nombreCreatures.setTextFieldListener(new GestionNombreCreatures());

        /* Label "Nombre de créatures non valide !" */
        this.labelNbCreaturesNonValide = new Label("Nombre de creatures non valide !", this.styleLabel);
        this.labelNbCreaturesNonValide.setVisible(false);

        /* Label "Nombre de ressources" */
        this.labelNbRessources = new Label("Nombre de ressources :", this.styleLabel);

        /* Champ à remplir "Nombre de ressources" */
        this.nombreRessources = new TextField("", this.styleTextField);
        this.nombreRessources.setMessageText("25");  // nombre de créatures par défaut
        this.nombreRessources.setTextFieldListener(new GestionNombreRessources());

        /* Label "Nombre de ressources non valide !" */
        this.labelNbRessourcesNonValide = new Label("Nombre de ressources non valide !", this.styleLabel);
        this.labelNbRessourcesNonValide.setVisible(false);

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
        this.table.left();
        this.table.add(this.labelNbCreatures).width(0);
        this.table.add(this.nombreCreatures);
        this.table.row();
        this.table.add(this.labelNbCreaturesNonValide).width(0);
        this.table.row();
        this.table.add(this.labelNbRessources).width(0);
        this.table.add(this.nombreRessources);
        this.table.row();
        this.table.add(this.labelNbRessourcesNonValide).width(0);
        this.table.row();
        this.table.add(this.labelAltLayer).width(0).pad(20);
        //this.table.row();
        this.table.add(this.altLayer);
        this.table.row();
        this.table.add(this.boutonValider).pad(100);


        /* Ajout de la table à la stage */
        this.stage.addActor(this.table);
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

    /**
     * Méthode appelée lorsque l'écran n'est plus l'écran courant
     */
    @Override
    public void hide() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }


    /* Section concernant les getters et les setters */

    public TextField getNombreCreatures() {
        return this.nombreCreatures;
    }

    public TextField getNombreRessources(){return this.nombreRessources;}

    public boolean isNbCreatureValide() {
        return nbCreatureValide;
    }

    public boolean isNbRessourceValide(){ return nbRessourceValide; }


    public boolean isRetourEcranDemarrage() {
        return retourEcranDemarrage;
    }

    public void setRetourEcranDemarrage(boolean retourEcranDemarrage) {
        this.retourEcranDemarrage = retourEcranDemarrage;
    }

    /**
     * Classe s'occupant de la gestion du champ à remplir "Nombre de créatures"
     */
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

    /**
     * Classe s'occupant de la gestion du champ "Nombre de ressources"
     */
    public class GestionNombreRessources implements TextField.TextFieldListener{
        @Override
        public void keyTyped(TextField textField, char c) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                try {
                    int nbRessources = Integer.parseInt(nombreRessources.getText());
                    nbRessourceValide = true;
                    pasDeChangement = false;
                    labelNbRessourcesNonValide.setVisible(false);
                }
                catch (NumberFormatException e)
                {
                    labelNbRessourcesNonValide.setVisible(true);
                    nbRessourceValide = false;
                }
            }
        }
    }

    /**
     * Classe s'occupant de la gestion du bouton "Retour à l'écran de démarrage"
     */
    public class GestionBoutonValider extends InputListener {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
            if (nbCreatureValide || pasDeChangement) {
                retourEcranDemarrage = true;
            }
            return true;
        }
    }

    /**
     * Classe d'occupant de la gestion de la checkbox "Affichage des altitudes"
     */
    public class GestionCheckedBox extends ChangeListener {

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            ConstantesBiologiques.AltLayer = !ConstantesBiologiques.AltLayer;
        }
    }
}
