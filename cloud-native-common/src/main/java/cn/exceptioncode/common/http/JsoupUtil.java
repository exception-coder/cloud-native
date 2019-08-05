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

@Slf4j
public class JsoupUtil {

    static LinkedHashMap<String, String> userIdMap = new LinkedHashMap<>(10);

    static {
        userIdMap.put("丿★阿英ㄨ", "455969164");
        userIdMap.put("丿凉丶◇风","425485346");
    }

    @SneakyThrows
    public static void main(String[] args) {

        Map<String, String> headers = new HashMap<>(1);
        String Cookie = "JSESSIONID=049764276E932EA37691D1910203649C; ied_rf=--; pgv_pvid=4472590744; pgv_info=pgvReferrer=&ssid=s2312268584; eas_sid=H1B5f6M5D0U2e6t380a3n5q6P1; _qpsvr_localtk=0.26140607177882247; uin=o0425485346; skey=@KevArWoEM; ptisp=ctc; RK=rMwQg7NZaK; ptcz=7cf34a537209db39a44ec7f460ddfd42a3c837e147ba446a78bbdeb870b44d97; IED_LOG_INFO2=userUin%3D425485346%26nickName%3D%2525E5%252587%2525AF%26userLoginTime%3D1565026304; sl_login=425485346%7C61%2E140%2E247%2E194%7C1565026307%7C0%7C1%7C2%5F8%7C0%5F0%7C1%5F5%7C0%5F0%7C6520800264C71C70577C016D0A602916; sFrom=website; onlinetime=\"2019-08-06 01:31:47\"; user_requests=9f651abddaddc29636872d03d7ac64453c1672e7d13ae9f36dd88856d630e45cabbcf1ec4a2d5e817aa411d960e7c44200fca13be0d5dc1afcc843331ba8bf12b50e9913c224efb7; first_login=f8c29e64a1d51f784d25d5809880a15a; user_hero=\"1302095433049915/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A2/190/1/2/1/215344095/1/g9#1302016221118280/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A1/177/1/2/1/215344095/1/g12#1289924374531220/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A0/154/1/2/1/215344095/1/m6#1302093755321980/%E9%98%B2%E5%BE%A1%E4%B8%80/130/2/1/1/6/1/m2#1315003310221209/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A4/121/1/1/1/6/1/m7#\"; user_game_info=8562458da0eeef5e425d9755efbfe52a229917b8b5868a646087ad7f12465f72c062184ae9f786ad9f7b9168add54160fb8ebbf64f9f0a1afd92bf74de6c1692c7cc76a7268767516f5f92fe7d702763485c00bb60a9ccbd682abd86f3c355b341a9ba8c38b2b3421220c7769f1dd1b4b8cc3dbe286c7e595f57b1d5a64ed4e1424fa4d890b1122f2994b47b0e945fe986c544435d2a2914a89763682fb6f584e93a0f4453c63fa23a3b43debf19c2f6c470546cc937bc8d739db07d8b126663dfc4c964996ed66c7e4cc680bf062d3895c0a4eddcf0ab15a4bb6ad2fbda8d3f63c0524a3efb7d64ccf8f59e4c3cfb588b5a4dfe3ff28103ba34942fce6ff9ff0ce13c082e761197218498e93473645d24507920ebeadeef2b2ee13a200570335e883d0f90651459a608ec2e2d182f68ad7da85eb2cb59175eec5334a66db609dac14e5369cc4ae8c76c971950fabf25443f759f99aa8bf8fcbbe935a93a838a5c7c50bc1637b52e35adb43526b059810507dffdb790c741c1ff23c85f0b87f62406f40202b17a1ebd8d3e9bf91cd895e7d43d46011bb4bbcf28eb29f1c88ab35c33c27a5bf44c26b0ae2192f244e298702c58cb04714de5910318a7c7ca2f5fa37d6b4607de2acfee01c6861968cda3; user_info=\"%E4%B8%BF%E5%87%89%E4%B8%B6%E2%97%87%E9%A3%8E/%E5%B9%BF%E5%B7%9E%E8%81%94%E7%9B%9F/31752787/2/4317/70392/23/%E8%AF%B8%E4%BE%AF%E7%8E%8B/425485346/1244254858510599/1/1\"; user_army=\"1/13614403/pudaobing/1#2/0/tieqiangbing/1#3/456000/jinweijun/1#4/7671121/jinjiawushi/1#5/13260148/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/6632940/shenhuochongbing/2#9/13752292/qingqibing/3#10/0/qishebing/3#11/860216/zhongqibing/3#12/8766543/hubenqishi/3#19/4375/fangshi/4#33/549398/tongshenyuzhe/4#20/681587/shensuanxingguan/4#21/91973/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"425485346,767076,120,building10\"; building007=\"list_trade.alc?cid=767076\"";
        // 天涯
        String cid = "140183";
        // 凉风
        cid = "767076";
        // 寒风
//        cid = "848100";
        headers.put("Cookie", Cookie);
//        zhenChaUser(headers,"920419363","6","-493",cid);
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
