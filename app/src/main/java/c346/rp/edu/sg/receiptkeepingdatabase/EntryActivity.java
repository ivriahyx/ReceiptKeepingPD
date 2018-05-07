package c346.rp.edu.sg.receiptkeepingdatabase;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 16023018 on 17/12/2017.
 */

public class EntryActivity extends AppCompatActivity {

    TextView tvC, tvP, tvD, tvCat;
    EditText etContent, etPriority, etDate, etCat, etImage;
    Button btnAction;

    CommentsDataSource datasource;

    //date
    static final int DATE_ID = 0;
    int year, month, day;

    String text = "";

    //image
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        tvC = (TextView) findViewById(R.id.textViewC);
        tvP = (TextView) findViewById(R.id.textViewP);
        etContent = (EditText) findViewById(R.id.etContent);
        etPriority = (EditText) findViewById(R.id.etPriority);
        btnAction = (Button) findViewById(R.id.btnAction);

        tvD = (TextView) findViewById(R.id.textViewD);
        etDate = (EditText) findViewById(R.id.etDate);

        tvCat = (TextView) findViewById(R.id.textViewCat);
        etCat = (EditText) findViewById(R.id.etCat);

        etImage = (EditText) findViewById(R.id.etImage);

        datasource = new CommentsDataSource(EntryActivity.this);

        Intent data = this.getIntent();
        String content = data.getStringExtra("content");
        String date = data.getStringExtra("date");
        Double priority = data.getDoubleExtra("priority", 0);
        final long id = data.getLongExtra("id", -1);

        String category = data.getStringExtra("category");
        String image = data.getStringExtra("image");

        String mode = data.getStringExtra("mode");

        if (mode.equals("Add")) {
            btnAction.setText("Add");
        } else {
            etContent.setText(content);
            etPriority.setText("" + priority);
            etDate.setText(date);
            etCat.setText(category);
            etImage.setText(image);
            btnAction.setText("Edit");
        }

        //get date now
        CalendarDate();
        //show dialog
        showDialogOnEditTextDatePickerDialog();

        etImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dispatchTakePictureIntent();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                etImage.setText(timeStamp);
            }
        });

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = etContent.getText().toString();
                Double priority = Double.parseDouble(etPriority.getText().toString());
                String date = etDate.getText().toString();
                String category = etCat.getText().toString();
                String image = etImage.getText().toString();

                if (btnAction.getText().equals("Add")) {
                    datasource.addComment(comment, priority, date,category,image);
                } else {
                    datasource.updateComment(comment, priority, date, category, image, id);
                }
                Intent data = new Intent();
                setResult(RESULT_OK, data);
                finish();
            }

        });
    }

     //DatePickerDialog
    public void showDialogOnEditTextDatePickerDialog() {
        etDate = (EditText) findViewById(R.id.etDate);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_ID);
            }
        });
    }

    public DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            year = y;
            month = m;
            day = d;
            etDate.setText(day + "/" + (month + 1) + "/" + year);
        }
    };

    public Dialog onCreateDialog(int id, Bundle args) {
        if (id == DATE_ID) {
            return new DatePickerDialog(this, dpd, year, month, day);
        }
        return null;
    }

    public void CalendarDate() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

    }
    //image
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            etImage.setText(imageBitmap.toString());
        }
    }
    String mCurrentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "" + timeStamp + "";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    }

