package com.ombremoon.enderring.client.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class HewgAnimations {
	public static final AnimationDefinition HEWG_HAMMER_TEMP = AnimationDefinition.Builder.withLength(1.2f).looping()
			.addAnimation("left_arm",
					new AnimationChannel(AnimationChannel.Targets.ROTATION,
							new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.15f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.3f, KeyframeAnimations.degreeVec(0f, 0f, -67.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.45f, KeyframeAnimations.degreeVec(0f, 0f, -112.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.6f, KeyframeAnimations.degreeVec(0f, 0f, -135f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, -112.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(0.9f, KeyframeAnimations.degreeVec(0f, 0f, -67.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.05f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f),
									AnimationChannel.Interpolations.LINEAR),
							new Keyframe(1.2f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
									AnimationChannel.Interpolations.LINEAR))).build();
}