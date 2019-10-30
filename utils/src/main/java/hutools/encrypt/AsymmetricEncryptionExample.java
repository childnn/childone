package hutools.encrypt;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/4/25 20:54
 * 非对称加密
 */
public class AsymmetricEncryptionExample {

    static void reaEncrypt(String data, String privateKeyString, String publicKeyString, KeyType encryptType, KeyType decryptType) {
        // 将公私钥字符串转换成对象
        PrivateKey privateKey = SecureUtil.generatePrivateKey(AsymmetricAlgorithm.RSA.getValue(), Base64.decode(privateKeyString));
        PublicKey publicKey = SecureUtil.generatePublicKey(AsymmetricAlgorithm.RSA.getValue(), Base64.decode(publicKeyString));
        // 获取字符串 byte 数组
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        // 创建 RSA 对象
        RSA rsa = new RSA();
        // 设置公钥, 谭厚执行公钥加密
        byte[] encrypt = rsa.setPublicKey(publicKey).encrypt(bytes, encryptType);
        // 设置私钥, 然后执行私钥解密
        byte[] decrypt = rsa.setPrivateKey(privateKey).decrypt(encrypt, decryptType);

        // 解密内容
        System.out.format("%s 加密, %s解密, 解密内容: %s", encryptType, decryptType, new String(decrypt, StandardCharsets.UTF_8));
    }

    public static void main(String[] args) {
        // 生成密钥对
        KeyPair keyPair = SecureUtil.generateKeyPair(AsymmetricAlgorithm.RSA.getValue());
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        // 将公钥私钥转换成 Base64 字符串
        String privateKeyString = java.util.Base64.getEncoder().encodeToString(privateKey.getEncoded());
        String publicKeyString = java.util.Base64.getEncoder().encodeToString(publicKey.getEncoded());
        // 设置要加密的内容
        String data = "Child";

        // RSA 公钥加密, 私钥解密
        reaEncrypt(data, privateKeyString, publicKeyString, KeyType.PublicKey, KeyType.PrivateKey);
        // RSA 私钥加密, 公钥解密
        //reaEncrypt(data, privateKeyString, publicKeyString, KeyType.PrivateKey, KeyType.PublicKey);

    }

}
