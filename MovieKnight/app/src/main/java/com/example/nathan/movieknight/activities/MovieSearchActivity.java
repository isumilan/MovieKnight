package com.example.nathan.movieknight.activities;

        /**
 * Created by chaitanyap7 on 4/19/16.
 */
//public class MovieSearchActivity extends NavigationDrawer {
//
//    ArrayList<String> movieNames;
//    ArrayList<String> movieImages;
//    ArrayList<Integer> movieID;
//    List<MovieBox> moviesFound;
//
//
//    android.widget.SearchView sv;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search);
//        boolean movie = false;
//        sv = (android.widget.SearchView) findViewById(R.id.movieSearchView2);
//
//        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String text) {
//                Log.d("SearchClass","SubmitWorks");
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String text) {
//                Log.d("SearchClass","ChangeWorks");
//                return false;
//            }
//        });
//
//    }
//
//
//    private void getMovieResults(String movieName) {
//
//        TmdbService movieService = ((MovieKnightAppli)getApplication()).getMovieService();
//
//        Call<MovieResults> infoCall = movieService.searchMovies(TmdbConnector.API_KEY, movieName);
//
//        infoCall.enqueue(new Callback<MovieResults>() {
//            @Override
//            public void onResponse(Response<MovieResults> response) {
//                MovieResults results = response.body();
//                if(results != null)
//                    setUpDetails(results);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//
//            }
//        });
//    }
//
//    private void setUpDetails(MovieResults results) {
//        moviesFound = results.getMovies();
//        String works = moviesFound.get(0).getTitle();
//        if(moviesFound == null) {
//            Log.d("MovieSearch","failed");
//        }
//        Log.d("MovieSearch",works);
//    }
//
//
//}






//@Override
//public boolean onCreateOptionsMenu(Menu menu) {
//
//
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_movies, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//
//        SearchManager searchManager = (SearchManager) MovieSearchActivity.this.getSystemService(Context.SEARCH_SERVICE);
//
//        SearchView searchView = null;
//        if (searchItem != null) {
//        searchView = (SearchView) searchItem.getActionView();
//        }
//        if (searchView != null) {
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(MovieSearchActivity.this.getComponentName()));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//@Override
//public boolean onQueryTextSubmit(String searchText) {
//
//        getMovieResults(searchText);
//
//        return false;
//        }
//
//@Override
//public boolean onQueryTextChange(String text) {
//        return false;
//        }
//        });
//        }
//
//        return super.onCreateOptionsMenu(menu);
//        }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu_movies, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//
//        SearchManager searchManager = (SearchManager) MovieSearchActivity.this.getSystemService(Context.SEARCH_SERVICE);
//
//        SearchView searchView = null;
//        if (searchItem != null) {
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(MovieSearchActivity.this.getComponentName()));
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String searchText) {
//
//                    getMovieResults(searchText);
//
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String text) {
//                    return false;
//                }
//            });
//        }
//
//        return super.onCreateOptionsMenu(menu);
//    }
//


//
//    public ArrayList<String> getAutoComplete(String input) {
//        ArrayList<String> movieNames = null;
//        ArrayList<String> moviesID = null;
//
//
//        return movieNames;
//    }


//class MovieAutoCompleteAdapter extends
//        ArrayAdapter<String> implements Filterable {
//    private ArrayList<String> resultList;
//
//    public MovieAutoCompleteAdapter(Context context, int textViewResourceId) {
//        super(context, textViewResourceId);
//    }
//
//    @Override
//    public int getCount() {
//        return resultList.size();
//    }
//
//    @Override
//    public String getItem(int index) {
//        return resultList.get(index);
//    }
//
//    @Override
//    public Filter getFilter() {
//        Filter filter = new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                FilterResults filterResults = new FilterResults();
//                if (constraint != null) {
//                    // Get the autocomplete results.
//                    resultList = getAutoComplete(constraint.toString());
//
//                    // Assign the data to the FilterResults
//                    filterResults.values = resultList;
//                    filterResults.count = resultList.size();
//                }
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
//                if (results != null && results.count > 0) {
//                    notifyDataSetChanged();
//                } else {
//                    notifyDataSetInvalidated();
//                }
//            }
//        };
//        return filter;
//    }
//}


    //In onCreate
//    AutoCompleteTextView autoCompleteMovieSearch =
//            (AutoCompleteTextView) view.findViewById(R.id.autoCompleteSearch);
//
//autoCompleteMovieSearch.setAdapter
//        (new MoviesAutoCompleteAdapter(MainActivity.this,
//        android.R.layout.simple_dropdown_item_1line));
//
//        autoCompleteMovieSearch.setOnItemClickListener(MainActivity.this);