package net.dusty_dusty.bnb_core.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin( CropBlock.class )
public abstract class MixinCropBlock
        extends BushBlock
        implements BonemealableBlock {

    // I don't think this will get used? But I made it match just in case...
    public MixinCropBlock( Properties pProperties) {
        super( pProperties );
        this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0));
    }

    /**
     * @author Dusty_Dusty
     * @reason Must return true even for fully grown. A crop with non-standard behaviour should overwrite this on its own.
     */
    @Overwrite
    public boolean isRandomlyTicking( BlockState pState ) {
        return true;
    }

    /**
     * @author Dusty_Dusty
     * @reason Prototyping; should be replaced later. Check for age range after firing forge event rather than before.
     */
    @Overwrite
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            int i = this.getAge(pState);
            float f = getGrowthSpeed(this, pLevel, pPos);
            if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / f) + 1) == 0) && i < this.getMaxAge()) {
                pLevel.setBlock(pPos, this.getStateForAge(i + 1), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
            }
        }
    }

    @Shadow
    public int getAge( BlockState blockState ) { return 0; }
    @Shadow
    public int getMaxAge() { return 1; }
    @Shadow
    protected static float getGrowthSpeed(Block pBlock, BlockGetter pLevel, BlockPos pPos) { return 0.0F; }
    @Shadow
    public BlockState getStateForAge(int pAge) { return null; }

    @Shadow
    protected IntegerProperty getAgeProperty() {return null;}
    @Shadow
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {return false;}
    @Shadow
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {return false;}
    @Shadow
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {}

}
