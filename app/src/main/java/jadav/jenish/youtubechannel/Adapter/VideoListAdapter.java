package jadav.jenish.youtubechannel.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jadav.jenish.youtubechannel.Interface.onViewClickListner;
import jadav.jenish.youtubechannel.Models.Item;
import jadav.jenish.youtubechannel.Models.Medium;
import jadav.jenish.youtubechannel.Models.Snippet;
import jadav.jenish.youtubechannel.R;
import jadav.jenish.youtubechannel.Utils.Messages;
import jadav.jenish.youtubechannel.Utils.MyUtils;

/**
 * Created by Jenish on 29-11-2017.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    List<Item> itemList;
    onViewClickListner onViewClickListner;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public VideoListAdapter(List<Item> itemList, onViewClickListner onViewClickListner) {
        this.itemList = itemList;
        this.onViewClickListner = onViewClickListner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        ;
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try
        {
            Snippet snippet=itemList.get(position).getSnippet();
            holder.txt_title.setText(MyUtils.textToHtml(snippet.getTitle()));
            holder.txt_description.setText(snippet.getDescription());

            Medium medium=snippet.getThumbnails().getMedium();
            Picasso.with(holder.itemView.getContext())
                    //.load(snippet.getThumbnails().get_default().getUrl())
                    .load(medium.getUrl())
                    .resize(120,90)
                    .placeholder(R.drawable.ic_youtube)
                    .error(R.drawable.ic_youtube)
                    .into(holder.img_thumbnail);
        }catch (Exception e)
        {
            Messages.log(e.toString());
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_title, txt_description;
        ImageView img_thumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description);
            img_thumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onViewClickListner.onClickListner(view, this.getLayoutPosition());
        }
    }
}
