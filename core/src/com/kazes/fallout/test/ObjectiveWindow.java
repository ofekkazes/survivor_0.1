package com.kazes.fallout.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;

public class ObjectiveWindow extends WindowEx {

    private Table missions;
    private Array<Mission> missionsArray;

    public ObjectiveWindow() {
        super(Assets.getAsset(Assets.UI_SKIN, Skin.class));
        this.setWidth(Gdx.graphics.getWidth() / 3f);
        this.setHeight(Gdx.graphics.getHeight() / 4f);
        this.setY(Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 8f - this.getHeight());
        this.setVisible(true);
        this.setVisible(false);

        missions = new Table();
        missionsArray = new Array<Mission>();

        ScrollPane scrollPane = new ScrollPane(missions);
        this.add(scrollPane);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for(int i = 0; i < missionsArray.size; i++) {
            missionsArray.get(i).update();
            if(missionsArray.get(i).isCompleted()) {
                removeMission(i);
                return;
            }
        }
    }

    public void addMission(Mission mission, Object requirment) {
        mission.addRequirment(requirment);
        missionsArray.add(mission);

        Label missionMessage = new Label(mission.getMessage(), Assets.getAsset(Assets.UI_SKIN, Skin.class));
        missions.add(missionMessage).row();
    }
    public void addMission(Mission mission, Object requirment, String message) {
        mission.addRequirment(requirment);
        mission.addMessage(message);
        missionsArray.add(mission);

        Label missionMessage = new Label(mission.getMessage(), Assets.getAsset(Assets.UI_SKIN, Skin.class));
        missions.add(missionMessage).left().top().row();
    }

    public void removeMission(int index) {
        for(Cell cell : missions.getCells()) {
            if(cell.getActor() != null) {
                if (((Label) cell.getActor()).getText().toString().contains(missionsArray.get(index).getMessage())) {
                    missions.removeActor(cell.getActor());
                    missionsArray.removeIndex(index);
                    return;
                }
            }
        }
    }
}
