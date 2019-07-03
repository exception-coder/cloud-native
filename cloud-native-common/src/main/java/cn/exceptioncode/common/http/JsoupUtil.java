package cn.exceptioncode.common.http;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JsoupUtil {

    static Map<String, String> userIdMap = new HashMap<>(10);

    static {
        userIdMap.put("丿★阿英ㄨ", "455969164");
        userIdMap.put("丿凉丶◇风","425485346");
    }

    @SneakyThrows
    public static void main(String[] args) {

        Map<String, String> headers = new HashMap<>(1);
        String Cookie = "JSESSIONID=8DA5C060040EF7D1C93AC6E4B4825D43; pgv_pvi=7795611648; RK=tExYgZNANK; ptcz=b67c82875b968c80fe1903e6464d171591a99d9e47592efe5fa5d16f1b3e4f27; pgv_pvid=9388715142; eas_sid=o1D5G4f567U6g5F8w2X1k3b7c8; tvfe_boss_uuid=316bd7199cab1b18; o_cookie=425485346; ied_qq=o0425485346; _ga=GA1.2.1225764715.1555726905; pgv_info=pgvReferrer=&ssid=s8691211104; _qpsvr_localtk=0.7384854534932486; pgv_si=s2370873344; ptisp=ctc; sFrom=website; building002=view_union_info.alc; numofhero=8f91b23cb1e054bb51072c4b4ec17f97bc1535a39c0c2d9b; midas_openid=425485346; midas_openkey=@S7gVggElz; user_game_info_425485346=ddb17431f42d19f5421c9a9261fe8a8c036bb9c23922e0f3b8a0459aacd3b1ea695450a8f4feac49a3511d42d707267bba5909f1aeb31ffe04aacc66501d346861070c39deccd1c7ddbe29cdae78e6311648738cf666f212117511400d2912e058822ad96d1a9275fa4e75feeba505cbffa7eefeb789a2581f13ef774789e60a865a48c1cd61ac06f2bc848fd613db0699e994c70d6f8121151c689ebf6536e629e5abb873f6e48c17333ba2128f509a18742cfc206044bcb8b5fc476282672000a7274459a3018cc71906c8597c94c23cae17eb703e5494e9861fb2b509449f7c2c6aa3485dcbdf1de70aac55471262f82273eead7654485780695fb6cb5be839666180a6bf015f972f355c141219cc704fc16787f665e7d5c835c5d85784a84568444353919aa240ce806b44f31eec32d7ab26fdf8f6219a02409dd1f6fb087029150bf807844a5d964873118123432832ea37d07296d410f05a4ea58e1e24c7b9ecaf4e986df9fb744c440cd1626f7237a48e41962546cb9b47c3cd8f207589a2a923ceca7e24cae955b5144e002a906dd441289aaecc479a0a44ca97c2c727b617de9171e223a29e22bbbefa21d31a9238ba7b824face513bfcf2b32ceecf8cbe35d2ed22fbbde862983399e2fd8; ied_rf=--; ptui_loginuin=491933744; uin=o0491933744; skey=@1VtctVbYN; sl_login=491933744%7C61%2E140%2E244%2E144%7C1561397310%7C0%7C1%7C0%5F0%7C2%5F7%7C0%5F0%7C0%5F0%7CCDEEFF396887BE1861AC560EE1F4CF1F; user_requests=2d6fee8f457d8ad2a628b0609bf6417f8498d4f81f79fd663c48f9efea5703d8d33efdc62901ffe3eaa7b4573170f04890f14ccf94c7cb01cdd76ed4a32befd9e807110f690b1b27; first_login=78207aef32264c418455f8f59163392c; user_hero=\"1376303705437370/%E6%94%BB%E5%87%BB%EF%BC%8D%EF%BC%91/200/1/2/0/32005/0/m19#1376303777781437/%E6%94%BB%E5%87%BB%EF%BC%8D%EF%BC%92/200/1/2/0/32005/0/m20#1376304181571492/%E6%94%BB%E5%87%BB%EF%BC%8D%EF%BC%93/200/1/2/1/32005/0/m19#1376303758143932/%E9%98%B2%E5%BE%A1-1/127/2/2/1/32005/0/m19#1376303851589383/%E9%98%B2%E5%BE%A1-3/126/2/2/1/32005/0/m21#\"; user_info=\"%E2%99%82%E5%A4%A9%E6%B6%AF/%E5%A4%A9%E5%AD%97%E7%9B%9F/0/20556/6541/60371/23/%E8%AF%B8%E4%BE%AF%E7%8E%8B/491933744/1247324702873190/1/1\"; user_army=\"1/0/pudaobing/1#2/0/tieqiangbing/1#3/0/jinweijun/1#4/0/jinjiawushi/1#5/0/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/0/shenhuochongbing/2#9/10999/qingqibing/3#10/0/qishebing/3#11/0/zhongqibing/3#12/0/hubenqishi/3#19/0/fangshi/4#33/0/tongshenyuzhe/4#20/0/shensuanxingguan/4#21/0/bingyinyangjia/4#\"; building003=\"491933744,32005,120,building4\"; building007=\"list_trade.alc?cid=32005\"; user_game_info_491933744=5877dfccc3c6f571a74a4fea71bcc53baeed1235c505540dce6cba510e6f85d5320a557813018c27626d10d293972059be0fafe3753fce56918a327303d93c1e93096b39c719532846aaf55345d20468974fa8bfd67f8cb562126b355230d21e636044c7b81b16e09233bb358ef1247635d3d516e39231caa0156ff2abda470d776a3cac58ff470f0e2043dcfc14e12634924da137d91f1bd46d848fa5dd243f5b1c1aae313f93f5eedf193807919a62fc53f77134e333cd8a70acf634811daf81ec6169afae0bfbb2de5cb88f4f56f6a83b8fa85d2fba1ca8a12200450e552cd41867a5f6a2f71f2d3eeb26b277aed789913385f9c71e891a141bee1305f887946d294a3ff6e6d48b1a1e3e8aa60b50cb5d2ea805486ac0cd905e811cb7d871fac653fda902cb5bec896042e80bb18219dabb7d072dc0b225e2800274c568c873b1e924cf5b71dc33b0f33ca53f9d54e0c2148a66119f3c970a9b9d5382e71037f8df1742b87976e6e46557507ef804eb52376b0081b06521a58cf1988fda38a37089c2cf46b208da36bd06172878928736826216a2e3f7e0d4d7038631b88989870daaa1299fa3677b222756b0c74c4c257974b6fa02c8; onlinetime=\"2019-06-25 01:58:51\"";
        Cookie = "JSESSIONID=60D40DBF31E157B68AEBED7045468F12; pgv_pvi=7795611648; RK=tExYgZNANK; ptcz=b67c82875b968c80fe1903e6464d171591a99d9e47592efe5fa5d16f1b3e4f27; pgv_pvid=9388715142; eas_sid=o1D5G4f567U6g5F8w2X1k3b7c8; tvfe_boss_uuid=316bd7199cab1b18; o_cookie=425485346; ied_qq=o0425485346; _ga=GA1.2.1225764715.1555726905; ptui_loginuin=630516784; ied_rf=--; pgv_info=pgvReferrer=&ssid=s3148599668; pgv_si=s7496012800; _qpsvr_localtk=0.341832795538358; ptisp=ctc; uin=o0425485346; skey=@Mhky9IuOP; IED_LOG_INFO2=userUin%3D425485346%26nickName%3D%2525E5%252587%2525AF%26userLoginTime%3D1562084365; sl_login=425485346%7C61%2E140%2E244%2E53%7C1562084368%7C0%7C1%7C2%5F8%7C0%5F0%7C1%5F5%7C0%5F0%7CD39502DE6806D998326BC4378F873788; sFrom=website; user_requests=3ea78335a3ced04cd209f93d7ecf69bf204810425a1319759ff42cfe86ab0e86f70a865198999c88405ad7f7372674cb9694c601c654f08a165534f12d1c233cb7fef657ec3d8b42; first_login=f8c29e64a1d51f784d25d5809880a15a; onlinetime=\"2019-07-03 00:19:39\"; user_hero=\"1302095433049915/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A2/189/1/2/1/215043799/1/g9#1302016221118280/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A1/177/1/2/1/215043799/1/g12#1289924374531220/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A0/153/1/2/1/215043799/1/m6#1302093755321980/%E9%98%B2%E5%BE%A1%E4%B8%80/129/2/1/1/6/0/m2#1315003310221209/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A4/120/1/1/1/6/0/m7#\"; user_game_info_425485346=2978ac1e66d370b8bcfc0375fbcd07ec944e8d853aada3a62272d4d7ce2146b6dd8927c17ffcebceb44aeef69bf44a06c1be3cdeb97be8926a29051bb14fe153b80b92f0927e5dc53a1bafea67a741a17c840e1028b6f705dc8a386184a10a417e9d48d46431eb542714bad525ad07a0eac86bb07a40c2ea4e3092a3404d8cda9a96b746864d75cf55aafdb719c1f50ed72093b3579b024931c43fba73c4f87595109b1483e8b6d0531e2aac8c1c76ec86eb85916137eeb2a7f7a3a1c2c18953d07ddf1f42b7c4baa453307003fea04d65d6ae576dce29e23f7445196d9f58e7e3aa10110959e1af5fa4d1721f3b31fbc503e700432f96ec973203d320a3008ce5a7e7e499a98e5c19ab58a9498607f37703c9fd148ab37a7432107abfc5fc83a1dedb484a808a8ab5f048ea92f86d2512b671d44c1790c84df8d503ae5c2e3d9205a6d73ccd21da6532b5a2084b576769397383c91b2d144e6a69affeb056ae12409b674e030dbc65fb9a93da9f9ce2fad04199bc26199f7a991056bd4f0113aadc8c67b8320f5aebfc22de32d83c683474ca1f09f7145367ffce7de1a250a924e5a8dcfe4ac618a5e0a53bee309defb3f3b6b9d307843edfa903f204089107c437657bca119440caffbac5f9905f8b; user_info=\"%E4%B8%BF%E5%87%89%E4%B8%B6%E2%97%87%E9%A3%8E/%E5%B9%BF%E5%B7%9E%E8%81%94%E7%9B%9F/27373267/2/4317/66482/23/%E8%AF%B8%E4%BE%AF%E7%8E%8B/425485346/1244254858510599/1/1\"; user_army=\"1/9528491/pudaobing/1#2/0/tieqiangbing/1#3/288000/jinweijun/1#4/6500692/jinjiawushi/1#5/13456260/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/4661876/shenhuochongbing/2#9/8548101/qingqibing/3#10/0/qishebing/3#11/440216/zhongqibing/3#12/7295567/hubenqishi/3#19/4375/fangshi/4#33/493432/tongshenyuzhe/4#20/441626/shensuanxingguan/4#21/80467/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"425485346,767076,120,building10\"; building007=\"list_trade.alc?cid=767076\"";
        // 天涯
        String cid = "140183";
        // 凉风
        cid = "767076";
        headers.put("Cookie", Cookie);
//        paiQianZiYuan(headers,"425485346","-379","284",cid);
        boolean continue_ = true;
        if(continue_){
            /**
             *
             * 长安 1
             * 敦煌 2
             * 楼兰 3
             * 葱岭 5
             * 高附 6
             */
            List<String> changAn = new ArrayList<>(10);
            List<String> dunHuang = new ArrayList<>(10);
            List<String> louLan = new ArrayList<>(10);
            List<String> congLing = new ArrayList<>(10);
            List<String> gaoFu = new ArrayList<>(10);






            Map<String, String> map = new HashMap<>(10);
            map.put("cid", cid);
            map.put("mid", "19854");
            map.put("tt", "629768586.7605");
            String resBody = OkHttpUtil.getResWithQueryParam("https://s8.sl.qq.com/s/view_hero_list.alc", headers, map);
            Document doc = Jsoup.parse(resBody);
            Elements elements = doc.getElementsByClass("table_list_hero");
            elements.forEach(element -> {

                System.out.println("======================================================================================");

                Elements elements1 = element.getElementsByAttributeValue("value", "查看");
                elements1.forEach(element1 -> {
                            String onclickStr = element1.attr("onclick");
                            System.out.println(onclickStr);
                            String heroId = onclickStr.substring(onclickStr.indexOf("heroId=") + "heroId=".length(), onclickStr.length() - 1);
                            System.out.println(heroId);

                            element.getElementsByTag("strong").forEach(element2 -> {
                                        String ownText = element2.ownText();
                                        if (ownText != null & ownText.contains("等级")) {
                                            ownText = ownText.replace("等级：", "");
                                            int heroLevel = Integer.valueOf(ownText);
                                            if (heroLevel < 22) {
                                                // 长安
                                                changAn.add(heroId);
                                            }
                                            if (heroLevel >= 22 && heroLevel < 36) {
                                                // 敦煌
                                                dunHuang.add(heroId);
                                            }
                                            if (heroLevel >= 36 && heroLevel < 48) {
                                                // 楼兰
                                                louLan.add(heroId);
                                            }
                                            if (heroLevel >= 48 && heroLevel < 58) {
                                                // 葱岭
                                                congLing.add(heroId);
                                            }
                                            if (heroLevel >= 60) {
                                                // 高附
                                                gaoFu.add(heroId);
                                            }


                                        }
                                    }
                            );

                        }
                );

            });

            // 派遣英雄 英雄冒险
            Map<String, String> form = new HashMap<>(4);
            form.put("cid", cid);
            form.put("0.1259743080763418", "");
            changAn.forEach(s -> {
                form.put("endCid", "1");
                send_hero(s, form, headers);
                create_mission_room(s, headers, "10014", "1104", "1");
            });


            dunHuang.forEach(s -> {
                form.put("endCid", "2");
                send_hero(s, form, headers);
                String res = create_mission_room(s, headers, "11021", "1116", "2");
                // TODO: 2019/6/27  冒险失败 尝试 冒险 漠北围歼战
                if (res == null || !res.contains("1")) {
                    // 清理丝绸之路 冒险失败等级不足 尝试冒险清理丝绸之路
                    System.out.println("天下无双霸王之证③ 冒险失败等级不足 尝试冒险漠北围歼战 res：" + res);
                    create_mission_room(s, headers, "11003", "1112", "2");
                }
            });


            louLan.forEach(s -> {
                form.put("endCid", "3");
                send_hero(s, form, headers);
                String res = create_mission_room(s, headers, "12051", "1206", "3");
                if (res == null || !res.contains("1")) {
                    // 清理丝绸之路 冒险失败等级不足 尝试冒险清理丝绸之路
                    System.out.println("清理丝绸之路 冒险失败等级不足 尝试冒险清理丝绸之路 res：" + res);
                    create_mission_room(s, headers, "12041", "1204", "3");
                }

            });


            congLing.forEach(s -> {
                form.put("endCid", "5");
                send_hero(s, form, headers);
                String res = create_mission_room(s, headers, "14031", "1403", "5");
                if (res == null || !res.contains("1")) {
                    // 天山雪莲 冒险失败等级不足 尝试冒险穆天子的扳指
                    System.out.println("天山雪莲 冒险失败等级不足 尝试冒险穆天子的扳指 res：" + res);
                    create_mission_room(s, headers, "14021", "1402", "5");
                }
            });

            gaoFu.forEach(s -> {
                form.put("endCid", "6");
                send_hero(s, form, headers);
                create_mission_room(s, headers, "15104", "1504", "6");
            });
        }


    }

    /**
     * 获取用户信息
     */
    static List<String> view_user_info(Map<String, String> headers, String userId) throws IOException {
        List<String> coordinates = new ArrayList<>(10);
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("tt", "502853132.0632");
        queryParam.put("userId", userId);
        String resBody = OkHttpUtil.getResWithQueryParam("https://s8.sl.qq.com/s/view_user_info.alc", headers, queryParam);
        Document document = Jsoup.parse(resBody);
        Elements elements = document.getElementsByClass("g_tab");
        Element element = elements.get(1);
        elements = element.getElementsByTag("tr");
        boolean isFirst = true;
        List<Element> trs = new ArrayList<>(10);
        for (Element element1 : elements) {
            if (!isFirst) {
                trs.add(element1);
            }
            isFirst = false;
        }
        for (Element tr : trs) {
            Elements elements1 = tr.getElementsByTag("td");
            Element element1 = elements1.get(1);
            coordinates.add(element1.ownText());
        }
        return  coordinates;
    }

    /**
     * 派遣英雄
     *
     * @param s
     * @param form
     * @param headers
     */
    static void send_hero(String s, Map<String, String> form, Map<String, String> headers) {
        form.put("heroId", s);
        try {
            /**
             *
             * 冒险城派遣类型为 4
             * 非冒险城派遣类型为 1
             */
            form.put("sendHeroType", "4");
            String res = OkHttpUtil.postWithForm("https://s8.sl.qq.com/s/send_hero.alc", headers, form);
            if (res == null || !res.contains("1")) {
                form.put("sendHeroType", "1");
                res = OkHttpUtil.postWithForm("https://s8.sl.qq.com/s/send_hero.alc", headers, form);
            }
            System.out.println("派遣英雄：" + res);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 英雄执行冒险
     */
    static String create_mission_room(String s, Map<String, String> headers, String routeId, String missionId, String cityId) {
        Map<String, String> form = new HashMap<>(10);
        form.put("heroId", s);
        form.put("routeId", routeId);
        form.put("missionId", missionId);
        form.put("cityId", cityId);
        form.put("roomName", "%E6%96%B0%E4%BB%BB%E5%8A%A1");
        form.put(" 0.08211898231370773", "");
        form.put("heroVocation", "");
        form.put("password", "");
        try {
            String res = OkHttpUtil.postWithForm("https://s8.sl.qq.com/s/create_mission_room.alc", headers, form);
            System.out.println("英雄执行冒险：" + res);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     *
     * 派遣军队
     *
     * @return
     */
    static  void send_army(Map<String,String> headers,String myX,String myY,String x,String y,String marchType,
                         String armyIds,String armyNums,String cid,String resource)throws IOException{
        Map<String, String> form = new HashMap<>(10);
        form.put("marchSpeed", " 61.8");
        form.put("marchSpeedPercent", "100");
  if(StringUtils.isEmpty(resource)){
      form.put("resource1", "");
      form.put("resource2", "");
      form.put("resource3", "");
      form.put("resource4", "");
      form.put("resource5", "");
  }else {
      form.put("resource1", resource);
      form.put("resource2", resource);
      form.put("resource3", resource);
      form.put("resource4", resource);
      form.put("resource5", resource);
  }
        form.put("firstHeroId", "");
        form.put("secondHeroId", "");
        form.put("thirdHeroId", "");
        form.put("queryType", "1");
        form.put("0.45699247085102046", "");

        if(marchType!=null){
            /**
             *
             * 5 派遣
             * 2 侦查
             */
            form.put("marchType", marchType);
        }
        form.put("x",x);
        form.put("y",y);
        form.put("myX",myX);
        form.put("myY",myY);
        form.put("cid", cid);
        form.put("armyIds", armyIds);
        form.put("armyNums", armyNums);
       String resBody =  OkHttpUtil.postWithForm("https://s8.sl.qq.com/s/send_army.alc", headers, form);
        System.out.println(resBody);
       log.info("{} 坐标 ({},{})，响应信息：{}",marchType,x,y,resBody);
    }

    static void send_army_zc(Map<String,String> headers,String myX,String myY,String x,String y,String armyNums,String cid)throws IOException {
        send_army(headers, myX, myY, x, y,"2","15",armyNums,cid,null);
    }

    static void send_army_paiqian(Map<String,String> headers,String myX,String myY,String x,String y,String armyNums,String cid)throws IOException {
        send_army(headers, myX, myY, x, y,"5","16",armyNums,cid,"50000000");
    }

    /**
     *
     * 一键侦查傻子
     *
     * @param headers
     * @param userId
     * @param myX
     * @param myY
     * @throws IOException
     */
    static void zhenChaUser(Map<String,String> headers,String userId,String myX,String myY,String cid)throws IOException{
        List<String> coordinates = view_user_info(headers,userId);
        coordinates.forEach(str->{
            try{
                str = StringUtils.removeStart(str,"（");
                str = StringUtils.removeEnd(str,"）");
                String[] strs = str.split(",");
                send_army_zc(headers,myX,myY,strs[0],strs[1],"50000",cid);
                Thread.sleep(1000*6);
            }catch (IOException| InterruptedException e){

            }
        });
    }

    static void paiQianZiYuan(Map<String,String> headers,String userId,String myX,String myY,String cid)throws IOException{
        List<String> coordinates = view_user_info(headers,userId);
        coordinates.forEach(str->{
            try{
                str = StringUtils.removeStart(str,"（");
                str = StringUtils.removeEnd(str,"）");
                String[] strs = str.split(",");
                send_army_paiqian(headers,myX,myY,strs[0],strs[1],"125000",cid);
                Thread.sleep(1000*6);
            }catch (IOException| InterruptedException e){

            }
        });
    }

}
