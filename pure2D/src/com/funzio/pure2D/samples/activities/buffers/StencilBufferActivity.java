package com.funzio.pure2D.samples.activities.buffers;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.funzio.pure2D.BaseScene;
import com.funzio.pure2D.Playable;
import com.funzio.pure2D.R;
import com.funzio.pure2D.Scene;
import com.funzio.pure2D.animators.RotateAnimator;
import com.funzio.pure2D.containers.MaskGroup;
import com.funzio.pure2D.gl.GLColor;
import com.funzio.pure2D.gl.gl10.textures.Texture;
import com.funzio.pure2D.samples.activities.StageActivity;
import com.funzio.pure2D.shapes.Rectangular;
import com.funzio.pure2D.shapes.Sprite;

public class StencilBufferActivity extends StageActivity {
    private Texture mTexture;
    private MaskGroup mMask;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScene.setColor(new GLColor(0, 0.5f, 0, 1f));
        // need to get the GL reference first
        mScene.setListener(new Scene.Listener() {

            @Override
            public void onSurfaceCreated(final GL10 gl) {
                loadTexture();
                createMask();
                addObject(mDisplaySizeDiv2.x, mDisplaySizeDiv2.y);
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see com.funzio.pure2D.samples.activities.StageActivity#createScene()
     */
    @Override
    protected BaseScene createScene() {
        // NOTE: this is needed for some certain devices such as Galaxy Tab
        // and need to be set before Scene assignment
        mStage.setEGLConfigChooser(8, 8, 8, 8, 16, 8);

        return super.createScene();
    }

    private void createMask() {
        mMask = new MaskGroup();
        mMask.setRenderChildren(true);
        mMask.setPosition(mDisplaySizeDiv2.x, mDisplaySizeDiv2.y);
        mScene.addChild(mMask);

        // create a rect for the mask group
        Rectangular rect = new Rectangular();
        rect.setColor(GLColor.BLACK);
        rect.setSize(mDisplaySize.x, mDisplaySize.x);
        rect.setOriginAtCenter();
        mMask.addChild(rect);

        // rotating
        RotateAnimator animator = new RotateAnimator(null);
        animator.setLoop(Playable.LOOP_REPEAT);
        animator.setDuration(5000);
        mMask.addManipulator(animator);
        animator.start(360);
    }

    private void loadTexture() {
        // create texture
        mTexture = mScene.getTextureManager().createDrawableTexture(R.drawable.cc_175, null);
    }

    private void addObject(final float x, final float y) {
        // create object
        final Sprite obj = new Sprite();
        obj.setTexture(mTexture);
        obj.setMask(mMask);

        // center origin
        obj.setOriginAtCenter();

        // set positions
        obj.setPosition(x, y);

        // add to scene
        mScene.addChild(obj);
    }

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mStage.queueEvent(new Runnable() {

                @Override
                public void run() {
                    addObject(event.getX(), mDisplaySize.y - event.getY());
                }
            });
        }

        return true;
    }
}