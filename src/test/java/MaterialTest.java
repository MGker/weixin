import com.mg.weixin.Application;
import com.mg.weixin.bean.MaterialEntity;
import com.mg.weixin.rest.Result;
import com.mg.weixin.service.material.IMaterialService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;

/**
 * @Auther: fujian
 * @Date: 2018/9/20 16:55
 * @Description:
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class MaterialTest {

    @Resource
    IMaterialService iMaterialService;

    @Test
    public void testAdd(){
        iMaterialService.addNormalMaterial(MaterialEntity.Type.IMAGE,"f:/beautify1.jpg");
    }

    @Test
    public void getMater()throws Exception{
        String mediaId = "qgJi64OEZqIvOtmleI6bO5hLpgMS0M517w0YLBFJKkM";
        Result result = iMaterialService.getNormalMaterial(mediaId);
        Object data = result.getData();
        if(data instanceof InputStream){
            InputStream in = (InputStream) data;
            File downFile = new File("f:/down1");
            FileOutputStream fos = new FileOutputStream(downFile);
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len=in.read(buffer))>-1){
                fos.write(buffer,0,len);
            }

            System.out.println("保存完");
        }else{
            System.err.println(data);
        }
    }

    @Test
    @Transactional
    public void deleteMaterial(){
        String id= "qgJi64OEZqIvOtmleI6bO6zR_JlF0G7EqqOc9mLvT64";
        Result result = iMaterialService.deleteMaterial(id);
        System.out.println(result.getMessage());
    }

    @Test
    public void addVideo(){
        String path = "f:/test.mp4";
        String title = "新增视频";
        String description = "视频说明";
        iMaterialService.addVideoMaterial(title,description,path);
    }
}
