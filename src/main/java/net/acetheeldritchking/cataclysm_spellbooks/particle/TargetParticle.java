package net.acetheeldritchking.cataclysm_spellbooks.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import io.redspace.ironsspellbooks.particle.SnowDustParticle;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Consumer;

public class TargetParticle extends TextureSheetParticle {
    private static final Vector3f ROTATION_VECTOR = Util.make(new Vector3f(0.5F, 0.5F, 0.5F), Vector3f::normalize);
    private static final Vector3f TRANSFORM_VECTOR = new Vector3f(-1.0F, -1.0F, 0.0F);
    private static final float DEGREES_90 = Mth.PI / 2f;

    public TargetParticle(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);

        this.xd = pX;
        this.yd = pY;
        this.zd = pZ;
        this.quadSize = 1.5f;
        this.lifetime = 20 * 5;
    }

    @Override
    public void tick() {
        if (this.age++ >= lifetime)
        {
            this.remove();
        }
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        this.renderRotatedParticle(pBuffer, pRenderInfo, pPartialTicks, (p_234005_) -> {
            p_234005_.mul(Vector3f.YP.rotation(0));
            p_234005_.mul(Vector3f.XP.rotation(-DEGREES_90));
            //System.out.println("Render top?");
        });
        //back face
        this.renderRotatedParticle(pBuffer, pRenderInfo, pPartialTicks, (p_234000_) -> {
            p_234000_.mul(Vector3f.YP.rotation(-(float) Math.PI));
            p_234000_.mul(Vector3f.XP.rotation(DEGREES_90));
            //System.out.println("Render back?");
        });
    }

    private void renderRotatedParticle(VertexConsumer pConsumer, Camera camera, float partialTick, Consumer<Quaternion> pQuaternion) {
        Vec3 vec3 = camera.getPosition();
        float f = (float)(Mth.lerp(partialTick, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp(partialTick, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp(partialTick, this.zo, this.z) - vec3.z());
        Quaternion quaternion = new Quaternion(ROTATION_VECTOR, 0.0F, true);
        pQuaternion.accept(quaternion);
        TRANSFORM_VECTOR.transform(quaternion);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f3 = this.getQuadSize(partialTick);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.transform(quaternion);
            vector3f.mul(f3);
            vector3f.add(f, f1, f2);
        }

        int j = this.getLightColor(partialTick);
        this.makeCornerVertex(pConsumer, avector3f[0], this.getU1(), this.getV1(), j);
        this.makeCornerVertex(pConsumer, avector3f[1], this.getU1(), this.getV0(), j);
        this.makeCornerVertex(pConsumer, avector3f[2], this.getU0(), this.getV0(), j);
        this.makeCornerVertex(pConsumer, avector3f[3], this.getU0(), this.getV1(), j);
    }

    private void makeCornerVertex(VertexConsumer pConsumer, Vector3f pVec3f, float p_233996_, float p_233997_, int p_233998_) {
        pConsumer.vertex(pVec3f.x(), pVec3f.y() + .08f, pVec3f.z()).uv(p_233996_, p_233997_).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(p_233998_).endVertex();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        return LightTexture.FULL_BRIGHT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            TargetParticle targetParticle = new TargetParticle(pLevel, pX, pY, pZ);
            targetParticle.pickSprite(this.sprites);
            targetParticle.setAlpha(1f);
            return targetParticle;
        }
    }
}
