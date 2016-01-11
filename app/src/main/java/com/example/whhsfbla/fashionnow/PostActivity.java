package com.example.whhsfbla.fashionnow;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostActivity extends Activity {

    private final int SELECT_PHOTO = 1;
    private final String IMGUR_CLIENT_ID = "6b54ed1388616b2";
    private ImageView imagePreview;
    private Button uploadButton;
    private InputStream imageStream;
    private HashMap events;
    private String imageURL;
    private Uri uploadFileUri;
    private Bitmap bitmap;
    TextView txtPostTitle;
    EditText editText;
    Button btnChoosePic, btnUploadPost;
    ImageView imgPreview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        txtPostTitle = (TextView) findViewById(R.id.txtPostTitle);
        editText = (EditText) findViewById(R.id.editText);
        btnChoosePic = (Button) findViewById(R.id.btnChoosePic);
        btnUploadPost = (Button) findViewById(R.id.btnMakePost);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);

        txtPostTitle.setText("Title:");
        btnChoosePic.setText("Choose Picture");
        btnUploadPost.setText("Create Post");
        btnUploadPost.setEnabled(false);

        /*btnChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openImageIntent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }); */

        btnChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openImageIntent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnUploadPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPost();
                finish();
            }
        });

    }

    private void openImageIntent() throws IOException {
        final File root = new File(Environment.getExternalStorageDirectory() +
                File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fileName = File.createTempFile("tmp", ".txt").getPath();
        final File sdImageMainDirectory = new File(root, fileName);

        uploadFileUri = Uri.fromFile(sdImageMainDirectory);

        //Camera
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : resolveInfoList) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uploadFileUri);
            cameraIntents.add(intent);
        }

        //Filesystem
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, SELECT_PHOTO);
    }

    private void uploadPost() {
        // Locate the image in res > drawable-hdpi
        bitmap = BitmapFactory.decodeResource(getResources(), R.id.imgPreview);
        // Convert it to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        // Compress image to lower quality scale 1 - 100
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        // Create the ParseFile
        ParseFile file = new ParseFile("image.png",image);
        // Upload the image into ParseCloud
        file.saveInBackground();

        // Create a New Class called "ImageUpload" in Parse
        ParseObject post = new ParseObject("Post");

        // Create a column named "ImageName" and set the string
        post.put("title", editText.getText().toString());

        // Create a column named "ImageFile" and insert the image
        post.put("ImageFile", file);

        post.put("user", User.username);

        // Create the class and the columns
        post.saveInBackground();

        // Show a simple toast message
        Toast.makeText(PostActivity.this, "Post Uploaded",
                Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageIntent) {
        super.onActivityResult(requestCode, resultCode, imageIntent);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PHOTO) {
                //Get the URI
                final boolean isCamera;
                if (imageIntent == null) {
                    isCamera = true;
                } else {
                    final String action = imageIntent.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(MediaStore.ACTION_IMAGE_CAPTURE);

                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = uploadFileUri;
                } else {
                    selectedImageUri = imageIntent == null ? null : imageIntent.getData();
                }

                //Get the Bitmap from the URI, and set it to the ImageView
                try {
                    imageStream = getContentResolver().openInputStream(selectedImageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imgPreview.setImageBitmap(selectedImage);
                    btnUploadPost.setEnabled(true);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
