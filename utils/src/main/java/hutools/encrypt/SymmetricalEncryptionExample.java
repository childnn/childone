package hutools.encrypt;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.nio.charset.StandardCharsets;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/25 18:30
 * 对称加密
 */
public class SymmetricalEncryptionExample {

    /**
     * 加密、解密
     *
     * @param data 加密数据
     * @param sal  {@link SymmetricAlgorithm}
     */
    public static void encrypt(SymmetricAlgorithm sal, String data) {
        // 随机生成密钥
        byte[] key = SecureUtil.generateKey(sal.getValue()).getEncoded();
        // 创建 AES 对象
        SymmetricCrypto aes = new SymmetricCrypto(sal, key);
        // 加密
        byte[] encrypt = aes.encrypt(data);
        // 解密
        byte[] decrypt = aes.decrypt(encrypt);

        System.out.println(sal.name() + " 加密的数据：" + new String(encrypt, StandardCharsets.UTF_8));
        System.out.println(sal.name() + " 解密的数据：" + new String(decrypt));
    }


    public static void main(String[] args) {
        String data = "Child";
        encrypt(SymmetricAlgorithm.AES, data);
        encrypt(SymmetricAlgorithm.ARCFOUR, data);
        encrypt(SymmetricAlgorithm.Blowfish, data);
        encrypt(SymmetricAlgorithm.DES, data);
        encrypt(SymmetricAlgorithm.DESede, data);
        encrypt(SymmetricAlgorithm.RC2, data);
        //encrypt(SymmetricAlgorithm.PBEWithMD5AndDES, data);
        //encrypt(SymmetricAlgorithm.PBEWithSHA1AndDESede, data);
        //encrypt(SymmetricAlgorithm.PBEWithSHA1AndRC2_40, data);
    }

}
