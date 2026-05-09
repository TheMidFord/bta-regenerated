package mc.jeryn.dev.regen.bta.item;

import mc.jeryn.dev.regen.bta.access.RegenerationDataAccess;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;

public class FobWatchItem extends Item {


	public FobWatchItem(String translationkey,String namespaceid, int id) {
		super(translationkey,namespaceid, id);
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player entityplayer) {
		RegenerationDataAccess playerRegenData = (RegenerationDataAccess) entityplayer;
		playerRegenData.setRegensLeft(12);
		itemstack.stackSize = 0;
		entityplayer.sendMessage("You now have" + playerRegenData.getRegensLeft() + " regenerations!");
		return super.onUseItem(itemstack, world, entityplayer);
	}

}
