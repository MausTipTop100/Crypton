package net.maustiptop100.crypton.math;

public class Hex {

	private byte[] bytes;
	private String hex;
	
	public Hex(byte[] b)
	{
		this.bytes = b;
		this.hex = bytes2HexString(b);
	}
	
	public Hex(String h)
	{
		this.hex = h;
		this.bytes = hex2Bytes(h);
	}
	
	public String getHex()
	{
		return this.hex;
	}
	
	public byte[] getBytes()
	{
		return this.bytes;
	}
	
	private static String byte2Hex(byte b)
	{
		char[] hex = new char[2];
		hex[0] = Character.forDigit((b >> 4) & 0xF, 16);
		hex[1] = Character.forDigit((b & 0xF), 16);

		return new String(hex);
	}

	private static String bytes2HexString(byte[] bytes)
	{
		StringBuffer hexBuffer = new StringBuffer();

		for (int i = 0; i < bytes.length; i++)
			hexBuffer.append(byte2Hex(bytes[i]));

		return hexBuffer.toString();
	}

	private static int char2Digit(char hexChar)
	{
		int num = Character.digit(hexChar, 16);

		if (num == -1)
			throw new IllegalArgumentException("not a hex char: " + hexChar);

		return num;
	}
	
	private static byte hex2Byte(String hexString)
	{
		int first = char2Digit(hexString.charAt(0));
		int second = char2Digit(hexString.charAt(1));

		return (byte) ((first << 4) + second);
	}

	private static byte[] hex2Bytes(String s)
	{
		if (s.length() % 2 == 1)
			throw new IllegalArgumentException("hex string cannot be odd");

		byte[] baBytes = new byte[s.length() / 2];

		for (int i = 0; i < s.length(); i += 2)
			baBytes[i / 2] = hex2Byte(s.substring(i, i + 2));

		return baBytes;
	}
	
}
