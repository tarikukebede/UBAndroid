package et.com.act.unibillingandroid.Network;

import java.util.List;

import et.com.act.unibillingandroid.Network.Dto.FetchMeterListResponseDto;
import et.com.act.unibillingandroid.Network.Dto.MeterReadingDto;
import et.com.act.unibillingandroid.Network.Dto.GenericResponseDto;
import et.com.act.unibillingandroid.Network.Dto.SignInRequestDto;
import et.com.act.unibillingandroid.Network.Dto.SignInResponseDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UniBillingApi {

    @POST(Url.SING_IN)
    Call<SignInResponseDto> signIn(@Body SignInRequestDto signInRequestDto);

    @Headers({NetworkConstants.HTTP_HEADER_FORMAT})
    @GET(Url.FETCH_METERS)
    Call<FetchMeterListResponseDto> fetchMeters(
            @Header(NetworkConstants.HTTP_HEADER_AUTHORIZATION) String token,
            @Query(Param.READER_ID) String agentId);

    @Headers({NetworkConstants.HTTP_HEADER_FORMAT})
    @POST(Url.SUBMIT_METER_READINGS)
    Call<GenericResponseDto> submitMeterReadings(
            @Header(NetworkConstants.HTTP_HEADER_AUTHORIZATION) String auth,
            @Body List<MeterReadingDto> meterReadings);

    @GET(Url.CHANGE_PASSWORD)
    Call<GenericResponseDto> changePassword(
            @Query(Param.USERNAME) String username,
            @Query(Param.NEW_PASSWORD) String newPassword);
}
