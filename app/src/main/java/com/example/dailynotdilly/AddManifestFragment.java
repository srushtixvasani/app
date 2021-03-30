package com.example.dailynotdilly;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.dailynotdilly.data.ManifestDatabase;
import com.example.dailynotdilly.models.ManifestFeature;
import com.example.dailynotdilly.models.ManifestViewModel;
import com.google.android.material.button.MaterialButton;

import java.lang.ref.WeakReference;

public class AddManifestFragment extends Fragment {
    private MaterialButton addImage;
    private ImageView imageView;
    private String imageURL;
    private EditText title;
    private EditText description;
    private MaterialButton addManifest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addManifest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save the added manifest
                addManifest();
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    //method to add manifestation to the feature
    private void addManifest(){
        String title = this.title.getText().toString().trim();
        String description = this.description.getText().toString().trim();

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


}
