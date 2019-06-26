package cn.exceptioncode.common.http;

import lombok.SneakyThrows;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class OkHttpUtil {


    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");


    private static OkHttpClient httpClient = new OkHttpClient();

    public static String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static String postWithRequestBody(String url, String json, Map<String, String> header) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .post(body);
        header.forEach((s, s2) -> reqBuilder.addHeader(s, s2));
        try (Response response = httpClient.newCall(reqBuilder.build()).execute()) {
            return response.body().string();
        }
    }

    public static String postWithForm(String url, Map<String, String> header, Map<String, String> form) throws IOException {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        form.forEach((k, v) ->
                formBodyBuilder.add(k, v)
        );
        Request.Builder reqBuilder = new Request.Builder()
                .url(url)
                .post(formBodyBuilder.build());
        header.forEach((s, s2) -> reqBuilder.addHeader(s, s2));
        try (Response response = httpClient.newCall(reqBuilder.build()).execute()) {
            return response.body().string();
        }
    }


    public static String getResWithQueryParam(String url, Map<String, String> header, Map<String, String> queryParam) throws IOException {
        boolean isFirst = true;
        for (String s : queryParam.keySet()) {
            String param = s + "=" + queryParam.get(s);
            if (isFirst) {
                url += "?";
            } else {
                url += "&";
            }
            url += param;
            isFirst = false;
        }
        Request.Builder reqBuilder = new Request.Builder()
                .url(url);
        header.forEach((s, s2) -> reqBuilder.addHeader(s, s2));

        try {
            Response response = httpClient.newCall(reqBuilder.build()).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @SneakyThrows
    public static void main(String[] args) {
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Cookie", "JSESSIONID=8DA5C060040EF7D1C93AC6E4B4825D43; pgv_pvi=7795611648; RK=tExYgZNANK; ptcz=b67c82875b968c80fe1903e6464d171591a99d9e47592efe5fa5d16f1b3e4f27; pgv_pvid=9388715142; eas_sid=o1D5G4f567U6g5F8w2X1k3b7c8; tvfe_boss_uuid=316bd7199cab1b18; o_cookie=425485346; ied_qq=o0425485346; _ga=GA1.2.1225764715.1555726905; pgv_info=pgvReferrer=&ssid=s8691211104; _qpsvr_localtk=0.7384854534932486; pgv_si=s2370873344; ptisp=ctc; sFrom=website; building002=view_union_info.alc; numofhero=8f91b23cb1e054bb51072c4b4ec17f97bc1535a39c0c2d9b; midas_openid=425485346; midas_openkey=@S7gVggElz; user_game_info_425485346=ddb17431f42d19f5421c9a9261fe8a8c036bb9c23922e0f3b8a0459aacd3b1ea695450a8f4feac49a3511d42d707267bba5909f1aeb31ffe04aacc66501d346861070c39deccd1c7ddbe29cdae78e6311648738cf666f212117511400d2912e058822ad96d1a9275fa4e75feeba505cbffa7eefeb789a2581f13ef774789e60a865a48c1cd61ac06f2bc848fd613db0699e994c70d6f8121151c689ebf6536e629e5abb873f6e48c17333ba2128f509a18742cfc206044bcb8b5fc476282672000a7274459a3018cc71906c8597c94c23cae17eb703e5494e9861fb2b509449f7c2c6aa3485dcbdf1de70aac55471262f82273eead7654485780695fb6cb5be839666180a6bf015f972f355c141219cc704fc16787f665e7d5c835c5d85784a84568444353919aa240ce806b44f31eec32d7ab26fdf8f6219a02409dd1f6fb087029150bf807844a5d964873118123432832ea37d07296d410f05a4ea58e1e24c7b9ecaf4e986df9fb744c440cd1626f7237a48e41962546cb9b47c3cd8f207589a2a923ceca7e24cae955b5144e002a906dd441289aaecc479a0a44ca97c2c727b617de9171e223a29e22bbbefa21d31a9238ba7b824face513bfcf2b32ceecf8cbe35d2ed22fbbde862983399e2fd8; ied_rf=--; ptui_loginuin=491933744; uin=o0491933744; skey=@1VtctVbYN; sl_login=491933744%7C61%2E140%2E244%2E144%7C1561397310%7C0%7C1%7C0%5F0%7C2%5F7%7C0%5F0%7C0%5F0%7CCDEEFF396887BE1861AC560EE1F4CF1F; user_requests=2d6fee8f457d8ad2a628b0609bf6417f8498d4f81f79fd663c48f9efea5703d8d33efdc62901ffe3eaa7b4573170f04890f14ccf94c7cb01cdd76ed4a32befd9e807110f690b1b27; first_login=78207aef32264c418455f8f59163392c; user_hero=\"1376303705437370/%E6%94%BB%E5%87%BB%EF%BC%8D%EF%BC%91/200/1/2/0/32005/0/m19#1376303777781437/%E6%94%BB%E5%87%BB%EF%BC%8D%EF%BC%92/200/1/2/0/32005/0/m20#1376304181571492/%E6%94%BB%E5%87%BB%EF%BC%8D%EF%BC%93/200/1/2/1/32005/0/m19#1376303758143932/%E9%98%B2%E5%BE%A1-1/127/2/2/1/32005/0/m19#1376303851589383/%E9%98%B2%E5%BE%A1-3/126/2/2/1/32005/0/m21#\"; user_info=\"%E2%99%82%E5%A4%A9%E6%B6%AF/%E5%A4%A9%E5%AD%97%E7%9B%9F/0/20556/6541/60371/23/%E8%AF%B8%E4%BE%AF%E7%8E%8B/491933744/1247324702873190/1/1\"; user_army=\"1/0/pudaobing/1#2/0/tieqiangbing/1#3/0/jinweijun/1#4/0/jinjiawushi/1#5/0/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/0/shenhuochongbing/2#9/10999/qingqibing/3#10/0/qishebing/3#11/0/zhongqibing/3#12/0/hubenqishi/3#19/0/fangshi/4#33/0/tongshenyuzhe/4#20/0/shensuanxingguan/4#21/0/bingyinyangjia/4#\"; building003=\"491933744,32005,120,building4\"; building007=\"list_trade.alc?cid=32005\"; user_game_info_491933744=5877dfccc3c6f571a74a4fea71bcc53baeed1235c505540dce6cba510e6f85d5320a557813018c27626d10d293972059be0fafe3753fce56918a327303d93c1e93096b39c719532846aaf55345d20468974fa8bfd67f8cb562126b355230d21e636044c7b81b16e09233bb358ef1247635d3d516e39231caa0156ff2abda470d776a3cac58ff470f0e2043dcfc14e12634924da137d91f1bd46d848fa5dd243f5b1c1aae313f93f5eedf193807919a62fc53f77134e333cd8a70acf634811daf81ec6169afae0bfbb2de5cb88f4f56f6a83b8fa85d2fba1ca8a12200450e552cd41867a5f6a2f71f2d3eeb26b277aed789913385f9c71e891a141bee1305f887946d294a3ff6e6d48b1a1e3e8aa60b50cb5d2ea805486ac0cd905e811cb7d871fac653fda902cb5bec896042e80bb18219dabb7d072dc0b225e2800274c568c873b1e924cf5b71dc33b0f33ca53f9d54e0c2148a66119f3c970a9b9d5382e71037f8df1742b87976e6e46557507ef804eb52376b0081b06521a58cf1988fda38a37089c2cf46b208da36bd06172878928736826216a2e3f7e0d4d7038631b88989870daaa1299fa3677b222756b0c74c4c257974b6fa02c8; onlinetime=\"2019-06-25 01:58:51\"");
        Map<String, String> form = new HashMap<>(10);
        form.put("marchSpeed", " 61.8");
        form.put("marchSpeedPercent", "100");
        form.put("resource1", "");
        form.put("resource2", "");
        form.put("resource3", "");
        form.put("resource4", "");
        form.put("resource5", "");
        form.put("marchType", "5");
        form.put("firstHeroId", "");
        form.put("secondHeroId", "");
        form.put("thirdHeroId", "");
        form.put("queryType", "1");
        form.put("0.45699247085102046", "");


        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        service.scheduleAtFixedRate(() -> {
            try {
                // 派遣到主城
                form.put("cid", "140183");
                // 兵种ids
                form.put("armyIds", "16");
                // 兵种 nums
                form.put("armyNums", "324143");
                                form.put("armyIds", "");
                form.put("armyNums", "");
                form.put("x", "-374");
                form.put("y", "278");
                form.put("myX", "-375");
                form.put("myY", "278");
                form.put("resource1", "");
                System.out.println("派遣到主城：" + OkHttpUtil.postWithForm("https://s8.sl.qq.com/s/send_army.alc", headers, form));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.MINUTES);


        service.scheduleAtFixedRate(() -> {
            try {
                form.put("cid", "32005");
                form.put("armyIds", "9,14,15,16,13,");
                form.put("armyNums", "0,0,0,324143,0,");
                form.put("resource1", "648286000");
                form.put("x", "-375");
                form.put("y", "278");
                form.put("myX", "-374");
                form.put("myY", "278");
                // 派遣到殖民1
                System.out.println("派遣到殖民1：" + OkHttpUtil.postWithForm("https://s8.sl.qq.com/s/send_army.alc", headers, form));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.MINUTES);


    }

    /**
     *
     * 派遣
     */
    static void send_army(Map<String,String> headers,
            String cid,String armyIds,String armyNums,
                          String x,String y,String myX,String myY,
                          String resource1,String resource2,String resource3,String resource4,String resource5) throws  IOException{
        Map<String, String> form = new HashMap<>(10);
        form.put("marchSpeed", " 61.8");
        form.put("marchSpeedPercent", "100");
        form.put("marchType", "5");
        form.put("firstHeroId", "");
        form.put("secondHeroId", "");
        form.put("thirdHeroId", "");
        form.put("queryType", "1");
        form.put("0.45699247085102046", "");
        form.put("cid", cid);
        form.put("armyIds", armyIds);
        form.put("armyNums", armyNums);
        form.put("resource1",resource1 );
        form.put("resource2", resource2);
        form.put("resource3", resource3);
        form.put("resource4", resource4);
        form.put("resource5", resource5);
        form.put("x", x);
        form.put("y", y);
        form.put("myX", myX);
        form.put("myY", myY);
        OkHttpUtil.postWithForm("https://s8.sl.qq.com/s/send_army.alc", headers, form);
    }

}
