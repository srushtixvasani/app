package com.example.dailynotdilly;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.dailynotdilly.adapters.ManifestFeatureAdapter;
import com.example.dailynotdilly.models.ManifestFeature;

import java.util.ArrayList;
import java.util.List;

public class ManifestFragment extends Fragment {

    private ArrayList<ManifestFeature> manifestFeatureArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ManifestFeatureAdapter manifestRecyclerViewAdapter = new ManifestFeatureAdapter(manifestFeatureArrayList);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.manifest_fragment, container, false);

        recyclerView = view.findViewById(R.id.manifest_recycler_list);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Context context = view.getContext();

        // instead of Linear or Grid Layout Manage, StaggeredGridLayoutManager is used to show
        // items in a staggered grid, like in the Pinterest app
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager
                (2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(manifestRecyclerViewAdapter);

    }

    void setManifestFeature(final List<ManifestFeature> manifestList){
        for (ManifestFeature manifestFeature : manifestList) {
            if(!manifestFeatureArrayList.contains(manifestFeature)) {
                manifestFeatureArrayList.add(manifestFeature);
                manifestRecyclerViewAdapter.
                        notifyItemInserted(manifestFeatureArrayList.indexOf(manifestFeature));

            }
        }

    }

}
