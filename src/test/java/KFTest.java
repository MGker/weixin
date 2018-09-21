import com.mg.weixin.Application;
import com.mg.weixin.bean.KFAccountEntity;
import com.mg.weixin.service.agent.IKFService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: fujian
 * @Date: 2018/9/17 09:05
 * @Description:
 */
@SpringBootTest(classes=Application.class)
@RunWith(SpringRunner.class)
public class KFTest {
    @Resource
    IKFService ikfService;
    @Test
    public void addKF(){
        KFAccountEntity kf = new KFAccountEntity();
        kf.setAccountName("test1@test");
        kf.setNickname("客服1");
        kf.setPassword("1234");
        ikfService.addKFAccount(kf);
    }

    @Test
    public void getKF(){
        List<KFAccountEntity> kflist = ikfService.getAllRemoteKF();
        for(KFAccountEntity kf:kflist){
            System.out.println("客服:"+kf.getAccountName());
        }
    }

}
