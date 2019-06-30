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
        Cookie = "JSESSIONID=2A1525532391F46B1E5E9D0F090BF42B; pgv_pvi=7795611648; RK=tExYgZNANK; ptcz=b67c82875b968c80fe1903e6464d171591a99d9e47592efe5fa5d16f1b3e4f27; pgv_pvid=9388715142; eas_sid=o1D5G4f567U6g5F8w2X1k3b7c8; tvfe_boss_uuid=316bd7199cab1b18; o_cookie=425485346; ied_qq=o0425485346; _ga=GA1.2.1225764715.1555726905; pgv_info=pgvReferrer=&ssid=s8691211104; _qpsvr_localtk=0.7384854534932486; pgv_si=s2370873344; sFrom=website; building002=view_union_info.alc; user_game_info_491933744=0ff86afbf98e6b1e2b505efdfc8c94720c67b1c1ead78b0fe3b55da275e1c56abc0575f21d8ef36ac1c37d103aabf232e7e0d770fb6932c9b4b8448153f5e4580945f9578da9d618dd754c795264569f666dc60de3e1481ea0b915b1c3a19218a3c5ceb7a25b46a71da6ead655c779db336248721881268c02ea48fa7408d1fa7861e68295d7498428f511740e84a11d10c71f0ea059fc3758acce57bc4be09f63b6c93bb421efcb4fd1717c5cea7da1f46bddbd7b7690cded4377c9c85e669f1d6935e4dcd0e70e066c1647fe77b01e145aac0c9689e54eb51341eba3a7bfaa2e6652d260345b0e755f234b24e0ea8f74d22c90e71d87d75d56f2ed4dc10ceb4e859ac7a4d4e17576f71b3915f42dae3c7fe22d830fc2ee25965b818a1c560a68136b9ef5cccdffc26f8bfe3602d3c2bc6414ac1f282f5f8e2718ede899be7dc7b7eb9716aaa1fc6d9d228b2a6f93b2f05911711037ecb15984148afe6550ba773f3d7073a0f7c47b22c1f14da29a737ed9e16527c984d5a7059b89bc1a67d2ffb29d509ac9df0fd74c0dcdbc6923ff5fda0e76f1e9d0e133c742232f2071b850f167a2f0f4d8b933f92a216148edc63b906de33084a2a3; imageurl=\"\"; numofhero=b3f6b90ab408134304fa68bce436ecebf3a55e06eea60ee7; ptisp=ctc; ptui_loginuin=630516784; uin=o0425485346; midas_openid=425485346; midas_openkey=@EAkfJbUVf; user_game_info_630516784=35327691c49b68db73742ab2c5915871637e1064eddbaeefa7a6d62b0e311c9bfc2d1ce3d25d949a8721fcddbd7a1aea6f7d1d88b88dd33435704402a1db761ed0c1e7d2d16026b62a2bb93aa22757872eaf12ea6c2c1c5adf26c0a24c6007bd13726d6e1f7d97e8a426e33eb63371767582ab3d6d8372956f186831e60ea2ad2b28cd3c7d697e7522be9d2de97edd07a0a5f893c6dead4f80763d93a4088e670baee88487c3827bc4c24ea702125cae2449dc143b13507b3d1244f4aa03154bbe437eb0041cf005dc891616bb8aa5c46332d64c35a3ba84423e2ab3a07c05dfa0bd8d20b2731fa1ffa92cda3fc683d63a80095a71091d099464523f605db933e81490429339fd6c2fdc3f844ad234c9ab11a758c6f169d99575e34a2d7bfd2ce69e6d85556e41e5126e95e78fbc50a1e5d0ea556e04c6798d9aff7e15d210f2477aa5a4fc8f6e1fb5903c7841ddc577089d4a130930b83c415ed596ebf02e5b13f110fde970c61742aa003792c5c6b7f52979111e6a44ce3f0da37ffb1dae4cbb35957cf856c33bf470e7950e3f2024ab9bdb775b91b6249bad05281bcacd9a89725a44862759f4; ied_rf=--; skey=@NvhvUJ35e; IED_LOG_INFO2=userUin%3D425485346%26nickName%3D%2525E5%252587%2525AF%26userLoginTime%3D1561739992; sl_login=425485346%7C61%2E140%2E245%2E85%7C1561740008%7C0%7C1%7C2%5F8%7C0%5F0%7C1%5F5%7C0%5F0%7C82DC71AC321C0DC8C17108C1711238B0; user_requests=a400464e0a7f0eb3053e23adb07e1eba249bde350316fb74bbef73535c64d1ab046ef9ac74e058f79bdbb4da82cff733285f628afc904ab7cd29634799fc16572244170434665454; first_login=f8c29e64a1d51f784d25d5809880a15a; user_hero=\"1302095433049915/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A2/189/1/1/1/8/1/g9#1302016221118280/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A1/177/1/1/1/6/0/g12#1289924374531220/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A0/153/1/1/1/8/1/m6#1302093755321980/%E9%98%B2%E5%BE%A1%E4%B8%80/129/2/1/1/7/0/m2#1315003310221209/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A4/120/1/1/1/7/0/m7#\"; user_game_info_425485346=f95da9d19b366f1dff8a68b9cc85b4cb1bf218fe4e2c109fd8992cc4d2aa91dd406d2e58d7c6d1da51a6fde53959b896ab7d93ad45568f39c8a2d1f5f65c84dfa6dce3296fbcf15690d26ace212674e68474f0cb21abaa64d2233b2b8553d8059f60e1bd5a73df0053170000cf05b96f73aaaf1f8a2634b0c6d5fcb3ce396b4d9f6a29603b264f9efb048563d75f035691c536f0ea3fd7e908ee5af470a02a9fd324d6f5bb32121a970cb2ae4e8caba35f729b6471cee98353446359264ffee8affc825065c40d7ca14c6f4c7b79ec2bc40749af500183fc12d6536ec3c4cd49f515f6a424e2f80523b1598d4832be841566acb57a663f0d7ad7c94592194f14bf24b0f39be70ee138f5fa272d2434dacfcf43af0dc679f838f747dee3cae417c51c42428ac229eefbca341ecc45ebac2a1ed1168a0adef7bb7f27ed93bae13a1387c8b06c5e081cdedf23eadb1e871f52e12d3077042ea29fad7c799a66f85f01607b8959195aae4bd26182ede69d5cee6b04f3d7af70e36025454a53dd990ddf0ca7eb3ca621954d3da89da6106def2e986565c94ef2a8c758eea3e7f8e2fa1af327753172427fa83554e1117d6939c00937f546318f7b7fe55e756707d59acfb383187b425909084cd660d87b4ec1; user_info=\"%E4%B8%BF%E5%87%89%E4%B8%B6%E2%97%87%E9%A3%8E/%E5%B9%BF%E5%B7%9E%E8%81%94%E7%9B%9F/27180000/2/4317/66360/23/%E8%AF%B8%E4%BE%AF%E7%8E%8B/425485346/1244254858510599/1/1\"; user_army=\"1/9492491/pudaobing/1#2/0/tieqiangbing/1#3/288000/jinweijun/1#4/6500692/jinjiawushi/1#5/13397473/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/4726141/shenhuochongbing/2#9/8342364/qingqibing/3#10/0/qishebing/3#11/356216/zhongqibing/3#12/7351567/hubenqishi/3#19/4375/fangshi/4#33/493432/tongshenyuzhe/4#20/429626/shensuanxingguan/4#21/75192/bingyinyangjia/4#\"; building003=\"425485346,767076,120,building10\"; building007=\"list_trade.alc?cid=767076\"; onlinetime=\"2019-06-29 00:40:09\"";
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
