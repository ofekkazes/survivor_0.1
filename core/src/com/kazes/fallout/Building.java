package com.kazes.fallout;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class Building extends Actor
{
    private Texture texture;
    private BitmapFont font;
    private String name;
    private boolean placed;

    private int level;

    public Building(String name, Vector2 position, String filename, boolean isPlaced) {
        this.texture = (new Texture(Gdx.files.internal(filename)));
       /* this.texture.setSize(100, 100);
        this.texture.flip(false, true);
        this.texture.setPosition(position.x - this.texture.getWidth() / 2, position.y - this.texture.getHeight() / 2);*/
        this.name = name;
        this.placed = isPlaced;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("truetypefont/roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        parameter.flip = true;
        parameter.shadowColor = new Color(0, 0.5f, 0, 0.75f);
        font = generator.generateFont(parameter);
        generator.dispose();

        setBounds(getX(),getY(),texture.getWidth(),texture.getHeight());
        setPos(new Vector2(position.x - this.texture.getWidth() / 2, position.y - this.texture.getHeight() / 2));
        //setPos(new Vector2(position.x - this.texture.getWidth() / 2, Gdx.graphics.getHeight() - position.y - this.texture.getHeight() / 2));
        setSize(150, 150);
        setColor(1, 1, 1, 0.3f);

        //IDEA have regions at the map an give the player an option of what building to assign to the region

        addListener(new DragListener() {


            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                if(!placed) {
                    setPos(new Vector2(x - texture.getWidth() / 2, y - texture.getHeight() / 2));
                    if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched()) {
                        placed = true;
                    }
                }
            }
        });

        this.level = 1;
    }

    public void update() {

    }
    public void update(Vector2 mousePos) {
        if(!placed) {
            this.setPos(new Vector2(mousePos.x - this.texture.getWidth() / 2, mousePos.y - this.texture.getHeight() / 2));
            if(Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Gdx.input.justTouched()) {
                this.placed = true;
            }
        }

    }

    public void render(SpriteBatch batch, float alpha) {
        if(!placed) {
            //this.texture.draw(batch, 0.78f);
            //font.draw(batch, name, this.texture.getX() + 10, this.texture.getY() );
        }
        else {
            //this.texture.draw(batch);
            font.draw(batch, name, this.getX() + 10, this.getY() );
        }


    }

    @Override
    public void draw(Batch batch, float alpha){
        if(!placed) {
            float lastAlpha = batch.getColor().a;
            batch.setColor(1, 1, 1, .78f);
            batch.draw(texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                    this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                    (int) texture.getWidth(), (int) texture.getHeight(), false, false);
            batch.setColor(1, 1, 1, lastAlpha);
        }
        else {
            batch.draw(texture, this.getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                    this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                    (int) texture.getWidth(), (int) texture.getHeight(), false, false);
            font.draw(batch, name, this.getX() + 10, this.getY() );
        }
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public boolean getPlaced() { return this.placed; }

    public void setName(String name) { this.name = name; }

    public String getName() { return this.name; }

    public Rectangle getPos() {  return new Rectangle(this.getX(), this.getY(), this.texture.getWidth(), this.texture.getHeight()); }

    public void setPos(Vector2 pos) {
        this.setPosition(pos.x, pos.y);
    }

    public void upgradeLevel() {
        level++;
    }

    public int getLevel() { return this.level; }
}
