package choongyul.android.com.memoappp.interfaces;

import java.sql.SQLException;

/**
 * Created by myPC on 2017-02-14.
 */
public interface IDInterface {
    public void goID() throws SQLException;
    public void fromIdToText();
    public void fromAddTextToId();

}
