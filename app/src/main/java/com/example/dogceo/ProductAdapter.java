package com.example.dogceo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{
    private static ClickListener clickListener;
    ArrayList<Product> mProductList;
    LayoutInflater inflater;
    Context context;
    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mProductList = products;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.detail_card, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product selectedProduct = mProductList.get(position);
        holder.setData(selectedProduct, position);

    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    public Product getItem(int pos)
    {
        return mProductList.get(pos);
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView productName;
        ImageView productImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            productName = (TextView) itemView.findViewById(R.id.textView);
            productImage = (ImageView) itemView.findViewById(R.id.imageView);

        }

        public void setData(Product selectedProduct, int position) {

            this.productName.setText(selectedProduct.getProductName());

            Glide.with(context)
                    .load(selectedProduct.getImageID())
                    .error(R.drawable.ic_launcher_background)
                    .apply(new RequestOptions().override(182, 268))
                    .centerCrop()
                    .into(this.productImage)
            ;


        }


        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ProductAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
}
