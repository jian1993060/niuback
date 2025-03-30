package cn.jian.stback.util;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Properties;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;

import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.InvalidKeySpecException;

public class ECSDAKit {
    private static final X9ECParameters CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1");
    public static final ECDomainParameters CURVE;
    private final ECPrivateKey eckey;
    private final String secret;

    public ECSDAKit(String privKey) {
        try {
            this.eckey = this.generatePrivateKey(Utils.hexToBytes(privKey));
            this.secret = privKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException var3) {
            var3.printStackTrace();
            throw new RuntimeException(var3);
        }
    }

    public static String[] generateKeyPair() {
        ECKeyPairGenerator generator = new ECKeyPairGenerator();
        ECKeyGenerationParameters keygenParams = new ECKeyGenerationParameters(CURVE, new SecureRandom());
        generator.init(keygenParams);
        AsymmetricCipherKeyPair keypair = generator.generateKeyPair();
        ECPrivateKeyParameters privParams = (ECPrivateKeyParameters)keypair.getPrivate();
        ECPublicKeyParameters pubParams = (ECPublicKeyParameters)keypair.getPublic();
        BigInteger priv = privParams.getD();
        String privHex = Utils.bytesToHex(Utils.bigIntegerToBytes(priv, 32));
        String pubHex = Utils.bytesToHex(pubParams.getQ().getEncoded(true));
        return new String[]{privHex, pubHex};
    }

    public static boolean verifyEcdsaSignature(String content, String signature, String pub) {
        ECDSASigner signer = new ECDSASigner();
        ECPublicKeyParameters params = new ECPublicKeyParameters(CURVE.getCurve().decodePoint(Utils.hexToBytes(pub)), CURVE);
        signer.init(false, params);
        BigInteger[] rs = decodeFromDER(Utils.hexToBytes(signature));
        return signer.verifySignature(Utils.sha256(Utils.sha256(content.getBytes(StandardCharsets.UTF_8))), rs[0], rs[1]);
    }

    public static BigInteger[] decodeFromDER(byte[] bytes) {
        ASN1InputStream decoder = null;

        BigInteger[] var6;
        try {
            Properties.setThreadOverride("org.bouncycastle.asn1.allow_unsafe_integer", true);
            decoder = new ASN1InputStream(bytes);
            ASN1Primitive seqObj = decoder.readObject();
            DLSequence seq = (DLSequence)seqObj;

            try {
                ASN1Integer r = (ASN1Integer)seq.getObjectAt(0);
                ASN1Integer s = (ASN1Integer)seq.getObjectAt(1);
                var6 = new BigInteger[]{r.getPositiveValue(), s.getPositiveValue()};
            } catch (ClassCastException var16) {
                throw new RuntimeException(var16);
            }
        } catch (IOException var17) {
            throw new RuntimeException(var17);
        } finally {
            if (decoder != null) {
                try {
                    decoder.close();
                } catch (IOException var15) {
                }
            }

            Properties.removeThreadOverride("org.bouncycastle.asn1.allow_unsafe_integer");
        }

        return var6;
    }
    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
//    	ECPrivateKey eckey = generatePrivateKey(Utils.hexToBytes("d0ae81bfd5d6393fcd2e374022114915ec2a2aa557f71ca65b6eef838a0072c9"));    
    	generateKeyPair();
	}

    public static ECPrivateKey generatePrivateKey(byte[] keyBin) throws InvalidKeySpecException, NoSuchAlgorithmException {
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256k1");
        KeyFactory kf = KeyFactory.getInstance("EC", new BouncyCastleProvider());
        ECNamedCurveSpec params = new ECNamedCurveSpec("secp256k1", spec.getCurve(), spec.getG(), spec.getN());
        ECPrivateKeySpec privKeySpec = new ECPrivateKeySpec(new BigInteger(keyBin), params);
        return (ECPrivateKey)kf.generatePrivate(privKeySpec);
    }

    public String sign(byte[] message) {
        try {
            Signature dsa = Signature.getInstance("SHA256withECDSA");
            dsa.initSign(this.eckey);
            dsa.update(Utils.sha256(message));
            return Utils.bytesToHex(dsa.sign());
        } catch (SignatureException | InvalidKeyException | NoSuchAlgorithmException var3) {
            var3.printStackTrace();
            throw new RuntimeException(var3);
        }
    }

    public String getPublicKey() {
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256k1");
        ECPoint pointQ = spec.getG().multiply(new BigInteger(this.secret, 16));
        byte[] publickKeyByte = pointQ.getEncoded(true);
        return Utils.bytesToHex(publickKeyByte);
    }

    static {
        CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(), CURVE_PARAMS.getH());
    }
}