package net.daythemagpie.letsgogambling.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class SlotMachineBlock extends AbstractMultiBlock implements IPreviewableMultiblock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<MachinePart> PART = EnumProperty.create("part", MachinePart.class);

    public SlotMachineBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(PART, MachinePart.BOTTOM));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public @Nullable DirectionProperty getDirectionProperty() {
        return FACING;
    }

    @Override
    public RenderShape getMultiblockRenderShape(@NonNull BlockState blockState, boolean b) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos pPos, @NonNull BlockState pState) {
        return null;
    }

    @Override
    public List<BlockPos> makeFullBlockShape(@NonNull Level level, @NonNull BlockPos blockPos, @NonNull BlockState blockState, @Nullable BlockEntity blockEntity, @Nullable Direction direction) {
        return List.of(blockPos, blockPos.above());
    }

    @Override
    public BlockState getStateForEachBlock(@NonNull BlockState state, @NonNull BlockPos pos, @NonNull BlockPos centerOffset, @NonNull Level level, @Nullable Direction direction) {
        return state.setValue(SlotMachineBlock.PART, IBlockPosOffsetEnum.fromOffset(MachinePart.class, centerOffset, direction, MachinePart.TOP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NonNull Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(PART);
    }

    public enum MachinePart implements StringRepresentable, IBlockPosOffsetEnum {
        TOP("top", BlockPos::above),
        BOTTOM("bottom", pos -> pos);

        private final String name;
        private final Function<BlockPos, BlockPos> offset;

        MachinePart(String name, Function<BlockPos, BlockPos> offset) {
            this.name = name;
            this.offset = offset;
        }

        @Override
        public @NonNull String getSerializedName() {
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
}