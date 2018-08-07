package com.kazes.fallout.test;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;


/**
 * An abstract class that adds on top of the game state screen implemented in libGDX
 * by adding more debug logging 
 * 
 * @author Basim
 *
 */
public abstract class AbstractScreen implements Screen{
	
	//The top level game holder
	protected final Survivor game;
	
	//Background colour
    protected Color clear = new Color(0f,0f,0f,1f);

	public AbstractScreen(Survivor game ){
		this.game = game;
	}

	@Override
	public void render(float delta){
		Gdx.gl.glClearColor( clear.r, clear.g, clear.b, clear.a);
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
	}

    public void pauseLogic() { }
    public void resumeLogic() { }

	@Override
	public void hide(){ Gdx.app.log( "Survivor Log", "Hiding screen: " + getName() ); }
	
	@Override
	public void show(){ Gdx.app.log( "Survivor Log", "Showing screen: " + getName() ); }
	
	@Override
	public void resize(int width,int height ){ Gdx.app.log( "Survivor Log", "Resizing screen: " + getName() + " to: " + width + " x " + height ); }

	@Override
	public void pause(){ Gdx.app.log( "Survivor Log", "Pausing screen: " + getName() ); }

	@Override
	public void resume(){ Gdx.app.log( "Survivor Log", "Resuming screen: " + getName() ); }

	@Override
	public void dispose(){ Gdx.app.log( "Survivor Log", "Disposing screen: " + getName() ); }
	
	protected String getName() {
		return getClass().getSimpleName();
	}
}
