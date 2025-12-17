package net.dusty_dusty.bnb_core.mixins;

import loaderCommon.forge.com.seibel.distanthorizons.common.wrappers.block.ClientBlockStateColorCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin( ClientBlockStateColorCache.class )
public abstract class MixinClientBlockStateColorCache {

    @Final
    private static RandomSource RANDOM;

    @Shadow
    private BlockState blockState;

    /// Doesn't Work :/
//    @Inject( method = "getQuadsForDirection", at = @At( "RETURN" ), remap = false )
//    private void onGetQuadsForDirection( @Nullable Direction direction, CallbackInfoReturnable<List<BakedQuad>> cir ){
//        if ( this.blockState.getBlock() instanceof SlabBlock ) {
//            BlockState fullBlockState = this.blockState.setValue( SlabBlock.TYPE, SlabType.DOUBLE );
//            cir.setReturnValue(
//                    Minecraft.getInstance().getModelManager().getBlockModelShaper().
//                    getBlockModel( fullBlockState ).getQuads( fullBlockState, direction, RANDOM)
//            );
//        }
//    }

    /**
     * @author Ada Aster
     * @reason Injector not working ;-;
     */
    @Overwrite( remap = false )
    private List<BakedQuad> getQuadsForDirection(@Nullable Direction direction) {
		List<BakedQuad> quads;

        if ( this.blockState.getBlock() instanceof SlabBlock ) {
            BlockState fullBlockState = this.blockState.setValue( SlabBlock.TYPE, SlabType.DOUBLE );
            quads = Minecraft.getInstance().getModelManager().getBlockModelShaper().
                    getBlockModel( fullBlockState ).getQuads( fullBlockState, direction, RANDOM);
        } else {
            quads = Minecraft.getInstance().getModelManager().getBlockModelShaper().
                    getBlockModel(this.blockState).getQuads(this.blockState, direction, RANDOM);
        }

		return quads;
	}
}
