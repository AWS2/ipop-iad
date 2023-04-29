package com.mygdx.ipop_game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class DegreeSelector extends ApplicationAdapter implements Screen {

    final IPOP game;
    private Stage stage;
    private static OrthographicCamera camera;
    SpriteBatch batch;
    TextButton degreeNameBtn;

    TextButton.TextButtonStyle selected;
    ArrayList<String> families = new ArrayList<>();
    ArrayList<String> familiaInformatica = new ArrayList<>();
    ArrayList<String> familiaAdministratiu = new ArrayList<>();
    ArrayList<String> familiaAutomocio = new ArrayList<>();
    ArrayList<String> familiaProduccio = new ArrayList<>();
    ArrayList<String> familiaMecanica = new ArrayList<>();
    ArrayList<String> familiaAigua = new ArrayList<>();

    public DegreeSelector(IPOP game) {
        this.game = game;
    }

    @Override
    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        super.render();
        stage.act();
        stage.draw();
        if (degreeNameBtn.isPressed()) {
            System.out.println("PEPE");
            System.out.println(stage.getHeight());
            System.out.println(stage.getWidth());
        }
    }

    @Override
    public void create() {

        //Añadir las familias de ciclos
        families.add("Informatica");
        families.add("Administratiu");
        families.add("Automocio");
        families.add("Manteniment i serveis a la produccio");
        families.add("Fabricacio Mecanica");
        families.add("Aigues");

        //Cicles de les diferents families
        familiaInformatica.add("Sistemes microinformatics i xarxes");
        familiaInformatica.add("Administracio de sistemes informatics en xarxa");
        familiaInformatica.add("Desenvolupament d’aplicacions multiplataforma");
        familiaInformatica.add("Desenvolupament d’aplicacions web");

        familiaAdministratiu.add("Gestio administrativa");
        familiaAdministratiu.add("Administracio i finances");
        familiaAdministratiu.add("Assistencia a la direccio");

        familiaAutomocio.add("Electromecanica de vehicles automobils");
        familiaAutomocio.add("Automocio");

        familiaMecanica.add("Mecanitzacio");
        familiaMecanica.add("Programacio de la produccio en fabricacio mecanica");

        familiaProduccio.add("Manteniment electromecanics");
        familiaProduccio.add("Mecatronica industrial");

        familiaAigua.add("Gestió de l’aigua");

        //todo Fer una taula que s'actualizi segons la foto que será el cicle formatiu i despres mostra la quanitat de cicles a la taula

        //Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        //make stage
        stage = new Stage();

        //create images
        Pixmap pmap = new Pixmap((int) (stage.getHeight()), (int) (stage.getWidth()/2),Format.RGBA4444);
        pmap.setColor(Color.RED);
        pmap.fill();
        Texture buttonUpRed = new Texture(pmap);
        pmap.setColor(Color.GREEN);
        pmap.fill();
        Texture buttonDownGreen = new Texture(pmap);
        pmap.dispose();

        //Texture selectedUp = new Texture("Yes_Check_Circle.png");


        //make button style (usually done with skin)
        ButtonStyle tbs = new ButtonStyle();
        tbs.down = new TextureRegionDrawable(new TextureRegion(buttonDownGreen));
        tbs.up = new TextureRegionDrawable(new TextureRegion(buttonUpRed));

        selected = new TextButton.TextButtonStyle();
        selected.font = new BitmapFont();
        selected.checkedDownFontColor = Color.ORANGE;
        //selected.up = new TextureRegionDrawable(new TextureRegion(selectedUp));
        //selected.down = new TextureRegionDrawable(new TextureRegion((buttonDownGreen)));
        degreeNameBtn = new TextButton("Test",selected);
        // make button
        Button btn = new Button(tbs);


        // ad button to layout table
        Table familia = new Table();
        familia.add(btn);
        //table.add(btn);
        familia.setPosition(0,0);
        //todo Mirar de separar las tablas
        familia.setSize(stage.getWidth()/2,stage.getHeight());
        familia.setColor(Color.WHITE);
        //add table to stage
        stage.addActor(familia);

        //Taula amb els cicles de la familia
        Table cicles = new Table();
        cicles.add(degreeNameBtn);
        cicles.setPosition(stage.getWidth()/2,stage.getHeight());
        stage.addActor(cicles);
        //Segons el cicle afegir els elements a la taula

        //set the stage to be the input processor so it responds to clicks
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //Añadir las familias de ciclos
        families.add("Informatica");
        families.add("Administratiu");
        families.add("Automocio");
        families.add("Manteniment i serveis a la produccio");
        families.add("Fabricacio Mecanica");
        families.add("Aigues");

        //Cicles de les diferents families
        familiaInformatica.add("Sistemes microinformatics i xarxes");
        familiaInformatica.add("Administracio de sistemes informatics en xarxa");
        familiaInformatica.add("Desenvolupament d’aplicacions multiplataforma");
        familiaInformatica.add("Desenvolupament d’aplicacions web");

        familiaAdministratiu.add("Gestio administrativa");
        familiaAdministratiu.add("Administracio i finances");
        familiaAdministratiu.add("Assistencia a la direccio");

        familiaAutomocio.add("Electromecanica de vehicles automobils");
        familiaAutomocio.add("Automocio");

        familiaMecanica.add("Mecanitzacio");
        familiaMecanica.add("Programacio de la produccio en fabricacio mecanica");

        familiaProduccio.add("Manteniment electromecanics");
        familiaProduccio.add("Mecatronica industrial");

        familiaAigua.add("Gestió de l’aigua");

        //todo Fer una taula que s'actualizi segons la foto que será el cicle formatiu i despres mostra la quanitat de cicles a la taula

        //Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        //make stage
        stage = new Stage();

        //create images
        Pixmap pmap = new Pixmap((int) (stage.getHeight()), (int) (stage.getWidth()/2),Format.RGBA4444);
        pmap.setColor(Color.RED);
        pmap.fill();
        Texture buttonUpRed = new Texture(pmap);
        pmap.setColor(Color.GREEN);
        pmap.fill();
        Texture buttonDownGreen = new Texture(pmap);
        pmap.dispose();

        //Texture selectedUp = new Texture("Yes_Check_Circle.png");


        //make button style (usually done with skin)
        ButtonStyle tbs = new ButtonStyle();
        tbs.down = new TextureRegionDrawable(new TextureRegion(buttonDownGreen));
        tbs.up = new TextureRegionDrawable(new TextureRegion(buttonUpRed));

        selected = new TextButton.TextButtonStyle();
        selected.font = new BitmapFont();
        selected.checkedDownFontColor = Color.ORANGE;
        //selected.up = new TextureRegionDrawable(new TextureRegion(selectedUp));
        //selected.down = new TextureRegionDrawable(new TextureRegion((buttonDownGreen)));
        degreeNameBtn = new TextButton("Test",selected);
        // make button
        Button btn = new Button(tbs);


        // ad button to layout table
        Table familia = new Table();
        familia.add(btn);
        //table.add(btn);
        familia.setPosition(0,0);
        //todo Mirar de separar las tablas
        familia.setSize(stage.getWidth()/2,stage.getHeight());
        familia.setColor(Color.WHITE);
        //add table to stage
        stage.addActor(familia);

        //Taula amb els cicles de la familia
        Table cicles = new Table();
        cicles.add(degreeNameBtn);
        cicles.setPosition(stage.getWidth()/2,stage.getHeight());
        stage.addActor(cicles);
        //Segons el cicle afegir els elements a la taula

        //set the stage to be the input processor so it responds to clicks
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}