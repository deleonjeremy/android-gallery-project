package com.example.androidproject.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidproject.Activities.CellActivity;
import com.example.androidproject.R;
import com.example.androidproject.Utils.ImageHelper;

import java.io.File;
import java.util.ArrayList;

// List of images (RecyclerView)
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private ArrayList<CellActivity> galleryList;
    private Context context;

    public GalleryAdapter(Context context, ArrayList<CellActivity> galleryList){
        this.galleryList = galleryList;
        this.context = context;
    }

    @NonNull
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell, viewGroup, false);
        return new GalleryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setImageFromPath(galleryList.get(i).getPath(), viewHolder.img);

        viewHolder.img.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Happens when you click on an Image
                Toast.makeText(context, "" + galleryList.get(i).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        public ViewHolder(View view){
            super(view);

            img = (ImageView) view.findViewById(R.id.img);
        }
    }

    private void setImageFromPath(String path, ImageView image){
        File imgFile = new File(path);
        if(imgFile.exists()){
            Bitmap myBitmap = ImageHelper.decodeSampleBitmapFromPath(imgFile.getAbsolutePath(), 200, 200);
            image.setImageBitmap(myBitmap);
        }
    }

}
