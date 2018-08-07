package com.kazes.fallout;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.util.ArrayList;
import java.util.Map;

public class OrthographicCameraExample implements ApplicationListener {

    static final int WORLD_WIDTH = 1000;
    static final int WORLD_HEIGHT = 1000;

    private Vector2 mouseInWorld2D = new Vector2();
    private Vector2 lastMouse = new Vector2();
    private Vector2 mouseChange2D = new Vector2();
    private Vector3 mouseInWorld3D = new Vector3();

    private DropdownMenu menu;

    private OrthographicCamera cam;
    private Viewport viewport;

    private SpriteBatch batch;
    private  SpriteBatch batch2;

    private Sprite mapSprite;

    ArrayList<Building> buildings;
    public static BuildGrid grid;
    Building b;


    private float rotationSpeed;

    private BitmapFont font;

    private int turn;
    public static ArrayList<String> lines;

    int inputMenu;
    int inputInline;
    int type;

    GUI gui;
    ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        Functions.init();

        String[] items = {"Buy Building", "Buy Men", "Buy Soldiers", "Buy Medicine", "Five"};
        menu = new DropdownMenu(items);
        rotationSpeed = 0.3f;

        mapSprite = new Sprite(new Texture(Gdx.files.internal("sc_map.png")));
        mapSprite.setPosition(0, 0);
        mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        mapSprite.flip(false, true);

        shapeRenderer = new ShapeRenderer();

        gui = new GUI();

        this.buildings = new ArrayList<Building>();

        //this.buildings.add(new Hospital());
        //this.buildings.add(new House());
        //this.buildings.add(new Base());
        this.grid = new BuildGrid();

        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        cam= new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.zoom = 30;
        viewport = new ScreenViewport(cam);

        viewport.apply();

        cam.position.set(500, 500, 0);
        cam.update();


        batch = new SpriteBatch();
        batch2 = new SpriteBatch();
        font = new BitmapFont();

        turn = 0;
        lines = new ArrayList<String>();
        this.inputMenu = 0;
        this.inputInline = 0;
        this.type = 0;

        this.init();
    }

    public void init() {
        lines.clear();
        switch (this.inputMenu) {
            case 0:
                lines.add("Welcome To Survival");
                lines.add("Turn " + gui.getTurn());
                lines.add("Money: " + gui.getMoney() + " Shmeckels");
                lines.add("Living in the colony: "+gui.getPersons().size());
                lines.add("Out of them protecting the country: "+gui.getSoldiers().size());
                lines.add("Number of buildings: " + this.buildings.size());

                lines.add(mouseInWorld2D.toString());
                for (String s: this.gui.getBonus()) {
                    lines.add(s);
                }

                break;
            case 1:
                lines.add("How Many Medications? ");
                lines.add("1. 1");
                lines.add("2. 2");
                lines.add("3. 5");
                lines.add("4. 10");
                lines.add("5. 50");
        }

    }

    public void update() {
        init();
        mouseInWorld3D.x = Gdx.input.getX();
        mouseInWorld3D.y = Gdx.input.getY();
        mouseInWorld3D.z = 0;
        cam.unproject(mouseInWorld3D);
        mouseInWorld2D.x = mouseInWorld3D.x;
        mouseInWorld2D.y = mouseInWorld3D.y;

        mouseChange2D = (this.lastMouse.sub(Gdx.input.getX(), Gdx.input.getY()));


        handleInput();
        cam.update();
        shapeRenderer.setProjectionMatrix(cam.combined);
        this.menu.update(mouseInWorld2D, true);
        this.gui.update(mouseInWorld2D);

        if(this.buildings.size() == 0 ) {
            this.gui.getBonus().add("You need to construct a center first");
            this.buildings.add(new ColonyCenter(mouseInWorld2D, false));
        }

        for(Building b : this.buildings) {
            b.update(mouseInWorld2D);
        }

        if(this.menu.isSelected() != -1) {
            if (this.menu.isSelected() == 0) {
                if(gui.addMoney(-1200)) {
                    //this.buildings.add(new House(mouseInWorld2D));
                    b = new House(mouseInWorld2D, false);
                }
                this.menu.cancelSelection();

            }
            if (this.menu.isSelected() == 1) {
                if(this.buildings.size() * 5 >= gui.getMen().size()) {
                    if(gui.addMoney(-300))
                        gui.addPerson();
                    else gui.getBonus().add("Not Enough Money");
                }
                else gui.getBonus().add("Not Enough buildings");
                this.menu.cancelSelection();
            }
            if (this.menu.isSelected() == 2) {
                if(this.buildings.size() * 5 >= gui.getMen().size()) {
                    if(gui.addMoney(-350))
                        gui.addSoldier();
                    else gui.getBonus().add("Not Enough Money");
                }
                else gui.getBonus().add("Not Enough buildings");
                this.menu.cancelSelection();
            }

        }
//CHANGE THE WAY IT WORKS
        if(b != null) {
            b.update(mouseInWorld2D);
//CHANGE THE WAY IT WORKS
                if(grid.addBuilding(b))
                    b = null;
//CHANGE THE WAY IT WORKS
        }



        lastMouse.x = Gdx.input.getX();
        lastMouse.y = Gdx.input.getY();
    }

    @Override
    public void render() {
        this.update();

        batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        mapSprite.draw(batch);
        batch.end();
        this.grid.render(cam, batch);

        batch.begin();
        if(b != null)
            b.render(batch, 1f); //CHANGE THE WAY IT WORKS
        for (int i = 0; i<buildings.size(); i++) {
            buildings.get(i).render(batch, 1f);
        }

        batch.end();
        batch2.begin();

        for (int i = 0; i < lines.size(); i++) {
            font.draw(this.batch2, lines.get(i), 0, Gdx.graphics.getHeight() - 5 - i * 20);
        }
        gui.render(batch2, cam);


        batch2.end();

        this.menu.render(font, batch, cam);

    }



    private void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            turn++;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            this.inputMenu = 1;
            this.init();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.zoom += 0.2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.zoom -= 0.2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.translate(-2, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            cam.translate(2, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.translate(0, -2, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.translate(0, 2, 0);
        }
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            cam.translate(mouseChange2D);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.rotate(rotationSpeed, 0, 0, 1);
        }



        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 50);

        cam.position.x = MathUtils.clamp(cam.position.x, -500, 1500);
        cam.position.y = MathUtils.clamp(cam.position.y, -500, 1500);


    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = 30f;
        cam.viewportHeight = 30f * height/width;
        cam.update();
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        mapSprite.getTexture().dispose();
        batch.dispose();
    }

    @Override
    public void pause() {
    }

}