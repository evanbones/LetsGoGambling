package net.daythemagpie.letsgogambling.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.nikdo53.tinymultiblocklib.block.AbstractMultiBlock;
import net.nikdo53.tinymultiblocklib.block.IPreviewableMultiblock;
import net.nikdo53.tinymultiblocklib.components.IBlockPosOffsetEnum;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class SlotMachineBlock extends AbstractMultiBlock implements IPreviewableMultiblock {
    public SlotMachineBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion());
    }
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<MachinePart> PART = EnumProperty.create("part", MachinePart.class);
    ;public enum MachinePart implements StringRepresentable, IBlockPosOffsetEnum {
        TOP("top", pos -> pos.above()),
        BOTTOM("bottom", pos -> pos);

        MachinePart(String name, Function<BlockPos,BlockPos> offset){
            this.name = name;
            this.offset = offset;
        }

        private final String name;
        private final Function<BlockPos,BlockPos> offset;

        @Override
        public String getSerializedName() {
            return name;
        }

        @Override
        public Function<BlockPos, BlockPos> getOffsetFunction() {
            return offset;
        }

        @Override
        public String toString() {
            return name;
        }
    }


    @Override
    public @Nullable DirectionProperty getDirectionProperty() {
        return FACING;
    }

    @Override
    public RenderShape getMultiblockRenderShape(BlockState blockState, boolean b) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }

    @Override
    public List<BlockPos> makeFullBlockShape(Level level, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, @Nullable Direction direction) {
        return List.of(blockPos, blockPos.above());
    }

    @Override
    public BlockState getStateForEachBlock(BlockState state, BlockPos pos, BlockPos centerOffset, Level level, @Nullable Direction direction) {
        state = state.setValue(SlotMachineBlock.PART, IBlockPosOffsetEnum.fromOffset(MachinePart.class, centerOffset, direction, MachinePart.TOP));

        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(PART);
    }
}
