package et.com.act.unibillingandroid.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    public static Retrofit get(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(NetworkConstants.HTTP_TIMEOUT, TimeUnit.MINUTES)
                .build();

        return new Retrofit.Builder()
                .baseUrl(Url.MOLALE)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
