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
        String Cookie = "JSESSIONID=212393610725FF325134F91F450BDEC0; ied_rf=--; pgv_pvid=8132209856; pgv_info=pgvReferrer=&ssid=s1121101888; eas_sid=F1L5o6d4E933O5213324Y0l9J8; _qpsvr_localtk=0.3468345091520648; ptisp=ctc; ptui_loginuin=425485346; uin=o0425485346; skey=@4UsCe83QN; RK=JMwY17NJaq; ptcz=4cc6c9b4cb7dc52c2b1c02905ac71c93538eeb384b0837ae1cbecbf0d2de03e6; IED_LOG_INFO2=userUin%3D425485346%26nickName%3D%2525E5%252587%2525AF%26userLoginTime%3D1564935140; sl_login=425485346%7C61%2E140%2E246%2E254%7C1564935143%7C0%7C1%7C2%5F8%7C0%5F0%7C1%5F5%7C0%5F0%7C2EA376627874FAC2FA2FF427858973B5; sFrom=website; user_requests=a56037e44b3d163900b6ea12b9c538b112f538285f17f1f045e37ed814d592d3e816657b6baef2f4a7bcf6272d9706eefca0b66a1f9a0461ffbc59ec1e11841a0ebe3c7990cdf34a; first_login=f8c29e64a1d51f784d25d5809880a15a; user_hero=\"1302095433049915/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A2/190/1/1/1/6/0/g9#1302016221118280/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A1/177/1/1/1/6/0/g12#1289924374531220/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A0/154/1/1/1/6/0/m6#1302093755321980/%E9%98%B2%E5%BE%A1%E4%B8%80/130/2/1/1/6/0/m2#1315003310221209/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A4/121/1/1/1/6/0/m7#\"; user_game_info=58bd3c0e390b4db52759993096319effd4447915f31948ac0bb1689c6f8e42bf1fd88dd53247cc37e62895733980c0f8e5b3911ab4988ce97ec75fe6103823f592b9b428ba1e30bd5298ae7f4cf37df24854e60313feeedae08e4a2b106744060a43448a0760aa0b2188c090a52fe72509a66d5a73a3d8d941ee70db33a7a7e50bb8308c53e40b181136f1ce627b87ca5a2d2a92761f71572b0d1722f6852d16ac3d2857933afe77d2b8416873d5f0c6d3e0c6d3a740a387d36b6cadf94129ba8c55839cffbdce847bb6f07d98b31d4e57c700f420cd5594076623bdd1cf89ebca16cd8b0ad181053a9105ee54475b51f45cc17927db025beb1b1f60bdbff59c0c29046027f1b11aeed73924efc6d7df0eb41dba67cf1711941d25f585f23e59acb558eec92076885ce3a3b44f22bd1a6ce596196f2b382d95d5266545e27f66922d756d1bf67eece366785a2b0da073c4bdb2cbb97dde33464457870bdf3fd1ac33bef0c71974a6cb8bff0923c1c962536af55943465beccd267ec1f9d238f0e05582407cfe540d896f773f8336aa3bbc65bbb38a34c852d17cff337d2d5e4b157a8960921b508dab36e36a85a8f736ac85439cb326b82cab85e8cddab8b5a096f2cf53c82b28c67247555101d7e46b; user_info=\"%E4%B8%BF%E5%87%89%E4%B8%B6%E2%97%87%E9%A3%8E/%E5%B9%BF%E5%B7%9E%E8%81%94%E7%9B%9F/31710903/2/4317/70292/23/%E8%AF%B8%E4%BE%AF%E7%8E%8B/425485346/1244254858510599/1/1\"; user_army=\"1/13614403/pudaobing/1#2/0/tieqiangbing/1#3/456000/jinweijun/1#4/7666049/jinjiawushi/1#5/13254465/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/6700893/shenhuochongbing/2#9/13682702/qingqibing/3#10/0/qishebing/3#11/824216/zhongqibing/3#12/8874876/hubenqishi/3#19/4375/fangshi/4#33/549398/tongshenyuzhe/4#20/677684/shensuanxingguan/4#21/90192/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"425485346,767076,120,building10\"; building007=\"list_trade.alc?cid=767076\"; onlinetime=\"2019-08-05 00:12:24\"";
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
