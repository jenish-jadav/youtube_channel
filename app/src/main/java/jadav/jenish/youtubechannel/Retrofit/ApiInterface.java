package jadav.jenish.youtubechannel.Retrofit;

import java.util.Map;

import jadav.jenish.youtubechannel.Models.VideoListResponse;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface ApiInterface {
    @GET(MyURL.SEARCH_VIDEO)
    Call<VideoListResponse> getVideoList(@QueryMap Map<String, String> data);
}
