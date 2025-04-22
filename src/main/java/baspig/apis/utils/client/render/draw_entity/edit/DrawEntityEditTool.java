package baspig.apis.utils.client.render.draw_entity.edit;

import baspig.apis.utils.util.PlayerDrawData;
import baspig.apis.utils.util.pos.PosUtils;
import baspig.apis.utils.util.state.PentaState;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class DrawEntityEditTool extends Item {
    private final NbtCompound nbtCompound = new NbtCompound();

    private PentaState item_state;

    private BlockPos secondPos;
    private BlockPos thirdPos;
    private BlockPos fourthPos;

    public DrawEntityEditTool(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(item_state == null) item_state = PentaState.First_State;

        if(context.getWorld().isClient()) return ActionResult.SUCCESS;

        double reach = 10.0D;
        Vec3d eyePos = context.getPlayer().getCameraPosVec(1.0F);
        Vec3d lookVec = context.getPlayer().getRotationVec(1.0F);
        Vec3d targetVec = eyePos.add(lookVec.multiply(reach));

        BlockHitResult hit = context.getWorld().raycast(new RaycastContext(
                eyePos,
                targetVec,
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                context.getPlayer()
        ));

        ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
        PlayerDrawData dataHolder = (PlayerDrawData) player;

        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = hit.getBlockPos();
            if(context.getPlayer() != null){
                if(item_state == PentaState.First_State){

                    context.getPlayer().sendMessage(Text.translatable("baspig_utils.draw.entity.first_pos").withColor(0xFF7ED4), true);
                    context.getPlayer().sendMessage(Text.of(pos.toString()), false);
                    item_state = PentaState.Second_State;

                    nbtCompound.putIntArray("first_block_drawer", PosUtils.getArrayFromBlockPos(pos));

                    dataHolder.setDrawData(nbtCompound);
                    return ActionResult.SUCCESS;

                }else if(item_state == PentaState.Second_State){
                    context.getPlayer().sendMessage(Text.translatable("baspig_utils.draw.entity.second_pos").withColor(0xFF7ED4), true);
                    context.getPlayer().sendMessage(Text.of(pos.toString()), false);
                    item_state = PentaState.Third_State;

                    nbtCompound.putIntArray("second_block_drawer", PosUtils.getArrayFromBlockPos(pos));
                    dataHolder.setDrawData(nbtCompound);
                    return ActionResult.SUCCESS;

                } else if(item_state == PentaState.Third_State){
                    thirdPos = pos;
                    context.getPlayer().sendMessage(Text.translatable("baspig_utils.draw.entity.third_pos").withColor(0xFF7ED4), true);
                    context.getPlayer().sendMessage(Text.of(pos.toString()), false);
                    item_state = PentaState.Fourth_State;

                    nbtCompound.putIntArray("third_block_drawer", PosUtils.getArrayFromBlockPos(pos));
                    dataHolder.setDrawData(nbtCompound);
                    return ActionResult.SUCCESS;

                } else if(item_state == PentaState.Fourth_State){
                    fourthPos = pos;
                    context.getPlayer().sendMessage(Text.translatable("baspig_utils.draw.entity.fourth_pos").withColor(0xFF7ED4), true);
                    context.getPlayer().sendMessage(Text.of(pos.toString()), false);
                    item_state = PentaState.Fifth_State;

                    nbtCompound.putIntArray("fourth_block_drawer", PosUtils.getArrayFromBlockPos(pos));
                    dataHolder.setDrawData(nbtCompound);
                    return ActionResult.SUCCESS;

                } else if(item_state == PentaState.Fifth_State){
                    context.getPlayer().sendMessage(Text.translatable("baspig_utils.draw.entity.reset").withColor(0xFF3EA5), true);

                    secondPos = null;
                    thirdPos = null;
                    fourthPos = null;

                    item_state = PentaState.First_State;
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canMine(ItemStack stack, BlockState state, World world, BlockPos pos, LivingEntity user) {
        return false;
    }

}
