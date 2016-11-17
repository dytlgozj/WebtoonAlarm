public class MainActivity extends AppCompatActivity
{
	public static String defaultUrl = "http://m.naver.com";
	Handler handler = new Handler();

	public void onCreate(Bundle savedInstanceState)
	{
		requestBtn.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v)
			{
				String urlStr = input01.getText().toString();

				ConnectThread thread = new ConnectThread(urlStr);
				thread.start();
			}
		});
	}
}

class ConnectThread extends Thread
{
	String urlStr;

	public ConnectThread(String inStr)
	{
		urlStr = inStr;
	}

	public void run()
	{
		try
		{
			final String output = request(urlStr);
			handler.post(new Runnable(){
				public void run()
				{
					txtMsg.setText(output);
				}
			});
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private String request(String urlStr)
	{
		StringBuilder output = new StringBuilder();
		try
		{
			URL url = new URL(urlStr);

			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			if(conn != NULL)
			{
				conn.setConnectTimeout(10000);
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.setDoOutput(true);

				int resCode = conn.getResponseCode();
				if(resCode == HttpURLConnection.HTTP_OK)
				{
					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String line = null;
					while(true)
					{
						line = reader.readLine();
						if(line == null)
							break;
						output.append(line + "\n");
					}
					reader.close();
					conn.disconnect();
				}
			}
		}
		catch(Exception ex)
		{
			Log.e("SampleHTTP", "Exception in processing response.", ex);
			ex.printStackTrace();
		}
		return output.toString();
	}
}