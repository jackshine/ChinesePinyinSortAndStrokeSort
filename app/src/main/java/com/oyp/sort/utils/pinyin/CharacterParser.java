/*
 * Filename	CharacterParser.java
 * Company	�Ϻ�����-�ֶ��ֹ�˾��
 * @author	LuRuihui
 * @version	0.1
 */
package com.oyp.sort.utils.pinyin;


import android.util.Log;

import java.nio.charset.Charset;

/**
 * Java汉字转换为拼音
 * pinyin4j的项目地址是：http://pinyin4j.sourceforge.net/
 * github上有个地址： https://github.com/belerweb/pinyin4j
 */
public class CharacterParser {
	/**
	 * 字符编码：UTF-8
	 */
	String CHARSET_UTF_8 = "utf-8";
	private static final String TAG = "CharacterParser";
	private StringBuilder buffer;
	private String resource;
	private static CharacterParser characterParser = new CharacterParser();
	private static final String TARGET_KEY = "秘鲁";
	private static final String FAKE_VALUE = "mi";
	private static final String REAL_VALUE = "bi";

	public static CharacterParser getInstance() {
		return characterParser;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	/** * 汉字转成ASCII码 * * @param chs * @return */
	private int getChsAscii(String chs) {
		int asc = 0;
		try {
			byte[] bytes = chs.getBytes("gb2312");
			if (bytes == null || bytes.length > 2 || bytes.length <= 0) {
				throw new RuntimeException("illegal resource string");
			}
			if (bytes.length == 1) {
				asc = bytes[0];
			}
			if (bytes.length == 2) {
				int hightByte = 256 + bytes[0];
				int lowByte = 256 + bytes[1];
				asc = (256 * hightByte + lowByte) - 256 * 256;
			}
		} catch (Exception e) {
			Log.e(TAG,"ERROR:ChineseSpelling.class-getChsAscii(String chs)" + e);
		}
		return asc;
	}

	public static void main(String[] args) {
		System.out.println(CharacterParser.getInstance().getSplitSelling("我的心肝宝贝"));
		System.out.println(CharacterParser.getInstance().getSplitSelling(TARGET_KEY));
		System.out.println(CharacterParser.getInstance().getSelling(TARGET_KEY));
	}

	/** * 词组解析 * * @param chs * @return */
	public String getSelling(String chs) {
		String key, value;
		buffer = new StringBuilder();
		for (int i = 0; i < chs.length(); i++) {
			key = chs.substring(i, i + 1);
			if (key.getBytes(Charset.forName(CHARSET_UTF_8)).length >= 2) {
				value = convert(key);
				if (value == null) {
					value = "unknown";
				}
			} else {
				value = key;
			}
			buffer.append(value);
		}
		return buffer.toString();
	}

	/** * 词组解析 * * @param chs * @return */
	public String getSplitSelling(String chs) {
		String key, value;
		buffer = new StringBuilder();
		for (int i = 0; i < chs.length(); i++) {
			key = chs.substring(i, i + 1);
			if (key.getBytes(Charset.forName(CHARSET_UTF_8)).length >= 2) {
				value = convert(key);
				if (value == null) {
					value = "unknown";
				}
			} else {
				value = key;
			}
			//秘（bi）鲁是多音字，为了防止直接在解析的类里面改影响其他业务，直接在这里判断一下好了
			if (TARGET_KEY.equals(chs) && FAKE_VALUE.equals(value)) {
				value = REAL_VALUE;
			}
			buffer.append(value);
			buffer.append('@');
		}
		return buffer.toString();
	}

	public String getSpelling() {
		return this.getSelling(this.getResource());
	}

	/**
	 * 单字解析 * * @param str * @return
	 */
	public String convert(String str) {
		return PinyinUtil.hanziToPinyin(str);
	}
}
