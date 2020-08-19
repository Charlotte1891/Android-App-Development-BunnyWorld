package edu.stanford.cs108.bunnyworld;

import android.app.Activity;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// extends `Activity` rather than AppCompatActivity
// to hide the title `BunnyWorld`
public class EditShapeActivity extends Activity {


    private EditText shape_name;
    private EditText x_input, y_input, width_input, height_input;
    private TextView scripts, page_name;
    private CheckBox hidden_cbox, movable_cbox;
    private Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_shape);
        init();

    }

    private void init() {
        game = GameCanvas.game;

        shape_name = findViewById(R.id.shape_name_editText);
        x_input = findViewById(R.id.shape_X_editText);
        y_input = findViewById(R.id.shape_Y_editText);
        width_input = findViewById(R.id.shape_W_editText);
        height_input = findViewById(R.id.shape_H_editText);
        scripts = findViewById(R.id.shape_script_text);
        hidden_cbox = findViewById(R.id.shape_hidden_checkBox);
        movable_cbox = findViewById(R.id.shape_movable_checkBox);
        page_name = findViewById(R.id.page_name);


    }

    public void updateShapeProperty(View view) {
        if (GameCanvas.cur_shape == null) {
            return;
        }
        Shape selected_shape = GameCanvas.cur_shape;
        String new_name = shape_name.getText().toString();
        String old_name = selected_shape.shape_name;

        if (!new_name.equals(old_name)) {
            if (game.shapes_name_pool.contains(new_name)) {
                Toast.makeText(getApplicationContext(),
                        "Shape name is duplicate!",
                        Toast.LENGTH_SHORT).show();
            } else {
                selected_shape.shape_name = new_name;
                game.update_name_pool();
            }

        }

        String str_x = x_input.getText().toString();
        String str_y = y_input.getText().toString();
        String str_w = width_input.getText().toString();
        String str_h = height_input.getText().toString();

        float new_left = str_x.isEmpty() ? selected_shape.bounding_box.left : Float.valueOf(str_x);
        float new_top = str_y.isEmpty() ? selected_shape.bounding_box.top : Float.valueOf(str_y);
        float new_right = str_w.isEmpty() ? selected_shape.bounding_box.left : Float.valueOf(str_w) + new_left;
        float new_bottom = str_h.isEmpty() ? selected_shape.bounding_box.bottom : Float.valueOf(str_h) + new_top;

        selected_shape.bounding_box = new RectF(new_left, new_top, new_right, new_bottom);

        // TODO: deal with script
        // TODO: add font style

        selected_shape.is_hidden = hidden_cbox.isChecked();
        selected_shape.is_movable = movable_cbox.isChecked();

        Toast.makeText(getApplicationContext(),
                "Update Successfully!",
                Toast.LENGTH_SHORT).show();

        GameCanvas.game_canvas.invalidate();

    }
}
