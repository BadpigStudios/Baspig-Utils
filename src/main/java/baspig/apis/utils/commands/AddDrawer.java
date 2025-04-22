package baspig.apis.utils.commands;

import baspig.apis.utils.BaspigsRegistries;
import baspig.apis.utils.client.render.draw_entity.DrawEntity;
import baspig.apis.utils.util.PlayerDrawData;
import baspig.apis.utils.util.pos.PosUtils;
import baspig.apis.utils.util.vertex.StringRenderLayerHandler;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.NumberRangeArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.NumberRange;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class AddDrawer {
    public AddDrawer(){
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment)
                        -> dispatcher.register(literal("drawer_add").requires(source -> source.hasPermissionLevel(3))
                                .then(argument("create", StringArgumentType.word())
                                        .suggests(((context, builder) -> {
                                            builder.suggest("create");
                                            return builder.buildFuture();
                                        }))
                                .then(argument("opacity", NumberRangeArgumentType.intRange())
                                        .suggests(((context, builder) -> {
                                            builder.suggest(255);
                                            return builder.buildFuture();
                                        })).then(argument("identifier", IdentifierArgumentType.identifier())
                                                .suggests(((context, builder) -> {
                                                    for (Identifier identifier : StringRenderLayerHandler.renderLayersMap.keySet()){
                                                        builder.suggest(identifier.toString());
                                                    }
                                                    return builder.buildFuture();
                                                })).then(argument("direction", StringArgumentType.word())
                                                        .suggests(((context, builder) -> {
                                                            builder
                                                                    .suggest("up")
                                                                    .suggest("down")
                                                                    .suggest("east")
                                                                    .suggest("west")
                                                                    .suggest("south")
                                                                    .suggest("north");
                                                            return builder.buildFuture();
                                                        }))
                        .executes(context -> {

                            String action = StringArgumentType.getString(context, "create");
                            NumberRange.IntRange opacity = NumberRangeArgumentType.IntRangeArgumentType.getRangeArgument(context, "opacity");
                            Identifier listing = IdentifierArgumentType.getIdentifier(context, "identifier");
                            String direction = StringArgumentType.getString(context, "direction");
                            
                            ServerPlayerEntity player = context.getSource().getPlayer();
                            assert player != null;
                            NbtCompound stored = ((PlayerDrawData) player).getDrawData();

                            ServerWorld serverWorld = context.getSource().getWorld();

                            if(stored.getIntArray("first_block_drawer").isPresent() &&
                                    stored.getIntArray("second_block_drawer").isPresent() &&
                                    stored.getIntArray("third_block_drawer").isPresent() &&
                                    stored.getIntArray("fourth_block_drawer").isPresent()){

                                BlockPos pos1 = PosUtils.getBlockPosFromArray(stored.getIntArray("first_block_drawer").get());
                                BlockPos pos2 = PosUtils.getBlockPosFromArray(stored.getIntArray("second_block_drawer").get());
                                BlockPos pos3 = PosUtils.getBlockPosFromArray(stored.getIntArray("third_block_drawer").get());
                                BlockPos pos4 = PosUtils.getBlockPosFromArray(stored.getIntArray("fourth_block_drawer").get());

                                byte alpha = opacity.min().isPresent() ? opacity.min().get().byteValue() : (byte) 255;

                                Direction drawerDirection = getDirection(player, direction);

                                generate(serverWorld, listing, pos1,
                                        PosUtils.getArrayFromBlockPos(pos1),
                                        PosUtils.getArrayFromBlockPos(pos2),
                                        PosUtils.getArrayFromBlockPos(pos3),
                                        PosUtils.getArrayFromBlockPos(pos4),
                                        alpha, drawerDirection
                                );
                            }
                            context.getSource().sendFeedback(() -> Text.literal("yay"), true);
                            return 1;
                        })))
        ))));


        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment)
                -> dispatcher.register(literal("drawer_remove").requires(source -> source.hasPermissionLevel(3))
                .then(argument("delete", StringArgumentType.word())
                        .suggests(((context, builder) -> {
                            builder.suggest("delete");
                            return builder.buildFuture();
                        })))
                .then(argument("drawer", EntityArgumentType.entity())
                        .executes(context -> {
                            Entity drawer = EntityArgumentType.getEntity(context, "drawer");
                            drawer.discard();
                            return 1;
                        }))
        ));
    }

    private Direction getDirection(ServerPlayerEntity player, String direction) {
        Direction drawerDirection = player.getFacing();

        if(direction.equals("up")) drawerDirection = Direction.UP;
        if(direction.equals("down")) drawerDirection = Direction.DOWN;
        if(direction.equals("east")) drawerDirection = Direction.EAST;
        if(direction.equals("west")) drawerDirection = Direction.WEST;
        if(direction.equals("south")) drawerDirection = Direction.SOUTH;
        if(direction.equals("north")) drawerDirection = Direction.NORTH;
        return drawerDirection;
    }

    public void generate(ServerWorld world, Identifier identifier, BlockPos pos, int[] array1, int[] array2, int[] array3, int[] array4, byte alpha, Direction direction) {

        DrawEntity drawEntity = new DrawEntity(BaspigsRegistries.DRAW_ENTITY, world);

        drawEntity.refreshPositionAndAngles(pos, 0, 0);

        world.spawnEntity(drawEntity);
        drawEntity.setStringData(
                PosUtils.getVector3fFromArray(array1),
                PosUtils.getVector3fFromArray(array2),
                PosUtils.getVector3fFromArray(array3),
                PosUtils.getVector3fFromArray(array4),
                alpha, direction, identifier);
    }
}
