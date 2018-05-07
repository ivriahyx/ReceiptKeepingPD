package c346.rp.edu.sg.receiptkeepingdatabase;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 16023018 on 20/12/2017.
 */

public class EntryClicked extends AppCompatActivity {
    ImageView iv;
    TextView tvTitleV, tvCostV, tvDateV, tvCatV;
    CommentsDataSource datasource;

    //image
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entryclicked);

        tvTitleV = (TextView)findViewById(R.id.textViewTitleV);
        tvCostV = (TextView)findViewById(R.id.textViewCostV);
        tvDateV = (TextView)findViewById(R.id.textViewDateV);
        tvCatV = (TextView)findViewById(R.id.textViewCatV);
        iv=(ImageView)findViewById(R.id.imageViewV);

        datasource = new CommentsDataSource(EntryClicked.this);
        datasource.getAllComments();

        Intent data = this.getIntent();
        String content = data.getStringExtra("content");
        String date = data.getStringExtra("date");
        Double priority = data.getDoubleExtra("priority", 0);
        final long id = data.getLongExtra("id", -1);

        String category = data.getStringExtra("category");
        String image = data.getStringExtra("image");


        tvTitleV.setText(content);
        tvCostV.setText(""+priority);
        tvDateV.setText(date);
        tvCatV.setText(category);


    }

    //image
    /*
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            iv.setImageBitmap(imageBitmap);
        }
    }
*/
}
