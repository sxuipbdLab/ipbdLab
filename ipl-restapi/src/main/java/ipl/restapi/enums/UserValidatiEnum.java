package ipl.restapi.enums;

/**
  * <p> Desciption </p>
  *
  * @author 原之安
  * @version v1.0
  * @date 2018/7/24 9:25
  * @package ipl.restapi.enums ipbdLab
  */

public enum UserValidatiEnum {
    USER_NAME("该用户名", 1),
    USER_PHONE("该用户手机号", 2),
    USER_EMAIL("该用户邮箱", 3);

    private int type;
    private String desc;

    UserValidatiEnum(String desc, int type) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}