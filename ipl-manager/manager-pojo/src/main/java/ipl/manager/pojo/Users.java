package ipl.manager.pojo;

import java.util.Date;

public class Users {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.user_id
     *
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.user_name
     *
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.user_password
     *
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    private String userPassword;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.phone
     *
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.email
     *
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.gen_time
     *
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    private Date genTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.login_time
     *
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    private Date loginTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.last_login_time
     *
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    private Date lastLoginTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column users.login_count
     *
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    private Integer loginCount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.user_id
     *
     * @return the value of users.user_id
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.user_id
     *
     * @param userId the value for users.user_id
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.user_name
     *
     * @return the value of users.user_name
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.user_name
     *
     * @param userName the value for users.user_name
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.user_password
     *
     * @return the value of users.user_password
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.user_password
     *
     * @param userPassword the value for users.user_password
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.phone
     *
     * @return the value of users.phone
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.phone
     *
     * @param phone the value for users.phone
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.email
     *
     * @return the value of users.email
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.email
     *
     * @param email the value for users.email
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.gen_time
     *
     * @return the value of users.gen_time
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public Date getGenTime() {
        return genTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.gen_time
     *
     * @param genTime the value for users.gen_time
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public void setGenTime(Date genTime) {
        this.genTime = genTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.login_time
     *
     * @return the value of users.login_time
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.login_time
     *
     * @param loginTime the value for users.login_time
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.last_login_time
     *
     * @return the value of users.last_login_time
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.last_login_time
     *
     * @param lastLoginTime the value for users.last_login_time
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column users.login_count
     *
     * @return the value of users.login_count
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public Integer getLoginCount() {
        return loginCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column users.login_count
     *
     * @param loginCount the value for users.login_count
     * @mbg.generated Fri Mar 16 10:09:06 CST 2018
     */
    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }
}