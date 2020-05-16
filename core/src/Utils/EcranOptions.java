package Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.awt.*;

public class EcranOptions implements Screen {

    private Stage stage;
    private Table table;
    private Skin skin;

    private TextField nombreCreatures;
    private TextField.TextFieldStyle style;
    private MyTextInputListener test;

    public EcranOptions(){
        //this.style = new TextField.TextFieldStyle(new BitmapFont(new FileHandle("test.txt"),new TextureRegion(new Texture(Gdx.files.internal("arbre.png")))), Color.WHITE, null, null, null);
        //this.skin = new Skin( Gdx.files.internal( "ui/defaultskin.json" ));
        this.style = new TextField.TextFieldStyle(new BitmapFont(Gdx.files.internal("default.fnt")), Color.WHITE, null, null, null);
        this.table = new Table();
        this.stage = new Stage();
        this.nombreCreatures = new TextField("Nombre de créatures : ", this.style);
        this.nombreCreatures.setWidth(200);
        int ok = this.nombreCreatures.getMaxLength();
        //this.nombreCreatures.addLister();
        //this.nombreCreatures.appendText("test");
        //this.nombreCreatures.clear();
        System.out.println(ok);
        Gdx.input.setInputProcessor(stage);

        this.test = new MyTextInputListener();

        this.stage.addActor(this.nombreCreatures);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(stage);
        stage.addActor(this.table);
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

    public class MyTextInputListener implements Input.TextInputListener {
        @Override
        public void input (String text) {
        }

        @Override
        public void canceled () {
        }
    }
}
