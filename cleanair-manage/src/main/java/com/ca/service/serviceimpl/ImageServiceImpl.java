package com.ca.service.serviceimpl;

import com.ca.service.ImageService;
import com.ca.vo.ImageVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@PropertySource(value = "classpath:/properties/image.properties",encoding = "UTF-8") //读取指定的配置文件
public class ImageServiceImpl implements ImageService {
    //定义文件存储的根目录

    @Value("${image.fileLocalDir}")
    private String fileLocalDir;
    @Value("${image.urlPath}")
    private String urlPath;



    private static Set<String>  typeSet = new HashSet<>();
    static {
        typeSet.add(".jpg");
        typeSet.add(".png");
        typeSet.add(".gif");
    }

    /**
     * 注意事项:
     *      1.校验是否为图片类型   xxx.jpg|png|jpeg|gif.....
     *      2.校验是否为恶意程序   宽度/高度
     *      3.采用分目录方式进行数据的存储  1.hash方式  2.时间单位 yyyy/MM/dd/
     *      4.防止文件重名....  UUID.jpg
     * @param
     * @return
     */

    @Override
    public ImageVO upload(MultipartFile images){
        //1.获取图片文件名称  a.jpg  A.JPG
        //123.jpg
        String fileName = images.getOriginalFilename();
        //全部小写.
        fileName = fileName.toLowerCase();
        //2.获取图片的类型
        int index = fileName.lastIndexOf(".");
        //.jpg
        String fileType = fileName.substring(index);
        if(!typeSet.contains(fileType)){
            //结束任务
            return ImageVO.fail();
        }

        //问题2: 防止恶意程序的攻击  图片 宽度和高度
        try {
            //获取图片对象类型
            BufferedImage bufferedImage =
                    ImageIO.read(images.getInputStream());
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            if(width ==0 || height == 0){
                return ImageVO.fail();
            }

            /**
             * 三: 分目录存储 以时间为维度截串 /yyyy/MM/dd/
             */
            String dateDir =
                    new SimpleDateFormat("/yyyy/MM/dd/")
                            .format(new Date());
            String fireDir = fileLocalDir + dateDir;
            File imageFileDir = new File(fireDir);
            if(!imageFileDir.exists()){
                //动态生成文件目录
                imageFileDir.mkdirs();
            }

            /**
             * 四: 防止文件重名,动态生成文件名称 uuid.jpg
             * uuid 32位16进制数  (2^4)^32= 2^128
             */
            String uuid = UUID.randomUUID()
                    .toString().replace("-", "");
            String realFileName = uuid + fileType; //uuid.jpg
            //yyyy/MM/dd/uuid.jpg
            File realFile = new File(fireDir+realFileName);
            images.transferTo(realFile);

            //如果程序一切正常
            //磁盘地址: F:\qk\images + dateDir + realFileName;
            // http://image.qk.com
            String url = urlPath + dateDir + realFileName;
//            String url ="F:\qk\images" + dateDir + realFileName;;
            return ImageVO.success(url, width, height);
        } catch (IOException e) {
            //将检查异常,转化为运行时异常
            e.printStackTrace();
            //throw new RuntimeException(e);
            return ImageVO.fail();
        }
    }
}
