package com.kazes.fallout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class GUI {
    private ShapeRenderer shapeRenderer;

    private Sprite topBar;
    private Sprite survivorCount;

    private int turn;
    Vector3 turnLocation;

    private int money;
    private ArrayList<Survivor> survivors;
    private int zombies;

    private RandomXS128 rnd;
    private ArrayList<String> turnBonus;

    public GUI() {
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setColor(0.8f, 0.1f, 0.4f, 1);

        this.topBar = new Sprite(new Texture(Gdx.files.internal("top_bar.png")));
        this.topBar.setSize(500, 35);
        this.topBar.setPosition(Gdx.graphics.getWidth() / 2 - this.topBar.getWidth() / 2, Gdx.graphics.getHeight() - this.topBar.getHeight());
        //this.topBar.flip(false, true);
        this.survivorCount = new Sprite(new Texture(Gdx.files.internal("survivor.png")));
        this.survivorCount.setSize(50, 30);
        this.survivorCount.setPosition(280, Gdx.graphics.getHeight() - this.survivorCount.getHeight() - 2);

        this.turn = 0;
        this.turnLocation = new Vector3(Gdx.graphics.getWidth() - 110, Gdx.graphics.getHeight() - 100, 0);
        this.money = 0;
        this.survivors = new ArrayList<Survivor>();
        this.survivors.add(new Person());
        this.zombies = 0;

        this.rnd = new RandomXS128();
        this.turnBonus = new ArrayList<String>();
    }

    public void update(Vector2 mousePos) {
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched() &&
                mousePos.x >= this.turnLocation.x && mousePos.x <= this.turnLocation.x + 100 &&
                mousePos.y >= this.turnLocation.y && mousePos.y <= this.turnLocation.y + 30) {
            this.turnBonus = changeTurn();
        }
    }

    public ArrayList<String> changeTurn() {
        ArrayList<String> bonus = new ArrayList<String>();
        this.turn++;
        this.money += 100;
        this.money += (this.survivors.size()) * 127;

        if(rnd.nextInt(turn * Gdx.app.getLogLevel()) % 9 == 0) {

            bonus.add("A few men just got to your colony");
        }
        if(rnd.nextInt(turn * Gdx.app.getLogLevel()) % 6 == 0) {
            zombies = rnd.nextInt(4);

            for(int i = 0; i< this.survivors.size() && zombies > 0; i++) {
                if(this.survivors.get(i) instanceof Soldier) {
                    this.survivors.remove(i);
                    this.zombies--;
                }
            }
            if(zombies > 0)
                bonus.add("GAME OVER");

            bonus.add("You were attacked by zombies");
        }
        return bonus;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        this.turnLocation = new Vector3(Gdx.graphics.getWidth() - 110, Gdx.graphics.getHeight() - 100, 0);
        camera.unproject(turnLocation);

        this.topBar.draw(batch);
        this.survivorCount.draw(batch);
        Functions.windowFont.draw(batch, this.survivors.size() + "", this.survivorCount.getX() + this.survivorCount.getWidth() + 10, this.survivorCount.getY() + Functions.windowFont.getLineHeight());


        batch.end();

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        {

            this.shapeRenderer.rect(turnLocation.x, turnLocation.y , 110, 30);

        }
        this.shapeRenderer.end();
        batch.begin();

        Functions.windowFont.draw(batch, "Next Turn (" + (this.turn + 1) + ")", Gdx.graphics.getWidth() - 100,  85);

    }

    public int getTurn() {
        return turn;
    }

    public int getMoney() {
        return money;
    }

    public ArrayList<Survivor> getMen() {
        return this.survivors;
    }

    public void addPerson() {
        this.survivors.add(new Person());
    }
    public void addSoldier() {
        this.survivors.add(new Soldier());
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

    public boolean addMoney (int value) {

        if(money + value > 0) {
            this.money += value;
            return true;
        }
        return false;

    }

    public ArrayList<String> getBonus() {
        return this.turnBonus;
    }
}
