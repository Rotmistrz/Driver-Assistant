package pl.filipmarkiewicz.filedatabase;

/**
 * Created by Filip on 2018-03-22.
 */

public interface FileDatabaseItem {
    public FileDatabaseRow toFileDatabaseRow();
    public boolean save(FileDatabaseManager fdm);
}
