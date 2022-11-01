package more.traders.mixin;

import more.traders.MoreTraders;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WanderingTraderSpawner.class)
public class ModifyTraderChanceMixin {
    @Shadow @Final private RandomSource random;

    @Redirect(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"
            ),
            method = "spawn"
    )
    private int moreTraders$modifyTraderChance(RandomSource random, int bound) {
        return random.nextIntBetweenInclusive(1, 100) <= MoreTraders.spawnSuccessPercentage ? 0 : 1;
    }
}
