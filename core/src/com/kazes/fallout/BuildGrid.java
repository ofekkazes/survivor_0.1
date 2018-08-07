package com.kazes.fallout;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BuildGrid {
    public Map<Vector2, Building> buildGrid;
    public boolean show;
    private ShapeRenderer shapeRenderer;

    public BuildGrid() {
        buildGrid = new HashMap();
        buildGrid.put(new Vector2(0, 0), new House());
        show = true;
        this.shapeRenderer = new ShapeRenderer();
        this.shapeRenderer.setColor(Color.GREEN);
        //System.out.println(buildGrid.values());
        this.init();
    }

    public void init() {
        buildGrid.put(new Vector2(289-50, 79-50), null);
        buildGrid.put(new Vector2(418-50, 174-50), null);
        buildGrid.put(new Vector2(781-50, 130-50), null);
        buildGrid.put(new Vector2(328-50, 288-50), null);
        buildGrid.put(new Vector2(217-50, 387-50), null);
        buildGrid.put(new Vector2(805-50, 429-50), null);
        buildGrid.put(new Vector2(698-50, 551-50), null);
        buildGrid.put(new Vector2(388-50, 577-50), null);
        buildGrid.put(new Vector2(205-50, 734-50), null);
        buildGrid.put(new Vector2(624-50, 730-50), null);
        buildGrid.put(new Vector2(788-50, 820-50), null);
    }

    public boolean addBuilding(Building b) {
        if(b.getPlaced()) {
            Iterator iterator = this.buildGrid.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                if (entry.getValue() == null) {
                    Vector2 pos = (Vector2) entry.getKey();
                    Rectangle block = new Rectangle(pos.x, pos.y, 100, 100);
                    //System.out.println(block);
                    //System.out.println(b.getPos());
                    if (block.overlaps(b.getPos())) {
                        b.setPos((Vector2)entry.getKey());
                        b.setPlaced(true);
                        entry.setValue(b);
                        return true;

                    } else b.setPlaced(false);
                }
            }
        }
        return false;
    }

    public void render(OrthographicCamera camera, SpriteBatch batch) {
        if(this.show) {
            shapeRenderer.setProjectionMatrix(camera.combined);

            {
                /*for (Map.Entry<Vector2, Building> entry : this.buildGrid.entrySet()) {
                    Vector2 pos = entry.getKey();
                    Building building = entry.getValue();
                    if(building != null)
                        shapeRenderer.rect(pos.x, pos.y, 80, 80);

                        System.out.println(pos  + "\n");
                }*/
                Iterator iterator = this.buildGrid.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    if(entry.getValue() == null) {
                        Vector2 pos = (Vector2)entry.getKey();
                        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                        shapeRenderer.rect(pos.x, pos.y, 100, 100);
                        shapeRenderer.end();
                    }
                    else {
                        Building b = (Building)entry.getValue();
                        batch.begin();
                        b.render(batch, 1f);
                        batch.end();
                    }
                }
            }

        }
    }
}
