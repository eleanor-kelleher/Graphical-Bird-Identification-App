package kellehj1.FYP.birdID;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    ArrayList<ContentValues> birdList;
    ArrayList<ContentValues> birdListFull;
    ArrayList<Integer> imageIds;
    Context context;

    public MyAdapter(Context context, ArrayList<ContentValues> birdList, ArrayList<Integer> imageIds) {
        this.context = context;
        this.birdList = birdList;
        birdListFull = new ArrayList<>(birdList);
        this.imageIds = imageIds;
        splitBirdNames(birdList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textViewName.setText(birdList.get(position).getAsString("NAME"));
        holder.textViewLatinName.setText(birdList.get(position).getAsString("LATINNAME"));
        holder.textViewIrishName.setText(birdList.get(position).getAsString("IRISHNAME"));
        holder.imageViewRow.setImageResource(imageIds.get(position));

        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BirdEntryActivity.class);
                intent.putExtra("BIRD_NAME", holder.textViewName.getText());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageIds.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ContentValues> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0 ) {
                filteredList.addAll(birdListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ContentValues bird : birdListFull) {
                    if (bird.getAsString("NAME").toLowerCase().contains(filterPattern)) {
                        filteredList.add(bird);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            birdList.clear();
            birdList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewLatinName, textViewIrishName;
        ImageView imageViewRow;
        ConstraintLayout rowLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewRowName);
            textViewLatinName = itemView.findViewById(R.id.textViewRowLatinName);
            textViewIrishName = itemView.findViewById(R.id.textViewRowIrishName);
            imageViewRow = itemView.findViewById(R.id.imageViewRow);
            rowLayout = itemView.findViewById(R.id.rowLayout);
        }
    }

    private void splitBirdNames(ArrayList<ContentValues> birdList) {
        for( int i = 0; i < birdList.size(); i++ ) {
            birdList.get(i).getAsString("NAME");
            birdList.get(i).getAsString("IRISHNAME");
            birdList.get(i).getAsString("LATINNAME");
        }
    }
}
