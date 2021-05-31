package org.jeecg;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.jeecg.modules.demo.mock.MockController;
import org.jeecg.modules.demo.test.entity.JeecgDemo;
import org.jeecg.modules.demo.test.mapper.JeecgDemoMapper;
import org.jeecg.modules.demo.test.service.IJeecgDemoService;
import org.jeecg.modules.gwb.service.*;
import org.jeecg.modules.system.service.ISysDataLogService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class SampleTest {

    @Resource
    private JeecgDemoMapper jeecgDemoMapper;

    @Resource
    private IJeecgDemoService jeecgDemoService;

    @Resource
    private ISysDataLogService sysDataLogService;

    @Resource
    private MockController mock;

    @Resource
    private IPtypeService ptypeService;

    @Resource
    private IHisPtpyeSyncService hisPtpyeSyncService;

    @Resource
    private IXwPPtypePriceService xwPPtypePriceService;

    @Autowired
    private ITGoodsStocksGlideService goodsStocksGlideService;

    @Autowired
    private IGoodsStocksService goodsStocksService;

    @Autowired
    private IDlySaleService dlySaleService;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<JeecgDemo> userList = jeecgDemoMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testXmlSql() {
        System.out.println(("----- selectAll method test ------"));
        List<JeecgDemo> userList = jeecgDemoMapper.getDemoByName("Sandy12");
        userList.forEach(System.out::println);
    }

    /**
     * 测试事务
     */
    @Test
    public void testTran() {
        jeecgDemoService.testTran();
    }

    // author:lvdandan-----date：20190315---for:添加数据日志测试----
    /**
     * 测试数据日志添加
     */
    @Test
    public void testDataLogSave() {
        System.out.println(("----- datalog test ------"));
        String tableName = "jeecg_demo";
        String dataId = "4028ef81550c1a7901550c1cd6e70001";
        String dataContent = mock.sysDataLogJson();
        sysDataLogService.addDataLog(tableName, dataId, dataContent);
    }

    // author:lvdandan-----date：20190315---for:添加数据日志测试----
    @Test
    public void testPtypeList() throws IOException {
        // Integer updateTag = hisPtpyeSyncService.selectMaxUpdateTag();
        ptypeService.syncSendPtypeInfData2Server();
    }

    @Test
    public void testPtypePriceList() throws IOException, ParseException {
        xwPPtypePriceService.syncPPtypePriceInfData2Server();

        // try {
        // xwPPtypePriceService.syncPPtypePriceInfData2Server(DateUtils.parseDate("2020-01-01",
        // "yyyy-MM-dd"));
        // } catch (IOException e) {
        // e.printStackTrace();
        // } catch (ParseException e) {
        // e.printStackTrace();
        // }
    }

    // @Test
    // public void testGoodsStock() {
    //
    // try {
    // this.goodsStocksService.syncGoodsStocksInfFromServer();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    //
    // }

    // public static void main(String[] args) {
    // String str = "<<<<<asad>> &a&& 'b' \"111";
    // System.out.println(XMLTransferUtil.transferXML(str));
    // }

    // @Test
    // public void testDlySaleInfSync() {
    // try {
    // dlySaleService.syncDlySaleInfFromServer();
    // } catch (IOException e) {
    // log.error("error:", e);
    // }
    // }
}
