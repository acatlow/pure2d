/**
 * 
 */
package com.funzio.pure2D.particles.nova.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author long
 */
public class NovaVO {
    public final int version;
    public final List<EmitterVO> emitters;
    public final List<AnimatorVO> animators;

    protected final Map<String, AnimatorVO> mAnimatorMap;

    @JsonCreator
    public NovaVO( //
            @JsonProperty("version")//
            final int version, //

            @JsonProperty("emitters")//
            final List<EmitterVO> emitters, //

            @JsonProperty("animators")//
            final List<AnimatorVO> animators //
    ) {
        this.version = version;
        this.emitters = emitters;
        this.animators = animators;

        // make the map
        mAnimatorMap = new HashMap<String, AnimatorVO>();
        for (AnimatorVO vo : animators) {
            mAnimatorMap.put(vo.name, vo);
        }
    }

    public AnimatorVO getAnimatorVO(final String name) {
        return mAnimatorMap.get(name);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Version: " + version + "\n" //
                + "Emitters: " + (emitters == null ? 0 : emitters.size()) + "\n" //
                + "Animators: " + (animators == null ? 0 : animators.size());
    }

}