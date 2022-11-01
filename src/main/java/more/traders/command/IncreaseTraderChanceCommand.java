package more.traders.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import more.traders.MoreTraders;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;


public class IncreaseTraderChanceCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("moreTraders").requires(context -> context.hasPermission(2))
                        .then(Commands.literal("setSpawnSuccessPercentage")
                                .then(Commands.argument("percentage", IntegerArgumentType.integer())
                                        .executes(context -> setTraderSpawnOneInXChance(context.getSource(), context.getArgument("percentage", Integer.class))))

                )
        );
    }

    private static int setTraderSpawnOneInXChance(CommandSourceStack source, int successPercentage) {
        if (successPercentage > 0 && successPercentage <= 100) {
            MoreTraders.spawnSuccessPercentage = successPercentage;
            source.sendSuccess(Component.translatable("Set the trader spawn attempt success percentage to " + successPercentage + "%"), false);
        } else source.sendFailure(Component.translatable("Insert a valid percentage \n(must be greater than 0 and at most 100)"));
        return Command.SINGLE_SUCCESS;
    }

}
