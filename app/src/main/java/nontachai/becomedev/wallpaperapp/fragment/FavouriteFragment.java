package nontachai.becomedev.wallpaperapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import nontachai.becomedev.wallpaperapp.R;
import nontachai.becomedev.wallpaperapp.adapters.WallpaperAdapter;
import nontachai.becomedev.wallpaperapp.models.Wallpaper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {


    List<Wallpaper> fabWalls;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    WallpaperAdapter adapter;

    DatabaseReference dbFavs;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fabWalls = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progreebar);
        adapter = new WallpaperAdapter(getActivity(),fabWalls);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_area,new SettingFragment()).commit();
            return;
        }

        dbFavs = FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("favourites");

        progressBar.setVisibility(View.VISIBLE);

        dbFavs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressBar.setVisibility(View.GONE);
                for (DataSnapshot category : dataSnapshot.getChildren())
                {
                    for (DataSnapshot wallpaperSnapshot:category.getChildren())
                    {
                        String id = wallpaperSnapshot.getKey();
                        String title = wallpaperSnapshot.child("title").getValue(String.class);
                        String desc = wallpaperSnapshot.child("desc").getValue(String.class);
                        String url = wallpaperSnapshot.child("url").getValue(String.class);

                        Wallpaper w = new Wallpaper(id,title,desc,url,category.getKey());
                        w.isFavourite = true;
                        fabWalls.add(w);

                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
