package the.pixel.factory.inshorts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

import the.pixel.factory.inshorts.MainActivity;
import the.pixel.factory.inshorts.R;
import the.pixel.factory.inshorts.models.News;

/**
 * Created by dhruvesh on 31/12/17.
 */

public class News_Adapter extends RecyclerView.Adapter<News_Adapter.MyViewHolder>{

    private Context mContext;
    private List<News> newsList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,description,likes;
        public ImageView thumbnail;
        public Button likeButton;
        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            thumbnail=(ImageView)view.findViewById(R.id.image);
            likes=(TextView)view.findViewById(R.id.likes);
            likeButton=(Button)view.findViewById(R.id.likebutton);


            //  year = (TextView) view.findViewById(R.id.year);


        }
    }


    public News_Adapter(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;

        // this.filteredList=albumList;
        //getFilter();
    }

    @Override
    public News_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nd_row, parent, false);
        return new News_Adapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final News_Adapter.MyViewHolder holder, final int position) {

        final News n = newsList.get(position);
        holder.title.setText(n.getTitle());
        holder.description.setText(n.getDescription());
        holder.likes.setText(String.valueOf(n.getLikes()));
        Picasso.with(mContext).load(n.getImage()).into(holder.thumbnail);
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p=position;
                Log.e("pos",String.valueOf(p));
                MainActivity m=new MainActivity();
                m.Like(n.getId(),n.getLikes());
            }
        });





    }



    @Override
    public int getItemCount() {
        return (newsList == null) ? 0 : newsList.size();

    }
}


