package edu.stanford.cs108.bunnyworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameCanvas extends View {

    static public Page cur_page;
    static public Shape cur_shape;
    static public Game game;
    static public GameCanvas game_canvas;

    private int canvas_width, canvas_height;
    private float selected_x, selected_y;
    private int shape_in_canvas_idx;

    static Paint possession_black_delimiter;


    public GameCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        possession_black_delimiter = new Paint();
        possession_black_delimiter.setColor(Color.BLACK);
        possession_black_delimiter.setAlpha(200);
        game = GameManager.cur_game;

        init();
    }

    private void init() {
        game_canvas = findViewById(R.id.game_canvas);
        shape_in_canvas_idx = -1;
        cur_shape = null;

        if (EditActivity.selected_page == null) {
            EditActivity.selected_page = new Page("Page1");
            cur_page = EditActivity.selected_page;
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int old_w, int old_h) {
        super.onSizeChanged(w, h, old_w, old_h);
        canvas_width = w;
        canvas_height = h;
        EditActivity.game_canvas_width = canvas_width;
        EditActivity.game_canvas_height = canvas_height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the possession area boundary line
        canvas.drawLine(0.0f,
                (int) Math.round(0.93 * EditActivity.game_canvas_height),
                EditActivity.game_canvas_width,
                (int) Math.round(0.93 * EditActivity.game_canvas_height),
                possession_black_delimiter);

        // draw current selected page
        if (EditActivity.selected_page != null) {
            EditActivity.selected_page.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ActionDownHelper(event);
                break;
            case MotionEvent.ACTION_MOVE:
                ActionMoveHelper(event);
                break;
            case MotionEvent.ACTION_UP:
                ActionUpHelper(event);
                break;
        }
        return true;
    }

    private void ActionDownHelper(MotionEvent event) {
        // init the selected state for all shapes
        for (Shape shape : cur_page.shapes) {
            shape.is_selected = false;
        }

        selected_x = event.getX();
        selected_y = event.getY();

        shape_in_canvas_idx = findSelectedShapeIndex();
        if (shape_in_canvas_idx != -1) {
            Shape newly_selected_shape = cur_page.shapes.get(shape_in_canvas_idx);
            // TODO: distinguish if in the possession area
            newly_selected_shape.is_selected = true;
            // bring to the top
            cur_page.shapes.remove(shape_in_canvas_idx);
            cur_page.shapes.add(newly_selected_shape);
            cur_shape = newly_selected_shape;
        }

        invalidate();
    }

    // TODO: implement drag operation
    private void ActionMoveHelper(MotionEvent event) {
    }

    // TODO: running `scripts` when releasing
    private void ActionUpHelper(MotionEvent event) {

    }

    private int findSelectedShapeIndex() {
        int idx = -1;
        for (int i = cur_page.shapes.size() - 1; i >= 0; i--) {
            Shape sp = cur_page.shapes.get(i);
            if (sp.bounding_box.contains(selected_x, selected_y)) {
                idx = i;
                break;
            }
        }
        return idx;
    }
}
