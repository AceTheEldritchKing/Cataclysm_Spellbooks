package net.acetheeldritchking.cataclysm_spellbooks.util;

import static com.github.L_Ender.cataclysm.client.particle.RingParticle.EnumRingBehavior;

public class RingBehaviourHelper {
	public static int convert(EnumRingBehavior behavior) {
		switch (behavior) {
			case GROW:
				return 0;
			case SHRINK:
				return 1;
			case GROW_THEN_SHRINK:
				return 2;
			case CONSTANT:
				return 3;
			default:
				return 3;
		}
	}
}
