/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Align;

public class SidePanel extends VerticalGroup implements GestureDetector.GestureListener{
    GestureDetector gestureDetector;

    float position;
    Pixmap pixmap;



    public SidePanel(float width, float height) {
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.0f, 0.0f, 0.0f, 0.5f);
        pixmap.fill();
        pixmap.fillRectangle((int)getX(), (int)getY(), (int)width, (int)height);
        this.setSize(width, height);
        position = width;
        setPosition(0, 0);

        gestureDetector = new GestureDetector(this);

    }

    boolean isClosed() {
        return (position == 0.0f);
    }
    boolean isOpen() {
        return (position == this.getWidth());
    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (x < this.getWidth()) {
            position = x;
            this.setPosition(x, 0, Align.bottomRight);
        }
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        if (x < this.getWidth()) {
            if (x < this.getWidth() / 2) {
                position = 0;
                this.setPosition(0, 0, Align.bottomRight);
            }
            else {
                position = this.getWidth();
                this.setPosition(this.getWidth(), 0, Align.bottomRight);
            }

                //clampPosition.set(this.getWidth(), 0);
            //this.setPosition(clampPosition.x, 0, Align.bottomRight);
        }
        return true;
    }


    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

}















/*
public class SidePanel extends Table implements GestureDetector.GestureListener {

    // only visual window and using scissor to avoid GPU to draw out of left-edge screen.
    private float areaWidth;
    private float areaHeight;
    private final Rectangle areaBounds = new Rectangle();
    private final Rectangle scissorBounds = new Rectangle();

    private Vector2 clampPosition; // position of clamp
    private Vector2 position;

    public void setAreaWidth(float areaWidth) {
        this.areaWidth = areaWidth;
    }
    public void setAreaHeight(float areaHeight) {
        this.areaHeight = areaHeight;
    }

    GestureDetector gestureDetector;

    public SidePanel(float width, float height) {
        position = new Vector2(0.0f, 0.0f);
        clampPosition = new Vector2(0.0f, 0.0f); //hidden
        gestureDetector = new GestureDetector(this);

        this.areaWidth = width;
        this.areaHeight = height;
        this.setSize(width, height);
    }



    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (x < this.getWidth()) {
            clampPosition.set(MathUtils.clamp(x, 0, this.getWidth()), 0);
            this.setPosition(clampPosition.x, 0, Align.bottomRight);
        }
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        if (x < this.getWidth()) {
            if (clampPosition.x < this.getWidth() / 2)
                clampPosition.set(0, 0);
            else
                clampPosition.set(this.getWidth(), 0);
            this.setPosition(clampPosition.x, 0, Align.bottomRight);
        }
        return true;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    @Override
    public void draw(Batch batch, float alpha) {
        getStage().calculateScissors(areaBounds.set(0, 0, areaWidth, areaHeight), scissorBounds);
        batch.flush();
        if (ScissorStack.pushScissors(scissorBounds)) {
            super.draw(batch, alpha);
            batch.flush();
            ScissorStack.popScissors();
        }
    }

    /*
        private void updatePosition() {
            clampPosition.set(MathUtils.clamp(inputX(), 0, this.getWidth()), 0);
            this.setPosition(clampPosition.x, 0, Align.bottomRight);
        }

    private Vector2 stgToScrX(float x, float y) {
        return getStage().stageToScreenCoordinates(new Vector2(x, y));
    }

    private Vector2 scrToStgX(float x, float y) {
        return getStage().screenToStageCoordinates(new Vector2(x, y));
    }

    private float inputX() {
        return Gdx.input.getX();
    }

    private boolean isTouched() {
        return Gdx.input.isTouched();
    }


    private Actor menuButton = new Actor();
    private boolean isRotateMenuButton = false;
    private float menuButtonRotation = 0f;

    private void rotateMenuButton() {
        if (isRotateMenuButton)
            menuButton.setRotation(clamp.x / this.getWidth() * menuButtonRotation);
    }

    public void setRotateMenuButton(Actor actor, float rotation) {
        this.menuButton = actor;
        this.isRotateMenuButton = true;
        this.menuButtonRotation = rotation;
    }
*/
    /*public void setEnableDrag(boolean enableDrag) {
        this.enableDrag = enableDrag;
    }
}
*/