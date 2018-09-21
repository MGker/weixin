import com.mg.weixin.Application;
import com.mg.weixin.bean.AccessTokenEntity;
import com.mg.weixin.dao.AccessTokenDao;
import com.mg.weixin.rest.WeiXinConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Auther: fujian
 * @Date: 2018/9/9 12:09
 * @Description:
 */
@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
public class AessTokenTest {
    @Resource
    private AccessTokenDao accessTokenDao;

    @Resource
    private WeiXinConfig weiXinConfig;

    @Test
    public void insertAT(){
        AccessTokenEntity accessTokenEntity =new AccessTokenEntity();
        accessTokenEntity.setAvailable(1);
        accessTokenEntity.setCreateTime(new Date());
        accessTokenEntity.setExpireIn(7200);
        accessTokenEntity.setId(1);
        accessTokenEntity.setAccessToken("ceshi");
        accessTokenDao.save(accessTokenEntity);
    }

    @Test
    public void getLastAccesstoken(){
        AccessTokenEntity accessTokenEntity = weiXinConfig.getAccessTokenEntity();
        System.out.println(accessTokenEntity);
    }
}
