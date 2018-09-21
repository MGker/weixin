import com.alibaba.fastjson.JSONObject;
import com.mg.weixin.Application;
import com.mg.weixin.bean.WXMenuEntity;
import com.mg.weixin.dao.WXMenuDao;
import com.mg.weixin.service.wxmenu.WXMenuServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: fujian
 * @Date: 2018/9/11 18:31
 * @Description:
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class MenuTest {
    @Resource
    private WXMenuServiceImpl wxMenuImpl;
    @Resource
    private WXMenuDao wxMenuDao;
    @Test
    public void testMenu(){
        JSONObject json = wxMenuImpl.getMenuJson();
        System.out.println(json.toJSONString());
    }

    @Test
    public void getMenus(){
        List<WXMenuEntity> list =wxMenuDao.findAll();
        System.out.println(list.size());
    }

    @Test
    public void createMenu(){
        System.out.println("dfa");
    }
}
