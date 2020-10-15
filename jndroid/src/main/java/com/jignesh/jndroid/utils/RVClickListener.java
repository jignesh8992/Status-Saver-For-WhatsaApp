package com.jignesh.jndroid.utils;

public interface RVClickListener {

    void onItemClick(int position);

    // How To Implement
     /*public class ThumbAdapter extends RecyclerView.Adapter<ThumbAdapter.MyAdapterHolder> {

        public RVClickListener clickListener;
        Context mContext;

        public ThumbAdapter(Context mContext, RVClickListener clickListener) {
            this.mContext = mContext;
            this.clickListener = clickListener;
        }


        @NonNull
        @Override
        public ThumbAdapter.MyAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int
                viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category,
                    parent, false);
            final MyAdapterHolder holder = new MyAdapterHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(holder.getPosition());
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ThumbAdapter.MyAdapterHolder holder, int position) {
            ....
        }

        @Override
        public int getItemCount() {
         ...
        }


        public class MyAdapterHolder extends RecyclerView.ViewHolder {
            ....
            public MyAdapterHolder(View itemView) {
                super(itemView);
                .....
            }
        }
    }*/


}