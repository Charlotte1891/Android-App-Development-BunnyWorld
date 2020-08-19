package edu.stanford.cs108.bunnyworld;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class EditActivity extends AppCompatActivity {

    GameCanvas game_canavs;
    static int game_canvas_width;
    static int game_canvas_height;

    static int gallary_width = 270;
    static int gallary_height = 270;


    // Game Canvas
    static Shape selected_shape;
    static Page selected_page;

    private float selected_x, selected_y;
    private float planned_x, planned_y;
    private TextView display_page_name;

    Integer[] image_ids = {
            R.drawable.plus_new,
            R.drawable.carrot,
            R.drawable.bunny,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        game_canavs = findViewById(R.id.game_canvas);
        game_canvas_width = game_canavs.getWidth();
        game_canvas_height = game_canavs.getHeight();

        display_page_name = findViewById(R.id.page_name);
        display_page_name.setText(selected_page.name);

        planned_x = (int) Math.round(game_canvas_width * 0.1);
        planned_y = (int) Math.round(game_canvas_height * 0.1);

        Gallery gallery = findViewById(R.id.drawable_gallery);
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selected_shape = new Shape(getApplicationContext(), "Shape1", planned_x, planned_y);

                int img_id = image_ids[position];
                String img_name = getResources().getResourceEntryName(img_id);
                selected_shape.referred_image_name = img_name;

                // Toast different message for `plus_new`
                // TODO: assign actual meaning to plus_new, that is, just draw a gray light in game canvas
                if (!img_name.equals("plus_new")) {
                    Toast.makeText(getBaseContext(), selected_shape.referred_image_name + " is selected",
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getBaseContext(), "new shape is added",
                            Toast.LENGTH_SHORT).show();
                }

                // Add this new shape to our page
                selected_page.shapes.add(selected_shape);
                selected_shape = null;

                game_canavs.invalidate();

                // update next planned coordinates
                planned_x += Shape.DEFAULT_WIDTH + 10;
                planned_y += Shape.DEFAULT_HEIGHT / 3;

                // just bring it back in the starting point
                if (planned_x > game_canvas_width || planned_y > game_canvas_height) {
                    planned_x = (int) Math.round(game_canvas_width * 0.1);
                    planned_y = (int) Math.round(game_canvas_height * 0.1);
                }
            }
        });
    }


    public void editShape(View view) {
        selected_shape = GameCanvas.cur_shape;
        if (selected_shape != null) {
            Intent intent = new Intent(this, EditShapeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), "Please select a shape on the canvas first",
                    Toast.LENGTH_SHORT).show();
        }

    }


    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;

        public ImageAdapter(Context c) {
            context = c;
            // sets a grey background; wraps around the images
            TypedArray a = obtainStyledAttributes(R.styleable.DrawableGallery);
            itemBackground = a.getResourceId(R.styleable.DrawableGallery_android_galleryItemBackground, 0);
            a.recycle();
        }

        // returns the number of images
        public int getCount() {
            return image_ids.length;
        }

        // returns the ID of an item
        public Object getItem(int position) {
            return position;
        }

        // returns the ID of an item
        public long getItemId(int position) {
            return position;
        }

        // returns an ImageView view
        // Ref: https://www.androidinterview.com/android-gallery-view-example-displaying-a-list-of-images/
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(image_ids[position]);
            imageView.setLayoutParams(new Gallery.LayoutParams(gallary_width, gallary_height));
            imageView.setBackgroundResource(itemBackground);
            return imageView;
        }
    }
}

