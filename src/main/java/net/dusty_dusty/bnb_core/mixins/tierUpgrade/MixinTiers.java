package net.dusty_dusty.bnb_core.mixins.tierUpgrade;

import net.minecraft.world.item.Tiers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SuppressWarnings("EqualsBetweenInconvertibleTypes")
@Mixin( Tiers.class )
public abstract class MixinTiers {

    @Shadow
    @Final
    private int uses;
    @Shadow
    @Final
    private float speed;
    @Shadow
    @Final
    private float damage;

    /**
     * @author Ada Aster
     * @reason Simple Change
     */
    @Overwrite
    public int getUses() {
        if ( this.equals( Tiers.NETHERITE ) ) {
            return this.uses + 500;
        }
        return this.uses;
    }

    /**
     * @author Ada Aster
     * @reason Simple Change
     */
    @Overwrite
    public float getSpeed() {
        if ( this.equals( Tiers.NETHERITE ) ) {
            return this.speed + 1;
        }
        return this.speed;
    }

    /**
     * @author Ada Aster
     * @reason Simple Change
     */
    @Overwrite
    public float getAttackDamageBonus() {
        if ( this.equals( Tiers.NETHERITE) ) {
            return this.damage + 1.5F;
        }
        return this.damage;
    }
}
