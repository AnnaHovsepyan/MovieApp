package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;


public class HorizontalAdapter extends ListAdapter<MoviesModel, HorizontalAdapter.ViewHolder> {

	private final ListItemClickListener<MoviesModel> listItemClickListener;

	public HorizontalAdapter(ListItemClickListener<MoviesModel> listItemClickListener) {
		super(new MovieItemCallback());
		this.listItemClickListener = listItemClickListener;
	}

	@NonNull
	@Override
	public HorizontalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.horizontal_list_item, viewGroup, false);
		final ViewHolder holder = new HorizontalAdapter.ViewHolder(view);
		holder.layout_1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				listItemClickListener.onItemClicked(getItem(holder.getAdapterPosition()));
			}
		});
		return holder;
	}

	@Override
	public void submitList(@Nullable List<MoviesModel> list) {
		super.submitList(list);
	}




	@Override
	public void onBindViewHolder(@NonNull final HorizontalAdapter.ViewHolder viewHolder, int i) {

		MoviesModel item = getItem(i);



            viewHolder.movie_title.setText(item.getTitle());

            String poster = "https://image.tmdb.org/t/p/w500" + item.getPosterPath();

            Picasso.get().load(poster).into(viewHolder.movie_image);

	}



	public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView movie_image;
        private TextView movie_title;

        private ViewGroup layout_1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setClickable(true);
            layout_1 = itemView.findViewById(R.id.layout_1);

            movie_image = itemView.findViewById(R.id.movie_image_1);
            movie_title = itemView.findViewById(R.id.movie_title_1);


        }
    }}
