package es.unican.moviesapp.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.unican.moviesapp.R;
import es.unican.moviesapp.models.Movie;

public class MoviesAdapter extends BaseAdapter implements Filterable {

    private final List<Movie> movies;
    private final Context context;
    private final View.OnClickListener listener;

    private final List<Movie> filteredMovies;
    private CustomFilter filter;

    private boolean showTitle= true;
    private boolean showDirector = true;
    private boolean showYear = true;

    public MoviesAdapter(Context context, List<Movie> movies, View.OnClickListener listener) {
        this.context = context;
        this.movies = movies;
        this.listener = listener;
        this.filteredMovies = new ArrayList<>(movies);
    }

    @Override
    public int getCount() {
        return filteredMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public void setShowDirector(boolean showDirector) {
        this.showDirector = showDirector;
    }

    public void setShowYear(boolean showYear) {
        this.showYear = showYear;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.movies_list_item_layout, parent, false);
        }
        convertView.setOnClickListener(listener);

        ImageView ivCover = convertView.findViewById(R.id.ivCover);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvYear = convertView.findViewById(R.id.tvYear);
        TextView tvDirector = convertView.findViewById(R.id.tvDirector);

        Movie movie = (Movie) getItem(position);
        assert movie != null;
        convertView.setTag(movie);

        int width = (int) context.getResources().getDimension(R.dimen.cover_width);
        Picasso.get().load(movie.getCoverUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .resize(width, 0)
                .centerCrop()
                .into(ivCover);
        tvTitle.setText(movie.getTitle());
        tvYear.setText(String.format(Locale.getDefault(), "(%d)", movie.getYear()));
        tvDirector.setText(movie.getDirector());

        tvTitle.setVisibility(showTitle ? View.VISIBLE : View.GONE);
        tvYear.setVisibility(showYear ? View.VISIBLE : View.GONE);
        tvDirector.setVisibility(showDirector ? View.VISIBLE : View.GONE);


        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Movie> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(movies);
            } else {
                String pattern = constraint.toString().toUpperCase().trim();
                for (Movie movie : movies) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(movie.getTitle());
                    sb.append(movie.getYear());
                    sb.append(movie.getDirector());
                    String movieString = sb.toString().toUpperCase();

                    if (movieString.contains(pattern)) {
                        filteredList.add(movie);
                    }
                }
            }

            results.count = filteredList.size();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredMovies.clear();
            filteredMovies.addAll((List) results.values);
            notifyDataSetChanged();
        }
    }
}
