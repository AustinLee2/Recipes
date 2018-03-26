package com.austinhlee.android.recipes;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Austin Lee on 3/14/2018.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Recipe> mRecipeList;
    private Context mContext;
    private LayoutInflater mInflater;

    RecipeListAdapter(Context context, List<Recipe> list){
        mContext = context;
        mRecipeList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recipe_item_layout, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Recipe currentRecipe = mRecipeList.get(position);
        RecipeViewHolder viewHolder = (RecipeViewHolder)holder;
        viewHolder.recipeNameTextview.setText(currentRecipe.getTitle());
        viewHolder.servingTextView.setText(Integer.toString(currentRecipe.getServings()));
        viewHolder.prepTextView.setText(currentRecipe.getPrepTime());
        Picasso.with(mContext).load(currentRecipe.getImage()).into(viewHolder.recipeImageView);
        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentRecipe.getUrl()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "Channel_ID")
                        .setSmallIcon(R.drawable.ic_link)
                        .setContentTitle(currentRecipe.getTitle())
                        .setContentIntent(pendingIntent)
                        .setContentText("The instruction for " + currentRecipe.getTitle() + " can be found here!")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("The instruction for " + currentRecipe.getTitle() + " can be found here!"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
                notificationManager.notify(2, mBuilder.build());
            }

        });
    }


    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {

        private TextView recipeNameTextview;
        private TextView servingTextView;
        private TextView prepTextView;
        private ImageView recipeImageView;
        private ImageButton imageButton;

        RecipeViewHolder(View itemView){
            super(itemView);
            recipeNameTextview = (TextView) itemView.findViewById(R.id.name_text_view);
            servingTextView = (TextView) itemView.findViewById(R.id.serving_text_view);
            prepTextView = (TextView) itemView.findViewById(R.id.prep_text_view);
            recipeImageView = (ImageView) itemView.findViewById(R.id.recipe_image_view);
            imageButton = (ImageButton) itemView.findViewById(R.id.notification_image_button);
        }
    }
}
