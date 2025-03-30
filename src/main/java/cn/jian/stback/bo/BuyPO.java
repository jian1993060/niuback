package cn.jian.stback.bo;

import java.net.URLDecoder;

import lombok.Data;

@Data
public class BuyPO {

	int num;

	String id;

	String signUrl;
	
	public static void main(String[] args) throws Exception {
		String s="https://render.alipay.com/p/s/i/?scheme=alipays%3A%2F%2Fplatformapi%2Fstartapp%3FappId%3D2021002147615737%26page%3Dpages%252Fh5page%252Findex%253F__appxPageId%253D1%2526clickTime%253D1696736108015%2526targetURL%253Dhttps%25253A%25252F%25252Fmain.m.taobao.com%25252Fapp%25252Fmain-search%25252Fh5search-webapp%25252Fhome.html%25253FpageType%25253D1%252526env%25253Dminiapp%252526miniappId%25253D2021002147615737%252526alipayMpPluginOpen%25253Dtrue%252526spm%25253Da21d48.23524963.0.d1%26enbsv%3D0.2.2309131255.22%26chInfo%3Dch_share__chsub_DingTalkSession%26fxzjshareChinfo%3Dch_share__chsub_DingTalkSession%26shareTimestamp%3D1696736547003%26apshareid%3D76040367-223b-49bf-a7ee-22cdf99e1d6c%26shareBizType%3DH5App_XCX";
			   System.out.println(URLDecoder.decode(s,"utf-8"));
	}
}
