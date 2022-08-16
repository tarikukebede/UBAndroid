package et.com.act.unibillingandroid.Network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import et.com.act.unibillingandroid.Activities.MainActivity;
import et.com.act.unibillingandroid.Dto.State;
import et.com.act.unibillingandroid.Interfaces.NetworkRequestListener;
import et.com.act.unibillingandroid.Models.Auth;
import et.com.act.unibillingandroid.Network.Dto.FetchMeterListResponseDto;
import et.com.act.unibillingandroid.Network.Dto.MeterReadingDto;
import et.com.act.unibillingandroid.Network.Dto.GenericResponseDto;
import et.com.act.unibillingandroid.Network.Dto.SignInRequestDto;
import et.com.act.unibillingandroid.Network.Dto.SignInResponseDto;
import et.com.act.unibillingandroid.Util.Constants;
import et.com.act.unibillingandroid.Util.Mapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestHandler {
    private static final String TAG = "RequestHandler";


    public static void singIn(NetworkRequestListener listener, SignInRequestDto signInRequestDto){

        HttpClient.get().create(UniBillingApi.class)
                .signIn(signInRequestDto)
                .enqueue(new Callback<SignInResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<SignInResponseDto> call, @NonNull Response<SignInResponseDto> response) {
                        if(response.isSuccessful() && !(response.body() == null) ){
                            Auth auth = Mapper.mapToMeter(response.body());
                            listener.OnNetworkRequestCompleteListener(new State(Url.SING_IN, Constants.REQUEST_SUCCESS, auth));
                        }else{
                            listener.OnNetworkRequestCompleteListener(new State(Url.SING_IN, Constants.REQUEST_FAILURE, null));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SignInResponseDto> call,@NonNull Throwable t) {
                            t.printStackTrace();
                            Log.d(TAG, "onFailure: " + t.getMessage());
                            listener.OnNetworkRequestCompleteListener(new State(Url.SING_IN, Constants.REQUEST_FAILURE, t.getMessage()));
                    }
                });
    }


    public static void importMeters(MainActivity activity, Auth auth, NetworkRequestListener listener){

         HttpClient.get().create(UniBillingApi.class)
                 .fetchMeters(NetworkConstants.ACCESS_TOKEN(auth), auth.getPartyId())
                 .enqueue(new Callback<FetchMeterListResponseDto>() {
                     @Override
                     public void onResponse(@NonNull Call<FetchMeterListResponseDto> call, @NonNull Response<FetchMeterListResponseDto> response) {
                         if(response.isSuccessful() && !(response.body() == null)){
                             Log.d(TAG, "importMeters onResponse: meters " + response.body().getMeterLocationDtos().size());
                             Log.d(TAG, "importMeters onResponse:" + new GsonBuilder().create().toJson(response.body()));
                             listener.OnNetworkRequestCompleteListener(new State(Url.FETCH_METERS, Constants.REQUEST_SUCCESS, response.body()));
                         }else{
                             listener.OnNetworkRequestCompleteListener(new State(Url.FETCH_METERS, Constants.REQUEST_FAILURE, null));
                             Log.d(TAG, "onResponse: response is null");
                         }
                     }

                     @Override
                     public void onFailure(@NonNull Call<FetchMeterListResponseDto> call, Throwable t) {
                            listener.OnNetworkRequestCompleteListener(new State(Url.FETCH_METERS, Constants.REQUEST_FAILURE, t.getMessage()));
                         Log.d(TAG, "importMeters onFailure: request failed " + t.getMessage());
                     }
                 });
    }


    public static void exportReading(Auth auth, List<MeterReadingDto> meterReadings, NetworkRequestListener listener){
        Log.d(TAG, "exportReading: =====>" + new GsonBuilder().create().toJson(meterReadings));
        HttpClient.get().create(UniBillingApi.class)
                .submitMeterReadings(NetworkConstants.ACCESS_TOKEN(auth), meterReadings)
                .enqueue(new Callback<GenericResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<GenericResponseDto> call, @NonNull Response<GenericResponseDto> response) {
                        if(response.isSuccessful() && !(response.body() == null)){
                            Log.d(TAG, "exportReading onResponse:" + new GsonBuilder().create().toJson(response.body()));
                            listener.OnNetworkRequestCompleteListener(new State(Url.SUBMIT_METER_READINGS, Constants.REQUEST_SUCCESS, meterReadings));
                        }else{
                            listener.OnNetworkRequestCompleteListener(new State(Url.SUBMIT_METER_READINGS, Constants.REQUEST_FAILURE, null));
                            Log.d(TAG, "exportReading onResponse: response is null");
                            Log.d(TAG, "exportReading onResponse: " + new GsonBuilder().create().toJson(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<GenericResponseDto> call, @NonNull Throwable t) {
                        Log.d(TAG, "exportReading onFailure: request failed " + t.getMessage());
                        listener.OnNetworkRequestCompleteListener(new State(Url.SUBMIT_METER_READINGS, Constants.REQUEST_FAILURE, t.getMessage()));
                    }
                });
    }


    public static void changePassword(String username, String newPassword, NetworkRequestListener listener){
        HttpClient.get().create(UniBillingApi.class)
                .changePassword(username, newPassword)
                .enqueue(new Callback<GenericResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<GenericResponseDto> call, @NonNull Response<GenericResponseDto> response) {
                        if(response.isSuccessful() && !(response.body() == null)){
                            Log.d(TAG, "changePassword onResponse:" + new GsonBuilder().create().toJson(response.body()));
                            listener.OnNetworkRequestCompleteListener(new State(Url.CHANGE_PASSWORD, Constants.REQUEST_SUCCESS, response.body()));
                        }else{
                            listener.OnNetworkRequestCompleteListener(new State(Url.CHANGE_PASSWORD, Constants.REQUEST_FAILURE, null));
                            Log.d(TAG, "changePassword exportReading onResponse: response is null");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull  Call<GenericResponseDto> call,@NonNull Throwable t) {
                        listener.OnNetworkRequestCompleteListener(new State(Url.CHANGE_PASSWORD, Constants.REQUEST_FAILURE, t.getMessage()));
                        Log.d(TAG, "changePassword onFailure: request failed " + t.getMessage());
                    }
                });

    }
}
