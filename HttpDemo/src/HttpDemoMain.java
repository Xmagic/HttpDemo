import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpDemoMain {

	public static final String TEXT_TO_TRANLATE = "Bullshit";

	public static void main(String[] args) {
		new HttpDemoMain();
	}

	public HttpDemoMain() {
		// new Thread(new WorkerGetThread()).start();
		new Thread(new WorkerPostThread()).start();
	}

	/**
	 * Request to youdao dict for translation
	 */
	class WorkerPostThread implements Runnable {

		@Override
		public void run() {
			doPost();
		}

		public void doPost() {
			Scanner scan = null;
			StringBuilder result = new StringBuilder();
			String strUrl = "http://fanyi.youdao.com/openapi.do";
			try {
				URL url = new URL(strUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestProperty("encoding", "utf-8");
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");

				OutputStream os = (conn.getOutputStream());
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
				bw.write("keyfrom=xmagictest&key=22697543&type=data&doctype=xml&version=1.1&q=" + TEXT_TO_TRANLATE);// here
																													// should
																													// be
																													// rest
																													// URL
				// requesting for something
				bw.flush();

				// Then we can read the request result from the
				// conn.getInputStream
				scan = new Scanner(conn.getInputStream(), "utf-8");
				while (scan.hasNextLine()) {
					String line = scan.nextLine();
					result.append(line);
				}

				System.out.println(result.toString());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (null != scan)
					scan.close();
			}
		}
	}

	/**
	 * Request HTTP GET from the URL
	 */
	class WorkerGetThread implements Runnable {

		@Override
		public void run() {
			doGet();
		}

		public void doGet() {
			Scanner scan = null;
			StringBuilder result = new StringBuilder();
			String strUrl = "http://www.baidu.com";
			try {
				URL url = new URL(strUrl);
				InputStream is = url.openConnection().getInputStream();
				scan = new Scanner(is);

				while (scan.hasNextLine()) {
					String line = scan.nextLine();
					result.append(line);
				}

				System.out.println(result.toString());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (null != scan)
					scan.close();
			}
		}
	}
}
