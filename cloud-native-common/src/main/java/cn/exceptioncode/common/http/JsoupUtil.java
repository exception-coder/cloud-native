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
//        service.scheduleAtFixedRate(runnable, 0, 6, TimeUnit.SECONDS);

    }


    public static void rc() throws Exception {
        {

            Map<String, String> headers = new HashMap<>(1);
            // 凉风
            String Cookie = "JSESSIONID=B8746F356020E7FED6008D50A60AAAD2; ied_rf=--; pgv_pvid=6658365906; pgv_info=pgvReferrer=&ssid=s9803666745; eas_sid=S1x55607c1u8E56282i5Q1C3D6; _qpsvr_localtk=0.860631263398284; pgv_pvi=189635584; pgv_si=s9149224960; uin=o0425485346; skey=@jMllxNW12; ptisp=ctc; RK=QMwY17NYbK; ptcz=78e6b5d6d828f2767f5b41ded4e7bb99c11e2f05f93c7647a61b8454223a2eaa; IED_LOG_INFO2=userUin%3D425485346%26nickName%3D%2525E5%252587%2525AF%26userLoginTime%3D1567185227; sl_login=425485346%7C61%2E140%2E245%2E61%7C1567185231%7C0%7C1%7C2%5F9%7C0%5F0%7C2%5F6%7C0%5F0%7C7F2344077CAA3C8D4C29DE99107F412C; sFrom=website; user_requests=d396f2662457d904ec8f5034c152c04ca856c82255169b789f32634943707c40c7fe5befd4874173115d22a56be3ac1713e0402ebc957923bba5276605e88bc5b826c9107461995f; first_login=f8c29e64a1d51f784d25d5809880a15a; user_hero=\"1302095433049915/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A2/190/1/1/1/6/0/g9#1302016221118280/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A1/178/1/1/1/6/0/g12#1289924374531220/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A0/155/1/1/1/6/0/m6#1302093755321980/%E9%98%B2%E5%BE%A1%E4%B8%80/132/2/1/1/6/0/m2#1315003310221209/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A4/124/1/1/1/6/0/m7#\"; user_game_info=bf59b3f6233a2e6a5f36a7f2b92b8b302cb42ffcb861a3c5ea24bd6cd79e7192bef72b2808cbe5702be977f592da65c8fd3dd4816764cd7a039dbcd97e3f5967ff97ac9b521ec5b40ed6a1fe8552cb20a659286403ea6c031d357bc0d432c4f2b9bd7ba7013af1a9425cfec64d37572e874576b25518605b81c914c20ebad9fdfb556a442c031395abf9112f526aa36aaf00e3b86c26d398bb5994b7d4532b211af338afed4521cb47d2bcfba6bb3d32ac2c9618f72cefd3184cad86a5cc4d20de828603a57240e2ba8ae6db1d27547c7ce7c268926f343b0fd4fca32f7a085a627cbb62460a28ac6c66304a4768e1c5a62d590b7881740fac312bda0c0489bf58ed5c82a10b8c745efb0ea49d50096ee4803ebc46bdda4c0271a22707bc342553f0101668167c8acdb17ee7d95383afc879b6b2735acefd95d52bb956cac51b306d686beb28eb07498787d4879830726f9a09b31fb44205eab6c3f7d073b19d3eb91f5ba4995c950547a8eca3702febf586d87eed1505f22c845efdbc38fbb05692549ca554c0e09837e69feb2c797575986e9423559464932cc2188613ee0fae237836d73c7998150ea6ca632beaf2f184dd5eea7ff69d257b69fa17e4a220c69cc576c872613b; user_info=\"%E4%B8%BF%E5%87%89%E4%B8%B6%E2%97%87%E9%A3%8E/%E5%8D%97%E9%9C%B8%E5%A4%A9/31956045/2/0/72972/23/%E8%AF%B8%E4%BE%AF%E7%8E%8B/425485346/1258725036850614/1/1\"; user_army=\"1/0/pudaobing/1#2/0/tieqiangbing/1#3/0/jinweijun/1#4/12415/jinjiawushi/1#5/0/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/13146/shenhuochongbing/2#9/1672505/qingqibing/3#10/0/qishebing/3#11/0/zhongqibing/3#12/750000/hubenqishi/3#19/0/fangshi/4#33/0/tongshenyuzhe/4#20/5809/shensuanxingguan/4#21/0/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"425485346,767076,120,building10\"; building007=\"list_trade.alc?cid=767076\"; onlinetime=\"2019-08-31 01:13:51\"";
            // 天涯
            String cid = "140183";
            // 凉风
            cid = "767076";
            // 寒风
//            Cookie = "JSESSIONID=E7C481A42735B94A158CCB43A815DDAD; eas_sid=l1B5B6J623M446m639R581B1O8; _qpsvr_localtk=0.34646638083422654; pgv_pvi=3871621120; pgv_si=s4984505344; ptisp=ctc; ptui_loginuin=862784951; uin=o0862784951; skey=@1Rr6NUPpB; RK=Aewsu2PdaG; ptcz=4f649b3e77a3be5d582a034eda0d4c4c8748f4e8dc5f3a227e97b387967dd686; IED_LOG_INFO2=userUin%3D862784951%26nickName%3D%2525E5%252587%2525AF%26userLoginTime%3D1566346713; sl_login=862784951%7C61%2E140%2E245%2E190%7C1566346716%7C0%7C1%7C0%5F0%7C0%5F0%7C0%5F0%7C0%5F0%7C9015A909F2BC294483F530984F305AE5; sFrom=website; user_requests=26ec17fca66209083185cdabd7e60e9f8ee74fd8ac0eaa1e0cf68f913aabb8b905acb05d4f52901b2531b2555e149e17933b8a716af5e89a72105840e3daf7ab6ca1f9f0115da54a; first_login=f973e14f26186112fe496e08f87ca061; user_hero=\"1534037917107690/%E5%B1%A0%E7%AE%AD/50/2/2/1/848100/0/g5#1534128042906619/%E5%B8%B8%E5%A4%A7%E5%A8%98/50/1/2/1/848100/0/m14#1534184883918714/%E8%91%A3%E5%BF%83/50/1/2/1/848100/0/g4#1534270972705411/%E9%9C%8D%E4%B8%80%E5%88%80/50/2/2/1/848100/0/g12#1534705866328392/%E6%A8%8A%E9%9D%92/50/2/2/1/848100/0/m12#\"; user_game_info=0974a6fc25e6da10caccbd5fa7bf4f045764fa03e46cecdcd4ef0849a5e23fca35cabd5f97db146ade8bf0f86b6fa8be0f6dfe8f1933add8b4f2a3a58d4793f788b83f94b5ec35e4edae6caab3996c56ebdef237c7505a83a21ef96c36e577699b837d2a1e3f6b9b99a662bf36812924cff0d511595d10f18c86ac346dbfdc3d73bcbe56e66725f306774052edaa65574b12e50fe2bcb9dd69b92d766da110e1d48c9b3368a72bc942df88760c2acc5989a7c8421fc58a97065394d772a601f8f3a9ba6ef3faa72c94cf82d9eb7dd77ec67188c20803de7f1cab9e64e6474070bab5825d2f2917eb9a8076287f73438c0600c8059997e652ab0fc8c533aa0e7cf90931f498d5b0601ff19a22bead8390fa92484783f208be354e31e474b7cfcc4986b6e7c3cc213d9a363d0de5ec9b8ba48a2acffb85ab0dd056729f8cd00b20f3cb0df733998435c348f6bec7ef54db7684d0452282ad09c1de141c018eb2107123791f28a3429dc2e27af9dd3f938e87614e4a1fcc350a1b56c4468e34638086e937e65c02ac5571a383b24aa1d0a194a9803f34128ce8592d0346e983b35f6bddfc335038a43df6ed14d08e76fbc9695088f6ba4df8a5; user_info=\"%E5%AF%92%E4%B8%BF%E9%A3%8E/%E5%B9%BF%E5%B7%9E%E8%81%94%E7%9B%9F/4356481/25/0/65585/22/%E4%B8%9E%E7%9B%B8/862784951/1244254858510599/1/1\"; user_army=\"1/8/pudaobing/1#2/0/tieqiangbing/1#3/0/jinweijun/1#4/0/jinjiawushi/1#5/9/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/0/shenhuochongbing/2#9/7/qingqibing/3#10/0/qishebing/3#11/0/zhongqibing/3#12/0/hubenqishi/3#19/0/fangshi/4#33/0/tongshenyuzhe/4#20/2/shensuanxingguan/4#21/0/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"862784951,848100,120,building9\"; building007=\"list_trade.alc?cid=848100\"; onlinetime=\"2019-08-21 08:23:07\"";
            // 寒风
//        cid = "848100";

//        zhenChaUser(headers,"920419363","6","-493",cid);
//        paiQianZiYuan(headers,"425485346","-379","284",cid);


            // 伤感寒风
            Cookie = "JSESSIONID=5E4A2501ACCBCDD6D0893486FDAA1675; ied_rf=--; pgv_pvid=2088586200; pgv_info=pgvReferrer=&ssid=s3866717554; eas_sid=K1g5W6o7Q2r6M7q672q5Q2T8A8; _qpsvr_localtk=0.20275611762781054; pgv_pvi=3457022976; pgv_si=s4782522368; ptisp=ctc; ptui_loginuin=1455749561; uin=o1455749561; skey=@cyBBy72gG; RK=QtStG7z8PF; ptcz=f4f8d92bb0d2ffce2bd7b890e91824fc407e06f992cdaf81a8ab7f1ff242eac1; IED_LOG_INFO2=userUin%3D1455749561%26nickName%3D%252B%26userLoginTime%3D1567267631; sl_login=1455749561%7C61%2E140%2E245%2E61%7C1567267635%7C0%7C1%7C0%5F0%7C0%5F0%7C0%5F0%7C0%5F0%7C3C837CBB54DD8D343A4CC87029375B30; sFrom=website; user_requests=c8e6322ed96bf25fb9e8b67fc70a571c307a9aca3199137e90b6454b05f94ad13566cde09797fa6890b1094674ba86e1762f8ab35067052950d932663eca5493db5fcb48d0d578db8f456d65c9dadc92; first_login=96553b56a8c37d9f103b3346d66ac9e1; user_hero=\"1462155970992765/%E5%BD%AD%E4%B8%89%E5%A8%98/52/1/1/1/3/1/m6#1462209042559505/%E5%87%8C%E9%94%A4/52/1/1/1/3/1/g10#1462252647564722/%E8%A3%B4%E9%86%89/52/2/1/1/3/1/m11#1456350907730935/%E4%BE%AF%E4%BA%8C%E5%A7%91%E5%A8%98/50/2/1/1/3/0/m10#1409259947578708/%E5%A4%A9%E5%85%B5%E5%A4%A9%E5%B0%865/46/2/1/1/3/1/g12#\"; user_game_info=d8466acb7883d5777ac2c8202a6842b6e84c27d8fe5fc56d138cb6819444f38ba321946a95a6c581fbf2c1539c7b339cbf979d2290054408a4694fc8e9aa83fbdda2a4d8467092703177cf35c879a7c43be887eafd1eca24057ad06fcbc88bac2f43180b3958a4f5218546425081895df6fa6c7626e862f667bb3b92ff00d7e49e3657dc5c67e3c6d699a7af0dfac1dfdaf9059792b18002a878d067d9454d887d5fde2efca66d9e073721fd5954161b7d17a8e5e2d60b5b01b33f68b1455b9647514ed1cb279ef09610fb066fe67a333cda95060b8973a351648141aa24a5fe6d39c7d35bc51624b8179b487100766b2fca4862ac2ab1205d3911bd7fc8e93d9973f41c306b5517fa52bc0060738f88ee418ce9ff064f0bc3b0a3f6fe349d192f615ddabadc39561b91ccff33591797c633ea382d7f26a6f4338ee23af8f0007f8b748b5a40b9b3a4e2b41e0ebf15107ec19a1b5607f6f523dbf97d6a264c0e8dd7f738278e985b20157e7f81f44c3af90d7df8446f41149b309453bceb1386bb1cbfbe8e4ec9dc8120200d38197e3fb1abf28e2d22240b20cf316820af4b9b81032b79e5282a2395955e9a2dc781ec09f7a49f6bec9ef8; user_info=\"%E4%BC%A4%E6%84%9F%E5%AF%92%E9%A3%8E/%E5%A4%A9%E5%AD%97%E7%9B%9F/304307/262/0/37498/20/%E9%AA%A0%E9%AA%91%E5%B0%86%E5%86%9B/1455749561/1247324702873190/0/1\"; user_army=\"1/35831/pudaobing/1#2/0/tieqiangbing/1#3/0/jinweijun/1#4/0/jinjiawushi/1#5/24360/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/21337/shenhuochongbing/2#9/72732/qingqibing/3#10/0/qishebing/3#11/0/zhongqibing/3#12/65368/hubenqishi/3#19/0/fangshi/4#33/0/tongshenyuzhe/4#20/0/shensuanxingguan/4#21/0/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"1455749561,1010319,120,building10\"; building007=\"list_trade.alc?cid=1010319\"; onlinetime=\"2019-09-01 00:07:18\"";
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
                create_mission_room(s, headers, "15104", "1504", "6");
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

        form.put("firstHeroId", firstHeroId);
//        form.put("secondHeroId", "0");
//        form.put("thirdHeroId", "0");
        form.put("queryType", "1");

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
