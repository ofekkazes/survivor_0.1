package com.kazes.fallout.test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.kazes.fallout.*;
import java.util.ArrayList;

public class ColonyScene extends Game implements ApplicationListener {

    Group windowScreen;
    Group gameScreen;

    Group buildings;

    private int turns;
    private boolean scrollable;
    private ArrayList<String> notifications;
    private ArrayList<Person> survivors;
    private int medications;
    private int bank;

    Stage stage;
    private Skin skin;

    @Override
    public void create() {
        SuperObject.init();
        skin = new Skin(Gdx.files.internal("skins/clean-crispy-ui.json"));
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        stage.getBatch().enableBlending();

        windowScreen = new Group();
        gameScreen = new Group();
        stage.addActor(gameScreen);
        stage.addActor(windowScreen);

        this.scrollable = true;
        this.notifications = new ArrayList<String>();
        this.survivors = new ArrayList<Person>();
        this.medications = 15;
        this.addMoney(10500);
        this.notifications.add("You start with 15 medications");
        this.notifications.add("You start with 10500 Shmeckels");

        gameScreen.addActor(new Land("images/maps/sc_map.png"));


        this.buildings = new Group();
        //this.buildings.add(new House(new Vector2(287, 83)));
        //this.buildings.add(new House(new Vector2(200, 392)));
        this.buildings.addActor(new Base(new Vector2(778, Gdx.graphics.getHeight() + 132), true));

        //this.buildings.add(new Hospital(new Vector2(795, 410)));
        this.buildings.addActor(new House(new Vector2(158, Gdx.graphics.getHeight() + 699), true));
        this.buildings.addActor(new ColonyCenter(new Vector2(661, Gdx.graphics.getHeight() + 332), true));


        gameScreen.addActor(buildings);
        gameScreen.addActor(new ColonyCenter(new Vector2(750, 20), true));



        addGUI();
    }

    public void addGUI() {
        final TextButton turnButton = new TextButton("Next turn", skin, "default");
        final TextButton medicationsButton = new TextButton("Get Meds", skin, "default");
        final Window medsWindow = new Window("Medications", skin);
        final Slider medSlider = new Slider( 0, this.bank / 150, 1, false, skin);
        final Label medsValue = new Label(medSlider.getValue()+"", skin);
        final Label medsMax = new Label(this.bank / 150+"", skin);

        final TextButton survivorButton = new TextButton("Survivors", skin, "default");
        final Window survivorWindow = new Window("Survivors", skin);

        final TextButton buildingButton = new TextButton("Buildings", skin, "default");
        final Window buildingWindow = new Window("Buildings", skin);

        turnButton.setWidth(100);
        turnButton.setHeight(30f);
        turnButton.setPosition(Gdx.graphics.getWidth() - turnButton.getWidth() - 10, Gdx.graphics.getHeight()-500);
        turnButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                changeTurn();
            }
        });
        windowScreen.addActor(turnButton);

        medicationsButton.setWidth(100);
        medicationsButton.setHeight(30f);
        medicationsButton.setPosition(0, Gdx.graphics.getHeight() - 60);
        medicationsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                windowScreen.addActor(medsWindow);
                medSlider.setValue(0);
                medSlider.setRange(0, bank / 150);
                medsMax.setText(bank / 150 + "");
            }
        });
        windowScreen.addActor(medicationsButton);


        medsWindow.setMovable(true);
        medsWindow.setSize(300,70);
        medsWindow.setPosition(50, Gdx.graphics.getHeight() - 60);
        medsWindow.addListener(new FocusListener() {
            @Override
            public boolean handle(Event event) {
                scrollable = false;
                return false;
            }
        });

        Table table = new Table(skin);
        medsWindow.add(table);


        table.add(medsValue);
        table.add(medSlider);
        table.add(medsMax);
        medSlider.setWidth(100);
        medSlider.setHeight(30f);
        medSlider.setPosition(medSlider.getParent().getWidth() / 2 - medSlider.getWidth() / 2,medSlider.getParent().getHeight() / 2 - medSlider.getHeight() / 2 - 10);
        medSlider.addListener(new DragListener() {
            @Override
            public boolean handle(Event event) {
                scrollable = false;
                medsValue.setText(medSlider.getValue()+"");
                return false;
            }
        });

        medsMax.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                medSlider.setValue(bank / 150);
            }
        });

        TextButton medsConfirm = new TextButton("Confirm", skin, "default");
        table.add(medsConfirm);
        medsConfirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //if(meds bought)
                if(addMoney(-1 * (int)medSlider.getValue() * 150)) {
                    medications += medSlider.getValue();
                    windowScreen.removeActor(medsWindow);
                }
            }
        });

        survivorButton.setWidth(100);
        survivorButton.setHeight(30f);
        survivorButton.setPosition(100, Gdx.graphics.getHeight() - 60);
        survivorButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                windowScreen.addActor(survivorWindow);

            }
        });
        windowScreen.addActor(survivorButton);

        survivorWindow.setMovable(true);
        survivorWindow.setSize(300,150);
        survivorWindow.setPosition(150, Gdx.graphics.getHeight() - 60);
        survivorWindow.addListener(new FocusListener() {
            @Override
            public boolean handle(Event event) {
                scrollable = false;
                return false;
            }
        });

        final Table survivorTable = new Table();
        survivorTable.add(new Label("Mission 1, 40% chance", skin, "default"));
        survivorTable.row();
        survivorTable.add(new Label("Mission 2, 30% chance", skin, "default"));

        survivorWindow.add(survivorTable);
        final List<String> a = new List<String>(skin);
        a.setItems("asd", "dasd", "asddd");
        survivorTable.add(a);
        a.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                survivorTable.removeActor(a);
            }
        });

        /*/

         */

        buildingButton.setWidth(100);
        buildingButton.setHeight(30f);
        buildingButton.setPosition(200, Gdx.graphics.getHeight() - 60);
        buildingButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                windowScreen.addActor(buildingWindow);

            }
        });
        windowScreen.addActor(buildingButton);

        buildingWindow.setMovable(true);
        buildingWindow.setSize(300,150);
        buildingWindow.setPosition(250, Gdx.graphics.getHeight() - 60);
        buildingWindow.addListener(new FocusListener() {
            @Override
            public boolean handle(Event event) {
                scrollable = false;
                return false;
            }
        });

        final List<String> buildingList = new List<String>(skin);
        buildingList.setItems("House", "Hospital", "Base");
        buildingList.setSize(300, 150);
        buildingWindow.add(buildingList);
        buildingList.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(addMoney(-3000)) {
                    switch (buildingList.getSelectedIndex()) {
                        case 0:
                            buildings.addActor(new House(SuperObject.mouseInWorld2D, false));
                            break;//House
                        case 1:
                            buildings.addActor(new Hospital(SuperObject.mouseInWorld2D, false));
                            break;//Hospital
                        case 2:
                            buildings.addActor(new Base(SuperObject.mouseInWorld2D, false));
                            break;//Base
                    }
                    windowScreen.removeActor(buildingWindow);
                }
            }
        });


    }

    public void update() {
        SuperObject.updateBegin(this.stage.getCamera());
        this.handleInput();

        /*Building tempB = new House();
        for(Actor b : buildings.getChildren().items) {
            if(b instanceof Building)
                tempB = (Building)b;
            tempB.update(SuperObject.mouseInWorld2D);
        }*/


        SuperObject.debug.add(".//Debug");
        SuperObject.debug.add("Mouse pos: " + SuperObject.mouseInWorld2D);
        SuperObject.debug.add("Bank: " + this.bank + " Shmeckels");
        SuperObject.debug.add("Survivors: "+this.survivors.size());
        SuperObject.debug.add("Medications: "+this.medications);
        SuperObject.debug.addAll(this.notifications);

        this.scrollable = true;
        SuperObject.updateEnd();
    }

    @Override
    public void render() {
        super.render();
        this.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        SuperObject.windowBatch.begin();
        for (int i = 0; i < SuperObject.debug.size(); i++) {
            SuperObject.simpleFont.draw(SuperObject.windowBatch, SuperObject.debug.get(i), 0, Gdx.graphics.getHeight() - 5 - i * 20 - 60);
        }
        SuperObject.simpleFont.draw(SuperObject.windowBatch, "Turn: " + this.turns, Gdx.graphics.getWidth() / 2 - 5, SuperObject.simpleFont.getLineHeight() + 10);
        SuperObject.windowBatch.end();
    }

    public void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.gameScreen.setX(this.gameScreen.getX() + 2);
            //buildings.addActor(new Base(SuperObject.mouseInWorld2D));

        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.gameScreen.setX(this.gameScreen.getX() - 2);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.gameScreen.setY(this.gameScreen.getY() - 2);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.gameScreen.setY(this.gameScreen.getY() + 2);
        }
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && scrollable) {
            //this.stage.getCamera().translate(SuperObject.mouseChange3D);
            this.gameScreen.setX(this.gameScreen.getX() - SuperObject.mouseChange3D.x);
            this.gameScreen.setY(this.gameScreen.getY() - SuperObject.mouseChange3D.y);
        }
    }

    private void changeTurn() {
        this.turns++;
        this.notifications.clear();
        if(SuperObject.randomizer.nextInt(4) == 1) {
            int addedPeople = SuperObject.randomizer.nextInt(3);
            if (addedPeople > 0) {
                for (int i = 0; i < addedPeople; i++)
                    this.survivors.add(new Person());
                this.notifications.add(addedPeople + " runaways have joined your colony");
            }
        }

        int killStreak = 0;
        for (int i = 0; i < this.survivors.size(); i++) {
            this.medications--;
            if(this.medications <= 0) {
                this.survivors.remove(0);
                killStreak++;
            }
        }
        if(killStreak > 0) {
            this.notifications.add("You did not have enough medications");
            if(killStreak == 1) this.notifications.add("a survivor died");
            else this.notifications.add(killStreak + " survivors died");
        }

        this.addMoney(127 * this.survivors.size());

        if(this.survivors.size() <= 0)
            this.notifications.add("Game Over");
    }

    //Official International Worldwide Bank
    public boolean addMoney (int value) {

        if(bank + value >= 0) {
            this.bank += value;
            return true;
        }
        return false;

    }
}
