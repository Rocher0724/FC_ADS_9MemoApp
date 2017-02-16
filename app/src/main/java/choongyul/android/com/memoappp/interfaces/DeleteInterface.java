package choongyul.android.com.memoappp.interfaces;

import java.sql.SQLException;

/**
 * Created by myPC on 2017-02-15.
 */

public interface DeleteInterface { // 딜리트 인터페이스로 바꾸고 다른 것도 딜리트할수있게 만들자
    public void textDelete(int position) throws SQLException;
    public void idDelete(int position) throws SQLException;
}
