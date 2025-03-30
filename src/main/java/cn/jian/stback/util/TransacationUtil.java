package cn.jian.stback.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

/**
 * 转账提现交易
 * 
 * @author lu.jian
 *
 */
@Slf4j
public class TransacationUtil {

	public static Web3j web3j = null;

	public static String BSC_URL = "https://bsc.blockpi.network/v1/rpc/059e0628434844a5bf00b054b9eedf22607dd95a";

	public static Web3j getWeb3j() {

		if (web3j == null) {
			OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();

//			OkHttpClient okHttpClient = httpBuilder.connectTimeout(60000L, TimeUnit.MILLISECONDS)
//					.proxy(new java.net.Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10809)))
//					.readTimeout(300000L, TimeUnit.MILLISECONDS).writeTimeout(300000L, TimeUnit.MILLISECONDS).build();
			OkHttpClient okHttpClient = httpBuilder.connectTimeout(60000L, TimeUnit.MILLISECONDS)
					.readTimeout(5, TimeUnit.MINUTES).writeTimeout(5, TimeUnit.MINUTES).build();
			web3j = Web3j.build((new HttpService(BSC_URL, okHttpClient)));
		}
		return web3j;
	}

//	public static void main(String[] args) throws Exception {
//		List<String> usdts = FileUtils.readLines(new File("D://file/usdt.txt"), "utf-8");
//		Credentials credentials = Credentials
//				.create("422ccb5640099b03dc07790711187e6cdf6d3f31c7404fce84d0f08a17749d37");
//		System.out.println(usdts.size());
//		BigInteger nonce = TransacationUtil.getWeb3j()
//				.ethGetTransactionCount("0x784B14C3a333fa245E62419b596b8FC2e4f87203", DefaultBlockParameterName.LATEST)
//				.sendAsync().get().getTransactionCount();
//		a: for (String usdt : usdts) {
//			Thread.sleep(500);
//			String[] strs = usdt.split(",");
//			String address = strs[0];
//			String aomunt = strs[1];
//			tansUsdt(address, new BigDecimal(aomunt), nonce, credentials);
//			nonce = nonce.add(new BigInteger("1"));
//		}
//
//	}

	public static BigInteger getBlockNumber() throws Exception {
		Web3j web3j = getWeb3j();
		EthBlockNumber ethBlockNumber = web3j.ethBlockNumber().sendAsync().get();
		BigInteger blockNumber = ethBlockNumber.getBlockNumber();
		return blockNumber;
	}

	public static Credentials getCredentials(String data) {
		String key = AccountUtil.decode(data);
		Credentials credentials = Credentials.create(key);
		return credentials;
	}

	public static void main(String[] args) throws Exception {
		BigDecimal a= getUsdt("0x2436f6208694B8ebb69d6a0d3c76C78a817891CD");
		System.out.println(a);
	}

	public static BigDecimal getUsdt(String address) throws Exception {
		return getBalance(address, "0x55d398326f99059fF775485246999027B3197955", new BigDecimal("1000000000000000000"));
	}

	public static String tansUsdt(String to, BigDecimal amount, BigInteger nonce, Credentials credentials)
			throws Exception {
		return transOther(credentials, nonce, to, amount + "", null, "0x55d398326f99059fF775485246999027B3197955",
				new BigDecimal("1000000000000000000"));
	}

	/**
	 * 测试主币转账
	 * 
	 * @param credentials 账户凭证私钥和文件都可以生成
	 * @param to          账户
	 * @throws Exception
	 */
	public static void trans(Credentials credentials, String to, String value, String gasPriceParam) throws Exception {
		Web3j web3j = getWeb3j();
		String from = credentials.getAddress();
		BigInteger gasPrice = null;
		if (gasPriceParam == null) {
			gasPrice = web3j.ethGasPrice().send().getGasPrice();
		} else {
			gasPrice = Convert.toWei(gasPriceParam, Convert.Unit.GWEI).toBigInteger();
		}
		System.out.println(gasPrice);
		BigInteger value1 = Convert.toWei(value, Convert.Unit.ETHER).toBigInteger();
		String data = "hello eth";
		BigInteger nonce = web3j.ethGetTransactionCount(from, DefaultBlockParameterName.LATEST).send()
				.getTransactionCount();
		RawTransaction rawTx = RawTransaction.createTransaction(nonce, gasPrice, new BigInteger("60000"), to, value1,
				data);
		byte[] signedMessage = TransactionEncoder.signMessage(rawTx, 56, credentials);
		// Numeric.toHexSting()方法的作用，是将字节数组转换为带0x前缀的16进制字符串
		String hexValue = Numeric.toHexString(signedMessage);
		String txHash = web3j.ethSendRawTransaction(hexValue).send().getTransactionHash();
		System.out.println("tx receipt => " + txHash);
	}

	/**
	 * 测试代币转账
	 * 
	 * @param credentials     凭证
	 * @param to              收
	 * @param value           值
	 * @param gasPriceParam   gas
	 * @param contractAddress 代币合约
	 * @throws Exception
	 */
	public static String transOther(Credentials credentials, BigInteger nonce, String to, String value,
			String gasPriceParam, String contractAddress, BigDecimal jd) throws Exception {
		Web3j web3j = getWeb3j();

		BigInteger va = new BigDecimal(value).multiply(jd).toBigInteger();
		// 生成参数和方法名
		org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function("transfer",
				Arrays.asList(new Address(to), new Uint256(va)), Arrays.asList(new TypeReference<Type>() {
				}));

		// 编译参数
		String encodedFunction = FunctionEncoder.encode(function);
		BigInteger gasPrice = null;
		if (gasPriceParam == null) {
			gasPrice = web3j.ethGasPrice().send().getGasPrice();
		} else {
			gasPrice = Convert.toWei(gasPriceParam, Convert.Unit.GWEI).toBigInteger();
		}

		RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, new BigInteger("100000"),
				contractAddress, encodedFunction);

		// 调用方法
		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, 56, credentials);
		String hexValue = Numeric.toHexString(signedMessage);

		EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
		if (!ethSendTransaction.hasError()) {
			String transactionHash = ethSendTransaction.getTransactionHash();
			log.info("hash:" + transactionHash);
			return transactionHash;
		}
		return null;
	}

	/**
	 * 测试代币转账
	 * 
	 * @param credentials     凭证
	 * @param to              收
	 * @param value           值
	 * @param gasPriceParam   gas
	 * @param contractAddress 代币合约
	 * @throws Exception
	 */
	public static BigDecimal getBalance(String address, String contractAddress, BigDecimal jd) throws Exception {
		Web3j web3j = getWeb3j();
		String methodName = "balanceOf";
		List<Type> inputParameters = new ArrayList<>();
		List<TypeReference<?>> outputParameters = new ArrayList<>();
		Address fromAddress = new Address(address);
		inputParameters.add(fromAddress);
		TypeReference<Uint256> typeReference = new TypeReference<Uint256>() {
		};
		outputParameters.add(typeReference);
		org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(methodName, inputParameters,
				outputParameters);
		String encodedFunction = FunctionEncoder.encode(function);
		Transaction transaction = Transaction.createEthCallTransaction(address, contractAddress, encodedFunction);
		EthCall ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
		List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
		String value = String.valueOf(results.get(0).getValue());
		BigDecimal a = new BigDecimal(value).divide(jd, 0, RoundingMode.DOWN);
		return a;
	}

	public static boolean queryHash(String txHash) throws Exception {
		System.out.println("wait for receipt...");
		Web3j web3j = getWeb3j();
		Optional<TransactionReceipt> receipt = null;
		receipt = web3j.ethGetTransactionReceipt(txHash).send().getTransactionReceipt();
		if (receipt.isPresent()) {
			log.info("got receipt");
			log.info(receipt.get().getStatus());
			return receipt.get().getStatus().equals("0x1");
		}
		return false;

	}
}
