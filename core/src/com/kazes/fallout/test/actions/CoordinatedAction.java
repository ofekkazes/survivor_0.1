package com.kazes.fallout.test.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.Array;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Action to add multiple actions to multiple actors at the same instance
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-10-28
 */
public class CoordinatedAction extends Action {
    private Array<Actor> objects;
    Array<Action> actions;

    public CoordinatedAction(Array<Actor> actorsArray, Action action) {
        this.objects = actorsArray;
        actions = new Array<Action>();
        for(int i = 0; i < objects.size; i++) {
            if(action instanceof MoveByAction) {
                MoveByAction moveByAction = new MoveByAction();
                moveByAction.setAmount(((MoveByAction) action).getAmountX(), ((MoveByAction) action).getAmountY());
                moveByAction.setDuration(((MoveByAction) action).getDuration());
                moveByAction.setInterpolation(Interpolation.sine);
                actions.add(moveByAction);
            }else {
                actions.add((Action) cloneObject(action));
            }
            this.objects.get(i).addAction(actions.get(i));
        }
    }

    @Override
    public boolean act(float delta) {
        boolean flag = true;
        for(int i = 0; i < objects.size; i++) {
            if(this.objects.get(i).hasActions()) {
                Gdx.app.log("asd2", this.objects.get(i).getActions().get(0).toString());
                flag = false;
                break;
            }
        }
        return true;
    }

    private static Object cloneObject(Object obj){
        try{
            Object clone = obj.getClass().newInstance();
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if(field.get(obj) == null || Modifier.isFinal(field.getModifiers())){
                    continue;
                }
                if(field.getType().isPrimitive() || field.getType().equals(String.class)
                        || field.getType().getSuperclass().equals(Number.class)
                        || field.getType().equals(Boolean.class)){
                    field.set(clone, field.get(obj));
                }else{
                    Object childObj = field.get(obj);
                    if(childObj == obj){
                        field.set(clone, clone);
                    }else{
                        field.set(clone, cloneObject(field.get(obj)));
                    }
                }
            }
            return clone;
        }catch(Exception e){
            return null;
        }
    }
}
