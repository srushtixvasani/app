package com.example.dailynotdilly;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.dailynotdilly.adapters.ManifestFeatureAdapter;
import com.example.dailynotdilly.data.ManifestDatabase;
import com.example.dailynotdilly.models.ManifestFeature;
import com.example.dailynotdilly.models.ManifestViewModel;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddManifestFragment extends Fragment {

    public static final String PERMISSIONS_REQUEST_COUNT = "PERMISSIONS_REQUEST_COUNT";
    public static final int MAX_NO_REQUEST_COUNT = 2;
    public static final int REQUEST_CODE_PERMISSIONS = 1111;
    public static final int REQUEST_CODE_IMAGE = 100;
    private int permissionRequestCount;
    private static final List<String> permissions = Arrays.asList
            (Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE);
    private AppCompatImageButton backButton;
    private MaterialButton addImage;
    private ImageView imageView;
    private String imageURL;
    Uri imageUri;
    private EditText title;
    private EditText description;
    private MaterialButton addManifest;
    private final String TAG = this.getClass().getName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            permissionRequestCount = savedInstanceState.getInt(PERMISSIONS_REQUEST_COUNT, 0);
        }

        // make sure the app has been allowed the permissions
        requestPermissionIfNecessary();

    }

    private void requestPermissionIfNecessary() {
        if (!checkCallingPermission()) {
            if (permissionRequestCount < MAX_NO_REQUEST_COUNT) {
                permissionRequestCount += 1;

                ActivityCompat.requestPermissions(getActivity(), permissions.toArray( new String[0]),
                        REQUEST_CODE_PERMISSIONS
                        );
            } else {
                Toast.makeText(getContext(), R.string.set_permissions, Toast.LENGTH_LONG).show();
                addImage.setEnabled(false); // disable the add image button if permission is not granted
            }

        }

    }

    private boolean checkCallingPermission() {
        boolean hasPermission = true;
        for (String permission : permissions) {
            hasPermission &= ContextCompat.checkSelfPermission(
                    getContext(), permission) == PackageManager.PERMISSION_GRANTED;
        }
        return hasPermission;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_manifest_fragment, container, false);

        addImage = view.findViewById(R.id.add_image_button);
        imageView = view.findViewById(R.id.manifest_add_image);
        title = view.findViewById(R.id.add_title);
        description = view.findViewById(R.id.add_description);
        addManifest = view.findViewById(R.id.add_manifest_button);
        backButton = view.findViewById(R.id.manifest_add_back);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ManifestActivity.class);
                startActivity(i);
            }
        });

        addManifest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the added manifest
                addManifest();
                Intent intent = new Intent(getActivity(), ManifestActivity.class);
                intent.putExtra("ImageURL", imageURL);
                startActivity(intent);
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // need Intent.ACTION_OPEN_DOCUMENT as it saves it even when the app re-runs
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);


            }
        });

    }

    //method to add manifestation to the feature
    private void addManifest(){
        String title = this.title.getText().toString().trim();
        String description = this.description.getText().toString().trim();
        imageURL = imageUri.toString();

        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(description)) {

        } else {
           ManifestFeature manifestFeature = new ManifestFeature(title, description, imageURL);
           new addManifestTask(getContext()).execute(manifestFeature);
        }
    }

    private static class addManifestTask extends AsyncTask<ManifestFeature, Void, Long> {
        private final WeakReference<Context> weakRef;

        public addManifestTask(Context context) {
            this.weakRef = new WeakReference<>(context);
        }

        @Override
        protected Long doInBackground(ManifestFeature... manifestFeatures) {

            // invoke the database
            ManifestDatabase db = ManifestDatabase.getInstance
                    (weakRef.get().getApplicationContext());

            return db.manifestDao().insert(manifestFeatures[0]);
        }

        @Override
        protected void onPostExecute(Long outcome) {
            if (outcome != (long) -1) {
                Toast.makeText(weakRef.get(), R.string.success, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(weakRef.get(), R.string.failure, Toast.LENGTH_LONG).show();
            }
        }




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_IMAGE:
                    imageUri = data.getData();
                    handleImageRequestResult(data);
                    ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(), imageUri);
                    try {
                        Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                        imageView.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    Log.i(TAG, "Request code unknown");
            }
        } else {
            Log.e(TAG, String.format("Unexpected result code %s", resultCode));
        }




    }

    private void handleImageRequestResult(Intent data) {
        Uri imageUri = null;

        if (data.getClipData() != null) {
            imageUri = data.getClipData().getItemAt(0).getUri();
        } else if (data.getData() != null) {
            imageUri = data.getData();
        }

        if (imageUri == null) {
            Log.e(TAG, "Invalid image input: ");
            return;
        }



    }
}
