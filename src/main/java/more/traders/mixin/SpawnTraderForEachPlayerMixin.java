package more.traders.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(WanderingTraderSpawner.class)
public abstract class SpawnTraderForEachPlayerMixin {

    @Shadow protected abstract boolean spawn(ServerLevel serverLevel);

    List<? extends Player> players;
    private int currentPlayerIndex = 0;

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.BEFORE,
                    target = "Lnet/minecraft/world/entity/npc/WanderingTraderSpawner;spawn(Lnet/minecraft/server/level/ServerLevel;)Z"
            )
    )
    private void moreTraders$getNonSpectatingPlayers(ServerLevel level, boolean spawnEnemies, boolean spawnFriendlies, CallbackInfoReturnable<Integer> cir) {
        currentPlayerIndex = 0;
        players = level.getPlayers(serverPlayer -> (!serverPlayer.isSpectator() && serverPlayer.isAlive()));
    }

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/npc/WanderingTraderSpawner;spawn(Lnet/minecraft/server/level/ServerLevel;)Z"
            )
    )

    private boolean moreTraders$trySpawnTraderForAllPlayers(WanderingTraderSpawner wanderingTraderSpawner, ServerLevel serverLevel) {
        int nonSpectatingPlayerCount = players.size();
        boolean shouldReturn = false;
        for(int playerIndex = 0; playerIndex < nonSpectatingPlayerCount && !shouldReturn; playerIndex++) {
            currentPlayerIndex = playerIndex;
            shouldReturn = spawn(serverLevel);
        }
        return shouldReturn;
    }

    @Redirect(
            method = "spawn",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/level/ServerLevel;getRandomPlayer()Lnet/minecraft/server/level/ServerPlayer;"
            )
    )
    private ServerPlayer moreTraders$getCurrentPlayer(ServerLevel level) {
        if(!players.isEmpty() && currentPlayerIndex < players.size())
            return (ServerPlayer) players.get(currentPlayerIndex);
        return null;
    }

}
