package com.example.dailynotdilly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.dailynotdilly.adapters.ManifestFeatureAdapter;
import com.example.dailynotdilly.models.ManifestFeature;
import com.example.dailynotdilly.models.ManifestViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ManifestFragment extends Fragment {

    private ArrayList<ManifestFeature> manifestFeatureArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ManifestFeatureAdapter manifestRecyclerViewAdapter = new ManifestFeatureAdapter(manifestFeatureArrayList);
    private ManifestViewModel manifestViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        manifestViewModel = new ViewModelProvider(requireActivity(),getDefaultViewModelProviderFactory()).get(ManifestViewModel.class);

//        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
//        ManifestViewModel manifestViewModel = new ViewModelProvider(this, factory).get(ManifestViewModel.class);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
    }

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

        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        //set add button to let user add a new manifest
        AppCompatImageButton addButton = view.findViewById(R.id.add_manifest);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddManifestActivity.class);
                startActivity(i);
            }
        });


    }

    void setManifestFeature(final List<ManifestFeature> manifestList) {
        for (ManifestFeature manifestFeature : manifestList) {
            if (!manifestFeatureArrayList.contains(manifestFeature)) {
                manifestFeatureArrayList.add(manifestFeature);
                manifestRecyclerViewAdapter.
                        notifyItemInserted(manifestFeatureArrayList.indexOf(manifestFeature));

            }
        }

    }

    private void setupViewModel() {

        manifestViewModel.getManifests().observe(getViewLifecycleOwner(), new Observer<List<ManifestFeature>>() {
            @Override
            public void onChanged(List<ManifestFeature> manifestFeatures) {
                for (ManifestFeature manifestFeature : manifestFeatures) {
                    Log.d("TAG", "onChanged: " + manifestFeature.toString());

                    manifestFeatureArrayList.add(manifestFeature);
                    manifestRecyclerViewAdapter.notifyItemInserted(manifestFeatureArrayList.indexOf(manifestFeature));
                }
            }
        });

        manifestRecyclerViewAdapter.setEachRowOnClickListener(new ManifestFeatureAdapter.eachRowOnClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                ManifestFeature manifestFeature = manifestFeatureArrayList.get(position);

                // test to see if onItemClick and listener works.
//                Log.d("test", "onItemClick" + manifestFeature.getName());

                Intent intent = new Intent(getContext(), ManifestDetailActivity.class);
                intent.putExtra("manifestation", manifestFeature);
                startActivity(intent);
            }
        });

    }



}


