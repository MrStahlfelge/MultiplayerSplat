package com.mygdx.game.systems;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.datamodels.PlayerData;

public class DrawInGameGuiSystem {

	private MyGdxGame game;
	private SpriteBatch batch;
	private Sprite[] players = new Sprite[3];
	private Sprite wall;
	int w = Settings.LOGICAL_WIDTH_PIXELS / 20;
	int h = Settings.LOGICAL_WIDTH_PIXELS / 20;

	public DrawInGameGuiSystem(MyGdxGame _game, SpriteBatch _batch) {
		game = _game;
		batch = _batch;
		
		wall = new Sprite(game.getTexture("sprites/redbricks.png"), w, h);
	}


	public void process() {
		// Draw brick border
		for (int x=0 ; x<Settings.LOGICAL_WIDTH_PIXELS ; x+=w) {
			batch.draw(wall, x, 0);
			batch.draw(wall, x, Settings.LOGICAL_HEIGHT_PIXELS-h);
		}
		for (int y=0 ; y<Settings.LOGICAL_HEIGHT_PIXELS ;y+=h) {
			batch.draw(wall, 0, y);
			batch.draw(wall, Settings.LOGICAL_WIDTH_PIXELS-w, y);
		}
		
		
		int num = 0;
		for (PlayerData player : game.players.values()) {
			int xStart = 20+(num*250);
			game.drawFont(batch, "Score: " + player.score, xStart, 90);
			if (player.lives > 0) {
				if (players[num] == null) {
					Texture tex = game.getTexture("sprites/player" + player.imageId + "_right1.png");
					players[num] = new Sprite(tex);
					players[num].setSize(Settings.PLAYER_SIZE, Settings.PLAYER_SIZE);
				}
				for (int i=0 ; i<player.lives ; i++) {
					players[num].setPosition(xStart+(i*20), 30);
					players[num].draw(batch);
				}
			} else {
				game.drawFont(batch, "GAME OVER!", xStart, 40);
			}
			num++;
		}
	}
}
