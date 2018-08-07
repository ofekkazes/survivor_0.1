package com.kazes.fallout;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kazes.fallout.gui.Button;
//import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import java.util.ArrayList;

public class Colony extends Game implements ApplicationListener {
    private static final int WORLD_WIDTH = 1000;
    private static final int WORLD_HEIGHT = 1000;

    private OrthographicCamera camera;

    private Sprite mapSprite;
    private Vector3 turnLocation;
    private Vector3 topPanel;
    private Vector3 survivorButton;

    private boolean firstRun;

    private DropdownMenu menu;
    private DropdownMenu buildingMenu;
    private DropdownMenu survivorMenu;

    private int turns;
    private int bank;
    public static ArrayList<Survivor> survivors;
    private ArrayList<Building> buildings;
    private int zombies;
    private int meds;

    private JobMaster jobMaster;

    private Window window;

    Button notificationButton;

    //Constructor (sort of)
    @Override
    public void create() {
        SuperObject.init();
        {
            camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            camera.zoom = 30;
            Viewport viewport = new ScreenViewport(camera);
            viewport.apply();
            camera.position.set(500, 500, 0);
            camera.update();
        }

        this.firstRun = true;

        mapSprite = new Sprite(new Texture(Gdx.files.internal("sc_map.png")));
        mapSprite.setPosition(0, 0);
        mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        mapSprite.flip(false, true);

        this.turnLocation = new Vector3();
        this.topPanel = new Vector3();
        this.survivorButton = new Vector3();

        String[] items = {"Buy Building", "Buy Men", "Buy Soldiers", "Buy Medicine", "Five"};
        this.menu = new DropdownMenu(items);

        String[] buildings = {"House", "Base", "Hospital"};
        this.buildingMenu = new DropdownMenu(buildings);

        String[] survivors = {"Person", "Soldier"};
        this.survivorMenu = new DropdownMenu(survivors);

        this.turns = 0;
        this.bank = 1150;
        this.buildings = new ArrayList<Building>();
        this.survivors = new ArrayList<Survivor>();
        this.zombies = 0;
        this.meds = 10;

        this.jobMaster = new JobMaster();

        //this.buildings.add(new House(new Vector2(287, 83)));
        //this.buildings.add(new House(new Vector2(200, 392)));
        this.buildings.add(new Base(new Vector2(778, 132), true));
        //this.buildings.add(new Hospital(new Vector2(795, 410)));
        this.buildings.add(new House(new Vector2(158, 699), true));
        this.buildings.add(new ColonyCenter(new Vector2(661, 332), true));

        for(int i = 0; i < this.buildings.size(); i++) {
            this.buildings.get(i).setPlaced(true);
        }

        this.notificationButton = new Button("notificationButton", new java.awt.Rectangle(Gdx.graphics.getWidth() - 200, Gdx.graphics.getHeight() - 150, 30, 30), 15f);
        //this.window = new Window("Window", Window.);
    }

    //Runs before the render function
    private void update() {
        SuperObject.updateBegin(this.camera);
        this.notificationButton.update();
        this.handleInput();

        camera.update();
        SuperObject.shapeRenderer.setProjectionMatrix(camera.combined);

        for(Building b : this.buildings) {
            b.update(SuperObject.mouseInWorld2D);
        }
        for(Survivor s : this.survivors) {

        }

        this.jobMaster.update();

        this.menu.update(SuperObject.mouseInWorld2D, true);
        this.buildingMenu.update(SuperObject.mouseInWorld2D, false);
        this.survivorMenu.update(SuperObject.mouseInWorld2D, false);
        this.menuSelection();

        SuperObject.debug.add(".//Debug");
        SuperObject.debug.add("Mouse pos: " + SuperObject.mouseInWorld2D);
        SuperObject.debug.add("Turn " + this.turns);
        SuperObject.debug.add("Bank: " + this.bank + " shmeckels");
        SuperObject.debug.add("Survivors: " + this.survivors.size());
        SuperObject.debug.add("Remaining meds: " + this.meds);
        SuperObject.debug.add("Zombies: " + this.zombies);

        if(this.firstRun) {
            if(this.buildings.size() == 0 )
                this.buildings.add(new ColonyCenter(SuperObject.mouseInWorld2D, false));
            if(this.buildings.get(0).getPlaced())
                changeTurn();

        }


        zombieAttack();
        SuperObject.updateEnd();
    }

    //Draw objects to screen
    @Override
    public void render() {
        super.render();
        this.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        SuperObject.gameBatch.setProjectionMatrix(camera.combined);

        SuperObject.gameBatch.begin();
        {
            this.mapSprite.draw(SuperObject.gameBatch);
            for (int i = 0; i < this.buildings.size(); i++) {
                buildings.get(i).render(SuperObject.gameBatch, 1f);
            }
        }
        SuperObject.gameBatch.end();
        this.menu.render(SuperObject.worldFont, SuperObject.gameBatch, this.camera);
        this.buildingMenu.render(SuperObject.worldFont, SuperObject.gameBatch, this.camera);
        this.survivorMenu.render(SuperObject.worldFont, SuperObject.gameBatch, this.camera);

        this.turnLocation = new Vector3(Gdx.graphics.getWidth() - 125, Gdx.graphics.getHeight() - 100, 0);
        this.topPanel = new Vector3(Gdx.graphics.getWidth() / 2 - 200, 0, 0);
        this.survivorButton = new Vector3(0, 20, 0);
        camera.unproject(turnLocation);
        camera.unproject(topPanel);
        camera.unproject(survivorButton);

        this.notificationButton.render(camera);

        SuperObject.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        {

            //shapeRenderer.line(new Vector2(Gdx.graphics.getWidth() / 2, 0), new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight()));
            SuperObject.roundedRect(topPanel.x, topPanel.y, 360, 30, 10, SuperObject.shapeRenderer);
            SuperObject.roundedRect(this.turnLocation.x, this.turnLocation.y, 110, 30, 5, SuperObject.shapeRenderer);
            SuperObject.roundedRect(this.survivorButton.x, this.survivorButton.y, 70, 30, 5, SuperObject.shapeRenderer);
        }
        SuperObject.shapeRenderer.end();

        SuperObject.windowBatch.begin();
        {

            for (int i = 0; i < SuperObject.debug.size(); i++) {
                SuperObject.simpleFont.draw(SuperObject.windowBatch, SuperObject.debug.get(i), 0, Gdx.graphics.getHeight() - 5 - i * 20 - 60);
            }
            SuperObject.windowFont.draw(SuperObject.windowBatch, "Turn " + this.turns, Gdx.graphics.getWidth() / 2 - 20, 15);
            SuperObject.windowFont.draw(SuperObject.windowBatch, "End the turn", Gdx.graphics.getWidth() - 110,  100 - SuperObject.windowFont.getLineHeight() / 2 - 2);
            SuperObject.windowFont.draw(SuperObject.windowBatch, "Survivors", 5,  Gdx.graphics.getHeight() - 20 - SuperObject.windowFont.getLineHeight() / 2 - 2);
            if(this.turns == 0) SuperObject.windowFont.draw(SuperObject.windowBatch, "Place the center of the colony", Gdx.graphics.getWidth() / 2 - 85, Gdx.graphics.getHeight() / 2 - SuperObject.windowFont.getLineHeight() / 2);
        }
        SuperObject.windowBatch.end();

        jobMaster.render(camera);


    }

    //Helping gc do its job
    @Override
    public void dispose() {
        super.dispose();
        //this.killGame();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 30f;
        camera.viewportHeight = 30f * height/width;

        camera.update();
    }

    //All input from the player is processed here
    private void handleInput() {
        /*if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            turn++;
        }*/
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        /*if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            this.inputMenu = 1;
            this.init();
        }*/

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.zoom += 0.2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.zoom -= 0.2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-2, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(2, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, -2, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, 2, 0);
        }
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            camera.translate(SuperObject.mouseChange2D);
        }
        /*if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.rotate(rotationSpeed, 0, 0, 1);
        }*/
        Rectangle turn = new Rectangle(this.turnLocation.x, this.turnLocation.y, 110, 30);
        if(turn.contains(SuperObject.mouseInWorld2D)) {
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched()){
                SuperObject.shapeSelected = true;
                changeTurn();
            }
            else if(!Gdx.input.isTouched())
                SuperObject.shapeSelected = false;
        }
        //jobMaster.show();
        Rectangle survivorsRec = new Rectangle(this.survivorButton.x, this.survivorButton.y, 70, 30);
        if(survivorsRec.contains(SuperObject.mouseInWorld2D)) {
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched()){
                SuperObject.shapeSelected = true;
                jobMaster.show();
                //jobMaster.show = true;
            }
            else if(!Gdx.input.isTouched())
                SuperObject.shapeSelected = false;
        }

        //Limit the viewable map size
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 50);
        camera.position.x = MathUtils.clamp(camera.position.x, -500, 1500);
        camera.position.y = MathUtils.clamp(camera.position.y, -500, 1500);


    }

    //Checks for the dropdown menus input
    private void menuSelection() {
        /*if(this.menu.isSelected() != -1) {
            if (this.menu.isSelected() == 0) {
                if(gui.addMoney(-1200)) {
                    //this.buildings.add(new House(mouseInWorld2D));
                    b = new House(mouseInWorld2D);
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

        }*/
        if(this.menu.isSelected() != -1) {
            switch(this.menu.isSelected()) {
                case 0:
                    //BUILDING
                    this.buildingMenu.show();
                    break;
                case 1:
                    //MEN
                    this.survivorMenu.show();
                    //System.out.println("1");
                    break;
                case 2:
                    //SOLDIERS
                    break;
                case 3:
                    //MEDICINE
                    if(this.addMoney(150 * 10)) this.meds += 10;
                    break;
                case 4:
                    break;
            }
            this.menu.cancelSelection();
        }
        if(this.buildingMenu.isSelected() != -1) {
            switch(this.buildingMenu.isSelected()) {
                case 0:
                    //HOUSE
                    if(this.addMoney(-1200)) this.buildings.add(new House(SuperObject.mouseInWorld2D, false));
                    break;
                case 1:
                    //BASE
                    if(this.addMoney(-1200)) this.buildings.add(new Base(SuperObject.mouseInWorld2D, false));
                    break;
                case 2:
                    //HOSPITAL
                    if(this.addMoney(-1200)) this.buildings.add(new Hospital(SuperObject.mouseInWorld2D, false));
                    break;

            }
            this.buildingMenu.cancelSelection();
        }

        if(this.survivorMenu.isSelected() != -1) {
            switch(this.survivorMenu.isSelected()) {
                case 0:
                    //PERSON
                    if(this.addMoney(-350)) this.survivors.add(new Person());
                    break;
                case 1:
                    //SOLDIER
                    if(this.addMoney(-350)) this.survivors.add(new Soldier());
                    break;

            }
            this.survivorMenu.cancelSelection();
        }
    }

    //A turn based game needs to be changed once a turn
    private void changeTurn() {
        this.turns++;
        if(firstRun) firstRun = false;

        this.bank += 50;

        for (Building b : this.buildings) {
            if(b instanceof House)
                this.bank += 100;
            else if(b instanceof Base)
                this.bank += 75;
            else if(b instanceof Hospital)
                this.bank += 80;
            else this.bank += 150;
        }

        for (Survivor s : this.survivors) {
            if(s instanceof Person)
                this.bank += 100;
            else if(s instanceof Soldier)
                this.bank += 75;
            /*else if(s instanceof Hospital)
                this.bank += 80;
            else this.bank += 150;*/
        }

        this.meds -= this.survivors.size();
        if(this.meds <= 0) {
            System.out.println("GAME OVER");
            this.killGame();
        }

        if(turns > 4 ) zombies = SuperObject.randomizer.nextInt(turns / 4);
    }

    //The most ferocious zombies might attack in this function
    public void zombieAttack() {
        if(zombies > 0) {
             if (getSoldiers().size() > 0) {
                for (int i = 0; i < this.survivors.size(); i++) {
                    if (zombies > 0) {
                        if (this.survivors.get(i) instanceof Soldier) {
                            zombies--;
                            this.survivors.remove(i);
                        }
                    }
                }
            }
            else {
                 while (zombies > 0) {
                     if(this.survivors.size() > 0) {
                         this.survivors.remove(0);
                         zombies--;
                     }
                     else {
                         zombies = 0;
                         SuperObject.debug.add("You Lose");
                         this.killGame();
                     }
                 }
             }
            SuperObject.debug.add("You were attacked by zombies");
        }
    }

    //Official International Worldwide Bank
    public boolean addMoney (int value) {

        if(bank + value > 0) {
            this.bank += value;
            return true;
        }
        return false;

    }

    public ArrayList<Soldier> getSoldiers() {
        ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
        for (Survivor p : this.survivors ) {
            if(p instanceof Soldier)
                soldiers.add((Soldier)p);
        }
        return soldiers;
    }
    public ArrayList<Person> getPersons() {
        ArrayList<Person> persons = new ArrayList<Person>();
        for (Survivor p : this.survivors ) {
            if(p instanceof Person)
                persons.add((Person) p);
        }
        return persons;
    }




    //Save memory for the long term
    public void killGame() {
        SuperObject.dispose();
        this.survivors.clear();
        this.buildings.clear();
        super.dispose();
        //Gdx.app.exit();
    }
}
