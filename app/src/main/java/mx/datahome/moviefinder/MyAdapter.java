package mx.datahome.moviefinder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mx.datahome.moviefinder.pojo.SearchResponse;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private SearchResponse mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvYear, tvType;
        public ImageView imageView;
        public MyViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_title);
            tvYear = v.findViewById(R.id.tv_year);
            tvType = v.findViewById(R.id.tv_type);
            imageView = v.findViewById(R.id.image_view);
        }
    }
    public MyAdapter(SearchResponse myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycle_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvTitle.setText(mDataset.getSearch().get(position).getTitle());
        holder.tvYear.setText(mDataset.getSearch().get(position).getYear());
        holder.tvType.setText(mDataset.getSearch().get(position).getType());
        if(!mDataset.getSearch().get(position).getPoster().equals("N/A"))
            Picasso.get().load(mDataset.getSearch().get(position).getPoster()).into(holder.imageView);
        else
            Picasso.get().load("https://cdn.amctheatres.com/Media/Default/Images/noposter.jpg").into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataset.getSearch().size();
    }
}