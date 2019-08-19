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

//        rc();

        Runnable runnable = () -> {
            try {
                System.out.println(new Date() + " 执行大都市攻打开始");
                rc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 60, TimeUnit.SECONDS);

    }


    public static void rc() throws Exception {
        {

            Map<String, String> headers = new HashMap<>(1);
            // 凉风
            String Cookie = "JSESSIONID=3C81FC9367036BAD870192BBB4350134; ied_rf=--; pgv_pvid=3122210209; pgv_info=pgvReferrer=&ssid=s5203063918; eas_sid=I1k5x6A5J840U2g7b7K6f7F0C7; pgv_pvi=7452976128; pgv_si=s5103954944; _qpsvr_localtk=0.1948267831532713; ptisp=ctc; ptui_loginuin=425485346; uin=o0425485346; skey=@2h1r1nbqv; RK=jExQw5NoZK; ptcz=14d49abfb4b2f3d17188f176a41c923b87508437fcd8aabd40946a017ff52683; IED_LOG_INFO2=userUin%3D425485346%26nickName%3D%2525E5%252587%2525AF%26userLoginTime%3D1565802864; sl_login=425485346%7C113%2E119%2E29%2E67%7C1565802867%7C0%7C1%7C2%5F8%7C0%5F0%7C1%5F6%7C0%5F0%7CB2DAB55E402FF9A1B01DF7066C1C0F5B; sFrom=website; user_requests=94890ae60346c57e75b9d27224dfe9a5caa5a3d4e251a2903ff06ddfc5129871d70fd9cd29db11a59ced27f49945e4758aa4a37a310e536320ec99712819c1ad209d82762d806075; first_login=f8c29e64a1d51f784d25d5809880a15a; user_hero=\"1302095433049915/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A2/190/1/2/1/767076/0/g9#1302016221118280/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A1/178/1/2/1/767076/0/g12#1289924374531220/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A0/154/1/2/1/767076/0/m6#1302093755321980/%E9%98%B2%E5%BE%A1%E4%B8%80/131/2/1/1/6/0/m2#1315003310221209/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A4/122/1/1/1/6/0/m7#\"; user_info=\"%E4%B8%BF%E5%87%89%E4%B8%B6%E2%97%87%E9%A3%8E/%E5%B9%BF%E5%B7%9E%E8%81%94%E7%9B%9F/32018929/2/4317/71229/23/%E8%AF%B8%E4%BE%AF%E7%8E%8B/425485346/1244254858510599/1/1\"; user_army=\"1/0/pudaobing/1#2/0/tieqiangbing/1#3/0/jinweijun/1#4/286/jinjiawushi/1#5/325/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/239/shenhuochongbing/2#9/1307/qingqibing/3#10/0/qishebing/3#11/0/zhongqibing/3#12/0/hubenqishi/3#19/0/fangshi/4#33/0/tongshenyuzhe/4#20/408/shensuanxingguan/4#21/0/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"425485346,767076,120,building10\"; building007=\"list_trade.alc?cid=767076\"; onlinetime=\"2019-08-15 01:14:31\"; user_game_info=37694765112ad16f51cc620557a26e2b5dc299b994a39cf3e0c377eddc37a46516aa80d1f00acc2284b7878c7c85be238e78396c90552ab11f69c2ec71fb4f0848556d3f5ad90aaaf9520b77f2c25c2db47decbb77f551dfdc253db397e5f777a43c9e4f11252073ede262f64d8b47cf287c6cc8dbce4e1ec14e609fc42338e7cd9056eb7377a66c0944189f153ca8becbd050311ce415683f9d4045e58678921aa9d0346fbf52b743c70161990fa8638d4e41be4e451e17bd798e608d497b41ba7fd25ea66d4da15d280ee053203cf1d2e02970b53f3d473cb6362b855e29104883dc6931361a198ba41efa4376dffab492eb29575e4c648b387334dd8794fdaba83362d4cff70734a271ce65a59ac69e1138eb3882048845e44d5f85ffbe96adc4bc045a4eb90112fdcb8536254621603967bb86bb48f51f8b8802835c91bdd41b6639e6851c829acc127fde2be295fcfe1ef7c5367fdbef1727f320abf5f3193d482d27f1b78422f0c0a98c13d0b0a5dc09275cf10a211e21ce28c0acc33ccf344e175da00fcd9c05fc42584e7187b179e310eef01f480e88289312dfe2e331237851dc9ac435e552b10910a0daef7e8160267c8a792abddae0f54965a8abfb8247c82828962f1e3d53ab9674a668";
            // 天涯
            String cid = "140183";
            // 凉风
            cid = "767076";
            // 寒风
//        cid = "848100";

//        zhenChaUser(headers,"920419363","6","-493",cid);
//        paiQianZiYuan(headers,"425485346","-379","284",cid);


            // 伤感寒风
            Cookie = "JSESSIONID=FC9288997BA78B63DF1FFD21FD85218F; pgv_pvid=6950034813; RK=LMwQl7N5bI; ptcz=0b989b1854accfdd956643e4cc7735929d112ab798ded47938d1ab3dffe655d6; pgv_pvi=5070309376; eas_sid=N1A556k2w0e8J6P6O4q0T4T3P5; ied_qq=o0425485346; tvfe_boss_uuid=7ba8bcfef0c3629e; ptui_loginuin=1455749561; ptisp=ctc; ied_rf=--; pgv_info=pgvReferrer=&ssid=s8420041655; pgv_si=s8177693696; _qpsvr_localtk=0.04313806156783717; IED_LOG_INFO2=userUin%3D425485346%26nickName%3D%2525E5%252587%2525AF%26userLoginTime%3D1565809041; uin=o1455749561; skey=@zkFSoFT8c; sl_login=1455749561%7C113%2E119%2E29%2E67%7C1565809055%7C0%7C1%7C0%5F0%7C0%5F0%7C0%5F0%7C0%5F0%7CD11C9EAE780401B4BEFB69D6AB8D64AA; sFrom=website; user_requests=f564a6d1c0ebaaf70e61a74b644918c2dcd3c934dec078c8f9e2495fc0d1e6d6c1356be163022d312a2cb0b40ceae03eb5faaa7f3aef8eeaa147675d3db052f61cd6ddb9241f366d421f888ec02c7d23; first_login=96553b56a8c37d9f103b3346d66ac9e1; user_hero=\"1460901026267770/%E7%9B%9B%E6%A8%B1/49/1/2/1/215384434/1/m10#1462153116005795/%E5%8F%B8%E9%A9%AC%E9%9B%AA%E8%96%87/45/2/2/1/215384437/1/m13#1462155970992765/%E5%BD%AD%E4%B8%89%E5%A8%98/45/1/2/1/215384435/1/m6#1462209042559505/%E5%87%8C%E9%94%A4/44/1/2/1/215384438/1/g10#1462252647564722/%E8%A3%B4%E9%86%89/44/2/2/1/215384439/1/m11#\"; user_game_info=b71170338337a72bf9521c83d1f15847f4f2867b9d2ac0050226beb479ddc2a50710316cee7e14bd075c708100fbd316f8dd1cfe5b36a3569cef521bf458bc6ded953bd5dae3eb75a01a0290381d3a7f9bb312b8f534c5c7d8e9e2187ed185122f616f5f7f5f855dfd9c5ba652696b902c86999deb9a12941d0fd95461031a244fd3d50df3360400bdd687cebb9b65e9b8d678a70cf0d5f96ddbdbaafc1ec68acff23a7cb6ec2bd490ef9db97097065925c5876d375c43c4146d0cb016b84b791a51080de93ceb7b7bc04bee0d4dd95f6af6ed8d35996fd432d0987c83bb12b2e04dbf63f9d8c6a9471eab34b593a3331d4aedf63bf53354b56aa78b6f14a39bdc08e179c2baf02c7c606577b3d3a57edbc8477f33dfb971f6b0aba65bb40bd36759a01815879bbaf1607c733a46a5b21bd30c2d6fa00081b4071af2ce1f043fa4f238de554d7b77bde6848fda5ea6ef08112c047b683fa1f202df8d27728a2aa03434b553c78ecce88db2718d9183d33daec1d94873caef5bb6e857efec89fae86816b73ee9890ec7f43f3d154bcd2babc9ac658b7a2891ef74fed9e8bc293af662d0ae365d088bea0dbd6f94fd171b8329d6d71f7bda8ac688d1560cea878d; user_info=\"%E4%BC%A4%E6%84%9F%E5%AF%92%E9%A3%8E/%E5%A4%A9%E5%AD%97%E7%9B%9F/394823/210/6541/37209/20/%E9%AA%A0%E9%AA%91%E5%B0%86%E5%86%9B/1455749561/1247324702873190/0/1\"; user_army=\"1/26807/pudaobing/1#2/0/tieqiangbing/1#3/0/jinweijun/1#4/0/jinjiawushi/1#5/24319/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/0/shenhuochongbing/2#9/27125/qingqibing/3#10/0/qishebing/3#11/0/zhongqibing/3#12/46258/hubenqishi/3#19/0/fangshi/4#33/0/tongshenyuzhe/4#20/0/shensuanxingguan/4#21/0/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"1455749561,1010319,120,building10\"; building007=\"list_trade.alc?cid=1010319\"; onlinetime=\"2019-08-15 02:57:52\"";
// 伤感寒风
            cid = "1010319";

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

                boolean fdx = true;
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
                boolean maoXian = false;
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
