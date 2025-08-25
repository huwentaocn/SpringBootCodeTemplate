package com.hwt.sbct.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description 社交类型
 * @Date 2025/8/20 15:41 星期三
 * @Author Hu Wentao
 */
@Getter
@AllArgsConstructor
public enum SocialType {

    DINGTALK("dingtalk", "钉钉"),

    BAIDU("baidu", "百度"),

    GITHUB("github", "github"),

    GITEE("gitee", "gitee"),

    WEIBO("weibo", "微博"),

    CODING("coding", "coding"),

    OSCHINA("oschina", "开源中国"),

    ALIPAY("alipay", "支付宝"),

    QQ("qq", "QQ"),

    WECHAT_OPEN("wechat_open", "微信开放平台"),

    CSDN("csdn", "CSDN"),

    TAOBAO("taobao", "淘宝"),

    GOOGLE("google", "谷歌"),

    FACEBOOK("facebook", "脸书"),

    DOUYIN("douyin", "抖音"),

    LINKEDIN("linkedin", "领英"),

    MICROSOFT("microsoft", "微软"),

    MI("mi", "小米"),

    TOUTIAO("toutiao", "今日头条"),

    TEAMBITION("teambition", "Teambition"),

    PINTEREST("pinterest", "Pinterest"),

    RENREN("renren", "人人"),

    STACK_OVERFLOW("stack_overflow", "Stack Overflow"),

    HUAWEI("huawei", "华为"),

    WECHAT_ENTERPRISE("wechat_enterprise", "企业微信"),

    KUJIALE("kujiale", "酷家乐"),

    GITLAB("gitlab", "gitlab"),

    MEITUAN("meituan", "美团"),

    ELEME("eleme", "饿了么"),

    MYGITLAB("mygitlab", "自定义的Gitlab"),

    TWITTER("twitter", "推特"),

    WECHAT_MP("wechat_mp", "微信公众号平台"),

    ALIYUN("aliyun", "阿里云"),

    XMLY("xmly", "喜马拉雅"),

    FEISHU("feishu", "飞书"),


    ;

    private final String type;

    private final String description;
}
