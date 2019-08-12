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
//                rc();
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
            String Cookie = "JSESSIONID=2A2EE35AC3FFDEC6835624AF15540B0F; ied_rf=--; pgv_pvid=380613964; pgv_info=pgvReferrer=&ssid=s3390984604; eas_sid=E1N5x6x5f556w9A9a92438j1Q6; pgv_pvi=2817674240; pgv_si=s2093238272; _qpsvr_localtk=0.6195076755788107; uin=o0425485346; skey=@cDXuKQ51k; ptisp=ctc; RK=yExYw5NYZq; ptcz=f238cbc505795cff20b0dc6220dd6b58662e0c433abae68bf1bb804c37627b83; IED_LOG_INFO2=userUin%3D425485346%26nickName%3D%2525E5%252587%2525AF%26userLoginTime%3D1565569995; sl_login=425485346%7C113%2E119%2E28%2E33%7C1565569998%7C0%7C1%7C2%5F8%7C0%5F0%7C1%5F6%7C0%5F0%7CBA47D1D01898B1018F34DD8E34B58AEF; sFrom=website; user_requests=acddd9888b60827251c07832e57d3a1983a1da317bbb6c4d8e6d068d6e33ec9ee8ed90e2a284f9b353bdf16d4d833ea2c9604b57ae9bf8c2c94456bb859116d71061c5877ba689f9; first_login=f8c29e64a1d51f784d25d5809880a15a; user_hero=\"1302095433049915/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A2/190/1/2/1/215369798/1/g9#1302016221118280/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A1/178/1/2/1/215369798/1/g12#1289924374531220/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A0/154/1/2/1/215369798/1/m6#1302093755321980/%E9%98%B2%E5%BE%A1%E4%B8%80/130/2/1/1/6/1/m2#1315003310221209/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A4/122/1/1/1/6/1/m7#\"; user_game_info=5d27eb5a5f867f895f3e7b6e4c3b88313cca1ae336191d920fb5eb22db61b0e1584d0cbd306363638611a7605cc43a826519740a5c3c212b89302d4b7c2254381e5faa42e948491f5c21180c8289ead217bc273b945a98ad7f8c7f7449603593f39c1e611c12636703e257acbce974ed198966f9a9643bb1344f1f4042b6e13e30418493fc26e89972d5ff88f27fc3a0562a0ea61d1fab1709e897a6fe39052571d570ad0542da825ca1aa476a39422d9e3b0b179f47ba7308b183f0eee3064fb458771d93c9be20c470558eba6fcd88fa0141dda2fdf6c49f879a382d8f7d807b759596ded8b508f60cc457eb82762b699577b9e87f4a2c7cd3b882ce9884fc06a85b36ce4cc335babe6bdc349f95b8cc7160526670696f4b2046bc7bc94b29370fa36745e1bebacca0d217d12a9fb3c879128c23abc8e0ecf00488276ff03beac2b844a48083929fdf055d5191645a0dfc42e6300c51c23fc87bf6e0684daba32a061cc803aa0df270b0b5bc1729dbd529363061ba4f99101c572fa46acd4a07ade52a8f3312fc5b7a56c14b0c129619e808c46926d516c52b40f12d6fb99bb5e9cfd599cfa9c4c5d254acd1a0e5e8ca9baf47400cd4dd7dc52129062f74d354da18f42964de7610d54d243bf399ba; user_info=\"%E4%B8%BF%E5%87%89%E4%B8%B6%E2%97%87%E9%A3%8E/%E5%B9%BF%E5%B7%9E%E8%81%94%E7%9B%9F/31923351/2/4317/70845/23/%E8%AF%B8%E4%BE%AF%E7%8E%8B/425485346/1244254858510599/1/1\"; user_army=\"1/14632915/pudaobing/1#2/0/tieqiangbing/1#3/456000/jinweijun/1#4/7923316/jinjiawushi/1#5/14255501/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/6809361/shenhuochongbing/2#9/14792404/qingqibing/3#10/0/qishebing/3#11/872216/zhongqibing/3#12/9021387/hubenqishi/3#19/4375/fangshi/4#33/573398/tongshenyuzhe/4#20/698435/shensuanxingguan/4#21/102192/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"425485346,767076,120,building10\"; building007=\"list_trade.alc?cid=767076\"; onlinetime=\"2019-08-12 08:33:18\"";
            // 天涯
            String cid = "140183";
            // 凉风
            cid = "767076";
            // 寒风
//        cid = "848100";

//        zhenChaUser(headers,"920419363","6","-493",cid);
//        paiQianZiYuan(headers,"425485346","-379","284",cid);


            // 伤感寒风
//            Cookie = "JSESSIONID=700DAD40BCD867A80C72267F29252EAE; ied_rf=--; pgv_pvid=9239953538; pgv_info=pgvReferrer=&ssid=s224340530; eas_sid=81n5r6J5e5m1h4s3T667j5O9I1; pgv_pvi=3609670656; pgv_si=s6809023488; _qpsvr_localtk=0.14251316923921187; ptisp=ctc; ptui_loginuin=1455749561; uin=o1455749561; skey=@70udwZAD8; RK=ykTtC5zMZn; ptcz=401372859ded921e3301d66e732c2fa7028ab4097af8d4ddf1aa777f2c50d3fe; IED_LOG_INFO2=userUin%3D1455749561%26nickName%3D%252B%26userLoginTime%3D1565514375; sl_login=1455749561%7C113%2E119%2E28%2E33%7C1565514378%7C0%7C1%7C0%5F0%7C0%5F0%7C0%5F0%7C0%5F0%7C1961599F2CF587608CE79198E7EAB773; sFrom=website; user_requests=cdeaf0b7f24b9278c1ba8fd12597a898b1f91af7c2aa8c8d35ec32362e5a6ac289dc8e7ca2d77d11fc6a6f2f9c9921c277f1d167df9ff21803fe7b9af8030227295ff14888d80e814c09265112ee12a0; first_login=96553b56a8c37d9f103b3346d66ac9e1; user_hero=\"1451681274393317/%E5%A4%A9%E5%85%B5%E5%A4%A9%E5%B0%8624/49/1/2/1/215365859/1/g10#1462075734859269/%E5%8D%95%E8%90%83/48/1/2/1/215365860/1/g2#1462125782674145/%E9%AA%86%E5%B8%88%E5%B8%88/48/2/2/1/215365863/1/m12#1462243304920916/%E6%94%AF%E6%80%9D%E6%80%9D/48/1/2/1/215365862/1/m14#1460901026267770/%E7%9B%9B%E6%A8%B1/47/1/2/1/215365864/1/m10#\"; user_game_info=11051ee7f51a598bfc2fb193ddfe5ac98527f542a68d5d4c21a41a10a4c0976deef25aebb9aafb41aa047eb6c163a8a16264fdaa1be2df0df92b803b8359119fb9f92950503c739ae6732ca7597a9b3ecb5a3eeba2866e895ca70368a42faf57cda3da1f14f065ecb18f65b56954b8b99f663cdb53a5e4f6ecfe79500fa5cb7216dc8818b3b0976e3093906c84c0934412a5385899c22d2a6b89aea8f4a2e6de0f66855dd984010d3fcae37e74527c40e41863b9860bb860f61d679956e678a8ad2911178818a48c07ffc76ed33013b8f7558d397c7e52fe4383f1e35ea9c6bdbc5532730a87be2f96358a5a598e2e3e98810bf8b57afc087321b3bd4a96173836e3025d244d55bd1d8c69343c20181349839c38fbff25d4980007019eb9e5a34189b7dff9272cdb9abb4cc92df8a54c04a2fcf5a3ff4693a49a01dbe4eeedc0bc5ce8f2eabdf32d5b0de41480d5cd376be8f953b2156e2920ae15bff01d7fc7aa58d2b9c5af186bad42ed0389a0a5352174231a017c4978a51f248aa5781a8772a05193fff937be4d4565076cf219ee8b8a1f00e944a823831c675861cfe22f44028f7272dc6cf7231c4dac7ec5249a2816cc67f74444954226194655e5bc0a; user_info=\"%E4%BC%A4%E6%84%9F%E5%AF%92%E9%A3%8E/%E5%A4%A9%E5%AD%97%E7%9B%9F/411627/204/6541/37029/20/%E9%AA%A0%E9%AA%91%E5%B0%86%E5%86%9B/1455749561/1247324702873190/0/1\"; user_army=\"1/5172/pudaobing/1#2/0/tieqiangbing/1#3/0/jinweijun/1#4/0/jinjiawushi/1#5/5152/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/0/shenhuochongbing/2#9/37481/qingqibing/3#10/0/qishebing/3#11/0/zhongqibing/3#12/4186/hubenqishi/3#19/0/fangshi/4#33/0/tongshenyuzhe/4#20/0/shensuanxingguan/4#21/0/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"1455749561,1010319,120,building10\"; building007=\"list_trade.alc?cid=1010319\"; onlinetime=\"2019-08-11 17:06:18\"";
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


//            gaoFu.forEach(s -> {
//                form.put("endCid", "6");
//                send_hero(s, form, headers);
//                create_mission_room(s, headers, "15104", "1504", "6");
//            });
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
