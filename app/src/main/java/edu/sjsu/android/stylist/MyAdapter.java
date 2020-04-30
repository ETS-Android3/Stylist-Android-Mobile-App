package edu.sjsu.android.stylist;

import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    // Other types of clothes inherited from Clothing, so they all can use this adapter
    private List<Clothing> list;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public View layout;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            imageView = (ImageView) v.findViewById(R.id.rowImage);

        }
    }

    public void add(int position, Top item) {
//        list.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public MyAdapter(List<Clothing> myDataset) {
        list = myDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Clothing clothing = list.get(position);

//        holder.imageView.setImageResource(top.getFilename());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // let user drag and drop item on model
                // create a copy of the image?
                // let user drag the image and delete it from the list, put it back to the list when user choose different item from the list
                // let user resize item
//                final Intent i = new Intent(v.getContext(), );
//                i.putExtra("position", position);
//                v.getContext().startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

