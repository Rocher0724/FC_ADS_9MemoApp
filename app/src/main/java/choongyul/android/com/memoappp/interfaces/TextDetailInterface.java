package choongyul.android.com.memoappp.interfaces;

import java.sql.SQLException;

import choongyul.android.com.memoappp.domain.Memo;


/**
 * Created by myPC on 2017-02-14.
 */

public interface TextDetailInterface {
    public void backToText();
    public void saveToList(Memo memo) throws SQLException;
    public void saveToTextModify(Memo memo) throws SQLException;
}
