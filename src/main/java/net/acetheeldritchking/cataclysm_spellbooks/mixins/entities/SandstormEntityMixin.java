package net.acetheeldritchking.cataclysm_spellbooks.mixins.entities;

import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import net.acetheeldritchking.cataclysm_spellbooks.util.IExtendedCataclysmProjectileInterface;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Sandstorm_Entity.class)
public class SandstormEntityMixin implements IExtendedCataclysmProjectileInterface {
    @Override
    public boolean isFromSpell() {
        return true;
    }

    @Override
    public void setFromSpell(boolean bool) {
        if (bool)
        {
            isFromSpell();
        }
    }

    @Shadow
    private Entity getCreatorEntity() {
        return null;
    }

    @Inject(
            method = "updateMotion",
            at = @At(value = "HEAD", target = "Lcom/github/L_Ender/cataclysm/entity/effect/Sandstorm_Entity;updateMotion()V"),
            remap = false
    )
    private void cataclysmSpellbooks$modifyUpdateMotion(CallbackInfo callback)
    {
        Entity owner = getCreatorEntity();
        Sandstorm_Entity sandstormEntity = (Sandstorm_Entity) (Object) this;
        if(owner !=null) {
            if (isFromSpell())
            {
                if (owner instanceof Player player) {
                    Vec3 center = owner.position().add(0.0, 0, 0.0);
                    float radius = 4;
                    float speed = sandstormEntity.tickCount * 0.04f;
                    float offset = sandstormEntity.getOffset();
                    Vec3 orbit = new Vec3(center.x + Math.cos((speed + offset)) * (double) radius, center.y, center.z + Math.sin((speed + offset)) * (double) radius);
                    sandstormEntity.moveTo(orbit);
                }
            }
        }
    }
}
