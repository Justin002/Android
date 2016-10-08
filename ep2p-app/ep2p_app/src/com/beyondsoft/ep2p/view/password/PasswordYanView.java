package com.beyondsoft.ep2p.view.password;

interface PasswordYanView {

    //void setError(String error);

    String getPassWord();

    void clearPassword();

    void setPassword(String password);

    void setPasswordVisibility(boolean visible);

    void togglePasswordVisibility();

    void setOnPasswordChangedListener(GridPasswordConfigView.OnPasswordChangedListener listener);

    void setPasswordType(PasswordType passwordType);
}
