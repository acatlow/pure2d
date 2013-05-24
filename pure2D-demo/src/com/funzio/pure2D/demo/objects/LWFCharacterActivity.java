package com.funzio.pure2D.demo.objects;

import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.funzio.pure2D.Scene;
import com.funzio.pure2D.demo.activities.StageActivity;
import com.funzio.pure2D.lwf.LWF;
import com.funzio.pure2D.lwf.LWFObject;

public class LWFCharacterActivity extends StageActivity {

    private LWFObject mLWFObject;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLWFObject = new LWFObject();
        mScene.addChild(mLWFObject);

        mScene.setListener(new Scene.Listener() {

             @Override
             public void onSurfaceCreated(final GL10 gl) {
                attachLWF(mDisplaySizeDiv2.x, mDisplaySizeDiv2.y);
             }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScene.dispose();
    }

    private void attachLWF(final float x, final float y) {
        // attach lwf
        LWF lwf = mLWFObject.attachLWF(getAssets(), "lwf/YetiBlue/YetiBlue.lwf");

        // position in Flash coordinate
        lwf.moveTo("_root", x - 450, -y - 80);
    }

    @Override
    public boolean onTouch(final View v, final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mStage.queueEvent(new Runnable() {

                @Override
                public void run() {
                    attachLWF(event.getX(), mDisplaySize.y - event.getY());
                }
            });
        }

        return true;
    }
}
