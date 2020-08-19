package edu.stanford.cs108.bunnyworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;

public class Shape {

    private Context context;

    public static final int RECT = 0;
    public static final int IMAGE = 1;
    public static final int TEXT = 2;

    // May be can set as private and access with bunch of getter/setter
    String shape_name;
    RectF bounding_box;
    String referred_image_name = "";
    String referred_text = "";
    boolean is_hidden = false;
    boolean is_movable = false;
    boolean is_selected = false;
    boolean is_ondrop = false;

    // Preload Painting
    static Paint rectangle_gray_fill;
    static Paint ondrop_green_stroke, selected_blue_stroke, text_paint;

    static float DEFAULT_WIDTH = 200;
    static float DEFAULT_HEIGHT = 200;


    Shape(Context context, String name, float x, float y) {
        initPainting();
        this.context = context;
        this.shape_name = name;
        this.bounding_box = new RectF(x, y, x + DEFAULT_WIDTH, y + DEFAULT_HEIGHT);

    }

    private void initPainting() {
        // init pre-defined drawing
        rectangle_gray_fill = new Paint();
        rectangle_gray_fill.setColor(Color.GRAY);
        rectangle_gray_fill.setStyle(Paint.Style.FILL);

        text_paint = new Paint();
        text_paint.setColor(Color.BLACK);

        selected_blue_stroke = new Paint();
        selected_blue_stroke.setColor(Color.BLUE);
        selected_blue_stroke.setStyle(Paint.Style.STROKE);

        ondrop_green_stroke = new Paint();
        ondrop_green_stroke.setColor(Color.GREEN);
        ondrop_green_stroke.setStyle(Paint.Style.STROKE);
    }

    public void draw(Canvas canvas) {
        // still draw hidden shapes if is editting
        if (is_hidden || GameManager.cur_game.is_editting) {
            // text
            if (!this.referred_text.isEmpty()) {
                // origin of the sentence
                float x = bounding_box.left;
                float y = bounding_box.bottom;
                canvas.drawText(referred_text, 0, referred_text.length(), x, y, text_paint);

            } else if (!this.referred_image_name.isEmpty()) {
                int idx = context.getResources().getIdentifier(this.referred_image_name,
                        "drawable", context.getPackageName());
                BitmapDrawable picToDraw = (BitmapDrawable) context.getResources().getDrawable(idx);
                Bitmap bitmap = picToDraw.getBitmap();
                canvas.drawBitmap(bitmap, null, this.bounding_box, null);

            } else {
                canvas.drawRect(bounding_box, rectangle_gray_fill);
            }

            if (is_selected) {
                canvas.drawRect(bounding_box, selected_blue_stroke);
            } else if (is_ondrop) {
                canvas.drawRect(bounding_box, ondrop_green_stroke);
            }
        }
    }


}
