package kellehj1.FYP.birdID;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<ContentValues> birdList;
    ArrayList<Integer> imageIds;
    Context context;

    public MyAdapter(Context context, ArrayList<ContentValues> birdList, ArrayList<Integer> imageIds) {
        this.context = context;
        this.birdList = birdList;
        this.imageIds = imageIds;
        splitBirdNames(birdList);
    }

    private void splitBirdNames(ArrayList<ContentValues> birdList) {

        for( int i = 0; i < birdList.size(); i++ ) {
            birdList.get(i).getAsString("NAME");
            birdList.get(i).getAsString("IRISHNAME");
            birdList.get(i).getAsString("LATINNAME");
        }
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
    }

    @Override
    public int getItemCount() {
        return imageIds.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewLatinName, textViewIrishName;
        ImageView imageViewRow;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewRowName);
            textViewLatinName = itemView.findViewById(R.id.textViewRowLatinName);
            textViewIrishName = itemView.findViewById(R.id.textViewRowIrishName);
            imageViewRow = itemView.findViewById(R.id.imageViewRow);
        }
    }
}
