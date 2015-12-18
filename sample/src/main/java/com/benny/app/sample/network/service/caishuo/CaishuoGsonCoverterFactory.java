package com.benny.app.sample.network.service.caishuo;

//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.ResponseBody;


//import okio.Buffer;
//import retrofit.Converter;

/**
 * Created by benny on 9/6/15.
 */
public class CaishuoGsonCoverterFactory /*implements Converter.Factory*/ {
/*    private final Gson gson;

    public static CaishuoGsonCoverterFactory create() {
        return create(new Gson());
    }

    public static CaishuoGsonCoverterFactory create(Gson gson) {
        return new CaishuoGsonCoverterFactory(gson);
    }

    private CaishuoGsonCoverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override public Converter<?> get(Type type) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CaishuoGsonConverter<>(gson, adapter);
    }


    static class CaishuoGsonConverter<T> implements Converter<T> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private static final Charset UTF_8 = Charset.forName("UTF-8");

        private final Gson gson;
        private final TypeAdapter<T> typeAdapter;

        CaishuoGsonConverter(Gson gson, TypeAdapter typeAdapter) {
            this.gson = gson;
            this.typeAdapter = typeAdapter;
        }

        @Override public T fromBody(ResponseBody body) throws IOException {
            Reader in = body.charStream();
            try {
                CaishuoEnveloped result = gson.fromJson(in, CaishuoEnveloped.class);
                if(result.isSuccess()) {
                    return typeAdapter.fromJson(result.data.toString());
                }

                if(result.error != null && result.error.code == 1003) {
                    //send access token invalid event
                    BusProvider.getInstance().post(new AccessTokenInvalidEvent());
                }
                throw new ServiceException(result.error.message, result.error.code);
            } finally {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }

        @Override public RequestBody toBody(T value) {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            try {
                typeAdapter.toJson(writer, value);
                writer.flush();
            } catch (IOException e) {
                throw new AssertionError(e); // Writing to Buffer does no I/O.
            }
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }*/
}


