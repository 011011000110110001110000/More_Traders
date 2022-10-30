package more.traders.mixin;

import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(WanderingTraderSpawner.class)
public class IncreaseTraderChanceMixin {
    @ModifyArg(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"
            ),
            method = "spawn"
    )
    private int moreTraders$increaseTraderChance(int bound) {
        return 1;
    }
}
