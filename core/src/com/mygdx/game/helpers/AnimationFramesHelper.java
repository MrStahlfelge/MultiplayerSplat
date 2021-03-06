package com.mygdx.game.helpers;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.AnimationCycleComponent;
import com.mygdx.game.components.WalkingAnimationComponent;
import com.scs.basicecs.AbstractEntity;

public class AnimationFramesHelper {

	private HashMap<String, Texture> textures = new HashMap<String, Texture>();
	private MyGdxGame game;
	
	public AnimationFramesHelper(MyGdxGame _game) {
		game = _game;
	}
	
	
	public AnimationCycleComponent generateForCoin(float size) {
		int numFrames = 8;
		AnimationCycleComponent acd = new AnimationCycleComponent(.1f);
		acd.frames = new Sprite[numFrames];
		
		/*for(int i=1 ; i<=8 ; i++) {
			Texture tex = getTexture("coin_0" + i + ".png");
			Sprite sprite = new Sprite(tex);
			sprite.setSize(size * (tex.getWidth()/45f), size);
			acd.frames[i-1] = sprite;
		}*/
		Texture texture = getTexture("sprites/Coin_16x16_Anim.png");
		TextureAtlas atlas = new TextureAtlas();
		for (int i=0 ; i<numFrames ; i++) {
			atlas.addRegion("frame"+i, texture, i*16, 0, 16, 16);
			acd.frames[i] = atlas.createSprite("frame"+i);
			acd.frames[i].setSize(size, size);
		}
		
		
		return acd;
	}
	
	
	public void createPlayersFrames(AbstractEntity player, int num, float w, float h) {
		WalkingAnimationComponent wac = (WalkingAnimationComponent)player.getComponent(WalkingAnimationComponent.class);
		wac.framesLeft = new Sprite[3];
		wac.framesRight = new Sprite[3];
		wac.framesLeft[0] = new Sprite(getTexture("sprites/player" + num + "_right1.png"));
		wac.framesLeft[0].flip(true,  false);
		wac.framesLeft[0].setSize(w, h);
		wac.framesLeft[1] = new Sprite(getTexture("sprites/player" + num + "_right2.png"));
		wac.framesLeft[1].setSize(w, h);
		wac.framesLeft[1].flip(true,  false);
		wac.framesLeft[2] = new Sprite(getTexture("sprites/player" + num + "_right3.png"));
		wac.framesLeft[2].setSize(w, h);
		wac.framesLeft[2].flip(true, false);

		wac.framesRight[0] = new Sprite(getTexture("sprites/player" + num + "_right1.png"));
		wac.framesRight[0].setSize(w, h);
		wac.framesRight[1] = new Sprite(getTexture("sprites/player" + num + "_right2.png"));
		wac.framesRight[1].setSize(w, h);
		wac.framesRight[2] = new Sprite(getTexture("sprites/player" + num + "_right3.png"));
		wac.framesRight[2].setSize(w, h);
		
		wac.idleFrame = wac.framesRight[0];
		
	}


	private Texture getTexture(String filename) {
		if (textures.containsKey(filename)) {
			return textures.get(filename);
		}
		Texture t = game.getTexture(filename);
		textures.put(filename, t);
		return t;
	}


	public void dispose() {
		for(Texture t : this.textures.values()) {
			t.dispose();
		}

	}


}
