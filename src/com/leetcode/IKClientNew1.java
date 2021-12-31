package com.leetcode;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class IKClientNew1 {

	private static List<String> list0 = Arrays
			.asList("3507,3508,3509,3510,3511,3512,3513,3514,3515,3516,3517,3518,3519,3520,3521,3522,3523,3524,3525,3526,3527,3528,3529,3530,3531,3532,3536,3551,3552,3553,3612,3613,3615,3616,3667,3668,3669,3670,3691,3692,3693,3694,3695,3696,3697,3699,3703,3704,3705,3706,3707,3709,3731,3733,3734,3735,3736,3737,3739,3740,3741,3743,3744,3745,3746,3747,3788,3789,3790,3791,3793,3794,3890,3891,3892,3893,3894,3895,3896,3897,3898,3904,3906,3907,3911,3912,3913,3914,3915,3916,3917,3918,3919,3920,3921,3922,3923,3924,3925,3926,3927,3928,3964,3965,3966,3981,3982,3983,3984,3985,3986,3987,3988,3989,3990,4027,4028,4029,4030,4031,4032,4119,4120,4121,4122,4123,4124,4125,4126,4127,4128,4129,4130,4131,4132,4133,4135,4136,4137,4138,4139,4142,4143,4144,4145,4146,4147,4148,4149,4150,4151,4152,4153,4154,4155,4169,4207,4208,4209,4210,4238,4239,4240,4255,4256,4257,4258,4288,4289,4290,4292,4293,4312,4313,4314,4315,4316,4317,4318,4319,4320,4321,4322,4323,4324,4341,4342,4343,4344,4345,4361,4362,4429,4451,4452,4453,4454,4457,4460,4461,4462,4463,4465,4468,4488,4489,4513,4514,4515,4516,4517,4518,4519,4520,4521,4554,4555,4556,4557,4558,4559,4560,4561,4562,4563,4564,4565,4567,4568,4569,4570,4571,4572,4573,4664,4665,4699,4701,4720,4721,4723,4733,4734,4735,4744,4745,4746,4747,4748,4749,4754,4755,4756,4757,4763,4798,4799,4800,4801,4802,4804,4805,4806,4807,4808,4809,4814,4830,4831,4834,4908,4922,4923,4945,4954,4983,4984,4985,4986,4987,4988,4989,4993,5003,5004,5005,5006,5007,5008,5009,5010,5011,5012,5013,5014,5015,5016,5017,5018,5072,5103,5104,5105,5126,5127,5128,5129,5143,5144,5145,5146,5147,5148,5149,5150,5170,5171,5172,5251,5287,5288,5295,5296,5330,5331,5332,5341,5343,5352,5358,5365,5394,5429,5430,5431,5432,5434,5435,5436,5437,5438,5439,5440,5441,5442,5463,5464,5489,5490,5505,5526,5531,5533,5534,5535,5536,5542,5543,5545,5546,5576,5626,5627,5628,5646,5649,5691,5692,5693,5694,5697,5699,5700,5701,5702,5703,5704,5706,5737,5738,5753,5754,5755,5760,5790,5791,5792,5793,5794,5795,5796,5804,5857,5858,5859,5860,5861,5872,5874,5875,5876,5878,5879,5880,5883,5885,5886,5887,5927,5928,5929,5959,5964,5965,5966,5967,6059,6060,6061,6094,6095,6096,6097,6098,6099,6101,6102,6103,6104,6105,6106,6108,6124,6126,6147,6148,6149,6162,6175,6176,6177,6179,6185,6186,6293,6294,6295,6296,6297,6298,6299,6300,6330,6331,6332,6333,6395,6396,6401,6402,6461,6462,6463,6464,6465,6466,6467,6468,6469,6470,6472,6475,6547,6568,6569,6576,6577,6603,6604,6605,6606,6632,6633,6634,6635,6636,6637,6638,6641,6642,6643,6644,6688,6689,6690,6691,6692,6693,6699,6700,6701,6702,6703,6704,6705,6706,6720,6721,6722,6760,6761,6762,6763,6764,6765,6766,6767,6800,6802,6803,6804,6856,6857,6858,6859,6869,6875,6876,6877,6878,6879,6880,6881,6882,6883,6884,6886,6934,6935,6936,6959,6960,6974,6975,6988,6991,6992,6993,6994,6995");
	
	private static String csrftoken = null;
	private static Map<Integer, String> course = new TreeMap<>();
	private static Map<Integer, String> courseTest = new TreeMap<>();
	private static Map<Integer, String> courseMock = new TreeMap<>();
	

	public static void main(String[] args) throws Exception {
		int start = 7000;
		int end = 7001;
		int cnt = 1;
		for(int i=start; i<end; i=i+cnt) {
			if(i+cnt>end) {
				cnt = end - i +1;
			}
			start(i, i+cnt);
			//System.out.println("\n");
			FileWriter fileWriter = new FileWriter("C:\\deepakEduDrive\\new1author.txt", true);
			BufferedWriter printWriter = new BufferedWriter(fileWriter);
			for(Map.Entry<Integer, String> set :course.entrySet()) {
				printWriter.append(set.getKey()+"="+set.getValue());
				printWriter.append("\n");
			}
			printWriter.close();
			course.clear();
			
			FileWriter fileWriter2 = new FileWriter("C:\\deepakEduDrive\\new1MockAuth.txt", true);
			BufferedWriter printWriter2 = new BufferedWriter(fileWriter2);
			for(Map.Entry<Integer, String> set :courseMock.entrySet()) {
				printWriter2.append(set.getKey()+"="+set.getValue());
				printWriter2.append("\n");
			}
			printWriter2.close();
			courseMock.clear();
		/*	
			FileWriter fileWriter3 = new FileWriter("C:\\deepakEduDrive\\new1Test.txt", true);
			BufferedWriter printWriter3 = new BufferedWriter(fileWriter2);
			for(Map.Entry<Integer, String> set :courseTest.entrySet()) {
				printWriter3.append(set.getKey()+"="+set.getValue());
				printWriter3.append("\n");
			}
			printWriter3.close();
			courseTest.clear();*/
		}	
	}







//String url = "https://uplevel.interviewkickstart.com/resource/rc-instruction-2284-23211-205-"+i;
	private static String searchString ="<h4 class=\"mg-b-0\">";
	private static void start(int i, int n) throws Exception {

		while (i<n) {
			if(!list0.contains(i)) {
				//continue;
			}
			int j=0;
			String url = "https://uplevel.interviewkickstart.com/resource/helpful-class-video-2319-85-"+i+"-0";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			loadHeader(con);


			int responseCode = con.getResponseCode();
			int len = con.getContentLength();
			boolean close = true;
			if(responseCode==200 ) {
				//System.out.print(i+":");
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String output;
				StringBuffer response = new StringBuffer();
				// csrftoken = con.getHeaderField("csrftoken");
				while ((output = in.readLine()) != null) {
					j++;
					if(j>460) {
						String str= "";
					}
					if(output.contains(searchString)) {
						loadMap(i,output);
						in.close();
						break;
					}
					
					//response.append(output);
					
					
				}
				if(close)
				in.close();
				 
			}
			i++;
			Thread.sleep(5000);
		}
	}


private static void loadMap(Integer key, String value) {
	value = value.substring(searchString.length(), value.length()-5);
	if(value.contains("Mock")) {
		courseMock.put(key, value);
	}else if(value.contains("Test Review")) {
		courseTest.put(key, value);
	}else {
		course.put(key, value);
	}
	
}
	
	private static void loadHeader(HttpURLConnection con) throws ProtocolException {
		loadHeader(con, "GET");
	}

	private static void loadHeader(HttpURLConnection con, String method) throws ProtocolException {
		// Setting basic post request
		con.setRequestMethod(method);
		con.setRequestProperty("authority", "uplevel.interviewkickstart.com");
		con.setRequestProperty("pragma", "no-cache");
		con.setRequestProperty("cache-control", "no-cache");
		con.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
			
		con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36");
		con.setRequestProperty("sec-fetch-site", "same-origin");
		con.setRequestProperty("sec-fetch-mode", "navigate");
		con.setRequestProperty("sec-fetch-dest", "document");
		
		
		con.setRequestProperty("sec-fetch-user", "?1");

		con.setRequestProperty("referer", "https://uplevel.interviewkickstart.com/schedule/");
		
		
		con.setRequestProperty("accept-language", "en-US,en;q=0.9");
		
		
		
		con.setRequestProperty("cookie",
				"csrftoken=C7WEKYe3R9ej7LujHz0Eidsu9S0xrRqg9bdqDAGrlR4A1cbkAe0k9Q1RdM2LHUU1; sessionid=nmjxrjwe9uu9mrnfus9nd48xgzl6fio4; _ga=GA1.2.1698293999.1631765311; _gid=GA1.2.873597057.1631765311; _hjid=215e3324-a1c6-4ad9-8b2f-3a04c8e25c38; _hjIncludedInPageviewSample=1; _hjAbsoluteSessionInProgress=0; last_pathname=/resource/helpful-class-video-2319-85-8659-0; _gat_gtag_UA_54935903_2=1");
	
		//con.setRequestProperty("cookie", "_ga=GA1.2.1719317506.1597947189; _hjid=84cdd69a-473c-401c-98f4-d9617d96bcf4; _gid=GA1.2.860139481.1599869613; csrftoken=sQpSlWqrpDt1XhqmVGFChOOLLJaWwOKIHI7Che8CibizEHoz9T35g62L20BbOUcW; sessionid=txlj8mxk914cxb5ar97bxg8r31uldvm4; _gcl_au=1.1.2101550324.1600407638; _uetsid=cc2c3ca8f68eac23cbe459c44877b398; _uetvid=5bfb9a48dd385e29f1ce18839e6930e2; _fbp=fb.1.1600407638192.1682530356; _hjTLDTest=1; _hjAbsoluteSessionInProgress=1; __extfc=1; _hjIncludedInPageviewSample=1; last_pathname=/resource/helpful-class-video-2319-85-549-0");
	}

}
