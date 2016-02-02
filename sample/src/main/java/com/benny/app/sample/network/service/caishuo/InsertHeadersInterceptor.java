package com.benny.app.sample.network.service.caishuo;

//import com.squareup.okhttp.Interceptor;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;

/**
 * Created by benny on 9/6/15.
 */
public class InsertHeadersInterceptor /*implements Interceptor*/ {
    /*private Context context;
    private String apiKey;

    public InsertHeadersInterceptor(Context context, String apiKey) {
        this.context = context;
        this.apiKey = apiKey;
    }

    @Override public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.addHeader("X-Client-Key", apiKey);
        requestBuilder.addHeader("X-SN-Code", SystemUtils.getDeviceId(context));
        requestBuilder.addHeader("X-Client-Version", SystemUtils.getApplicationVersion(context));
        if(ApplicationContext.getInstance().hasLogin()) {
            requestBuilder.addHeader("Authorization", ApplicationContext.getInstance().getAccessToken()).build();
        }
        Response response = chain.proceed(requestBuilder.build());
        return response;
    }*/
}
