package weixin.popular.api;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import weixin.popular.bean.media.Media;
import weixin.popular.bean.media.MediaType;
import weixin.popular.client.LocalHttpClient;
import weixin.popular.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;


/**
 * Class
 * Created by wwx193433
 * 2019-12-09 17:45
 */
public class CheckAPI  extends BaseAPI{

    public static Media checkMedia(String accessToken, String url, MediaType mediaType){
        try{
            InputStream inputStream = new URL(url).openStream();
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/img_sec_check");
            byte[] data = null;
            data = StreamUtils.copyToByteArray(inputStream);
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addBinaryBody("media", data, ContentType.DEFAULT_BINARY, "temp." + MediaType.image.fileSuffix())
                    .addTextBody("access_token", API.accessToken(accessToken))
                    .addTextBody("type", mediaType.uploadType())
                    .build();
            httpPost.setEntity(reqEntity);
            Media media = LocalHttpClient.executeJsonResult(httpPost, Media.class);
            return media;
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
