package the.pixel.factory.inshorts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryListenOptions;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import the.pixel.factory.inshorts.adapter.News_Adapter;
import the.pixel.factory.inshorts.models.News;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private News_Adapter adapter;
    private List<News> newsList;
    FirebaseFirestore db;
    private String id[];
    static int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            newsList= new ArrayList<>();
            recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
            adapter=new News_Adapter(getApplicationContext(),newsList);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            SnapHelper snapHelper=new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
            ItemTouchHelper.SimpleCallback simpleItemTouchCallback= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    int i=viewHolder.getAdapterPosition();
                    String url=newsList.get(i).getLink();
                    Intent i1= new Intent(MainActivity.this, News_Detail.class);
                    i1.putExtra("url", url);
                    startActivity(i1);


                }
            };

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
            RealtimeUpdate();


    }


    public  void Like(String id,int likes){

        //String i2=id[p];
        //Log.e("id", i2);
        DocumentSnapshot d;
        db = FirebaseFirestore.getInstance();
        DocumentReference documentReference=db.collection("sports").document(id);
      documentReference.update("likes",likes+1)
      .addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {
              Log.d("message", "DocumentSnapshot successfully updated!");

          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Log.w("message", "Error updating document", e);

          }
      });

    }

    public void  RealtimeUpdate(){
         db = FirebaseFirestore.getInstance();
        CollectionReference reference=db.collection("sports");

        reference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshot, FirebaseFirestoreException e) {
                //DocumentSnapshot d= (DocumentSnapshot) documentSnapshot.getDocuments();
                if(e!=null){
                    Log.w("Error", "Listen failed.", e);
                    return;

                }
                id=new String[documentSnapshot.size()];
                newsList.clear();
                for(DocumentSnapshot document:documentSnapshot){


                    Log.d("data",document.getId()+"=>"+document.getData());
                    /*id[i]=document.getId();
                    String i1=id[0];
                    Log.e("id", i1);
                    i++;*/
                    News n=document.toObject(News.class);
                    n.setId(document.getId());
                            /*News n= new News();
                            n.setTitle(document.getString("title"));
                            n.setDescription(document.getString("description"));
                            n.setImage(document.getString("image"));
                            n.setUrl(document.getString("link"));*/

                    newsList.add(n);
                    adapter.notifyDataSetChanged();



                }



            }


        });

    }

}
