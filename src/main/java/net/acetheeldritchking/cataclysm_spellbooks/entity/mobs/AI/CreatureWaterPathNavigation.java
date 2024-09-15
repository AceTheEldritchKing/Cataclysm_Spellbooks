package net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.AI;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class CreatureWaterPathNavigation extends FlyingPathNavigation {
    // Making this because WaterBoundPathNavigation crashes my poor fish babies
    public CreatureWaterPathNavigation(Mob pMob, Level pLevel) {
        super(pMob, pLevel);
    }

    @Override
    protected PathFinder createPathFinder(int pMaxVisitedNodes) {
        this.nodeEvaluator = new SwimNodeEvaluator(false);
        return new PathFinder(this.nodeEvaluator, pMaxVisitedNodes);
    }

    @Override
    protected Vec3 getTempMobPos() {
        return new Vec3(this.mob.getX(), this.mob.getY(0.5D), this.mob.getZ());
    }

    @Override
    protected boolean canUpdatePath() {
        return this.isInLiquid();
    }

    protected double getGroundY(Vec3 vec3)
    {
        return vec3.y;
    }

    @Override
    protected boolean canMoveDirectly(Vec3 pPosVec31, Vec3 pPosVec32) {
        return isClearForMovementBetween(this.mob, pPosVec31, pPosVec32, true);
    }

    @Override
    public boolean isStableDestination(BlockPos pPos) {
        return !this.level.getBlockState(pPos).isSolidRender(this.level, pPos);
    }

    @Override
    public void setCanFloat(boolean pCanSwim) {
        //
    }
}
