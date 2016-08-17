package com.example.harapekoyuki.application;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

public class LoginDialog extends DialogFragment {

    private DialogInterface.OnClickListener loginClickListener = null;
    private EditText memberIdEditText;
    private EditText passwordEditText;

    public static LoginDialog newInstance(int title, int message, int text_password) {
        LoginDialog fragment = new LoginDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("message", message);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle safedInstanceState) {
        int title = getArguments().getInt("title");
        int message = getArguments().getInt("message");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setView(this.memberIdEditText)
                .setView(this.passwordEditText)
                .setPositiveButton("ログイン", this.loginClickListener);

        return builder.create();
    }


    /**
     * ログインクリックリスナーの登録
     */
    public void setOnLoginClickListener(DialogInterface.OnClickListener listener) {
        this.loginClickListener = listener;
    }

    /**
     * 会員IDのEditTextの登録
     */
    public void setMemberIdEditText(EditText memberIdEditText) {
        this.memberIdEditText = memberIdEditText;
    }

    /**
     * パスワードのEditTextの登録
     */
    public void setPasswordEditText(EditText passwordEditText) {
        this.passwordEditText = passwordEditText;
    }

}