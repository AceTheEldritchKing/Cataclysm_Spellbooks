package net.acetheeldritchking.cataclysm_spellbooks.spells.holy;

import com.github.L_Ender.cataclysm.client.particle.Options.StormParticleOptions;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonedEntitiesCastData;
import io.redspace.ironsspellbooks.entity.spells.EarthquakeAoe;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.PhantomAncientRemnant;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.spells.AbstractSummonSpell;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class ThothsWitnessSpell extends AbstractSummonSpell {
	private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID,
			"thoths_witness");

	@Override
	public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
		return List.of(Component.translatable("ui.cataclysm_spellbooks.remnant_count", spellLevel));
	}

	private final DefaultConfig defaultConfig = new DefaultConfig()
			.setMinRarity(SpellRarity.LEGENDARY)
			.setSchoolResource(SchoolRegistry.HOLY_RESOURCE)
			.setMaxLevel(1)
			// 600 secs
			.setCooldownSeconds(600)
			.build();

	public ThothsWitnessSpell() {
		this.manaCostPerLevel = 20;
		this.baseSpellPower = 10;
		this.spellPowerPerLevel = 5;
		this.castTime = 100;
		this.baseManaCost = 650;
	}

	@Override
	public ResourceLocation getSpellResource() {
		return spellId;
	}

	@Override
	public DefaultConfig getDefaultConfig() {
		return defaultConfig;
	}

	@Override
	public CastType getCastType() {
		return CastType.LONG;
	}

	@Override
	public CastResult canBeCastedBy(int spellLevel, CastSource castSource, MagicData playerMagicData,
			Player player) {
		if (castSource == CastSource.SCROLL) {
			return new CastResult(CastResult.Type.FAILURE,
					Component.translatable("ui.cataclysm_spellbooks.thoths_witness_scroll_failure",
							new Object[] { this.getDisplayName(player) })
							.withStyle(ChatFormatting.RED));
		}
		if (!player.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistries.PHARAOH_MAGE_HELMET.get())
				&& !player.getItemBySlot(EquipmentSlot.CHEST)
						.is(ItemRegistries.PHARAOH_MAGE_CHESTPLATE.get())
				&& !player.getItemBySlot(EquipmentSlot.LEGS)
						.is(ItemRegistries.PHARAOH_MAGE_LEGGINGS.get())
				&& !player.getItemBySlot(EquipmentSlot.FEET)
						.is(ItemRegistries.PHARAOH_MAGE_BOOTS.get())) {
			return new CastResult(CastResult.Type.FAILURE,
					Component.translatable("ui.cataclysm_spellbooks.thoths_witness_armor_failure",
							new Object[] { this.getDisplayName(player) })
							.withStyle(ChatFormatting.RED));
		}
		return super.canBeCastedBy(spellLevel, castSource, playerMagicData, player);
	}

	@Override
	public boolean canBeCraftedBy(Player player) {
		return false;
	}

	@Override
	public boolean allowLooting() {
		return false;
	}

	@Override
	public boolean allowCrafting() {
		return false;
	}

	@Override
	public AnimationHolder getCastStartAnimation() {
		return SpellAnimations.CHARGE_SPIT_ANIMATION;
	}

	@Override
	public AnimationHolder getCastFinishAnimation() {
		return SpellAnimations.SPIT_FINISH_ANIMATION;
	}

	@Override
	public Optional<SoundEvent> getCastStartSound() {
		return Optional.of(SoundEvents.BELL_BLOCK);
	}

	@Override
	public Optional<SoundEvent> getCastFinishSound() {
		return Optional.of(ModSounds.REMNANT_ROAR.get());
	}

	@Override
	public void onServerPreCast(Level level, int spellLevel, LivingEntity entity,
			@Nullable MagicData playerMagicData) {
		// Let the ground tremble!!
		EarthquakeAoe aoe = new EarthquakeAoe(level);
		aoe.moveTo(entity.position());
		aoe.setOwner(entity);
		aoe.setCircular();
		aoe.setRadius(10);
		aoe.setDuration(getCastTime(spellLevel));
		aoe.setDamage(0);
		aoe.setSlownessAmplifier(0);

		level.addFreshEntity(aoe);

		super.onServerPreCast(level, spellLevel, entity, playerMagicData);
	}

	@Override
	public void onServerCastTick(Level level, int spellLevel, LivingEntity entity,
			@Nullable MagicData playerMagicData) {
		float random = 0.04F;

		float r = 0.89F + entity.getRandom().nextFloat() * random;
		float g = 0.85F + entity.getRandom().nextFloat() * random;
		float b = 0.69F + entity.getRandom().nextFloat() * random * 1.5F;

		MagicManager.spawnParticles(level,
				new StormParticleOptions(r, g, b, 2.75F + entity.getRandom().nextFloat() * 0.6F,
						3.75F + entity.getRandom().nextFloat() * 0.6F, entity.getId()),
				entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 1, true);
		MagicManager.spawnParticles(level,
				new StormParticleOptions(r, g, b, 2.5F + entity.getRandom().nextFloat() * 0.45F,
						3.0F + entity.getRandom().nextFloat() * 0.45F, entity.getId()),
				entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 1, true);
		MagicManager.spawnParticles(level,
				new StormParticleOptions(r, g, b, 2.25F + entity.getRandom().nextFloat() * 0.45F,
						2.25F + entity.getRandom().nextFloat() * 0.45F, entity.getId()),
				entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 1, true);
		MagicManager.spawnParticles(level,
				new StormParticleOptions(r, g, b, 1.25F + entity.getRandom().nextFloat() * 0.45F,
						1.25F + entity.getRandom().nextFloat() * 0.45F, entity.getId()),
				entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 1, true);

		if (entity.tickCount % 10 == 0) {
			// ring 1
			int count = 16;
			float particleRadius = 1.25f;
			for (int i = 0; i < count; i++) {
				double x, z;
				double theta = Math.toRadians((double) 360 / count) * i;
				x = Math.cos(theta) * particleRadius;
				z = Math.sin(theta) * particleRadius;
				MagicManager.spawnParticles(entity.level(), ModParticle.DESERT_GLYPH.get(),
						entity.position().x + x, entity.position().y, entity.position().z + z,
						1, 0, 0, 0, 0.1, false);
			}

			// ring 2
			int count2 = 16;
			float particleRadius2 = 2.25f;
			for (int i = 0; i < count; i++) {
				double x, z;
				double theta = Math.toRadians((double) 360 / count2) * i;
				x = Math.cos(theta) * particleRadius2;
				z = Math.sin(theta) * particleRadius2;
				MagicManager.spawnParticles(entity.level(), ModParticle.DESERT_GLYPH.get(),
						entity.position().x + x, entity.position().y, entity.position().z + z,
						1, 0, 0, 0, 0.1, false);
			}

			// ring 3
			int count3 = 16;
			float particleRadius3 = 3.25f;
			for (int i = 0; i < count; i++) {
				double x, z;
				double theta = Math.toRadians((double) 360 / count3) * i;
				x = Math.cos(theta) * particleRadius3;
				z = Math.sin(theta) * particleRadius3;
				MagicManager.spawnParticles(entity.level(), ModParticle.DESERT_GLYPH.get(),
						entity.position().x + x, entity.position().y, entity.position().z + z,
						1, 0, 0, 0, 0.1, false);
			}
		}

		super.onServerCastTick(level, spellLevel, entity, playerMagicData);
	}

	@Override
	public void onClientPreCast(Level level, int spellLevel, LivingEntity entity, InteractionHand hand,
			@Nullable MagicData playerMagicData) {
		super.onClientPreCast(level, spellLevel, entity, hand, playerMagicData);
	}

	@Override
	protected int onSummoningCast(Level level, int spellLevel, LivingEntity caster, CastSource castSource,
			MagicData playerMagicData, SummonedEntitiesCastData castData) {
		// Only is around for 45 seconds
		int summonTimer = 1200;

		Vec3 vec = caster.getEyePosition();

		double randomNearbyX = vec.x + caster.getRandom().nextGaussian() * 1.5;
		double randomNearbyZ = vec.z + caster.getRandom().nextGaussian() * 1.5;

		var ancientRemnant = spawnHelper(randomNearbyX, caster.getY(), randomNearbyZ, caster, level,
				summonTimer, castData, () -> new PhantomAncientRemnant(level, caster));
		ancientRemnant.setNecklace(true);

		// Just for visuals
		EarthquakeAoe aoe = new EarthquakeAoe(level);
		aoe.moveTo(ancientRemnant.position());
		aoe.setOwner(ancientRemnant);
		aoe.setCircular();
		aoe.setRadius(6);
		aoe.setDuration(20);
		aoe.setDamage(0);
		aoe.setSlownessAmplifier(0);
		level.addFreshEntity(aoe);

		MagicManager.spawnParticles(level,
				new BlastwaveParticleOptions(SchoolRegistry.HOLY.get().getTargetingColor(), 6),
				caster.getX(), caster.getY() + 0.8F, caster.getZ(), 1, 0, 0, 0, 0, true);
		ScreenShake_Entity.ScreenShake(level, caster.position(), 6.0F, 0.15F, 20, 20);

		return summonTimer;
	}
}
