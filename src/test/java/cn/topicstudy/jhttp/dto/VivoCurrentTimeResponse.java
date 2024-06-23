package cn.topicstudy.jhttp.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;


/**
 * {
 *     "code": 200,
 *     "msg": "正常!",
 *     "data": {
 *         "tempCookies": "{\"cookieId\":\"164225eba1a14cd9af797bd8fc6c922c\",\"fakeSessionId\":\"a0c62bc5b705429da1dcdfe02c89c459\"}",
 *         "imgHostUrl": "https://shopact-vivofs.vivo.com.cn/campaign/",
 *         "vivoShopUrlPrefix": "//shop.vivo.com.cn/wap",
 *         "callAppSwitch": "0",
 *         "nowTime": 1718004184928,
 *         "prdHost": "//mshopact.vivo.com.cn"
 *     },
 *     "success": true
 * }
 */
@Data
public class VivoCurrentTimeResponse {
    private Integer code;
    private String msg;
    private Dta data;
    private Boolean success;

    @Data
    public  class Dta{
        private Long nowTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

