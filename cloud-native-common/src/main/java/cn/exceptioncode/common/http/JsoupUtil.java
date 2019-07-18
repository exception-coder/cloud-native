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
        Cookie = "JSESSIONID=41998764642BF30CCF17FB2B0303BB04; pgv_pvi=7795611648; RK=tExYgZNANK; ptcz=b67c82875b968c80fe1903e6464d171591a99d9e47592efe5fa5d16f1b3e4f27; pgv_pvid=9388715142; eas_sid=o1D5G4f567U6g5F8w2X1k3b7c8; tvfe_boss_uuid=316bd7199cab1b18; o_cookie=425485346; ied_qq=o0425485346; _ga=GA1.2.1225764715.1555726905; pgv_info=pgvReferrer=&ssid=s3148599668; pgv_si=s7496012800; _qpsvr_localtk=0.341832795538358; sFrom=website; building002=view_union_info.alc; user_game_info_1294885372=14d25cff71bfa9f6bc216c136b80c23d9879b0e759ef359fe5695eebeff374d7c54f4b64d7015361f08ca8e31ec8dc0bebc029f14ee71899b7f59b3b27f0e88dda59d5e758e8ae8292cdafffdb26a23b69f3425d87f289deae6114f21c317016a4164e303748a1bff22363a20b3bd2aa2be9094098be467f42256d60d1fcdfa7d393b6212c41088086bf8082d9feb1c8e652c581bfc7ac1736d406c037bb794491a2929af7777f366106533b5c3611d1ff9ee5358aae043d2056508f5eb654c0829043d3f3e2ecaca9807edc9ad05a96cc7e3812830e97c1b0864816a356e3397fba7b1eb9a37f9d48142f3a7e656526b9a0a366c8256bab0de8e575b2bcdd9cfe81599503716004467f05f91eab1f3fbe68ed8664a1f1d4a066fa81111570ac094cee4b54f4673149770b8c35c2ba0985b97f1b4da323426fc7c143061f8cf5ee5a2ae1f479b3d0c80ec86163206f40febc5aa264d1d838d36430ea9e2a42a0c785cd1687fe263c87fbd2cdbcbf4732d5e4e928f1a543a717e9c7fb2fb70d29aa4fa7de00be5e085900d0bbd53a819a; imageurl=\"\"; midas_openid=425485346; midas_openkey=@R4AT09rI2; user_game_info_491933744=eed9d2c232f3a8619e946d944358090cb5497b10070758c471408e9b6117b218576ff395c22d636b3fc1d46e6cdc0ee08cb759643e5c4b811b8d12d445b3ac63fcdbe3b0e2fdf61a8e15e9c5eaa303b7ce0a3687fd2bdd0fd9afb5f963bde92df4c26e04092ff6229ba4e26f4fd1ce0ecfbb914e428b2da1fec67b4d156d2a7a9b8c4f1a61b342accefec75871db2fa159dec67a38e88ddfb56a6646f2f94a1c8d14fa93ca9129ea94735f9498dc02475f3ce50a76d05e56abb9f0bf9aac600efc09439f2771e8fbf33b1bee27dc3b6b795b0aaaee4102de27f4bd08657802d60f1cb3062a420980ca7a4a03d3995a5477827a9cc2601b671c2146738102055fd0d3656beee6229d0179509c18000637e61ce280989ac5238228080d40f4fa81b10c9fa5c1f6fb0a6955afb846399a46e144d5484da037144e69e692461c5ae6c796037508758170221fd0a928d2ad8407afecbc4e37ea26353acb5ba28440214e9049c9d00303a9bcc8c8740db0e9a1bca1134f37e9fcb1fae766b40013a74dfd86eb4e85699331cd2fd4bf4d3b5f425ce64c63243022c2a49f2a2c5d46f132ce3f052717c681cc6455c3a187cc18a8a8195478d402616e; user_game_info_862784951=4ca243643040cd2584d528d1ac2443dcb9fc5dd5b7c5b4cb986707b95796d31ea41b096da6a025a540e25070e1d0138fa4a1d776d05011894a56da8291c998eac5ad331bf022aae277bcbff30e8d996b4755a4ff95ff7bf5bbffb7d3cce7261c7d9f01bf5cd0ae4e7d4318cb801c6a5858d019132deeb1237570560f0767272d7a22ad06d2eb31478e5f9d64a1fc90d6e457496e47672420eda8af24a28132a30647a5b18e139b03915ff776880bacf300d1ac1d23577dddf7658ba3ec86ab401fb7ad7dfc5feec11b3ba226cc2a9fe19455fe98474f5f2297195713d59c0a2ce3d7f55049d37e89da682f6ca830baf6ce17b5da0667ae2c685f130e040923c3b5bc5fdd1d4f976a85be302ff6c4e2d1ae66fc8151a7a4bb2ac35215f3cf55b31aea27f41424aae38415fd4a230ad098fa4461e5600924196efa6d7e2b51c3409e00bef739c0b8c6563ca277ad9463656def47bc00ec569d89e5dd3906d04830d673949707cd3e9bd92e2460d67ef568c5702cd2150e0362c47c8b390b9925cd0e6b9d59780491127712465f0a22e7d6d6f3088387b15b054398fa75082390ce6b7caf35d1fd5da19c705ee3fc77dd5a4bc893927dab14a1; ptui_loginuin=1294885372; ied_rf=--; uin=o0425485346; ptisp=os; first_login=f8c29e64a1d51f784d25d5809880a15a; building003=\"425485346,767076,120,building10\"; building007=\"list_trade.alc?cid=767076\"; skey=@vAcFKWHjS; IED_LOG_INFO2=userUin%3D425485346%26nickName%3D%2525E5%252587%2525AF%26userLoginTime%3D1563379172; sl_login=425485346%7C47%2E90%2E47%2E200%7C1563379175%7C0%7C1%7C2%5F8%7C0%5F0%7C1%5F5%7C0%5F0%7CCE33EFF1CF738DD79AB811EAB094EB95; user_requests=945405f7659a50ad86f66a95e376e6408ad29220be4090b5464fe174bf3862fc132a5ccb42d863f32e18ccb30731ced3b7bb97df845c6f55351177423d1c31a39a9966139ab5a24e; login.17dian.com=f8c29e64a1d51f784d25d5809880a15a; chuanhao_check_1563379175542=931e8dd9a257cc3ae804db612c0fc982; user_hero=\"1302095433049915/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A2/190/1/2/1/215167561/1/g9#1302016221118280/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A1/177/1/2/1/215167588/1/g12#1289924374531220/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A0/153/1/1/1/6/0/m6#1302093755321980/%E9%98%B2%E5%BE%A1%E4%B8%80/129/2/1/1/6/0/m2#1315003310221209/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A4/120/1/1/1/6/0/m7#\"; user_game_info_425485346=f23dcdae26704946fffc7b9ca8d95d68ace9d0ff906fb3f25d7b8c859db75fefe2e2ba36e56624555c27ae75a34435fd00cb65368afa2fbced1418a6e77757a71faab5bddfcd28b012d40e2588104f0e67765197e1a30061a80f83a23c114762fa05886c31c4cd3ad0e495724b1744b956d9e8e6897c52ad3d0687e648d0ddefc109cac02c1e33983580266fd429464085fc3077a80b909fb61792a5f0958e2e160bb68e0bfbeb810adeed97bf5f35944ce3c4b2605f93dbedc642d0b9bf067b81624954a83e2b0bb4b44fe01e7c142c56eddc66b24411e6c571007d89a987053fa7decdc4fcab0d9b6025f91114576dbde7dbb2e87eccec3851bd3217ea87f44feed6593acdc337c7c4b869f3c0936f2b40b7c4ea95497be3cbfe11b43d8c7233c16e3ecd12f5e3deeb0bc447027f20496c1d51aa4a11516d09b97e2ff7abff0601871e34b53e6fdcb54f78694ebf08944d6944f4b8632e4732899cf38fd297ea9c6037d12f661fd95d8406319a89a3659a6aba9ad62152e80c5163d9b27cfb1d35161e558ea42b28a1d7261b86f67f7e1606b732d530c2bfdc7efda554032ee889b2263c94f5a4957549489991ebfc7e488f04b109fe46805c0faa873b34fd6ad9d92358a3914f88e2cc26f1bc799e; user_info=\"%E4%B8%BF%E5%87%89%E4%B8%B6%E2%97%87%E9%A3%8E/%E5%B9%BF%E5%B7%9E%E8%81%94%E7%9B%9F/29250375/2/4317/68289/23/%E8%AF%B8%E4%BE%AF%E7%8E%8B/425485346/1244254858510599/1/1\"; user_army=\"1/31105/pudaobing/1#2/0/tieqiangbing/1#3/0/jinweijun/1#4/0/jinjiawushi/1#5/7919/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/4145/shenhuochongbing/2#9/37921/qingqibing/3#10/0/qishebing/3#11/12000/zhongqibing/3#12/12000/hubenqishi/3#19/0/fangshi/4#33/0/tongshenyuzhe/4#20/7139/shensuanxingguan/4#21/0/bingyinyangjia/4#\"; onlinetime=\"2019-07-17 23:59:37\"";
        // 天涯
        String cid = "140183";
        // 凉风
        cid = "767076";
        // 寒风
//        cid = "848100";
        headers.put("Cookie", Cookie);
//        zhenChaUser(headers,"382649535","135","103",cid);
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
                // 远征左贤王
                create_mission_room(s, headers, "11013", "1115", "2");
                // 莫高窟盗佛
                create_mission_room(s, headers, "11012", "1114", "2");
                // 月牙泉的三宝
                create_mission_room(s, headers, "11011", "1113", "2");
                // TODO: 2019/6/27  冒险失败 尝试 冒险 漠北围歼战
                if (res == null || !res.contains("1")) {
                   // 漠北围歼战
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

//            gaoFu.forEach(s -> {
//                form.put("endCid", "6");
//                send_hero(s, form, headers);
//                create_mission_room(s, headers, "15104", "1504", "6");
//            });
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
//       log.info("{} 坐标 ({},{})，响应信息：{}",marchType,x,y,resBody);
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
                send_army_zc(headers,myX,myY,strs[0],strs[1],"10000",cid);
                Thread.sleep(1000*15);
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
