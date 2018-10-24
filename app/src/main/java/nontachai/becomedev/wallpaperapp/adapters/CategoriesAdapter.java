package nontachai.becomedev.wallpaperapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.List;

import nontachai.becomedev.wallpaperapp.R;
import nontachai.becomedev.wallpaperapp.activities.WallpaperActivity;
import nontachai.becomedev.wallpaperapp.models.Category;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>{

    private Context mContext;
    private List<Category> categoryList;
    private InterstitialAd mInterstitialAd;

    public CategoriesAdapter(Context mContext, List<Category> categoryList) {
        this.mContext = mContext;
        this.categoryList = categoryList;


        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId("ca-app-pub-1787292132881960/3784869672");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });


    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_categories,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category c = categoryList.get(position);
        holder.textView.setText(c.name);
        Glide.with(mContext).load(c.thumb).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView textView;
        ImageView imageView;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view_cate_name);
            imageView = itemView.findViewById(R.id.image_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

//            ///load banner
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }



            int p = getAdapterPosition();
            Category c =categoryList.get(p);

            Intent intent = new Intent(mContext, WallpaperActivity.class);
            intent.putExtra("category",c.name);
            mContext.startActivity(intent);

        }
    }
}
