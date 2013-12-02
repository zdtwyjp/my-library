package org.j2se.authorization;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 获取网络信息工具类
 * 
 * @author YangJunping
 */
public final class NetworkInfo {
	/**
	 * 获得本地IP地址
	 * 
	 * @author YangJunping
	 */
	public final static String getIPAddress() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}

	/**
	 * 获得本地IP地址集合
	 * 
	 * @author YangJunping
	 */
	public final static List<String> getIPAddressList() throws IOException {
		List<String> ips = new ArrayList<String>();
		Enumeration<?> netInterfaces;
		List<NetworkInterface> netlist = new ArrayList<NetworkInterface>();
		try {
			// 获取当前环境下的所有网卡
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces
						.nextElement();
				// 过滤 lo网卡
				if (ni.isLoopback()) {
					continue;
				}
				// 倒置网卡顺序
				netlist.add(0, ni);
			}
			// 遍历每个网卡
			for (NetworkInterface list : netlist) {
				// 获取网卡下所有ip
				Enumeration<?> cardipaddress = list.getInetAddresses();
				// 将网卡下所有ip地址取出
				while (cardipaddress.hasMoreElements()) {
					InetAddress ip = (InetAddress) cardipaddress.nextElement();
					if (!ip.isLoopbackAddress()) {
						if (ip.getHostAddress().equalsIgnoreCase("127.0.0.1")) {
							continue;
						}
						// 过滤ipv6地址
						if (ip instanceof Inet6Address) {
							continue;
						}
						// 返回ipv4地址
						if (ip instanceof Inet4Address) {
							String tmp = ip.getHostAddress();
							ips.add(tmp);
						}
					}
					// return ip.getLocalHost().getHostAddress();// 默认返回
				}

			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ips;

	}

	/**
	 * 获得本地MAC地址
	 * 
	 * @author YangJunping
	 */
	public final static List<String> getMacAddress() throws IOException {
		String os = System.getProperty("os.name");
		try {
			if (os.startsWith("Windows")) {
				return windowsParseMacAddress(windowsRunIpConfigCommand());
			} else if (os.startsWith("Linux")) {
				return linuxParseMacAddress(linuxRunIfConfigCommand());
			} else {
				throw new IOException("unknown operating system: " + os);
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}

	/**
	 * Linux stuff
	 * 
	 * @author YangJunping
	 */
	private final static List<String> linuxParseMacAddress(
			String ipConfigResponse) throws ParseException {
		List<String> result = new ArrayList<String>();
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		} catch (java.net.UnknownHostException ex) {
			ex.printStackTrace();
			throw new ParseException(ex.getMessage(), 0);
		}
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		// String lastMacAddress = null;
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();
			// boolean containsLocalHost = line.indexOf(localHost) >= 0;
			// see if line contains IP address
			// if (containsLocalHost && lastMacAddress != null) {
			// result.add(lastMacAddress);
			// } // see if line contains MAC address
			int macAddressPosition = line.indexOf("HWaddr");
			if (macAddressPosition <= 0) {
				continue;
			}
			String macAddressCandidate = line.substring(macAddressPosition + 6)
					.trim();
			if (linuxIsMacAddress(macAddressCandidate)) {
				// lastMacAddress = macAddressCandidate;
				result.add(macAddressCandidate);
				continue;
			}
		}
		if (result.size() > 0) {
			return result;
		} else {
			ParseException ex = new ParseException(
					"cannot read MAC address for " + localHost + " from ["
							+ ipConfigResponse + "]", 0);
			ex.printStackTrace();
			throw ex;
		}
	}

	private final static boolean linuxIsMacAddress(String macAddressCandidate) {
		// TODO: use a smart regular expression
		if (macAddressCandidate.length() != 17) {
			return false;
		}
		return true;
	}

	private final static String linuxRunIfConfigCommand() throws IOException {
		Process p = Runtime.getRuntime().exec("ifconfig");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
		StringBuffer buffer = new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1) {
				break;
			}
			buffer.append((char) c);
		}
		String outputText = buffer.toString();
		stdoutStream.close();
		return outputText;
	}

	private final static List<String> windowsParseMacAddress(
			String ipConfigResponse) throws ParseException {
		List<String> result = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		// String lastMacAddress = null;
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();
			// see if line contains IP address
			// if (line.endsWith(localHost) && lastMacAddress != null) {
			// result.add(lastMacAddress);
			// }
			// see if line contains MAC address
			int macAddressPosition = line.indexOf(":");
			if (macAddressPosition <= 0) {
				continue;
			}
			String macAddressCandidate = line.substring(macAddressPosition + 1)
					.trim();
			if (windowsIsMacAddress(macAddressCandidate)) {
				// lastMacAddress = macAddressCandidate;
				result.add(macAddressCandidate);
				continue;
			}
		}
		if (result.size() > 0) {
			return result;
		} else {
			ParseException ex = new ParseException(
					"cannot read MAC address from [" + ipConfigResponse + "]",
					0);
			ex.printStackTrace();
			throw ex;
		}
	}

	private final static boolean windowsIsMacAddress(String macAddressCandidate) {
		// TODO: use a smart regular expression
		if (macAddressCandidate.length() != 17) {
			return false;
		}
		return true;
	}

	private final static String windowsRunIpConfigCommand() throws IOException {
		Process p = Runtime.getRuntime().exec("ipconfig /all");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
		StringBuffer buffer = new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1) {
				break;
			}
			buffer.append((char) c);
		}
		String outputText = buffer.toString();
		stdoutStream.close();
		return outputText;
	}

	public final static void main(String[] args) {
		try {
			System.out.println("Network infos");
			System.out.println("  Operating System: "
					+ System.getProperty("os.name"));
			System.out.println("  IP/Localhost: "
					+ InetAddress.getLocalHost().getHostAddress());
			System.out.println("  MAC Address: " + getMacAddress());
			System.out.println(" IP List: " + getIPAddressList());
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}
}
