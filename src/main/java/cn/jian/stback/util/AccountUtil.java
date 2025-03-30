package cn.jian.stback.util;

import java.util.Base64;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

public class AccountUtil {
	public static void main(String[] args) throws Exception {

		ECKeyPair ecKeyPair = Keys.createEcKeyPair();// 调用Keys的静态方法创建密钥对
		String privateKey = "0x" + ecKeyPair.getPrivateKey().toString(16);// 获取私钥
		String publicKey = ecKeyPair.getPublicKey().toString(16);// 获取公钥
		String address = Keys.getAddress(publicKey);// 获取地址值
		System.out.println("Your private key:" + privateKey);
		System.out.println("Your address:" + "0x" + address);
		byte[] data = EnDecoderUtil.DESEncrypt("#@$#FDWE@#534564cdxzgfsd", privateKey);
		System.out.println(Base64.getEncoder().encodeToString(data));
		// 解密
		byte[] decode_bytes = EnDecoderUtil.DESDecrypt("#@$#FDWE@#534564cdxzgfsd", data);
		System.out.println(new String(decode_bytes));
//		decode("TEk9ale3Y+pw1LRGccR76tcYbW77npjQjEvSddVVlFYErOobZpYx0zGjdh+rxvk5JZ/fkVgGj213HDeZlYVi//oSlTUON2ev");
	}

	public static String decode(String data) {
		byte[] csdata = Base64.getDecoder().decode(data);
		byte[] decode_bytes = EnDecoderUtil.DESDecrypt("#@$#FDWE@#534564cdxzgfsd", csdata);
		String s = new String(decode_bytes);
		System.out.println(s);
		return s;

	}

	public static String encode(String privateKey) {
		byte[] data = EnDecoderUtil.DESEncrypt("#@$#FDWE@#534564cdxzgfsd", privateKey);
		return Base64.getEncoder().encodeToString(data);
	}

}
