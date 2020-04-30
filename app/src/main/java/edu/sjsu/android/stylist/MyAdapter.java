package edu.sjsu.android.stylist;

import java.util.List;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    // Other types of clothes inherited from Clothing, so they all can use this adapter
    private List list;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public View layout;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            imageView = (ImageView) v.findViewById(R.id.rowImage);

        }
    }

    public void add(int position, Clothing item) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public MyAdapter(List myDataset) {
        list = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        final ViewHolder vh = new ViewHolder(v);
        final View image = vh.imageView;
        vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Clothing item = (Clothing) list.get(vh.getAdapterPosition());
                DragData state = new DragData(item, image.getWidth(), image.getHeight());
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(image);
                ViewCompat.startDragAndDrop(image, null, shadow, state, 0);
                return true;
            }
        });
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Clothing clothing = (Clothing) list.get(position);
//        holder.imageView.setImageResource(clothing.getImage());
            Bitmap myBitmap = BitmapFactory.decodeFile(clothing.getImageLocation());
            holder.imageView.setImageBitmap(myBitmap);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

