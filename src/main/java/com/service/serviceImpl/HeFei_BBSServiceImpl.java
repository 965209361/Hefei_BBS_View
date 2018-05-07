package com.service.serviceImpl;

import com.dao.Hefei_bbsMapper;
import com.model.Hefei_bbs;
import com.service.HeFei_BBSService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class HeFei_BBSServiceImpl implements HeFei_BBSService {

    @Resource
    private Hefei_bbsMapper hefei_bbsMapper;


    private static Properties PropKit = HeFei_BBSServiceImpl.prop("hefei/hefeibbs.properties");

    @Override
    public void hefei_BBs() {
        ExecutorService pool = Executors.newFixedThreadPool(6);
        pool.execute(() -> {
            Document doc = null;
            Document docList = null;
            Elements elems = null;
            Elements elemsList = null;
            String listUrl = null;
            String title = null;
            String content = null;
            String fbsj = null;
            StringBuffer buffer = new StringBuffer("insert into hefei_bbs(listUrl,title,content,fbsj,comment,commentCount) values");
            String sql = null;
            try {
                doc = Jsoup.connect("http://bbs.hefei.cc/forum.php?mod=forumdisplay&fid=196&filter=author&orderby=dateline").get();
                elems = doc.select("tbody[id^=\"normalthread\"] .xst");
                List<String> listQuery = query();
                String listtoString = listQuery.toString();
                for (int k = 0; k < elems.size(); k++) {
                    listUrl = elems.get(k).attr("href");
                    title = elems.get(k).text();
                    if (listtoString.equals(title)) {
                        continue;
                    }
                    int nums = 0;
                    while (nums < 3) {
                        try {
                            docList = Jsoup.connect(listUrl).get();
                            break;
                        } catch (Exception e) {
                            nums++;
                        } finally {
                            while (nums == 3)
                                continue;
                        }
                    }
                    content = docList.select(".pcb").first().text();
                    fbsj = docList.select(".hm span:first-child").text().replace("发表于", "").replace("更新于", "");
                    elemsList = docList.select(".pcb");
                    List<String> list = new ArrayList<>();
                    if (fbsj == null || content == null || title == null) {
                        continue;
                    }
                    if (elemsList.size() == 1) {
                        if (k < elems.size() - 1) {
                            buffer.append(String.format("('%s','%s','%s','%s','%s',%s),", listUrl, title, content, fbsj, "空", 0));
                        } else {
                            buffer.append(String.format("('%s','%s','%s','%s','%s',%s);", listUrl, title, content, fbsj, "空", 0));
                        }
                    } else {
                        for (int i = 1; i < elemsList.size(); i++) {
                            list.add(i + " 楼:" + elemsList.get(i).text() + "\t||");
                        }
                        if (k < elems.size() - 1) {
                            buffer.append(String.format("('%s','%s','%s','%s','%s',%s),", listUrl, title, content, fbsj, list.toString(), elemsList.size()));
                        } else {
                            buffer.append(String.format("('%s','%s','%s','%s','%s',%s);", listUrl, title, content, fbsj, list.toString(), elemsList.size()));
                        }
                    }
                    list.clear();
                }
                String buffTo = buffer.toString();
                if (!buffTo.isEmpty()) {
                    buffTo.substring(buffTo.length() - 1, buffTo.length()).replaceAll("[,;]", ";");
                    int numInsert = insert(buffTo);
                    if (numInsert > 0) {
                        System.out.println(numInsert + "条数据存入成功");
                    } else {
                        System.out.println(numInsert + "条存入失败");
                    }
                }
                System.out.println("本次刷新结束!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(title + "IOE出错!!!!!!!!!!!!!!");
            } catch (NullPointerException e) {
                System.out.println(title + "NullPointer出错!!!!!!!!!!!!!!");
            }
        });
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Hefei_bbs> QueryNews(int page, int pageCount) {
        List<Hefei_bbs> list = new ArrayList<>();
        list = hefei_bbsMapper.QueryNews(page, pageCount);
        for (int i = 0; i < list.size(); i++) {
            String content = list.get(i).getContent();
            String listUrl = list.get(i).getListurl();
            String comment = list.get(i).getComment();
            if (content.length() > 15) {
                list.get(i).setContent(content.substring(0, 20));
            }
            list.get(i).setListurl(listUrl.substring(0, 21));
            if (comment.length() > 35) {
                list.get(i).setComment(comment.substring(0, 35));
            }
        }
        return list;
    }

    /*@Override
    public List<Hefei_bbs> QueryNews(int page, int pageCount) {
        String sql = String.format("SELECT * FROM `hefei_bbs` LIMIT %d,%d;", page, pageCount);
        PreparedStatement prep = null;
        ResultSet resultSet = null;
        List<Hefei_bbs> Domainlist = new ArrayList<>();
        try {
            prep = connection().prepareStatement(sql);
            resultSet = prep.executeQuery();
            while (resultSet.next()) {
                Hefei_bbs Hefei_bbs = new Hefei_bbs();
                Hefei_bbs.setId(resultSet.getInt("id"));
                Hefei_bbs.setListurl(resultSet.getString("listUrl"));
                Hefei_bbs.setTitle(resultSet.getString("title"));
                Hefei_bbs.setContent(resultSet.getString("content"));
                Hefei_bbs.setCommentcount(resultSet.getInt("commentCount"));
                Hefei_bbs.setComment(resultSet.getString("comment"));
                Hefei_bbs.setFbsj(resultSet.getString("fbsj"));
                Domainlist.add(Hefei_bbs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Domainlist;
    }*/

    /**
     * 新增语句 编辑语句
     *
     * @param sql
     * @return
     */
    public int insert(String sql) {
        PreparedStatement prep = null;
        int num = 0;
        try {
            prep = connection().prepareStatement(sql);
            num = prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 对数据库的id和网址进行查询
     *
     * @return
     */
    public List<String> query() {
        PreparedStatement prep = null;
        String sql = "SELECT title from hefei_bbs;";
        List<String> list = new ArrayList<>();
        try {
            prep = connection().prepareStatement(sql);
            ResultSet resultSet = prep.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 连接JDBC的Connection语句
     *
     * @return
     */
    public Connection connection() {
        Connection conn = null;
        try {
            Class.forName(PropKit.getProperty("driver"));
            conn = DriverManager.getConnection(PropKit.getProperty("url"), PropKit.getProperty("username"), PropKit.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 配置的连接
     */
    public static Properties prop(String name) {
        InputStream inputStream = HeFei_BBSServiceImpl.class.getClassLoader().getResourceAsStream(name);
        Properties prop = new Properties();
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * 查询总共有多少条数
     *
     * @return
     */
    @Override
    public Integer QueryPageCount() {
        int num = 0;
        num = hefei_bbsMapper.QueryPageCount();
        return num;
    }


}
