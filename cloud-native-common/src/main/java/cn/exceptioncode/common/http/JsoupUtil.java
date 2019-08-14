package cn.exceptioncode.common.http;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JsoupUtil {

    static LinkedHashMap<String, String> userIdMap = new LinkedHashMap<>(10);

    static {
        userIdMap.put("丿★阿英ㄨ", "455969164");
        userIdMap.put("丿凉丶◇风", "425485346");
    }

    @SneakyThrows
    public static void main(String[] args) {

        rc();

        Runnable runnable = () -> {
            try {
                System.out.println(new Date() + " 执行大都市攻打开始");
                rc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//        service.scheduleAtFixedRate(runnable, 0, 60, TimeUnit.SECONDS);

    }


    public static void rc() throws Exception {
        {

            Map<String, String> headers = new HashMap<>(1);
            // 凉风
            String Cookie = "JSESSIONID=6F70283723D7F09B53576045AAB93968; ied_rf=--; pgv_pvid=4894692547; pgv_info=pgvReferrer=&ssid=s3674985232; eas_sid=O1z5g6I557v1m2x8D4q1a502K1; pgv_pvi=2980904960; pgv_si=s415192064; _qpsvr_localtk=0.7980474793982131; uin=o0425485346; skey=@qaMX7GhTl; ptisp=ctc; RK=jExQw5NoZK; ptcz=a5a40a665a14837e5a6cdf1aa9f0d43d3cc3cb49d69746017592c1b15dcdc4c8; IED_LOG_INFO2=userUin%3D425485346%26nickName%3D%2525E5%252587%2525AF%26userLoginTime%3D1565712843; sl_login=425485346%7C113%2E119%2E29%2E67%7C1565712846%7C0%7C1%7C2%5F8%7C0%5F0%7C1%5F6%7C0%5F0%7CD1E88A30BDDC444EF3FAF65221158D26; sFrom=website; onlinetime=\"2019-08-14 00:14:06\"; user_requests=2b84a6a9bfcf0600c1c3a22c3a49f31cc9e9bf0e33edab467f42aba9dddcf4957f2384a166621572e6d8b787bc791084df80f1e15ffd45ce662e6d8ba6ca3c8721d6197628a5e8bd; first_login=f8c29e64a1d51f784d25d5809880a15a; user_hero=\"1302095433049915/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A2/190/1/2/1/767076/0/g9#1302016221118280/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A1/178/1/2/1/767076/0/g12#1289924374531220/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A0/154/1/2/1/767076/0/m6#1302093755321980/%E9%98%B2%E5%BE%A1%E4%B8%80/130/2/1/1/6/1/m2#1315003310221209/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A4/122/1/1/1/6/1/m7#\"; user_game_info=dc4e2b39898b7f126a63ff57889983410cbf659f16d4f62bd182a0317552172fd47a54d142b37eba9b00dd5d630202cedacd1fc12efb99d660199f382194ef86d20cf709639aef8a4d9c068b3ac8ad2af21112eab0d2e219539a9c56d513071f80db566d82325f0f2ccb4c1023d0071803a785faca9bac815d6142bec19e6fe90f57448d93ff6af5b20c5d3998536f3e5324197a5a74e646b4b7350d82056f4af30c1fc6e573b328986d104260fd699cba70aee5e4ea00b9db40326c757a6a65949011c2c0c5d7e08e1e224cc7347d3f789738f9b263e771e4bc53b1cd72d35e4e9b2ed98d679dddb9206359ecd0334e57ec5765f90510f7cf8dc6aece644dee1dcd28b1fd044bac7989b45d0834d695ba46c4c0f8e24fbfc76ca4874919c3762069a4caeeedbba5201776124bcad1cddfc739e090869724b8cf4ccff8e1d9d9034f7d76bf8b2f4dd19078590ab5e3358e195c71bc6fbb0840241e8f70ba1ed670e432fa8df73b2b97bd0d2f930f68a121bc7f3485d82285da68fe1cef40681790d4461e2acfd21654c441ac9ac0e13df2e4f1a788d7bbd417a1c7814a2d5f36f5ca732ac528cb55c3e6f09ae8db9ef3e043f8dc3524ed942114a6f1cdc480d8680fc5954ee97bab4a7537e638a276a6; user_info=\"%E4%B8%BF%E5%87%89%E4%B8%B6%E2%97%87%E9%A3%8E/%E5%B9%BF%E5%B7%9E%E8%81%94%E7%9B%9F/31974519/2/4317/71070/23/%E8%AF%B8%E4%BE%AF%E7%8E%8B/425485346/1244254858510599/1/1\"; user_army=\"1/14632915/pudaobing/1#2/0/tieqiangbing/1#3/456000/jinweijun/1#4/7932836/jinjiawushi/1#5/14267501/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/6841091/shenhuochongbing/2#9/14846306/qingqibing/3#10/0/qishebing/3#11/872216/zhongqibing/3#12/8938225/hubenqishi/3#19/4375/fangshi/4#33/581549/tongshenyuzhe/4#20/705587/shensuanxingguan/4#21/102192/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"425485346,767076,120,building10\"; building007=\"list_trade.alc?cid=767076\"";
            // 天涯
            String cid = "140183";
            // 凉风
            cid = "767076";
            // 寒风
//        cid = "848100";

//        zhenChaUser(headers,"920419363","6","-493",cid);
//        paiQianZiYuan(headers,"425485346","-379","284",cid);


            // 伤感寒风
//            Cookie = "JSESSIONID=950C28A22933B3AC7F18A988C57CDAEE; ied_rf=--; pgv_pvid=3533730208; pgv_info=pgvReferrer=&ssid=s9452651312; eas_sid=V1d5v6W5R6o0U318n6q7q2b9S9; pgv_pvi=3299686400; pgv_si=s317265920; _qpsvr_localtk=0.9633678080502068; ptisp=ctc; ptui_loginuin=1455749561; uin=o1455749561; skey=@YCz9iOMdi; RK=ykTtC5zMZn; ptcz=856b8e2834d397040716c320830962a0523441e8f8d38474fa89de0793d3f2d9; IED_LOG_INFO2=userUin%3D1455749561%26nickName%3D%252B%26userLoginTime%3D1565603872; sFrom=website; onlinetime=\"2019-08-12 17:57:56\"; user_requests=fbbd18d5dad8ecc06746b3850ba5e61a634501cb9080aa5a5da2ebb6bf68e4607e7f8a72059bd0525df4f4774bfeb7009be4cdf39ee4468e732ce3341b3a50bd3022aaf42e463509f97a21c48e3182fa; first_login=96553b56a8c37d9f103b3346d66ac9e1; user_hero=\"1462075734859269/%E5%8D%95%E8%90%83/49/1/2/1/1010319/0/g2#1462125782674145/%E9%AA%86%E5%B8%88%E5%B8%88/49/2/2/1/1010319/0/m12#1462243304920916/%E6%94%AF%E6%80%9D%E6%80%9D/49/1/2/1/1010319/0/m14#1460901026267770/%E7%9B%9B%E6%A8%B1/48/1/2/1/1010319/0/m10#1462153116005795/%E5%8F%B8%E9%A9%AC%E9%9B%AA%E8%96%87/43/2/2/1/1010319/0/m13#\"; user_game_info=93820f54843f2273fcf16b1b99ea8342a5df6d044d22a8ac3742d8f5246caeee0219d3572047134ae26eb8703ee5185080ffe342fb3093400009d3bc849bd998b3ca88cc16dd6a0f5198fda2fa7c40da94464066252d866827cf7c4cac75f6e63a18f227e42fc4ce1e46729ce6e7d924f21f93512df9c9609618b09ed1b24d013b2209ff6bc6fbb98a664bb0a3f10b18b3e0d35d8a877e3e0d4e3cd9eeee3dce55f9c51139349e098cd9a012e1cdeb5ca88b549368182f6cd7367e7bcb906d1dc70b3e330ea63e6e352c14e2effc7b129982bdc24d568baead24d10d53eff5be2a2ffe1995f2901fea51d20b60675478bbec62827b03522be803575c818d06769815901f170b26b36d3c48c72a31898985a5c95e417850243c3f5407d1fb9164185d7c00952d34feb126b905061518dfc4942eb39ecbd4c213986c479868e563858c0a41b344106b118098e53dfa3cf23b47414a51a41ab2800b8aab72a0e24802a2b9cc7b6b3923ef8b934083b04c194fe3b8d82589a9883b537060b136ec93daea437fbb7780cf417c2f670a1f0905c45e27a44f2b606ead29a726c60a3d398a564b8b5a27576f01be378520d94ce49116641fb99ea216cd2da43bb97e2a40; user_info=\"%E4%BC%A4%E6%84%9F%E5%AF%92%E9%A3%8E/%E5%A4%A9%E5%AD%97%E7%9B%9F/414154/203/6541/37071/20/%E9%AA%A0%E9%AA%91%E5%B0%86%E5%86%9B/1455749561/1247324702873190/0/1\"; user_army=\"1/11534/pudaobing/1#2/0/tieqiangbing/1#3/0/jinweijun/1#4/0/jinjiawushi/1#5/10988/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/0/shenhuochongbing/2#9/47481/qingqibing/3#10/0/qishebing/3#11/0/zhongqibing/3#12/123463/hubenqishi/3#19/0/fangshi/4#33/0/tongshenyuzhe/4#20/0/shensuanxingguan/4#21/0/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"1455749561,1010319,120,building10\"; building007=\"list_trade.alc?cid=1010319\"; sl_login=1455749561%7C113%2E119%2E28%2E33%7C1565603907%7C0%7C1%7C0%5F0%7C0%5F0%7C0%5F0%7C0%5F0%7C2CB5E85C9E106FB97B3C8B51B9E59132";
// 伤感寒风
//            cid = "1010319";

            headers.put("Cookie", Cookie);
            boolean continue_ = true;
            if (continue_) {


                List<String> zhuCheng = new ArrayList<>(10);
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
                    Elements elements1 = element.getElementsByAttributeValue("value", "查看");
                    elements1.forEach(element1 -> {
                                String onclickStr = element1.attr("onclick");
                                String heroId = onclickStr.substring(onclickStr.indexOf("heroId=") + "heroId=".length(), onclickStr.length() - 1);

                                element.getElementsByTag("strong").forEach(element2 -> {
                                            String ownText = element2.ownText();
                                            if (ownText != null & ownText.contains("位置:")) {
                                                Element element3 = element2.parent();
                                                if (element3 != null && element3.ownText().contains("凉粉")) {
                                                    zhuCheng.add(heroId);
                                                }
                                            }
                                            if (ownText != null & ownText.contains("等级")) {
                                                ownText = ownText.replace("等级：", "");
                                                int heroLevel = Integer.valueOf(ownText);
                                                if (heroLevel < 18) {
                                                    // 长安
                                                    changAn.add(heroId);
                                                }
                                                if (heroLevel >= 18 && heroLevel < 36) {
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

                boolean fdx = false;
                if (fdx) {

                    resBody = OkHttpUtil.getResWithQueryParam("https://s8.sl.qq.com/s//main.alc", headers, map);
                    doc = Jsoup.parse(resBody);
                    boolean send = true;
                    for (Element element : doc.getElementsByTag("a")) {
                        if (element.ownText().contains("-201,201")) {
                            send = false;
                        }
                    }
                    String sendResponseBody;
                    if (send) {
                        sendResponseBody = send_army_dadushi(headers, "-206", "199", "-201", "201", cid, zhuCheng.get(0));
                        System.out.println(sendResponseBody);
                        Thread.sleep(1000 * 6);
                    }


                    send = true;
                    for (Element element : doc.getElementsByTag("a")) {
                        if (element.ownText().contains("-200,201")) {
                            send = false;
                        }
                    }
                    if (send) {
                        sendResponseBody = send_army_dadushi(headers, "-206", "199", "-200", "201", cid, zhuCheng.get(1));
                        System.out.println(sendResponseBody);
                        Thread.sleep(1000 * 6);
                    }


                    send = true;
                    for (Element element : doc.getElementsByTag("a")) {
                        if (element.ownText().contains("-199,201")) {
                            send = false;
                        }
                    }
                    if (send) {
                        sendResponseBody = send_army_dadushi(headers, "-206", "199", "-199", "201", cid, zhuCheng.get(2));
                        System.out.println(sendResponseBody);
                        Thread.sleep(1000 * 6);
                    }


                    send = true;
                    for (Element element : doc.getElementsByTag("a")) {
                        if (element.ownText().contains("-201,200")) {
                            send = false;
                        }
                    }
                    if (send) {
                        sendResponseBody = send_army_dadushi(headers, "-206", "199", "-201", "200", cid, zhuCheng.get(3));
                        System.out.println(sendResponseBody);
                        Thread.sleep(1000 * 6);
                    }


                    send = true;
                    for (Element element : doc.getElementsByTag("a")) {
                        if (element.ownText().contains("-200,199")) {
                            send = false;
                        }
                    }
                    if (send) {
                        sendResponseBody = send_army_dadushi(headers, "-206", "199", "-200", "199", cid, zhuCheng.get(4));
                        System.out.println(sendResponseBody);
                        Thread.sleep(1000 * 6);
                    }


                    send = true;
                    for (Element element : doc.getElementsByTag("a")) {
                        if (element.ownText().contains("-199,200")) {
                            send = false;
                        }
                    }
                    if (send) {
                        sendResponseBody = send_army_dadushi(headers, "-206", "199", "-199", "200", cid, zhuCheng.get(5));
                        System.out.println(sendResponseBody);
                        Thread.sleep(1000 * 6);
                    }


                    send = true;
                    for (Element element : doc.getElementsByTag("a")) {
                        if (element.ownText().contains("-201,199")) {
                            send = false;
                        }
                    }
                    if (send) {
                        sendResponseBody = send_army_dadushi(headers, "-206", "199", "-201", "199", cid, zhuCheng.get(6));
                        System.out.println(sendResponseBody);
                        Thread.sleep(1000 * 6);
                    }


                    send = true;
                    for (Element element : doc.getElementsByTag("a")) {
                        if (element.ownText().contains("-199,199")) {
                            send = false;
                        }
                    }
                    if (send) {
                        sendResponseBody = send_army_dadushi(headers, "-206", "199", "-199", "199", cid, zhuCheng.get(7));
                        System.out.println(sendResponseBody);
                        Thread.sleep(1000 * 6);
                    }
                }


                /**
                 *
                 *
                 * 英雄执行派遣和冒险
                 */
                boolean maoXian = true;
                if (maoXian) {
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


            gaoFu.forEach(s -> {
                form.put("endCid", "6");
                send_hero(s, form, headers);
//                create_mission_room(s, headers, "15104", "1504", "6");
            });
                }

            }


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
        return coordinates;
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


    static String send_army_dadushi(Map<String, String> headers, String myX, String myY, String x, String y,
                                    String cid, String firstHeroId) throws IOException {
        Map<String, String> form = new HashMap<>(10);
        form.put("marchSpeed", " 61.8");
        form.put("marchSpeedPercent", "100");
//            form.put("resource1", "");
//            form.put("resource2", "");
//            form.put("resource3", "");
//            form.put("resource4", "");
//            form.put("resource5", "");

        form.put("firstHeroId", firstHeroId);
        form.put("secondHeroId", "0");
        form.put("thirdHeroId", "0");
        form.put("queryType", "1");
        form.put("0.45699247085102046", "");

        /**
         * 攻打城门
         */
        form.put("marchType", "13");
        form.put("x", x);
        form.put("y", y);
        form.put("myX", myX);
        form.put("myY", myY);
        form.put("cid", cid);
        form.put("posArmy", "3#0#0#0#1000!3#0#0#0#1000!3#0#0#0#1000!3#0#0#0#1000!3#0#0#0#1000!");
        form.put("armyIds", "1,2,3,4,5,6,7,8,9,10,11,12,15,16,17,18,31,32,33,");
        form.put("armyNums", "0,0,0,0,0,0,0,0,0,0,0,5000,0,0,0,0,0,0,0,");
        form.put("loss", "100");
        String resBody = OkHttpUtil.postWithForm("https://s8.sl.qq.com/s/send_army.alc", headers, form);
        return resBody;
    }

    /**
     * 派遣军队
     *
     * @return
     */
    static void send_army(Map<String, String> headers, String myX, String myY, String x, String y, String marchType,
                          String armyIds, String armyNums, String cid, String resource) throws IOException {
        Map<String, String> form = new HashMap<>(10);
        form.put("marchSpeed", " 61.8");
        form.put("marchSpeedPercent", "100");
        if (StringUtils.isEmpty(resource)) {
            form.put("resource1", "");
            form.put("resource2", "");
            form.put("resource3", "");
            form.put("resource4", "");
            form.put("resource5", "");
        } else {
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

        if (marchType != null) {
            /**
             *
             * 5 派遣
             * 2 侦查
             */
            form.put("marchType", marchType);
        }
        form.put("x", x);
        form.put("y", y);
        form.put("myX", myX);
        form.put("myY", myY);
        form.put("cid", cid);
        form.put("armyIds", armyIds);
        form.put("armyNums", armyNums);
        String resBody = OkHttpUtil.postWithForm("https://s8.sl.qq.com/s/send_army.alc", headers, form);
        System.out.println(resBody);
//       log.info("{} 坐标 ({},{})，响应信息：{}",marchType,x,y,resBody);
    }

    static void send_army_zc(Map<String, String> headers, String myX, String myY, String x, String y, String armyNums, String cid) throws IOException {
        send_army(headers, myX, myY, x, y, "2", "15", armyNums, cid, null);
    }

    static void send_army_paiqian(Map<String, String> headers, String myX, String myY, String x, String y, String armyNums, String cid) throws IOException {
        send_army(headers, myX, myY, x, y, "5", "16", armyNums, cid, "50000000");
    }


    /**
     * 一键侦查傻子
     *
     * @param headers
     * @param userId
     * @param myX
     * @param myY
     * @throws IOException
     */
    static void zhenChaUser(Map<String, String> headers, String userId, String myX, String myY, String cid) throws IOException {
        List<String> coordinates = view_user_info(headers, userId);
        coordinates.forEach(str -> {
            try {
                str = StringUtils.removeStart(str, "（");
                str = StringUtils.removeEnd(str, "）");
                String[] strs = str.split(",");
                send_army_zc(headers, myX, myY, strs[0], strs[1], "10000", cid);
                Thread.sleep(1000 * 15);
            } catch (IOException | InterruptedException e) {

            }
        });
    }

    static void paiQianZiYuan(Map<String, String> headers, String userId, String myX, String myY, String cid) throws IOException {
        List<String> coordinates = view_user_info(headers, userId);
        coordinates.forEach(str -> {
            try {
                str = StringUtils.removeStart(str, "（");
                str = StringUtils.removeEnd(str, "）");
                String[] strs = str.split(",");
                send_army_paiqian(headers, myX, myY, strs[0], strs[1], "125000", cid);
                Thread.sleep(1000 * 6);
            } catch (IOException | InterruptedException e) {

            }
        });
    }

}
