package com.mygdx.ipop_game;
import static com.badlogic.gdx.Gdx.gl;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;

public class DegreeSelector extends ApplicationAdapter implements Screen {

    private Stage stage;
    Integer number = 0;
    private static OrthographicCamera camera;
    SpriteBatch batch;
    Button btn;
    ButtonStyle tbs;
    TextButton degreeNameBtn,exitBtn,degreeBtn;
    Boolean firstTime = true;

    String selectedDegree = "Selecciona un cicle";

    Table familia,cicles;
    Texture img1, img2, img3, img4, img5, img6, img0;

    TextButton.TextButtonStyle selected,exit,currentDegree;
    ArrayList<String> families = new ArrayList<>();
    ArrayList<Texture> imgFamilies = new ArrayList<>();
    ArrayList<String> familiaInformatica = new ArrayList<>();
    ArrayList<String> familiaAdministratiu = new ArrayList<>();
    ArrayList<String> familiaAutomocio = new ArrayList<>();
    ArrayList<String> familiaProduccio = new ArrayList<>();
    ArrayList<String> familiaMecanica = new ArrayList<>();
    ArrayList<String> familiaAigua = new ArrayList<>();



    @Override
    public void render() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        super.render();
        stage.act();
        stage.draw();
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
        familiaInformatica.add("Desenvolupament d aplicacions multiplataforma");
        familiaInformatica.add("Desenvolupament d aplicacions web");

        familiaAdministratiu.add("Gestio administrativa");
        familiaAdministratiu.add("Administracio i finances");
        familiaAdministratiu.add("Assistencia a la direccio");

        familiaAutomocio.add("Electromecanica de vehicles automobils");
        familiaAutomocio.add("Automocio");

        familiaMecanica.add("Mecanitzacio");
        familiaMecanica.add("Programacio en produccio fabricacio mecanica");

        familiaProduccio.add("Manteniment electromecanics");
        familiaProduccio.add("Mecatronica industrial");

        familiaAigua.add("Gestió de l aigua");

        //Imatges de prova
        img0 = new Texture("preCicles.jpg");

        img1 = new Texture("img1.png");
        img2 = new Texture("img2.jpg");
        img3 = new Texture("img3.jpg");
        img4 = new Texture("img4.jpg");
        img5 = new Texture("img5.jpg");
        img6 = new Texture("img6.jpg");


        imgFamilies.add(img1);
        imgFamilies.add(img2);
        imgFamilies.add(img3);
        imgFamilies.add(img4);
        imgFamilies.add(img5);
        imgFamilies.add(img6);

        //todo Fer una taula que s'actualizi segons la foto que será el cicle formatiu i despres mostra la quanitat de cicles a la taula

        //Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        //make stage
        stage = new Stage();

        //make button style (usually done with skin)
        tbs = new ButtonStyle();
        tbs.up = new TextureRegionDrawable(new TextureRegion(img0 ,(int) (stage.getWidth())/2, (int) (stage.getHeight())));



        // make button
        btn = new Button(tbs);

        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (number + 1 < imgFamilies.size()) {
                    if (firstTime == true) {
                        number = 0;
                        firstTime = false;
                    } else {
                        number += 1;
                    }
                } else {
                    number = 0;
                }
                tbs.up = new TextureRegionDrawable(imgFamilies.get(number));
                System.out.println(families.get(number));

                //Calcular les diferentes families
                cicles.clear();
                Gdx.gl.glClearColor( 255, 255, 255, 0 );
                Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
                ArrayList<String> arrayList = familiaButtons(families.get(number));
                createFamilyButtons(arrayList);
            }
        });

        // ad button to layout table
        familia = new Table();
        familia.add(btn);
        //table.add(btn);
        familia.setPosition(0,0);
        //todo Mirar de separar las tablas
        familia.setSize(stage.getWidth()/3,stage.getHeight());
        familia.setColor(Color.WHITE);
        //add table to stage
        stage.addActor(familia);

        //Taula amb els cicles de la familia
        cicles = new Table();
        cicles.setPosition((float) (stage.getHeight()/2.5),stage.getWidth()/10);
        System.out.println(cicles.getX());
        System.out.println(cicles.getY());
        cicles.setSize(stage.getWidth()/2,stage.getHeight());

        exit = new TextButton.TextButtonStyle();
        exit.font = new BitmapFont();
        exit.fontColor = Color.RED;

        currentDegree = new TextButton.TextButtonStyle();
        currentDegree.font = new BitmapFont();
        currentDegree.fontColor = Color.GREEN;

        stage.addActor(cicles);
        //Segons el cicle afegir els elements a la taula

        //set the stage to be the input processor so it responds to clicks
        Gdx.input.setInputProcessor(stage);

    }

    public ArrayList<String> familiaButtons(String family) {

        selected = new TextButton.TextButtonStyle();
        selected.font = new BitmapFont();
        selected.fontColor = Color.YELLOW;

        //Es podria calcular la longitud del Array per a fer un pad mes dinamic
        if (family.equals("Informatica")) {
            degreeNameBtn = new TextButton(family,selected);
            degreeNameBtn.setTransform(true);
            degreeNameBtn.setScale(7.5f);
            cicles.add(degreeNameBtn).padTop(350);
            cicles.add().row();
            return familiaInformatica;
        } else if (family.equals("Administratiu")) {
            degreeNameBtn = new TextButton(family,selected);
            degreeNameBtn.setTransform(true);
            degreeNameBtn.setScale(7.5f);
            cicles.add(degreeNameBtn).padTop(250);
            cicles.add().row();
            return familiaAdministratiu;

        } else if (family.equals("Automocio")) {
            degreeNameBtn = new TextButton(family,selected);
            degreeNameBtn.setTransform(true);
            degreeNameBtn.setScale(7.5f);
            cicles.add(degreeNameBtn).padTop(250);
            cicles.add().row();
            return familiaAutomocio;

        } else if (family.equals("Manteniment i serveis a la produccio")) {
            degreeNameBtn = new TextButton(family,selected);
            degreeNameBtn.setTransform(true);
            degreeNameBtn.setScale(7.5f);
            cicles.add(degreeNameBtn).padTop(250);
            cicles.add().row();
            return familiaProduccio;

        } else if (family.equals("Fabricacio Mecanica")) {
            degreeNameBtn = new TextButton(family,selected);
            degreeNameBtn.setTransform(true);
            degreeNameBtn.setScale(7.5f);
            cicles.add(degreeNameBtn).padTop(250);
            cicles.add().row();
            return familiaMecanica;

        } else {
            degreeNameBtn = new TextButton(family,selected);
            degreeNameBtn.setTransform(true);
            degreeNameBtn.setScale(7.5f);
            cicles.add(degreeNameBtn).padTop(250);
            cicles.add().row();
            return familiaAigua;
        }
    }

    public void createFamilyButtons (ArrayList<String> aList) {

        ArrayList<Integer> buttonY = new ArrayList<>();
        selected = new TextButton.TextButtonStyle();
        selected.font = new BitmapFont();
        selected.checkedDownFontColor = Color.ORANGE;
        selected.fontColor = Color.LIGHT_GRAY;

        for (int i = 0; i < aList.size(); i++) {

            final String cicle = aList.get(i);
            if (i == 0) {
                buttonY.add((int) (cicles.getHeight()/aList.size()));
            } else {
                buttonY.add(buttonY.get(i-1) + (int) (cicles.getHeight()/aList.size()));
            }



            //selected.up = new TextureRegionDrawable(new TextureRegion(selectedUp));
            //selected.down = new TextureRegionDrawable(new TextureRegion((buttonDownGreen)));
            degreeNameBtn = new TextButton(cicle,selected);
            degreeNameBtn.setTransform(true);
            degreeNameBtn.setScale(6.0f);
            //degreeNameBtn.setPosition(0,buttonY.get(i));
            //degreeNameBtn.setY(buttonY.get(i));
            degreeNameBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);

                    selectedDegree = cicle;
                    degreeBtn.setText(selectedDegree);
                    Gdx.gl.glClearColor( 255, 255, 255, 0 );
                    Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
                }
            });
            cicles.add().row();
            cicles.add(degreeNameBtn).pad(50);
            cicles.add().row();

            //cicles.add(degreeNameBtn).padLeft(10).width(cicles.getWidth()/aList.size()).height(cicles.getHeight()*i);


        }


        degreeBtn = new TextButton(selectedDegree,currentDegree);
        degreeBtn.setTransform(true);
        degreeBtn.setScale(6.0f);

        cicles.add().row();
        cicles.add(degreeBtn).padTop(cicles.getHeight()/8);
        exitBtn = new TextButton("MENU",exit);
        exitBtn.setTransform(true);
        exitBtn.setScale(6.5f);

        cicles.add().row();
        cicles.add(exitBtn).pad(100);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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