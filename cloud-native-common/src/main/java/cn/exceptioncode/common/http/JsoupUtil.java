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
        String Cookie = "JSESSIONID=E9E1374A4AFB4B19E5B94C80690E1E08; JSESSIONID=F6E6DEAD6FCB68A8A93973A02D6272BA; onlinetime=\"2019-08-0601:26:58\"; user_requests=2a80c7f010fdfd18786c2e0e2950cfc9761110ab106825791af46408a7c140d461a98ddf08d3e4333cf9f748133367885417b0e2153bc4ce76f818c77387810965c22d8f936c0c9f; first_login=f8c29e64a1d51f784d25d5809880a15a; user_hero=\"1302095433049915/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A2/190/1/2/1/767076/0/g9#1302016221118280/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A1/177/1/2/1/767076/0/g12#1289924374531220/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A0/154/1/2/1/767076/0/m6#1302093755321980/%E9%98%B2%E5%BE%A1%E4%B8%80/130/2/1/1/6/1/m2#1315003310221209/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A4/121/1/1/1/6/1/m7#\"; user_info=\"%E4%B8%BF%E5%87%89%E4%B8%B6%E2%97%87%E9%A3%8E/%E5%B9%BF%E5%B7%9E%E8%81%94%E7%9B%9F/31752787/2/4317/70392/23/%E8%AF%B8%E4%BE%AF%E7%8E%8B/425485346/1244254858510599/1/1\"; user_army=\"1/13614403/pudaobing/1#2/0/tieqiangbing/1#3/456000/jinweijun/1#4/7671121/jinjiawushi/1#5/13260148/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/6705107/shenhuochongbing/2#9/13752292/qingqibing/3#10/0/qishebing/3#11/860216/zhongqibing/3#12/8910876/hubenqishi/3#19/4375/fangshi/4#33/549398/tongshenyuzhe/4#20/681587/shensuanxingguan/4#21/91973/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"425485346,767076,120,building10\"; building007=767076\"; sl_login=425485346%7C61%2E140%2E247%2E194%7C1565026019%7C0%7C1%7C2%5F8%7C0%5F0%7C1%5F5%7C0%5F0%7CDAEAD73DA6E1BBA75DACBF995F60D90B; sFrom=website; uin=o0425485346; skey=@KevArWoEM; pt_recent_uins=2ad60b4e2fa1bbaffcb48570fd512f8e84d6cda81e4b39848fbb3247c8575b306bff76ac8bba58fc1d9e7af9279b9f6efb9856a7f33da926; ptisp=ctc; RK=rMwQg7NZaK; ptcz=d2db1db294b32d86aa47aee14b396b628d3f5da14d08a6a1f90248d84bd0c67e; ptui_loginuin=425485346; pt_user_id=16051747300740860543; pt_login_sig=sBJPPKP9kXviSwD3uHfFC*vyR4taasskJ9k7vVFb4HM4Wr58eH5AbdVs9cpDbYjk; pt_clientip=37163d8cf7c26395; pt_serverip=e0316474160c7428; ptui_identifier=000E012E1C5F5FC51D5C36C7B2BFCC777B64DEC0F8E28F665BF6D30AD7; user_game_info=2f1009c06d6b46a6458da7cfabe0f47f7c028283c4e59aeceabd084eed87f331781549e77f98e0d67b3fd4d03a4bcf0cf4300fdb11f973b971e52059492825d9909cdffe3b80dcea7d84aa77c21fcaad839f3fcfd0fa78a1598e1b71ede154c8ac1d2f678b41ed80e011276084b26f94f03523ba7ebc6d6052bc8e01afac7a66e618eeabccb549026fdfd5384b98415531c34ed0f00cbc100696c671bddd13e001d8ce091c2a7ad8b5b6ffbe6f4a6089816b609143dca81491ed6eea6ab875efdd9ae297148b6cea8e97ea3c35cdecd35a3eceab006b0f0fdc584e11d42b9606602f7e2fe02e1443385743f8e0fe5db41c341482df3b6496e861328aeba5728ccaad30bed9043878c583a1853ec6610b94c51dbd11f3945f5441c69e82740ccb91cf5daa6ed6a2240a96d449ad9dc9fb507eaf436d61e8440fefde22d07dcd0a26b506da46f0dc0f192e4f94554c7aebd4c02c4b7f388b23607490255dc0f488725d5a31156d19ea1f3d99f765242ad9b27ca917870d8621b5dca29fd057a9a92e8d72247f28d8037e8229e3e68eb2dbdec3c2c8c2ceef01c2fc20a04f6f595539df0eaaf1c762204942792e78a9e45076faaecf1ab32c69141ca0915f03c5985153cf0b07bbe5a96b999395e93a95c7; pgv_pvid=6489527440; euin_cookie=6B70FF9DDBEC5FC81336530ECFAD928CB0D89EAA2B9F2CA3; ied_qq=o0425485346; pgv_pvi=5730516992; uin_cookie=862784951; eas_sid=Q1u5W6B3q5P399F8q0G6S499Z4; JSESSIONID=F6E6DEAD6FCB68A8A93973A02D6272BA; onlinetime=\"2019-08-07 00:48:04\"; user_requests=2a80c7f010fdfd18786c2e0e2950cfc9761110ab106825791af46408a7c140d461a98ddf08d3e4333cf9f748133367885417b0e2153bc4ce76f818c77387810965c22d8f936c0c9f; first_login=f8c29e64a1d51f784d25d5809880a15a; user_hero=\"1302095433049915/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A2/190/1/1/1/6/0/g9#1302016221118280/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A1/177/1/1/1/6/0/g12#1289924374531220/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A0/154/1/1/1/6/0/m6#1302093755321980/%E9%98%B2%E5%BE%A1%E4%B8%80/130/2/1/1/6/0/m2#1315003310221209/%E6%94%BB%E5%87%BB%E6%89%8B%E2%91%A4/121/1/1/1/6/0/m7#\"; user_info=\"%E4%B8%BF%E5%87%89%E4%B8%B6%E2%97%87%E9%A3%8E/%E5%B9%BF%E5%B7%9E%E8%81%94%E7%9B%9F/31776580/2/4317/70430/23/%E8%AF%B8%E4%BE%AF%E7%8E%8B/425485346/1244254858510599/1/1\"; user_army=\"1/13614403/pudaobing/1#2/0/tieqiangbing/1#3/456000/jinweijun/1#4/7675566/jinjiawushi/1#5/13266465/duangongbing/2#6/0/changgongbing/2#7/0/shengongbing/2#8/6708545/shenhuochongbing/2#9/13787536/qingqibing/3#10/0/qishebing/3#11/860216/zhongqibing/3#12/8910876/hubenqishi/3#19/4375/fangshi/4#33/549398/tongshenyuzhe/4#20/681587/shensuanxingguan/4#21/95655/bingyinyangjia/4#\"; building002=view_union_info.alc; building003=\"425485346,767076,120,building10\"; building007=\"list_trade.alc?cid=767076\"; sl_login=425485346%7C61%2E140%2E247%2E194%7C1565026019%7C0%7C1%7C2%5F8%7C0%5F0%7C1%5F5%7C0%5F0%7CDAEAD73DA6E1BBA75DACBF995F60D90B; sFrom=website; uin=o0425485346; skey=@KevArWoEM; pt_recent_uins=2ad60b4e2fa1bbaffcb48570fd512f8e84d6cda81e4b39848fbb3247c8575b306bff76ac8bba58fc1d9e7af9279b9f6efb9856a7f33da926; ptisp=ctc; RK=rMwQg7NZaK; ptcz=d2db1db294b32d86aa47aee14b396b628d3f5da14d08a6a1f90248d84bd0c67e; ptui_loginuin=425485346; pt_user_id=16051747300740860543; pt_login_sig=sBJPPKP9kXviSwD3uHfFC*vyR4taasskJ9k7vVFb4HM4Wr58eH5AbdVs9cpDbYjk; pt_clientip=37163d8cf7c26395; pt_serverip=e0316474160c7428; ptui_identifier=000E012E1C5F5FC51D5C36C7B2BFCC777B64DEC0F8E28F665BF6D30AD7; user_game_info=dc99741ae115c55e16ba816b3963f7665c5d101da7555f72c465d9b1c61a7cf90d3145782690a581341065c52153cc3c9d085ef51d3409b9f52d1e1199b002ba863bae9eadd10bea1a78719884840dc23faa42d5a9d7cf5ab5967c5ac4d2abd37850a1bb618b92a65f27c9ff0010d4eba6cbac8260cd3601a40e86d4aa95628d60c15db7fac225dc18105d6c0c1beb3685e2e54c271b9652a05005dd194357e916d2d2febfb3ec564dade0fc3e5e91f437e3f1b3f4a6a1f6ff51337e3122aec9f868ae62edc9e117b8c6a15b486b652007fba72b305a69c7a438b21910a8df0d130ff8c124869d83b4967045e7b91d84939bda5f964765a2b813381a2d8807da435c64baa841743fe2932d046d08710926725e632d60db5849904f820c780ce06beee7b2ee039ab4df5906d1a47748b969875d56a7692cc329bb188d6d5c111de4cf657e6e084d9cb938f511d261c1e700582560053c6a555e1ed61686be04c36e4bd3ecceb422d5d020df1606e7ef9556e751693a18ee277a42e01e01ca011262a2857a90e4e4acc1caadbc51ec099a8aba7adb7231542ea120b60c76526286fc548dcf715f76f9653e285c61fbfd001fc857d457dc326ec097d62e387de38cc9efa9eac63f2d3a20cbdff85d06d09c; chuanhao_check_1565107443089=931e8dd9a257cc3ae804db612c0fc982";
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
