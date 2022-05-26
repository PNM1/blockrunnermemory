package com.blockrunnermemory.event;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Flip extends Animation {


    private View from;
    private View to;

    public Flip(View from, View to) {
        this.from = from;
        this.to = to;
        setDuration(675);
        setFillAfter(false);
        setInterpolator(new AccelerateDecelerateInterpolator());
    }
    private boolean forward = true;
    public void RP() {
        forward = false;
        View switchView = to;
        to = from;
        from = switchView;
    }
    private float centerX;
    private float centerY;
    private Camera camera;
    @Override
    public void initialize(int w, int h, int width, int height) {
        super.initialize(w, h, width, height);
        centerX = w / 2;
        centerY = h / 2;
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float degrees = (float) (180.0 * interpolatedTime);

        if (interpolatedTime >= 0.5f) {
            degrees -= 180.f;
            from.setVisibility(View.GONE);
            to.setVisibility(View.VISIBLE);
        }
        if (forward)
            degrees = -degrees;
        final Matrix matrix = t.getMatrix();
        camera.save();
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
