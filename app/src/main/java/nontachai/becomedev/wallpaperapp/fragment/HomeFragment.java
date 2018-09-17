package nontachai.becomedev.wallpaperapp.fragment;


import android.os.Bundle;
import android.renderscript.Script;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import nontachai.becomedev.wallpaperapp.R;
import nontachai.becomedev.wallpaperapp.adapters.CategoriesAdapter;
import nontachai.becomedev.wallpaperapp.models.Category;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ProgressBar progressBar;
    private DatabaseReference dbCategories;
    private List<Category> categoryList;

    private RecyclerView recyclerView;

    private CategoriesAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progreebar);
        progressBar.setVisibility(View.VISIBLE);
//
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        categoryList = new ArrayList<>();
        adapter = new CategoriesAdapter(getActivity(),categoryList);
        recyclerView.setAdapter(adapter);

        dbCategories = FirebaseDatabase.getInstance().getReference("categories");
        dbCategories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    progressBar.setVisibility(View.GONE);
                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        String name = ds.getKey();
                        String desc = ds.child("desc").getValue(String.class);
                        String thumbnail = ds.child("thumbnail").getValue(String.class);

                        Category c = new Category(name,desc,thumbnail);
                        categoryList.add(c);
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
