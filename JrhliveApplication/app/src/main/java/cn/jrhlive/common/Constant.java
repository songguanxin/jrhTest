package cn.jrhlive.common;

/**
 * desc:
 * Created by jiarh on 17/1/19 10:27.
 */

public class Constant {

    /**
     * 笑话大全
     */
    public static class Joke{

        /**
         * 接口地址：http://japi.juhe.cn/joke/content/list.from
         支持格式：json
         请求方式：http get
         请求示例：http://japi.juhe.cn/joke/content/list.from?key=您申请的KEY&page=2&pagesize=10&sort=asc&time=1418745237
         接口备注：根据时间戳返回该时间点前或后的笑话列表
         调用样例及调试工具：API测试工具
         请求参数说明：
         名称	类型	必填	说明
         sort	string	是	类型，desc:指定时间之前发布的，asc:指定时间之后发布的
         page	int	否	当前页数,默认1
         pagesize	int	否	每次返回条数,默认1,最大20
         time	string	是	时间戳（10位），如：1418816972
         key	string	是	您申请的key
         返回参数说明：
         名称	类型	说明
         error_code	int	返回码
         reason	string	返回说明
         */
        public static final String BASE_URL ="http://japi.juhe.cn/";
    }
}
