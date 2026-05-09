package mc.jeryn.dev.regen.bta.mixin;

import mc.jeryn.dev.regen.bta.access.ModelPlayerAccess;
import mc.jeryn.dev.regen.bta.access.RegenerationDataAccess;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.model.ModelPlayer;

import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static mc.jeryn.dev.regen.bta.Regeneration.MASTER_RANDOM;
import static net.minecraft.client.render.model.ModelPlayer.copyTranslation;


@Mixin({ModelPlayer.class, ModelBiped.class})
public class ModelBipedMixin implements ModelPlayerAccess {

	private Player player;


	@Override
	public void setLivingEntity(Player player) {
		this.player = player;
	}

	@Override
	public Player getLivingEntity() {
		return player;
	}

	private final ModelBiped thisAs = (ModelBiped) ((Object) this);


	@Inject(method = "setupAnimation(FFFFFF)V", at = @At("HEAD"), cancellable = true, remap = false)
	public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale, CallbackInfo ci) {

		RegenerationDataAccess playerRegenData = (RegenerationDataAccess) getLivingEntity();

		if (playerRegenData != null && playerRegenData.getRegenerationTicksElapsed() > 0) {
			double armShake = MASTER_RANDOM.nextDouble();
			long currentTicks = playerRegenData.getRegenerationTicksElapsed();
			float armRotY = (float) currentTicks * 1.5F;
			float armRotZ = (float) currentTicks * 1.5F;
			float headRot = (float) currentTicks * 1.5F;

			if (armRotY > 95) {
				armRotY = 95;
			}

			if (armRotZ > 95) {
				armRotZ = 95;
			}

			if (headRot > 45) {
				headRot = 45;
			}


			// ARMS
			thisAs.armLeft.yRot = 0;
			thisAs.armRight.yRot = 0;

			thisAs.armLeft.xRot = 0;
			thisAs.armRight.xRot = 0;

			thisAs.armLeft.zRot = (float) -Math.toRadians(armRotZ + armShake);
			thisAs.armRight.zRot = (float) Math.toRadians(armRotZ + armShake);
			thisAs.armLeft.yRot = (float) -Math.toRadians(armRotY);
			thisAs.armRight.yRot = (float) Math.toRadians(armRotY);

			// BODY
			thisAs.body.xRot = 0;
			thisAs.body.yRot = 0;
			thisAs.body.zRot = 0;

			// LEGS
			thisAs.legLeft.yRot = 0;
			thisAs.legRight.yRot = 0;

			thisAs.legLeft.xRot = 0;
			thisAs.legRight.xRot = 0;

			thisAs.legLeft.zRot = (float) -Math.toRadians(5);
			thisAs.legRight.zRot = (float) Math.toRadians(5);

			thisAs.head.xRot = (float) Math.toRadians(-headRot);
			thisAs.head.yRot = (float) Math.toRadians(0);
			thisAs.head.zRot = (float) Math.toRadians(0);

			copyAll();
			ci.cancel();
		} else {
			// Resets the legs, since Minecraft doesn't actually do this on its own
			thisAs.legLeft.zRot = 0;
			thisAs.legRight.zRot = 0;
		}
	}


	private void copyAll() {
		if(thisAs instanceof ModelPlayer) {
			ModelPlayer modelPlayer = (ModelPlayer) thisAs;
			copyTranslation(thisAs.legLeft, modelPlayer.bipedLeftLegOverlay);
			copyTranslation(thisAs.legRight, modelPlayer.bipedRightLegOverlay);
			copyTranslation(thisAs.armLeft, modelPlayer.bipedLeftArmOverlay);
			copyTranslation(thisAs.armRight, modelPlayer.bipedRightArmOverlay);
			copyTranslation(thisAs.body, modelPlayer.bipedBodyOverlay);
		}
		copyTranslation(thisAs.head, thisAs.hair);
	}


}
