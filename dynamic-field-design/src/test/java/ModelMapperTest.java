import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.study.App;
import org.study.config.ModelFactory;
import org.study.mapper.ModelMapper;
import org.study.mapper.PocAdminSubConfigDao;
import org.study.model.MoneyModel;

import java.util.List;
import java.util.Map;

/**
 * @author chenyao
 * @date 2020/12/24 14:44
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})
public class ModelMapperTest {
    @Autowired
    private PocAdminSubConfigDao pocAdminSubConfigDao;
    @Autowired
    private ModelMapper modelMapper;
    @Test
    public void test(){
        /**
         * 写死的情况
         */
        List<Map<String, Object>> modelResult = pocAdminSubConfigDao.getModelResult(ModelFactory.MONEY_CONFIG);
        System.out.println(modelResult.size());;
    }
    @Test
    public void test2(){
        /**
         * 通过传递参数的情况
         */
        List<MoneyModel> modelResult = modelMapper.getModelResult(MoneyModel.class);
        System.out.println(modelResult.size());
    }
}
