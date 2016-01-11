package com.example.whhsfbla.fashionnow;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.preference.PreferenceActivity;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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
    private String[] eventNames;
    private HashMap events;
    private String imageURL;
    private Uri uploadFileUri;

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

        btnUploadPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openImageIntent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    private void uploadImage() {
        //The drawable from the ImageView is converted to a bitmap, and then to a byte array, and then encoded,
        //and sent as a request parameter to the Imgur website.
        Bitmap bitmap = ((BitmapDrawable) imagePreview.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String base64Image = Base64.encodeToString(b, Base64.DEFAULT);
        //AsyncHttpClient client = new AsyncHttpClient();
       //client.addHeader("Authorization", "Client-ID " + IMGUR_CLIENT_ID);

        //RequestParams requestParams = new RequestParams();
        //requestParams.add("image", base64Image);

        /*client.post("https://api.imgur.com/3/image", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, JSONObject response) {
                try {
                    //Get the Imgur URL for the image
                    imageURL = response.getJSONObject("data").getString("link");
                    showDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            */
        }
    }

