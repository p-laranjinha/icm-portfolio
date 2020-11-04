package ptua.retrofitsample.network;



import java.util.List;

import ptua.retrofitsample.model.RetroPhoto;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by praka on 12/24/2017.
 */

public interface GetDataService {

    @GET("/photos")
    Call<List<RetroPhoto>> getAllPhotos();
}
