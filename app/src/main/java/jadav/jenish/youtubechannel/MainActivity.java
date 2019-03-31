package jadav.jenish.youtubechannel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jadav.jenish.youtubechannel.Adapter.VideoListAdapter;
import jadav.jenish.youtubechannel.Interface.onViewClickListner;
import jadav.jenish.youtubechannel.Models.Item;
import jadav.jenish.youtubechannel.Models.VideoListResponse;
import jadav.jenish.youtubechannel.Retrofit.ApiClient;
import jadav.jenish.youtubechannel.Retrofit.ApiInterface;
import jadav.jenish.youtubechannel.Utils.Constants;
import jadav.jenish.youtubechannel.Utils.Messages;
import jadav.jenish.youtubechannel.Utils.MyDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    MyDialog dialog;
    RecyclerView recyclerView;
    List<Item> itemList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setNoDataView(true, "No Video Found");
        getYoutubeVideoList();
    }

    private void init() {
        dialog = new MyDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    private void setNoDataView(boolean flag, String errorMsg) {
        try {
            findViewById(R.id.rl_no_data).setVisibility(!flag ? View.GONE : View.VISIBLE);
            recyclerView.setVisibility(flag ? View.GONE : View.VISIBLE);
            ((TextView) findViewById(R.id.txt_msg)).setText(errorMsg);
        } catch (Exception e) {
            Messages.log(e.toString());
        }
    }

    private void getYoutubeVideoList() {
        try {
            dialog.show();
            Map<String, String> data = new HashMap<>();
            data.put("part", "snippet");
            data.put("channelId", Constants.CHANNEL_ID);
            data.put("key", Constants.API_KEY);
            data.put("maxResults", "50");
            data.put("order", "date");

            dialog.setMessage("Finding videos...");
            Call<VideoListResponse> call = ApiClient.getClient(this).create(ApiInterface.class).getVideoList(data);
            call.enqueue(new Callback<VideoListResponse>() {
                @Override
                public void onResponse(Call<VideoListResponse> call, Response<VideoListResponse> response) {
                    dialog.close();
                    if (!response.isSuccessful()) {
                        setNoDataView(true, response.message());
                        return;
                    }
                    VideoListResponse videoListResponse = response.body();
                    itemList = videoListResponse.getItems();
                    initializeAdapter();
                }

                @Override
                public void onFailure(Call<VideoListResponse> call, Throwable t) {
                    dialog.close();
                    setNoDataView(true, "E: " + t.getLocalizedMessage());
                }
            });
        } catch (Exception e) {
            Messages.log(e.toString());
        }
    }

    private void initializeAdapter() {
        try {
            if (itemList == null)
                itemList = new ArrayList<>();
            VideoListAdapter adapter = new VideoListAdapter(itemList, listner);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            setNoDataView(adapter.getItemCount() == 0, "No Video Found");
        } catch (Exception e) {
            Messages.log(e.toString());
        }
    }


    onViewClickListner listner = new onViewClickListner() {
        @Override
        public void onClickListner(View view, int position) {
            playVideo(itemList.get(position).getId().getVideoId());
        }
    };

    private void playVideo(String videoId) {
        Intent intent = new Intent(this, MyPlayerActivity.class);
        intent.putExtra("videoid", videoId);
        startActivity(intent);
    }
}
