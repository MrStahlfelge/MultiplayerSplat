package com.mygdx.game.systems;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.CanBeHarmedComponent;
import com.mygdx.game.components.CanCollectComponent;
import com.mygdx.game.components.CollectableComponent;
import com.mygdx.game.components.HarmOnContactComponent;
import com.mygdx.game.datamodels.CollisionResults;
import com.scs.basicecs.AbstractEntity;

public class ProcessCollisionSystem {//implements ISystem {

	private MyGdxGame game;

	public ProcessCollisionSystem(MyGdxGame _game) {//, BasicECS ecs) {
		//super(ecs, null);

		game = _game;
	}


	public void processCollision(AbstractEntity mover, CollisionResults results) {
		// Generic harm
		{
			CanBeHarmedComponent cbh = (CanBeHarmedComponent)mover.getComponent(CanBeHarmedComponent.class);
			if (cbh != null) {
				HarmOnContactComponent hoc = (HarmOnContactComponent)results.collidedWith.getComponent(HarmOnContactComponent.class);
				if (hoc != null) {
					mover.remove();
					return;
				}
			}
		}

		// Collecting - player moves into collectable
		{
			CanCollectComponent ccc = (CanCollectComponent)mover.getComponent(CanCollectComponent.class);
			if (ccc != null) {
				CollectableComponent cc = (CollectableComponent)results.collidedWith.getComponent(CollectableComponent.class);
				if (cc != null) {
					game.collectorSystem.entityCollected(mover, results.collidedWith);
					return;
				}
			}
		}

		// Collecting - collectable moves into player
		{
			CanCollectComponent ccc = (CanCollectComponent)results.collidedWith.getComponent(CanCollectComponent.class);
			if (ccc != null) {
				CollectableComponent cc = (CollectableComponent)mover.getComponent(CollectableComponent.class);
				if (cc != null) {
					game.collectorSystem.entityCollected(results.collidedWith, mover);
					return;
				}
			}
		}
	}


}
