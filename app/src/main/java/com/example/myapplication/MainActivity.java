package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "ad91b59ba50186e1f1d29ef5c4577ed2";
    private List<MoviesModel> moviesModels = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private int page;
    private int page_size = 15;
    private boolean isLastPage;
    private boolean isLoading;
    private MoviesAdapter moviesAdapter;
    private RecyclerView movies_recyclerview;
    private List<MoviesModel> moviesModels1 = new ArrayList<>();
    private List<MoviesModel> moviesModels2 = new ArrayList<>();

    private LinearLayoutManager LayoutManager;
    private LinearLayoutManager LayoutManager1;


    private HorizontalAdapter horizontalAdapter,horizontalAdapter1;


    private RecyclerView horiz_recyclerview;
    private RecyclerView horiz_recyclerview_1;

    private Retrofit retrofit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		movies_recyclerview = findViewById(R.id.movies_recyclerview);
        horiz_recyclerview = findViewById(R.id.movies_recyclerview_1);
        horiz_recyclerview_1 = findViewById(R.id.movies_recyclerview_2);


        horizontalAdapter = new HorizontalAdapter(new ListItemClickListener<MoviesModel>() {
            @Override
            public void onItemClicked(MoviesModel item) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("overview", item.getOverview());
                intent.putExtra("data_release", item.getReleaseDate());
                intent.putExtra("image", item.getPosterPath());
                intent.putExtra("vote_average", item.getVoteAverage());
                startActivity(intent);
            }
        });
        horizontalAdapter1 = new HorizontalAdapter(new ListItemClickListener<MoviesModel>() {
            @Override
            public void onItemClicked(MoviesModel item) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("overview", item.getOverview());
                intent.putExtra("data_release", item.getReleaseDate());
                intent.putExtra("image", item.getPosterPath());
                intent.putExtra("vote_average", item.getVoteAverage());
                startActivity(intent);
            }
        });

        LayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        horiz_recyclerview.setLayoutManager(LayoutManager);
        horiz_recyclerview_1.setLayoutManager(LayoutManager1);


        moviesAdapter = new MoviesAdapter(new ListItemClickListener<MoviesModel>() {
			@Override
			public void onItemClicked(MoviesModel item) {
				Intent intent = new Intent(MainActivity.this, DetailActivity.class);
				intent.putExtra("title", item.getTitle());
				intent.putExtra("overview", item.getOverview());
				intent.putExtra("data_release", item.getReleaseDate());
				intent.putExtra("image", item.getPosterPath());
				intent.putExtra("vote_average", item.getVoteAverage());
				startActivity(intent);
			}
		});
		mLayoutManager = new LinearLayoutManager(this);
		movies_recyclerview.setLayoutManager(mLayoutManager);
		retrofit = new Retrofit.Builder()
				.baseUrl("https://api.themoviedb.org/3/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		page = 1;

		try {
			getMoviesResponse();
		} catch (Exception e) {
			Log.d("tag", "Main Ex : " + e.getMessage());
		}
		movies_recyclerview.setAdapter(moviesAdapter);

        horiz_recyclerview.setAdapter(horizontalAdapter);
        horiz_recyclerview_1.setAdapter(horizontalAdapter1);
      getMoviesResponse1();
      getMoviesResponse2();

		movies_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}


			@Override
			public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);

				int visibleItem = mLayoutManager.getChildCount();
				int totalItem = mLayoutManager.getItemCount();
				int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

				if (!isLoading && !isLastPage) {

					if ((visibleItem + firstVisibleItemPosition) * 2 >= totalItem
							&& firstVisibleItemPosition >= 0
							&& totalItem >= page_size) {

						page++;
						getMoviesResponse();
					}
				}
			}
		});
	}


	private void getMoviesResponse() {

		isLoading = true;

		RequestInterface requestInteface = retrofit.create(RequestInterface.class);
		Call<MovieResponseWrapper> call = requestInteface.getMoviesJson(API_KEY, page);

		call.enqueue(new Callback<MovieResponseWrapper>() {
			@Override
			public void onResponse(Call<MovieResponseWrapper> call, Response<MovieResponseWrapper> response) {
				isLoading = false;

				if (response.body() != null) {

					if (response.body().getResults().size() > 0) {
					    //moviesModels.clear();

                        moviesModels.addAll(response.body().getResults());
						moviesAdapter.submitList(new ArrayList<>(moviesModels));
						isLastPage = moviesModels.size() < page_size;
					} else {
						isLastPage = true;
					}
				} else {
					Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
				}
			}


			@Override
			public void onFailure(Call<MovieResponseWrapper> call, Throwable t) {
				t.printStackTrace();


			}
		});
	}
    private void getMoviesResponse1() {


        RequestInterface requestInteface = retrofit.create(RequestInterface.class);
        Call<MovieResponseWrapper> call = requestInteface.getMoviesJson1(API_KEY);

        call.enqueue(new Callback<MovieResponseWrapper>() {
            @Override
            public void onResponse(Call<MovieResponseWrapper> call, Response<MovieResponseWrapper> response) {


                if (response.body() != null) {

                    if (response.body().getResults().size() > 0) {
                        moviesModels1.addAll(response.body().getResults());
                        horizontalAdapter.submitList(new ArrayList<>(moviesModels1));


                    } else {
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<MovieResponseWrapper> call, Throwable t) {
                t.printStackTrace();


            }
        });
    }

    private void getMoviesResponse2() {


        RequestInterface requestInteface = retrofit.create(RequestInterface.class);
        Call<MovieResponseWrapper> call = requestInteface.getMoviesJson2(API_KEY);

        call.enqueue(new Callback<MovieResponseWrapper>() {
            @Override
            public void onResponse(Call<MovieResponseWrapper> call, Response<MovieResponseWrapper> response) {


                if (response.body() != null) {

                    if (response.body().getResults().size() > 0) {
                        moviesModels2.addAll(response.body().getResults());
                        horizontalAdapter1.submitList(new ArrayList<>(moviesModels2));


                    } else {
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                }
            }


            @Override
            public void onFailure(Call<MovieResponseWrapper> call, Throwable t) {
                t.printStackTrace();


            }
        });
    }

}




