package com.kazes.fallout.test.screens;

/**
 * Each game screen will need these methods
 * @author Ofek Kazes
 * @version 1.0
 * @since 2018-09-15
 */
public interface GameScreenInterface {

    //Map and parallax loading
    void setMap();

    //Decoration loading
    void setDecor();

    //Player loading
    void setPlayer(float startingPointX);

    //NPCS and their dialog loading
    void setNPCS();

    //Enemies loading
    void setEnemies();

    //Scattered items loading
    void setItems();

    //Input logic
    void proccessInput();


}
