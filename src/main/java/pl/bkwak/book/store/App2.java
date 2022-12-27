package pl.bkwak.book.store;

import org.apache.commons.codec.digest.DigestUtils;
import pl.bkwak.book.store.core.Authenticator;

public class App2 {
    public static void main(String[] args) {
        String hash = DigestUtils.md5Hex("adam" + Authenticator.seed);
        System.out.println(hash);
    }
}
