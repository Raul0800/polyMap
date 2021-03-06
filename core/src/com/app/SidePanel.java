

package com.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Align;

/*
public class SidePanel extends VerticalGroup implements GestureDetector.GestureListener {
    GestureDetector gestureDetector;

    float position;



    public SidePanel(float width, float height) {
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
*/

/**
 * Class for slide panel
 */
public class SidePanel extends Table {

    /**
     * only visual window and using scissor to avoid GPU to draw out of left-edge screen
     */

    private float areaWidth;
    private float areaHeight;
    private final Rectangle areaBounds = new Rectangle();
    private final Rectangle scissorBounds = new Rectangle();

    /**
     * it's revealed with (widthStart = 60F;) when the user swipes a finger from the left edge of the screen with start touch.
     */

    private float widthStart = 60f;
    /**
     * when the user swipes a finger from the right edge of the screen, it goes into off-screen after (widthBack = 20F;).
     */
    private float widthBack = 20f;
    /**
     * speed of dragging
     */
    private float speed = 15f;

    /**
     * some attributes to make real dragging
     */
    private Vector2 clamp = new Vector2();
    private Vector2 posTap = new Vector2();
    private Vector2 end = new Vector2();
    private Vector2 first = new Vector2();
    private Vector2 last = new Vector2();

    private boolean show = false;
    private boolean isTouched = false;
    private boolean isStart = false;
    private boolean isBack = false;
    private boolean auto = false;
    private boolean enableDrag = true;

    /**
     * Set width of area
     *
     * @param areaWidth
     */
    public void setAreaWidth(float areaWidth) {
        this.areaWidth = areaWidth;
    }

    /**
     * Set height of area
     *
     * @param areaHeight
     */
    public void setAreaHeight(float areaHeight) {
        this.areaHeight = areaHeight;
    }

    /**
     * Constuctor with parametrs
     *
     * @param width
     * @param height
     */
    public SidePanel(float width, float height) {
        this.areaWidth = width;
        this.areaHeight = height;

        this.setSize(width, height);
    }

    private NavigationDrawerListener listener;

    public interface NavigationDrawerListener {
        void moving(Vector2 clamp);
    }

    /**
     * Set navigation drawer listener
     *
     * @param listener
     */
    public void setNavigationDrawerListener(NavigationDrawerListener listener) {
        this.listener = listener;

    }

    /**
     * Set width start drag
     *
     * @param widthStartDrag
     */
    public void setWidthStartDrag(float widthStartDrag) {
        this.widthStart = widthStartDrag;
    }

    /**
     * Set width back drag
     *
     * @param widthBackDrag
     */
    public void setWidthBackDrag(float widthBackDrag) {
        this.widthBack = widthBackDrag;
    }

    /**
     * Set speed of dragging
     *
     * @param speed
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Get speed of dragging
     *
     * @return speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * The panel will be showing manually with this speed
     *
     * @param show
     * @param speed
     */
    public void showManually(boolean show, float speed) {
        this.auto = true;
        this.show = show;
        this.speed = speed;
    }

    /**
     * The panel will be showing manually with default speed
     *
     * @param show
     */
    public void showManually(boolean show) {
        this.showManually(show, speed);
    }

    /**
     * Drawing of panel
     *
     * @param batch
     * @param alpha
     */
    @Override
    public void draw(Batch batch, float alpha) {
        getStage().calculateScissors(areaBounds.set(0, 0, areaWidth, areaHeight), scissorBounds);
        batch.flush();
        if (ScissorStack.pushScissors(scissorBounds)) {
            super.draw(batch, alpha);
            batch.flush();
            ScissorStack.popScissors();
        }

        if (isTouched() && inputX() < stgToScrX(this.getWidth(), 0).x) {
            auto = false;
            if (!isTouched) {
                isTouched = true;
                first.set(scrToStgX(inputX(), 0));
            }
            last.set(scrToStgX(inputX(), 0)).sub(first);

            if (isCompletelyClosed()) // open = false, close = true;
                startDrag();

            if ((isStart || isBack) && enableDrag) // open = false, close =
                // false;
                if (inputX() > stgToScrX(widthStart, 0).x)
                    dragging();

            if (isCompletelyOpened()) // open = true, close = false;
                backDrag();

        } else
            noDrag();

        updatePosition();

        moving();

        rotateMenuButton();

        fadeBackground();

    }

    private boolean isMax = false;
    private boolean isMin = false;

    /**
     * Moving of panel
     */
    private void moving() {
        if (listener == null)
            return;
        if (!isCompletelyClosed() && !isCompletelyOpened()) {
            listener.moving(clamp);
        } else {
            if (!isMax && isCompletelyOpened()) {
                isMax = true;
                isMin = false;
                listener.moving(clamp);
            }
            if (!isMin && isCompletelyClosed()) {
                isMin = true;
                isMax = false;
                listener.moving(clamp);
            }
        }

    }

    /**
     * Update position of panel
     */
    private void updatePosition() {
        clamp.set(MathUtils.clamp(end.x, 0, this.getWidth()), 0);
        this.setPosition(clamp.x, 0, Align.bottomRight);
    }

    /**
     * Dragging the panel
     */
    private void dragging() {
        if (isStart)
            end.set(scrToStgX(inputX(), 0));

        if (isBack && last.x < -widthBack)
            end.set(last.add(this.getWidth() + widthBack, 0));

    }

    /**
     * Dragging the panel back
     */
    private void backDrag() {
        isStart = false;
        isBack = true;
        show = false;
    }

    /**
     * Start of dragging
     */
    private void startDrag() {
        // check if the player touch on the drawer to OPEN it.
        if (inputX() < stgToScrX(widthStart, 0).x) {
            isStart = true;
            isBack = false;

            hintToOpen(); // hint to player if he want to open the drawer
        }
    }

    /**
     * No drag
     */
    private void noDrag() {
        isStart = false;
        isBack = false;
        isTouched = false;

        // set end of X to updated X from clamp
        end.set(clamp);

        if (auto) {
            if (show)
                end.add(speed, 0); // player want to OPEN drawer
            else
                end.sub(speed, 0); // player want to CLOSE drawer
        } else {
            if (toOpen())
                end.add(speed, 0); // player want to OPEN drawer
            else if (toClose())
                end.sub(speed, 0); // player want to CLOSE drawer
        }

    }

    /**
     * Hint to open
     */
    private void hintToOpen() {
        end.set(stgToScrX(widthStart, 0));
    }

    /**
     * Checking for completely closed panel
     *
     * @return if x = 0, return true
     */
    public boolean isCompletelyClosed() {
        return clamp.x == 0;
    }

    /**
     * Checking for completely opened panel
     *
     * @return if x = width, return true
     */
    public boolean isCompletelyOpened() {
        return clamp.x == this.getWidth();
    }

    /**
     * Checking for opening panel
     *
     * @return if x > half of width, return true
     */
    private boolean toOpen() {
        return clamp.x > this.getWidth() / 2;
    }

    /**
     * Checking for closing panel
     *
     * @return if x < half of width< return true
     */
    private boolean toClose() {
        return clamp.x < this.getWidth() / 2;
    }

    /**
     * Convert stage coordinates to screen coordinates
     *
     * @param x
     * @param y
     * @return x and y in vector
     */
    private Vector2 stgToScrX(float x, float y) {
        return getStage().stageToScreenCoordinates(posTap.set(x, y));
    }

    /**
     * Convert screen coordinates to stage coordinates
     *
     * @param x
     * @param y
     * @return x and y in vector
     */
    private Vector2 scrToStgX(float x, float y) {
        return getStage().screenToStageCoordinates(posTap.set(x, y));
    }

    /**
     * Input X coordinate
     *
     * @return x
     */
    private float inputX() {
        return Gdx.input.getX();
    }

    /**
     * Checking for touching screen
     *
     * @return boolean
     */
    private boolean isTouched() {
        return Gdx.input.isTouched();
    }

    /**
     * Optional
     **/
    private Actor menuButton = new Actor();
    private boolean isRotateMenuButton = false;
    private float menuButtonRotation = 0f;

    /**
     * Rotate menu button
     */
    private void rotateMenuButton() {
        if (isRotateMenuButton)
            menuButton.setRotation(clamp.x / this.getWidth() * menuButtonRotation);
    }

    /**
     * Set rotate menu button
     *
     * @param actor
     * @param rotation
     */
    public void setRotateMenuButton(Actor actor, float rotation) {
        this.menuButton = actor;
        this.isRotateMenuButton = true;
        this.menuButtonRotation = rotation;
    }

    /**
     * Set enable drag
     *
     * @param enableDrag
     */
    public void setEnableDrag(boolean enableDrag) {
        this.enableDrag = enableDrag;
    }

    /**
     * Optional
     **/
    private Actor background = new Actor();
    private boolean isFadeBackground = false;
    private float maxFade = 1f;

    /**
     * Fade background
     */
    private void fadeBackground() {
        if (isFadeBackground)
            background.setColor(background.getColor().r, background.getColor().g, background.getColor().b,
                    MathUtils.clamp(clamp.x / this.getWidth() / 2, 0, maxFade));
    }

    /**
     * Set fade background
     *
     * @param background
     * @param maxFade
     */
    public void setFadeBackground(Actor background, float maxFade) {
        this.background = background;
        this.isFadeBackground = true;
        this.maxFade = maxFade;
    }

}