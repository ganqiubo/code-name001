package com.pojul.fastIM.message.response;

import com.pojul.objectsocket.message.ResponseMessage;

public class ClaimCommunResp extends ResponseMessage{

    private String account;
    private String passwd;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
